package rps;
import java.util.*;

public class Rock_Paper_Scissors {

	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		String rps[]= {"Rock","Paper","Scissors"};
		System.out.println("\t\t\tWelcome to Rock,Paper,Scissors Game");
		String play="yes";
		while(play.equals("yes")) {

			String computerMove=rps[new Random().nextInt(rps.length)];
			System.out.println("Select Which Move Would you like to play :- 1)Rock 2)Paper 3)Scissors");
			int a=sc.nextInt();
			switch(a) {
			case 1:if(computerMove.equals("Paper")) System.out.println("Sorry but Paper beats Rock");
			else if(computerMove.equals("Rock")) System.out.println("Bummer Rock can't beat Rock");
			else System.out.println("Wow you won, Rock Beats Scissors");
			break;
			case 2:if(computerMove.equals("Paper")) System.out.println("Bummer Paper can't beat Paper");
			else if(computerMove.equals("Rock")) System.out.println("Wow you won, Paper beats Rock");
			else System.out.println("Sorry but Paper can't beat Scissors");
			break;
			case 3:if(computerMove.equals("Paper")) System.out.println("Wow you won, Scissors beat Paper");
			else if(computerMove.equals("Rock")) System.out.println("Sorry but Scissors can't beat Rock");
			else System.out.println("Bummer Scissors can't beat Scissors");
			break;
			default:System.out.println("Sorry but give the value in between 1 and 3");break;
			}
			System.out.println("So would you like to play again ? ");play=sc.next().toLowerCase();
		}
		System.out.println("It was Good Playing with you, Let's play again soon ");
	}

}
