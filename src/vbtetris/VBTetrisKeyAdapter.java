package vbtetris;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class VBTetrisKeyAdapter extends KeyAdapter {
	private VBTetrisPlayer player;
	private VBTetrisPieceMover mover;
	
	public VBTetrisKeyAdapter(VBTetrisPlayer player, VBTetrisPieceMover mover){
		this.player = player;
		this.mover = mover;
	}
	public synchronized void moveLeft(){
		mover.moveLeft(player);
	}
	public synchronized void moveRight(){
		mover.moveRight(player);
	}
	public synchronized void moveDown(){
		mover.moveDown(player);
	}
	public synchronized void rotate(){
		mover.rotate(player);
	}
	
	
}
