package com.connectfour;
import java.util.Arrays;
import java.util.Scanner;

public class ConnectFourGame {
	private static final int ROWS = 6;
    private static final int COLUMNS = 7;
    private static final char EMPTY = ' ';
    private static final char PLAYER1 = 'X';
    private static final char PLAYER2 = 'O';

    private char[][] board;
    private boolean player1Turn;

    public ConnectFourGame() {
        board = new char[ROWS][COLUMNS];
        for (char[] row : board) {
            Arrays.fill(row, EMPTY);
        }
        player1Turn = true;
    }

    public void printBoard() {
        for (char[] row : board) {
            for (char cell : row) {
                System.out.print("| " + cell + " ");
            }
            System.out.println("|");
        }
        for (int i = 0; i < COLUMNS * 4 + 1; i++) {
            System.out.print("-");
        }
        System.out.println();
        for (int i = 0; i < COLUMNS; i++) {
            System.out.print("  " + (i + 1) + " ");
        }
        System.out.println();
    }

    public boolean dropToken(int col) {
        if (col < 0 || col >= COLUMNS) {
            System.out.println("Invalid column number!");
            return false;
        }

        for (int i = ROWS - 1; i >= 0; i--) {
            if (board[i][col] == EMPTY) {
                board[i][col] = (player1Turn) ? PLAYER1 : PLAYER2;
                return true;
            }
        }
        System.out.println("Column is full!");
        return false;
    }

    public boolean checkWinner() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                char player = board[row][col];
                if (player != EMPTY) {
                    // Check horizontally
                    if (col + 3 < COLUMNS &&
                            player == board[row][col + 1] &&
                            player == board[row][col + 2] &&
                            player == board[row][col + 3]) {
                        return true;
                    }
                    // Check vertically
                    if (row + 3 < ROWS &&
                            player == board[row + 1][col] &&
                            player == board[row + 2][col] &&
                            player == board[row + 3][col]) {
                        return true;
                    }
                    // Check diagonally (positive slope)
                    if (col + 3 < COLUMNS && row + 3 < ROWS &&
                            player == board[row + 1][col + 1] &&
                            player == board[row + 2][col + 2] &&
                            player == board[row + 3][col + 3]) {
                        return true;
                    }
                    // Check diagonally (negative slope)
                    if (col + 3 < COLUMNS && row - 3 >= 0 &&
                            player == board[row - 1][col + 1] &&
                            player == board[row - 2][col + 2] &&
                            player == board[row - 3][col + 3]) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
    	ConnectFourGame game = new ConnectFourGame();
        Scanner scanner = new Scanner(System.in);

        boolean gameOver = false;

        while (!gameOver) {
            game.printBoard();
            int col;
            if (game.player1Turn) {
                System.out.print("Player 1 (X) - Enter column number (1-7): ");
            } else {
                System.out.print("Player 2 (O) - Enter column number (1-7): ");
            }

            col = scanner.nextInt() - 1;

            if (game.dropToken(col)) {
                if (game.checkWinner()) {
                    game.printBoard();
                    if (game.player1Turn) {
                        System.out.println("Player 1 (X) wins!");
                    } else {
                        System.out.println("Player 2 (O) wins!");
                    }
                    gameOver = true;
                } else {
                    game.player1Turn = !game.player1Turn;
                }
            }
            // Check for a tie
            boolean isTie = true;
            for (char[] row : game.board) {
                for (char cell : row) {
                    if (cell == game.EMPTY) {
                        isTie = false;
                        break;
                    }
                }
                if (!isTie) {
                    break;
                }
            }
            if (isTie) {
                game.printBoard();
                System.out.println("It's a tie!");
                gameOver = true;
            }

        }
        scanner.close();
    }
}