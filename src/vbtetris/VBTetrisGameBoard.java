package vbtetris;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import javax.swing.JPanel;
import javax.swing.Timer;

import vbtetris.VBTetrisPieces.Tetrominoes;

public class VBTetrisGameBoard extends JPanel implements ActionListener
{
	VBTetrisBlock[] _board;	// board array holds the fallen pieces
	
	// board constants to be set by constructor
	private final int BOARD_WIDTH;
	private final int BOARD_HEIGHT;
	private final int SQUARE_SIZE;
	private final int KILL_LINE;
	
	public enum moveStatus { OK, HIT_BOUNDARY, HIT_PIECE }; 
	
	// SHOULD BE IN GAME ENGINE CLASS?
	private VBTetrisTimer timer;    // timer triggers and action every msToUpdate milliseconds
	private int msToUpdate = 400;	// game speed in milliseconds
	private VBTetrisPieceMover _mover;
	
	// SHOULD BE IN GAME ENGINE CLASS
	private boolean gameOver;
	private boolean gamePaused;
	
	public boolean isGameOver() {
		return gameOver;
	}
	public boolean isGamePaused() {
		return gamePaused;
	}

	private VBTetrisPlayer[] players;
	private BufferedImage boardBackground;
	
	public VBTetrisGameBoard(int w, int h, int s, int k, VBTetrisPlayer[] players)
	{
		BOARD_WIDTH = w;
		BOARD_HEIGHT = h;
		SQUARE_SIZE = s;
		KILL_LINE = k;
		
		this.players = players;
		
		// give board the keyboard input
		setFocusable(true);
		
		// set spawn point for each player and create a new piece
		for (int i = 0; i < players.length; ++i) {
			// set start position
			players[i].setStartPos(BOARD_WIDTH);
			// create a new current piece
			players[i].setcurPiece(new VBTetrisPieces());
			// create a new next piece
			players[i].setNextPiece(new VBTetrisPieces());
			// create initial piece
			players[i].getNextPiece().setRandomShape();
			players[i].getNextPiece().setOwner(players[i].getPlayerID());
		}
		
		// set timer with initial time to fire of msToUpdate
		timer = new VBTetrisTimer(msToUpdate, this);
		
		// create the game board (offset access)
		_board = new VBTetrisBlock[BOARD_WIDTH * BOARD_HEIGHT];
		
		// create the board background image
		boardBackground = new VBTetrisBackgroundImage(VBTetris._gameEnvir.getLevelImage(0));
		// create the mover that is an interface for pieces to move on the board
		_mover = new VBTetrisPieceMover(this);
		addKeyListener(new TAdapter());
		addKeyListener(new VBTetrisWASDKeys(players[1], _mover));
		addKeyListener(new VBTetrisArrowKeys(players[0], _mover));
	}
	
	public VBTetrisPlayer[] getPlayers()
	{
		return players;
	}
	
	// drop down piece at the specified time interval
	public void actionPerformed(ActionEvent e)
	{ 
		for (int i = 0; i < players.length; ++i) dropOneDown(players[i]);
	}
	
	// returns a block from a spot on the game board
	private VBTetrisBlock getBlock(int x, int y)
	{	
		return _board[(y * BOARD_WIDTH) + x];	// (row_num * row_offset) + col
	}
	
	private boolean isPlayerBlock(VBTetrisPlayer currPlayer, int currPlayerX, int currPlayerY)
	{	
		// check if any of the other players have a piece in specified location
		for (int i = 0; i < players.length; ++i) {	// for each player
			if (!players[i].equals(currPlayer)) {	// that isnt the current player		
				// check xy against all blocks in other players piece
				for (int j = 0; j < VBTetrisPieces.NUM_BLOCKS; ++j) {	// for each block in the other piece
					if ( (currPlayerX == players[i].getxPos() + players[i].getcurPiece().getBlock(j).getX()) && 	// currX == otherX &&
						 (currPlayerY == players[i].getyPos() - players[i].getcurPiece().getBlock(j).getY()) ) {	// currY == otherY
							return true;	// has player block
					}
				}
				
			}
		}
		return false;	// has no player block
	}
	
	private boolean isBoardBlock(int x, int y)
	{
		if (_board[(y * BOARD_WIDTH) + x].isEmpty() ) return false;
		return true;
	}


	public void init()
	{
		for (int i = 0; i < _board.length; ++i)
			_board[i] = new VBTetrisBlock();
		clear();
		
		gameOver = false;
		gamePaused = false;
		
		timer.start();
	}

	// toggle pause state
	private void togglePause()
	{
		gamePaused = !gamePaused;	
		if (gamePaused) timer.stop();
		else timer.start();
	}
	
	// ****************************************************************************************************************
	// paints the background and the falling and fallen pieces
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		// draw the background
		g.drawImage(boardBackground, 0, 0, this);
		
