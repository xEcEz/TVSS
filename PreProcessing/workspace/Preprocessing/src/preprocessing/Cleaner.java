package preprocessing;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import edu.stanford.nlp.ling.TaggedWord;


public class Cleaner {
	private static HashSet<String> blacklist;
	private static HashSet<String> whitelist;
	//private static HashSet<String> deletedWords;
	
	public Cleaner() {
		String[] forbiddenTags = {".", ",", "'", "?", "!", ":", "''", "``", "DT", "PRP", "PRP$", "WDT", "WP", "WP$", "WRB", "CC", "TO", "MD"};
		String[] allowedTags = {"JJ", "JJR", "JJS", "NN", "NNP", "NNPS", "NNS", "VB", "VBD", "VBG", "VBN", "VBP", "VBZ"};
		/*String[] debug = {"the", ".", ",", "with", "'s", "a", "you", "he", "n't", "your", "of", "'d",
				"him", "i", "to", "?", "in", "if", "us", "uh", "'re", "'m", "is", "so", "be", "...",
				"it", "here", "up", "what", "do", "they", "we", "me", "all", "about", "but", "--", 
				"yes", "no", "yeah","will", "would", "was", "like", "''", "``", "or", "not", "that", 
				"out", "off", "for", "could", "one", "an", "'", "were", "where", "'ll", "then", "and",
				"she", "anyway", "from", "are", "why", "only", "how", "got", "after", "been", "some", 
				"its", "did", "when", "finally", "on", "his", "had", "this", "himself", "oh", "ahem", 
				"my", "go", "well", "have", "can", "say", "because", "want", "know", "very", "'ve",
				"really", "used", "has", "!", "her", "must", "another", "at", "knows", "am", "big", 
				"who", "does", "mean", "tell", "any", "now", "yet", "ever", "by", "ago", "made", "them",
				"there", "just", "always", "too", "get", "put", "make", "into", "these", "down", "maybe",
				"back", "their", "again", "ahead", "huh", "thursday", "our", "each", "already", "right", "show"};*/
		blacklist = new HashSet<String>();
		whitelist = new HashSet<String>();
		//deletedWords = new HashSet<String>();
		blacklist.addAll(Arrays.asList(forbiddenTags));
		whitelist.addAll(Arrays.asList(allowedTags));
		//deletedWords.addAll(Arrays.asList(debug));
	}
	
	public List<TaggedWord> clean(List<TaggedWord> sentence) {
		ArrayList<TaggedWord> words = new ArrayList<>();
		
		for (TaggedWord tw : sentence) {
			if (whitelist.contains(tw.tag())) {
				words.add(tw);
			}
//			} else {
//				if (!deletedWords.contains(tw.word().toLowerCase())) {
//					//System.out.println(tw.word().toLowerCase());
//				}
//			}
		}
		
		return words;
	}

}
