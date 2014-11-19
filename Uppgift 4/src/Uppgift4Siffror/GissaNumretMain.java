package Uppgift4Siffror;

import javax.swing.JOptionPane;

public class GissaNumretMain {
	
	public static void main(String[] args) {
		
		String quest;
		int guess;
		int svar;
		do{
			svar = (int)(Math.random()*100);
			guess = -1;
			quest = "Gissa talet som datorn tänker på mellan 1 till 100";
			do{
				try{
					guess = Integer.valueOf(JOptionPane.showInputDialog(quest));
				}catch(java.lang.NumberFormatException e){
					JOptionPane.showMessageDialog(null,"Var snäll och skriv in ett tal");
				}
				if(guess-50>svar||guess+50<svar){
					quest="ISKALLT";
				}else if(guess-25>svar||guess+25<svar){
					quest="Kallt";
				}else if(guess-15>svar||guess+15<svar){
					quest="Tempererat";
				}else if(guess-5>svar||guess+5<svar){
					quest="Varmt";
				}else{
					quest="JÄTTEVARMT";
				}
				
				quest += ", gissa med ett ";
				if(guess<svar)quest+="högre tal!";
				if(guess>svar)quest+="lägre tal!";
			}while(guess!=svar);
		}while(JOptionPane.showConfirmDialog(null, "Du gissade rätt!\n\nVill du köra programmet igen?")==0);
		
	}

}
