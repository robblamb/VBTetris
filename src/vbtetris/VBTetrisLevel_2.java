package vbtetris;

import java.applet.Applet;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
/**
 * 
 * @author Robert Lamb, Team Virtual Boy, CSCI 331, Fall 2013
 * 
 *  // CSCI331 RL SUBCLASS
 *  This is a subclass of VBTetrisLevel, it implements all the functionality
 *  This can be treated like any other Level
 *  
 *  This level has the shodan picture from some game
 *  It uses star trek sounds taken from the internet 
 *  The player colours are standard Color.color from java
 *  
 *  
 *
 */
public class VBTetrisLevel_2 extends VBTetrisLevel {
	 
	VBTetrisLevel_2() {
		 super();
		 myBackground = "../VBTetrisImage/SHODAN-BG.png";
	}
	 private void setClip(URL url, Clip c){
		 try {
		        // Set up an audio input stream piped from the sound file.
		        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
		        // Get a clip resource.
		        c = AudioSystem.getClip();
		        // Open audio clip and load samples from the audio input stream.
		        c.open(audioInputStream);
		    } catch (UnsupportedAudioFileException e) {
		        e.printStackTrace();
		    } catch (IOException e) {
		        e.printStackTrace();
		    } catch (LineUnavailableException e) {
		        e.printStackTrace();
		    }
	 }
	@Override
	public void startBackgroundSound() {
		URL url = getClass().getResource("../VBTetrisSound/100_Phonography_Am04.wav");
		setClip(url,backClip);
		if(!backClip.isRunning()) {
			backClip.setFramePosition(0);
			backClip.start();
		}
	}
	@Override
	public void stopBackgroundSound() {
		if(backClip.equals(null)) return;
		backClip.stop();
			
	}
	@Override
	public void playLineSound(int numLinesScored) {
		// Code here borrowed from a comment on stackoverflow.com
		URL url = getClass().getResource("../VBTetrisSound/zap"+numLinesScored+".wav");
  	    setClip(url,clip);//this method will load the sound
	    if (clip.isRunning())
		 	 clip.stop();   // Stop the player if it is still running
	   	clip.setFramePosition(0); // rewind to the beginning
	    clip.start();     // Start playing

	}
	@Override
	public void playkillLineSound() {
		playLineSound(0);
		
	}	
	 
	@Override
	public Color getPieceColour(int playerNum) {
		switch(playerNum) {
			case(0): return Color.black;		// just so we can count from 1
			case(1): return Color.red;
			case(2): return Color.cyan;
			case(3): return Color.orange;
			case(4): return Color.blue;
			default: return null;
		}
	}
	
	private int p1red = 30, p1grn = 30, p1blu=30;
	private int p2red = 30, p2grn = 30, p2blu=30;
	private int p3red = 30, p3grn = 30, p3blu=30;
	private int p4red = 30, p4grn = 30, p4blu=30;

	@Override
	public Color getPieceColour(VBColours colour) {
		switch(colour){
		
		case PLAYER1: return Color.red;
		case PLAYER2: return Color.cyan;
		case PLAYER3: return Color.orange;
		case PLAYER4: return Color.blue;
		case EMPTY: return Color.black;
		case POWERUP1: 
			p1red = (p1red + 50)% 255;
			return new Color(p1red,p1grn,p1blu);
		case POWERUP2:
			p2blu = (p2blu + 30)% 255;
			return new Color(p2red,p2grn,p2blu);
		default: return Color.white;
		}
	}
	@Override
	public BufferedImage getLevelImage() {
		BufferedImage buffImage = null;
		try {
			buffImage = ImageIO.read(getClass().getResourceAsStream(myBackground));
		} catch (IOException e) {
			// do nothing
		}
  	
		return buffImage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((myBackground == null) ? 0 : 57);
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
		if (!(obj instanceof VBTetrisLevel_2)) {
			return false;
		}
		VBTetrisLevel_2 other = (VBTetrisLevel_2) obj;
		if (myBackground == null) {
			if (other.myBackground != null) {
				return false;
			}
		} else if (!myBackground.equals(other.myBackground)) {
			return false;
		}
		return true;
	}


}
