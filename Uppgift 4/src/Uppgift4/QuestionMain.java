package Uppgift4;

import javax.swing.JOptionPane;

public class QuestionMain {
	
	static String[] question = 
		{"Vilken bokstav kommer efter A?", 
		 "Vilken siffra b�rjar Pi med",
		 "Vilken f�rg har en r�d bil?",
		 "Vilket �r det tredje, femte eller sjunde bokstaven i alphabetet",
		 "Vad �r svaret p� livets alla fr�gor?"};
	static String[][] questionAnswer = 
		{{"b"},
		{"3"},
		{"r�d"},
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
			JOptionPane.showMessageDialog(null, "Antalet fr�gor st�mmer inte med antalet svar!\nAvslutar programmet.");
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
		}while(JOptionPane.showConfirmDialog(null, "Du har nu svarat p� alla fr�gor och hade " + points + "/" + numb().length +" r�tt!\n\nVill du k�ra programmet igen?")==0);
		
	}

}
