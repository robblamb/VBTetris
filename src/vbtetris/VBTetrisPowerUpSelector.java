package vbtetris;

import java.util.Random;

public class VBTetrisPowerUpSelector {
	private boolean powerUpOnGameBoard;
	private Random random;
	
	public VBTetrisPowerUpSelector() {
		powerUpOnGameBoard = false;
		int seed = (int)(Math.random()*100000000);
		random = new Random(seed);
	}
	
	public VBTetrisPowerUp chooseAPowerUp() {
		if (random.nextInt(100) < 15) {
			VBTetrisPowerUp powerToReturn = null;
			int myChoice = random.nextInt(4);
			switch ( myChoice ) {
				case 0:
					powerToReturn = new VBTetrisPowerUpCompleteLine();
					break;
				case 1:
					powerToReturn = new VBTetrisPowerUpSpeedUp();
					break;
				case 2:
					powerToReturn = new VBTetrisPowerUpFireFly();
					break;
				case 3:
					powerToReturn = new VBTetrisPowerUpStopEveryoneElse();
					break;
			}
			return powerToReturn;
		}
		return null;
	}
	
	public boolean getPowerUpOnGameBoard() 
	{
		return powerUpOnGameBoard;
	}
	
	public void setPowerUpOnGameBoard(boolean valToSet) 
	{
		powerUpOnGameBoard = valToSet;
	}
}