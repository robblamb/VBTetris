package vbtetris;

public class VBTetrisPieceMover {
	private VBTetrisGameBoard _board;
	public VBTetrisPieceMover(VBTetrisGameBoard board) {
		this._board = board;
	}
	public synchronized void moveLeft(VBTetrisPlayer _player){
		if(_board.isGameOver()||_board.isGamePaused()){
			return;
		}
		_board.tryMove(_player,_player.getcurPiece(), _player.getxPos() - 1,_player.getyPos());
	}
	public synchronized void moveRight(VBTetrisPlayer _player){
		if(_board.isGameOver()||_board.isGamePaused()){
			return;
		}
		_board.tryMove(_player,_player.getcurPiece(), _player.getxPos() + 1,_player.getyPos());
	}
	public synchronized void moveDown(VBTetrisPlayer _player){
		if(_board.isGameOver()||_board.isGamePaused()){
			return;
		}
		_board.dropOneDown(_player);
	}
	public synchronized void rotate(VBTetrisPlayer _player){
		if(_board.isGameOver()||_board.isGamePaused()){
			return;
		}
		_board.tryMove(_player,_player.getcurPiece().rotateLeft(), _player.getxPos(),_player.getyPos());
	}
}
