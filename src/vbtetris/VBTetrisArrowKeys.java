package vbtetris;

import java.awt.event.KeyEvent;

public class VBTetrisArrowKeys extends VBTetrisKeyAdapter{

	public VBTetrisArrowKeys(VBTetrisPlayer player, VBTetrisPieceMover mover) {
		super(player, mover);
	}
	@Override
	public void keyPressed(KeyEvent e)	{
		int keycode = e.getKeyCode();
		switch (keycode) {
			case KeyEvent.VK_LEFT:
				moveLeft();
				break;
			case KeyEvent.VK_RIGHT:
				moveRight();
				break;
			case KeyEvent.VK_DOWN:
				moveDown();
				break;
			case KeyEvent.VK_UP:
				rotate();
				break;
		}
	}
}