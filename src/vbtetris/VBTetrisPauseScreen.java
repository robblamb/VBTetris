package vbtetris;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Mode;

public class VBTetrisPauseScreen extends JPanel {
	private final int FRAME_WIDTH_PX;
	private final int FRAME_HEIGHT_PX;
	private final VBTetrisPlayer[] players;
	
	private int winnerID;
	private double winnerScore;
	
	private int paneSize;
	private int paneBuff;
	
	private BufferedImage imgKey;
	private double lineNum;
	
	private VBTetrisClient client;
	
	private boolean amIRule;
	
	// TEMP
	ArrayList<String> playerKeys = new ArrayList<String>();
	
	public VBTetrisPauseScreen(int w, int h, VBTetrisPlayer[] players) {
		setLayout(null);
		setBackground(new Color(0, 0, 0, 0));
		
		FRAME_WIDTH_PX = w;
		FRAME_HEIGHT_PX = h;
		this.players = players;
		
		winnerID = 0;
		winnerScore = 0;
		
		client = new VBTetrisClient(); 
		
		// TEMP
		playerKeys.add("A");
		playerKeys.add("W");
		playerKeys.add("S");
		playerKeys.add("D");
		playerKeys.add("\u2190");
		playerKeys.add("\u2191");
		playerKeys.add("\u2193");
		playerKeys.add("\u2192");
		playerKeys.add("");
		playerKeys.add("");
		playerKeys.add("");
		playerKeys.add("");
		playerKeys.add("");
		playerKeys.add("");
		playerKeys.add("");
		playerKeys.add("");
		
		// set pane size, pane buffer, key image width
		paneSize = FRAME_WIDTH_PX / players.length;
		paneBuff = (int) (paneSize * 0.1);
				
		// get and size the key image
		try {
			imgKey = ImageIO.read(getClass().getResourceAsStream("../VBTetrisImage/key.png"));
			imgKey = createKeyImage(imgKey, paneSize, paneBuff);
		} catch (IOException e) {
			// create an image
		}

		amIRule = true;
	}
	
	public void paintRules()
	{
		if (!amIRule) {
			amIRule = true;
			setVisible(true);
			repaint();
			//amIRule = false;
		}
	}
	
	public void setAmIRule(boolean valToSet)
	{
		amIRule = valToSet;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// draw pause screen background
		g.setColor(new Color(0, 0, 0, 180));
		g.fillRect(0, 0, FRAME_WIDTH_PX, FRAME_HEIGHT_PX);
		
		if (VBTetris._board.isGameOver() != true) {
			drawStatus("GAME PAUSED", 30, g);	// draw status bar
			drawControls(playerKeys, paneBuff, 200, g);	// draw stats pane
			if (amIRule) {
				drawRules(g);
			} else {
				drawStats(paneBuff, 300, g);	// print stats
			}
		} else {
			// find the winner and pass to draw status 
			winnerID = VBTetris._board.findWinner();
			drawStatus("PLAYER " + winnerID + " WINS!", 30, g);
			drawHighScore(paneBuff, 150, g);
		}
			
	}
	
	private BufferedImage createKeyImage(BufferedImage img, int paneWidth, int buffer) {
		int keyWidth = (int) ((paneWidth-(buffer*2)) / 3);
		return Scalr.resize(img, Mode.FIT_TO_WIDTH, keyWidth);
	}
	
	private void drawStatus(String status, int yPosStatusText, Graphics g) {

		int heightStatusText = 60;
		
		// set the font for the game status
		g.setFont(new Font("Times New Roman", Font.BOLD + Font.ITALIC, VBTetrisFontUtils.adjustFontSize(36)));
		
		// draw game status box
		g.setColor(Color.BLACK);
		g.fillRect(-1, yPosStatusText, FRAME_WIDTH_PX+2, heightStatusText);
		g.setColor(Color.WHITE);
		g.drawRect(-1, yPosStatusText, FRAME_WIDTH_PX+2, heightStatusText);
		
		// print the game status
		g.setColor(Color.WHITE);
		VBTetrisFontUtils.drawCenteredString(status, 0, yPosStatusText, this.getWidth(), heightStatusText, g);
	}
	
