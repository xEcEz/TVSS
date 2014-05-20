package preprocessing;
import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.process.Morphology;


public class Lemmatizer {
	Morphology m;
	
	public Lemmatizer() {
		m = new Morphology();
	}
	
	public List<String> lemmatize(List<TaggedWord> words) {
		List<String> lemmas = new ArrayList<String>();
		
		for (TaggedWord taggedWord : words) {
			String s = m.lemma(taggedWord.word(), taggedWord.tag(), true);
			lemmas.add(s.toLowerCase());
		}
		
		return lemmas;
	}
}
