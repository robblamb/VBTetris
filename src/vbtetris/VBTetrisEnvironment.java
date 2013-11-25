package vbtetris;

// CSCI331 RL INTERFACE
/**
 * The environment class creates an interface for the other modules to use. It provides
 * the sounds, colours, and images that all pieces, players, and boards need to use.  I have
 * provided several different methods, some of which do the same things with different inputs,
 * to facilitate this need.  Since I also added some javadoc style of comments, when using
 * an instance of environment, the methods parameters and returns are shown.  The names that
 * I have given to the parameters also helps with understanding the results without having to
 * know what the inner workings are.  
 * For example, a user knows that invoking the getLevelImage method returns the image for the current
 * level, but, they would never know that the environment actually passes the message along to a private
 * variable instance, and returns the results.
 * 
 */

import java.awt.Color;
import java.awt.image.BufferedImage;
/**
 * 
 * @author Robert Lamb, Team Virtual Boy, CSCI 331 Fall 13
 * @author // CSCI331 RL PATTERN CREATOR
 * 
 * Pattern:
 * Name: Creator
 * Problem: which class should have the responsibility to create levels
 * Solution: The class with knowledge of levels, uses levels, and aggregates
 * 	levels should create them. Since the environment has expert knowledge of
 * 	levels, it is an excellent candidate.  Since using the environment interface
 *  to interact with levels reduces coupling to the other modules, it suggests using
 *  the environment as the creator.  Most of all, the environment has as a private
 *  variable (aggregates) a level, and it is the only class to do so, the choice
 *  for the environment to be the creator is best option.
 *  
 * This pattern fits perfectly with this part of my code.  It reduces coupling, 
 * retains high cohesion, and gives the responsibilities to a logical recipient.
 *   * 
 * @see Glossary for VBTetris for any term clarifications.
 *
 */
enum VBColours { 
	PLAYER1, PLAYER2, PLAYER3, PLAYER4, EMPTY, POWERUP1, POWERUP2, POWERUP3, POWERUP4
}

public class VBTetrisEnvironment {

	private static final int numLevels = 2;


	
	// CSCI331 RL ENCAPSULATION
	/**
	 * here curLevel indicates the current level the environment should be using.
	 * This number is used to generate levels, and although the generation method
	 * watches for non-valid numbers, the value should always be >=1 and never
	 * higher than numLevels. By keeping this variable private, I can control the
	 * values that it can get assigned; furthermore, this particular variable has 
	 * no public setter method.  For an outside object/program to change the value
	 * it must used the levelUp() method, furthering encapsulation.  The value of
	 * curLevel, however, can be public knowledge, thus a getter is there.
	 * 
	 * In general, any instance variable that a class uses should be private. That way 
	 * the only changes can be made through getter and setter methods.  Any possible illegal
	 * values will be impossible (as long as the getters and setters do proper checking).
	 * 
	 */
	private int curLevel=0;
	private VBTetrisLevel _level;
	private boolean MUTE;
	
	/**
	 * @param none
	 * @return a new instance of VBTetrisEnvironment that needs to have init() run before use
	 * 
	 * a new instance must have init() run before use
	 */
	VBTetrisEnvironment(){
		curLevel = 1; //sets current level
		MUTE = false;
	}
	
