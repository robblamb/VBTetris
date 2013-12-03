package vbtetris;

import java.util.Arrays;
import java.lang.Math;

/*
 * 
 * each new VBTetrisPieces holds coords for each of the 9 blocks (int blockCoords[][])
 * and holds what shape it is (Tetrominoes pieceShape) 
 *  
 */
public class VBTetrisPieces
{	
	public final static int NUM_BLOCKS = 9;             // size of each shape
	public final static int NUM_SHAPES = 8;             // the number of shapes
	
    enum Tetrominoes {
    	EMPTY,
    	Z_SHAPE,
    	S_SHAPE,
    	LINE_SHAPE,
    	T_SHAPE,
    	SQUARE_SHAPE,
    	L_SHAPE,
    	MIRRORED_L_SHAPE
    	//O_SHAPE
    };
    
    private static int[][][] blockCoordsTable;						// holds all possible block coords
    
    protected Tetrominoes pieceShape;								// piece shape
    private int owner;												// piece owner
   
    protected VBTetrisBlock blocks[];								// holds the blocks that make up the shape
    
    public VBTetrisPieces()
    {
    	blocks = new VBTetrisBlock[NUM_BLOCKS];						// creare a new container for blocks
        setShape(Tetrominoes.EMPTY);								// set the shape as empty
        setOwner(0);												// set owner to player zero
    }
    
    // set piece owner
    public void setOwner(int player) {owner = player;}
    
    // get piece owner
    public int getOwner() {return owner;}
    
    // returns the blocks that make up the piece
    public VBTetrisBlock[] getBlocks() {return blocks;}
    
    // returns a specific block that make up the piece
    public VBTetrisBlock getBlock(int i) {return blocks[i];}
    
    public void setShape(Tetrominoes shape)
    {	
    	blockCoordsTable = new int[][][] {
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
        
    	
        // fill the blocks array with 9 new blocks, each block gets a set of coords and an owner,
        // coords are based on the shape we want to create
        for (int i = 0; i < NUM_BLOCKS; ++i) {											// for each block
            blocks[i] = new VBTetrisBlock();
            blocks[i].setBlockCoords(blockCoordsTable[shape.ordinal()][i]);				// setCoords, blockCoordsTable[shape][block]
        }
        
        pieceShape = shape;
        
    }
    
    // get the shape of the piece
    public Tetrominoes getShape() {return pieceShape;}

    public void setRandomShape()
    {
    	int rand = (int)((NUM_SHAPES-1)*Math.random()) + 1;
        Tetrominoes[] values = Tetrominoes.values(); 
        setShape(values[rand]);
    }
    
    public VBTetrisPieces rotateLeft() 
    {
    	// must implement image rotation
    	if (pieceShape == Tetrominoes.SQUARE_SHAPE)
            return this;

        VBTetrisPieces result = new VBTetrisPieces();						// create new result piece
        result.pieceShape = this.pieceShape;								// set result shape to piece shape
        result.owner = this.owner;											// set result owner to piece shape
        
        for (int i = 0; i < NUM_BLOCKS; ++i) {								// for each block in the piece
        	result.getBlock(i).setX(this.getBlock(i).getY());
        	result.getBlock(i).setY(-this.getBlock(i).getX());
        }
        return result;
    }

	@Override
	public String toString() {
		return "VBTetrisPieces [pieceShape=" + pieceShape + ", owner=" + owner
				+ ", blocks=" + Arrays.toString(blocks) + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(blocks);
		result = prime * result + owner;
		result = prime * result
				+ ((pieceShape == null) ? 0 : pieceShape.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof VBTetrisPieces)) {
			return false;
		}
		VBTetrisPieces other = (VBTetrisPieces) obj;
		if (!Arrays.equals(blocks, other.blocks)) {
			return false;
		}
		if (owner != other.owner) {
			return false;
		}
		if (pieceShape != other.pieceShape) {
			return false;
		}
		return true;
	}

    /*
    public VBTetrisPieces rotateRight()
    {
    	// must implement image rotation
        if (pieceShape == Tetrominoes.SQUARE_SHAPE || pieceShape == Tetrominoes.O_SHAPE)
            return this;

        VBTetrisPieces result = new VBTetrisPieces();
        result.pieceShape = pieceShape;
        result.owner = owner;
        
        for (int i = 0; i < NUM_BLOCKS; ++i) {
        	result.getBlock(i).setX(-this.getBlock(i).getY());
        	result.getBlock(i).setY(this.getBlock(i).getX());
        }
        return result;
    }
    */

}