package tika_poc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

public class ExtractMetadataWithParser {

	public static void main(String[] args) throws IOException, SAXException, TikaException{

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter path of your file");
		String filepath = scanner.nextLine();

		// creating the file object		        
		File file = new File(filepath);
		FileInputStream stream = new FileInputStream(file);

		// create the parser
		Parser parser = new AutoDetectParser();

		// create the handler
		BodyContentHandler handler = new BodyContentHandler();

		// create an empty metadata
		Metadata meta = new Metadata();

		// create the context
		ParseContext context = new ParseContext();

		parser.parse(stream, handler, meta, context);

		for(String metadata: meta.names()){
			System.out.println(metadata + ": " + meta.get(metadata));
		}
	}
}
