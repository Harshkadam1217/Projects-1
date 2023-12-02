package com.game;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
                                                                                

import javax.swing.*;
import javax.swing.Timer;
public class SnakeGame extends JPanel implements ActionListener,KeyListener{
	private class Tile{
		int x,y;
		Tile(int x,int y){
			this.x=x;
			this.y=y;
		}
	}
	int boardwidth;
	int boardHeight;
	int tileSize=25;
	Random random;
	
	//Game Logic
	Timer gameLoop;
	int velocityX,velocityY;
	boolean gameOver=false;
	
	//snake
	Tile snakehead;
	ArrayList<Tile> snakebody;
	
	//food
	Tile food;
	
	SnakeGame(int boardwidth,int boardHeight){
		this.boardHeight=boardHeight;
		this.boardwidth=boardwidth;
		setPreferredSize(new Dimension(this.boardwidth,this.boardHeight));
		setBackground(Color.cyan);
		addKeyListener(this);
		setFocusable(true);

		snakehead=new Tile(2,2);//Default starting place of the snake head
		snakebody=new ArrayList<>();
		
		
		food=new Tile(10,10);
		random =new Random();
		placeFood();
		velocityX=1;
		velocityY=0;
		
		gameLoop=new Timer(100, this);
		gameLoop.start();
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	public void draw(Graphics g) {
		//Grid Size
		//(x1,y1,x2,y2)
		//drawLine function Draws a line, using the current color, between the points (x1, y1) and (x2, y2)in this graphics context's coordinate system.
//		for(int i=0;i<boardwidth/tileSize;i++) {
//			g.drawLine(i*tileSize, 0, i*tileSize, boardHeight);
//			g.drawLine(0, i*tileSize, boardwidth, i*tileSize);
//		}
		
		//food
		g.setColor(Color.red);//Set the color of the snake
		g.fillRoundRect(food.x*tileSize, food.y*tileSize, tileSize, tileSize,tileSize, tileSize);//we will create the shape of circle
		
		//Snake head
		g.setColor(Color.black);//Set the color of the snake
		g.fillRoundRect(snakehead.x*tileSize, snakehead.y*tileSize, tileSize, tileSize,tileSize, tileSize);//we will create the shape of circle
		
		//snake body
		for (int i = 0; i < snakebody.size(); i++) {
			Tile snakePart=snakebody.get(i);
			g.setColor(Color.BLUE);//Set the color of the snake
			g.fillRoundRect(snakePart.x*tileSize, snakePart.y*tileSize, tileSize, tileSize,tileSize, tileSize);//we will create the shape of circle
		}
		
		//Score
		g.setFont(new Font("TimesNewRoman",Font.BOLD,16));
		if(gameOver) {
			g.setColor(Color.red);
			g.drawString("Game Over : "+String.valueOf(snakebody.size()),tileSize-16,tileSize);
		}
		else {
			g.drawString("Score : "+snakebody.size(), tileSize-16, tileSize);
		}
		
		
		
	}
	
	
	
	
	public void placeFood() {
		food.x=random.nextInt(24);
		food.y=random.nextInt(24);
	}
	
	
	public boolean collisions(Tile tile1,Tile tile2) {
		return tile1.x == tile2.x && tile1.y==tile2.y;
		
	}
	
	
	
	
	
	public void move(int velocityX,int velocityY) {
		//eat food
		if(collisions(snakehead,food)) {
			snakebody.add(new Tile(food.x,food.y));
			placeFood();
		}
		
		//Snake Body
		for(int i=snakebody.size()-1;i>=0;i--) {
			Tile snakePart=snakebody.get(i);
			if(i==0) {
				snakePart.x=snakehead.x;
				snakePart.y=snakehead.y;
			}
			else {
				Tile prevSnakepart=snakebody.get(i-1);
				snakePart.x=prevSnakepart.x;
				snakePart.y=prevSnakepart.y;
			}
		}
		
		
		//snake head
		snakehead.x+=velocityX;
		snakehead.y+=velocityY;
		
		//game Over Conditions
		for(int i=0;i<snakebody.size();i++) {
			Tile snakePart=snakebody.get(i);
			if(collisions(snakehead,snakePart)) {
				gameOver=true;
			}
		}
		if(snakehead.x*tileSize==0 || snakehead.x*tileSize==boardwidth || snakehead.y*tileSize==0 || snakehead.x*tileSize==boardHeight) gameOver=true;
		
	}
	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {
		move(velocityX, velocityY);
		repaint();
		if(gameOver) gameLoop.stop();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_UP  && velocityY!=1) {
			velocityX=0;
			velocityY=-1;
		}
		 if(e.getKeyCode()==KeyEvent.VK_DOWN && velocityY!=-1) {
			velocityX=0;
			velocityY=1;
		}
		 if(e.getKeyCode()==KeyEvent.VK_LEFT && velocityX!=1) {
			velocityX=-1;
			velocityY=0;
		}
		 if(e.getKeyCode()==KeyEvent.VK_RIGHT && velocityX!=-1) {
			velocityX=1;
			velocityY=0;
		}
		
	}
	//Do not need these
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	
}
