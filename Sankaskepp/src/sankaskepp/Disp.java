package sankaskepp;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class Disp {
	
	static Display disp = new Display();
	static Shell shell = new Shell(SWT.CLOSE | SWT.TITLE | SWT.MIN);
	static FormLayout layout = new FormLayout();

	static Button[][] butt = new Button[10][10];
	
	public static void displayStart(){
		
		int buttonPosition = 10;
		int buttonSize = 35;
		int buttonSpace = 25;
		
		shell.setSize(butt.length*buttonPosition+butt.length*buttonSpace,
				butt.length*buttonPosition+butt.length*buttonSpace + 22);
		
		FormData[][] formData = new FormData[10][10];
		for(int x = 0; x < butt.length; x++){
			for(int y = 0; y < butt[x].length; y++){
				butt[x][y] = new Button(shell, SWT.PUSH);
				try{
					butt[x][y].setImage(mapImage(x, y));
				}catch(java.io.FileNotFoundException e){
					butt[x][y].setText("File not found.");
				}
				final int bx = x;
				final int by = y;
				butt[x][y].addListener(SWT.Selection, new Listener(){
					public void handleEvent(Event e) {
						if(e.type == SWT.Selection){
							System.out.println(bx + ", " + by);
							State.map(bx, by, 1);
							try{
								butt[bx][by].setImage(mapImage(bx, by));
							}catch(java.io.FileNotFoundException e1){
								butt[bx][by].setText("File not found.");
							}
						}
					}				
				});
				
				formData[x][y] = new FormData();
				formData[x][y].top = new FormAttachment(0, y*buttonPosition+buttonSpace*y);
				formData[x][y].left = new FormAttachment(0, x*buttonPosition+buttonSpace*x);
				formData[x][y].bottom = new FormAttachment(0, y*buttonPosition+buttonSize+buttonSpace*y);
				formData[x][y].right = new FormAttachment(0, x*buttonPosition+buttonSize+buttonSpace*x);
			
			butt[x][y].setLayoutData(formData[x][y]);
			}	
		}
		
		shell.setLayout(layout);
		shell.open();
	}
	
	public static Image mapImage(int x, int y) throws java.io.FileNotFoundException{
		Image image;
		switch (State.map(x, y)){
		case 0:
			image = new Image(disp, State.path("Water.png"));
			return image;
		case 1:
			image = new Image(disp, State.path("Hit.png"));
			return image;
		case 2:
			image = new Image(disp, State.path("Miss.png"));
			return image;
		case 3:
			image = new Image(disp, State.path("Ship.png"));
			return image;
		}
		return null;
	}
	
	public static void runDisplay(){
		while(!shell.isDisposed()){
			if (!disp.readAndDispatch()) disp.sleep();
		}
		disp.dispose();
		System.exit(0);
	}
}
