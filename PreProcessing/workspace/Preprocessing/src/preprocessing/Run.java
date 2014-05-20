package preprocessing;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;


import edu.stanford.nlp.ling.TaggedWord;


public class Run {
	public static void main(String[] args) {
		HashMap<String, Integer> wordsCounter = new HashMap<String, Integer>();
		POSTagger tagger = null;
		Cleaner cleaner = new Cleaner();
		Lemmatizer lemmatizer = new Lemmatizer();		
		tagger = new POSTagger();
		File input = new File("../../data/parsed2/");
		String output = "../../data/preprocessed2/";
		PrintWriter pw;

		Collection<File> files = FileUtils.listFiles(input, TrueFileFilter.INSTANCE, null);
		for(File f : files) {
			System.out.println("handling file : " + f.toPath());
			try {
				//tagger = new POSTagger(f.toPath().toString());
				tagger.setDocument(f.toPath().toString());
				List<TaggedWord> s;
				while (tagger.hasNext()) {
					s = tagger.tagNext();
					
					s = cleaner.clean(s);
					//System.out.println(s);

					//words = Stemmer.stem(words);
					List<String> words = lemmatizer.lemmatize(s);

					//System.out.println(words);
					for (String word : words) {
						if (wordsCounter.get(word) == null) {
							wordsCounter.put(word, 1);
						} else {
							wordsCounter.put(word, wordsCounter.get(word) + 1);
						}
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(wordsCounter.entrySet());
			 
			Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
				public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
					return o2.getValue() - o1.getValue();
				}
			});
			
			Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
			for (Iterator<Entry<String, Integer>> it = list.iterator(); it.hasNext();) {
				Map.Entry<String, Integer> entry = it.next();
				sortedMap.put(entry.getKey(), entry.getValue());
			}
			
			try {
				pw = new PrintWriter(output + f.getName() + ".preprocessed");
				for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
					pw.println(entry.getKey() + "\t" + entry.getValue());
				}
				pw.flush();
				pw.close();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			wordsCounter.clear();
		}
	}
}
