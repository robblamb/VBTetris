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
 *  This level has the planet made out of tetriminoes as the background
 *  It uses star trek sounds taken from the internet 
 *  The player colours are standard Color.color from java
 *  
 *  
 *
 */
public class VBTetrisLevel_1 extends VBTetrisLevel{
	 

	 public VBTetrisLevel_1() {
		 super();
		 myBackground = "../VBTetrisImage/world2-bg.png";
	 }
	 private void setClip(URL url){
		 try {
		        // Set up an audio input stream piped from the sound file.
		        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
		        // Get a clip resource.
		        clip = AudioSystem.getClip();
		        // Open audio clip and load samples from the audio input stream.
		        clip.open(audioInputStream);
		    } catch (UnsupportedAudioFileException e) {
		        e.printStackTrace();
		    } catch (IOException e) {
		        e.printStackTrace();
		    } catch (LineUnavailableException e) {
		        e.printStackTrace();
		    }
	 }
	@Override
	public void playLineSound(int numLinesScored) {
		// Code here borrowed from a comment on stackoverflow.com
	    URL url = getClass().getResource("../VBTetrisSound/zap"+numLinesScored+".wav");//You can change this to whatever other sound you have
  	    setClip(url);//this method will load the sound
	    if (clip.isRunning())
		 	 clip.stop();   // Stop the player if it is still running
	   	clip.setFramePosition(0); // rewind to the beginning
	    clip.start();     // Start playing

	}
	@Override
	public void startBackgroundSound() {
		URL url = getClass().getResource("../VBTetrisSound/100_Phonography_Am04.wav");
		setBackClip(url);
		if(!backClip.isRunning()) {
			backClip.setFramePosition(0);
			backClip.start();
			backClip.loop(backClip.LOOP_CONTINUOUSLY);
		}
	}
	 private void setBackClip(URL url){
		 try {
		        // Set up an audio input stream piped from the sound file.
		        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
		        // Get a clip resource.
		        backClip = AudioSystem.getClip();
		        // Open audio clip and load samples from the audio input stream.
		        backClip.open(audioInputStream);
		    } catch (UnsupportedAudioFileException e) {
		        e.printStackTrace();
		    } catch (IOException e) {
		        e.printStackTrace();
		    } catch (LineUnavailableException e) {
		        e.printStackTrace();
		    }
	 }
	@Override
	public void stopBackgroundSound() {
		if(backClip.equals(null)) return;
		backClip.stop();
		
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
	public String toString() {
		return "VBTetrisLevel_1 [lineZap=" 
				+ ", myBackground=" + myBackground + ", hashCode()="
				+ hashCode() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((myBackground == null) ? 0 : 37);
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
		if (!(obj instanceof VBTetrisLevel_1)) {
			return false;
		}
		VBTetrisLevel_1 other = (VBTetrisLevel_1) obj;
		if (myBackground == null) {
			if (other.myBackground != null) {
				return false;
			}
		} else if (!myBackground.equals(other.myBackground)) {
			return false;
		}
		return true;
	}
	private int p1red = 0, p1grn = 0, p1blu=0;
	private int p2red = 0, p2grn = 0, p2blu=0;
	private int p3red = 0, p3grn = 0, p3blu=0;
	private int p4red = 0, p4grn = 0, p4blu=0;

	@Override
	public Color getPieceColour(VBColours colour) {
		switch(colour){
		case PLAYER1: return Color.red;
		case PLAYER2: return Color.cyan;
		case PLAYER3: return Color.orange;
		case PLAYER4: return Color.blue;
		case EMPTY: return Color.black;
		case POWERUP1: 
			p1red = (p1red + 50)% 256;
			return new Color(p1red,p1grn,p1blu);
		case POWERUP2:
			p2blu = (p2blu + 30)% 256;
			return new Color(p2red,p2grn,p2blu);
		case POWERUP3:
			p3grn = (p3grn + 30)% 256;
			return new Color(p3red,p3grn,p3blu);
		default: return Color.white;
		}
	}


}

