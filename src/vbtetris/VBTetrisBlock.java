package vbtetris;

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
       
}