package vbtetris;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * // CSCI331 RL SUPERCLASS
 * 
 * @author Robert Lamb, Team Virtual Boy, CSCI 331, Fall 2013
 * 
 * VBTetrisLevel is a super class to the levels of VBTetris. All
 * methods must be implemented by a subclass so it may be seen as
 * an interface class; however we need there to be a single type
 * of object to re-use, leading to a subclass. 
 * 
 *   Subclasses of this can be used whenever a level is expected,
 *   therefore, they add polymorphic capabilities to the subclasses.
 *   
 *   Siblings to the subclasses (the children of this class) are
 *   all going to be different levels, with different colour, sound, 
 *   and image schemes.  They may even contain other information not
 *   present anywhere else like power-ups, timer times, etc.
 * A VBTetrisLevel contains information on a level of VBTetris play
 * 	this includes the sounds to be played, the images to be used,
 * 	the colours to be used, and other level related features.
 * 
 *
 */
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
	  * @param lines>0 indicates the number of lines scored
	  * @return an audio clip of the sound to be played
	  */
	 public abstract AudioClip getLineSound(int lines);
	 public abstract AudioClip getLineSoundRandom();
	 public abstract void playkillLineSound();
	 public abstract void playLineSound(int numLinesScored);
}
