package Uppgift2;

import java.util.Scanner;

public class NumberBleerghh {


	static double[] inP = {0, 0};
	static Scanner scan = new Scanner(System.in);
	
	public static void input(int i){ //ser till at inmatningen är korrekt

		while(true){ 
			try{
				inP[i] = Double.valueOf((scan.next().toString().replaceAll(",", ".")));
				break;
			}catch(java.lang.NumberFormatException e){
				System.out.println("Felaktig input, försök igen med siffror");
			}
		}
	}
	public static void main(String[] args) {
		
		@SuppressWarnings("resource")
		String[] typ = {"Addition; ", "Subtraktion; ","Multiplikation; ","Division; "};
		
		System.out.println("Ge mig 2 tal, skriv nu första talet!");
		
		input(0);
		
		for(int i = 0; i < 4; i++){ //Antalet par som ska köras igenom
			
			System.out.println("Skriv ytterligare ett tal!");
			
			input(1);
			
			switch (i){
			case 0:
				inP[0] += inP[1];
				break;
			case 1:
				inP[0] -= inP[1];
				break;
			case 2:
				inP[0] *= inP[1];
				break;
			case 3:
				inP[0] /= inP[1];
				break;
			}
			
			System.out.println(typ[i] + inP[0]);
			
		}
		
		System.out.println("Bra jobbat! Programmet är slut");
		
		System.exit(0);
	}
}
