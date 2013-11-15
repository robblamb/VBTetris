package vbtetris;

// CSCI331 RL JUNIT
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class VBTetrisTestLevel {
	private static VBTetrisLevel level[]= {
											new VBTetrisLevel_1(), 
											new VBTetrisLevel_2()
										 	};
	@Ignore
	@Test
	public void playSound(){
		for (int i = 0; i< level.length ; ++i){
			level[i].playLineSound(1);
		}
	}
	
	@Test
	public void playsinglesound(){
		level[0].playLineSound(0);
	}
	@Test
	public void testequals(){
		assertTrue("Array not equal to array",level.equals(level));
		assertFalse(level[1].equals(null));
	}
	@Test
	public void testequals2(){
		assertTrue("LEvel not equal to self",level[1].equals(level[1]));
	}
	@Test
	public void testequals3(){
		assertFalse(level[0].equals(null));
	}
	@Test
	public void testequals4(){
		assertTrue(level[0].equals(level[0]));
	}
	@Test
	public void testequals5(){
		assertFalse(level[1].equals(level[0]));
	}
}
