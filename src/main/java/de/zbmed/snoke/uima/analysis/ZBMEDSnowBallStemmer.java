package de.zbmed.snoke.uima.analysis;

import java.io.FileNotFoundException;
import java.text.ParseException;

import org.apache.uima.conceptMapper.support.stemmer.Stemmer;
import org.tartarus.snowball.ext.EnglishStemmer;

public class ZBMEDSnowBallStemmer extends EnglishStemmer implements Stemmer {
	EnglishStemmer es = new EnglishStemmer ();
	@Override
	public String stem(String token) {
		
		// TODO Auto-generated method stub
		if (!token.equals(null)) {
			es.setCurrent(token);
			if (es.stem()) {
				return es.getCurrent();
			} else {
				return token;
			}
		} else {
			return "";
		}
	}

	@Override
	public void initialize(String dictionary) throws FileNotFoundException, ParseException {
		// TODO Auto-generated method stub

	}

}
