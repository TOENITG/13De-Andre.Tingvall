package sankaskepp;

public class Debug {

	private static int debugMode = 0; //0=ingen debug 1=viss information 2=all information

	public static void out(String outString){
		if(debugMode>=1){
			System.out.println(outString);
		}
	}
	public static void all(String outString){
		if(debugMode>=2){
			System.out.println(outString);
		}
	}
}
