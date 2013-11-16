package vbtetris;

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
	     for (int i = 0; i < NUM_BLOCKS; ++i) {											// for each block
	         blocks[i] = new VBTetrisBlock();
	         blocks[i].setBlockCoords(myBlockCoordsTable[i]);				// setCoords, blockCoordsTable[shape][block]
	     }
	        
	     pieceShape = Tetrominoes.SQUARE_SHAPE;
	}
	
	public void expandLeft(int numSquaresToExpand)
	{
		for (int i = 0; i < numSquaresToExpand && i < NUM_BLOCKS; i++) {
			myBlockCoordsTable[i][0] = i;
		}
		
		for (int i = 0; i < NUM_BLOCKS; ++i) {											
	         blocks[i] = new VBTetrisBlock();
	         blocks[i].setBlockCoords(myBlockCoordsTable[i]);				
	     }
	}
}