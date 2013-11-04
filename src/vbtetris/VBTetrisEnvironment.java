package vbtetris;


import java.awt.*;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class VBTetrisEnvironment {

	private     AudioClip lineZap1;
	private     AudioClip lineZap2;
	private     AudioClip lineZap3;
	private     AudioClip lineZap4;
	private     AudioClip lineZap5;

	private     AudioClip lineZap1A;
	private     AudioClip lineZap2A;
	private     AudioClip lineZap3A;
	private     AudioClip lineZap4A;
	private     AudioClip lineZap5A;
	private static int levels = 2;
	//private Image myLevelImages[];
	private String myLevelImages[];
	
	VBTetrisEnvironment(){
		
	}
	
	public synchronized AudioClip getLineZap(int i){  // LineZap is for first background environment
		
        if (i == 1) return lineZap1;
        else if (i == 2) return lineZap2;
	    else if (i == 3) return lineZap3;
	    else if (i == 4) return lineZap4;
        else return lineZap5;
	}

	public AudioClip getLineZapA(int i){   // LineZapA is for hypothetic second background screen
		
        if (i == 1) return lineZap1A;
	    else if (i == 2) return lineZap2A;
	    else if (i == 3) return lineZap3A;
	    else if (i == 4) return lineZap4A;
	    else return lineZap5A;
	}

	public void init() {  // contains audio for both first and second background screen
		
	    lineZap1 = Applet.newAudioClip(getClass().getResource("../VBTetrisSound/EXPPOL01.WAV")); // Directory needs to be changed to proper directory
	    lineZap2 = Applet.newAudioClip(getClass().getResource("../VBTetrisSound/EXPPOL02.WAV")); // Directory needs to be changed to proper directory
	    lineZap3 = Applet.newAudioClip(getClass().getResource("../VBTetrisSound/EXPPOL03.WAV")); // Directory needs to be changed to proper directory
	    lineZap4 = Applet.newAudioClip(getClass().getResource("../VBTetrisSound/EXPPOL04.WAV")); // Directory needs to be changed to proper directory
        lineZap5 = Applet.newAudioClip(getClass().getResource("../VBTetrisSound/EXPPOL05.WAV")); // Directory needs to be changed to proper directory
 
        lineZap1A = Applet.newAudioClip(getClass().getResource("../VBTetrisSound/SHODAN1.WAV")); // Directory needs to be changed to proper directory
	    lineZap2A = Applet.newAudioClip(getClass().getResource("../VBTetrisSound/SHODAN2.WAV")); // Directory needs to be changed to proper directory
	    lineZap3A = Applet.newAudioClip(getClass().getResource("../VBTetrisSound/SHODAN3.WAV")); // Directory needs to be changed to proper directory
	    lineZap4A = Applet.newAudioClip(getClass().getResource("../VBTetrisSound/SHODAN4.WAV")); // Directory needs to be changed to proper directory
	    lineZap5A = Applet.newAudioClip(getClass().getResource("../VBTetrisSound/SHODAN5.WAV")); // Directory needs to be changed to proper directory
		
	    myLevelImages = new String[levels];
        myLevelImages[0] = "../VBTetrisImage/world2-bg.png";
        }
		
        public BufferedImage getLevelImage(int i){
        	
    		//String imgPath = "../VBTetrisImage/world2-bg.png";
    		BufferedImage buffImage = null;
    		try {
    			buffImage = ImageIO.read(getClass().getResourceAsStream(myLevelImages[i]));
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        	
    		return buffImage;
    		
        }
	
        public Color getPieceColor(int i){
    	    return PieceColors[i];
        }
	   
        private static Color PieceColors[] = {  // both backgrounds will use the same piece colours, not sure if purple exists
	     
         Color.black,		// just so we can count from 1
         Color.red,
	     Color.cyan,
	     Color.orange,
	     Color.blue

        };	
	
};

	