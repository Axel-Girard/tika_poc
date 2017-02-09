package tika_poc;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

public class TestCustomExtractMain {
	private static HashMap<String, ArrayList<String>> customdata = new HashMap<String, ArrayList<String>>();
	private static int failedFiles, successfulFiles = 0;

	public static void main(String[] args) {
//		System.out.println("Enter a path");
//		Scanner scanner = new Scanner(System.in);
//		String path = scanner.nextLine();
//		scanner.close();
		String path = "/home/agirard/lucene/CV";

		Path folder = Paths.get(path);
		System.out.println("Searching " + folder.toAbsolutePath() + "...");
		processFolder(folder);
		System.out.print("\n%%%%%%%--Results--%%%%%%%");
		for(Entry<String, ArrayList<String>> entry : customdata.entrySet()){
			String key = entry.getKey();
			ArrayList<String> value = entry.getValue();
			if(value.size() >= 1){
				System.out.println("");
				System.out.println("____________"+key+"___________\n");
				for(String itVal: value){
					System.out.println(itVal);
				}
				TestNeo4j.createNode("document", key, value);
			}
		}
		System.out.println("Found " + customdata.size() + " matches");
		System.out.println("Parsed " + successfulFiles + "/" + (successfulFiles + failedFiles) + " documents");
		TestNeo4j.disconnect();
	}

	/**
	 * Parse of files of the Path given
	 * @param folder the Path of the folder
	 */
	public static void processFolder(Path folder) {
		try{
			Files.walkFileTree(folder, new SimpleFileVisitor<Path>(){
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes atrs) throws IOException {
					try{
						process(file, successfulFiles + failedFiles);
						successfulFiles++;
					} catch(Exception e){
						failedFiles++;
						// ignore the file
						System.err.println("Extract of " + file + " failed!");
					}
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
					failedFiles++;
					return FileVisitResult.CONTINUE;
				}
			});
		}catch(IOException e){
		}
	}

	/**
	 * Parse and extract data of a file
	 * @param file the Path of the file to parse and extract data
	 * @throws IOException
	 * @throws SAXException
	 * @throws TikaException
	 */
	public static void process(Path file, int id) throws IOException, SAXException, TikaException{
		Parser parser = new AutoDetectParser();
		Metadata metadata = new Metadata();

		System.out.println("Extract of " + file);
		TestCustomExtract handler = new TestCustomExtract(new BodyContentHandler(),  metadata);
		try(InputStream stream = new BufferedInputStream(Files.newInputStream(file))) {
			parser.parse(stream, handler, metadata, new ParseContext());
		}
		String[] numbers = metadata.getValues("customdata");
		ArrayList<String> array = new ArrayList<String>();
		for(int i = 0; i < numbers.length; i++){
			array.add(numbers[i]);
		}
		customdata.put(file.toString(), array);
	}
}
