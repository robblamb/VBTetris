package vbtetris;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Mode;

public class VBTetrisPauseScreen extends JPanel {
	private final int FRAME_WIDTH_PX;
	private final int FRAME_HEIGHT_PX;
	private final VBTetrisPlayer[] players;
	
	private BufferedImage imgKey;
	
	private JButton btnQuit;
	private JButton btnSubmitScore;
	
	private int paneSize;
	private int paneBuff;
	
	public VBTetrisPauseScreen(int w, int h, VBTetrisPlayer[] players) {
		
		setLayout(null);
		setBackground(new Color(0, 0, 0, 0));
		
		FRAME_WIDTH_PX = w;
		FRAME_HEIGHT_PX = h;
		this.players = players;
		
		createKeyImage();
		
		// create quit game button
		btnQuit = new JButton("Quit Game");
		btnQuit.setFocusable(false);
		btnQuit.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent e) {
		    	 System.exit(0);
		    }
		});
		
		// create submit score button
		btnSubmitScore = new JButton("Submit Score");
		btnSubmitScore.setFocusable(false);
		btnSubmitScore.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent e) {
		    	System.out.println("Submit Score");
		    }
		});
		
		// position buttons
		btnQuit.setBounds(FRAME_WIDTH_PX/2-60-75, FRAME_HEIGHT_PX-120, 120, 40);
		btnSubmitScore.setBounds(FRAME_WIDTH_PX/2-60+75, FRAME_HEIGHT_PX-120, 120, 40);
		
		// add buttons to pause screen
		add(btnQuit);
		add(btnSubmitScore);
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		// draw pause screen background
		Color color = new Color(0, 0, 0, 220); 
		g.setColor(color);
		g.fillRect(0, 0, FRAME_WIDTH_PX, FRAME_HEIGHT_PX);
		
		int imgWidth = imgKey.getWidth();
		int imgHeight = imgKey.getHeight();
		int xPos = paneBuff;
		int yPos = 200;
		
		// draw controls for each player
		for (int i = 1; i <= players.length; ++i) {
			for (int j = 0; j < 3; ++j) {
				g.drawImage(imgKey, xPos, yPos, this);
				xPos += imgWidth;
				if (j == 0) {
					g.drawImage(imgKey, xPos, yPos-imgHeight-1, this);
				}
			}
			xPos = paneBuff + (paneSize * i);
		}
		
	}
	
	// get and size key image
	public void createKeyImage() {
		
		// http://stackoverflow.com/questions/4248104/applying-a-tint-to-an-image-in-java
		
		int numPlayers = players.length;
		int keyWidth;
		
		// get the key image
		try {
			imgKey = ImageIO.read(getClass().getResourceAsStream("../VBTetrisImage/key.png"));
		} catch (IOException e) {
			// do nothing
		}
		
		// set pane size, pane buffer, key image width
		paneSize = FRAME_WIDTH_PX / numPlayers;
		paneBuff = (int) (paneSize * 0.1);
		keyWidth = (int) ((paneSize-(paneBuff*2)) / 3);
		
		// scale image
		imgKey = Scalr.resize(imgKey, Mode.FIT_TO_WIDTH, keyWidth);
	}
}