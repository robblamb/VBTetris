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

public class VBTetrisLevel_1 extends VBTetrisLevel{
	 
	 protected static final int numLineZap = 5;
	 protected AudioClip lineZap[];
	 private String myBackground; 
	 protected Clip clip;
	 
	 
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
		return "VBTetrisLevel_1 [lineZap=" + Arrays.toString(lineZap)
				+ ", myBackground=" + myBackground + ", hashCode()="
				+ hashCode() + "]";
	}

	@Override
	public AudioClip getLineSound(int lines) {
		if (lines>=1 && lines<=numLineZap){return this.lineZap[lines];}
		else return null;
	}

	@Override
	public AudioClip getLineSoundRandom() {
		return lineZap[(int) (1 + (int)(Math.random() * numLineZap))];
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



}
