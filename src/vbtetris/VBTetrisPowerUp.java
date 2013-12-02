package vbtetris;

//CSCI331 MC SUPERCLASS

/**
 * I abstracted out several elements, both variables and methods,
 * to the super class.  Firstly, I abstracted the methods
 * amIActive, the setters and getters for the x and y positions, didICollide,
 * and getColor.  I did this because these methods were required by every 
 * subclass, made heavy use of variables that each subclass object would inherit from 
 * this class (i.e. the parent class), and required the exact same functionality.
 * Thus, in the interest of code sharing and to ensure that the exact same 
 * functionality was given to each subclass object it made sense to abstract these
 * methods.  I also abstracted several variables including readyToFire and xPosition
 * amongst others.  I did this because every subclass object makes use of these
 * variables either as part of the abstracted methods mentioned above or for said
 * subclass object's key functionality.  Thus, in order to ensure that every new 
 * subclass has the necessary variables it follows that these variables had to be included
 * in this class (the parent class).
 * 
 * The subclasses of this class (VBTetrisPowerUp) add the main functionality of 
 * each power up.  So, for example, the child of this class VBTetrisPowerUpCompleteLine gives
 * access to the CompleteLine power up's abilities (i.e. this gives access to the 
 * Complete piece which gives the user a single square piece that extends up to 8 squares to the 
 * right of the initial piece depending on if other pieces already exist there). 
 * 
 * In addition to the VBTetrisPowerUpFireFly subclass, VBTetris also has the children 
 * VBTetrisPowerUpCompleteLine, VBTetrisPowerUpSpeedUp, and VBTetrisPowerUpStopEveryoneElse.
 * These subclasses could also gain additional siblings in the form of additional
 * power ups to be used in the game.  So, for example, a power up that causes the user to
 * instantly gain 2x points for every row completed for a certain amount of time could be added.
 * 
 * @author MatthewCormons
 *
 */

public abstract class VBTetrisPowerUp {
	protected boolean readyToFire; //This variable is used to determine whether the 
									//power up is ready to be used yet by the subclasses.
	
	//It is recommended that hash code be overridden, thus hash code has been overridden.
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

	//It is recommended that equals be overridden, thus equals has been overridden.
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

	//Proected variables neeeded by the subclasses.
	protected int xPosition, yPosition; //x and y position of the power up
	protected VBTetrisGameBoard _board; //a reference to the tetris game board
	protected VBTetrisPlayer _player; //a reference to the player with the power up
	protected VBTetrisTimer _time; //a reference to the tetris game board's timer
	protected boolean activity; //a variable used to indicate when a power up is active
	protected VBColours colour; //the color of the power up
	protected String myName; //the name of the power up
	
	public VBTetrisPowerUp()
	{
		//Simply initializes everything to its base state
		_board = null;
		_player = null;
		_time = null;
		xPosition = yPosition = 0;
		readyToFire = activity = false;
		myName = "";
	}
	public VBColours getColour(){return colour;}//returns the color of the power up
	//CSCSI331 MC ENCAPSULATION
	/**
	 * Consider the variable xPosition and its getter and setter setXPosition and 
	 * getXPosition, respectively.  It is bad for variables to be declared public
	 * because if said variables are public said variables can be accessed anywhere
	 * in the program.  Thus, parts of the program that have nothing to do with 
	 * the operation of this code could change that value of this variable.  Thus,
	 * the code could malfunction (i.e. crash) and debugging would be much more
	 * difficult.  Specifically, for this program it is important that only the
	 * block of code with access to the reference to this object, which in this case
	 * wouldn't be this object at all but an instantiation of one of the subclasses
	 * as this class is abstract, since said block of code is keeping track of
	 * where everything is on the game board.  Thus, if x changes without the 
	 * knowledge of the aforesaid block of code the power up will be lost on 
	 * the board and will be activated when the board thinks that it is somewhere 
	 * else and is printing out an image on the computer's screen to that effect.
	 * Thus, the code would horribly malfunction if strict adherence to access of
	 * xPosition was not enforced.
	 * 
	 * @author Matthew Cormons
	 */
	
	public void setXPosition(int xPos)
	{
		xPosition = xPos; //setter for xPosition
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