package vbtetris;
import java.awt.event.ActionListener;

import javax.swing.Timer;
public class VBTetrisTimer extends Timer {

	public VBTetrisTimer(int initialDelay, ActionListener listener) {
		super(initialDelay, listener);
	}
	
	// Reduces the timed delay by 10%
	public void speedup(){
		this.speedupby(10);
	}
	
	// reduces speed by the given percent
	public void speedupby(int percent){
		double newSpeed = this.getDelay();
		newSpeed = newSpeed*(100-percent)/100;
		this.setDelay((int)newSpeed);
	}

}
