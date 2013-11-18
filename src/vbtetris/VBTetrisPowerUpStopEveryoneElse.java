package vbtetris;

import java.util.Timer;
import java.util.TimerTask;

public class VBTetrisPowerUpStopEveryoneElse extends VBTetrisPowerUp {
	Timer timer;
	
	public VBTetrisPowerUpStopEveryoneElse()
	{
		super();
	}
	
	@Override
	public boolean commitAction(VBTetrisGameBoard gameToPowUp, VBTetrisPlayer playerWithPow, VBTetrisTimer boardTime)
	{
		playerWithPow.setdropping(false);
		_player = playerWithPow;
		activity = true;
		timer = new Timer();
		timer.schedule(new MyTask(), 10*1000);

		
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
	
	class MyTask extends TimerTask {
		@Override
		public void run() 
		{
			set();
			timer.cancel();
		}
	}
	
	private void set()
	{
		_player.setdropping(true);
		readyToFire = true;
		activity = false;
	}
	
	@Override
	public boolean amIActive()
	{
		if (activity) {
			_player.setdropping(false);
		}
		return activity;
	}
}