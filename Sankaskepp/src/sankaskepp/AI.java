package sankaskepp;

public class AI {
	
	private static int lastTask = 0;
	private static int shipAtX = -1;
	private static int shipAtY = -1;
	
	public static void fire(){
		switch (lastTask){
		case 0:
			randomShot();
			break;
		case 1:
			foundShip();
			break;
		}
		
		if(State.allIsSunk()&&State.pl(State.player)==State.player){
			Debug.out("Disp.displayStart()\tAll ships are destroyed");
			Disp.updateView();
			Disp.winWindow();
		}
	}
	
	private static void randomShot(){
		
		//Läser in hela planen och kollar vilka rutor AI borde skjuta på
		int[][] tempField = new int[State.fieldSize][State.fieldSize];
		for(int x = 0; x < State.fieldSize; x++){
			for(int y = 0; y < State.fieldSize; y++){
				if(State.gameBoard[x][y][State.pl(State.player)]==1){
					tempField[x][y] = 1;
					if(State.isShip(State.pl(State.player), x, y)!=-1){
						tempField[State.isWithinArBounds(x-1)][y] = 1;
						tempField[State.isWithinArBounds(x+1)][y] = 1;
						tempField[x][State.isWithinArBounds(y-1)] = 1;
						tempField[x][State.isWithinArBounds(y+1)] = 1;
						tempField[State.isWithinArBounds(x-1)][State.isWithinArBounds(y+1)] = 1;
						tempField[State.isWithinArBounds(x+1)][State.isWithinArBounds(y+1)] = 1;
						tempField[State.isWithinArBounds(x-1)][State.isWithinArBounds(y-1)] = 1;
						tempField[State.isWithinArBounds(x+1)][State.isWithinArBounds(y-1)] = 1;
					}
				}
			}
		}
		//Räknar ut antalet rutor som AI borde skjuta på
		int slotsLeft = State.fieldSize*State.fieldSize;
		for(int x = 0; x < State.fieldSize; x++){
			for(int y = 0; y < State.fieldSize; y++){
				if(tempField[x][y]==1){
					slotsLeft--;
				}
			}
		}
		
		//slumpar ut ett tal som ska användas som positionen för skottet
		int x = (int) (slotsLeft*Math.random());
		int y = 0;
		while(x>=State.fieldSize){
			x=x-State.fieldSize;
			y++;
		}
		
		//kollar om positionen som AI vill skjuta på är tillåten annars byter den till rutan bredvid
		boolean bool = true;
		while(bool){
			if(tempField[x][y]==1){
				x++;
				while(x>=State.fieldSize){
					x=x-State.fieldSize;
					y++;
					if(y>=State.fieldSize)
						y=0;
				}
			}else{
				bool = false;
			}
		}
		
		//skjuter och sedan agerar därefter om det var en träff eller ej
		State.fire(x, y);
		if(State.isShip(State.pl(State.player), x, y)!=-1){
			lastTask = 1;
			shipAtX = x;
			shipAtY = y;
			fire();
		}else{
			lastTask = 0;
		}
	}
	
	private static int oldX = -1;
	private static int oldY = -1;
	private static int lastDir = -1;
	
	private static void foundShip(){
		//kollar om skeppet AI sköt på sist blev sänkt
		if(State.isSunk(State.pl(State.player), State.isShip(State.pl(State.player), shipAtX, shipAtY))){
			lastTask = 0;
			fire();
		}else if(lastDir != -1 && State.isShip(State.pl(State.player), oldX, oldY)!=-1){ //skeppet blev alltså inte sänkt, då försöker den sänka skeppet
			nextShotInDir(lastDir);
		}else{
			newShotDir();
		}
	}
	
	private static void newShotDir(){
		oldX = shipAtX;
		oldY = shipAtY;
		int dir = lastDir;
		if (dir ==-1){
			dir  = (int) (3*Math.random());
		}
		
		boolean canFire = false;
		for(int i = 0; i < 6; i++){
			if(dir == 0 &&
					State.isWithinArBounds(oldX+1)==oldX+1
					&& State.gameBoard[State.isWithinArBounds(oldX+1)][oldY][State.pl(State.player)]!=1){
				canFire = true;
			}else{
				dir++;
			}
			if(dir == 2 &&
					State.isWithinArBounds(oldX-1)==oldX-1
					&& State.gameBoard[State.isWithinArBounds(oldX-1)][oldY][State.pl(State.player)]!=1){
				canFire = true;
			}else{
				dir++;
			}
			if(dir == 1 &&
					State.isWithinArBounds(oldY+1)==oldY+1
					&& State.gameBoard[shipAtX][State.isWithinArBounds(shipAtY+1)][State.pl(State.player)]!=1){
				canFire = true;
			}else{
				dir++;
			}
			if(dir == 3 &&
					State.isWithinArBounds(oldY-1)==oldY-1
					&& State.gameBoard[oldX][State.isWithinArBounds(oldY+1)][State.pl(State.player)]!=1){
				canFire = true;
			}else{
				dir=0;
			}
		}
		if(canFire){
			lastDir=dir;
			lastTask=1;
			fire();
		}else{
			lastDir=-1;
			lastTask=0;
			fire();
		}
	}
	
