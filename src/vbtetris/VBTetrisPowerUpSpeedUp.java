package vbtetris;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 
 * @author MatthewCormons
 * Last edited: December 2, 2013
 */

public class VBTetrisPowerUpSpeedUp extends VBTetrisPowerUp {
	//CSCI331 MC STATICBINDING
	/**
	 * Static methods are resolved at compile time.  Java discourages 
	 * dynamic binding for member variables.  
	 * 
	 * Class information (i.e. methods and variables defined in the class 
	 * definition) is what is used to determine what variables and methods
	 * can be accessed in static binding.  This is also when overloading
	 * of methods occurs.  Thus, the system will allow one to access
	 * said methods and variables.
	 * 
	 * @author MatthewCormons
	 */
	private VBTetrisTimer myTime;
	Timer timer;
	
	public VBTetrisPowerUpSpeedUp()
	{
		super();//Call VBTetrisPowerUp's constructor
		myName = "Quicken";//Set name as Quicken
	}
	
	@Override
	public boolean commitAction(VBTetrisGameBoard gameToPowUp, VBTetrisPlayer playerWithPow, VBTetrisTimer boardTime)
	{
		myTime = boardTime;
		myTime.speedupby(40);//Speed up the rate which pieces drop at by 40%
		timer = new Timer();//Make a new timer
		timer.schedule(new SetTask(), 10*1000);//schedule the piece to slow down 
												//the rate they drop at 10 seconds from now

		
		return true;
	}
	
	//CSCI331 MC OVERRIDING
	public boolean secondCommit(VBTetrisPlayer currentPlayer)
	{
		if (readyToFire) {//When readyToFire == true we are ready for a new power up
			readyToFire = false;
			return true;//So return true to indicate the second action occurred successfully
		}
		return false;
	}
	
	//A new timer class so that we can take appropriate action after 
	//a certain amount of time has elapsed
	class SetTask extends TimerTask {
		@Override
		public void run() 
		{
			set();
			timer.cancel();//Cancel the timer
		}
	}
	
	private void set()
	{
		myTime.speedupby(-40);//Slow down the rate pieces drop at by 40%
		readyToFire = true;//Set readyToFire == true; we are ready for next action 
		myName = "";//Power up is done so set its name to the nothing that it is
	}
}