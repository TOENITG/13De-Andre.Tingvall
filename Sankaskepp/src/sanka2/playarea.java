package sanka2;

import java.util.Vector;

public class playarea {

	int[][] area;
	int size_x;
	int size_y;
	Vector<Ship> ships = new Vector<Ship>();
	
	// Init play area
	public playarea( int size_x , int size_y ){
		area = new int[ size_x ][ size_y ];
		this.size_x = size_x;
		this.size_y = size_y;
		for( int x = 0 ; x < size_x ; x++ )
			for( int y = 0 ; y < size_y ; y++ ) area[x][y] = 0;
	}
	
	// Add a ship
	public void addShip( Ship s ){
		ships.add( s );
	}
	
	// Drop bomb on cordinate x,y
	public void hit( int x , int y ){
		if( area[x][y] == 0 ){
			area[x][y] = -1;
		}
	}
	
	// Checks if all ships are sunked
	public boolean allSunk(){
		for(int s=0; s<ships.size(); s++){
			int scords[][] = ships.get(s).cords;
			for( int n=0 ; n < scords.length ; n++ ){
				if( area[ scords[n][0] ][ scords[n][1] ] == 0 ) return false;
			}
		}
		return true;
	}
	
	// Checks if cords x,y is on a ship
	// returna ship no (starting with 0)
	public int isShip( int x , int y ){
		for(int s=0; s<ships.size(); s++){
			int scords[][] = ships.get(s).cords;
			for( int n=0 ; n < scords.length ; n++ ){
				if( scords[n][0] == x && scords[n][1] == y ) return s;
			}
		}
		return -1;
	}
	
	// Checks status of cords x,y
	// return -1 if cords has been bombed, -2 if ship is hit 
	public int getState( int x , int y ){
		if( area[x][y] != 0 ){
			if( isShip(x,y) >= 0 ) return -2;
			return area[x][y]; 
		}
		return 0;
	}
	
}
