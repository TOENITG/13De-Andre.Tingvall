package uppgift2;

import javax.swing.JOptionPane;

public class mainFiskFis {

	public static void main(String[] args) {
		
		String[] aStr = new String[5];  //sparar all ny text i en position i arrayen
		String outP = ""; //strängen som skrivs ut
		
		for(int i = 0; i < 5; i++){ //loopen som gör allt
			aStr[i] = JOptionPane.showInputDialog("Inmatning " + (i + 1)); // frågar efter nästa sträng
			outP += " " + aStr[i]; //för över strängen till outP
			
			JOptionPane.showMessageDialog( null, outP, outP, JOptionPane.INFORMATION_MESSAGE); //skriver ut strängen
		}
		
		System.exit(0);
	}

}
