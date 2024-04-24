package de.zbmed.snoke.dl.doc2vec;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.models.word2vec.wordstore.VocabCache;
import org.deeplearning4j.text.documentiterator.LabelAwareIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.shade.jackson.core.JsonFactory;
import org.nd4j.shade.jackson.core.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.zbmed.snoke.dl.json.BioASQDocument;

import static de.zbmed.snoke.dl.json.ReadBioASQJavaX.preProcess;

import java.io.*;
import java.util.*;

/**
 * MeSHClassifier
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2019
 */
public class MeSHClassifier {
    ParagraphVectors paragraphVectors;
    LabelAwareIterator iterator;
    TokenizerFactory tokenizerFactory;
    static int maxdoc;
    static List<String> sentences = new ArrayList<String>();
    static List<BioASQDocument> bs = new ArrayList<BioASQDocument>();
    static JsonParser jsonParser;
    private static final Logger log = LoggerFactory.getLogger(MeSHClassifier.class);

    public static void main(String[] args) throws Exception {
        maxdoc = Integer.parseInt(args[1]);
        FileInputStream fis = new FileInputStream(
                //"C:\\Users\\Muellerb.ZB_MED\\Documents\\BioASQ2019\\BioASQ-SampleDataA.json"
                //"C:\\Users\\Muellerb.ZB_MED\\Documents\\BioASQ2019\\allMeSH_2019\\allMeSH_2019.json"
                args[0]
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
                    bod = bod.parseJSON(line);
                    //sbod.add(bod);
                    sentences.addAll(preProcess(bod.getTitle()));
                    sentences.addAll(preProcess(bod.getAbstractText()));
                    log.info("Adding " + bod.getPmid());
                    bs.add(bod);

                    log.info("Set size " + bs.size());
                }
                if (counter == maxdoc)
                    break;
            } else {
                log.info("Something went wrong here!");
            }
        }

        System.out.println("Done reading documents: " + bs.size());
        MeSHClassifier app = new MeSHClassifier();
        MyLabelAwareIterator mai = new MyLabelAwareIterator();

        mai.setDocs(bs);
        app.iterator = mai;
        app.makeParagraphVectors();
        app.checkUnlabeledData();
        /*
                Your output should be like this:
                Document 'health' falls into the following categories:
                    health: 0.29721372296220205
                    science: 0.011684473733853906
                    finance: -0.14755302887323793
                Document 'finance' falls into the following categories:
                    health: -0.17290237675941766
                    science: -0.09579267574606627
                    finance: 0.4460859189453788
                    so,now we know categories for yet unseen documents
         */
    }

    private void makeParagraphVectors() {
        tokenizerFactory = new DefaultTokenizerFactory();
        tokenizerFactory.setTokenPreProcessor(new CommonPreprocessor());

        // ParagraphVectors training configuration

        Word2Vec w2v = new Word2Vec();
        VocabCache vc = null;
        try {
            vc = WordVectorSerializer.readVocabCache(
                    new File("C:\\Users\\Muellerb.ZB_MED\\Modelle\\w2vvocab100000.txt"));

            paragraphVectors = new ParagraphVectors.Builder()
                    .learningRate(0.025)
                    .minLearningRate(0.001)
                    .batchSize(1000)
                    .epochs(20)
                    .iterate(iterator)
                    .trainWordVectors(true)
                    .vocabCache(vc)
                    .labelsSource(iterator.getLabelsSource())
                    .tokenizerFactory(tokenizerFactory)
                    .build();

            // Start model training
            paragraphVectors.fit();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void checkUnlabeledData() {

    }

}
