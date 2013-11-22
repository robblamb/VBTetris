package vbtetris;

public abstract class VBTetrisPowerUp {
	protected boolean readyToFire;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_board == null) ? 0 : _board.hashCode());
		result = prime * result + ((_player == null) ? 0 : _player.hashCode());
		result = prime * result + xPosition;
		result = prime * result + yPosition;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof VBTetrisPowerUp)) {
			return false;
		}
		VBTetrisPowerUp other = (VBTetrisPowerUp) obj;
		if (_board == null) {
			if (other._board != null) {
				return false;
			}
		} else if (!_board.equals(other._board)) {
			return false;
		}
		if (_player == null) {
			if (other._player != null) {
				return false;
			}
		} else if (!_player.equals(other._player)) {
			return false;
		}
		if (xPosition != other.xPosition) {
			return false;
		}
		if (yPosition != other.yPosition) {
			return false;
		}
		return true;
	}

	protected int xPosition, yPosition;
	protected VBTetrisGameBoard _board;
	protected VBTetrisPlayer _player;
	protected VBTetrisTimer _time;
	protected boolean activity;
	protected VBColours colour;
	protected String myName;
	
	public VBTetrisPowerUp()
	{
		_board = null;
		_player = null;
		_time = null;
		xPosition = yPosition = 0;
		readyToFire = activity = false;
		myName = "";
	}
	public VBColours getColour(){return colour;}
	public void setXPosition(int xPos)
	{
		xPosition = xPos;
	}
	
	public void setYPosition(int yPos)
	{
		yPosition = yPos;
	}
	
	public int getXPosition()
	{
		return xPosition;
	}
	
	public int getYPosition()
	{
		return yPosition;
	}
	
	public boolean didICollide(int xPos, int yPos) 
	{
		if (xPos == xPosition) {
			if (yPos == yPosition) {
				return true;
			}
		}
		return false;
	}
	
	public boolean amIActive()
	{
		return activity;
	}
	
	public abstract boolean commitAction(VBTetrisGameBoard gameToPowUp, VBTetrisPlayer playerWithPow, VBTetrisTimer boardTime);
	
	public abstract boolean secondCommit(VBTetrisPlayer currentPlayer);
	
	public String getName(VBTetrisPlayer currentPlayer)
	{
		if (_player == null || _player.equals(currentPlayer)) {
			return myName;
		} else {
			return "";
		}
	}
}