package de.zbmed.snoke.dl.doc2vec;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.deeplearning4j.models.embeddings.inmemory.InMemoryLookupTable;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.models.word2vec.VocabWord;

import org.deeplearning4j.text.documentiterator.FileLabelAwareIterator;
import org.deeplearning4j.text.documentiterator.LabelAwareIterator;
import org.deeplearning4j.text.documentiterator.LabelledDocument;
import org.deeplearning4j.nlp.uima.tokenization.tokenizer.preprocessor.StemmingPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.nd4j.common.primitives.Pair;

import de.zbmed.snoke.dl.doc2vec.tools.LabelSeeker;

import java.io.File;
import java.io.IOException;
import java.util.*;



/**
 * MyParagraphVectorization - A Paragraph Vector model is trained on an input of a labeled folder structure.
 * The labeled folders should contain files with text. The folder structure can be generated with 
 * ConvertJSON2MeSHFolders. The class can be used as standalone runnable jar with command line options.
 *   -i root folder for the structure of subfolder where each name of a subfolder is a MeSH Heading
 *   -o patch and file name for the Paragraph Vector model
 *   -l learning rate for the training of the Paragraph Vector model
 *   -m minimum learning rate for the training of the Paragraph Vector model
 *   -b batch size for the training of the Paragraph Vector model
 *   -e epochs for the training of the Paragraph Vector model
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
    
    static String inputFilePath = null;
    static String outputFilePath = null;
    static double lr = .0;
    static double min = .0;
    static int batch = 0;
    static int epochs = 0;
    static int winsize = 0;
    static String elementsalg = null;
    static String sequencealg = null;


    public static void readCLI (String [] args) throws ParseException {
    	Options options = new Options();

        Option input = new Option("i", "input", true, "input file path");
        input.setRequired(true);
        options.addOption(input);

        Option output = new Option("o", "output", true, "output folder");
        output.setRequired(true);
        options.addOption(output);
        
        Option olr = new Option("l", "learning rate", true, "learning rate for pv model");
        olr.setType(Number.class);
        olr.setRequired(true);
        options.addOption(olr);
        
        Option omlr = new Option("m", "minimum learning rate", true, "minimum learning rate for pv model");
        omlr.setType(Number.class);
        omlr.setRequired(true);
        options.addOption(omlr);
        
        Option ob = new Option("b", "batch size", true, "batch size for pv model");
        ob.setType(Number.class);
        ob.setRequired(true);
        options.addOption(ob);
        
        Option oe = new Option("e", "epochs", true, "epochs for pv model");
        oe.setType(Number.class);
        oe.setRequired(true);
        options.addOption(oe);
        
        Option ow = new Option("w", "window size", true, "window size for pv model");
        ow.setType(Number.class);
        ow.setRequired(true);
        options.addOption(ow);
        
        Option osa= new Option("sa", "sequence algorithm", true, "sequence learning algorithm for pv model / "
        		+ "org.deeplearning4j.models.embeddings.learning.impl.sequence.DM");
        osa.setType(Number.class);
        osa.setRequired(true);
        options.addOption(osa);
        
        Option oea = new Option("ea", "elements algorithm", true, "elements learning algorithm for pv model /"
        		+ "org.deeplearning4j.models.embeddings.learning.impl.elements.CBOW");
        oea.setType(Number.class);
        oea.setRequired(true);
        options.addOption(oea);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);

            System.exit(1);
        }

        inputFilePath = cmd.getOptionValue("input");
        outputFilePath = cmd.getOptionValue("output");
        lr = ((Number)cmd.getParsedOptionValue("learning rate")).doubleValue();
        min = ((Number)cmd.getParsedOptionValue("minimum learning rate")).doubleValue();
        batch = ((Number)cmd.getParsedOptionValue("batch size")).intValue();
        epochs = ((Number)cmd.getParsedOptionValue("epochs")).intValue();
        winsize = ((Number)cmd.getParsedOptionValue("window size")).intValue();
        elementsalg = cmd.getOptionValue("elements algorithm");
        sequencealg = cmd.getOptionValue("sequence algorithm");

        
        log.info("Using parameters:");
        log.info("\tinput: " + inputFilePath);
        log.info("\toutput: " + outputFilePath);
        log.info("\tlearning rate: " + lr);
        log.info("\tminimum learning rate: " + min);
        log.info("\tbatch size: " + batch);
        log.info("\tepochs: " + epochs);
        log.info("\twindow size: " + winsize);
        log.info("\telements algorithm: " + elementsalg);
        log.info("\tsequence algorithm: " + sequencealg);
    }
    public static void main(String[] args) throws Exception {
    	
    	/**
    	 *  .learningRate(0.025)
            .minLearningRate(0.001)
            .batchSize(1000)
            .epochs(1)
    	 */

    	readCLI (args);
        log.info("test");
        MyParagraphVectorization app = new MyParagraphVectorization();
        log.info("Calling makeParagraphVectors ()");
        app.makeParagraphVectors(
        		inputFilePath, 
        		outputFilePath,
        		lr,
        		min,
        		batch,
        		epochs,
        		winsize,
        		elementsalg,
        		sequencealg
        	);
        // log.info("Calling checkUnlabeldData ()");
        // app.checkUnlabeledData();
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

    void makeParagraphVectors(
    		String sourcedirectory, 
    		String outfile,
    		double lr,
    		double min,
    		int batch,
    		int epochs,
    		int winsize,
    		String elementsalg,
    		String sequencealg
    		)  throws Exception {
        //ClassPathResource resource = new ClassPathResource(
        //        "C:\\Users\\Muellerb.ZB_MED\\gitlab\\oxygen-workspace\\DeepLearning4J\\src\\main\\resources\\paravec\\labeled");
        // build a iterator for our dataset

        // String filename = "C:/Users/Muellerb.ZB_MED/gitlab/oxygen-workspace/DeepLearning4J/src/main/resources/paravec/labeled";

        log.info("Starting up...");

        TokenizerFactory t = new DefaultTokenizerFactory();
        StemmingPreprocessor sp = new StemmingPreprocessor ();
        t.setTokenPreProcessor(sp);


        File f = new File (sourcedirectory);
        //log.infon(resource.getURL());
        log.info("Creating FileLabelAwareIterator on " + sourcedirectory);
        iterator = new FileLabelAwareIterator.Builder()
            .addSourceFolder(f)
            .build();
        log.info("Creating ParagraphVectors");
        // ParagraphVectors training configuration
        
        List<String> stopWords =
                Arrays.asList(
                    "a", "an", "and", "are", "as", "at", "be", "but", "by", "for", "if", "in", "into", "is",
                    "it", "no", "not", "of", "on", "or", "such", "that", "the", "their", "then", "there",
                    "these", "they", "this", "to", "was", "will", "with");

        paragraphVectors = new ParagraphVectors.Builder()
            .tokenizerFactory(t)
            //.useExistingWordVectors(w2v)
            //.lookupTable(w2v.getLookupTable())
            //.vocabCache(vc)
            .learningRate(lr)
            .minLearningRate(min)
            .batchSize(batch)
            .epochs(epochs)
            .iterate(iterator)
            .windowSize(winsize)
            .trainWordVectors(true)
            // default: PV-DBOW; DBOW, DM
            .sequenceLearningAlgorithm(sequencealg)
            // default: SkipGram; CBOW, SkipGram, SparkCBOW, SparkDBOW, SparkDM, SparkSkipGram
            .elementsLearningAlgorithm(elementsalg)
            //.sequenceLearningAlgorithm("org.deeplearning4j.models.embeddings.learning.impl.sequence.DBOW")
            // org.deeplearning4j.models.embeddings.learning.impl.sequence.DM")
            .enableScavenger(true)
            .useAdaGrad(true)
            .useHierarchicSoftmax(true)
            .useUnknown(true)
            .usePreciseWeightInit(true)
            .allowParallelTokenization(true)
            .trainElementsRepresentation(true)
            .workers(24)
            .layerSize(200)
            .minWordFrequency(5)
            // .stopWords(stopWords)
            .build();


        log.info("Starting fit");
        // Start model training
        paragraphVectors.fit();
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
