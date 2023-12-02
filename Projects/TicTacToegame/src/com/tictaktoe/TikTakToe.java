package com.tictaktoe;

import java.util.Scanner;

public class TikTakToe {
	
	public static void printBoard(char[][] board) {
		for(int row=0;row<board.length;row++) {
			for(int colum=0;colum<board.length;colum++) {
				System.out.print(board[row][colum]+" | ");
			}
			System.out.println();
		}
	}

	private static boolean haveWon(char[][] board, char player) {
		//Check the Rows
		for(int row=0;row<board.length;row++) {
			if(board[row][0]==player && board[row][1]==player && board[row][2]==player)
				return true;
		
		}
		
		//Checking for Coloum
		for(int colum=0;colum<board.length;colum++) {
			if(board[0][colum]==player && board[1][colum]==player && board[2][colum]==player)
				return true;
		
		}
		
		//Check for Diagonal
		if(board[0][0]==player && board[1][1]==player && board[2][2]==player) return true;
		if(board[0][2]==player && board[1][1]==player && board[2][0]==player) return true;
		return false;
	}
	public static void main(String[] args) {
		char[][] board=new  char[3][3];
		for(int row=0;row<board.length;row++) {
			for(int colum=0;colum<board.length;colum++) {
				board[row][colum]=' ';
			}
		}
		char player='X';
		boolean gameOver=false;
		Scanner sc=new Scanner(System.in);
		while(!gameOver) {
			printBoard(board);
			System.out.println("Player "+player+" Enter: ");
			int row = sc.nextInt();
			int colum=sc.nextInt();
			if(board[row][colum]==' ') {
				board[row][colum]=player;
				gameOver=haveWon(board,player);
				if(gameOver) {
					System.out.println("Player "+player+" Has Won");
				}
				else {
					player= (player=='X')?'O':'X';
				}
			}
			else {
				System.out.println("InValid Move there already exist a value");
			}
		}
		printBoard(board);

	}


}
