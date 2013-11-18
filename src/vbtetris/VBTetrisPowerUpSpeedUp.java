package vbtetris;

import java.util.Timer;
import java.util.TimerTask;

public class VBTetrisPowerUpSpeedUp extends VBTetrisPowerUp {
	private VBTetrisTimer myTime;
	Timer timer;
	
	public VBTetrisPowerUpSpeedUp()
	{
		super();
	}
	
	@Override
	public boolean commitAction(VBTetrisGameBoard gameToPowUp, VBTetrisPlayer playerWithPow, VBTetrisTimer boardTime)
	{
		myTime = boardTime;
		myTime.speedup();
		timer = new Timer();
		timer.schedule(new SetTask(), 10*1000);

		
		return true;
	}
	
	public boolean secondCommit(VBTetrisPlayer currentPlayer)
	{
		if (readyToFire) {
			readyToFire = false;
			return true;
		}
		return false;
	}
	
	class SetTask extends TimerTask {
		@Override
		public void run() 
		{
			set();
			timer.cancel();
		}
	}
	
	private void set()
	{
		myTime.speedupby(11);
		readyToFire = true;
	}
}