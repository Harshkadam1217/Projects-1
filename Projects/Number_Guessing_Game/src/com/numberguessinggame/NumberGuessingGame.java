package com.numberguessinggame;
import java.util.*;
public class NumberGuessingGame {

	public static void main(String[] args) {
		System.out.println("\t\t\t\t\t Guess the Game\t\t");
		Scanner sc=new Scanner(System.in);
		String play="yes";
		
		// While loop is to determine if we are going to play the game again
		
		while(play.equals("yes")) {
			System.out.println("Enter The Number You want to guess from 0 to :");
			int range=sc.nextInt();
			if(range!=0) {
				Random r=new Random();
				int randnum=r.nextInt(range);
				int guess=-1;
				int tries=0;
				
				
				//While loop to check if the game is over
				while(guess!=randnum) {
					System.out.println("Guess a number between 0 and " + range + " : ");
					guess = sc.nextInt();
					tries++;
					if(guess==randnum) {
						System.out.println("Awesome! you guessed the  number!");
						System.out.println("It only took you " + tries +" guesses!");
						System.out.println("Would you like to play again Yes or No :");
						play=sc.next().toLowerCase();
					}
					else if(guess<randnum) {
						System.out.println("Your Guess is too Low try again.");
					}
					else {
						System.out.println("Your Guess is too high, try again.");
					}
				}
				if(play.equals("no")) System.out.println("Thank You for Playing, Lets play again");
			}
			else {
				System.out.println("Enter Number which is Greater than 0 ");
			}
			
		}
		sc.close();
	}

}
