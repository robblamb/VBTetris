package vbtetris;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class VBTetrisButton extends JButton {
		//implements MouseListener {
	
	public VBTetrisButton(String text) {
		setText(text);
		//addMouseListener(this);
		setFocusable(false);
	}
	
	/*
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawString(getText(), 0, 0);
	}
	*/

	/*
	public void mouseClicked(MouseEvent arg0) {
		System.out.println("Button clicked");
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	*/
}