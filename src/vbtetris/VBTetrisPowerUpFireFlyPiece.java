package vbtetris;

import vbtetris.VBTetrisPieces.Tetrominoes;

public class VBTetrisPowerUpFireFlyPiece extends VBTetrisPieces {
	private int[][] myBlockCoordsTable;
	private int counter;
	private boolean status;
	
	public VBTetrisPowerUpFireFlyPiece()
	{
		super();
		counter = 0;
		status = true;
	}
	
	public void setsShape()
	{     	
		myBlockCoordsTable = new int[][] {         
				//Firefly
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
	        
	     pieceShape = Tetrominoes.S_SHAPE;
	}
	
	public void setStatus(boolean valToSet)
	{
		status = valToSet;
	}
	
	public VBTetrisPieces rotateLeft()
	{
		if (status) {
			return rotatesLeft();
		} else {
			return super.rotateLeft();
		}
	}
	
	private VBTetrisPieces rotatesLeft() 
    {
    	// must implement image rotation
    	if (pieceShape == Tetrominoes.SQUARE_SHAPE)
            return this;

        VBTetrisPieces result = new VBTetrisPieces();						// create new result piece
        result.pieceShape = this.pieceShape;								// set result shape to piece shape
        result.setOwner(this.getOwner());											// set result owner to piece shape
        counter++;
        
        if (counter%4 == 0) {
        	for (int i = 0; i < NUM_BLOCKS; ++i) {								// for each block in the piece
        		result.getBlock(i).setX(this.getBlock(i).getX()+1);
        		result.getBlock(i).setY(this.getBlock(i).getY());
        	}
        } else if (counter%4 == 1) {
        	for (int i = 0; i < NUM_BLOCKS; ++i) {								// for each block in the piece
        		result.getBlock(i).setX(this.getBlock(i).getX());
        		result.getBlock(i).setY(this.getBlock(i).getY()+1);
        	}
        } else if(counter%4 == 2) {
        	for (int i = 0; i < NUM_BLOCKS; ++i) {								// for each block in the piece
        		result.getBlock(i).setX(this.getBlock(i).getX()-1);
        		result.getBlock(i).setY(this.getBlock(i).getY());
        	}
        } else {
        	for (int i = 0; i < NUM_BLOCKS; ++i) {								// for each block in the piece
        		result.getBlock(i).setX(this.getBlock(i).getX());
        		result.getBlock(i).setY(this.getBlock(i).getY()-1);
        	}
        }
        return result;
    }
}