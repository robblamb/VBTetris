package vbtetris;

import java.util.Arrays;

import vbtetris.VBTetrisPieces.Tetrominoes;

/** CSCI331 ER STATICBINDING
 * 
 * All of the methods and fields in this class will be statically bound.
 * 
 * private, final and static methods and variables uses static binding
 * and are bonded by the compiler.
 * 
 * All the static method calls are resolved at compile time because
 * static methods are accessed using the class name. Therefore there
 * the compiler knows which version of the method is going to be called. 
 * 
 */
public class VBTetrisBlock
{	
	
	/** CSCI331 ER ENCAPSULATION
	 * 
	 * In this class, all the fields are private and can only be
	 * accessed using the public getters and setters.
	 * 
	 * Although the getters and setters in this class don't do
	 * anything special (like validation), using encapsulation
	 * allows me to easily change implimentation in the future,
	 * if necessary, without changing every file in the project.
	 * 
	 * One of the main reasons for not using public member variables
	 * is that if something goes wrong with a public field the problem
	 * can possibly be anywhere in the code, whereas if the field is
	 * private there is only one place to look for the bug (right here
	 * in this class).
	 * 
	 */
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
    
    public void setX(int x) {blockCoords[0] = x;}
    public int getX() {return blockCoords[0];}
    
    public void setY(int y) {blockCoords[1] = y;}
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