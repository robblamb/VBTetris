package vbtetris;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;

public class VBTetrisServer {
	
	private static File file = new File("highscores.txt");
	private static BufferedReader brFile = null;
	private static BufferedWriter bwFile = null;
	
	private static ServerSocket serverSocket = null;
	private static Socket clientSocket = null;
	private static BufferedReader brClientSocket = null;
	private static BufferedWriter bwClientSocket = null;
	
	
	public static void main(String[] args) {
		startServer();
	}
	
	private static void startServer() {
		try {
			serverSocket = new ServerSocket(25888,1,InetAddress.getByName("localhost"));
			
			// wait for clients to connect
			while(true) {
				clientSocket = serverSocket.accept();
				brClientSocket = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				bwClientSocket = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
				
				// wait for client to send a message
				while (true) {
					String clientMsg = brClientSocket.readLine();
					
					if (clientMsg.equals("close")) {
						break;
					} else if (clientMsg.equals("get")) {
						sendHighScores();
					} else if (clientMsg.equals("put")) {
						addHighScore(brClientSocket.readLine());
					}
				}
				clientSocket.close();
			}
		} 
		
		catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {	
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// get high scores from file to client
	private static void sendHighScores() {
		String serverMsg;
		
		try {
			
			if (!file.exists()) { file.createNewFile(); }
			brFile = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			
			while ((serverMsg = brFile.readLine()) != null) {
				// send all high scores to the client
				bwClientSocket.write(serverMsg);
				bwClientSocket.newLine();
				bwClientSocket.flush();
			}
			
			bwClientSocket.write("done");
			bwClientSocket.newLine();
			bwClientSocket.flush();
			
			try {
				if (brFile != null) brFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// add high score
	private static void addHighScore(String newEntry) {
		try {
			
			// highscores
			String fileHighScore;
			ArrayList<String> highscores = new ArrayList<String>();	// list of high scores, name:score format

			// create the high score file if it doesn't already exist
			if (!file.exists()) { file.createNewFile(); }
			
			// read all the entries from the high score file (could be empty)
			brFile = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			while ((fileHighScore = brFile.readLine()) != null) {
				// add the highscore from the file to the highscores ArrayList
				highscores.add(fileHighScore);
			}
			if (brFile != null) brFile.close();

			// write the new entries to the high score file			
			bwFile = new BufferedWriter(new FileWriter(file.getAbsoluteFile(), false)); // overwrite file
			
			// for every entry in the highscore list, parse it and eliminate the lowest score
			if (highscores.size() == 0) {	// if the list is empty just add the new entry
				bwFile.write(newEntry);
				//System.out.println("new entry: store new: " + newEntry);
			} else {	// else add the new entry in the right spot
				double newScore = Double.parseDouble(newEntry.split(":")[1]);	// parse score from newEntry
				boolean entryWritten = false;	// has the new entry been written?
				// for each entry in the old log file...
				// compare the current score to the new score
				int counter = 0;
			    for (Iterator<String> iter = highscores.iterator(); iter.hasNext();){
			    	String oldEntry = iter.next();
			    	double currScore = Double.parseDouble(oldEntry.split(":")[1]);
			    	// check if the new score is greater than the current score
			    	// write the new score to the file,
			    	if (newScore > currScore && entryWritten == false) {
			    		bwFile.write(newEntry);
			    		bwFile.newLine();
			    		entryWritten = true;
			    		counter++;
			    		//System.out.println(counter + ": store new: new score > curr score: " + newEntry);
			    		// followed by the old score (IF the old score is still top 10)
			    		if (counter < 10) {
			    			bwFile.write(oldEntry);
			    			bwFile.newLine();
			    			counter++;
			    			//System.out.println(counter + ": old score still valid, counter < 10: store old: " + oldEntry);
			    		}
			    	} else {
			    		// otherwise the new score is <= current score...
			    		// write the old score to the file
			    		bwFile.write(oldEntry);
			    		bwFile.newLine();
			    		counter++;
			    		//System.out.println(counter + ": new score is <= current score: store old: " + oldEntry);
			    	}
			    	// add the new score to the high score list if the list is not full
			    	if (newScore < currScore && iter.hasNext() == false && counter != 10) {
			    		bwFile.write(newEntry);
			    		entryWritten = true;
			    		counter++;
			    		//System.out.println(counter + ": new score was added anyway (list not full): " + newEntry);
			    	}
			    	if (counter == 10) break; // keep high score list at max of 10
			    }
			}
			bwFile.close();
			
			//System.out.println();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}