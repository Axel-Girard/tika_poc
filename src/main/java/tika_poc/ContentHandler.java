package tika_poc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.ToXMLContentHandler;
import org.xml.sax.SAXException;

public class ContentHandler {

	public static void main(String[] args) {
		ContentHandler contentHandler = new ContentHandler();
		try {
			System.out.println(contentHandler.parseToHTML().trim());
		} catch (IOException | SAXException | TikaException e) {
			e.printStackTrace();
		}
	}

	public String parseToHTML() throws IOException, SAXException, TikaException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter path of your file");
		String filepath = scanner.nextLine();
		scanner.close();

		// creating the file object
		File file = new File(filepath);
		FileInputStream stream = new FileInputStream(file);

		ToXMLContentHandler handler = new ToXMLContentHandler();

		AutoDetectParser parser = new AutoDetectParser();
		Metadata metadata = new Metadata();

		parser.parse(stream, handler, metadata);
		return handler.toString();
	}

	public String parseBodyToHTML() throws IOException, SAXException, TikaException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter path of your file");
		String filepath = scanner.nextLine();
		scanner.close();

		// creating the file object
		File file = new File(filepath);
		FileInputStream stream = new FileInputStream(file);

		BodyContentHandler handler = new BodyContentHandler(new ToXMLContentHandler());

		AutoDetectParser parser = new AutoDetectParser();
		Metadata metadata = new Metadata();

		parser.parse(stream, handler, metadata);
		return handler.toString();
	}
}
