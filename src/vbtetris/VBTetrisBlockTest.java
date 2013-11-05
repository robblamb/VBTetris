package vbtetris;

import static org.junit.Assert.*;
import vbtetris.VBTetrisPieces.Tetrominoes;

import org.junit.Test;

public class VBTetrisBlockTest {

	@Test
	public void testEqual() {
		//Initialize two VBTetrisBlock for use in testing
		VBTetrisBlock tester = null;
		VBTetrisBlock other = null;
		
		//Initialize an array to all 0 for use as 
		//coordinates in tester
		int[] block = new int[2];
		block[0] = 0;
		block[1] = 0;
		
		//Make tester point to a VBTetrisBlock object
		tester = new VBTetrisBlock();
		
		//Set tester's coordinates to the contents of block
		tester.setBlockCoords(block);
		
		//Ensure that false is returned since an initialized
		//object cannot be equal to null
		assertFalse("null is equal to something", tester.equals(other));
		
		//Set the second array item in block == 1
		block[1] = 1;
		
		//Make other point to a VBTetrisBlock object
		//with others block coordinates being set to 0, 1
		other = new VBTetrisBlock();
		other.setBlockCoords(block);
		
		//Ensure false is returned since otherwise two objects with
		//different coordinates are equal to each other
		assertFalse("returns equal even though array positions are not equal", tester.equals(other));
		
		//Make block coordinates of tester equal those of block
		block[1] = 0;
		other.setBlockCoords(block);
		
		//Set the owner values of tester and other ==
		//to different values
		tester.setOwner(0);
		other.setOwner(1);
		
		//Ensure false is returned since owner values of
		//tester and other are different
		assertFalse("returns equal even though owner params not equal", tester.equals(other));
		
		//Make owner values equal
		other.setOwner(0);	
		
		//Set shape values to two different shapes
		tester.setShape(Tetrominoes.L_SHAPE);
		other.setShape(Tetrominoes.LINE_SHAPE);
		
		//Ensure false is returned since shape values
		//are different
		assertFalse("returns equal even though shapes are not equal", tester.equals(other));
		
		//Make shapes values == in both tester and other
		other.setShape(Tetrominoes.L_SHAPE);
		
		//Make sure tester and other are equal
		assertTrue("Equal things are not equal", tester.equals(other));
		
		//Make sure tester is equal to itself
		assertTrue("Something not equal to itself", tester.equals(tester));
	}

}
