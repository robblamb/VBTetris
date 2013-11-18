package vbtetris;

import vbtetris.VBTetrisPieces.Tetrominoes;

public class VBTetrisPowerUpFireFlyPiece extends VBTetrisPieces {
	private int[][] myBlockCoordsTable;
	
	public VBTetrisPowerUpFireFlyPiece()
	{
		super();
	}
	
	public void setsShape()
	{     	
		myBlockCoordsTable = new int[][] {         
				//Firefly
            	{ 1, 1 },	{ 1, 1 },	{ 1, 1 },
            	{ 1, 1 },	{ 1, 1 },	{ 1, 1 },
            	{ 1, 1 },	{ 1, 1 },	{ 1, 1 }
	        }; 
		
		// fill the blocks array with 9 new blocks, each block gets a set of coords and an owner,
	     // coords are based on the shape we want to create
	     for (int i = 0; i < NUM_BLOCKS; ++i) {											// for each block
	         blocks[i] = new VBTetrisBlock();
	         blocks[i].setBlockCoords(myBlockCoordsTable[i]);				// setCoords, blockCoordsTable[shape][block]
	     }
	        
	     pieceShape = Tetrominoes.S_SHAPE;
	}
}