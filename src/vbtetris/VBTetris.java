package vbtetris;

import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

public class VBTetris extends JFrame
{	
	// players
	final static int NUM_PLAYERS = 2;
	private VBTetrisPlayer players[];
	
	// game constants
	final static int PLAYER_GAP = 4;
	final static int BOARD_WIDTH = PLAYER_GAP + (NUM_PLAYERS*(PLAYER_GAP+(int)Math.sqrt(VBTetrisPieces.NUM_BLOCKS)));
	final static int BOARD_HEIGHT = 32;
	final static int SQUARE_SIZE = 20;
	final static int KILL_LINE = 18;
	final static int BOARD_WIDTH_PX = BOARD_WIDTH * SQUARE_SIZE;
	final static int BOARD_HEIGHT_PX = BOARD_HEIGHT * SQUARE_SIZE;
	
	final static int PANEL_WIDTH = 175;
	final static int FRAME_WIDTH = SQUARE_SIZE * BOARD_WIDTH + PANEL_WIDTH;
	final static int FRAME_HEIGHT = SQUARE_SIZE * BOARD_HEIGHT;
	final static int VICTORY_SCORE = 20000;
	
	// environment object
	static VBTetrisEnvironment _gameEnvir;
	
	// jpanels
	static VBTetrisGameBoard _board;
	static VBTetrisPlayerPane _pane;
	
	public VBTetris()
	{
		setLayout( new BoxLayout( getContentPane(), BoxLayout.X_AXIS ));
		
		//VBTetrisStartGameData gameData = (new VBTetrisOptions(BOARD_WIDTH_PX+PANEL_WIDTH, BOARD_HEIGHT_PX)).getGameCond();
	//	VBTetrisOptions _options = new VBTetrisOptions(BOARD_WIDTH_PX+PANEL_WIDTH, BOARD_HEIGHT_PX);
	//	add(_options);
		
		// create array of players
		players = new VBTetrisPlayer[NUM_PLAYERS];
		for (int i = 0; i < players.length; ++i) {
			players[i] = new VBTetrisPlayer(VICTORY_SCORE);
		}
		
		// create the game environment
		_gameEnvir = new VBTetrisEnvironment();
		_gameEnvir.init();
		
		// create the game board
		_board = new VBTetrisGameBoard(BOARD_WIDTH, BOARD_HEIGHT, SQUARE_SIZE, KILL_LINE, players);
		_board.setPreferredSize(new Dimension(BOARD_WIDTH_PX, BOARD_HEIGHT_PX));
		_board.init();
		
		// create the player paneB
		_pane = new VBTetrisPlayerPane(PANEL_WIDTH, BOARD_HEIGHT, SQUARE_SIZE);
		_pane.setPreferredSize(new Dimension(PANEL_WIDTH, BOARD_HEIGHT_PX));
		
		// add the game board and player pane to the jframe
		add(_board);
		add(_pane);
		
		// set jframe options
		setResizable(false);
		setTitle("Virtual Boy Tetris");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		pack();
		setLocationRelativeTo(null);
	}
	
	
	static public void main (String[] args)
	{
		new VBTetris();
	}
}