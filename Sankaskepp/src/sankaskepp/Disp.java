package sankaskepp;

import java.io.FileNotFoundException;

import image.ImageLocation;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class Disp {
	
	static Display disp = new Display();
	static Shell shell = new Shell(SWT.CLOSE | SWT.TITLE | SWT.MIN);
	static FormLayout layout = new FormLayout();

	static Button buttNext = new Button(shell, SWT.PUSH);
	static Button[][] butt = new Button[State.fieldSize][State.fieldSize];
	static Label[][] dinPlan = new Label[State.fieldSize][State.fieldSize];
	static Image[] img = {
		ImageLocation.load("Water.png"),
		ImageLocation.load("Miss.png"),
		ImageLocation.load("Hit.png"),
		ImageLocation.load("Ship.png"),
		ImageLocation.load("ShipUnsunk.png")};


	public static void displayStart(){ //fixar all nödvändig imformation för att rätt saker ska visas på skärmen
		
		//För lättare modifiering utav storleken på knapparna
		int buttonPosition = 10;
		int buttonSize = 35;
		int buttonSpace = 25;
		int mapSpace = 25;
		
		shell.setText(String.valueOf(State.player+1));
		
		shell.setSize((butt.length*buttonPosition+butt.length*buttonSpace)*2+mapSpace+5,
				butt.length*buttonPosition+butt.length*buttonSpace + 30);
		FormData[][] formData = new FormData[State.fieldSize][State.fieldSize];
		FormData[][] formDataDin = new FormData[State.fieldSize][State.fieldSize];
		for(int x = 0; x < butt.length; x++){
			for(int y = 0; y < butt[x].length; y++){
				
				//Skapandet av knapparna
				butt[x][y] = new Button(shell, SWT.PUSH);
				try{
					butt[x][y].setImage(mapImage(State.player,x, y,false));
				}catch(java.io.FileNotFoundException e){
					butt[x][y].setText("File not found.");
				}
				final int bx = x;
				final int by = y;
				butt[x][y].addListener(SWT.Selection, new Listener(){
					public void handleEvent(Event e) {
						if(e.type == SWT.Selection){
							if(State.map(State.pl(State.player), bx, by)!=1){
								//System.out.println("X:" + bx + ", Y:" + by + ", Current player:" + State.player + ", Next player:" + State.pl(State.player));
								State.fire(bx,by);
								if(State.isShip(State.pl(State.player), bx, by)!=-1){
									if(State.allIsSunk()){
										updateView();
										winWindow();
									}else{
										updateView();
									}
								}else{
									State.pl(-1);
									hideView();
									buttNext.setText("Player " + (State.player+1) + " it's your turn!");
									buttNext.setVisible(true);
								}
								
							}
						}
					}				
				});
				
				formData[x][y] = new FormData();
				formData[x][y].top = new FormAttachment(0, y*buttonPosition+buttonSpace*y); //knappplacering
				formData[x][y].left = new FormAttachment(0, x*buttonPosition+buttonSpace*x);
				formData[x][y].bottom = new FormAttachment(0, y*buttonPosition+buttonSize+buttonSpace*y);
				formData[x][y].right = new FormAttachment(0, x*buttonPosition+buttonSize+buttonSpace*x);
			
				butt[x][y].setLayoutData(formData[x][y]);
				
				//skapandet av din plan som �r till h�ger
				
				dinPlan[x][y] = new Label(shell,SWT.BORDER);
				
				
				try{
					dinPlan[x][y].setImage(mapImage(State.player,x,y,true));
				} catch (FileNotFoundException e) {
					dinPlan[x][y].setText("File not found.");
				}
				
				formDataDin[x][y] = new FormData();
				formDataDin[x][y].top = new FormAttachment(0, y*buttonPosition+buttonSpace*y); //knappplacering
				formDataDin[x][y].left = new FormAttachment(0, x*buttonPosition+buttonSpace*x+mapSpace
						+butt.length*buttonPosition+butt.length*buttonSpace);
				formDataDin[x][y].bottom = new FormAttachment(0, y*buttonPosition+buttonSize+buttonSpace*y);
				formDataDin[x][y].right = new FormAttachment(0, x*buttonPosition+buttonSize+buttonSpace*x+mapSpace
						+butt.length*buttonPosition+butt.length*buttonSpace);
				
				dinPlan[x][y].setLayoutData(formDataDin[x][y]);
				
			}	
		}
		//skapar knappen som visas mellan varje g�ng n�sta spelare ska spela
		buttNext.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e) {
				if(e.type == SWT.Selection){
					buttNext.setVisible(false);
					updateView();
				}
			}				
		});
		FormData formDataNext = new FormData();
		formDataNext.top = new FormAttachment(0, 0);
		formDataNext.left = new FormAttachment(0, 0);
		formDataNext.bottom = new FormAttachment(0, butt.length*buttonPosition+butt.length*buttonSpace);
		formDataNext.right = new FormAttachment(0, butt.length*buttonPosition+butt.length*buttonSpace);
		buttNext.setLayoutData(formDataNext);
		buttNext.setVisible(false);
		
		updateView();
		shell.setLayout(layout);
		shell.open();
	}
	
	public static void updateView(){
		for(int x = 0; x < State.fieldSize; x++){
			for(int y = 0; y < State.fieldSize; y++){
				
				try{
					butt[x][y].setImage(mapImage(State.pl(State.player),x, y,false));
				}catch(java.io.FileNotFoundException e1){
					butt[x][y].setText("File not found.");
				}
				
				
				try{
					dinPlan[x][y].setImage(mapImage(State.player,x,y,true));
				} catch (FileNotFoundException e) {
					dinPlan[x][y].setText("File not found.");
				}
				
				butt[x][y].setVisible(true);
				dinPlan[x][y].setVisible(true);
			}
		}
	}
	
	public static void winWindow(){//rutan som poppar upp n�r en spalare har s�nk alla motst�ndarens skepp
		
		int xSize = 175;
		int ySize = 150;
		Shell winShell = new Shell(disp/*, SWT.CLOSE | SWT.TITLE | SWT.MIN*/);
		winShell.setText("Spelare " + (State.player+1) + " vann!");
		
		Label tex = new Label(winShell, SWT.NONE);
		tex.setText("Spelare " + (State.player+1) + " vann!");
		tex.setSize(5,5);

		Button okButton = new Button(winShell,SWT.NONE);
		okButton.setText("Quit");
		okButton.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e) {
				if(e.type == SWT.Selection){
					winShell.dispose();
					shell.dispose();
					System.exit(0);
				}
			}				
		});
		
		FormData formData = new FormData();
		formData.top = new FormAttachment(20, 0);
		formData.left = new FormAttachment(20, 0);
		formData.bottom = new FormAttachment(40, 0);
		formData.right = new FormAttachment(100, 0);
		tex.setLayoutData(formData);
		
		FormData formData2 = new FormData();
		formData2.top = new FormAttachment(60,0 );
		formData2.left = new FormAttachment(20, 0);
		formData2.bottom = new FormAttachment(100, 0);
		formData2.right = new FormAttachment(80, 0);
		okButton.setLayoutData(formData2);
		
		FormLayout lay = new FormLayout();
		winShell.setLocation(shell.getLocation().x+shell.getSize().x/2-xSize/2,shell.getLocation().y+shell.getSize().y/2-ySize/2);
		winShell.setLayout(lay);
		winShell.setSize(xSize,ySize);
		winShell.open();
		winShell.layout();
	}
	
	public static void hideView(){
		for(int x = 0; x < State.fieldSize; x++){
			for(int y = 0; y < State.fieldSize; y++){
				butt[x][y].setVisible(false);
				dinPlan[x][y].setVisible(false);
			}
		}
	}
	
	public static Image mapImage(int p, int x, int y, boolean showFriendlyShip) throws java.io.FileNotFoundException{
		if(State.map(p, x, y)==1){ //Om spelaren har skjutit på plats x,y
			if(State.isShip(p, x, y)!=-1){//Om det finns ett skepp på x,y
				if(State.isSunk(p, State.isShip(p, x, y))){//om skeppet på x,y är sänkt
					return img[3];
				}else
					return img[2];
			}else
				return img[1];
		}else{
			if(State.isShip(p, x, y)!=-1 && showFriendlyShip){
				return img[4];
			}else
				return img[0];
		}
	}
	
	public static void runDisplay(){
		while(!shell.isDisposed()){
			if (!disp.readAndDispatch()) disp.sleep();
		}
		disp.dispose();
		System.exit(0);
	}
}
