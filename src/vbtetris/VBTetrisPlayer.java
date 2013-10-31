package vbtetris;

import java.awt.Color;

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
	
}