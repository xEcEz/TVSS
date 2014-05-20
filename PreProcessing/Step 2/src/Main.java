import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;



public class Main {
	public static void main(String[] args) {
		File input = new File("preprocessed\\");
		
		String cleaned = "cleaned\\";
		
		File vocabulary_output = new File("f_preprocessed\\vocabulary");
		File corpus_output = new File("f_preprocessed\\corpus");
		File shows_output = new File("f_preprocessed\\shows");
		
		HashSet<String> dictionary = readDictionary("wordsEn.txt");
		HashSet<String> stopWords = readDictionary("stopWords.txt");
		
		Vocabulary.clean(input, cleaned, dictionary, stopWords);
		
		Vocabulary.createVocabulary(new File(cleaned), vocabulary_output);
		
		HashMap<Integer, String> vocabularyList = readVocabulary(vocabulary_output);
		
		Vocabulary.vocabularize(new File(cleaned), corpus_output, shows_output, vocabularyList);
	}
	
	private static HashSet<String> readDictionary(String name) {
		HashSet<String> dictionary = new HashSet<String>();
		BufferedReader br;
		String currentLine;
		try {
			br = new BufferedReader(new FileReader(name));
			while((currentLine = br.readLine()) != null) {
				String word = currentLine.trim();
				dictionary.add(word.toLowerCase());
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return dictionary;
	}
	
	private static HashMap<Integer, String> readVocabulary(File vocabulary) {
		HashMap<Integer, String> vocabularyList = new HashMap<Integer, String>();
		BufferedReader br;
		String currentLine;
		try {
			br = new BufferedReader(new FileReader(vocabulary));
			while((currentLine = br.readLine()) != null) {
				String[] entry = currentLine.split("\\s+");
				vocabularyList.put(Integer.parseInt(entry[0]), entry[1].trim());
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return vocabularyList;
	}
}
