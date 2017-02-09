package tika_poc;

import java.io.File;
import java.util.Scanner;

import org.apache.tika.Tika;

public class MIMEType {

   public static void main(String[] args) throws Exception {
   
      @SuppressWarnings("resource")
      Scanner scanner = new Scanner(System.in);
      System.out.print("Type a pathfile : ");
      String filepath = scanner.nextLine();
   
      //creating file object
      File file = new File(filepath);//
   
      //using tika facade class 
      Tika tika = new Tika();
   
      //detecting the file type using detect method
      String filetype = tika.detect(file);
      System.out.println(filetype);
   }
}