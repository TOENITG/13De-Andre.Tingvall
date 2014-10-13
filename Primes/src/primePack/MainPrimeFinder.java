package primePack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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
			sFil(path, "2\n3\n5\n");
		}
		
		getPrimes();
		
		System.out.println(lFil(path).get(lFil(path).size() - 1));
		System.out.println("Done");
		System.exit(0);
	}
		
	private static void getPrimes() throws IOException {
		int cInt = 3;
		try{
			cInt = lFil(path).get(lFil(path).size() - 1);
		}catch(NumberFormatException e){
			
		}
		boolean isPrime = true;
		
		for(int li = 0; li < 1000; li ++){
			for(int i = 0; i < lFil(path).size() && isPrime; i++){
				if((int)((double)cInt/(double)lFil(path).get(i)) == ((double)cInt/(double)lFil(path).get(i))){
					isPrime = false;
				}
			}
			
			if (isPrime){
				sFil(path, lFil(path).toString().replace(", ", "\n").replace("[", "").replace("]", "") + "\n" + String.valueOf(cInt));
				System.out.println(lFil(path).get(lFil(path).size() - 1));
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
