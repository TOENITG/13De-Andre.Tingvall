package sankaskepp;

public class SankMain {

	public static void main(String[] args){
		State.newShip(0, 5, 5, 2, 0);
		State.newShip(0, State.fieldSize-1, State.fieldSize-1, 2, 0);
		State.newShip(1, 5, 5, 2, 0);
		State.newShip(1, 5, 5, 3, 1);
		Disp.displayStart();
		Disp.runDisplay();
	}

}
