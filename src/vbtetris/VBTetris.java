package vbtetris;

import java.awt.Dimension;

import javax.swing.JFrame;
import vbtetris.VBTetrisEnvironment;
import vbtetris.VBTetrisGameBoard;

public class VBTetris extends JFrame
{	
	// constants used by VBTetrisGameBoard
	// current bg image ratio: 12:13 (but it can be stretched)
	final static int BOARD_WIDTH = 28;
	final static int BOARD_HEIGHT = 26;
	final static int SQUARE_SIZE = 20;
	final static int KILL_LINE = 18;

	// constants used by VBTetris
	final static int FRAME_WIDTH = SQUARE_SIZE * BOARD_WIDTH + 200;
	final static int FRAME_HEIGHT = SQUARE_SIZE * BOARD_HEIGHT;
	
	// public environment object
	public static VBTetrisEnvironment _gameEnvir;
	
	// private game board
	private VBTetrisGameBoard _board;

	// players
	final static int NUM_PLAYERS = 2;
	private VBTetrisPlayer players[];
	
	public VBTetris()
	{
		
		// create array of players
		players = new VBTetrisPlayer[NUM_PLAYERS];
		for (int i = 0; i < players.length; ++i) {
			players[i] = new VBTetrisPlayer();
		}
		
		// create the game environment
		_gameEnvir = new VBTetrisEnvironment();
		_gameEnvir.init();
		
		// create the game board
		_board = new VBTetrisGameBoard(BOARD_WIDTH, BOARD_HEIGHT, SQUARE_SIZE, KILL_LINE, players);
		_board.init();
		
		// add the board to the frame
		add(_board);
		
		// set jframe options
		setTitle("Virtual Boy Tetris");
		getContentPane().setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		
	}
	
	
	static public void main (String[] args)
	{
		new VBTetris();
	}
}