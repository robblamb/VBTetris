package vbtetris;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class VBTetrisPauseScreen extends JPanel {
	private final int FRAME_WIDTH_PX;
	private final int FRAME_HEIGHT_PX;
	
	public VBTetrisPauseScreen(int w, int h, int p) {
		setBackground(new Color(0, 0, 0, 0));
		FRAME_WIDTH_PX = w;
		FRAME_HEIGHT_PX = h;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Color color = new Color(0, 0, 255, 150); 
		g.setColor(color);
		g.fillRect(0, 0, FRAME_WIDTH_PX, FRAME_HEIGHT_PX);
	}
}