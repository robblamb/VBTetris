package vbtetris;

import java.util.Calendar;
import java.util.Date;

public class VBTetrisPowerUpSpeedUp extends VBTetrisPowerUp {
	private long goalTime;
	private VBTetrisTimer myTime;
	
	@Override
	public boolean commitAction(VBTetrisGameBoard gameToPowUp, VBTetrisPlayer playerWithPow, VBTetrisTimer boardTime)
	{
		goalTime = getTime() + 10;
		myTime = boardTime;
		myTime.speedup();
		readyToFire = true;
		
		return true;
	}
	
	public boolean secondCommit(VBTetrisPlayer currentPlayer)
	{
		if (readyToFire) {
			if (goalTime <= getTime()) {
				myTime.speedupby(-10);
				readyToFire = false;
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	private long getTime() 
	{
		return new Date().getTime();
	}
}