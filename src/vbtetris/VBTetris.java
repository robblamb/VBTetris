package vbtetris;

import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class VBTetris extends JPanel {
	
	// http://docs.oracle.com/javase/tutorial/uiswing/components/layeredpane.html
	
	// players
	final static int NUM_PLAYERS = 2;
	private VBTetrisPlayer players[];
	
	final static int PLAYER_GAP = 4;	// squares between each player
	final static int KILL_LINE = 18;	// squares before kill line
	final static int VICTORY_SCORE = 20000;	// points needed to win 
	
	// board dimensions in terms of squares / blocks
	final static int BOARD_WIDTH = PLAYER_GAP + (NUM_PLAYERS*(PLAYER_GAP+(int)Math.sqrt(VBTetrisPieces.NUM_BLOCKS)));
	final static int BOARD_HEIGHT = 32;
	final static int SQUARE_SIZE = 20;
	
	// board dimensions in terms of pixels
	final static int BOARD_WIDTH_PX = BOARD_WIDTH * SQUARE_SIZE;
	final static int BOARD_HEIGHT_PX = BOARD_HEIGHT * SQUARE_SIZE;
	final static int PANEL_WIDTH_PX = 175;
	
	// frame dimensions in terms of pixels
	final static int FRAME_WIDTH_PX = BOARD_WIDTH_PX + PANEL_WIDTH_PX;
	final static int FRAME_HEIGHT_PX = BOARD_HEIGHT_PX;
	
	// environment object
	static VBTetrisEnvironment _gameEnvir;
	
	static JFrame frame;
	static JComponent contentPane;
	static JLayeredPane layeredPane;
	
	static VBTetrisGameBoard _board;
	static VBTetrisPlayerPane _pane;
	static VBTetrisPauseScreen _pause;
	static VBTetrisOptionsScreen _options;
	
	public VBTetris() {
		
		// set contentPane layout manager
		setLayout( new BoxLayout( this, BoxLayout.X_AXIS ));
		
		// create and set up the layered pane
		layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(FRAME_WIDTH_PX, FRAME_HEIGHT_PX));
		
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
		_board.init();
		
		// create the player pane
		_pane = new VBTetrisPlayerPane(PANEL_WIDTH_PX, BOARD_HEIGHT, SQUARE_SIZE);
		
		// create pause screen panel
		_pause = new VBTetrisPauseScreen(FRAME_WIDTH_PX, FRAME_HEIGHT_PX);
		
		// create options screen panel
		_options = new VBTetrisOptionsScreen(FRAME_WIDTH_PX, FRAME_HEIGHT_PX);
		
		// add the game board pane
		layeredPane.add(_board);
		_board.setBounds(0, 0, BOARD_WIDTH_PX, BOARD_HEIGHT_PX);
		
		// add the player pane
		layeredPane.add(_pane);
		_pane.setBounds(BOARD_WIDTH_PX, 0, FRAME_WIDTH_PX, FRAME_HEIGHT_PX);
		
		// add the pause screen pane
		layeredPane.add(_pause);
		_pause.setBounds(0, 0, FRAME_WIDTH_PX, FRAME_HEIGHT_PX);
		_pause.setVisible(false);
		
		// add the options screen pane
		layeredPane.add(_options);
		_options.setBounds(0, 0, FRAME_WIDTH_PX, FRAME_HEIGHT_PX);			
		
		// add the layeredPane to the content pane
		add(layeredPane);
		
		// set the position of the _pause pane
		layeredPane.setPosition(VBTetris._pause, 0);
		
	}
	
	private static void createAndShowGUI() {
		
		// create and set up the window
		JFrame frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Virtual Boy Tetris");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(FRAME_WIDTH_PX, FRAME_HEIGHT_PX));
		frame.setLocationRelativeTo(null);

		// create and set up the content pane
		JComponent contentPane = new VBTetris();
		contentPane.setOpaque(true); //content panes must be opaque
		frame.setContentPane(contentPane);

		// show the window
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}