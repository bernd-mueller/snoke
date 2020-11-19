package de.zbmed.snoke.ontology.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.tartarus.snowball.SnowballProgram;
import org.tartarus.snowball.ext.DanishStemmer;
import org.tartarus.snowball.ext.DutchStemmer;
import org.tartarus.snowball.ext.EnglishStemmer;
import org.tartarus.snowball.ext.FinnishStemmer;
import org.tartarus.snowball.ext.FrenchStemmer;
import org.tartarus.snowball.ext.GermanStemmer;
import org.tartarus.snowball.ext.HungarianStemmer;
import org.tartarus.snowball.ext.ItalianStemmer;
import org.tartarus.snowball.ext.NorwegianStemmer;
import org.tartarus.snowball.ext.PortugueseStemmer;
import org.tartarus.snowball.ext.RussianStemmer;
import org.tartarus.snowball.ext.SpanishStemmer;
import org.tartarus.snowball.ext.SwedishStemmer;
/**
 * SnowballStemmer
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class SnowballStemmer {
String language = "en";
	
	private HashMap<String,SnowballProgram> stemmers;
	SnowballProgram stemmer;
	Method stemmerStemMethod;
	private static final Object[] emptyArgs = new Object[0];
	
	public SnowballStemmer() {
		this.stemmers = new HashMap<String, SnowballProgram>();
		this.stemmers.put("da", new DanishStemmer());
		this.stemmers.put("nl", new DutchStemmer());
		this.stemmers.put("en", new EnglishStemmer());
		this.stemmers.put("fi", new FinnishStemmer());
		this.stemmers.put("fr", new FrenchStemmer());
		this.stemmers.put("de", new GermanStemmer());
		this.stemmers.put("hu", new HungarianStemmer());
		this.stemmers.put("it", new ItalianStemmer());
		this.stemmers.put("no", new NorwegianStemmer());
		this.stemmers.put("pt", new PortugueseStemmer());
		this.stemmers.put("ru", new RussianStemmer());
		this.stemmers.put("es", new SpanishStemmer());
		this.stemmers.put("sw", new SwedishStemmer());
		
		this.stemmer = this.stemmers.get(this.language);

	    // create stemms if stemmer for the current document language is available
	    if (this.stemmer != null) {

	      

	        try {
				this.stemmerStemMethod = this.stemmer.getClass().getMethod("stem", new Class[0]);
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	        
	}
	
	public String doTheSnowballStem(String token) {
		// TODO Auto-generated method stub
	    stemmer.setCurrent(token);
	    try {
			stemmerStemMethod.invoke(stemmer, emptyArgs);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    

	  // get stemmer result and set annotation feature
	  return stemmer.getCurrent();
	}
}
