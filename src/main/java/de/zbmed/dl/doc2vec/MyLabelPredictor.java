package de.zbmed.dl.doc2vec;

import de.zbmed.dl.doc2vec.tools.LabelSeeker;
import org.deeplearning4j.models.embeddings.inmemory.InMemoryLookupTable;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.models.word2vec.VocabWord;
import org.deeplearning4j.text.documentiterator.FilenamesLabelAwareIterator;
import org.deeplearning4j.text.documentiterator.LabelledDocument;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.StemmingPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.primitives.Pair;
import org.nd4j.shade.jackson.core.JsonParseException;
import org.nd4j.shade.jackson.databind.JsonNode;
import org.nd4j.shade.jackson.databind.ObjectMapper;
import org.nd4j.shade.jackson.databind.ObjectWriter;
import org.nd4j.shade.jackson.databind.node.ArrayNode;
import org.nd4j.shade.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.util.*;

/**
 * MyLabelPredictor
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2019
 */
public class MyLabelPredictor {
    static ParagraphVectors paragraphVectors;
    private static final Logger log = LoggerFactory.getLogger(MyLabelPredictor.class);
    static String mapfilename;
    public static Map <String, String> mname2id;
    private static Map<String, String> readMname2id(String mapfilename) {
        mname2id = new HashMap<String, String> ();
        try {
            FileInputStream fis = new FileInputStream(
                mapfilename
            );

            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            log.info("Reading Mapping file " + mapfilename);

            String line = reader.readLine();
            int counter = 0;
            while(line != null){
                line = reader.readLine();
                if (line!= null) {
                    if (line.length() > 2) {
                        String [] lines = line.split("\t");
                        String key = lines[0];
                        String val = lines[1];
                        mname2id.put(key, val);
                        //log.info(key + "\t" + val);
                    }
                }
            }

            log.info("Done reading mapping file...");


        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mname2id;
    }

    public static void main (String args []) {
        String pvfilename = args[0];
        String unlabeledfolder = args[1];
        String jsonout = args[2];
        String mapfilename = args[3];

        mname2id = readMname2id (mapfilename);
        readPV(pvfilename);
        try {
            checkUnlabeledData(unlabeledfolder, jsonout);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    static void readPV(String pvfilename) {
        try {
            log.info("Reading paragraphVectors from " + pvfilename);
            paragraphVectors = WordVectorSerializer.readParagraphVectors(pvfilename);
            TokenizerFactory t = new DefaultTokenizerFactory();
            StemmingPreprocessor sp = new StemmingPreprocessor ();
            t.setTokenPreProcessor(sp);
            paragraphVectors.setTokenizerFactory(t);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static void checkUnlabeledData(String unlabeledfolder, String jsonout) throws IOException {
      /*
      At this point we assume that we have model built and we can check
      which categories our unlabeled document falls into.
      So we'll start loading our unlabeled documents and checking them
     */


        File f = new File (unlabeledfolder);
        log.info("Iterating over files in folder " + unlabeledfolder);
        //ClassPathResource unClassifiedResource = new ClassPathResource("paravec/unlabeled");
        //FileDocumentIterator fdi = new FileDocumentIterator("");
        FilenamesLabelAwareIterator unClassifiedIterator = new FilenamesLabelAwareIterator.Builder()
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

        // all
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootnode = mapper.createObjectNode();
        ArrayNode rootarray = mapper.createArrayNode();
        int counter = 0;
        while (unClassifiedIterator.hasNextDocument()) {
            LabelledDocument document = unClassifiedIterator.nextDocument();
            //log.info(document.getContent());
            // paragraphVectors.predict(document);
            String pmid = document.getLabels().toString().replaceAll("\\.txt", "");
            pmid = pmid.substring(1, pmid.length()-1);
            log.info("Number " + pmid);
            INDArray documentAsCentroid = paragraphVectors.inferVector(document);
            //INDArray documentAsCentroid = meansBuilder.documentAsVector(document);
            List<Pair<String, Double>> scores = seeker.getScores(documentAsCentroid);

         /*
          please note, document.getLabel() is used just to show which document we're looking at now,
          as a substitute for printing out the whole document name.
          So, labels on these two documents are used like titles,
          just to visualize our classification done properly
         */
            int scorecounter = 0;
            Set <Double> setscores = new HashSet <Double> ();
            List<Double> lscores = new ArrayList<Double>();
            HashMap<Double, String> mscores = new HashMap<Double, String>() ;
            // log.info("Document '" + document.getLabels() + "' falls into the following categories: ");
            for (Pair<String, Double> score: scores) {
                //System.out.println("        " + score.getFirst() + ": " + score.getSecond());
                if (score.getSecond()>0) {
                    setscores.add(score.getSecond());
                    mscores.put(score.getSecond(), mname2id.get(score.getFirst()));
                    lscores.add(score.getSecond());
                }
            }


            /*
            if (counter++>3) {
                break;
            }
             */



            Collections.sort(lscores);
            Collections.reverse(lscores);

            ArrayNode array = mapper.createArrayNode();

            int sizecounter = 0;
            for (Double key : lscores) {
                // log.info(key+"\t" + mscores.get(key) + "\t" +(mscores.get(key)));


                array.add(mscores.get(key));
            }
            JsonNode childNode = mapper.createObjectNode();
            ((ObjectNode) childNode).set("labels", array);
            ((ObjectNode) childNode).put("pmid", Integer.parseInt(pmid));

            rootarray.add(childNode);


        }
        ((ObjectNode) rootnode).set("documents", rootarray);
        //ObjectWriter jwriter = mapper.writerWithDefaultPrettyPrinter();
        ObjectWriter jwriter = mapper.writer();
        String jsonString = jwriter.writeValueAsString(rootnode)+"\n";
        File snoke_pvall = new File (jsonout);
        BufferedWriter writer = new BufferedWriter(new FileWriter(snoke_pvall));
        log.debug("Writing snoke_pvall...");
        writer.write(jsonString);
        writer.close();

    }
    public static LinkedHashMap<String, Double> sortHashMapByValues(
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