	private static int newDirAttempts = 0;
	
	private static void nextShotInDir(int dir){
		if(dir == 0 &&
				State.isWithinArBounds(oldX+1)==oldX+1
				&& State.gameBoard[State.isWithinArBounds(oldX+1)][oldY][State.pl(State.player)]!=1){
			oldX++;
			State.fire(oldX,oldY);
			newDirAttempts=0;
		}else if(dir == 2 &&
				State.isWithinArBounds(oldX-1)==oldX-1
				&& State.gameBoard[State.isWithinArBounds(oldX-1)][oldY][State.pl(State.player)]!=1){
			oldX--;
			State.fire(oldX,oldY);
			newDirAttempts=0;
		}else if(dir == 1 &&
				State.isWithinArBounds(oldY+1)==oldY+1
				&& State.gameBoard[shipAtX][State.isWithinArBounds(shipAtY+1)][State.pl(State.player)]!=1){
			oldY++;
			State.fire(oldX,oldY);
			newDirAttempts=0;
		}else if(dir == 3 &&
				State.isWithinArBounds(oldY-1)==oldY-1
				&& State.gameBoard[oldX][State.isWithinArBounds(oldY+1)][State.pl(State.player)]!=1){
			oldY--;
			State.fire(oldX,oldY);
			newDirAttempts=0;
		}else{
			newDirAttempts++;
			if(newDirAttempts > 5){
				lastDir++;
			}else if(newDirAttempts > 10){
				randomShot();
			}
			lastDir=(lastDir+2)%4;
//			if(lastDir<0){
//				lastDir+=4;
//			}
		}
		
		if(State.isSunk(State.pl(State.player), State.isShip(State.pl(State.player), shipAtX, shipAtY))){
			lastDir=-1;
		}else{
			fire();
		}
		
	}
	
//	private static int[] envCheck(int x, int y){
//		
//	}
//	
//	private static int inField(int cord, boolean add){
//		if(add){
//			if(State.isWithinArBounds(cord+1)==cord+1){
//				return cord+1;
//			}else{
//				return cord;
//			}
//		}else{
//			if(State.isWithinArBounds(cord-1)==cord-1){
//				return cord-1;
//			}else{
//				return cord;
//			}
//		}
//	}
	
