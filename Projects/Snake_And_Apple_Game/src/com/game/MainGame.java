package com.game;
import java.awt.Color;

import javax.swing.*;
public class MainGame {

	public static void main(String[] args) {
		int boardwidth = 600;
		int boardheight=boardwidth;
		JFrame frame=new JFrame("Snake_And_Apple_Game");
		frame.setVisible(true);
		frame.setSize(boardwidth, boardheight);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setBackground(Color.black);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SnakeGame s=new SnakeGame(boardwidth,boardheight);
		frame.add(s);
		frame.pack();
		s.requestFocus();
	}

}
