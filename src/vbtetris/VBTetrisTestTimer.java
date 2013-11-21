package vbtetris;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
public class VBTetrisTestTimer implements ActionListener {
	private int time;
	public static int x;
	public static void main(String[] args) {
		System.out.println("Trying to get the timer tested");
		VBTetrisTestTimer tester = new VBTetrisTestTimer();
		final VBTetrisTimer timer = new VBTetrisTimer(400, tester);
		timer.start();
		ActionListener al = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				//timer.stop();
				timer.speedupby(10);
				//timer.slowDown();
			}
		};
		Timer newt = new Timer(400*6,al);
		newt.start();
		while(true){}

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{ 
		String s="";
		for (int i = 0; i < (x); i++)
		{
			s += "*";
		}
		System.out.println(s);
		x = (x+1) % 6;
		
		
	}
}