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
	
	private int paneSize;
	private int paneBuff;
	
	private BufferedImage imgKey;
	private double lineNum;
	
	// TEMP
	ArrayList<String> playerKeys = new ArrayList<String>();
	
	public VBTetrisPauseScreen(int w, int h, VBTetrisPlayer[] players) {
		setLayout(null);
		setBackground(new Color(0, 0, 0, 0));
		
		FRAME_WIDTH_PX = w;
		FRAME_HEIGHT_PX = h;
		this.players = players;
		
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

	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// draw pause screen background
		g.setColor(new Color(0, 0, 0, 180));
		g.fillRect(0, 0, FRAME_WIDTH_PX, FRAME_HEIGHT_PX);
		
		if (VBTetris._board.isGameOver() != true) {
			drawStatus("GAME PAUSED", 30, g);	// draw status bar
			drawControls(playerKeys, paneBuff, 200, g);	// draw stats pane
			drawStats(paneBuff, 300, g);	// print stats
		} else {
			drawStatus("GAME OVER", 30, g);
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
			// stats should be stored in player class for easy retrieval
			// i think rob is having each player hold own colour, will change later
			printStat("Current Score", player.getScore(), xPos, yPos, fontSize, player.getPlayerID(), g);
			printStat("Most Points", player.getMostPoints(), xPos, yPos, fontSize, player.getPlayerID(), g);
			printStat("Lines Cleared", player.getnumLinesRemoved(), xPos, yPos, fontSize, player.getPlayerID(), g);
			printStat("Pieces Spawned", player.getPiecesSpawned(), xPos, yPos, fontSize, player.getPlayerID(), g);
			
			// update colour and text position for next player
			g.setColor(VBTetris._gameEnvir.getPieceColor(players[i-1].getPlayerID()));
			xPos = paneBuff + (paneSize * i);
		}
	}
	
	// print stat
	private void printStat(String statName, int stat, int xPos, int yPos, int fontSize, int playerID, Graphics g) {
		// where the stats start (in relation to the panel)
		int lineHeight = fontSize + 5;
		int fontOffsetX = 20;
		int fontOffsetY = lineHeight;
		
		g.setColor(VBTetris._gameEnvir.getPieceColor(playerID));
		g.drawString(statName, xPos+fontOffsetX, yPos+fontOffsetY + (int)(lineHeight*lineNum++));
		
		g.setColor(Color.WHITE);
		g.drawString(String.valueOf(stat), xPos+fontOffsetX, yPos+fontOffsetY + (int)(lineHeight*(lineNum++)));
		
		lineNum+=0.5;
	}
	
}