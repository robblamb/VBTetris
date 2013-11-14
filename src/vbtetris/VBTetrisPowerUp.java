package vbtetris;

public class VBTetrisPowerUp {
	private int xPosition, yPosition;
	
	public VBTetrisPowerUp()
	{
		// does nothing for now
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
}