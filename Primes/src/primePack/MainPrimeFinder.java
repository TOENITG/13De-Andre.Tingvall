package primePack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MainPrimeFinder {

	static String path = MainPrimeFinder.class.getProtectionDomain().getCodeSource().getLocation().getPath()+"PrimeFile.txt"
			+ File.separator;
	
	public static void main(String[] args) throws IOException {
		
		try{
			System.out.println(lFil(path));
		}catch(FileNotFoundException e){
			e.printStackTrace();
			System.out.println("\n\n Creating new file since the old file wasn't found.\n\n");
			File f = new File(path);
			f.createNewFile();
			sFil(path, "2\n3\n");
		}
		
		getPrimes(lFil(path));
		
		System.out.println(lFil(path).get(lFil(path).size() - 1));
		System.out.println("Done");
		System.exit(0);
	}
		
	public static void getPrimes(ArrayList<Integer> pList) throws IOException {
		int cInt = 3;
		try{
			cInt = pList.get(pList.size() - 1);
		}catch(NumberFormatException e){
			
		}
		boolean isPrime = true;
		
		for(int li = 0; li < 100; li ++){
			for(int i = 0; i < pList.size()/2+1 && isPrime; i++){
				if((int)((double)cInt/(double)pList.get(i)) == ((double)cInt/(double)pList.get(i))){
					isPrime = false;
				}
			}
			
			if (isPrime){
				sFil(path, pList.toString().replace(", ", "\n").replace("[", "").replace("]", "") + "\n" + String.valueOf(cInt));
				pList.add(cInt);
				System.out.println(pList.get(pList.size() - 1));
			}

			
			isPrime = true;
			cInt += 2;
			
		}
		
	}

	public static void sFil(String fLoc, String nyText) throws IOException{
		
		BufferedWriter fWrite = new BufferedWriter(new FileWriter(fLoc));
		fWrite.write(nyText);
		fWrite.close();
		
	}
		
	public static ArrayList<Integer> lFil(String fLoc) throws IOException{
		BufferedReader fRead = new BufferedReader(new FileReader(fLoc));
		ArrayList<Integer> prime = new ArrayList<Integer>();
		
		
		String r = fRead.readLine();
		while(r!=null){
			try{
				prime.add(Integer.parseInt(r));
			}catch (NumberFormatException e){
				
			}
			r = fRead.readLine();
		}

		fRead.close();
		return prime;
	}
		

}