	/**
	 * @param none
	 * @return none
	 * 
	 * prepares an instance for use and starts music
	 * 
	 */
	public void init() { 
		curLevel=1; 
		setLevel();
		playMusic();
	}
	/**
	 * if the environment is not muted the current levels background
	 * music will play
	 */
	public void playMusic(){if(!MUTE)_level.startBackgroundSound();}
	/**
	 * stops the current levels background music
	 */
	public void stopMusic(){_level.stopBackgroundSound();}
	/**
	 *  stops all sounds and prevents further sounds
	 */
	public void mute(){ 
		if (!MUTE) {
			MUTE=true; 
			stopMusic();
		}
	}
	/**
	 * starts background music and allows sounds
	 */
	public void unmute(){
		if(MUTE){
			MUTE=false; 
			playMusic();
		}
	}
	/**
	 * 
	 * @return true if the environment is muted
	 */
	public boolean isMute(){return MUTE;}
	/**
	 * @param none
	 * @return none
	 * 
	 * plays the sound, if any, for the kill line
	 */
	public void playkillLineSound(){
		if(!MUTE)_level.playkillLineSound();
	}
	/**
	 * `
	 * @param lines is which sound to play, lines > 0 or no sound
	 * 
	 */
	public void playLineSound(int lines){
		if(!MUTE)_level.playLineSound(lines);
	}
	/**
	 * 
	 * @return current level base 1 counting, always >=1
	 *  
	 */
	public int getCurrentLevel(){return curLevel;};
	/**
	 * Creates and stores a new level using curLevel
	 * @param curLevel does not change
	 * @param _level is rewritten
	 */
	private void setLevel(){
		// CSCI331 RL DYNAMICBINDING
		/**
		 *  The current level for the environment is some level instance of VBTetrisLevel
		 *  It is unknown until the method levelBuilder returns the level, which level will
		 *  be created. Thus _level is statically bound to the class VBTetrisLevel but it 
		 *  will also be dynamically cast to some subclass of VBTetrisLevel (since abstract)
		 *  
		 *  This also means that any time the private variable _level is used, it is being
		 *  used in a dynamic/ polymorhpic way.  The system will have to wait until a 
		 *  method is invoked, then goto that subclasses method to find the proper block of
		 *  code to run.
		 */
		_level = levelBuilder(curLevel);
	}
	/**
	 *  Increases the level by one
	 */
	public void levelUp(){
		if (curLevel+1<=numLevels) {
			++curLevel;
			setLevel();
		}
	}
	// CSCI331 RL STATICBINDING
	/**
	 * The method getMaxLevel is a static method that returns
	 * a static integer.  Since the integer is static, it is a class
	 * variable and exists at all times.  This means at compile time the
	 * value is known, and is available (through the getter). The system
	 * can do this because of the keyword static.
	 */
	/**
	 * 
	 * @return the number of levels (base 1 counting)
	 */
	public static int getMaxLevel() {return numLevels;}
	/**
	 * 
	 * @param i is the level number
	 * @return the background as BufferedImage
	 * 
	 * @deprecated always uses getLevelImage()
	 */
	// returns the background image for current level
	// DEPRECIATED: always uses the same method without input parameters
    public BufferedImage getLevelImage(int i){
    	return _level.getLevelImage();
    }
    /**
     * 
     * @return the background image for current level
     */
    // returns the background image for current level
	public BufferedImage getLevelImage(){ return _level.getLevelImage();}
	
	/**
	 * 
	 * @param i is the colour (or player) number for the current level
	 * @return a Color for the current level
	 * 
	 */
	public Color getPieceColour(VBColours clr){
		return _level.getPieceColour(clr);
	}
	/**
	 * 
	 * @param i indicates with number to use, each colour has a number
	 * @return a Color for the current level and given number
	 */
	private Color getVBColour(int i){
		VBColours clr = VBColours.EMPTY;
		switch(i){
		case 0: clr = VBColours.EMPTY;
		break;
		case 1: clr = VBColours.PLAYER1;
		break;
		case 2: clr = VBColours.PLAYER2;
		break;
		case 3: clr = VBColours.PLAYER3;
		break;
		case 4: clr = VBColours.PLAYER4;
		break;
		case 5: clr = VBColours.POWERUP1;
		break;
		case 6: clr = VBColours.POWERUP2;
		break;
		case 7: clr = VBColours.POWERUP3;
		break;
		case 8: clr = VBColours.POWERUP4;
		break;
		}
		return _level.getPieceColour(clr);
	}
	/**
	 * 
	 * @param numColour indicates the assigned number for the colour for the level
	 * @return a Color for the current level
	 * @future this will be replaced by the method using the Colours enum type
	 * 
	 */
    public Color getPieceColor(int numColour){
       return getVBColour(numColour);
    }
	  /**
	   * 
	   * @param newLevel in base 1 counting
	   * @return a new VBTetrisLevel
	   */
	private VBTetrisLevel levelBuilder(int newLevel) {
		switch(newLevel){
			case (1): return new VBTetrisLevel_1();
			case (2): return new VBTetrisLevel_2();
			default: return null;
		}
	}
	/**
	 *  The recommended override for all objects
	 */
	@Override
	public int hashCode() {
		final int prime = 4973;
		int result = 6067;
		result = prime * result + ((_level == null) ? 0 : _level.hashCode());
		result = prime * result + curLevel;
		return result;
	}
	/**
	 *  The recommended override for all objects
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof VBTetrisEnvironment)) {
			return false;
		}
		VBTetrisEnvironment other = (VBTetrisEnvironment) obj;
		if (_level == null) {
			if (other._level != null) {
				return false;
			}
		} else if (!_level.equals(other._level)) {
			return false;
		}
		if (curLevel != other.curLevel) {
			return false;
		}
		return true;
	}
	/**
	 *  The recommended override for all objects
	 */
	@Override
	public String toString() {
		return "VBTetrisEnvironment [_level=" + _level + "]" + " CurrentLevel= " + curLevel;
	}

	
}

	