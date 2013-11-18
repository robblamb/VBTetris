package vbtetris;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class VBTetrisFrame extends JFrame {

	private int killLine;
	private int players;
	
	
	public VBTetrisFrame(){
		initGame();
	}
	public int getkillLine(){return killLine;}
	public void setkillLine(int newKill){ this.killLine=newKill;}
	
	public int getplayers(){return players;}
	public void setplayers( int players){this.players=players;}
	
	public final void initGame(){
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
		
		// creata a new action listener to deal with the
		// selection
		exMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		reset.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				System.out.println("Resitn for new game");
			}
		});
		
		// add the created items to the new menu
		// order matters here
		file.add(reset);
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
			JMenuItem opkillhigh = new JMenuItem("High");
			JMenuItem opkillmid = new JMenuItem("Middle");
			JMenuItem opkillLow = new JMenuItem("Low (Hard)");
			JMenuItem opkilloff = new JMenuItem("off");
			
			opkillhigh.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					setkillLine(18);
				}
			});
			opkillmid.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					setkillLine(14);
				}
			});
			opkillLow.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					setkillLine(9);
				}
			});
			opkilloff.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					setkillLine(32);
				}
			});
		
			
			// order matters when adding, but not creating
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
					setplayers(1);
				}
			});
			plrs2.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					setplayers(2);
				}
			});
			plrs3.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					setplayers(3);
				}
			});
			plrs4.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					setplayers(4);
				}
			});
			players.add(plrs1);
			players.add(plrs2);
			players.add(plrs3);
			players.add(plrs4);
		options.add(players);
		
			
		
		JMenu help = new JMenu("Help");
		
		JMenuItem about = new JMenuItem("About");
		JMenuItem keys = new JMenuItem("Input Keys");
		keys.setToolTipText("-A left-  -D right-  -W up-  -S down-");
	
		
		help.add(about);
		help.add(keys);
		

		menubar.add(file);
		menubar.add(options);
		menubar.add(help);
		
		
		setJMenuBar(menubar);
		
		setTitle("Virtual Boy Tetris");
	//	setSize(800,600);
		 setLocationRelativeTo(null);
	     setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	 public static void main(String[] args) {

	        SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                VBTetrisFrame ex = new VBTetrisFrame();
	        		// create and set up the content pane
	        		JComponent contentPane = new VBTetris();
	        		contentPane.setOpaque(true); //content panes must be opaque
	        		ex.setContentPane(contentPane);
	        		ex.pack();
	                ex.setVisible(true);
	            }
	        });
	    }
}
