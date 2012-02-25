import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * 
 */

/**
 * Class: Main
 * @author Hamidreza Tavakoli
 * Last revision: Feb 21, 2012 2:27:17 PM
 */
public class Main {
    public static void log(String text){
    	try {
			//PrintWriter out = new PrintWriter("log.txt");
    		PrintStream out = new PrintStream(new FileOutputStream("log.txt", true)); 
			out.println(text);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public static int getAscii(String s){
    	int result = 0;
    	for (int i=0; i<s.length();i++)
    		result +=(int)s.charAt(i);
    	return result;
    }
	public static void main(String[] args) throws IOException
	{
//		log("test");
		System.out.println(getAscii("hahaha")%32);
	}
}
