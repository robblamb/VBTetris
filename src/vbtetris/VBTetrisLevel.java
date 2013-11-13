package vbtetris;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.image.BufferedImage;

public abstract class VBTetrisLevel {
	/**
	 * Get Piece Colour returns the colour of the player given by playerNum
	 * @param playerNum
	 * @return Colour(Color) of players piece given by playerNum for this level
	 */
	 public abstract Color getPieceColour(int playerNum);
	 /**
	  * 
	  * @return A buffered Image of the background of the level
	  */
	 public abstract BufferedImage getLevelImage();
	 /**
	  * 
	  * @param lines indicates the number of lines scored
	  * @param no parameters gives a random line score sound
	  * @return an audio clip of the sound to be played
	  */
	 public abstract AudioClip getLineSound(int lines);
	 public abstract AudioClip getLineSoundRandom();
	 public abstract void playkillLineSound();
	 public abstract void playLineSound(int numLinesScored);
}
