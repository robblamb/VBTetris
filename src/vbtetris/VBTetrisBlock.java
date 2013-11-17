package vbtetris;

import java.util.Arrays;

import vbtetris.VBTetrisPieces.Tetrominoes;

public class VBTetrisBlock
{	
	private boolean isEmpty;									// is the block occupied
	private Tetrominoes shape;									// shape block belongs to
	private int owner;											// owner of the block
    private int blockCoords[];									// coords of the block
	
    VBTetrisBlock() {
    	shape = Tetrominoes.EMPTY;
    	isEmpty = true;
    	blockCoords = new int[2];								// create new blockCoords array
    	owner = 0;    	
    }
    
    public void setShape(Tetrominoes shape) {this.shape = shape;}
    public Tetrominoes getShape() {return shape;}
    
    public void setEmpty(boolean status) {isEmpty = status;}
    public boolean isEmpty() {return isEmpty;}
    
    public void setOwner(int player) {owner = player;}
    public int getOwner() {return owner;}
    
    // set the x position of the piece block
    public void setX(int x) {blockCoords[0] = x;}
    
    // set the y position of the piece block
    public void setY(int y) {blockCoords[1] = y;}
    
    // get the x position of the piece block
    public int getX() {return blockCoords[0];}
    
    // get the y position of the piece block
    public int getY() {return blockCoords[1];}
    
    public void setBlockCoords(int[] coords) {
    	blockCoords[0] = coords[0];								// set x
    	blockCoords[1] = coords[1];								// set y
    }
    
    public int[] getBlockCoords() {return blockCoords;}

	@Override
	public String toString() {
		return "VBTetrisBlock [isEmpty=" + isEmpty + ", shape=" + shape
				+ ", owner=" + owner + ", blockCoords="
				+ Arrays.toString(blockCoords) + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(blockCoords);
		result = prime * result + owner;
		result = prime * result + ((shape == null) ? 0 : shape.hashCode());
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
		if (!(obj instanceof VBTetrisBlock)) {
			return false;
		}
		VBTetrisBlock other = (VBTetrisBlock) obj;
		if (!Arrays.equals(blockCoords, other.blockCoords)) {
			return false;
		}
		if (owner != other.owner) {
			return false;
		}
		if (shape != other.shape) {
			return false;
		}
		return true;
	}
    
       
}