		// paint items in game board
		for (int i = 0; i < BOARD_HEIGHT; ++i) {
			for (int j = 0; j < BOARD_WIDTH; ++j) {	
				VBTetrisBlock block = getBlock(j, BOARD_HEIGHT - i - 1);
				if (!block.isEmpty())
					drawSquare(g, j * SQUARE_SIZE, i * SQUARE_SIZE, block.getOwner());
			}
		}

		// paint the falling pieces
		for (int j = 0; j < players.length; ++j) {
			if (players[j].getcurPiece().getShape() != Tetrominoes.EMPTY) {
				for (int i = 0; i < VBTetrisPieces.NUM_BLOCKS; ++i) {
					int x = players[j].getxPos() + players[j].getcurPiece().getBlock(i).getX();
					int y = players[j].getyPos() - players[j].getcurPiece().getBlock(i).getY();
					drawSquare(g, 0 + x * SQUARE_SIZE,
								(BOARD_HEIGHT - y - 1) * SQUARE_SIZE,
								players[j].getcurPiece().getOwner());
				}
			}
		}
	}
	// ****************************************************************************************************************
	

	// drop the piece to the bottom of the board
//	private void dropDown(VBTetrisPlayer player)
//	{
//		int y = player.getyPos();
//		while (y > 0) {
//			if (!tryMove(player, player.getcurPiece(), player.getxPos(), y - 1))
//				break;
//			--y;
//		}
//		storePiece(player);
//	}
	
	@Override
	public String toString() {
		return "VBTetrisGameBoard [BOARD_WIDTH=" + BOARD_WIDTH
				+ ", BOARD_HEIGHT=" + BOARD_HEIGHT + ", SQUARE_SIZE="
				+ SQUARE_SIZE + ", KILL_LINE=" + KILL_LINE + ", players="
				+ Arrays.toString(players) + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + SQUARE_SIZE;
		result = prime * result + BOARD_HEIGHT;
		result = prime * result + BOARD_WIDTH;
		result = prime * result + KILL_LINE;
		result = prime * result + Arrays.hashCode(players);
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
		if (!(obj instanceof VBTetrisGameBoard)) {
			return false;
		}
		VBTetrisGameBoard other = (VBTetrisGameBoard) obj;
		if (SQUARE_SIZE != other.SQUARE_SIZE) {
			return false;
		}
		if (BOARD_HEIGHT != other.BOARD_HEIGHT) {
			return false;
		}
		if (BOARD_WIDTH != other.BOARD_WIDTH) {
			return false;
		}
		if (KILL_LINE != other.KILL_LINE) {
			return false;
		}
		if (!Arrays.equals(players, other.players)) {
			return false;
		}
		return true;
	}

	public void dropOneDown(VBTetrisPlayer player)
	{	
		// if the piece hits the boundary (in this case the bottom) store the piece in the game board
		// we can check HIT_BOUNDARY and know we hit the bottom because we only move the piece down
		if (tryMove(player, player.getcurPiece(), player.getxPos(), player.getyPos()-1)==moveStatus.HIT_BOUNDARY)
			storePiece(player);
	}
	
	// clears the board
	private void clear()
	{	
		for (int i = 0; i < _board.length; ++i)
			_board[i].setEmpty(true);
	}

	// stores a piece on the board
	private void storePiece(VBTetrisPlayer player)
	{

		// *****************************************************
		for (int i = 0; i < VBTetrisPieces.NUM_BLOCKS; ++i) {
			int x = player.getxPos() + player.getcurPiece().getBlock(i).getX();
			int y =player.getyPos() - player.getcurPiece().getBlock(i).getY();
			_board[(y * BOARD_WIDTH) + x] = player.getcurPiece().getBlock(i);
			
			// when a piece has fallen, transfer piece info to gameboard
			_board[(y * BOARD_WIDTH) + x].setShape(player.getcurPiece().getShape());
			_board[(y * BOARD_WIDTH) + x].setOwner(player.getPlayerID());
			
			// if block is not of shape empty, mark as full
			if (_board[(y * BOARD_WIDTH) + x].getShape() != Tetrominoes.EMPTY) {
				_board[(y * BOARD_WIDTH) + x].setEmpty(false);
			}
		}
		// *****************************************************
		
		// find (and remove) complete rows
		int numCompleteRows = checkCompleteRow(player.getMinY(), player.getMaxY());
		
		// update the number of complete rows
		player.setnumLinesRemoved((player.getnumLinesRemoved()+numCompleteRows));
		
		// find (and remove) blocks above kill line
		int numKillLines = checkKillZone();
		
		// calculate score multiplier
		if (numKillLines > 1) numKillLines = 1;
		int multiple = numCompleteRows - numKillLines;
		
		// modify player score
		if (multiple > 0) player.addtoscore(1000*multiple*multiple+15);
		else player.addtoscore(-1000*multiple*multiple+15);
		
		// create a new piece
		newPiece(player);
	}
	
	private void newPiece(VBTetrisPlayer player)
	{
		// set the current piece to the next piece
		player.getcurPiece().setShape(player.getNextPiece().getShape());
		player.getcurPiece().setOwner(player.getPlayerID());
		
		// generate a new next piece
		player.getNextPiece().setRandomShape();
		
		// reset piece xy position
		player.setxPos(player.getStartPos());
		player.setyPos((int)(BOARD_HEIGHT - Math.sqrt(VBTetrisPieces.NUM_BLOCKS)));
		
		// game is over if a new piece cant spawn
		if (tryMove(player, player.getcurPiece(), player.getxPos(),player.getyPos()) != moveStatus.OK) {
			timer.stop();
			gameOver = true;
		}
		
		player.setdropping(true);
	}

	// try to move a piece
	public moveStatus tryMove(VBTetrisPlayer player, VBTetrisPieces newPiece, int newXPos, int newYPos)
	{	
		for (int i = 0; i < VBTetrisPieces.NUM_BLOCKS; ++i) {
			int x = newXPos + newPiece.getBlock(i).getX();
			int y = newYPos - newPiece.getBlock(i).getY();
			
			if (x < 0 || x >= BOARD_WIDTH || y < 0 || y >= BOARD_HEIGHT) return moveStatus.HIT_BOUNDARY;
			if (isBoardBlock( x, y )) return moveStatus.HIT_BOUNDARY;
			if (isPlayerBlock( player, x, y )) return moveStatus.HIT_PIECE;
		}
		
		// update the piece and repaint
		player.setcurPiece(newPiece);
		player.setxPos(newXPos);
		player.setyPos(newYPos);
		
		repaint();
		VBTetris._pane.repaint();
		return moveStatus.OK;
	}
	
	// check if the placed piece completed any rows
	private int checkCompleteRow (int minY, int maxY)
	{	
		int numCompleteRows = 0;
		
		int activeRow = minY;
		int rowsChecked = 0;
		while (rowsChecked <= maxY-minY) {
			for (int j = 0; j < BOARD_WIDTH; ++j) {
				if (!isBoardBlock(j,activeRow)) {
					++activeRow;
					break;
				} else if (j == BOARD_WIDTH-1) {
					removeRow(activeRow);
					++numCompleteRows;
				}
			}
			++rowsChecked;
		}
		return numCompleteRows;
	}
	
	// check if the placed piece sits above the kill line
	private int checkKillZone()
	{	
		int numKillLines = 0;
		
		int rowsChecked = KILL_LINE;
		while (rowsChecked <= BOARD_HEIGHT - 1) {
			for (int j = 0; j < BOARD_WIDTH; ++j) {
				if (isBoardBlock(j,KILL_LINE)) {
					removeRow(0);
					++numKillLines;
					break;
				}
			}
			++rowsChecked;
		}
        return numKillLines;
	}
	
	// remove a row
	private void removeRow(int activeRow)
	{
		// move all the rows above the target row down by 1
		for (int k = activeRow; k < BOARD_HEIGHT - 1; ++k) {
			for (int j = 0; j < BOARD_WIDTH; ++j)
				_board[(k * BOARD_WIDTH) + j] = getBlock(j, k + 1);
		}
		repaint();
	}

	public void drawSquare(Graphics g, int x, int y, int player)
	{
		Color color = VBTetris._gameEnvir.getPieceColor(player);
		g.setColor(color);
		
		// ***********************************************************************************
		g.fillRect(x + 1, y + 1, SQUARE_SIZE - 2, SQUARE_SIZE - 2);
		
		// draw white edge
		g.setColor(Color.WHITE);
		g.drawLine(x, y + SQUARE_SIZE - 1, x, y);
		g.drawLine(x, y, x + SQUARE_SIZE - 1, y);
		
		// draw black edge
		g.setColor(Color.BLACK);
		g.drawLine(x + 1, y + SQUARE_SIZE - 1, x + SQUARE_SIZE - 1, y + SQUARE_SIZE - 1);
		g.drawLine(x + SQUARE_SIZE - 1, y + SQUARE_SIZE - 1, x + SQUARE_SIZE - 1, y + 1);
		// ***********************************************************************************
	}
	
	
	// Only respsonsible for pausing or unpausing the game
	private class TAdapter extends KeyAdapter
	{
		@Override
		public void keyPressed(KeyEvent e)
		{

			if (gameOver) return;

			int keycode = e.getKeyCode();

			if (keycode ==  KeyEvent.VK_SPACE) {
				togglePause();
				return;
			} 
			if (gamePaused) return;

		}
	}
	// ***************************************************************************************

}