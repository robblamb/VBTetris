package vbtetris;

public abstract class VBTetrisPowerUp {
	private int xPosition, yPosition;
	private VBTetrisGameBoard _board;
	
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
	
	public abstract void commitAction();
}