	private void drawHighScore(int xPos, int yPos, Graphics g) {
		
		double minutesPlayed = VBTetrisGameBoard.clock.minutes();
		
		// draw high score panel
		g.setColor(Color.BLACK);
		g.fillRoundRect((FRAME_WIDTH_PX/2)-210, yPos, 420, FRAME_HEIGHT_PX-57-yPos, 10, 10);
		g.setColor(Color.YELLOW);
		g.drawRoundRect((FRAME_WIDTH_PX/2)-210, yPos, 420, FRAME_HEIGHT_PX-57-yPos, 10, 10);
		
		// print scores
		int fontSize;
		
		// set the font for the high scores title
		fontSize = VBTetrisFontUtils.adjustFontSize(28);
		g.setFont(new Font("Courier", Font.PLAIN, fontSize));
		
		VBTetrisFontUtils.drawCenteredString("High Scores", (FRAME_WIDTH_PX/2)-210, yPos/2, 420, yPos + 90, g);
		 
		// set the font for the high scores
		fontSize = VBTetrisFontUtils.adjustFontSize(18);
		g.setFont(new Font("Courier", Font.PLAIN, fontSize));
		
		// ----------------------------------------------------
		// winnerScore (calculate ppm)
		winnerScore = players[winnerID-1].getScore();
		winnerScore = winnerScore / minutesPlayed;
		winnerScore = Math.round(winnerScore * 100.0) / 100.0;
		// ----------------------------------------------------
		// CSCI331 COMMUNICATION
		// connect to high score server
		client.connect("localhost", 25888, "localhost");
		
		ArrayList<String> highscores = new ArrayList<String>();
		highscores = client.getHighScores(); // returns an ArrayList of highscore entires
		client.putHighScore("Player " + winnerID, String.valueOf(winnerScore));
		client.disconnect();
		
		// print each score
		boolean inserted = false;
		int lineHeight = fontSize + 5;
		lineNum = 90 / 2 / lineHeight;
		
		int counter = 1;
		int listSize = highscores.size();
		for (Iterator<String> iter = highscores.iterator(); iter.hasNext();){
			
			// ----------------------------------------------------
			//  PARSE EACH HIGH SCORE ENTRY FROM THE ARRAYLIST
			String[] highScores = (iter.next()).split(":");
			String name = highScores[0];
			double score = Double.parseDouble(highScores[1]);
			// ----------------------------------------------------
			
			// check if the current winners score is higher than the highscore being printed
			if (winnerScore > score && inserted == false) {
				g.setColor(players[winnerID-1].getPlayerColour());
				VBTetrisFontUtils.drawCenteredString(formatHighScoreEntry(counter, "Player " + winnerID, winnerScore),
						(FRAME_WIDTH_PX/2)-210, yPos/2, 420, yPos + 175 + (int)(lineHeight*(lineNum++)), g);
				counter++;
				lineNum+=1.3;
				inserted = true;
			}	
			
			if (counter < 11) {
				// print each high score returned from the server
				g.setColor(Color.WHITE);
				VBTetrisFontUtils.drawCenteredString(formatHighScoreEntry(counter, name, score),
						(FRAME_WIDTH_PX/2)-210, yPos/2, 420, yPos + 175 + (int)(lineHeight*(lineNum++)), g);
				counter++;
				lineNum+=1.3;
			}
			
			if (counter == 11) break; // only display top 10 scores
		}
		
		// if the list is not full and the winners score has not been printed,
		// print the winners high score at the end of the list 
		if (listSize < 10 && inserted == false) {
			g.setColor(players[winnerID-1].getPlayerColour());
			VBTetrisFontUtils.drawCenteredString(formatHighScoreEntry(counter, "Player " + winnerID, winnerScore),
					(FRAME_WIDTH_PX/2)-210, yPos/2, 420, yPos + 175 + (int)(lineHeight*(lineNum++)), g);
		}
			
	}
	
