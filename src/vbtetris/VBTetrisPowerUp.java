package vbtetris;

public abstract class VBTetrisPowerUp {
	protected int xPosition, yPosition;
	protected VBTetrisGameBoard _board;
	protected VBTetrisPlayer _player;
	
	public VBTetrisPowerUp()
	{
		_board = null;
	}
	
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
	
	public abstract void commitAction(VBTetrisGameBoard gameToPowUp, VBTetrisPlayer playerWithPow);
}