package vbtetris;

/**
 * 
 * @author MatthewCormons
 * Last edited: December 2, 2013
 */

public class VBTetrisPowerUpCompleteLine extends VBTetrisPowerUp {
	private VBTetrisPowerUpCompletePiece myPiece;
	
	public VBTetrisPowerUpCompleteLine()
	{
		super();//Call VBTetrisPowerUp's constructor
		readyToFire = false;
		myPiece = new VBTetrisPowerUpCompletePiece(); //Get a new complete piece
		myPiece.setsShape();//Set this new piece's shape
		myName = "Extend";//Give power up the name Extend
	}
	
	@Override
	public boolean commitAction(VBTetrisGameBoard gameToPowUp, VBTetrisPlayer playerWithPow, VBTetrisTimer boardTime)
	{
		readyToFire = true;//Set true so we are ready for the next action
		_board = gameToPowUp;
		_player = playerWithPow;
		myPiece.setOwner(_player.getcurPiece().getOwner());//Set owner of piece == _player's owner
		_player.setcurPiece(myPiece);//Set _player's current piece to myPiece
		return true;
	}
	
	@Override
	public boolean secondCommit(VBTetrisPlayer currentPlayer)
	{
		if (readyToFire && _player.equals(currentPlayer)) {//If readyToFire and _player == currentPlayer
			int numToAddRight = _board.rowEmptyToRight(_player.getxPos(), _player.getyPos(), _player);
			//Get number of squares to the right of _player's piece that are empty
			myPiece.expandLeft(numToAddRight);//Expand the piece right by numToAddRight squares
			_player.setcurPiece(myPiece);//Set this new piece as _player's current piece
			readyToFire = false;
			return true;//We have successfully completed the second action
		}
		return false;//Else we have not completed the second action yet
	}
}