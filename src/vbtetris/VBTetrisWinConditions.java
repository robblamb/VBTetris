package vbtetris;

public class VBTetrisWinConditions {
	private int score;
	private int lines;
	private int loseScore;
	
	public VBTetrisWinConditions() {
		score = 10000;
		lines = 10;
		loseScore = -5000;
	}
	public VBTetrisWinConditions(int score, int lines, int lose){
		this.score = score;
		this.lines = lines;
		this.loseScore = lose;
	}
	public int getWinScore(){return score;}
	public void setWinScore(int score){this.score=score;}
	
	public int getWinLines(){return lines;}
    public void setWinLInes(int lines){this.lines= lines;}
    
    public int getLoseScore(){return loseScore;}
    public void setLoseScore(int lose){this.loseScore=lose;}
    
    public boolean isWinScore(int score){return score>=this.score;}
    public boolean isWinLines(int lines){return lines>=this.lines;} 
    public boolean isWin(int score, int lines){return (score>=this.score || lines>=this.lines);}
    public boolean isLose(int score){return score<=this.loseScore;}
    
}
