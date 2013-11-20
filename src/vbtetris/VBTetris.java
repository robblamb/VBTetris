package vbtetris;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class VBTetris extends JPanel {
	
	// http://docs.oracle.com/javase/tutorial/uiswing/components/layeredpane.html
	
	// players
	static int NUM_PLAYERS = 2;
	private static VBTetrisPlayer players[];

	final static int PLAYER_GAP = 4;	// squares between each player
	static int KILL_LINE = 18;	// squares before kill line
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
	
	private static JFrame frame;
	static JComponent contentPane;
	static JLayeredPane layeredPane;
	
	static VBTetrisGameBoard _board;
	static VBTetrisPlayerPane _pane;
	static VBTetrisPauseScreen _pause;
	private static int numplayers;
	//private static int killLine;
	
	public VBTetris() {
		
		// set contentPane layout manager
		setLayout( new BoxLayout( this, BoxLayout.X_AXIS ));
		
		// create and set up the layered pane
		layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(FRAME_WIDTH_PX, FRAME_HEIGHT_PX));	
		// create array of players
		players = new VBTetrisPlayer[NUM_PLAYERS];
		for (int i = 0; i < players.length; ++i) {
			players[i] = new VBTetrisPlayer(VICTORY_SCORE,i+1);
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
		_pause = new VBTetrisPauseScreen(FRAME_WIDTH_PX, FRAME_HEIGHT_PX, players );

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
		

		// add the layeredPane to the content pane
		add(layeredPane);
		
		// set the position of the _pause pane
		layeredPane.setPosition(VBTetris._pause, 0);
		
	}
	public static void setPlayersNum(int newNum){NUM_PLAYERS=newNum;}
	public int getkillLine(){return KILL_LINE;}
	public static void setkillLine(int newKill){ KILL_LINE=newKill;}
	
	public static void killgame(){
		_board = null;
		for (int i = 0; i < players.length; ++i){
			players[i]=null;
		}
		_gameEnvir = null;
		frame.setVisible(false);
		frame = null;
	}
	public int getplayers(){return numplayers;}

	private static void createAndShowGUI() {
		// create the menu bar across top 
				JMenuBar menubar = new JMenuBar();
				// declare main menus
				
				// File:
				//	Create the menu and selectable options
				JMenu file = new JMenu ("File");
				// menu items are selectable things
				JMenuItem exMenuItem = new JMenuItem("Exit");
				// what a hover over says
				exMenuItem.setToolTipText("Exit Virtual Boy Tetris");

				JMenuItem reset = new JMenuItem("Reset Game");
				reset.setToolTipText("Restarts the game with the current Options");
				
				JMenuItem pause = new JMenuItem("Pause/Unpause");
				pause.setToolTipText("ShortCut: Spacebar");
				
				// create a new action listener to deal with the selection
				exMenuItem.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						System.exit(0);
					}
				});
				pause.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						_board.togglePause();
					}
				});
				reset.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e){
						killgame();
						javax.swing.SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								createAndShowGUI();
							}
						});
					}
				});
				
				// add the created items to the new menu
				// order matters here
				file.add(reset);
				file.add(pause);
				file.addSeparator(); 
				file.add(exMenuItem);
				
				
				// 	Options:
				JMenu options = new JMenu("Options");
					// options: kill line
					JMenu killLine = new JMenu("Kill Line");
					killLine.setToolTipText("The kill line is the white line, have a "
											+ "piece end up above it and the bottom of the "
											+ "board is killed, along with the score!");	
					// MenuItems can be selected
					JMenuItem opkillveryhigh = new JMenuItem("Very High");
					JMenuItem opkillhigh = new JMenuItem("High");
					JMenuItem opkillmid = new JMenuItem("Middle");
					JMenuItem opkillLow = new JMenuItem("Low (Hard)");
					JMenuItem opkilloff = new JMenuItem("off");
					
					opkillveryhigh.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0) {
							setkillLine(22); // sets kill line in frame
							_board.setKill(22); // sets kill line in game (no reset needed)
						}
					});
					opkillhigh.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0) {
							setkillLine(18); // sets kill line in frame
							_board.setKill(18); // sets kill line in game (no reset needed)
						}
					});
					opkillmid.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0) {
							setkillLine(14);
							_board.setKill(14);
						}
					});
					opkillLow.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0) {
							setkillLine(9);
							_board.setKill(9);
						}
					});
					opkilloff.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0) {
							setkillLine(BOARD_HEIGHT+10);
							_board.setKill(BOARD_HEIGHT+10);
						}
					});
				
					
					// order matters when adding, but not creating
					killLine.add(opkillveryhigh);
					killLine.add(opkillhigh);
					killLine.add(opkillmid);
					killLine.add(opkillLow);
					killLine.addSeparator();
					killLine.add(opkilloff);
				// the the sub-menu parts to main menu part
				options.add(killLine);
				
				JMenu players = new JMenu("Players");
					JMenuItem plrs1 = new JMenuItem("One   (1)");
					JMenuItem plrs2 = new JMenuItem("Two   (2)");
					JMenuItem plrs3 = new JMenuItem("Three (3)");
					JMenuItem plrs4 = new JMenuItem("Four  (4)");
					
				
					plrs1.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0) {
							setPlayersNum(1);
						}
					});
					plrs2.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0) {
							setPlayersNum(2);
						}
					});
					plrs3.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0) {
							setPlayersNum(3);
						}
					});
					plrs4.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0) {
							setPlayersNum(4);
						}
					});
					players.add(plrs1);
					players.add(plrs2);
					players.add(plrs3);
					players.add(plrs4);
					players.setVisible(false); // need to fix before making true
				options.add(players);
				JMenu help = new JMenu("Help");
				JMenuItem about = new JMenuItem("About");
				JMenuItem keys = new JMenuItem("Input Keys");
				keys.setVisible(false);
				help.add(about);
				help.add(keys);	
				menubar.add(file);
				menubar.add(options);
				menubar.add(help);

		// create and set up the window
		frame = new JFrame();
		frame.setJMenuBar(menubar);
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