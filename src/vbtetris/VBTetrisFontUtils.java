package vbtetris;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;

public class VBTetrisFontUtils {

	// fix font size across platforms
	public static int adjustFontSize(int fontSize) {
		int screenRes = Toolkit.getDefaultToolkit().getScreenResolution();
    	return (int)Math.round(fontSize / (screenRes / 72.0));
	}
	
	// http://www.java2s.com/Tutorial/Java/0261__2D-Graphics/Centertext.htm
	public static void drawCenteredString(String s, int xPos, int yPos, int w, int h, Graphics g) {
		FontMetrics fm = g.getFontMetrics();
		int x = xPos + (w - fm.stringWidth(s)) / 2;
		int y = yPos + (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2);
		g.drawString(s, x, y);
	}
	
}
