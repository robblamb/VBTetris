package vbtetris;

public class VBTetrisPieceMover {
	private VBTetrisGameBoard _board;
	public VBTetrisPieceMover(VBTetrisGameBoard board) {
		this._board = board;
	}
	public void moveLeft(VBTetrisPlayer _player){
		if(_board.isGameOver()||_board.isGamePaused()){
			return;
		}
		_board.tryMove(_player,_player.getcurPiece(), _player.getxPos() - 1,_player.getyPos());
	}
	public void moveRight(VBTetrisPlayer _player){
		if(_board.isGameOver()||_board.isGamePaused()){
			return;
		}
		_board.tryMove(_player,_player.getcurPiece(), _player.getxPos() + 1,_player.getyPos());
	}
	public void moveDown(VBTetrisPlayer _player){
		if(_board.isGameOver()||_board.isGamePaused()){
			return;
		}
		_board.dropOneDown(_player);
	}
	public void rotate(VBTetrisPlayer _player){
		if(_board.isGameOver()||_board.isGamePaused()){
			return;
		}
		_board.tryMove(_player,_player.getcurPiece().rotateLeft(), _player.getxPos(),_player.getyPos());
	}
}
/*

	class TAdapter extends KeyAdapter
	{
		public void keyPressed(KeyEvent e)
		{

			if (gameOver) return;

			int keycode = e.getKeyCode();

			if (keycode ==  KeyEvent.VK_SPACE) {
				togglePause();
				return;
			} 
			if (gamePaused) return;

			switch (keycode)
			{
				// player one
				case 'd':
				case 'D':
					// left
					tryMove(players[0],players[0].getcurPiece(), players[0].getxPos() + 1,players[0].getyPos());
					break;
				case 'a':
				case 'A':
					//right
					tryMove(players[0],players[0].getcurPiece(), players[0].getxPos() - 1,players[0].getyPos());
					break;
				case 'w':
				case 'W':
					tryMove(players[0],players[0].getcurPiece().rotateLeft(), players[0].getxPos(),players[0].getyPos());
					break;
				case 's':
				case 'S':
					dropOneDown(players[0]);
					break;
				
				// player two
				case KeyEvent.VK_LEFT:
					tryMove(players[1],players[1].getcurPiece(), players[1].getxPos() - 1,players[1].getyPos());
					break;
				case KeyEvent.VK_RIGHT:
					tryMove(players[1],players[1].getcurPiece(), players[1].getxPos() + 1,players[1].getyPos());
					break;
				case KeyEvent.VK_DOWN:
					dropOneDown(players[1]);
					break;
				case KeyEvent.VK_UP:
					tryMove(players[1],players[1].getcurPiece().rotateLeft(), players[1].getxPos(),players[1].getyPos());
					break;
			}
		}
	}
	// ***************************************************************************************

}

*/