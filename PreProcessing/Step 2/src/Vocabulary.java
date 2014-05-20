import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;


public class Vocabulary {
	
	public static void vocabularize(File input, File corpus, File shows, HashMap<Integer, String> vocabulary) {
		
		try {
			BufferedWriter bw_corpus = new BufferedWriter(new PrintWriter(corpus));
			BufferedWriter bw_shows = new BufferedWriter(new PrintWriter(shows));

			Collection<File> files = FileUtils.listFiles(input, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);

			int id = 0;
			for (File f : files) {
				System.out.println("Handling " + f.getName());
				
				bw_shows.write(id + "\t" + f.getName().replace(".show.preprocessed", "") + "\n");
				bw_corpus.write(id + "\t");

				HashMap<String, Integer> wordsCount = addToWordsCount(f);
				
				for (Entry<Integer, String> e : vocabulary.entrySet()) {
					if (wordsCount.containsKey(e.getValue())) {
						//bw.write(word + "\t" + wordsCount.get(word) + "\n");
						bw_corpus.write(e.getKey() + "," + wordsCount.get(e.getValue()) + " ");
					}
				}
				
				bw_corpus.write("\n");
				id++;
			}
			
			bw_shows.close();
			bw_corpus.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		
	}

	public static void createVocabulary(File input, File output) {
		HashMap<String, Integer> wordCountDoc = new HashMap<String, Integer>();
		HashSet<String> vocabulary = new HashSet<String>();

		Collection<File> files = FileUtils.listFiles(input, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
		
		for (File f : files) {
			System.out.println("Handling " + f.getName());
			addToVocabulary(f, vocabulary, wordCountDoc);
		}
		
		//Clean voc with 90% rule
		int n_shows = 1121;
		int percentage = 90;
		
		for (Entry<String, Integer> entry : wordCountDoc.entrySet()) {
			if (entry.getValue() > n_shows*percentage/100) {
				vocabulary.remove(entry.getKey());
			}
		}

		try {
			BufferedWriter bw = new BufferedWriter(new PrintWriter(output));
			int i = 0;
			for (String word : vocabulary) {
				bw.write(i + "\t" + word + "\n");
				i++;
			}
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void addToVocabulary(File f, HashSet<String> vocabulary, HashMap<String, Integer> wordCountDoc) {
		BufferedReader br;
		String currentLine;
		try {
			br = new BufferedReader(new FileReader(f));
			while((currentLine = br.readLine()) != null) {
				String word = currentLine.split("\\s+")[0].trim();
				vocabulary.add(word);
				addToHashmap(wordCountDoc, word, 1);
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	private static HashMap<String, Integer> addToWordsCount(File f) {
		HashMap<String, Integer> wordsCount = new HashMap<String, Integer>();
		BufferedReader br;
		String currentLine;
		try {
			br = new BufferedReader(new FileReader(f));
			while((currentLine = br.readLine()) != null) {
				String[] line = currentLine.split("\\s+");
				wordsCount.put(line[0], Integer.parseInt(line[1]));
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return wordsCount;
	}
	
	public static void clean(File input, String output, HashSet<String> dictionary, HashSet<String> stopWords) {
		Collection<File> files = FileUtils.listFiles(input, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);

		for (File f : files) {
			System.out.println("Cleaning " + f.getName());			
			cleanFile(f, output + f.getName(), dictionary, stopWords);
		}		
		
	}
	
	private static void cleanFile(File f, String outputPath, HashSet<String> dictionary, HashSet<String> stopWords) {
		HashMap<String, Integer> wordsCount = new HashMap<String, Integer>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			
			String currentLine;			
			while((currentLine = br.readLine()) != null) {				
				String[] line = currentLine.split("\\s+");
				
				String word = line[0];
				if (word.contains("-") || word.contains(".") || word.contains("?") || word.contains("!")) {
					String[] subWords = word.split("[-.?!]");
					for (String subWord : subWords) {
						if (wordIsValid(subWord, dictionary, stopWords)) {
							addToHashmap(wordsCount, subWord, Integer.parseInt(line[1]));
						}
					}
				} else if (wordIsValid(word, dictionary, stopWords)) {
					addToHashmap(wordsCount, word, Integer.parseInt(line[1]));
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (wordsCount.size() == 0) {
			return;
		}
		
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new PrintWriter(outputPath));
			
			for (Entry<String, Integer> e : wordsCount.entrySet()) {
				bw.write(e.getKey() + "\t" + e.getValue() + "\n");
			}
			
			bw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		
	}
	
	private static boolean wordIsValid(String word, HashSet<String> dictionary, HashSet<String> stopWords) {
		return (word.length() > 2 && word.length() < 16 && word.matches("[a-z]+") && dictionary.contains(word) && !stopWords.contains(word));
	}

	private static void addToHashmap(HashMap<String, Integer> wordsCount, String key, Integer value) {
		if (wordsCount.containsKey(key)) {
			wordsCount.put(key, wordsCount.get(key) + value);
		} else {
			wordsCount.put(key, value);
		}
	}
}
