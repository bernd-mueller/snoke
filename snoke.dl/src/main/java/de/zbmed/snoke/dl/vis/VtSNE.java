package de.zbmed.snoke.dl.vis;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.models.word2vec.wordstore.VocabCache;
//import org.deeplearning4j.plot.BarnesHutTsne;
import org.deeplearning4j.nlp.uima.tokenization.tokenizer.preprocessor.StemmingPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.linalg.api.buffer.DataType;
import org.nd4j.linalg.api.buffer.util.DataTypeUtil;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * VtSNE
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2019
 */
public class VtSNE {
    private static final Logger log = LoggerFactory.getLogger(VtSNE.class);
    static ParagraphVectors paragraphVectors;
    public static void main (String [] args) {
        String pvfilename = args[0];
        String outfile = args[1];

        //STEP 1: Initialization
        int iterations = 100;
        //create an n-dimensional array of doubles
        DataTypeUtil.setDTypeForContext(DataType.DOUBLE);
        List<String> cacheList = new ArrayList<String>(); //cacheList is a dynamic array of strings used to hold all words

        //STEP 2: Turn text input into a list of words
        log.info("Load & Vectorize data....");
        File wordFile = null;   //Open the file
        try {
            log.info("Reading paragraphVectors from " + pvfilename);
            paragraphVectors = WordVectorSerializer.readParagraphVectors(pvfilename);
            TokenizerFactory t = new DefaultTokenizerFactory();
            StemmingPreprocessor sp = new StemmingPreprocessor ();
            t.setTokenPreProcessor(sp);
            paragraphVectors.setTokenizerFactory(t);

            //Get the data of all unique word vectors
            VocabCache cache = paragraphVectors.getVocab();
            INDArray weights = paragraphVectors.getLookupTable().getWeights();
            // Pair<InMemoryLookupTable,VocabCache> vectors

            // INDArray weights = vectors.getFirst().getSyn0();    //seperate weights of unique words into their own list

            for(int i = 0; i < cache.numWords(); i++)   //seperate strings of words into their own list
                cacheList.add(cache.wordAtIndex(i));

            //STEP 3: build a dual-tree tsne to use later
            log.info("Build model....");
//            BarnesHutTsne tsne = new BarnesHutTsne.Builder()
//                .setMaxIter(iterations).theta(0.5)
//                .normalize(false)
//                .learningRate(500)
//                .useAdaGrad(false)
////                .usePca(false)
//                .build();
//
//            //STEP 4: establish the tsne values and save them to a file
//            log.info("Store TSNE Coordinates for Plotting....");
//            String outputFile = outfile;
//            (new File(outputFile)).getParentFile().mkdirs();
//            tsne.plot(weights,2,cacheList,outputFile);
//            //This tsne will use the weights of the vectors as its matrix, have two dimensions, use the words strings as
//            //labels, and be written to the outputFile created on the previous line

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
