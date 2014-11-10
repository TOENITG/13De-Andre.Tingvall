package Bubble;

import java.util.Random;

public class MainBubblesort {

	static int numberOfActions = 0;
	static int numberOfLoops = 0;
	static int numberOfSubLoops = 0;
	
	public static int[] bubbleSort(int arToSort[]){
		boolean didStuff; 
		do{
			didStuff = false;
			for(int i = 0; i < arToSort.length-1; i ++){
				if(arToSort[i] < arToSort[i+1]){
					int a = arToSort[i+1];
					arToSort[i+1] = arToSort[i];
					arToSort[i]=a;
					didStuff = true;
					numberOfActions++;
				}
				numberOfSubLoops++;
			}
			numberOfLoops++;
		}while(didStuff);
		
		return arToSort;
	}
	
	public static void printAll(int arToPrint[]){
		for(int i = 0; i < arToPrint.length; i++){
			if(i % 10 == 0) System.out.println();
			System.out.print("\t"+ arToPrint[i]);
		}
	}
	
	public static void randomAll(int arToRand[]){
		
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
		System.out.println("\n\tActions: " + numberOfActions + "\n\tLoops: " + numberOfLoops+ "\n\tSubloops: " + numberOfSubLoops);
	}

}
