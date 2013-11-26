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
import java.util.Random;

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
	private int KILL_LINE;
	
	public enum moveStatus { OK, HIT_BOUNDARY, HIT_PIECE }; 
	

	private VBTetrisWinConditions _winCond;

	// SHOULD BE IN GAME ENGINE CLASS?
	private VBTetrisTimer timer;    // timer triggers and action every msToUpdate milliseconds
	private int msToUpdate = 400;	// game speed in milliseconds
	private int numUpdates = 50;    // number of times to update before speeding up
	private int curUpdates = 0;
	
	private VBTetrisPieceMover _mover;
	private VBTetrisPowerUpSelector _power;
	private VBTetrisPowerUp powUpOnBoard;
	private Random random;
	protected static VBTetrisClock clock;
	
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
	private VBTetrisKeyAdapter[] playAdapters;
	private VBTetrisPauser myPause;

	private int startPositions[];
	
	public VBTetrisGameBoard(int w, int h, int s, int k, VBTetrisPlayer[] players)
	{
		BOARD_WIDTH = w;
		BOARD_HEIGHT = h;
		SQUARE_SIZE = s;
		KILL_LINE = k;
		
		int startpossize = (int) (w/(players.length +1));
		
		
        _winCond = new VBTetrisWinConditions();
		this.players = players;
		
		// give board the keyboard input
		setFocusable(true);
		
		// set spawn point for each player and create a new piece
		for (int i = 0; i < players.length; ++i) {
			// set start position
			//players[i].setStartPos(BOARD_WIDTH);
			players[i].setInitialPos(startpossize*(i+1));
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
		boardBackground = new VBTetrisBackgroundImage(VBTetris._gameEnvir.getLevelImage());
		// create the mover that is an interface for pieces to move on the board
		_mover = new VBTetrisPieceMover(this);
		myPause = new VBTetrisPauser();
		 addKeyListener(myPause);
		
		playAdapters = new VBTetrisKeyAdapter[players.length];
		playAdapters[0] = new VBTetrisWASDKeys(players[0], _mover);
		playAdapters[1] = new VBTetrisArrowKeys(players[1], _mover);
		for (int i = 0; i < players.length; i++) {
			addKeyListener(playAdapters[i]);
		}
		
		_power = new VBTetrisPowerUpSelector();
		powUpOnBoard = null;
		int seed = (int)(Math.random()*100000000);
		random = new Random(seed);
		
		clock = new VBTetrisClock();
	}
	public void setKill(int newKill){this.KILL_LINE = newKill;}
	
	public void stop()
	{
		VBTetris._gameEnvir.stopMusic();
		clock.stop();
		gameOver = true;
		for (int i = 0; i < players.length; i++) {
			removeKeyListener(playAdapters[i]);
		}
		removeKeyListener(myPause);
		timer.stop();
	}
	
	public String getPowUpName(VBTetrisPlayer currentPlayer) 
	{
		if (powUpOnBoard != null) {
			return powUpOnBoard.getName(currentPlayer);
		}
		return "";
	}
	
	public VBTetrisPlayer[] getPlayers()
	{
		return players;
	}
	
	// drop down piece at the specified time interval
	public synchronized void actionPerformed(ActionEvent e)
	{ 
		if ((++curUpdates)%numUpdates==0){
			timer.speedupby(5);
			numUpdates*=2;
		}
		for (int i = 0; i < players.length; ++i) {
			if (players[i].isdropping()) {
				dropOneDown(players[i]);
			}
		}
		putPowerBlock();
	}
	
	private synchronized void putPowerBlock() 
	{
		if ((_power.getPowerUpOnGameBoard() == false && powUpOnBoard == null)) {
			
			for (int i = 0; i < _board.length; i++) {
				if (_board[i].getOwner() == 0) {
					_board[i].setEmpty(true);
				}
			}
			
			_power.setPowerUpOnGameBoard(false);
			powUpOnBoard = null;
			powUpOnBoard = _power.chooseAPowerUp();
			if (powUpOnBoard != null) {
				_power.setPowerUpOnGameBoard(true);
				int xPos = random.nextInt(BOARD_WIDTH);
				int yPos = random.nextInt(BOARD_HEIGHT-4);
				while (isPlayerBlock(null, xPos, yPos) || isBoardBlock(xPos, yPos)) {
					xPos = random.nextInt(BOARD_WIDTH);
					yPos = random.nextInt(BOARD_HEIGHT-4);
				}
				powUpOnBoard.setXPosition(xPos);
				powUpOnBoard.setYPosition(yPos);
				_board[(yPos * BOARD_WIDTH) + xPos].setEmpty(false);
				_board[(yPos * BOARD_WIDTH) + xPos].setShape(Tetrominoes.SQUARE_SHAPE);
			}
		}
	}
	// returns a block from a spot on the game board
	private VBTetrisBlock getBlock(int x, int y)
	{	if (x>BOARD_WIDTH || y>BOARD_HEIGHT) return new VBTetrisBlock();
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
		//if (x<0 || y < 0 || x > BOARD_WIDTH || y > BOARD_HEIGHT) return false;
		if ( _board[(y * BOARD_WIDTH) + x].isEmpty() ) return false;
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
	public boolean getGamePaused()
	{
		return gamePaused;
	}
	
	// toggle pause state
	public void togglePause()
	{
		gamePaused = !gamePaused;	
		if (gamePaused) {
			clock.stop();
			timer.stop();
			VBTetris._pause.setVisible(true);
		}
		else {
			clock.start();
			timer.start();
			VBTetris._pause.setVisible(false);
			VBTetris._pause.setAmIRule(false);
		}
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
		// paint the kill line
		g.setColor(Color.WHITE);
		g.drawLine(0, SQUARE_SIZE*BOARD_HEIGHT-KILL_LINE*SQUARE_SIZE, BOARD_WIDTH*SQUARE_SIZE, SQUARE_SIZE*BOARD_HEIGHT-KILL_LINE*SQUARE_SIZE);
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

	public synchronized void dropOneDown(VBTetrisPlayer player)
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
	private synchronized void storePiece(VBTetrisPlayer player)
	{
		if (powUpOnBoard != null && _power.getPowerUpOnGameBoard() == false) {
			if (powUpOnBoard.secondCommit(player)) {
				powUpOnBoard = null;
			}
		}
		
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
		int pointsAchieved = 1000*multiple*multiple+15;
		if (multiple >= 0) {
			player.addtoscore(pointsAchieved);
			// update player most points achieved
			if (pointsAchieved > player.getMostPoints()) {player.setMostPoints(pointsAchieved);}
		} else {
			player.addtoscore(-pointsAchieved);
		}

		// check for a victorious player
		if (_winCond.isLose(player.getScore()) || _winCond.isWinScore(player.getScore()) )
		{	
			stop();
			togglePause();
			return;
		}

		if (numCompleteRows>0) VBTetris._gameEnvir.playLineSound(numCompleteRows);
		if (numKillLines>0) VBTetris._gameEnvir.playLineSound(0);

		// create a new piece
		newPiece(player);
		player.setPiecesSpawned();
	}
	public int rowEmptyToRight(int toTheRightOfMe, int atMyHeight, VBTetrisPlayer myPlayer)
	{
		if (toTheRightOfMe > BOARD_WIDTH || atMyHeight > BOARD_HEIGHT 
				|| toTheRightOfMe < 0 || atMyHeight < 0) {
			return 0;
		}
		
		int howManyToTheRight = 0;
		
		for (int i = toTheRightOfMe; i < BOARD_WIDTH; i++) {
			if (!isBoardBlock(i, atMyHeight) && !isPlayerBlock(myPlayer, i, atMyHeight)) {
				howManyToTheRight++;
			} else {
				break;
			}
		}
		
		return howManyToTheRight;
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
			stop();
			togglePause();
			return;
		}
		
		if (powUpOnBoard != null && !powUpOnBoard.amIActive()) {
			player.setdropping(true);
		}
	}
	// CSCI331 COLLISION
	/**
	 * Although we used the collisions using an array instead of coordinates, 
	 * this is the point in the code where collisions are detected.  Not only
	 * are they detected here, but the return indicates the type of collision.
	 * 
	 * @param player is the player trying to move a piece
	 * @param newPiece is the piece that the player will have after the move
	 * @param newXPos is the desired x coordinate 
	 * @param newYPos is the desired y coordinate
	 * @return
	 */
	// try to move a piece
	public synchronized moveStatus tryMove(VBTetrisPlayer player, VBTetrisPieces newPiece, int newXPos, int newYPos)
	{	
		for (int i = 0; i < VBTetrisPieces.NUM_BLOCKS; ++i) {
			int x = newXPos + newPiece.getBlock(i).getX();
			int y = newYPos - newPiece.getBlock(i).getY();
			
			if (x < 0 || x >= BOARD_WIDTH || y < 0 || y >= BOARD_HEIGHT) return moveStatus.HIT_BOUNDARY;
			if (isBoardBlock( x, y )) {
				if (_power.getPowerUpOnGameBoard() && powUpOnBoard != null) {
					if (powUpOnBoard.didICollide(x, y)) {
						if (powUpOnBoard.commitAction(this, player, timer)) {
							_board[(powUpOnBoard.getYPosition() * BOARD_WIDTH) + powUpOnBoard.getXPosition()].setEmpty(true);
							_power.setPowerUpOnGameBoard(false);
							repaint();
							return moveStatus.OK;
						}
					}
				} 
				return moveStatus.HIT_BOUNDARY;
			}
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
	private synchronized void removeRow(int activeRow)
	{
		if (powUpOnBoard != null && _power.getPowerUpOnGameBoard() ) {
			_board[(powUpOnBoard.getYPosition() * BOARD_WIDTH) + powUpOnBoard.getXPosition()].setEmpty(true);
			powUpOnBoard = null;
			_power.setPowerUpOnGameBoard(false);
			//putPowerBlock();
		}
		
		// move all the rows above the target row down by 1
		for (int k = activeRow; k < BOARD_HEIGHT - 1; ++k) {
			for (int j = 0; j < BOARD_WIDTH; ++j) {	
				_board[(k * BOARD_WIDTH) + j] = getBlock(j, k + 1);
			}
		}
		for (int i = BOARD_WIDTH*(BOARD_HEIGHT-1); i < BOARD_WIDTH*BOARD_HEIGHT; ++i){
			_board[i]=new VBTetrisBlock();
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
	private class VBTetrisPauser extends KeyAdapter
	{
		@Override
		public void keyPressed(KeyEvent e)
		{

			if (gameOver) return;
			if (e.getKeyCode() ==  KeyEvent.VK_SPACE) {
				e.consume();
				togglePause();
			} 
	
		}
	}
	
	// does not take a tie into account
	public int findWinner() {
		int currWinnerID = players[0].getPlayerID();
		int highestScore = players[0].getScore();
		for (int i = 1; i < players.length; ++i) {
			if (highestScore < players[i].getScore()) {
				highestScore = players[i].getScore();
				currWinnerID = players[i].getPlayerID();
			}
		}
		return currWinnerID;
	}


}
