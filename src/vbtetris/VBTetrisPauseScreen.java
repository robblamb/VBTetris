package vbtetris;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JPanel;

public class VBTetrisPauseScreen extends JPanel {
	private final int FRAME_WIDTH_PX;
	private final int FRAME_HEIGHT_PX;
	
	private JButton btnQuit;
	private JButton btnSubmitScore;
	
	public VBTetrisPauseScreen(int w, int h) {
		
		setLayout(null);
		setBackground(new Color(0, 0, 0, 0));
		
		FRAME_WIDTH_PX = w;
		FRAME_HEIGHT_PX = h;
		
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
		btnQuit.setBounds(FRAME_WIDTH_PX/2-60-75, FRAME_HEIGHT_PX/2-20, 120, 40);
		btnSubmitScore.setBounds(FRAME_WIDTH_PX/2-60+75, FRAME_HEIGHT_PX/2-20, 120, 40);
		
		// add buttons to pause screen
		add(btnQuit);
		add(btnSubmitScore);
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Color color = new Color(0, 0, 0, 220); 
		g.setColor(color);
		g.fillRect(0, 0, FRAME_WIDTH_PX, FRAME_HEIGHT_PX);
		
	}
	
	public void createButtons() {
		
	}
}