package vbtetris;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
/**
 * 
 * @author Robert Lamb, Team Virtual Boy, CSCI 331, Fall 2013
 *
 * Tests the Environment class Methods:
 * 
 * 		Constructor
 * 		init()
 * 
 * 
 */
// CSCI331 RL JUNIT
public class VBTetrisEnvironmentTest {

	@Test
	public void newTest(){
		VBTetrisEnvironment env = new VBTetrisEnvironment();
		assertFalse("Did not create a new environment", env.equals(null));
	}
	@Test
	public void equalsTest(){
		
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
