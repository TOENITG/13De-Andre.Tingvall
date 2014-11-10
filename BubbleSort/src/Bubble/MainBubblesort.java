package Bubble;

import java.util.Random;

public class MainBubblesort {

	//extra information
	static int numberOfActions = 0; //antalet flyttningar
	static int numberOfLoops = 0; //antalet loopar 
	
	public static int[] bubbleSort(int arToSort[]){ //sorterar
		boolean didStuff; 
		do{
			didStuff = false;
			for(int i = 0; i < arToSort.length-1; i ++){ //går igenom och jämför talen i arrayen
				if(arToSort[i] < arToSort[i+1]){
					int a = arToSort[i+1];
					arToSort[i+1] = arToSort[i];
					arToSort[i]=a;
					didStuff = true;
					numberOfActions++;
				}
			}
			numberOfLoops++;
		}while(didStuff); //loopen avslutas när programmet inte har gjort någon ändring i arrayen
		
		return arToSort;
	}
	
	public static void printAll(int arToPrint[]){ //Skriver ut arrayen
		for(int i = 0; i < arToPrint.length; i++){
			if(i % 10 == 0) System.out.println();
			System.out.print("\t"+ arToPrint[i]);
		}
	}
	
	public static void randomAll(int arToRand[]){ //slumpar fram tal och sätter in dem i arrayen
		
		Random rand = new Random();
		
		for(int i = 0; i < arToRand.length; i++){
			arToRand[i] = rand.nextInt(arToRand.length);
		}
	}
	
	public static void main(String[] args) {
		
		int[] listOfInts = new int[100];
		
		randomAll(listOfInts);
		printAll(listOfInts);
		System.out.println("\n\n");
		
		bubbleSort(listOfInts);
		printAll(listOfInts);
		System.out.println("\n\tActions: " + numberOfActions
				+ "\n\tLoops: " + numberOfLoops);
	}

}
