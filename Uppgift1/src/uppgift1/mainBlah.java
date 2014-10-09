package uppgift1;

import javax.swing.JOptionPane;

public class mainBlah {

	public static void main(String[] args) {
		
		String fNamn = JOptionPane.showInputDialog("Förnamn:");
		String eNamn = JOptionPane.showInputDialog("Efternamn:");
		String pNamn = JOptionPane.showInputDialog("Personnummer:");
		String aNamn = JOptionPane.showInputDialog("Adress:");
		
		int[] pNum = {Integer.parseInt(pNamn.substring(2,4)),Integer.parseInt(pNamn.substring(4,6))};
		
		String[] manad = {"Januari","Februari","Mars","April","Maj","Juni","Juli","Augusti","September","Oktober","November","December"};
		
		
		JOptionPane.showMessageDialog(null, ("Välkommen " + fNamn + " " + eNamn + ", då du fyller den " + pNum[0] + " " + manad[pNum[1]] + " kommer vi komma till dig, " + aNamn + " och fira dig. Detta är ett automatiskt meddelande."), aNamn, 0);
		
	}
	
}
