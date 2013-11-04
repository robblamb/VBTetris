package vbtetris;

public class VBTetrisPlayer {
	
	static int numPlayers = 0;				// total number of players
	
	private int playerNum;					// unique player number
	private int startPos;					// players start position
	private boolean dropping; 				// is the current piece still moving
	private int numLinesRemoved; 			// current score
	private VBTetrisPieces curPiece; 		// current piece
	private int xPos, yPos;					// current position of players piece
	private int score;						// players score

	VBTetrisPlayer()
	{	
		playerNum = ++numPlayers;			// assign player number
		
		dropping = true;
		numLinesRemoved = 0;
		xPos = yPos = 0;
		score = 0;
	}

	@Override
	public String toString() {
		return "VBTetrisPlayer [playerNum=" + playerNum + ", startPos="
				+ startPos + ", dropping=" + dropping + ", numLinesRemoved="
				+ numLinesRemoved + ", curPiece=" + curPiece + ", xPos=" + xPos
				+ ", yPos=" + yPos + ", score=" + score + "]";
	}

	public int getPlayerID() {return playerNum;}
	
	public int getStartPos() {return startPos;}
	
	public void setStartPos(int width) {startPos = (width / (numPlayers+1)) * playerNum;}
	
	public boolean isdropping() {return dropping;}
	
	public void setdropping(boolean dropping) {this.dropping = dropping;}
	
	public int getnumLinesRemoved() {return numLinesRemoved;}
	
	public void setnumLinesRemoved (int numLinesRemoved) {this.numLinesRemoved = numLinesRemoved;}
	
	public VBTetrisPieces getcurPiece() {return curPiece;}
	
	public void setcurPiece(VBTetrisPieces curPiece) {this.curPiece = curPiece;}
	
	public int getxPos() {return xPos;}
	
	public int getyPos() {return yPos;}
	
	public void setyPos(int yPos) {this.yPos = yPos;}
	
	public void setxPos(int xPos) {this.xPos = xPos;}
	
	public void resetScore() {score = 0;}
	
	public int getScore() {return score;}
	public void addtoscore(int addtoscore) {score += addtoscore;}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof VBTetrisPlayer)) {
			return false;
		}
		VBTetrisPlayer other = (VBTetrisPlayer) obj;
		if (curPiece == null) {
			if (other.curPiece != null) {
				return false;
			}
		} else if (!curPiece.equals(other.curPiece)) {
			return false;
		}
		if (playerNum != other.playerNum) {
			return false;
		}
		if (score != other.score) {
			return false;
		}
		if (xPos != other.xPos) {
			return false;
		}
		if (yPos != other.yPos) {
			return false;
		}
		return true;
	}
		

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((curPiece == null) ? 0 : curPiece.hashCode());
		result = prime * result + playerNum;
		result = prime * result + score;
		result = prime * result + xPos;
		result = prime * result + yPos;
		return result;
	}

	
}