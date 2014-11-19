package Uppgift4;

import javax.swing.JOptionPane;

public class QuestionMain {
	
	static String[] question = 
		{"Vilken bokstav kommer efter A?", 
		 "Vilken siffra börjar Pi med",
		 "Vilken färg har en röd bil?",
		 "Vilket är det tredje, femte eller sjunde bokstaven i alphabetet",
		 "Vad är svaret på livets alla frågor?"};
	static String[][] questionAnswer = 
		{{"b"},
		{"3"},
		{"röd"},
		{"c","e","g"},
		{"42"}};
	
	public static boolean askQ(int i){
		String s = JOptionPane.showInputDialog(question[i]);
		for(int i1 = 0; i1 < questionAnswer[i].length; i1++)
			if(s.equalsIgnoreCase(questionAnswer[i][i1]))return true;
		return false;
	}
	
	public static String[] numb(){
		if (!(question.length == questionAnswer.length)){
			JOptionPane.showMessageDialog(null, "Antalet frågor stämmer inte med antalet svar!\nAvslutar programmet.");
			System.exit(0);
		}
		return question;
	}
	
	public static void main(String[] args) {
		int points;
		do{
			points = 0;
			for(int i = 0; i < numb().length; i++){
				if(askQ(i))points++;
			}
		}while(JOptionPane.showConfirmDialog(null, "Du har nu svarat på alla frågor och hade " + points + "/" + numb().length +" rätt!\n\nVill du köra programmet igen?")==0);
		
	}

}