	// draw key controls for each player
	private void drawControls(ArrayList<String> keys, int x, int y, Graphics g) {
		int imgWidth = imgKey.getWidth();
		int imgHeight = imgKey.getHeight();
		
		// TEMP
		Iterator<String> itr = keys.iterator();
		
		for (int i = 1; i <= players.length; ++i) {
			for (int j = 0; j < 3; ++j) {
				drawKey(itr.next(), x, y, imgWidth, imgHeight, g);
				x += imgWidth;
				if (j == 0) {
					drawKey(itr.next(), x, y-imgHeight-1, imgWidth, imgHeight, g);
				}
			}
			x = paneBuff + (paneSize * i);
		}
	}
	
	private void drawKey(String c, int x, int y, int imgWidth, int imgHeight, Graphics g) {
		g.drawImage(imgKey, x, y, this);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Times New Roman", Font.BOLD, VBTetrisFontUtils.adjustFontSize(26)));
		VBTetrisFontUtils.drawCenteredString(c, x, y, imgWidth, imgHeight, g);
	}
	
	private void drawStats(int xPos, int yPos, Graphics g) {
		
		int fontSize;
		
		// set the font for the player stats
		if (players.length < 3) {
			fontSize = VBTetrisFontUtils.adjustFontSize(24);
		} else {
			fontSize = VBTetrisFontUtils.adjustFontSize(28);
			fontSize = (int)Math.round(fontSize / (1 + ((players.length-2)/(double)players.length)) );
		}
		g.setFont(new Font("Times New Roman", Font.BOLD, fontSize));
		
		// print stats for each player
		for (int i = 1; i <= players.length; ++i) {
			VBTetrisPlayer player = players[i-1];
			lineNum = 0.5;	// first line will start here
			
			// draw stats panel
			g.setColor(Color.BLACK);
			g.fillRoundRect(xPos, yPos, paneSize-(paneBuff*2), 315, 10, 10);
			g.setColor(VBTetris._gameEnvir.getPieceColor(players[i-1].getPlayerID()));
			g.drawRoundRect(xPos, yPos, paneSize-(paneBuff*2), 315, 10, 10);
			
			// print stats
			printStat("Current Score", player.getScore(), xPos, yPos, fontSize, player.getPlayerColour(), g);
			printStat("Most Points", player.getMostPoints(), xPos, yPos, fontSize, player.getPlayerColour(), g);
			printStat("Lines Cleared", player.getnumLinesRemoved(), xPos, yPos, fontSize, player.getPlayerColour(), g);
			printStat("Pieces Spawned", player.getPiecesSpawned(), xPos, yPos, fontSize, player.getPlayerColour(), g);
			
			// update colour and text position for next player
			g.setColor(VBTetris._gameEnvir.getPieceColor(players[i-1].getPlayerID()));
			xPos = paneBuff + (paneSize * i);
		}
	}
	
	// print stat
	private void printStat(String statName, int stat, int xPos, int yPos, int fontSize, Color colour, Graphics g) {
		// where the stats start (in relation to the panel)
		int lineHeight = fontSize + 5;
		int fontOffsetX = 20;
		int fontOffsetY = lineHeight;
		
		g.setColor(colour);
		g.drawString(statName, xPos+fontOffsetX, yPos+fontOffsetY + (int)(lineHeight*lineNum++));
		
		g.setColor(Color.WHITE);
		g.drawString(String.valueOf(stat), xPos+fontOffsetX, yPos+fontOffsetY + (int)(lineHeight*(lineNum++)));
		
		lineNum+=0.5;
	}
	
	public String formatHighScoreEntry(int i, String name, double score) {
		String strRank = String.valueOf(i) + ". ";
		String strName = name + " ";
		String strDots = "";
		String strScore = score + " ";
		
		int entryLength = strRank.length() + strName.length() + strScore.length() + 3;
		
		for (int j = entryLength; j < 29; j++) {
			strDots += ".";
		}
		strDots += " ";
		
		return strRank + strName + strDots + strScore + "ppm";
	}
	
