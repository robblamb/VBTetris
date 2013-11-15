package vbtetris;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class VBTetrisPauseScreen extends JPanel {
	private final int FRAME_WIDTH_PX;
	private final int FRAME_HEIGHT_PX;
	
	private VBTetrisButton myButton;
	
	public VBTetrisPauseScreen(int w, int h) {
		
		setLayout(null);
		setBackground(new Color(0, 0, 0, 0));
		
		FRAME_WIDTH_PX = w;
		FRAME_HEIGHT_PX = h;
		
		myButton = new VBTetrisButton("test button");
		myButton.setBounds(FRAME_WIDTH_PX/2-50, FRAME_HEIGHT_PX/2-50, 100, 100);
		
		add(myButton);
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Color color = new Color(0, 0, 0, 200); 
		g.setColor(color);
		g.fillRect(0, 0, FRAME_WIDTH_PX, FRAME_HEIGHT_PX);
		
		
	}
}