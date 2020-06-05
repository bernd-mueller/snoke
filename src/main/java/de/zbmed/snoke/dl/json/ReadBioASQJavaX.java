package de.zbmed.snoke.dl.json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.stopwords.StopWords;
import org.deeplearning4j.nlp.uima.tokenization.tokenizer.preprocessor.StemmingPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.shade.jackson.core.JsonFactory;
import org.nd4j.shade.jackson.core.JsonParseException;
import org.nd4j.shade.jackson.core.JsonParser;

import uk.ac.nactem.tools.sentencesplitter.EnglishSentenceSplitter;

/**
 * ReadBioASQJavaX
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2019
 */
public class ReadBioASQJavaX {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//create JsonParser object
		int maxdoc = Integer.parseInt(args[1]);
		List <String> sentences = new ArrayList <String> ();
		
		JsonParser jsonParser;
		//Set <BioASQDocument> sbod = new HashSet <BioASQDocument>();
		try {
			FileInputStream fis = new FileInputStream(
					//"C:\\Users\\Muellerb.ZB_MED\\Documents\\BioASQ2019\\BioASQ-SampleDataA.json"
					//"C:\\Users\\Muellerb.ZB_MED\\Documents\\BioASQ2019\\allMeSH_2019\\allMeSH_2019.json"
					args[0]
				);
					
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
          
            System.out.println("Reading File line by line using BufferedReader");
          
            String line = reader.readLine();
            int counter = 0;
            while(line != null){
				line = reader.readLine();
				if (line!= null) {
					if (++counter%10000==0)
						System.out.println("#" + counter);
					if (line.length() > 2) {
						jsonParser = new JsonFactory().createParser(line);
						BioASQDocument bod = new BioASQDocument ();
						bod = BioASQDocument.parseJSON(line);
						//sbod.add(bod);
						sentences.addAll(preProcess(bod.getTitle()));
						sentences.addAll(preProcess(bod.getAbstractText()));
					}
					if (counter==maxdoc)
						break;
				}
            }        

			System.out.println("Done reading documents...");
			
		    List<java.lang.String> sw = StopWords.getStopWords();
		    TokenizerFactory t = new DefaultTokenizerFactory();
		    StemmingPreprocessor sp = new StemmingPreprocessor ();
		    t.setTokenPreProcessor(sp);
		    MySentenceIterator msi = new MySentenceIterator (sentences);
		    System.out.println("Creating w2v...");
		    final long timeStart = System.currentTimeMillis(); 
	        Word2Vec w2v = new Word2Vec.Builder()
	                .minWordFrequency(2)
	                .layerSize(500)
	                .seed(42)
	                .windowSize(5)
	                .iterate(msi)
	                .tokenizerFactory(t)
	                .build();

	        final long timeEnd = System.currentTimeMillis(); 
	        System.out.println("Runtime Constructor Word2Vec " + (timeEnd - timeStart) + " Milliseconds"); 
	        final long timeStart2 = System.currentTimeMillis(); 
	        w2v.fit();
	        final long timeEnd2 = System.currentTimeMillis();
	        System.out.println("Runtime w2v.fit() " + (timeEnd2 - timeStart2) + " Milliseconds"); 
	         
	        System.out.println("Number of words in vocabulary: " + w2v.getVocab().words().size());
	        
		    System.out.println("Finished w2v...\nSaving to file...");
		    WordVectorSerializer.writeWord2VecModel(w2v, "w2vmodel"+maxdoc+".txt");
		    WordVectorSerializer.writeVocabCache(w2v.getVocab(), new File ("w2vvocab"+maxdoc+".txt"));
		    System.out.println("Done!");
		    
			//print employee object
			//System.out.println("BioASQDocument Object\n\n"+sbod);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	public static List<String> preProcess (String p) {   
	    EnglishSentenceSplitter ess = new EnglishSentenceSplitter();
	    List <String> sentences = ess.splitParagraph(p);
	    return sentences;
	}
}
