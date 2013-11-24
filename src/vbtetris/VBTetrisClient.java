package vbtetris;

import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class VBTetrisClient {
	
	private Socket socket = null;
	private BufferedReader br = null;
	private BufferedWriter bw = null;
	
	
	public void connect(String serverAddr, int serverPort, String clientAddr) {			
		try {
			socket = new Socket(InetAddress.getByName(serverAddr), serverPort, InetAddress.getByName(clientAddr), 0);
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void disconnect() {
		try {
			bw.write("close");
			bw.newLine();
			bw.flush();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<String> getHighScores() {	
		
		ArrayList<String> highscores = new ArrayList<String>();
		
		try {
			bw.write("get");
			bw.newLine();
			bw.flush();
			
			// get all high scores from server
			String entry;
			while (true) {
				entry = br.readLine();
				if (entry.equals("done")) break;
				// get each line from the file and add it to the ArrayList
				highscores.add(entry);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return highscores;
	}
	
	public void putHighScore(String name, String score) {
		String scoreEntry = name + ":" + score;
		
		try {
			bw.write("put");
			bw.newLine();
			bw.flush();
			
			bw.write(scoreEntry);
			bw.newLine();
			bw.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}