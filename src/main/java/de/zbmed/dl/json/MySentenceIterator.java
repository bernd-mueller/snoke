package de.zbmed.dl.json;

import java.util.Iterator;
import java.util.List;

import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentencePreProcessor;

/**
 * MySentenceIterator
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2019
 */
public class MySentenceIterator implements SentenceIterator {

	List <String> sentences;
	Iterator <String> it;
	public MySentenceIterator () {
		
	}
	
	public MySentenceIterator (List <String> sentences) {
		this.sentences = sentences;
		it = sentences.iterator();
	}
	public String nextSentence() {
		// TODO Auto-generated method stub
		return it.next();
	}

	public boolean hasNext() {
		// TODO Auto-generated method stub
		return it.hasNext();
	}

	public void reset() {
		// TODO Auto-generated method stub
		it = sentences.iterator();
	}

	public void finish() {
		// TODO Auto-generated method stub
	}

	public SentencePreProcessor getPreProcessor() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setPreProcessor(SentencePreProcessor preProcessor) {
		// TODO Auto-generated method stub
		
	}

}
