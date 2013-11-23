package vbtetris;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.sound.sampled.Clip;

/**
 * // CSCI331 RL SUPERCLASS
 * 
 * @author Robert Lamb, Team Virtual Boy, CSCI 331, Fall 2013
 * 
 *   VBTetrisLevel is a super class to the levels of VBTetris. All
 *   methods must be implemented by a subclass so it may be seen as
 *   an interface class; however we need there to be a single type
 *   of object to re-use, leading to a subclass. 
 *   
 *   The abstraction here is to give a simple and easy way to get the 
 *   sounds, images, and colours from a single source.  This abstract 
 *   class provides the simplicity and ease of further use.
 * 
 *   Subclasses of this can be used whenever a level is expected,
 *   therefore, they add polymorphic capabilities to the subclasses.
 *   
 *   Siblings to the subclasses (the children of this class) are
 *   all going to be different levels, with different colour, sound, 
 *   and image schemes.  They may even contain other information not
 *   present anywhere else like power-ups, timer times, etc.
 *   
 *  A VBTetrisLevel contains information on a level of VBTetris play
 * 	this includes the sounds to be played, the images to be used,
 * 	the colours to be used, and other level related features.
 * 
 *
 */
public abstract class VBTetrisLevel {

	 protected String myBackground; 
	 protected Clip clip;
	 protected Clip backClip;
	 
	/**
	 * Get Piece Colour returns the colour of the player given by playerNum
	 * @param playerNum
	 * @return Colour(Color) of players piece given by playerNum for this level
	 */
	 public abstract Color getPieceColour(int playerNum);
	 public abstract Color getPieceColour(VBColours colour);
	 /**
	  * 
	  * @return A buffered Image of the background of the level
	  */
	 public abstract BufferedImage getLevelImage();
	/**
	 * Plays the warning sound of kill line hit
	 */
	 public abstract void playkillLineSound();
	 /**
	  *  
	  * @param numLinesScored designates sound to play: 0 kill 2 good 3 great 4 amazing. All others default sound 
	  */
	 public abstract void playLineSound(int numLinesScored);
	 /**
	  * 
	  */
	 public abstract void startBackgroundSound();
	 public abstract void stopBackgroundSound();
}