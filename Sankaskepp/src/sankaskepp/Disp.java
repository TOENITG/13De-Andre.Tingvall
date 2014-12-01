package sankaskepp;

import org.eclipse.swt.SWT;
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
	static Shell shell = new Shell();
	static FormLayout layout = new FormLayout();

	static Button[][] butt = new Button[10][10];
	
	public static void displayStart(){
		
		FormData[][] formData = new FormData[10][10];
		for(int x = 0; x < butt.length; x++){
			for(int y = 0; y < butt[x].length; y++){
				butt[x][y] = new Button(shell, SWT.PUSH);
				butt[x][y].setText("ha");
				butt[x][y].addListener(SWT.Selection, new Listener(){
					public void handleEvent(Event e) {
						if(e.type == SWT.Selection){
							System.out.println("ha");
						}
					}				
				});

				formData[x][y] = new FormData();
				try{
					formData[x][y].top = new FormAttachment(butt[x][y+1], 2);
				}catch(java.lang.ArrayIndexOutOfBoundsException e){
					formData[x][y].top = new FormAttachment(0, 2);
				}
				try{
					formData[x][y].left = new FormAttachment(butt[x-1][y], 2);
				}catch(java.lang.ArrayIndexOutOfBoundsException e){
					formData[x][y].left = new FormAttachment(0, 2);
				}
				try{
					formData[x][y].right = new FormAttachment(butt[x+1][y], 2);
				}catch(java.lang.ArrayIndexOutOfBoundsException e){
					formData[x][y].right = new FormAttachment(100, 2);
				}
				try{
					formData[x][y].bottom = new FormAttachment(butt[x][y-1], 2);
				}catch(java.lang.ArrayIndexOutOfBoundsException e){
					formData[x][y].bottom = new FormAttachment(100, 2);
				}
				butt[x][y].setLayoutData(formData[x][y]);
			}
		}
		shell.setLayout(layout);
		shell.open();
	}
	
	public static void runDisplay(){
		while(!shell.isDisposed()){
			if (!disp.readAndDispatch()) disp.sleep();
			
		}
		disp.dispose();
		System.exit(0);
	}
}
