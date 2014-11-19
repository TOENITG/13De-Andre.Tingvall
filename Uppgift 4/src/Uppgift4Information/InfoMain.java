package Uppgift4Information;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class InfoMain {
	
	static String[] alternativ ={
		"Star Trek: The Motion Picture",
		"Star Trek II: The Wrath of Khan",
		"Star Trek III: The Search for Spock",
		"Star Trek IV: The Voyage Home",
		"Star Trek V: The Final Frontier",
		"Star Trek VI: The Undiscovered Country",
		"Star Trek: Generations",
		"Star Trek: First Contact",
		"Star Trek: Insurrection",
		"Star Trek: Nemesis",
		"Star Trek",
		"Star Trek Into Darkness"};
	
	static Display disp = new Display();
	static Shell shell = new Shell(disp, SWT.CLOSE | SWT.TITLE | SWT.MIN);
	static Shell[] cs = new Shell[alternativ.length];
	static Color colW = disp.getSystemColor(SWT.COLOR_WHITE);
	static Color colB = disp.getSystemColor(SWT.COLOR_BLACK);
	
	static FillLayout fLay = new FillLayout();
	static Button[] butt = new Button[alternativ.length];
	
	public static void run(){
		shell.pack();
		shell.open();
		while(!shell.isDisposed()){
			if (!disp.readAndDispatch()) disp.sleep();
		}
		disp.dispose();
	}
	
	public static void displayStartup(){
		
		shell.setText("Hello puny human!");
		shell.setBackground(colW);
		fLay.type = SWT.VERTICAL;
		shell.setLayout(fLay);
		
		for(int i = 0; i < alternativ.length;i++){
			butt[i] = new Button(shell, SWT.PUSH);
			butt[i].setText(alternativ[i]);
			butt[i].setBackground(colW);
			butt[i].setForeground(colB);
			butt[i].addListener(SWT.Selection, new Listener(){
				public void handleEvent(Event e) {
					Shell cs = new Shell(shell);
				}
			});
		}
		
		
		
		
	}

	public static void main(String[] args) {
		displayStartup();
		run();
	}

}
