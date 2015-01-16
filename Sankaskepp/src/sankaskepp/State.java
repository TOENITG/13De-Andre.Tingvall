package sankaskepp;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class State {
			
	public static int player = 0;//player-1
	public static int maxPlayers = 2;//maxPlayer
	public static int fieldSize = 10;//storleken pÃ¥ planen
	public static int maxShips = 3;
	public static boolean opponentIsAI = false; //Om man kör mot datorn eller ej
	public static boolean[] playerIsAlive;

	public static int[][][] gameBoard;// = new int[fieldSize][fieldSize][maxPlayers];//antal x, antal y, spelare
	
	//ska innehÃ¥lla all ShipObject med alla kordinater fÃ¶r varje skepp
	public static ArrayList<ShipObject> shipList = new ArrayList<ShipObject>();

	public static int map(int player, int x, int y){ //kollar värdet i arrayen
		Debug.all("State.map\tplayer:"+player+"\tx:"+x+"\ty:"+y);
		if (Integer.valueOf(gameBoard[x][y][player]) == null) gameBoard[x][y][player] = 0;
		return gameBoard[x][y][player];
	}
	
	public static void map(int player, int x, int y, int state){ //ändrar värdet i arrayen
		Debug.out("State.map\tplayer:"+player+"\tx:"+x+"\ty"+y+"\tstate:"+state);
		gameBoard[x][y][player] = state;
	}
	
	public static int pl(int p){
		Debug.all("State.pl\tp:"+p);
		if(p == -1){ //gï¿½r att nï¿½sta spelare fï¿½r spela
			do{
				player ++;
			}while(!playerIsAlive[player-1]);
			if (player >= maxPlayers)player=0;
			if (opponentIsAI && player==0){
				AI.fire();
				pl(-1);
			}
			return player;
		}else{ //returnerar nï¿½sta spelare efter 'p'
			int tempP;
			if (p > maxPlayers-2){
				tempP = 0;
			}else{
				tempP = player + 1;
			}
			while(!playerIsAlive[tempP]){
				if (tempP > maxPlayers-2){
					tempP = 0;
				}else{
					tempP++;
				}
			}
			return tempP;
		}
	}

	public static void fire(int x, int y){ //sätter in rätt värde i arrayen när man skjuter
		Debug.out("State.fire\tx:"+x+"\ty:"+y);
		map(pl(player), x, y, 1);
	}
	
	public static ArrayList<Integer> sunkenShips(int p){
		ArrayList<Integer> numberOfSunkShips = new ArrayList<Integer>();
		for(int i = 0; i < shipList.size(); i++){
			if(isSunk(p, i))
				numberOfSunkShips.add(shipList.get(i).pX.length);
		}
		return numberOfSunkShips;
	}

	public static boolean isSunk(int p, int ship){ //kollar om skeppet 'ship' Ã¤r sÃ¤nkt fï¿½r spelare p
		Debug.out("State.isSunk\tp:"+p+"\tship:"+ship);
		for(int i = 0; i < shipList.get(ship).pX.length; i++){
			if(shipList.get(ship).player==p 
					&& gameBoard[shipList.get(ship).pX[i]]
							[shipList.get(ship).pY[i]]
									[p]!=1)
				return false;
			
		}
		return true;
	}

	public static boolean allIsSunk(){ //kollar om alla skepp Ã¤r sÃ¤nkta
		Debug.out("State.allIsSunk()\tChecking");
		for(int i = 0; i < shipList.size(); i++){ //Kollar igenom alla skepp
			if(!isSunk(pl(player), i)){
				Debug.out("State.allIsSunk()\tThere is still an undestroyed ship left");
				return false;
			}
		}
		playerIsAlive[pl(player)]=false;
		Debug.out("State.allIsSunk()\tAll ships were destroyed");
		return true;
	}

	public static int isShip(int p, int x, int y){ //kollar om det finns ett skepp pÃ¥ platsen x,y och vilket skepp det är
		Debug.all("State.isShip\tp:"+p+"\tx:"+x+"\ty:"+y);
		for(int shipNumber = 0; shipNumber < shipList.size(); shipNumber++){
			for(int shipCoordinate = 0; shipCoordinate < shipList.get(shipNumber).pX.length; shipCoordinate++){
				if(shipList.get(shipNumber).pX[shipCoordinate]==x &&
						shipList.get(shipNumber).pY[shipCoordinate]==y &&
						shipList.get(shipNumber).player==p){
					Debug.all("State.isShip\tFound ship \tID:"+shipNumber);
					return shipNumber;
				}
			}	
		}
		Debug.all("State.isShip\tNo ship was found");
		return -1;
	}
	
	public static boolean maxNumberOfShips(int p){ //kollar om spelare p har överstigig max antal skepp som får placeras ut
		int numberOfShips = 0;
		
		for(int i = 0; i < shipList.size(); i++){
			
			if(shipList.get(i).player==p){
				Debug.all("State.maxNumberOfShips\tcounter: +1");
				numberOfShips++;
			}else{
				Debug.all("State.maxNumberOfShips\tcounter: no change");
			}
		}
		
		if(numberOfShips > maxShips){
			Debug.out("State.maxNumberOfShips\tp:"+p+"\tfalse");
			return false;
		}else{
			Debug.out("State.maxNumberOfShips\tp:"+p+"\ttrue");
			return true;
		}
	}
	
	public static int numberOfShips(int p){ //kollar antalet skepp spelare p har placeras ut
		int numberOfShips = 0;
		
		for(int i = 0; i < shipList.size(); i++){
			
			if(shipList.get(i).player==p){
				Debug.all("State.numberOfShips\tcounter: +1");
				numberOfShips++;
			}else{
				Debug.all("State.numberOfShips\tcounter: no change");
			}
		}
		
		return numberOfShips;
	}

	public static int isWithinArBounds(int i){ //ser till at vÃ¤rdet Ã¤r inom spelplanens grÃ¤nser
		Debug.all("State.isWithinArBounds\ti:"+i);
		if (i < 0){
			return 0;
		}else if(i >= fieldSize){
			return fieldSize-1;
		}else{
			return i;
		}
	}
	
	public static int nextShipSize(int p){
		return maxShips-numberOfShips(p);
	}

	//Sätter ut nya skepp
	public static void newShip(int p, int x, int y, int lenght ,int dir){ //direction 0=öst 1=syd 2=väst 3=norr
		Debug.out("State.newShip\tp:"+p+"\tx:"+x+"\ty:"+y+"\tlenght:"+lenght+"\tdir:"+dir);
		
		if(lenght<1)
			lenght=1;
		
		if(maxNumberOfShips(p)){
			int[] arX = new int[lenght];
			int[] arY = new int[lenght];

			boolean legalPosition = true; //hï¿½ller koll om platsen bï¿½ten placeras pï¿½ ï¿½r tillï¿½ten
			
			switch (dir){
			case 0:
				for(int i = 0; i < lenght; i++){
					if(x+i>fieldSize)legalPosition=false;
					Array.set(arX, i, isWithinArBounds(x+i));
					Array.set(arY, i, isWithinArBounds(y));
				}
				break;
			case 1:
				for(int i = 0; i < lenght; i++){
					if(y+i>fieldSize)legalPosition=false;
					Array.set(arX, i, isWithinArBounds(x));
					Array.set(arY, i, isWithinArBounds(y+i));
				}
				break;
			case 2:
				for(int i = 0; i < lenght; i++){
					if(x-i<0)legalPosition=false;
					Array.set(arX, i, isWithinArBounds(x-i));
					Array.set(arY, i, isWithinArBounds(y));
				}
				break;
			case 3:
				for(int i = 0; i < lenght; i++){
					if(y-i<0)legalPosition=false;
					Array.set(arX, i, isWithinArBounds(x));
					Array.set(arY, i, isWithinArBounds(y-i));
				}
				break;
			}
			
			//kollar om det redan finns ett skepp pï¿½ den platsen
			for(int newShipPos = 0; newShipPos < lenght; newShipPos++){
				for(int oldShip = 0; oldShip < shipList.size(); oldShip++){
					for(int oldShipPos = 0; oldShipPos < shipList.get(oldShip).pX.length; oldShipPos++){
						if( //ska kolla rutan som skeppet ska placeras pÃ¥ och alla rutor runt om Ã¤r lediga
								(isWithinArBounds(arX[newShipPos])==shipList.get(oldShip).pX[oldShipPos] //pÃ¥ rutan
								&& isWithinArBounds(arY[newShipPos])==shipList.get(oldShip).pY[oldShipPos]
										&& p==shipList.get(oldShip).player)
								
								|| (isWithinArBounds(arX[newShipPos]+1)==shipList.get(oldShip).pX[oldShipPos] //Ã¶st om rutan
								&& isWithinArBounds(arY[newShipPos])==shipList.get(oldShip).pY[oldShipPos]
										&& p==shipList.get(oldShip).player)
								
								|| (isWithinArBounds(arX[newShipPos]-1)==shipList.get(oldShip).pX[oldShipPos] //vÃ¤st
								&& isWithinArBounds(arY[newShipPos])==shipList.get(oldShip).pY[oldShipPos]
										&& p==shipList.get(oldShip).player)
																
								|| (isWithinArBounds(arX[newShipPos])==shipList.get(oldShip).pX[oldShipPos] //Syd
								&& isWithinArBounds(arY[newShipPos]+1)==shipList.get(oldShip).pY[oldShipPos]
										&& p==shipList.get(oldShip).player)
								
								|| (isWithinArBounds(arX[newShipPos])==shipList.get(oldShip).pX[oldShipPos] //Norr
								&& isWithinArBounds(arY[newShipPos]-1)==shipList.get(oldShip).pY[oldShipPos]
										&& p==shipList.get(oldShip).player)
								
								|| (isWithinArBounds(arX[newShipPos]+1)==shipList.get(oldShip).pX[oldShipPos] //s-Ã¶
								&& isWithinArBounds(arY[newShipPos]+1)==shipList.get(oldShip).pY[oldShipPos]
										&& p==shipList.get(oldShip).player)
																
								|| (isWithinArBounds(arX[newShipPos]-1)==shipList.get(oldShip).pX[oldShipPos] //n-v
								&& isWithinArBounds(arY[newShipPos]-1)==shipList.get(oldShip).pY[oldShipPos]
										&& p==shipList.get(oldShip).player)
								
								|| (isWithinArBounds(arX[newShipPos]+1)==shipList.get(oldShip).pX[oldShipPos] //n-Ã¶
								&& isWithinArBounds(arY[newShipPos]-1)==shipList.get(oldShip).pY[oldShipPos]
										&& p==shipList.get(oldShip).player)
								
								|| (isWithinArBounds(arX[newShipPos]-1)==shipList.get(oldShip).pX[oldShipPos] //s-v
								&& isWithinArBounds(arY[newShipPos]+1)==shipList.get(oldShip).pY[oldShipPos]
										&& p==shipList.get(oldShip).player)
							)
							legalPosition=false;
					}
				}
			}
			if(legalPosition){
				Debug.out("State.newShip\tGood position for ship placement");
				shipList.add(new ShipObject(p, arX, arY));
			}else{
				Debug.out("State.newShip\tBad position for ship placement");
			}
		}
		
		

		/*System.out.println("antal: " + shipList.size()); //Extrakod fÃ¶r debugging av placeringen av skepp
		if(shipList.size()!=0)System.out.println("spelare: " + shipList.get(shipList.size()-1).player);*/
		
	}
	
}
