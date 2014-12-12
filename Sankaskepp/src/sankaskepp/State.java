package sankaskepp;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class State {
	
	
	public static String path(String s){//letar på platsen som bilderna ligger på
		return (SankMain.class.getProtectionDomain().getCodeSource().getLocation().getPath() + s
				+ File.separator).replaceAll(File.separator+"bin", "");
	}
			
	private static int player = 0;//player-1
	private static int maxPlayers = 1;//maxPlayer-1
	
	private static int[][][] gameBoard = new int[10][10][maxPlayers];//antal x, antal y, spelare
	
	public static int map(int x, int y){
		if (Integer.valueOf(gameBoard[x][y][whosTurn()]) == null) gameBoard[x][y][whosTurn()] = 0;
		return gameBoard[x][y][whosTurn()];
	}
	
	public static void map(int x, int y, int state){
		gameBoard[x][y][whosTurn()] = state;
	}
	
	public static int whosTurn(){
		return player;
	}
	
	public static int whosTurn(boolean bool){
		if (bool){								//next players turn if true
			player ++;
			if (player > maxPlayers) player = 0;
			return player;
		}else{									//returns the next possible player
			if (player > maxPlayers)return 0;
			else return player+1;
		}
	}
	
	public boolean isSunk(int ship){ //kollar om skeppet 'ship' är sänkt
		if(Array.getLength(shipX.get(ship))!=Array.getLength(shipY.get(ship))){
			System.out.println("Error!\nKoordinater saknas.");
			System.exit(0);
		}
		
		for(int i = 0; i < Array.getLength(shipX.get(shipX.size())); i++){
			if(gameBoard[Array.getInt(shipX.get(ship),i)][Array.getInt(shipY.get(ship),i)][whosTurn()]!=1)
				return false;
		}
		return true;
	}

	//ska innehålla all arrayer med alla kordinater för varje skepp
	private static ArrayList<int[]> shipX = new ArrayList<int[]>();
	private static ArrayList<int[]> shipY = new ArrayList<int[]>(); 
	
	public static void newShip(int x, int y, int lenght ,int dir){ //direction 0=öst 1=norr 2=väst 3=syd
		//shipX.add((Array) Array.newInstance(int.class.getComponentType(), lenght));
		//shipY.add((Array) Array.newInstance(int.class.getComponentType(), lenght));
		shipX.add(int[]);
		Object arX = shipX.get(shipX.size());
		Object arY = shipY.get(shipY.size());
		
		switch (dir){
		case 0:
			for(int i = 0; i < lenght; i++){
				Array.set(arX, i, x+i);
				Array.set(arY, i, y);
			}
			break;
		case 1:
			for(int i = 0; i < lenght; i++){
				Array.set(arX, i, x);
				Array.set(arY, i, y+i);
			}
			break;
		case 2:
			for(int i = 0; i < lenght; i++){
				Array.set(arX, i, x-i);
				Array.set(arY, i, y);
			}
			break;
		case 3:
			for(int i = 0; i < lenght; i++){
				Array.set(arX, i, x);
				Array.set(arY, i, y-i);
			}
			break;
		}
	}
	
	
}
