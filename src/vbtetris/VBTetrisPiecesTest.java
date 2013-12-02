package vbtetris;

/**
 * @author MatthewCormons
 * Edited December 2, 2013
 */

//CSCI331 MC JUNIT

import static org.junit.Assert.*;

import java.util.Arrays;

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
		
		other.setOwner(3);
		//Ensure false is returned since otherwise two objects with
		//different owners are equal to each other
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

	
	@Test
	public void hashCodeTest() {
		VBTetrisPieces s = new VBTetrisPieces();
		assertTrue("s was not equal to itself!!", s.equals(s));
		
		VBTetrisPieces s2 = new VBTetrisPieces();
		assertTrue("Equals not reflexive!!", s.equals(s2) == s2.equals(s));
		
		VBTetrisPieces s3 = new VBTetrisPieces();
		assertTrue("Equals not transitive!!", s.equals(s3) == (s.equals(s2) && s2.equals(s3)));
		
		assertTrue("Equals not consistent!!", s.equals(s2) == (s.equals(s2) == s.equals(s2)));
		
		assertTrue("s equal to NULL!!", s.equals(null) != true);
		
		assertTrue("HashCode returns different values when invoked more than "
				+ "once on the same object!!", s.hashCode() == s.hashCode());
		
		assertTrue("HashCode returns different values when invoked on two objects "
				+ "that are equal!!", s.hashCode() == s2.hashCode());
		
		s.setOwner(4);
		assertTrue("HashCode returns the same value for "
				+ "different objects", s.hashCode() != s2.hashCode());
	}
	
	@Test
	public void testSetShape() {
		int blockCoordsTable[][][] = new int[][][] {
	    		/*
	    		 * 	// TEMPLATE (upside down)
	             *	{ -1, 1 },	{ 0, 1 },	{ 1, 1 },
	             *	{ -1, 0 }, 	{ 0, 0 },	{ 1, 0 },
	             *	{ -1, -1 },	{ 0, -1 },	{ 1, -1 }
	    		 */
	    			
	            {	// EMPTY
	            	{ 0, 0 },	{ 0, 0 },	{ 0, 0 },
	            	{ 0, 0 },	{ 0, 0 },	{ 0, 0 },
	            	{ 0, 0 },	{ 0, 0 },	{ 0, 0 }
	            },
	            
	            {	// Z_SHAPE
	            	{ -1, 1 },	{ 0, 0 },	{ 0, 0 },
	            	{ -1, 0 },	{ 0, 0 },	{ 0, 0 },
	            	{ 0, 0 },	{ 0, -1 },	{ 0, 0 }
	            },
	            
	            {	// S_SHAPE
	            	{ 0, 0 },	{ 0, 0 },	{ 1, 1 },
	            	{ 0, 0 },	{ 0, 0 },	{ 1, 0 },
	            	{ 0, 0 },	{ 0, -1 },	{ 0, 0 }
	            },
	            
	            {	// LINE_SHAPE
	            				{ 0, 2 },
	            	{ 0, 0 },	{ 0, 1 },	{ 0, 0 },
	            	{ 0, 0 },	{ 0, 0 },	{ 0, 0 },
	            	{ 0, 0 },	{ 0, -1 }
	            },
	            
	            {	// T_SHAPE
	            	{ 0, 0 },	{ 0, 0 },	{ 0, 0 },
	            	{ 0, 0 },	{ 0, 0 },	{ 0, 0 },
	            	{ -1, -1 },	{ 0, -1 },	{ 1, -1 }
	            },
	            
	            {	// SQUARE_SHAPE
	            	{ 0, 0 },	{ 0, 0 },	{ 0, 0 },
	            	{ -1, 0 },	{ 0, 0 },	{ 0, 0 },
	            	{ -1, -1 },	{ 0, -1 },	{ 0, 0 }
	            },
	            
	            {	// L_SHAPE
	            	{ 0, 0 },	{ 0, 1 },	{ 0, 0 },
	            	{ 0, 0 },	{ 0, 0 },	{ 0, 0 },
	            	{ -1, -1 },	{ 0, -1 },	{ 0, 0 }
	            },
	            
	            {	// MIRRORED_L_SHAPE
	            	{ 0, 0 },	{ 0, 1 },	{ 0, 0 },
	            	{ 0, 0 },	{ 0, 0 },	{ 0, 0 },
	            	{ 0, 0 },	{ 0, -1 },	{ 1, -1 }
	            }
	            
	            //,
	            
	            //{	// O_SHAPE
	            //	{ -1, 1 },	{ 0, 1 },	{ 1, 1 },
	            //	{ -1, 0 }, 	{ 1, 1 },	{ 1, 0 },
	            //	{ -1, -1 },	{ 0, -1 },	{ 1, -1 }
	            //}
	            
	        };
		
		VBTetrisPieces s = new VBTetrisPieces();
		s.setShape(Tetrominoes.Z_SHAPE);
		assertTrue("Z_SHAPE does not equal itself!!", s.getShape() == Tetrominoes.Z_SHAPE);
		
		for (int i = 0; i < 9; i++) {
			VBTetrisBlock tmp = new VBTetrisBlock();
			tmp.setBlockCoords(blockCoordsTable[1][i]);
			assertTrue("Z_SHAPE encoding error!!", s.getBlock(i).equals(tmp));
		}
		
		s.setShape(Tetrominoes.S_SHAPE);
		assertTrue("S_SHAPE does not equal itself!!", s.getShape() == Tetrominoes.S_SHAPE);
		
		for (int i = 0; i < 9; i++) {
			VBTetrisBlock tmp = new VBTetrisBlock();
			tmp.setBlockCoords(blockCoordsTable[2][i]);
			assertTrue("S_SHAPE encoding error!!", s.getBlock(i).equals(tmp));
		}
		s.setShape(Tetrominoes.L_SHAPE);
		assertTrue("L_SHAPE does not equal itself!!", s.getShape() == Tetrominoes.L_SHAPE);
		
		for (int i = 0; i < 9; i++) {
			VBTetrisBlock tmp = new VBTetrisBlock();
			tmp.setBlockCoords(blockCoordsTable[6][i]);
			assertTrue("L_SHAPE encoding error!!", s.getBlock(i).equals(tmp));
		}
		s.setShape(Tetrominoes.LINE_SHAPE);
		assertTrue("LINE_SHAPE does not equal itself!!", s.getShape() == Tetrominoes.LINE_SHAPE);
		
		for (int i = 0; i < 9; i++) {
			VBTetrisBlock tmp = new VBTetrisBlock();
			tmp.setBlockCoords(blockCoordsTable[3][i]);
			assertTrue("LINE_SHAPE encoding error!!", s.getBlock(i).equals(tmp));
		}
		s.setShape(Tetrominoes.T_SHAPE);
		assertTrue("T_SHAPE does not equal itself!!", s.getShape() == Tetrominoes.T_SHAPE);
		
		for (int i = 0; i < 9; i++) {
			VBTetrisBlock tmp = new VBTetrisBlock();
			tmp.setBlockCoords(blockCoordsTable[4][i]);
			assertTrue("T_SHAPE encoding error!!", s.getBlock(i).equals(tmp));
		}
		s.setShape(Tetrominoes.SQUARE_SHAPE);
		assertTrue("SQUARE_SHAPE does not equal itself!!", s.getShape() == Tetrominoes.SQUARE_SHAPE);
		for (int i = 0; i < 9; i++) {
			VBTetrisBlock tmp = new VBTetrisBlock();
			tmp.setBlockCoords(blockCoordsTable[5][i]);
			assertTrue("SQUARE_SHAPE encoding error!!", s.getBlock(i).equals(tmp));
		}
		s.setShape(Tetrominoes.MIRRORED_L_SHAPE);
		assertTrue("MIRRORED_L_LSHAPE does not equal itself!!", s.getShape() == Tetrominoes.MIRRORED_L_SHAPE);
		for (int i = 0; i < 9; i++) {
			VBTetrisBlock tmp = new VBTetrisBlock();
			tmp.setBlockCoords(blockCoordsTable[7][i]);
			assertTrue("MIRRORED_L_SHAPE encoding error!!", s.getBlock(i).equals(tmp));
		}
		
	}
}
