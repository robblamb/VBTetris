package vbtetris;

public class VBTetrisPowerUpFireFly extends VBTetrisPowerUp {
	private VBTetrisPowerUpFireFlyPiece myPiece;
	
	public VBTetrisPowerUpFireFly()
	{
		super();
		readyToFire = false;
		myPiece = new VBTetrisPowerUpFireFlyPiece();
		myPiece.setsShape();//This is just abstracted to a private method that is not random
	}
	
	@Override
	public boolean commitAction(VBTetrisGameBoard gameToPowUp, VBTetrisPlayer playerWithPow, VBTetrisTimer boardTime)
	{
		readyToFire = true;
		_board = gameToPowUp;
		_player = playerWithPow;
		myPiece.setOwner(_player.getcurPiece().getOwner());
		_player.setcurPiece(myPiece);
		return true;
	}
	
	@Override
	public boolean secondCommit(VBTetrisPlayer currentPlayer)
	{
		if (readyToFire) {
			_player.setcurPiece(myPiece);
			readyToFire = false;
			return true;
		}
		return false;
	}
}