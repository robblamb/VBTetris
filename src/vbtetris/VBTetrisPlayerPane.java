package vbtetris;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class VBTetrisPlayerPane extends JPanel
{
	// board constants to be set by constructor
	private final int PANEL_WIDTH;
	private final int BOARD_HEIGHT;
	private final int SQUARE_SIZE;
	
	VBTetrisPlayerPane(int pw, int bh, int sq)
	{
		PANEL_WIDTH = pw;
		BOARD_HEIGHT = bh;
		SQUARE_SIZE = sq;
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		int ysiz = BOARD_HEIGHT*SQUARE_SIZE;
		int areaSize = ysiz / VBTetris._board.getPlayers().length;
		
		// fix font size across platforms
	    int screenRes = Toolkit.getDefaultToolkit().getScreenResolution();
	    int fontSize = (int)Math.round(22.0 / (screenRes / 72.0));
		
		for (int i = 0; i < VBTetris._board.getPlayers().length; ++i) {
			
			int currTop = areaSize * i;	// find y value of top of current box
			int currBottom = areaSize*i + areaSize;
			
			// draw player pane
			g.setColor(VBTetris._board.getPlayers()[i].getPlayerColour());
			g.fillRect(0, currTop, PANEL_WIDTH, areaSize);
			
			// draw score box
			g.setColor(Color.white);
			g.fillRect(5, currTop + 5, PANEL_WIDTH-10, 30);
			
			// draw score
			g.setColor(Color.black);
			g.setFont(new Font("Times New Roman", Font.BOLD, fontSize));
			g.drawString("Score: " + VBTetris._board.getPlayers()[i].getScore(), 10, currTop+30);
			
			// draw lines scored
			g.setColor(Color.white);
			g.fillRect(5, currTop + 40, PANEL_WIDTH-10, 30);
			
			// draw number of lines scored
			g.setColor(Color.black);
			g.setFont(new Font("Times New Roman", Font.BOLD, fontSize-2));
			g.drawString("Cleared Lines: " + VBTetris._board.getPlayers()[i].getnumLinesRemoved(), 10, currTop+62);
			
			// draw next piece in player pane 
			for (int j = 0; j < VBTetrisPieces.NUM_BLOCKS; ++j) {
				int x = (PANEL_WIDTH/2) + (VBTetris._board.getPlayers()[i].getNextPiece().getBlock(j).getX() * SQUARE_SIZE);
				int y = (currTop + 35 + (4*SQUARE_SIZE)) - (VBTetris._board.getPlayers()[i].getNextPiece().getBlock(j).getY() * SQUARE_SIZE);
				VBTetris._board.drawSquare(g, x, y, VBTetris._board.getPlayers()[i].getNextPiece().getOwner());
			}
		}
		
	}
	
}
