package vbtetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

import vbtetris.VBTetrisBlock;
import vbtetris.VBTetrisPieces.Tetrominoes;


public class VBTetrisGameBoard extends JPanel implements ActionListener
{
	VBTetrisBlock[] _board;	// board array holds the fallen pieces
	
	// board constants to be set by constructor
	private final int BOARD_WIDTH;
	private final int BOARD_HEIGHT;
	private final int SQUARE_SIZE;
	private final int KILL_LINE;
	
	private enum moveStatus { OK, HIT_BOUNDARY, HIT_PIECE }; 
	
	private Timer timer;
	private int msToUpdate = 400;	// game speed in milliseconds
	
	// SHOULD BE IN GAME ENGINE CLASS
	boolean gameOver;
	boolean gamePaused;
	
	private VBTetrisPlayer players[];

	public VBTetrisGameBoard(VBTetris parent, int w, int h, int s, int k, VBTetrisPlayer[] players)
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
			// create a new piece
			players[i].setcurPiece(new VBTetrisPieces());
		}
		
		// set timer
		timer = new Timer(msToUpdate, this);
		
		// create the game board (offset access)
		_board = new VBTetrisBlock[BOARD_WIDTH * BOARD_HEIGHT];
		
		addKeyListener(new TAdapter());
	}
	
	// drop down piece at the specified time interval
	public void actionPerformed(ActionEvent e)
	{ 
		for (int i = 0; i < players.length; ++i) dropOneDown(players[i]);
	}
	
	// returns a block from a spot on the game board
	VBTetrisBlock getBlock(int x, int y)
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
		
		gameOver = false;
		gamePaused = false;
		clear();
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
	// paints the falling and fallen pieces in two steps
	public void paint(Graphics g)
	{ 
		g.drawImage(VBTetris._gameEnvir.getLevelImage(1), 0,0,this);
		
		Dimension size = getSize();
		int boardTop = (int) size.getHeight() - BOARD_HEIGHT * SQUARE_SIZE;
		
		
		// Step 1: paint items in game board
		for (int i = 0; i < BOARD_HEIGHT; ++i) {
			for (int j = 0; j < BOARD_WIDTH; ++j) {	
				VBTetrisBlock block = getBlock(j, BOARD_HEIGHT - i - 1);
				if (!block.isEmpty())
					drawSquare(g, 0 + j * SQUARE_SIZE, boardTop + i * SQUARE_SIZE, block.getOwner());
			}
		}

		
		// Step 2: paint the falling piece
		for (int j = 0; j < players.length; ++j) {
			if (players[j].getcurPiece().getShape() != Tetrominoes.EMPTY) {
				for (int i = 0; i < VBTetrisPieces.NUM_BLOCKS; ++i) {
					int x = players[j].getxPos() + players[j].getcurPiece().getBlock(i).getX();
					int y = players[j].getyPos() - players[j].getcurPiece().getBlock(i).getY();
					drawSquare(g, 0 + x * SQUARE_SIZE,
								boardTop + (BOARD_HEIGHT - y - 1) * SQUARE_SIZE,
								players[j].getcurPiece().getOwner());
				}
			}
		}
		// TODO finish the paint for player areas
		// Step 3: paint the player areas
		int temp = 2;
		int ysiz = BOARD_HEIGHT*SQUARE_SIZE;
		int xLeft = BOARD_WIDTH*SQUARE_SIZE -4; // hack of +4 to fix size issue
		int xRight = xLeft + 300; // hack of + 300, 300 = player area widt
		int areaSize = ysiz / players.length;
		for (int i = 0; i < players.length; ++i) {
			int currTop = areaSize * i; // find y value of top of current box
			int currBottom = areaSize*i + areaSize;
			//	draw area and box in with boarder
			g.setColor(VBTetris._gameEnvir.getPieceColor((players[i].getcurPiece().getOwner())));
			g.fillRect(xLeft, currTop, xRight-xLeft, areaSize);
			
			g.setColor(Color.white);
			g.fillRect(xLeft + 5, currTop + 5, 270, 35);
			
			g.setColor(Color.black);
			g.setFont(new Font("Times New Roman", Font.BOLD, 32));
			g.drawString("Score: " + players[i].getScore(),xLeft + 10, currTop+35);
			
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
	
	private void dropOneDown(VBTetrisPlayer player)
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
		int multiple = removeRow();
		player.addtoscore(1000*multiple*multiple+15);
		newPiece(player);
	}
	
	private void newPiece(VBTetrisPlayer player)
	{

		player.getcurPiece().setRandomShape();
		player.getcurPiece().setOwner(player.getPlayerID());
		player.setxPos(player.getStartPos());
		player.setyPos((int)(BOARD_HEIGHT - Math.sqrt(VBTetrisPieces.NUM_BLOCKS)));
		
		// game is over if a new piece cant spawn
		if (tryMove(player, player.getcurPiece(), player.getxPos(),player.getyPos()) != moveStatus.OK) {
			timer.stop();
			gameOver = true;
		}
		
		player.setdropping(true);
	}

	// ********************************************************************************
	// try to move a piece
	private moveStatus tryMove(VBTetrisPlayer player, VBTetrisPieces newPiece, int newXPos, int newYPos)
	{	
		for (int i = 0; i < VBTetrisPieces.NUM_BLOCKS; ++i) {
			int x = newXPos + newPiece.getBlock(i).getX();
			int y = newYPos - newPiece.getBlock(i).getY();
			
			if (x < 0 || x >= BOARD_WIDTH || y < 0 || y >= BOARD_HEIGHT) return moveStatus.HIT_BOUNDARY;
			if (isBoardBlock( x, y )) return moveStatus.HIT_BOUNDARY;
			if (isPlayerBlock( player, x, y )) return moveStatus.HIT_PIECE;
		}

		player.setcurPiece(newPiece);
		player.setxPos(newXPos);
		player.setyPos(newYPos);
		
		repaint();
		return moveStatus.OK;
	}
	// ********************************************************************************
	
	// check for and remove full lines
	// check for and remove kill lines
	private int removeRow()
	{
		boolean FullLines = false;
		boolean KillLines = false;
		int linesFilled = 0;
		for (int i = BOARD_HEIGHT - 1; i >= 0; --i) {										// starting at the top of the board,
			boolean lineIsFull = true;														// check if the line is full

			for (int j = 0; j < BOARD_WIDTH; ++j) {
				if (!isBoardBlock(j,i)) {													// if any square in the line is empty,
					lineIsFull = false;														// we can stop checking, its not full
					break;
				}
			}

			if (lineIsFull) {
				FullLines = true;															// line is full
				for (int k = i; k < BOARD_HEIGHT - 1; ++k) {								// move all the lines above full line down by 1
					for (int j = 0; j < BOARD_WIDTH; ++j)
						_board[(k * BOARD_WIDTH) + j] = getBlock(j, k + 1);
				}
				linesFilled++;
			}
		}
		
		for (int i = BOARD_HEIGHT - 1; i >= KILL_LINE; --i) {								// starting at the top of the board,
			boolean isKillLine = false;														// check if a square exceeds the kill line

			for (int j = 0; j < BOARD_WIDTH; ++j) {
				if (isBoardBlock(j,i)) {													// if any square in the line is full,
					isKillLine = true;														// we can stop checking, we exceed kill line 
					break;
				}
			}
			
			if (isKillLine) {
				KillLines = true;															// kill line was exceeded
				for (int k = 0; k < BOARD_HEIGHT - 1; ++k) {								// move all lines down by 1
					for (int j = 0; j < BOARD_WIDTH; ++j)
						_board[(k * BOARD_WIDTH) + j] = getBlock(j, k + 1);
				}
			}
		}
		if (FullLines || KillLines) repaint();	

		return linesFilled;
	}

	private void drawSquare(Graphics g, int x, int y, int player)
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
	
	
	// ***************************************************************************************
	// TODO move to own file
	// TODO make own class and incorporate abstracted multi player
	class TAdapter extends KeyAdapter
	{
		public void keyPressed(KeyEvent e)
		{

			if (gameOver) return;

			int keycode = e.getKeyCode();

			if (keycode ==  KeyEvent.VK_SPACE) {
				togglePause();
				return;
			} 
			if (gamePaused) return;

			switch (keycode)
			{
				// player one
				case 'd':
				case 'D':
					// left
					tryMove(players[0],players[0].getcurPiece(), players[0].getxPos() + 1,players[0].getyPos());
					break;
				case 'a':
				case 'A':
					//right
					tryMove(players[0],players[0].getcurPiece(), players[0].getxPos() - 1,players[0].getyPos());
					break;
				case 'w':
				case 'W':
					tryMove(players[0],players[0].getcurPiece().rotateLeft(), players[0].getxPos(),players[0].getyPos());
					break;
				case 's':
				case 'S':
					dropOneDown(players[0]);
					break;
				
				// player 2
				case KeyEvent.VK_LEFT:
					tryMove(players[1],players[1].getcurPiece(), players[1].getxPos() - 1,players[1].getyPos());
					break;
				case KeyEvent.VK_RIGHT:
					tryMove(players[1],players[1].getcurPiece(), players[1].getxPos() + 1,players[1].getyPos());
					break;
				case KeyEvent.VK_DOWN:
					dropOneDown(players[1]);
					break;
				case KeyEvent.VK_UP:
					tryMove(players[1],players[1].getcurPiece().rotateLeft(), players[1].getxPos(),players[1].getyPos());
					break;
			}
		}
	}
	// ***************************************************************************************

}