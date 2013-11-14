package vbtetris;

public class VBTetrisPowerUpCompletePiece extends VBTetrisPieces {
	@Override
	public void setShape(Tetrominoes shape)
	{
		setsShape(Tetrominoes.SQUARE_SHAPE);
	}
	
	private void setsShape(Tetrominoes shape)
	{
		int[][] myBlockCoordsTable = new int[][] {         
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
	        
	     pieceShape = shape;
	}
	
	@Override
	public void setRandomShape()
	{
		setsShape(Tetrominoes.SQUARE_SHAPE);
	}
}