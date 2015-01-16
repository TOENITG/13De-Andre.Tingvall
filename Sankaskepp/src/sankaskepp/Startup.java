package sankaskepp;

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
import org.eclipse.swt.widgets.Text;

public class Startup {
	
	static boolean booleanOfAI = false;
	static boolean closedWindow = false;
	
	public static boolean initialize(){
		
		String[] fieldHints = {"Board size:",
				"Number of players:",
				"Number of ships:"};
		String[] fieldValues = {"10",
				"2",
				"5"};
		
		Display disp = new Display();
		Shell shell = new Shell(SWT.CLOSE | SWT.TITLE | SWT.MIN);
		FormLayout layout = new FormLayout();

		shell.setSize(400,190);
		shell.setLayout(layout);
		
		Button setAI = new Button(shell, SWT.CHECK);
		setAI.setText("AI opponent");
		setAI.setToolTipText("Makes the first player controlled by the computer.");
		setAI.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e) {
				if(e.type == SWT.Selection){
					if(booleanOfAI){
						booleanOfAI=false;
					}else{
						booleanOfAI=true;
					}
				}
			}				
		});
		
		Text[] inputField = new Text[fieldHints.length];
		Label[] inputHint = new Label[fieldHints.length];
		FormData[][] inputFormData = new FormData[fieldHints.length][2];
		
		for(int i = 0; i < fieldHints.length; i++){
			
			inputHint[i] = new Label(shell, SWT.NONE);
			inputHint[i].setText(fieldHints[i]);
			
			inputField[i] = new Text(shell, SWT.BORDER);
			inputField[i].setText(fieldValues[i]);
			
			
			inputFormData[i][0] = new FormData();
			try{
				inputFormData[i][0].top = new FormAttachment(inputHint[i-1],10);
			}catch(java.lang.ArrayIndexOutOfBoundsException e){
				inputFormData[i][0].top = new FormAttachment(0,20);
			}
			inputFormData[i][0].left = new FormAttachment(0,20);
			inputHint[i].setLayoutData(inputFormData[i][0]);
			
			inputFormData[i][1] = new FormData();
			inputFormData[i][1].top = new FormAttachment(inputHint[i],0,SWT.TOP);
			inputFormData[i][1].left = new FormAttachment(inputHint[i],10);
			inputFormData[i][1].right = new FormAttachment(100,-20);
			inputField[i].setLayoutData(inputFormData[i][1]);
		}
		
		Button startGame = new Button(shell, SWT.PUSH);
		startGame.setText("Start a new game");
		startGame.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e) {
				if(e.type == SWT.Selection){
					try{
						State.fieldSize = Integer.valueOf(inputField[0].getText());
						State.maxPlayers = Integer.valueOf(inputField[1].getText());
						State.playerIsAlive = new boolean[State.maxPlayers];
						for(int i = 0; i < State.maxPlayers; i++){
							State.playerIsAlive[i]=true;
						}
						State.gameBoard  = new int[State.fieldSize][State.fieldSize][State.maxPlayers];
						State.maxShips = Integer.valueOf(inputField[2].getText());
						
						State.opponentIsAI = booleanOfAI;

//						State.newShip(0, 5, 5, 2, 0);
//						State.newShip(0, State.fieldSize-1, State.fieldSize-1, 2, 0);
//						State.newShip(1, 5, 5, 2, 0);
//						State.newShip(1, 5, 5, 3, 1);
//						State.newShip(1, 2, 2, 3, 0);
						
						closedWindow = true;
						shell.dispose();
					}catch(java.lang.NumberFormatException ef){
						shell.setText("Please enter only numbers!");
					}
				}
			}				
		});
		
		FormData startFormData = new FormData();
		startFormData.bottom = new FormAttachment(100,-20);
		startFormData.right = new FormAttachment(100,-20);
		startGame.setLayoutData(startFormData);
		
		FormData vsAIFormData = new FormData();
		vsAIFormData.bottom = new FormAttachment(100,-20);
		vsAIFormData.left = new FormAttachment(0,20);
		setAI.setLayoutData(vsAIFormData);
		
		shell.setText("Enter start parameters");
		shell.open();
		
		while(!shell.isDisposed()){
			if (!disp.readAndDispatch()) disp.sleep();
		}
		shell.dispose();
		
		return closedWindow;
	}
	
	public static boolean shipPlacementManager(){
		closedWindow = false;
		for(int i = 0; i < State.maxPlayers; i++){
			if(Startup2.shipPlacement(i))
				return false;
		}
		
		return true;
	}
	
	
}