	private void drawRules(Graphics g) {
		
		int xPos = paneBuff;
		int yPos = 260;
		int fontSize = 13;
		int lineHeight = fontSize + 5;
		int fontOffsetX = 20;
		int fontOffsetY = lineHeight;
		
		g.setFont(new Font("Times New Roman", Font.BOLD, fontSize));
		
			lineNum = 0.5;	// first line will start here
						
		g.setColor(Color.WHITE);
		g.drawString("Controls", xPos+fontOffsetX, yPos+fontOffsetY + (int)(lineHeight*(lineNum++)));
		g.drawString("  -Use the Controls Listed Above For Players 1 and 2 respectively", xPos+fontOffsetX, yPos+fontOffsetY + (int)(lineHeight*(lineNum++)));
		g.drawString("  -W/Up arrow rotates respective piece", xPos+fontOffsetX, yPos+fontOffsetY + (int)(lineHeight*(lineNum++)));
		g.drawString("  -A/Left arrow moves respective piece left", xPos+fontOffsetX, yPos+fontOffsetY + (int)(lineHeight*(lineNum++)));
		g.drawString("  -D/Right arrow moves respective piece right", xPos+fontOffsetX, yPos+fontOffsetY + (int)(lineHeight*(lineNum++)));
		g.drawString("  -S/Down arrow moves respective piece down more quickly", xPos+fontOffsetX, yPos+fontOffsetY + (int)(lineHeight*(lineNum++)));
		g.drawString("  -Press the spacebar to pause the game", xPos+fontOffsetX, yPos+fontOffsetY + (int)(lineHeight*(lineNum++)));
		g.drawString("  -Collide with randomly appearing pink power ups to gain that", xPos+fontOffsetX, yPos+fontOffsetY + (int)(lineHeight*(lineNum++)));
		g.drawString("    powerup.  Watch out though, power ups disappear when a row is", xPos+fontOffsetX, yPos+fontOffsetY + (int)(lineHeight*(lineNum++)));
		g.drawString("    removed.", xPos+fontOffsetX, yPos+fontOffsetY + (int)(lineHeight*(lineNum++)));
		g.drawString("   -Watch out.  The pieces drop faster as the game progresses.", xPos+fontOffsetX, yPos+fontOffsetY + (int)(lineHeight*(lineNum++)));
		g.drawString("    -Watch out. If your piece stops above the white line a row is removed", xPos+fontOffsetX, yPos+fontOffsetY + (int)(lineHeight*(lineNum++)));
		g.drawString("     and points deducted from your score. ", xPos+fontOffsetX, yPos+fontOffsetY + (int)(lineHeight*(lineNum++)));
		g.drawString("    -Score 20000 to win, -5000 to lose.", xPos+fontOffsetX, yPos+fontOffsetY + (int)(lineHeight*(lineNum++)));
		g.drawString("    -Points added for getting piece to bottom and removing rows.", xPos+fontOffsetX, yPos+fontOffsetY + (int)(lineHeight*(lineNum++)));
		g.drawString("Power Ups", xPos+fontOffsetX, yPos+fontOffsetY + (int)(lineHeight*(lineNum++)));				
		g.drawString("  -Stop stops your piece's descent", xPos+fontOffsetX, yPos+fontOffsetY + (int)(lineHeight*(lineNum++)));
		g.drawString("  -Extend gives access to a single piece that can extend 8 spaces to the right", xPos+fontOffsetX, yPos+fontOffsetY + (int)(lineHeight*(lineNum++)));
		g.drawString("  -Quicken accelerates every piece's descent", xPos+fontOffsetX, yPos+fontOffsetY + (int)(lineHeight*(lineNum++)));
		g.drawString("  -Firefly gives access to a piece whose rotate makes the piece jump", xPos+fontOffsetX, yPos+fontOffsetY + (int)(lineHeight*(lineNum++)));
			
	}
}
