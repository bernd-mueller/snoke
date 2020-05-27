package de.zbmed.dl.doc2vec;

import de.zbmed.dl.doc2vec.tools.LabelSeeker;
import org.deeplearning4j.models.embeddings.inmemory.InMemoryLookupTable;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.models.word2vec.VocabWord;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.documentiterator.FileLabelAwareIterator;
import org.deeplearning4j.text.documentiterator.LabelAwareIterator;
import org.deeplearning4j.text.documentiterator.LabelledDocument;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.StemmingPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.primitives.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * MyParagraphVectorization
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2019
 */
public class MyParagraphVectorization {

    ParagraphVectors paragraphVectors;
    LabelAwareIterator iterator;
    TokenizerFactory tokenizerFactory;

    private static final Logger log = LoggerFactory.getLogger(MyParagraphVectorization.class);

    public static void main(String[] args) throws Exception {
        org.nd4j.linalg.api.buffer.DataType asd;
        org.nd4j.nativeblas.PointerPointerWrapper qwe;
        log.info("test");
        MyParagraphVectorization app = new MyParagraphVectorization();
        log.info("Calling makeParagraphVectors ()");
        app.makeParagraphVectors(args[0], args[1]);
        log.info("Calling checkUnlabeldData ()");
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

    void makeParagraphVectors(String sourcedirectory, String outfile)  throws Exception {
        //ClassPathResource resource = new ClassPathResource(
        //        "C:\\Users\\Muellerb.ZB_MED\\gitlab\\oxygen-workspace\\DeepLearning4J\\src\\main\\resources\\paravec\\labeled");
        // build a iterator for our dataset

        // String filename = "C:/Users/Muellerb.ZB_MED/gitlab/oxygen-workspace/DeepLearning4J/src/main/resources/paravec/labeled";

        log.info("Starting up...");

        TokenizerFactory t = new DefaultTokenizerFactory();
        StemmingPreprocessor sp = new StemmingPreprocessor ();
        t.setTokenPreProcessor(sp);

        String modelpath = "D:\\Modelle\\w2vmodel100000.txt";
        String vocabpath = "D:\\Modelle\\w2vvocab100000.txt";

        Word2Vec w2v = new Word2Vec();
        /*
        VocabCache vc = null;
        log.info("Loading vocab from " + vocabpath);
        vc = WordVectorSerializer.readVocabCache(
                new File(vocabpath));

         */

        log.info("Loading model from " + modelpath);
        //w2v = WordVectorSerializer.readWord2VecModel(new File(modelpath),
        //            true);
        //w2v.setVocab(vc);


        //w2v.setTokenizerFactory(t);

        // String filename = "D:\\resources\\meshlabeldocs";
        String filename = sourcedirectory;


        File f = new File (filename);
        //log.infon(resource.getURL());
        log.info("Creating FileLabelAwareIterator on " + filename);
        iterator = new FileLabelAwareIterator.Builder()
            .addSourceFolder(f)
            .build();
        log.info("Creating ParagraphVectors");
        // ParagraphVectors training configuration

        paragraphVectors = new ParagraphVectors.Builder()
            .tokenizerFactory(t)
            //.useExistingWordVectors(w2v)
            //.lookupTable(w2v.getLookupTable())
            //.vocabCache(vc)
            .learningRate(0.025)
            .minLearningRate(0.001)
            .batchSize(1000)
            .epochs(1)
            .iterate(iterator)
            .trainWordVectors(true)
            //.sequenceLearningAlgorithm("org.deeplearning4j.models.embeddings.learning.impl.sequence.DBOW")
            // org.deeplearning4j.models.embeddings.learning.impl.sequence.DM")
            .build();


        log.info("Starting fit");
        // Start model training
        paragraphVectors.fit();
        String path ="D:\\Modelle\\paragraphvectorssmall.txt";
        WordVectorSerializer.writeParagraphVectors(paragraphVectors, outfile);
        //WordVectorSerializer.writeVocabCache(w2v.getVocab(), new File ("w2vvocab"+maxdoc+".txt"));

/*
        paragraphVectors = WordVectorSerializer.readParagraphVectors("D:\\Modelle\\paragraphvectorstest.txt");
        tokenizerFactory = new DefaultTokenizerFactory();
        tokenizerFactory.setTokenPreProcessor(new CommonPreprocessor());
        paragraphVectors.setTokenizerFactory(tokenizerFactory);
 */
    }

    void checkUnlabeledData() throws IOException {
      /*
      At this point we assume that we have model built and we can check
      which categories our unlabeled document falls into.
      So we'll start loading our unlabeled documents and checking them
     */
        String current = new java.io.File( "." ).getCanonicalPath();
        log.info("Current dir:"+current);
        String currentDir = System.getProperty("user.dir");
        log.info("Current dir using System:" +currentDir);

        File f = new File ("D:\\resources\\meshunabeldocs");
        //ClassPathResource unClassifiedResource = new ClassPathResource("paravec/unlabeled");
        //FileDocumentIterator fdi = new FileDocumentIterator("");
        FileLabelAwareIterator unClassifiedIterator = new FileLabelAwareIterator.Builder()
            .addSourceFolder(f)
            .build();

     /*
      Now we'll iterate over unlabeled data, and check which label it could be assigned to
      Please note: for many domains it's normal to have 1 document fall into few labels at once,
      with different "weight" for each.
     */

        log.info("Number of labels: " + paragraphVectors.getLabelsSource().getLabels().size());
        LabelSeeker seeker = new LabelSeeker(paragraphVectors.getLabelsSource().getLabels(),
            (InMemoryLookupTable<VocabWord>) paragraphVectors.getLookupTable());

        while (unClassifiedIterator.hasNextDocument()) {
            LabelledDocument document = unClassifiedIterator.nextDocument();
            log.info(document.getContent());
            paragraphVectors.predict(document);
            INDArray documentAsCentroid = paragraphVectors.inferVector(document);
            //INDArray documentAsCentroid = meansBuilder.documentAsVector(document);
            List<Pair<String, Double>> scores = seeker.getScores(documentAsCentroid);

         /*
          please note, document.getLabel() is used just to show which document we're looking at now,
          as a substitute for printing out the whole document name.
          So, labels on these two documents are used like titles,
          just to visualize our classification done properly
         */
            HashMap<String, Double> mscores = new HashMap<String, Double>() ;
            log.info("Document '" + document.getLabels() + "' falls into the following categories: ");
            for (Pair<String, Double> score: scores) {
                System.out.println("        " + score.getFirst() + ": " + score.getSecond());
                mscores.put(score.getFirst(), score.getSecond());
            }
            int counter = 0;

            Map<String, Double> sorted = sortHashMapByValues(mscores);
            for (String key : sorted.keySet()) {
                if (counter++>100) break;
                log.info(key + ": " + sorted.get(key));
            }
        }

    }
    public LinkedHashMap<String, Double> sortHashMapByValues(
        HashMap<String, Double> passedMap) {
        List<String> mapKeys = new ArrayList<String>(passedMap.keySet());
        List<Double> mapValues = new ArrayList<Double>(passedMap.values());
        Collections.sort(mapValues, Collections.reverseOrder());
        Collections.sort(mapKeys, Collections.reverseOrder());

        LinkedHashMap<String, Double> sortedMap =
            new LinkedHashMap<String, Double>();

        Iterator<Double> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            Double val = valueIt.next();
            Iterator<String> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                String key = keyIt.next();
                Double comp1 = passedMap.get(key);
                Double comp2 = val;

                if (comp1.equals(comp2)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }
}
