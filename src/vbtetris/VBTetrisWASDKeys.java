package vbtetris;

import java.awt.event.KeyEvent;

public class VBTetrisWASDKeys extends VBTetrisKeyAdapter {

	public VBTetrisWASDKeys(VBTetrisPlayer player, VBTetrisPieceMover mover) {
		super(player, mover);
	}
	@Override
	public void keyPressed(KeyEvent e)	{
		int keycode = e.getKeyCode();
		switch (keycode)
		{
			case 'd':
			case 'D':
				// right
				moveRight();
				break;
			case 'a':
			case 'A':
				//left
				moveLeft();
				break;
			case 'w':
			case 'W':
				rotate();
				break;
			case 's':
			case 'S':
				moveDown();
				break;

		}
	}
	

}
