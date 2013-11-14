package vbtetris;

public class VBTetrisPowerUpCompleteLine extends VBTetrisPowerUp {
	boolean readyToFire;
	
	public VBTetrisPowerUpCompleteLine()
	{
		super();
		readyToFire = false;
	}
	
	@Override
	public void commitAction(VBTetrisGameBoard gameToPowUp, VBTetrisPlayer playerWithPow)
	{
		readyToFire = true;
		_board = gameToPowUp;
		_player = playerWithPow;
	}
	
	public void spread()
	{
		if (readyToFire) {
			//_board.
		}
	}
}