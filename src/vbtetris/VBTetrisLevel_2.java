package vbtetris;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class VBTetrisLevel_2 extends VBTetrisLevel {
	 
	 private static final int numLineZap = 5;
	 private AudioClip lineZap[];
	 String myBackground; 
	 
	 public VBTetrisLevel_2() {
		 super();
		 for (int i = 1; i < numLineZap; ++i) {
		  //  this.lineZap[i] = Applet.newAudioClip( getClass().getResource("../VBTetrisSound/SHODAN" + i + ".WAV"));
		 }
		 myBackground = "../VBTetrisImage/SHODAN-BG.png";
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
	public AudioClip getLineSound(int lines) {
		if (lines>=1 && lines<=numLineZap){return this.lineZap[lines];}
		else return null;
	}

	@Override
	public AudioClip getLineSoundRandom() {
		return lineZap[(int) (1 + (int)(Math.random() * numLineZap))];
	}
}
