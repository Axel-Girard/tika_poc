package tika_poc;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

public class Extract {

	public static void main(final String[] args) throws IOException, SAXException,TikaException {

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.println("enter path of your file");
		String filepath = scanner.nextLine();
		
		//creating the file object		        
		File file = new File(filepath); 
		
		//using tika facade
		Tika tika = new Tika();
		String filecontent = tika.parseToString(file);
		System.out.println("Extracted Content: " + filecontent);
	}
}
