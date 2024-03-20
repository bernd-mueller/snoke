package de.zbmed.snoke.dl.json;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.nd4j.shade.jackson.core.JsonParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.nactem.tools.sentencesplitter.EnglishSentenceSplitter;

/**
 * CreatePMIDFilesFromRawJSON
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2019
 */
public class CreatePMIDFilesFromRawJSON {
    private static final Logger log = LoggerFactory.getLogger(CreatePMIDFilesFromRawJSON.class);
    public static void main(String[] args) {
        List <BioASQDocument> sbod = new ArrayList <BioASQDocument>();
        try {
            FileInputStream fis = new FileInputStream(
                //"C:\\Users\\Muellerb.ZB_MED\\Documents\\BioASQ2019\\BioASQ-SampleDataA.json"
                //"C:\\Users\\Muellerb.ZB_MED\\Documents\\BioASQ2019\\allMeSH_2019\\allMeSH_2019.json"
                args[0]
            );

            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            log.info("Reading File line by line using BufferedReader from " + args[0]);
            sbod = new ArrayList<BioASQDocument>();
            String line = reader.readLine();
            int counter = 0;
            while(line != null){
                line = reader.readLine();
                if (line!= null) {
                    log.info("#" + counter++);
                    if (line.length() > 2) {
                        BioASQDocument bod = new BioASQDocument();
                        bod = BioASQDocument.parseJSON(line);
                        sbod.add(bod);
                    } else {
                        log.info("Line length smaller than 2: " + line);
                    }

                }
            }
            writeDocuments(sbod,args[1]);
            reader.close();

            log.info("Done reading JSON and writing documents...");



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

    public static void writeDocuments(List<BioASQDocument> sbod, String basepath) {
        Iterator<BioASQDocument> biter = sbod.iterator();
        while (biter.hasNext()) {
            BioASQDocument bsd = biter.next();
            if (bsd.getPmid() != null) {
                File bf = new File (basepath);
                if (!bf.exists()) {
                    log.info("Creating directory " + basepath);
                    bf.mkdirs();
                } else {
                    //log.info("Directory " + basepath + " already exists.");
                }
                String path = basepath +
                    File.separator +
                    "raw";
                File f = new File(path);
                if (!f.exists()) {
                    log.info("Creating directory " + path);
                    f.mkdirs();
                } else {
                    //log.info ("Directory " + path + " already exists.");
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
                //log.debug("Writing " + fpmid);

            }
        }
    }
}


