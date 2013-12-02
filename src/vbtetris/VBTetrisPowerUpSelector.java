package vbtetris;

import java.util.Random;

/**
 * @author MatthewCormons
 * Last edited: December 2, 2013
 */

//CSCI331 MC PATTERN
/**
 * Name: Polymorphism
 * Problem: Solves the problem of how do you handle the different
 * behaviors of different things when trying to do the 
 * same thing.  
 * Solution: Uses subclasses to divide different behavior into
 * a class that represents one of the different types of things you want 
 * to use and which will try to do the same thing.
 * Why it fits my code: I have many different types of power ups
 * that must interact in the same way with the code that is calling it.
 * Otherwise, one would require an inordinate amount of specialized
 * calls to power up class object in question in order to make said
 * object run correctly.   Thus, it makes sense to simply have one
 * parent class define the behaviors I need and to allow the children to
 * override said behaviors thus allowing me the ability to simply and easily 
 * use many versions of said code required to run the many different power ups
 * I have.  The switch statement below is a good example of what I am talking about.
 * A single type is used to return many different types of power up.
 * 
 * @author MatthewCormons
 *
 */


public class VBTetrisPowerUpSelector {
	private boolean powerUpOnGameBoard;
	private Random random;
	
	public VBTetrisPowerUpSelector() {
		powerUpOnGameBoard = false;//Set false since there is no power up on the board
		int seed = (int)(Math.random()*100000000);//Get seed
		random = new Random(seed);//Seed the random number generator
	}
	
	public VBTetrisPowerUp chooseAPowerUp() {
		if (random.nextInt(100) < 15) {//If random number picked is < 15 pick a new power up
			VBTetrisPowerUp powerToReturn = null;
			int myChoice = random.nextInt(4);//Randomly choose a power up's number
			switch ( myChoice ) {//Return the power up selected
				case 0:
					//CSCI331 MC DYNAMICBINDING
					/**
					 * Dynamic binding is when method overriding is done.  
					 * The system decides which methods and variables to allow
					 * by first looking at the class information (i.e. class methods
					 * and variables) of the statically bound variable.  All of these
					 * methods and variables are now made available.  Then the system
					 * looks and sees if any methods have been overridden.  If there are
					 * this code is what is used for said available method.
					 * 
					 * @author MatthewCormons
					 */
					
					powerToReturn = new VBTetrisPowerUpCompleteLine();
					break;
				case 1:
					powerToReturn = new VBTetrisPowerUpSpeedUp();
					break;
				case 2:
					powerToReturn = new VBTetrisPowerUpFireFly();
					break;
				case 3:
					powerToReturn = new VBTetrisPowerUpStopEveryoneElse();
					break;
			}
			return powerToReturn;
		}
		return null;
	}
	
	public boolean getPowerUpOnGameBoard() 
	{
		return powerUpOnGameBoard;
	}
	
	public void setPowerUpOnGameBoard(boolean valToSet) 
	{
		powerUpOnGameBoard = valToSet;
	}
}