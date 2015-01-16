package sankaskepp;

public class SankMain {
	
	public static void main(String[] args){
		
		if(Startup.initialize()){
			if(Startup.shipPlacementManager()){
				State.pl(-1);
				Disp.displayStart();
			}
		}
	}
}
