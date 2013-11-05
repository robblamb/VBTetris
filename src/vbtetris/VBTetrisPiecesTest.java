package vbtetris;

import static org.junit.Assert.*;

import org.junit.Test;

import vbtetris.VBTetrisPieces.Tetrominoes;

public class VBTetrisPiecesTest {

	@Test
	public void testEquals() {
		//Initialize two VBTetrisPieces for use in testing
		VBTetrisPieces tester = null;
		VBTetrisPieces other = null;
				
		//Make tester point to a VBTetrisPieces object
		tester = new VBTetrisPieces();
				
		//Ensure that false is returned since an initialized
		//object cannot be equal to null
		assertFalse("null is equal to something", tester.equals(other));
				
		//Make other point to a VBTetrisPieces object
		other = new VBTetrisPieces();
				
		//Ensure false is returned since otherwise two objects with
		//different coordinates are equal to each other
		assertFalse("returns equal even though array positions are not equal", tester.equals(other));
				
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
