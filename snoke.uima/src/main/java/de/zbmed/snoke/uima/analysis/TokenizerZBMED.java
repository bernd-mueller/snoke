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

import java.text.BreakIterator;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.analysis_engine.ResultSpecification;
import org.apache.uima.analysis_engine.annotator.AnnotatorConfigurationException;
import org.apache.uima.UimaContext;
import org.apache.uima.conceptMapper.support.stemmer.Stemmer;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;

import com.sun.tools.sjavac.Log;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;

/**
 * Simple class to tokenize a string (similar to <code>java.util.StringTokenizer</code>), except
 * that this tokenizer returns
 * {@link TokenAnnotation} objects, which, in
 * addition to the token text string, also contain the start and end offsets of the token in the
 * original string.
 * <p>
 * 
 * The tokenizer will optionally perform stemming and case normalization on the tokens, and the set
 * of characters that delimit tokens may be specified. The default stemmer is the Snowball Porter
 * stemmer, but any stemmer may be supplied to the tokenizer as long as it implements the
 * {@link org.apache.uima.conceptMapper.support.stemmer.Stemmer Stemmer}interface.
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class TokenizerZBMED  extends JCasAnnotator_ImplBase {

	  // ****************************************
	  // * Static vars holding break iterators
	  // ****************************************
	  BreakIterator sentenceBreak = BreakIterator.getSentenceInstance(Locale.US);
	  private String sentenceDelim = "";

	  BreakIterator wordBreak = BreakIterator.getWordInstance(Locale.US);
	  private String wordDelim = "";
	  

		JCas jcas;

		String input;
  /** The input text string being tokenized */
  private String text;

  /** The stemmer that will perform the stemming. */
  private Stemmer stemmer = null;

  //private Logger logger;

  /** Configuration parameter key/label for the case matching string */
  public static final String PARAM_CASE_MATCH = "caseMatch";

  /** Configuration parameter key/label for the stemmer class spec */
  public static final String PARAM_STEMMER_CLASS = "Stemmer";

  /** Configuration parameter key/label for the token delimiters string */
  public static final String PARAM_TOKEN_DELIM = "tokenDelimiters";

  /** The current offset into  */
  private int offset;

  /** The total length of  */
  private int length;

  /** Base whitespace token delimiters */
  private String wsDelim = " \t\n\r\f";

  /**
   * The extra specifiable delimiters used by the tokenizer to separate the input string into
   * individual tokens.
   */
  private String extraDelim = ",-/();:";

  /**
   * The delimiters used by the tokenizer to separate the input string into individual tokens.
   */
  private String delim = wsDelim + extraDelim;

  /** Case folding flag for folding tokens with initial cap. */
  private boolean caseFoldInitCap = false;

  /** Case folding flag for folding tokens with at least one digit character. */
  private boolean caseFoldDigit = false;

  /**
   * Case folding flag for folding all tokens (subsumes {@link #caseFoldInitCap caseFoldInitCap}and
   * {@link #caseFoldDigit caseFoldDigit}).
   */
  private boolean caseFoldAll = false;

  /** Stemming on/off flag. If true, tokens will be stemmed by the tokenizer. */
  private boolean stemTokens = false;

  /**
   * regular expression pattern used to identify terms with an initial capital letter followed by
   * all lowercase letters.
   */
  private Pattern capPat = null;

  /**
   * regular expression pattern used to identify terms with at least one digit character in them.
   */
  private Pattern hasDigit = null;

  /**
   * Create a new <code>OffsetTokenizer</code>. Initializes the default stemmer and sets up the
   * regular expressions for the various case folding options.
   */
  public TokenizerZBMED() {
    try {
      capPat = Pattern.compile("^[A-Z][a-z]+$");
      hasDigit = Pattern.compile("[0-9]");
    } catch (PatternSyntaxException e) {
      e.printStackTrace();
    }

  }


  /**
   * @return Returns the text.
   */
  public String getText() {
    return text;
  }

  /**
   * Set the text to tokenize. After this method is called, the next call to
   * {@link #nextToken(JCas) nextToken} will return the first token from the input string
   * as a TokenAnnotation; you can get the text by using
   * {@link TokenAnnotation#getText()}
   */
  public void setText(String text) {
    this.text = text;
    offset = 0;
    length = getText().length();
  }
  
  
  /**
   * @return Returns the stemmer.
   */
  public Stemmer getStemmer() {
    return stemmer;
  }

  /**
   * @param stemmer
   *          The stemmer to set.
   */
  public void setStemmer(Stemmer stemmer) {
    this.stemmer = stemmer;
  }

  public TokenAnnotation newToken(JCas jcas) {
    return new TokenAnnotation(jcas);
  }



  public TokenAnnotation nextToken(JCas jcas) {
    StringBuilder token = new StringBuilder();
    // System.out.println("in NextToken");
    // skip any delimiters
    while ((offset < length) && (getDelim().indexOf(getText().charAt((offset))) >= 0)) {
      offset += 1;
    }

    if (offset < length) {
      int start = offset;
      while ((offset < length) && (getDelim().indexOf(getText().charAt((offset))) < 0)) {
        token.append(getText().charAt(offset));
        offset += 1;
      }
      TokenAnnotation returnVal = newToken(jcas);
      // System.out.println("token = " + token.toString() + " fold = " +
      // foldCase(token.toString()));
      returnVal.setText(stem(foldCase(token.toString())));
      returnVal.setBegin(start);
      returnVal.setEnd(offset);

      return returnVal;
    }
    return null;
  }



  /**
   * If one of the case folding flags is true and the input string matches the character pattern
   * corresponding to that flag, then convert all letters to lowercase.
   * 
   * @param token
   *          The string to case fold
   * 
   * @return The case folded string
   */
  protected String foldCase(String token) {
    if (shouldFoldCase(token)) {
      return doFoldCase(token);
    }
    return token;
  }

  public String doFoldCase(String token) {
    return token.trim().toLowerCase();
  }



  public boolean shouldFoldCase(String token) {
    return (caseFoldAll || (caseFoldInitCap && capPat.matcher(token).matches()) || (caseFoldDigit && hasDigit
            .matcher(token).find()));
  }

  public boolean shouldStem() {
    return stemTokens;
  }

  /**
   * Set the delimiters used to separate the input string into tokens. This adds the new delimiters
   * to the base whitespace delimiters " \t\n\r\f".
   * 
   * @param delim
   *          The new set of delimiters.
   */
  protected void setDelim(String delim) {
    this.delim = wsDelim + delim;
  }

  /**
   * Set the delimiters used to separate the input string into tokens. This sets the delimiters to
   * exactly the given set. The base whitespace delimiters are not included.
   * 
   * @param delim
   *          The new set of delimiters.
   */
  protected void overrideDelim(String delim) {
    this.delim = delim;
  }

  /**
   * Turn stemming on (true) or off (false).
   * 
   * @param flag
   *          true - turn stemming on, false - turn stemming off
   */
  private void setStemming(boolean flag) {
    stemTokens = flag;
  }

  /**
   * Set case folding flag for folding tokens with initial cap.
   * 
   * @param flag
   *          true - turn on case folding, false - turn off case folding.
   */
  private void setCaseFoldInitCap(boolean flag) {
    caseFoldInitCap = flag;
  }

  /**
   * Set case folding flag for folding tokens with at least one digit character.
   * 
   * @param flag
   *          true - turn on case folding, false - turn off case folding.
   */
  private void setCaseFoldDigit(boolean flag) {
    caseFoldDigit = flag;
  }

  /**
   * Set case folding flag for folding all tokens (subsumes
   * {@link #setCaseFoldInitCap setCaseFoldInitCap}).
   * 
   * @param flag
   *          true - turn on case folding, false - turn off case folding.
   */
  private void setCaseFoldAll(boolean flag) {
    caseFoldAll = flag;
  }

  /**
   * Get the current list of delimiters used to separate the input string into tokens.
   * 
   * @return The current list of delimiters used to separate the input string into tokens.
   */
  protected String getDelim() {
    return delim;
  }

  /**
   * Get the current stemming flag.
   * 
   * @return true if stemming is currently on, false otherwise
   */
  protected boolean getStemming() {
    return stemTokens;
  }

  /**
   * Get case folding flag for folding tokens with initial cap.
   * 
   * @return the current value of the flag
   */
  protected boolean getCaseFoldInitCap() {
    return (caseFoldInitCap);
  }

  /**
   * Get the case folding flag for folding tokens with at least one digit character.
   * 
   * @return the current value of the flag
   */
  protected boolean getCaseFoldDigit() {
    return (caseFoldDigit);
  }

  /**
   * Get case folding flag for folding all tokens.
   * 
   * @return the current value of the flag.
   */
  protected boolean getCaseFoldAll() {
    return (caseFoldAll);
  }

  /**
   * Initialize the annotator, which includes compilation of regular expressions, fetching
   * configuration parameters from XML descriptor file, and loading of the dictionary file.
   */
  public void initialize(UimaContext uimaContext) throws ResourceInitializationException {
    super.initialize(uimaContext);
    try {
      //logger = new Logger("TextTokenizer", annotatorContext.getLogger());

      String[] configParameterNames = uimaContext.getConfigParameterNames();
      Object[] configParameters = new Object[configParameterNames.length];
      for (int i = 0; i < configParameters.length; i++) {
        configParameters[i] = uimaContext.getConfigParameterValue(configParameterNames[i]);
      }

      processAllConfigurationParameters(configParameterNames, configParameters);
      initTokenizer(configParameterNames, configParameters);
    } catch (Exception e) {
      throw new ResourceInitializationException(e);
    }
    
	sentenceDelim = "";
	for (int i = sentenceBreak.first(); i<sentenceBreak.last(); i++) {
		sentenceDelim += sentenceBreak.next();
		
	}
	wordDelim = "";
	for (int i = wordBreak.first(); i<wordBreak.last(); i++) {
		wordDelim += wordBreak.next();
		
	}

  }





  public void processAllConfigurationParameters(String[] configParameterNames,
          Object[] configParameters) throws AnnotatorConfigurationException {
    for (int i = 0; i < configParameterNames.length; i++) {
      processConfigurationParameter(configParameterNames[i], configParameters[i]);
    }
  }


  /**
   * Perform the actual analysis. Iterate over the document content looking for tokens and post an
   * annotation for each match found.
   * 
   * @param jcas
   *          the current CAS to process.
   *
   * @see org.apache.uima.analysis_engine.annotator.JTextAnnotator#process(JCas, ResultSpecification)
   */
  public void process(JCas jcas) throws AnalysisEngineProcessException  {
	  // makeAnnotations(sentenceAnnotationMaker, sentenceBreak);
      // makeAnnotations(tokenAnnotationMaker, wordBreak);
    try {
    	
    	Maker sentenceAnnotationMaker = new Maker() {
    	    Annotation newAnnotation(JCas jcas, int start, int end) {
    	      return new SentenceAnnotation(jcas, start, end);
    	    }
    	  };
      doTokenization(jcas, jcas.getDocumentText(), getDelim());
      makeAnnotations(sentenceAnnotationMaker, sentenceBreak, jcas.getDocumentText(), jcas);
    	
      
    } catch (Exception e) {
      throw new AnalysisEngineProcessException(e);
    }
  }
  
  
