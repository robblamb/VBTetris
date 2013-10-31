package vbtetris;

import javax.swing.JFrame;

import vbtetris.VBTetrisEnvironment;
import vbtetris.VBTetrisGameBoard;

public class VBTetris extends JFrame
{	
	// constants used by VBTetrisGameBoard
	final static int BOARD_WIDTH = 18;
	final static int BOARD_HEIGHT = 27;
	final static int SQUARE_SIZE = 20;
	final static int KILL_LINE = 18;

	// constants used by VBTetris
	final static int FRAME_WIDTH = SQUARE_SIZE * BOARD_WIDTH + 300;
	final static int FRAME_HEIGHT = SQUARE_SIZE * BOARD_HEIGHT+30;
	
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
    	
    	// create the game board
    	_board = new VBTetrisGameBoard(this, BOARD_WIDTH, BOARD_HEIGHT, SQUARE_SIZE, KILL_LINE, players);
    	
    	// create the game environment
    	_gameEnvir = new VBTetrisEnvironment();
    	_gameEnvir.init();
    	
    	// add the board to the frame and start the game
        add(_board);
        _board.init();
    }
    
    public static void main(String[] args)
    {
        VBTetris game = new VBTetris();
        
        game.setTitle("Virtual Boy Tetris");
        game.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        game.setDefaultCloseOperation(EXIT_ON_CLOSE);
        game.setLocationRelativeTo(null);
        game.setVisible(true);
    }
}