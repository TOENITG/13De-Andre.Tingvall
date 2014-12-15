package sankaskepp;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class State {
			
	public static int player = 0;//player-1
	public static int maxPlayers = 2;//maxPlayer
	public static int fieldSize = 10;//storleken på planen
	
	public static int[][][] gameBoard = new int[fieldSize][fieldSize][maxPlayers];//antal x, antal y, spelare
	
	public static int map(int player, int x, int y){
		if (Integer.valueOf(gameBoard[x][y][player]) == null) gameBoard[x][y][player] = 0;
		return gameBoard[x][y][player];
	}
	
	public static void map(int player, int x, int y, int state){
		gameBoard[x][y][player] = state;
	}
	
	public static int pl(int p){
		if(p == -1){ //g�r att n�sta spelare f�r spela
			player ++;
			if (player >= maxPlayers)player=0;
			return player;
		}else{ //returnerar n�sta spelare efter 'p'
			if (p > maxPlayers-2)return 0;
			else return p+1;
		}
	}
	
	public static void fire(int x, int y){
		map(pl(player), x, y, 1);
	}
	
	public static boolean isSunk(int p, int ship){ //kollar om skeppet 'ship' är sänkt f�r spelare p
		for(int i = 0; i < shipList.get(ship).pX.length; i++){
			//System.out.println((gameBoard[shipList.get(ship).pX[i]][shipList.get(ship).pY[i]][p]!=1));
			if(gameBoard[isWithinArBounds(shipList.get(ship).pX[i])]
					[isWithinArBounds(shipList.get(ship).pY[i])]
							[p]!=1)
				return false;
		}
		return true;
	}
	
	public static boolean allIsSunk(){
		for(int i = 0; i < shipList.size(); i++){
			System.out.println(isSunk(player,i) + " " + i + " " + pl(player));
			if(!isSunk(player, i))
				return false;
		}
		return true;
	}
	
	public static int isShip(int p, int x, int y){
		for(int shipNumber = 0; shipNumber < shipList.size(); shipNumber++){
			for(int shipCoordinate = 0; shipCoordinate < shipList.get(shipNumber).pX.length; shipCoordinate++){
				if(shipList.get(shipNumber).pX[shipCoordinate]==x &&
						shipList.get(shipNumber).pY[shipCoordinate]==y &&
						shipList.get(shipNumber).player==p){
					return shipNumber;
				}
			}	
		}
		return -1;
	}
	
	public static int isWithinArBounds(int i){
		if (i < 0){
			return 0;
		}else if(i > fieldSize){
			return fieldSize;
		}else{
			return i;
		}
	}

	//ska innehålla all arrayer med alla kordinater för varje skepp
	public static ArrayList<ShipObject> shipList = new ArrayList<ShipObject>();
	
	public static void newShip(int p, int x, int y, int lenght ,int dir){ //direction 0=öst 1=norr 2=väst 3=syd
		
		int[] arX = new int[lenght];
		int[] arY = new int[lenght];

		boolean legalPosition = true; //h�ller koll om platsen b�ten placeras p� �r till�ten
		
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
		
		//kollar om det redan finns ett skepp p� den platsen
		for(int newShipPos = 0; newShipPos < lenght; newShipPos++){
			for(int oldShip = 0; oldShip < shipList.size(); oldShip++){
				for(int oldShipPos = 0; oldShipPos < shipList.get(oldShip).pX.length; oldShipPos++){
					if( //ska kolla rutan som skeppet ska placeras på och alla rutor runt om är lediga
							(isWithinArBounds(arX[newShipPos])==shipList.get(oldShip).pX[oldShipPos] //på rutan
							&& isWithinArBounds(arY[newShipPos])==shipList.get(oldShip).pY[oldShipPos]
									&& p==shipList.get(oldShip).player)
							
							|| (isWithinArBounds(arX[newShipPos]+1)==shipList.get(oldShip).pX[oldShipPos] //öst om rutan
							&& isWithinArBounds(arY[newShipPos])==shipList.get(oldShip).pY[oldShipPos]
									&& p==shipList.get(oldShip).player)
							
							|| (isWithinArBounds(arX[newShipPos]-1)==shipList.get(oldShip).pX[oldShipPos] //väst
							&& isWithinArBounds(arY[newShipPos])==shipList.get(oldShip).pY[oldShipPos]
									&& p==shipList.get(oldShip).player)
															
							|| (isWithinArBounds(arX[newShipPos])==shipList.get(oldShip).pX[oldShipPos] //Syd
							&& isWithinArBounds(arY[newShipPos]+1)==shipList.get(oldShip).pY[oldShipPos]
									&& p==shipList.get(oldShip).player)
							
							|| (isWithinArBounds(arX[newShipPos])==shipList.get(oldShip).pX[oldShipPos] //Norr
							&& isWithinArBounds(arY[newShipPos]-1)==shipList.get(oldShip).pY[oldShipPos]
									&& p==shipList.get(oldShip).player)
							
							|| (isWithinArBounds(arX[newShipPos]+1)==shipList.get(oldShip).pX[oldShipPos] //s-ö
							&& isWithinArBounds(arY[newShipPos]+1)==shipList.get(oldShip).pY[oldShipPos]
									&& p==shipList.get(oldShip).player)
															
							|| (isWithinArBounds(arX[newShipPos]-1)==shipList.get(oldShip).pX[oldShipPos] //n-v
							&& isWithinArBounds(arY[newShipPos]-1)==shipList.get(oldShip).pY[oldShipPos]
									&& p==shipList.get(oldShip).player)
							
							|| (isWithinArBounds(arX[newShipPos]+1)==shipList.get(oldShip).pX[oldShipPos] //n-ö
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
		if(legalPosition)shipList.add(new ShipObject(p, arX, arY));

		System.out.println("antal: " + shipList.size());
		if(shipList.size()!=0)System.out.println("spelare: " + shipList.get(shipList.size()-1).player);
		
	}
	
}