//*************************************************************
 // * Helper Methods *
 // *************************************************************
 void makeAnnotations(Maker m, BreakIterator b, String input, JCas jcas) {
   b.setText(input);
   for (int end = b.next(), start = b.first(); end != BreakIterator.DONE; start = end, end = b
           .next()) {
     // eliminate all-whitespace tokens
     boolean isWhitespace = true;
     for (int i = start; i < end; i++) {
    		 char c = input.toCharArray()[i];
    		 if (!Character.isWhitespace(c)) {
    			 isWhitespace = false;
    			 break;
    		 }
    	 
     }
     if (!isWhitespace) {
       m.newAnnotation(jcas, start, end).addToIndexes();
     }
   }
 }
  
  abstract class Maker {
	    abstract Annotation newAnnotation(JCas jcas, int start, int end);
	  }

  // *********************************************
  // * function pointers for new instances *
  // *********************************************
  Maker sentenceAnnotationMaker = new Maker() {
    Annotation newAnnotation(JCas jcas, int start, int end) {
      return new SentenceAnnotation(jcas, start, end);
    }
  };

  /*
   * (non-Javadoc)
   * 
   * @see org.apache.uima.conceptMapper.support.tokenizer.TextTokenizer#initTokenizer(java.lang.String[],
   *      java.lang.Object[])
   */
  public void initTokenizer(String[] paramNames, Object[] paramValues) throws Exception {
    // nothing to do here
    return;
  }

  /**
   * @param jcas
   * @param documentText
   * @param delimiters
   */
  protected void doTokenization(JCas jcas, String documentText, String delimiters) {

    int numTokens = 0;
    TokenAnnotation annotation;

    // System.out.println("doTokenization = " + documentText);
    overrideDelim(delimiters);

    setText(documentText);

    while (null != (annotation = nextToken(jcas))) {
      annotation.addToIndexes();
      numTokens++;
    }
  }

  /**
   * @param configParameterName
   * @param configParameterValue
   */
  public void processConfigurationParameter(String configParameterName, Object configParameterValue) {
    if (configParameterName.equals(PARAM_CASE_MATCH)) {
      String caseSense = (String) configParameterValue;
      if (caseSense != null) {
        if (caseSense.equalsIgnoreCase("insensitive")) {
          this.setCaseFoldInitCap(true);
        } else if (caseSense.equalsIgnoreCase("digitfold")) {
          this.setCaseFoldDigit(true);
        } else if (caseSense.equalsIgnoreCase("ignoreall")) {
          // System.out.println("SETCASEFOLDALL");
          this.setCaseFoldAll(true);
        }
      }
    } else if ((configParameterName.equals(PARAM_STEMMER_CLASS)) && (configParameterValue != null)) {
      try {
        // System.err.println ("Trying to instantiate stemmer class: '" +
        // (String) configParameters [i] + "'");
        Class<?> stemmerClass = Class.forName((String) configParameterValue);
        setStemmer((Stemmer) stemmerClass.newInstance());
        setStemming(true);
      } catch (Exception e) {
        System.err.println("Exception trying to instantiate stemmer class: '"
                + (String) configParameterValue + "', original exception:" + e.getMessage());
        e.printStackTrace();
      }
    } else if (configParameterName.equals(PARAM_TOKEN_DELIM)) {
      String tokenDelimiters = (String) configParameterValue;
      if (tokenDelimiters != null) {
        this.setDelim(tokenDelimiters);
      }
    }
  }


  /**
   * If the stemming flag is true, then return the stemmed form of the supplied word using the
   * Porter stemmer.
   * 
   * @param token
   *          the word to stem
   * @return the original word if the stemming flag is false, otherwise the stemmed form of the word
   */
  protected String stem(String token) {
    if (shouldStem()) {
      return doStemming(token, getStemmer());
    }
    return token;
  }

  public String doStemming(String token, Stemmer stemmer) {
    return stemmer.stem(token.trim());
  }

}