	/*
	 *
	
	private static int lastX=-1;
	private static int lastY=-1;
	
	private static int firstHitOnShipX=-1;
	private static int firstHitOnShipY=-1;
	private static int firstSuccesfulFollowupShot = -1;
	
	public static void fire(){
		if (firstSuccesfulFollowupShot !=-1 && !State.isSunk(State.pl(State.player), State.isShip(State.pl(State.player), firstHitOnShipX, firstHitOnShipY))){
			followupShot();
		}else if(State.isShip(State.pl(State.player), lastX, lastY)!=-1){
			searchDir();
		}else{
			randomShot();
		}
	}
	
	private static void searchDir(){
		if(firstHitOnShipX == lastX && firstHitOnShipY == lastY){
			boolean bool = true;
			int tempR = (int)(3*Math.random());
			for(int i = 0; i < 8; i++){
				switch(tempR){
				case 0:
					if(State.gameBoard[State.isWithinArBounds(lastX-1)][lastY][State.pl(State.player)]!=1 
					&& State.isWithinArBounds(lastX-1)==lastX-1){
							lastX--;
							firstSuccesfulFollowupShot = 0;
							bool = false;
					}else{
						tempR++;
					}
					break;
				case 1:
					if(State.gameBoard[State.isWithinArBounds(lastX)][State.isWithinArBounds(lastY-1)][State.pl(State.player)]!=1 
					&& State.isWithinArBounds(lastY-1)==lastY-1){
							lastY--;
							firstSuccesfulFollowupShot = 1;
							bool = false;
					}else{
						tempR++;
					}
					break;
				case 2:
					if(State.gameBoard[State.isWithinArBounds(lastX+1)][State.isWithinArBounds(lastY)][State.pl(State.player)]!=1 
					&& State.isWithinArBounds(lastX+1)==lastX+1){
							lastX++;
							firstSuccesfulFollowupShot = 2;
							bool = false;
					}else{
						tempR++;
					}
					break;
				case 3:
					if(State.gameBoard[State.isWithinArBounds(lastX)][State.isWithinArBounds(lastY+1)][State.pl(State.player)]!=1
					&& State.isWithinArBounds(lastY+1)==lastY+1){
							lastY++;
							firstSuccesfulFollowupShot = 3;
							bool = false;
					}else{
						tempR=0;
					}
					break;
				}
			}
			
			if(bool){
				randomShot();
			}
			
			if(State.isShip(State.pl(State.player), lastX, lastY)==-1){
				firstSuccesfulFollowupShot = -1;
			}
			State.fire(lastX, lastY);
		}else if(State.isShip(State.pl(State.player), lastX, lastY)!=-1){
			followupShot();
		}
	}
	
	private static void followupShot(){
		switch(firstSuccesfulFollowupShot){
		case 0:
			if(State.isWithinArBounds(lastX-1)==lastX-1){
				lastX--;
			}else{
				lastX = firstHitOnShipX;
				lastY = firstHitOnShipY;
				firstSuccesfulFollowupShot-=2;
				if(firstSuccesfulFollowupShot<0){
					firstSuccesfulFollowupShot+=4;
				}
			}
			break;
		case 1:
			if(State.isWithinArBounds(lastY-1)==lastY-1){
				lastY--;
			}else{
				lastX = firstHitOnShipX;
				lastY = firstHitOnShipY;
				firstSuccesfulFollowupShot-=2;
				if(firstSuccesfulFollowupShot<0){
					firstSuccesfulFollowupShot+=4;
				}
			}
			break;
		case 2:
			if(State.isWithinArBounds(lastX+1)==lastX+1){
				lastX++;
			}else{
				lastX = firstHitOnShipX;
				lastY = firstHitOnShipY;
				firstSuccesfulFollowupShot-=2;
				if(firstSuccesfulFollowupShot<0){
					firstSuccesfulFollowupShot+=4;
				}
			}
			break;
		case 3:
			if(State.isWithinArBounds(lastY+1)==lastY+1){
				lastY++;
			}else{
				lastX = firstHitOnShipX;
				lastY = firstHitOnShipY;
				firstSuccesfulFollowupShot-=2;
				if(firstSuccesfulFollowupShot<0){
					firstSuccesfulFollowupShot+=4;
				}
			}
			break;
		}
		State.fire(lastX, lastY);
		if(State.isShip(State.pl(State.player), lastX, lastY)==-1){ //kollar om AI tr�ffade skeppet om inte b�ter 
				lastX = firstHitOnShipX;
				lastY = firstHitOnShipY;
				firstSuccesfulFollowupShot-=2;
				if(firstSuccesfulFollowupShot<0){
					firstSuccesfulFollowupShot+=4;
				}
		}else{
			fire();
		}
	}
	
	private static void randomShot(){
		int legalShots = State.fieldSize*State.fieldSize;
		for(int x = 0; x < State.fieldSize; x++){
			for(int y = 0; y < State.fieldSize; y++){
				if(State.gameBoard[x][y][State.pl(State.player)]==1){
					legalShots--;
				}
			}
		}
		
//		for(int i = 0; i < State.sunkenShips(State.pl(State.player)).size(); i++){
//			nextShot = State.sunkenShips(State.pl(State.player)).get(i)*2+6;
//		}
		
		int x = (int) (legalShots*Math.random());
		int y = 0;
		while(x>=State.fieldSize){
			x=x-State.fieldSize;
			y++;
		}
		
		boolean bool = true;
		while(bool){
			if(State.gameBoard[x][y][State.pl(State.player)]==1){
				x++;
				while(x>=State.fieldSize){
					x=x-State.fieldSize;
					y++;
					if(y>=State.fieldSize)
						y=0;
				}
			}else{
				bool = false;
			}
		}
		lastX=x;
		lastY=y;
		State.fire(x, y);
		if(State.isShip(State.pl(State.player), x, y)!=-1
				&& State.playerIsAlive[State.pl(State.player)]){
			firstHitOnShipX = x;
			firstHitOnShipY = y;
			fire();
		}else{
			firstHitOnShipX = -1;
			firstHitOnShipY = -1;
		}
	}*/
}
