/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package de.zbmed.snoke.uima.analysis;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.HashMap;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.text.Language;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Level;
import org.apache.uima.util.Logger;
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
 * Custom analysis engine ZBMEDSnowballAnnotator that stems tokens based on the
 * Snowball stemming algorithm.
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class ZBMEDSnowballAnnotator extends JCasAnnotator_ImplBase implements org.apache.uima.conceptMapper.support.stemmer.Stemmer {

  private Logger logger;
	private HashMap<String,SnowballProgram> stemmers;
	SnowballProgram stemmer;
	Method stemmerStemMethod;
	String language = "en";

  private static final Object[] emptyArgs = new Object[0];

  
  
  public void process(JCas aJCas) throws AnalysisEngineProcessException {

    this.logger.log(Level.INFO, "Snowball annotator starts processing");

    // get get stemmer for the document language
    String language = new Language(aJCas.getDocumentLanguage()).getLanguagePart();

    SnowballProgram stemmer = this.stemmers.get(language);

    // create stemms if stemmer for the current document language is available
    if (stemmer != null) {

      // get stem() method from stemmer
      Method stemmerStemMethod;
      try {
        stemmerStemMethod = stemmer.getClass().getMethod("stem", new Class[0]);
      } catch (Exception ex) {
        throw new AnalysisEngineProcessException(ex);
      }

      // iterate over all token annotations and add stem if available
      FSIterator<Annotation> tokenIterator = aJCas.getAnnotationIndex(TokenAnnotation.type).iterator();
      while (tokenIterator.hasNext()) {
        // get token content
        TokenAnnotation annot = (TokenAnnotation) tokenIterator.next();
        String span = annot.getCoveredText();

        // set annotation content and call stemmer
        try {
          stemmer.setCurrent(span);
          stemmerStemMethod.invoke(stemmer, emptyArgs);
        } catch (Exception ex) {
          throw new AnalysisEngineProcessException(ex);
        }

        // get stemmer result and set annotation feature
        annot.setStem(stemmer.getCurrent());
      }
    } else {
      if (language.equals("x")) {
        this.logger.log(Level.WARNING, "Language of the CAS is set to 'x', SnowballAnnotator skipped processing.");
      }
    }
    this.logger.log(Level.INFO, "Snowball annotator processing finished");
  }

  @Override
  public void initialize(UimaContext aContext) throws ResourceInitializationException {
    super.initialize(aContext);
    // initialize logger
    try {
      this.logger = aContext.getLogger();

      // initialize stemmers
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
    } catch (Exception ex) {
      throw new ResourceInitializationException(ex);
    }

    this.logger.log(Level.INFO, "Snowball annotator successfully initialized");
  }

@Override
public String stem(String token) {
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


public void initialize() throws FileNotFoundException, ParseException {
	// TODO Auto-generated method stub
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

@Override
public void initialize(String dictionary) throws FileNotFoundException, ParseException {
	// TODO Auto-generated method stub
	// TODO Auto-generated method stub
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
}