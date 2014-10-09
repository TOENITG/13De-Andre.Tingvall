package uppgift2;

import javax.swing.JOptionPane;

public class mainFiskFis {

	public static void main(String[] args) {
		
		String[] aStr = new String[5]; 
		String outP = "";
		
		for(int i = 0; i < 5; i++){
			aStr[i] = JOptionPane.showInputDialog("Inmatning " + (i + 1));
			outP += " " + aStr[i];
			
			JOptionPane.showMessageDialog( null, outP, outP, JOptionPane.INFORMATION_MESSAGE);
		}
		
		System.exit(0);
	}

}
