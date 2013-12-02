package vbtetris;

/**
 * @author MatthewCormons
 * Last edited: December 2, 2013
 */

import java.util.Timer;
import java.util.TimerTask;

public class VBTetrisPowerUpStopEveryoneElse extends VBTetrisPowerUp {
	Timer timer;
	
	public VBTetrisPowerUpStopEveryoneElse()
	{
		super();//Call VBTetrisPowerUp's constructor
		myName = "Stop";//Set power up's name to Stop
	}
	
	@Override
	public boolean commitAction(VBTetrisGameBoard gameToPowUp, VBTetrisPlayer playerWithPow, VBTetrisTimer boardTime)
	{
		playerWithPow.setdropping(false);//Stop player with power up's piece from dropping
		_player = playerWithPow;
		activity = true;//Set activity to true so we know that an activity is occurring
		timer = new Timer();//Make a new timer
		timer.schedule(new MyTask(), 10*1000);//Schedule the power up to end after 10 seconds

		
		return true;
	}
	
	public boolean secondCommit(VBTetrisPlayer currentPlayer)
	{
		if (readyToFire) {
			readyToFire = false;//If readyToFire return true since the second action has occurred
			return true;        //at the end of the timer
		}
		return false;//Else return false since the timer has not elapsed yet
	}
	
	//Make a timer class to time how long power up lasts
	class MyTask extends TimerTask {
		@Override
		public void run() 
		{
			set();
			timer.cancel();//end the timer
		}
	}
	
	private void set()
	{
		_player.setdropping(true);//Start player with power up dropping again
		readyToFire = true;//Set readyToFire == true since we have completed the second action
		activity = false;//Set activity == false since the activity is over
		myName = "";//Set name as nothing since there is no longer any power up
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