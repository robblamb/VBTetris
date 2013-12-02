package vbtetris;

/**
 * 
 * @author MatthewCormons
 * Last edited: December 2, 2013
 */

public class VBTetrisPowerUpFireFly extends VBTetrisPowerUp {
	private VBTetrisPowerUpFireFlyPiece myPiece;
	
	public VBTetrisPowerUpFireFly()
	{
		super();//Call VBTetrisPowerUp's constructor
		readyToFire = false;
		myPiece = new VBTetrisPowerUpFireFlyPiece();//Get a new Firefly piece
		myPiece.setsShape();//Set the shape of myPiece to FireFly piece
		myName = "Firefly";//set power up name to FireFly
	}
	
	@Override
	public boolean commitAction(VBTetrisGameBoard gameToPowUp, VBTetrisPlayer playerWithPow, VBTetrisTimer boardTime)
	{
		readyToFire = true;//Set true so we are ready for next action
		_board = gameToPowUp;
		_player = playerWithPow;
		myPiece.setOwner(_player.getcurPiece().getOwner());//Make owner of FireFly piece the same as 
															//the VBTetrisPlayer that is getting the 
															//power up
		_player.setcurPiece(myPiece);//Set player getting power up's piece to the FireFly piece
		return true;
	}
	
	@Override
	public boolean secondCommit(VBTetrisPlayer currentPlayer)
	{
		if (readyToFire && _player.equals(currentPlayer)) {
			//If readyToFire and currentPlayer == _player (the player who has the power up)
			//We need to set a completely new piece since _player's nextPiece currently still
			//uses the FireFly piece's overloaded methods
			VBTetrisPieces newPiece = new VBTetrisPieces();//Get the next tetris piece for the 
															//player who had the power up
			newPiece.setOwner(_player.getcurPiece().getOwner());//Set owner == _player's owner
			newPiece.setShape(_player.getNextPiece().getShape());//Set shape of newPiece ==
																//_player's next piece
			myPiece.setStatus(false);
			_player.setNextPiece(newPiece);//set _player's nextPiece to newPiece
			readyToFire = false;
			return true;//return true; we have successfully completed the second action
		}
		return false;//Else return false; we have not completed the second action yet
	}
}