package vbtetris;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.image.BufferedImage;

public abstract class VBTetrisLevel {
	 public abstract Color getPieceColour(int playerNum);
	 public abstract BufferedImage getLevelImage();
	 public abstract AudioClip getLineSound(int lines);
	 public abstract AudioClip getLineSoundRandom();
}
