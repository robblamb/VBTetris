package vbtetris;

import static org.junit.Assert.*;

import org.junit.Test;

import vbtetris.VBTetrisPieces.Tetrominoes;

public class VBTetrisPlayerTest {

	final static int VICTORY_SCORE = 20000;
	@Test
	public void testEquals() {
		//Initialize two VBTetrisPlayer for use in testing
		VBTetrisPlayer tester = null;
		VBTetrisPlayer other = null;
				
		//Make tester point to a VBTetrisPlayer object
		tester = new VBTetrisPlayer(VICTORY_SCORE,1);
				
		//Ensure that false is returned since an initialized
		//object cannot be equal to null
		assertFalse("null is equal to something", tester.equals(other));
		
		//Make notTP a reference to a non-VBTetrisPlayer object
		VBTetrisPieces notTP = new VBTetrisPieces();
		
		//Ensure false otherwise a non-VBTetrisPlayer object
		//is equal to a VBTetrisPlayer object
		assertFalse("a non-VBTetrisPlayer is equal to a VBTetrisPlayer", tester.equals(notTP));
		
		//Make other point to a VBTetrisPieces object
		other = new VBTetrisPlayer(VICTORY_SCORE,2);
		
		//Make a new empty VBTetrisPiece to give to other
		VBTetrisPieces nNotTP = new VBTetrisPieces();
				
		//Set tester's current piece to notTP
		tester.setcurPiece(notTP);
		
		//Ensure that false is returned since otherwise a null
		//curPiece is equal to an empty curPiece (which is NOT 
		//the same!)
		assertFalse("a null piece is equal to an empty piece", tester.equals(other));
		
		//Make other's curPiece == nNotTP
		other.setcurPiece(nNotTP);
		
		//Make tester's curPiece non-Empty and with owner 0
		tester.getcurPiece().setShape(Tetrominoes.L_SHAPE);
		tester.getcurPiece().setOwner(0);
		
		//Make other's curPiece non-Empty and equal to tester's
		//curPiece EXCEPT for the owner which is different
		//(0 != 1)
		other.getcurPiece().setShape(Tetrominoes.L_SHAPE);
		other.getcurPiece().setOwner(1);
		
		//Ensure that false since otherwise two unequal
		//curPieces would be equal
		assertFalse("two unequal pieces are equal!!", tester.equals(other));
		
		//Make other's curPiece's owner == tester's curPiece's owner
		other.getcurPiece().setOwner(0);
		
		//Ensure false otherwise two different player's are equal each other 
		assertFalse("two different players are equal each other", tester.equals(other));
		
		//Ensure true since otherwise tester would have different score or different
		//xPos or different yPos from itself
		assertTrue("values initialized to 0 are not equal", tester.equals(tester));
	}

}
