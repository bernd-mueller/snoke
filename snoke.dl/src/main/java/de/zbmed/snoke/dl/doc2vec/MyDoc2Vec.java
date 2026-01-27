package de.zbmed.snoke.dl.doc2vec;

import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.models.word2vec.wordstore.VocabCache;
import org.deeplearning4j.nlp.uima.tokenization.tokenizer.preprocessor.StemmingPreprocessor;

import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.shade.jackson.core.JsonFactory;
import org.nd4j.shade.jackson.core.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.zbmed.snoke.dl.json.BioASQDocument;
import uk.ac.nactem.tools.sentencesplitter.EnglishSentenceSplitter;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * MyDoc2Vec
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2019
 */
public class MyDoc2Vec {
	StemmingPreprocessor sp;
    static int maxdoc = 5;
    private static final Logger log = LoggerFactory.getLogger(MyDoc2Vec.class);
    static List<String> sentences = new ArrayList<String>();
    static JsonParser jsonParser;
    static List<BioASQDocument> bs = new ArrayList<BioASQDocument>();
    Word2Vec w2v = new Word2Vec();
    VocabCache<?> vc = null;
    public static void main(String[] args) throws Exception {
        FileInputStream fis = new FileInputStream(
                "C:\\Users\\Muellerb.ZB_MED\\gitlab/oxygen-workspace/Word2Vec/data/BioASQ-SampleDataA.json"
        );

        BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

        System.out.println("Reading File line by line using BufferedReader");

        String line = reader.readLine();
        int counter = 0;

        while (line != null) {
            line = reader.readLine();
            log.info(line);
            if (line != null) {
                //if (++counter%10000==0)
                log.info("Counter #" + counter++);
                if (line.length() > 2) {
                    jsonParser = new JsonFactory().createParser(line);
                    BioASQDocument bod = new BioASQDocument();
                    bod = BioASQDocument.parseJSON(line);
                    //sbod.add(bod);
                    sentences.addAll(preProcess(bod.getTitle()));
                    sentences.addAll(preProcess(bod.getAbstractText()));
                    log.info("Adding " + bod.getPmid());
                    bs.add(bod);

                    List <String> curs = new ArrayList<String> ();
                    curs.add(bod.getTitle());
                    curs.add(bod.getAbstractText());
                    myDoc2vec (curs,
                            (Collection<String>)bod.getLabels());

                    log.info("Set size " + bs.size());
                }
                if (counter == maxdoc)
                    break;
            } else {
                log.info("Something went wrong here!");
            }
        }
        reader.close();
    }
    public static List<String> preProcess (String p) {
        EnglishSentenceSplitter ess = new EnglishSentenceSplitter();
        List <String> sentences = ess.splitParagraph(p);
        return sentences;
    }
    public static String myDoc2vec (List<String> cursentences, Collection <String> labels) {
        log.info("Sentence: " + cursentences);
        log.info("Labels: " + labels.toString());
        TokenizerFactory t = new DefaultTokenizerFactory();
        StemmingPreprocessor sp = new StemmingPreprocessor ();
        t.setTokenPreProcessor(sp);
        MyLabelAwareIterator mlai = new MyLabelAwareIterator ();
        mlai.setDocs(bs);
        System.out.println("Creating w2v...");
        Word2Vec myw2v = new Word2Vec.Builder()
                .minWordFrequency(2)
                .layerSize(500)
                .seed(42)
                .windowSize(5)
                .iterate(mlai)
                .tokenizerFactory(t)
                .build();
        myw2v.fit();
        log.info("getWordVectors(labels) " + myw2v.wordsNearest("addition", 5));
        return "";
    }
}
