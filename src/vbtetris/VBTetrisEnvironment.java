package vbtetris;


import java.awt.Color;
import java.awt.image.BufferedImage;

public class VBTetrisEnvironment {

	private static int levels = 2;
	private int curLevel;
	private VBTetrisLevel _level;
	
	VBTetrisEnvironment(){}

	public void init() {  // contains audio for both first and second background screen
		curLevel = 1;
		_level = levelBuilder(curLevel);
	}
    public BufferedImage getLevelImage(int i){
    	return _level.getLevelImage();
    }
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
}

	