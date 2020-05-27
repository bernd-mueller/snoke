package de.zbmed.dl.json;

import org.nd4j.shade.jackson.core.JsonFactory;
import org.nd4j.shade.jackson.core.JsonParseException;
import org.nd4j.shade.jackson.core.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.nactem.tools.sentencesplitter.EnglishSentenceSplitter;

import java.io.*;

import java.util.*;

/**
 * ConvertJSON2MesHFolders
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2019
 */
public class ConvertJSON2MesHFolders {
    private static final Logger log = LoggerFactory.getLogger(ConvertJSON2MesHFolders.class);
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        //create JsonParser object
        int maxdoc = Integer.parseInt(args[1]);
        List<String> sentences = new ArrayList<String>();
        Set<String> meshLabels = new HashSet<String>();
        JsonParser jsonParser;
        List <BioASQDocument> sbod = new ArrayList <BioASQDocument>();
        try {
            FileInputStream fis = new FileInputStream(
                    //"C:\\Users\\Muellerb.ZB_MED\\Documents\\BioASQ2019\\BioASQ-SampleDataA.json"
                    //"C:\\Users\\Muellerb.ZB_MED\\Documents\\BioASQ2019\\allMeSH_2019\\allMeSH_2019.json"
                    args[0]
            );

            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            log.info("Reading File line by line using BufferedReader");

            String line = reader.readLine();
            int counter = 0;
            while(line != null){
                line = reader.readLine();
                if (line!= null) {

                    if (line.length() > 2) {

                            jsonParser = new JsonFactory().createParser(line);
                            BioASQDocument bod = new BioASQDocument();
                            bod = BioASQDocument.parseJSON(line);
                            if (!bod.getYear().equals("null"))
                                if (Integer.parseInt(bod.getYear())>2017)
                                    sbod.add(bod);
                        if (counter++%100000==0) {
                            log.info("#" + counter);
                            writeDocuments(sbod,args[2]);
                            sbod = new ArrayList<BioASQDocument>();
                        }
                            //sentences.addAll(preProcess(bod.getTitle()));
                            //sentences.addAll(preProcess(bod.getAbstractText()));
                        //}
                    }
                    if (counter==maxdoc)
                        break;
                }
            }

            log.info("Done reading documents...");


            File meshlabels = new File ("D:\\resources" +
                File.separator +
                "meshlabels.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(meshlabels));
            log.debug("Writing meshlabels...");
            for (String label : meshLabels) {
                writer.write(label + "\n");
            }

            writer.close();
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

    public static void writeDocuments (List <BioASQDocument> sbod, String basepath) {
        Iterator<BioASQDocument> biter = sbod.iterator();
        while (biter.hasNext()) {
            BioASQDocument bsd = biter.next();
            if (bsd.getYear()!= null) {
                if (!bsd.getYear().equals("null")) {
                    List<String> meshTerms = bsd.getLabels();
                    Iterator<String> miter = meshTerms.iterator();
                    while (miter.hasNext()) {
                        String meshterm = miter.next();

                        String path = basepath +
                            File.separator +
                            "meshlabeldocs2018" +
                            File.separator +
                            meshterm;
                        File f = new File(path);
                        if (!f.exists()) {
                            f.mkdirs();
                        }
                        File fpmid = new File(path +
                            File.separator +
                            bsd.getPmid());
                        BufferedWriter writer = null;
                        try {
                            writer = new BufferedWriter(new FileWriter(fpmid));
                            writer.write(bsd.getTitle() + "\t" + bsd.getAbstractText());

                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        log.debug("Writing " + fpmid);

                    }
                }
            }
        }

    }
}
