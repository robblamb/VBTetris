package vbtetris;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.image.BufferedImage;



import vbtetris.VBColours;	

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
/**
 * 
 * @author Robert Lamb, Team Virtual Boy, CSCI 331, Fall 2013
 *
 * 
 * 
 */
// CSCI331 RL JUNIT


public class VBTetrisEnvironmentTest {
	private int linesToTry = 5;
	
	@Test
	public void newTest(){
		VBTetrisEnvironment env = new VBTetrisEnvironment();
		assertFalse("Did not create a new environment", env.equals(null));
	}
	@Test
	public void equalsTest(){
		VBTetrisEnvironment e = new VBTetrisEnvironment();
		VBTetrisEnvironment other = new VBTetrisEnvironment();
		
		// test un-initialized 
		assertFalse(e.equals(null));
		assertTrue(e.equals(e));
		assertFalse(other.equals(null));
		assertTrue(e.equals(other));
		assertTrue(e.toString().equals(other.toString()));
		// test initialized
		e.init();
		assertFalse(e.equals(other));
		assertFalse(e.toString().equals(other.toString()));
		other.init();
		assertTrue(e.equals(other));
		assertTrue(e.toString().equals(other.toString()));
		// test level up
		e.levelUp();
		assertFalse(e.equals(other));
		assertFalse(e.toString().equals(other.toString()));
		other.levelUp();
		assertTrue(e.equals(other));
		assertTrue(e.toString().equals(other.toString()));
	}
	@Test
	public void soundMethodTest(){
		VBTetrisEnvironment e = new VBTetrisEnvironment();
		e.init();
		e.playMusic();
		e.stopMusic();
		e.playkillLineSound();
		for (int i = 1; i < linesToTry; ++i)
			e.playLineSound(i);
		e.mute();
		e.unmute();
	}
	@Test
	public void levelTest(){
		VBTetrisEnvironment e = new VBTetrisEnvironment();
		e.init();
		int curLevel = e.getCurrentLevel();
		assertTrue(curLevel >0);
		for (int i = curLevel; i < e.getMaxLevel(); ++i){
			e.levelUp();
			assertFalse(curLevel==e.getCurrentLevel());
			curLevel = e.getCurrentLevel();
		}
		
	}
	
	@Test
	public void imageMethodTest(){
		VBTetrisEnvironment e = new VBTetrisEnvironment();
		e.init();
		BufferedImage b = e.getLevelImage();
		assertFalse(b.equals(null));
	}

	@Test
	public void colourTest(){
		VBTetrisEnvironment e = new VBTetrisEnvironment();
		e.init();
		for (int i = 0; i < 10; ++i){
			Color c = e.getPieceColor(i);
			assertFalse(c.equals(null));
		}
	}
	

}
