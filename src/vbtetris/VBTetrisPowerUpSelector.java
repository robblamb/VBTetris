package vbtetris;

public class VBTetrisPowerUpSelector {
	private boolean powerUpOnGameBoard;
	
	public VBTetrisPowerUpSelector() {
		powerUpOnGameBoard = false;
	}
	
	public VBTetrisPowerUp chooseAPowerUp() {
		if (99*Math.random() < 95) {
			VBTetrisPowerUp powerToReturn = null;
			int myChoice = (int)(4*Math.random());
			switch ( myChoice ) {
				case 0:
					powerToReturn = new VBTetrisPowerUpCompleteLine();
				case 1:
					//powerToReturn = new VBTetrisPowerUpSpeedUp();
				case 2:
					powerToReturn = new VBTetrisPowerUpFireFly();
				case 3:
					powerToReturn = new VBTetrisPowerUpStopEveryoneElse();
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