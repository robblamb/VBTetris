package vbtetris;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JPanel;

public class VBTetrisPauseScreen extends JPanel {
	private final int FRAME_WIDTH_PX;
	private final int FRAME_HEIGHT_PX;
	
	private JButton myButton;
	
	public VBTetrisPauseScreen(int w, int h) {
		
		setLayout(null);
		setBackground(new Color(0, 0, 0, 0));
		
		FRAME_WIDTH_PX = w;
		FRAME_HEIGHT_PX = h;
		
		myButton = new JButton("test button");
		myButton.setFocusable(false);
		myButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent e) {
		        System.out.println("Button clicked");
		    }
		});
		myButton.setBounds(FRAME_WIDTH_PX/2-50, FRAME_HEIGHT_PX/2-50, 100, 100);
		
		add(myButton);
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Color color = new Color(0, 0, 0, 220); 
		g.setColor(color);
		g.fillRect(0, 0, FRAME_WIDTH_PX, FRAME_HEIGHT_PX);
		
		
	}
}