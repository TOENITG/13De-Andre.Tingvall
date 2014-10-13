package uppgift1;

import javax.swing.JOptionPane;

public class mainBlah {

	public static void main(String[] args) {
		
		String namn = JOptionPane.showInputDialog("Namn:"); //Frågar efter namn
		String pNamn = JOptionPane.showInputDialog("Personnummer:"); // -||- personnummer
		String aNamn = JOptionPane.showInputDialog("Adress:"); // -||- adress
		
		int[] pNum = {Integer.parseInt(pNamn.substring(2,4)),Integer.parseInt(pNamn.substring(4,6))}; 
		//hämtar ut dag och månad ur personnummret
		
		String[] manad = {	"Januari","Februari","Mars", //Namnet på alla månader som ska användas
							"April","Maj","Juni",
							"Juli","Augusti","September", 
							"Oktober","November","December"}; 
		
		JOptionPane.showMessageDialog(null, (	"Välkommen " + namn +  ",\n" + // skriver texten
												"då du fyller den " + pNum[1] + " " + manad[pNum[0]-1] + 
												" kommer vi komma till dig,\n" +
												aNamn + " och fira dig.\n" + 
												"Detta är ett automatiskt meddelande."),
												"Message to puny human named " + namn, 1); // texten i toppen av dialogrutan och typ av meddelande
		
		System.exit(0); // ser till att programmet verkeligen stänger ner sig även om det görs automatiskt
	}
	
}
