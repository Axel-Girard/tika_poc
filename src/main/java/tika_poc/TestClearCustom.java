package tika_poc;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TestClearCustom {


	private static String regex = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    /**
     * Extract a pattern of the String
     * @param text String where we extract pattern
     * @return
     */
	public static ArrayList<String> extractCustom(String text) {
		TestDataDictionary.connection();
		ArrayList<String> dataDictionary = TestDataDictionary.getDataDictionnary();

		regex = "";
		for(String itData: dataDictionary){
			itData = itData.trim();
			regex += itData + "|";
		}
		if(regex.length() >= 1){
			regex = regex.substring(0, regex.length()-1).toLowerCase();
		}
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text.toLowerCase().trim());
        ArrayList<String> customExtract = new ArrayList<String>();
        while (m.find()) {
            customExtract.add(m.group());
        }

        TestDataDictionary.deconnection();
        return customExtract;
	}

}
