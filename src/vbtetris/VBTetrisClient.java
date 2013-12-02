package vbtetris;

import java.net.*;
import java.util.ArrayList;
import java.io.*;

/** CSCI331 ER INTERFACE
 * 
 * This is the client portion of the client / server high score leaderboard.
 * 
 * The client class provides a very simple interface for connecting to and 
 * communicating with the high score server.
 * 
 * Once the class has been instanciated, the user can call one of four
 * available methods: connect, disconnect, getHighScores, and putHighScore
 * 
 * In addition to connecting to and disconnecting from the server, the clients
 * only responsibilities are to request a list of high scores from the server,
 * and to send a single high score to the server. The client communicates
 * with the server, and the server performs the desired action. The user need
 * not know ANY details about how the client OR the server has been implimented.
 * All the user needs to know is how to use the client interface and what the 
 * methods will return.
 * 
 */
public class VBTetrisClient {
	
	private Socket socket = null;
	private BufferedReader br = null;
	private BufferedWriter bw = null;
	
	/**
	 * Connects to the high score server.
	 * 
	 * @param serverAddr (required) Server hostname or IP address.
	 * @param serverPort (required) Server port number.
	 * @param clientAddr (required) Client hostname or IP address.
	 */
	public void connect(String serverAddr, int serverPort, String clientAddr) {			
		try {
			socket = new Socket(InetAddress.getByName(serverAddr), serverPort, InetAddress.getByName(clientAddr), 0);
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** Disconnects from the high score server. */
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
	
	/**
	 * Requests a list of high scores from the server.
	 * 
	 * @param None
	 * @return ArrayList<String>, each entry in the list contains
	 * 			 a colon delimited high score (name:score).
	 */
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
				// get each entry from the server and add it to the highscores list
				highscores.add(entry);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return highscores;
	}
	
	/**
	 * Sends a high score to the server to be added to the high score list.
	 * 
	 * @param name (required) Player name.
	 * @param score (required) Player score.
	 */
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