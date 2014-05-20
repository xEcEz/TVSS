package preprocessing;
import java.util.ArrayList;
import java.util.List;


public class Stemmer {
	
	/*
	 * Very basic stemmer, just remove the plural form.
	 */
	public static List<String> stem_test(List<String> words) {
		ArrayList<String> stemmedWords = new ArrayList<>();
		for (String word : words) {
			
			String stemmedWord = word;
			if (word.endsWith("s")) {
				if (word.endsWith("sses")) {
					stemmedWord = stemmedWord.substring(0, word.length() - 2);
				} else if (word.endsWith("ies")) {
					stemmedWord = stemmedWord.substring(0, word.length() - 3) + "y";
				} else if (word.endsWith("ves")) {
					stemmedWord = stemmedWord.substring(0, word.length() - 3) + "f";
				} else if (word.endsWith("es")) {
					stemmedWord = stemmedWord.substring(0, word.length() - 2);
				} else if (!word.endsWith("ss")) {
					stemmedWord = stemmedWord.substring(0, word.length() - 1);
				}
			}
			stemmedWords.add(stemmedWord);
		}
		return stemmedWords;		
	}
	
}
