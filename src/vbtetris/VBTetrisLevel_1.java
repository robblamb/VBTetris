package vbtetris;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class VBTetrisLevel_1 extends VBTetrisLevel{
	 
	 private static final int numLineZap = 5;
	 private AudioClip lineZap[];
	 String myBackground; 
	 
	 public VBTetrisLevel_1() {
		 super();
		 for (int i = 1; i <= numLineZap; ++i) {
		  //lineZap[i] = Applet.newAudioClip( getClass().getResource("../VBTetrisSound/EXPPOL01.WAV"));
		 }
		 myBackground = "../VBTetrisImage/world2-bg.png";
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
