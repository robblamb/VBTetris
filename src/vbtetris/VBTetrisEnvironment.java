package vbtetris;


import java.awt.Color;
import java.awt.image.BufferedImage;
/**
 * 
 * @author Robert Lamb, Team Virtual Boy, CSCI 331 Fall 13
 * @author // CSCI331 RL
 * 
 * @see Glossary for VBTetris for any term clarifications.
 *
 */
public class VBTetrisEnvironment {

	private static final int numLevels = 2;

	private int curLevel=0;
	private VBTetrisLevel _level;

	/**
	 * @param none
	 * @return none
	 * 
	 * a new instance must have init() run before use
	 */
	VBTetrisEnvironment(){
		curLevel = 1; //sets current level
	}
	
	/**
	 * @param none
	 * @return none
	 * 
	 * prepares an instance for use
	 * 
	 */
	public void init() { curLevel=1; setLevel();}

	/**
	 * @param none
	 * @return none
	 * 
	 * plays the sound, if any, for the kill line
	 */
	public void playkillLineSound(){
		_level.playkillLineSound();
	}
	/**
	 * `
	 * @param lines is which sound to play, lines > 0 or no sound
	 * 
	 */
	public void playLineSound(int lines){
		_level.playLineSound(lines);
	}
	/**
	 * 
	 * @return current level base 1 counting
	 */
	public int getCurrentLevel(){return curLevel;};
	/**
	 * Creates and stores a new level using curLevel
	 * @param curLevel does not change
	 * @param _level is rewritten
	 */
	private void setLevel(){
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
    public Color getPieceColor(int i){
       return _level.getPieceColour(i);
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

	