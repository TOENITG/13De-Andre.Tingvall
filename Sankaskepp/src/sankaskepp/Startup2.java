package sankaskepp;

import java.io.FileNotFoundException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class Startup2 {
private static int shipDir = 0;
	
	private static Button butt[][] = new Button[State.fieldSize][State.fieldSize];
	
	private static void updateShipView(int player){
		Debug.out("Startup.updateShipView()\tUpdate view");
		for(int x = 0; x < State.fieldSize; x++){
			for(int y = 0; y < State.fieldSize; y++){
				Debug.all("Startup.updateShipView()\tChange tile appearance: "+x+" "+y);
				
				try {
					butt[x][y].setImage(Disp.mapImage(player, x, y, true));
				} catch (FileNotFoundException e) {
					butt[x][y].setText("File not found");
				}
			}
		}
	}
	
	public static boolean shipPlacement(int player){
		
		int buttonPosition = 12;
		int buttonSize = 36;
		int buttonSpace = 25;
		
		Display disp = Display.getCurrent();
		Shell shell = new Shell(SWT.CLOSE | SWT.TITLE | SWT.MIN);
		FormLayout layout = new FormLayout();
		
		shell.setLayout(layout);
		shell.setSize(butt.length*buttonPosition+butt.length*buttonSpace + 107,
				butt.length*buttonPosition+butt.length*buttonSpace + 30);
		
		FormData numbData = new FormData();
		Label numbOfShips = new Label(shell, SWT.NONE);
		numbOfShips.setText("You have placed\n  " + String.valueOf(State.numberOfShips(player)) + " ships");
		numbData.top = new FormAttachment(0,130);
		numbData.right = new FormAttachment(100, -10);
		numbOfShips.setLayoutData(numbData);
		
		FormData dirFormN = new FormData();
		Button setDirN = new Button(shell, SWT.RADIO);
		setDirN.setText("North");
		dirFormN.top = new FormAttachment(0, 50);
		dirFormN.left = new FormAttachment(100, -70);
		setDirN.setLayoutData(dirFormN);
		setDirN.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e) {
				if(e.type == SWT.Selection){
					Debug.out("Startup.shipPlacement(player)\tChanged Startup.shipDir to" + shipDir);
					shipDir = 3;
				}
			}
		});
		
		FormData dirFormS = new FormData();
		Button setDirS = new Button(shell, SWT.RADIO);
		setDirS.setText("South");
		dirFormS.top = new FormAttachment(0, 70);
		dirFormS.left = new FormAttachment(100, -70);
		setDirS.setLayoutData(dirFormS);
		setDirS.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e) {
				if(e.type == SWT.Selection){
					Debug.out("Startup.shipPlacement(player)\tChanged Startup.shipDir to" + shipDir);
					shipDir = 1;
				}
			}
		});
		
		FormData dirFormE = new FormData();
		Button setDirE = new Button(shell, SWT.RADIO);
		setDirE.setText("East");
		setDirE.setSelection(true);
		dirFormE.top = new FormAttachment(0, 90);
		dirFormE.left = new FormAttachment(100, -70);
		setDirE.setLayoutData(dirFormE);
		setDirE.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e) {
				if(e.type == SWT.Selection){
					Debug.out("Startup.shipPlacement(player)\tChanged Startup.shipDir to" + shipDir);
					shipDir = 0;
				}
			}
		});
		
		FormData dirFormW = new FormData();
		Button setDirW = new Button(shell, SWT.RADIO);
		setDirW.setText("West");
		dirFormW.top = new FormAttachment(0, 110);
		dirFormW.left = new FormAttachment(100, -70);
		setDirW.setLayoutData(dirFormW);
		setDirW.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e) {
				if(e.type == SWT.Selection){
					Debug.out("Startup.shipPlacement(player)\tChanged Startup.shipDir to" + shipDir);
					shipDir = 2;
				}
			}
		});
		
		for(int x = 0; x < butt.length; x++){
			for(int y = 0; y < butt[x].length; y++){
				butt[x][y] = new Button(shell, SWT.PUSH);
				try {
					butt[x][y].setImage(Disp.mapImage(player, x, y, true));
				} catch (FileNotFoundException e) {
					butt[x][y].setText("File not found");
				}
				int tx = x;
				int ty = y;
				
				butt[x][y].addListener(SWT.Selection, new Listener(){
					public void handleEvent(Event e) {
						if(e.type == SWT.Selection){
							State.newShip(player, tx, ty, State.nextShipSize(player) ,shipDir);
							updateShipView(player);
							numbOfShips.setText("You have placed\n  " + String.valueOf(State.numberOfShips(player)) + " ships");
							if(!State.maxNumberOfShips(player)){
								Startup.closedWindow = false;
								shell.dispose();
							}
						}
					}
				});
				
				FormData form = new FormData();
				
				form.top = new FormAttachment(0, y*buttonPosition+buttonSpace*y); //knappplacering
				form.left = new FormAttachment(0, x*buttonPosition+buttonSpace*x);
				form.bottom = new FormAttachment(0, y*buttonPosition+buttonSize+buttonSpace*y);
				form.right = new FormAttachment(0, x*buttonPosition+buttonSize+buttonSpace*x);
				
				butt[x][y].setLayoutData(form);
			}
		}
		
		shell.layout();
		shell.open();
		Startup.closedWindow = true;
		while(!shell.isDisposed()){
			if (!disp.readAndDispatch()) disp.sleep();
		}
		shell.dispose();
		return Startup.closedWindow;
	}
}
