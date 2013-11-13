package vbtetris;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import vbtetris.VBTetrisGameBoard.moveStatus;
import vbtetris.VBTetrisPieces.Tetrominoes;

/*
 * IMPORTANT NOTE: These tests do not use VBTetrisEnvironment, and (for now) the game board
 * wants to look for the static environment variable in VBTetris which will be null.
 * As a quick fix, comment out the use of boardBackground in the VBTetrisGameBoard constructor
 * before running these tests.
 */
public class VBTetrisGameBoardTest
{
	final static int NUM_PLAYERS = 2;
	private static VBTetrisPlayer players[];
	
	private static VBTetrisGameBoard _board;
	
	final static int BOARD_WIDTH = 20;
	final static int BOARD_HEIGHT = 20;
	final static int SQUARE_SIZE = 2;
	final static int KILL_LINE = 10;
	final static int VICTORY_SCORE = 20000;
	
	/*
	 * Setup the game board and players
	 */
	@BeforeClass
	public static void setUp()
	{
		// create array of players
		players = new VBTetrisPlayer[NUM_PLAYERS];
		for (int i = 0; i < players.length; ++i) {
			players[i] = new VBTetrisPlayer(VICTORY_SCORE);
			players[i].setcurPiece(new VBTetrisPieces());
		}
		
		_board = new VBTetrisGameBoard(BOARD_WIDTH, BOARD_HEIGHT, SQUARE_SIZE, KILL_LINE, players);
	}
	
	/*
	 * Reset the game board
	 */
	@Before
	public void init()
	{
		_board.init();
	}
	
	/*
	 * Test that we can remove rows from a complete line
	 */
	@Test
	public void testRemoveCompleteRow()
	{	
		boolean rowRemoved = true;
		
		VBTetrisPlayer player1 = players[0];
		
		player1.getcurPiece().setShape(Tetrominoes.L_SHAPE);
		player1.setxPos(3);
		player1.setyPos(1);
		
		// fill the width of the board with line tetrominoes
		for (int i = 0; i < BOARD_WIDTH; ++i) {
			_board._board[i].setEmpty(false);
			_board._board[i].setShape(Tetrominoes.LINE_SHAPE);
		}
		
		// drop a block to initiate row removal
		_board.dropOneDown(player1);
		
		// make sure each block in the row is now removed
		for (int i = 0; i < BOARD_WIDTH; ++i) {
			if (!_board._board[i].isEmpty() && _board._board[i].getShape() != Tetrominoes.L_SHAPE) {
				rowRemoved = false;
			}
		}

		assertTrue("Row was not removed!", rowRemoved==true);
	}
	
	/*
	 * Test that we can remove rows above the kill line
	 */
	@Test
	public void testRemoveKillLine()
	{
		boolean blocksRemoved = true;
				
		VBTetrisPlayer player1 = players[0];
		
		player1.getcurPiece().setShape(Tetrominoes.L_SHAPE);
		player1.setxPos(3);
		player1.setyPos(1);
		
		// but a block above the kill line
		_board._board[BOARD_WIDTH*KILL_LINE].setEmpty(false);
		
		// drop a block to initiate row removal
		_board.dropOneDown(player1);
		
		for (int i = BOARD_WIDTH*BOARD_HEIGHT-1; i > BOARD_WIDTH*KILL_LINE-1 ; --i) {
			if (!_board._board[i].isEmpty()) {
				blocksRemoved=false;
			}
		}
		
		assertTrue("Blocks above kill line were not removed!", blocksRemoved==true);
	}
	
	/*
	 * Test collision detection
	 */
	@Test
	public void testMoveOk()
	{
		moveStatus status;
		
		VBTetrisPlayer player1 = players[0];
		
		player1.getcurPiece().setShape(Tetrominoes.SQUARE_SHAPE);
		player1.setxPos(1);
		player1.setyPos(BOARD_HEIGHT-3);
		
		// move block off board
		status  = _board.tryMove(player1, player1.getcurPiece(), player1.getxPos(), player1.getyPos()-1);
		
		assertTrue("Move was not OK", status==moveStatus.OK);
	}
	
	@Test
	public void testMoveHitBoundary()
	{
		moveStatus status;
		
		VBTetrisPlayer player1 = players[0];
		
		player1.getcurPiece().setShape(Tetrominoes.SQUARE_SHAPE);
		player1.setxPos(1);
		player1.setyPos(BOARD_HEIGHT-3);
		
		// move block off board
		status  = _board.tryMove(player1, player1.getcurPiece(), player1.getxPos()-1, player1.getyPos());
		
		assertTrue("Boundary collision did not register", status==moveStatus.HIT_BOUNDARY);
	}
	
	@Test
	public void testMoveHitPiece()
	{
		moveStatus status;
		
		VBTetrisPlayer player1 = players[0];
		VBTetrisPlayer player2 = players[1];
		
		player1.getcurPiece().setShape(Tetrominoes.SQUARE_SHAPE);
		player1.setxPos(5);
		player1.setyPos(BOARD_HEIGHT-3);
		
		player2.getcurPiece().setShape(Tetrominoes.SQUARE_SHAPE);
		player2.setxPos(5);
		player2.setyPos(BOARD_HEIGHT-5);
		
		// move block onto a player piece
		status  = _board.tryMove(player1, player1.getcurPiece(), player1.getxPos(), player1.getyPos()-1);
		
		assertTrue("Piece collision did not register", status==moveStatus.HIT_PIECE);
	}
	
}
