package vbtetris;

import vbtetris.VBTetrisPieces.Tetrominoes;

public class VBTetrisPowerUpCompleteLine extends VBTetrisPowerUp {
	private boolean readyToFire;
	private VBTetrisPowerUpCompletePiece myPiece;
	
	public VBTetrisPowerUpCompleteLine()
	{
		super();
		readyToFire = false;
		myPiece = new VBTetrisPowerUpCompletePiece();
		myPiece.setsShape();//This is just abstracted to a private method that is not random
	}
	
	@Override
	public void commitAction(VBTetrisGameBoard gameToPowUp, VBTetrisPlayer playerWithPow)
	{
		readyToFire = true;
		_board = gameToPowUp;
		_player = playerWithPow;
		myPiece.setOwner(_player.getcurPiece().getOwner());
		_player.setcurPiece(myPiece);
	}
	
	@Override
	public boolean secondCommit(VBTetrisPlayer currentPlayer)
	{
		if (readyToFire && _player.equals(currentPlayer)) {
			int numToAddRight = _board.rowEmptyToRight(_player.getxPos(), _player.getyPos(), _player);
			myPiece.expandLeft(numToAddRight);
			_player.setcurPiece(myPiece);
			_board.repaint();
			readyToFire = false;
			return true;
		}
		return false;
	}
}