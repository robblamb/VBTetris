package vbtetris;


import java.awt.Color;
import java.awt.image.BufferedImage;

public class VBTetrisEnvironment {

	private static final int numLevels = 2;

	private int curLevel=0;
	private VBTetrisLevel _level;

	
	VBTetrisEnvironment(){
		curLevel = 1;
	}
    // initialize the level 
	public void init() { setLevel();}

	public void playkillLineSound(){
		_level.playkillLineSound();
	}
	public void playLineSound(int lines){
		_level.playLineSound(lines);
	}
	// returns the current level, base one counting
	public int getCurrentLevel(){return curLevel;};

	// sets level to a new level using curLevel as the level number
	private void setLevel(){
		_level = levelBuilder(curLevel);
	}
	// increases current level by one and initializes
	public void levelUp(){
		if (curLevel+1<=numLevels) {
			++curLevel;
			setLevel();
		}
	}
	// returns the background image for current level
	// DEPRECIATED: always uses the same method without input parameters
    public BufferedImage getLevelImage(int i){
    	return _level.getLevelImage();
    }
    // returns the background image for current level
	public BufferedImage getLevelImage(){ return _level.getLevelImage();}
	
	
    public Color getPieceColor(int i){
       return _level.getPieceColour(i);
    }
	  
	private VBTetrisLevel levelBuilder(int newLevel) {
		switch(newLevel){
			case (1): return new VBTetrisLevel_1();
			case (2): return new VBTetrisLevel_2();
			default: return null;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 4973;
		int result = 6067;
		result = prime * result + ((_level == null) ? 0 : _level.hashCode());
		result = prime * result + curLevel;
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
	@Override
	public String toString() {
		return "VBTetrisEnvironment [_level=" + _level + "]" + " CurrentLevel= " + curLevel;
	}

	
}

	