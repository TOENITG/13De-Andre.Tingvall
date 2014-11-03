package Uppgift2;

import javax.swing.JOptionPane;

public class NumberBleerghh {


	static double[] inP = {0, 0};
	
	public static void message(String s){
		JOptionPane.showMessageDialog(null, s);
	}
	
	public static void input(int i){ //ser till at inmatningen är korrekt

		while(true){ 
			try{
				inP[i] = Double.valueOf((JOptionPane.showInputDialog("").toString().replaceAll(",", ".")));
				break;
			}catch(java.lang.NumberFormatException e){
				message("Felaktig input, försök igen med siffror");
			}
		}
	}
	public static void main(String[] args) {
		
		@SuppressWarnings("resource")
		String[] typ = {"Addition: ", "Subtraktion: ","Multiplikation: ","Division: "};
		
		message("Ge mig 2 tal, skriv nu första talet!");
		
		input(0);
		
		for(int i = 0; i < 4; i++){ //Antalet par som ska köras igenom
			
			message("Skriv ytterligare ett tal!");
			
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
			
			message(typ[i] + inP[0]);
			
		}
		
		message("Bra jobbat! Programmet är slut");
		
		System.exit(0);
	}
}
