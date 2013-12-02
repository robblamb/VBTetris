package vbtetris;

/**
 * 
 * @author MatthewCormons
 * Last edited: December 2, 2013
 */

public class VBTetrisPowerUpCompletePiece extends VBTetrisPieces {
	private int[][] myBlockCoordsTable;
	
	public VBTetrisPowerUpCompletePiece()
	{
		super();
	}
	
	public void setsShape()
	{     	
		myBlockCoordsTable = new int[][] {         
	            //Singleton
	            { 0, 0 },	{ 0, 0 },	{ 0, 0 },
	            { 0, 0 },	{ 0, 0 },	{ 0, 0 },
	            { 0, 0 },	{ 0, 0 },	{ 0, 0 }
	        }; 
		
		// fill the blocks array with 9 new blocks, each block gets a set of coords and an owner,
	     // coords are based on the shape we want to create
	     for (int i = 0; i < NUM_BLOCKS; ++i) {		// for each block make a new block and get coords from
	         blocks[i] = new VBTetrisBlock();		//myBlockCoordsTable
	         blocks[i].setBlockCoords(myBlockCoordsTable[i]);
	     }
	        
	     pieceShape = Tetrominoes.SQUARE_SHAPE;//Give it square shape since we don't want this shape to rotate
	}
	
	public void expandLeft(int numSquaresToExpand)
	{
		for (int i = 0; i < numSquaresToExpand && i < NUM_BLOCKS; i++) {
			myBlockCoordsTable[i][0] = i;//Expand right until either 8 blocks to the
										//right of the original block has been reached
										//or max number of blocks has been reached
		}
		
		for (int i = 0; i < NUM_BLOCKS; ++i) {	//Make a new piece with the above parameters (i.e. the width)										
	         blocks[i] = new VBTetrisBlock();
	         blocks[i].setBlockCoords(myBlockCoordsTable[i]);				
	     }
	}
}