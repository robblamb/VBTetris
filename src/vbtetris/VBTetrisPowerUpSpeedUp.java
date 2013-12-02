package vbtetris;

import java.util.Timer;
import java.util.TimerTask;



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
		super();
		myName = "Quicken";
	}
	
	@Override
	public boolean commitAction(VBTetrisGameBoard gameToPowUp, VBTetrisPlayer playerWithPow, VBTetrisTimer boardTime)
	{
		myTime = boardTime;
		myTime.speedupby(40);
		timer = new Timer();
		timer.schedule(new SetTask(), 10*1000);

		
		return true;
	}
	
	//CSCI331 MC OVERRIDING
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
		myTime.speedupby(-40);
		readyToFire = true;
		myName = "";
	}
}