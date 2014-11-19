package Selection;

import java.util.Random;

public class MainSelectionsort {

	//extra information
	static int numberOfActions = 0; //antalet flyttningar
	static int numberOfLoops = 0; //antalet loopar 
	
	public static int[] selectionSort(int arToSort[]){ //sorterar
		for(int i = 0; i < arToSort.length; i++){
			int a = 0;
			for(int il = 0; il < (il-i); il++){
				if(arToSort[i]<arToSort[il]){
					int b = arToSort[il];
					arToSort[il]=arToSort[i];
					arToSort[i]=b;
					numberOfActions++;
				}
			}
			
		}
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
		
		selectionSort(listOfInts);
		printAll(listOfInts);
		System.out.println("\n\tActions: " + numberOfActions
				+ "\n\tLoops: " + numberOfLoops);
	}

}
