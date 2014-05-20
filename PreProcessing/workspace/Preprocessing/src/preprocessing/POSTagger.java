package preprocessing;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

/*
 * Useful links:
 * http://www.computing.dcu.ie/~acahill/tagset.html
 * http://nlp.stanford.edu/software/tagger.shtml
 * http://nlp.stanford.edu/software/pos-tagger-faq.shtml
 */

public class POSTagger {
	private MaxentTagger tagger;
	private Iterator<List<HasWord>> it;

	public POSTagger() {
		tagger = new MaxentTagger("taggers/english-left3words-distsim.tagger");
	}

	public POSTagger(String path) throws FileNotFoundException {
		tagger = new MaxentTagger("taggers/english-left3words-distsim.tagger");
		setDocument(path);
	}

	public void setDocument(String path) throws FileNotFoundException {
		TokenizerFactory<CoreLabel> ptbTokenizerFactory = PTBTokenizer.factory(new CoreLabelTokenFactory(), "untokenizable=noneKeep");
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
		DocumentPreprocessor documentPreprocessor = new DocumentPreprocessor(br);
		documentPreprocessor.setTokenizerFactory(ptbTokenizerFactory);

		it = documentPreprocessor.iterator();
	}

	public boolean hasNext() {
		return it.hasNext();
	}

	public List<TaggedWord> tagNext() {
		List<HasWord> s = it.next();
		//System.out.println(s);
		return tagger.tagSentence(s);
	}
}

/*
 * public class POSTagger {
	private MaxentTagger tagger;
	private Iterator<List<HasWord>> it;

	public POSTagger() {
		tagger = new MaxentTagger("taggers/english-left3words-distsim.tagger");
	}

	public POSTagger(String path) throws FileNotFoundException {
		tagger = new MaxentTagger("taggers/english-left3words-distsim.tagger");
		setDocument(path);
	}

	public void setDocument(String path) throws FileNotFoundException {
		TokenizerFactory<CoreLabel> ptbTokenizerFactory = PTBTokenizer.factory(new CoreLabelTokenFactory(), "untokenizable=noneKeep");
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
		DocumentPreprocessor documentPreprocessor = new DocumentPreprocessor(br);
		documentPreprocessor.setTokenizerFactory(ptbTokenizerFactory);

		it = documentPreprocessor.iterator();
	}

	public boolean hasNext() {
		return it.hasNext();
	}

	public List<TaggedWord> tagNext() {
		List<HasWord> s = it.next();
		//System.out.println(s);
		return tagger.tagSentence(s);
	}
}
 */ 
