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
			quest = "Gissa talet som datorn t�nker p� mellan 1 till 100";
			do{
				try{
					guess = Integer.valueOf(JOptionPane.showInputDialog(quest));
				}catch(java.lang.NumberFormatException e){
					JOptionPane.showMessageDialog(null,"Var sn�ll och skriv in ett tal");
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
					quest="J�TTEVARMT";
				}
				
				quest += ", gissa med ett ";
				if(guess<svar)quest+="h�gre tal!";
				if(guess>svar)quest+="l�gre tal!";
			}while(guess!=svar);
		}while(JOptionPane.showConfirmDialog(null, "Du gissade r�tt!\n\nVill du k�ra programmet igen?")==0);
		
	}

}
