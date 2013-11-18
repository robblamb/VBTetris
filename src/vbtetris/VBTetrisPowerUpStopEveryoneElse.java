package vbtetris;

import java.util.Calendar;
import java.util.Date;

public class VBTetrisPowerUpStopEveryoneElse extends VBTetrisPowerUp {
	private long goalTime;
	private VBTetrisTimer myTime;
	
	@Override
	public boolean commitAction(VBTetrisGameBoard gameToPowUp, VBTetrisPlayer playerWithPow, VBTetrisTimer boardTime)
	{
		goalTime = getTime() + 10000;
		myTime = boardTime;
		playerWithPow.setdropping(false);
		readyToFire = true;
		_player = playerWithPow;
		
		return true;
	}
	
	public boolean secondCommit(VBTetrisPlayer currentPlayer)
	{
		if (readyToFire) {
			if (goalTime <= getTime()) {
				_player.setdropping(true);
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