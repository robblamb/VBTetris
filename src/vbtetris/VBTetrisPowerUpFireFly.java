package vbtetris;

public class VBTetrisPowerUpFireFly extends VBTetrisPowerUp {
	private VBTetrisPowerUpFireFlyPiece myPiece;
	
	public VBTetrisPowerUpFireFly()
	{
		super();
		readyToFire = false;
		myPiece = new VBTetrisPowerUpFireFlyPiece();
		myPiece.setsShape();//This is just abstracted to a private method that is not random
		myName = "Firefly";
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
		if (readyToFire && _player.equals(currentPlayer)) {
			VBTetrisPieces newPiece = new VBTetrisPieces();
			newPiece.setOwner(_player.getcurPiece().getOwner());
			newPiece.setShape(_player.getNextPiece().getShape());
			myPiece.setStatus(false);
			_player.setNextPiece(newPiece);
			readyToFire = false;
			return true;
		}
		return false;
	}
}