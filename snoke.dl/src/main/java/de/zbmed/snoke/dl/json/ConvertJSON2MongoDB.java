package de.zbmed.snoke.dl.json;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.bson.Document;
import org.nd4j.shade.jackson.core.JsonParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import uk.ac.nactem.tools.sentencesplitter.EnglishSentenceSplitter;

/**
 * ConvertJSON2MongoDB Create a mongodb collection with documents extracted from a BioASQ dump file in JSON format.
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2020
 */
public class ConvertJSON2MongoDB {
	static MongoClient mongoClient = null;
	static MongoDatabase database = null;
	static MongoCollection<Document> coll = null;
	private static final Logger log = LoggerFactory.getLogger(ConvertJSON2MesHFolders.class);
    public static void main(String[] args) {
    	Options options = new Options();

        Option input = new Option("i", "input", true, "input file path");
        input.setRequired(true);
        options.addOption(input);

        Option output = new Option("o", "output", true, "output folder");
        output.setRequired(true);
        options.addOption(output);
        
        Option omax = new Option("m", "maxdoc", true, "maximum files to process");
        omax.setType(Number.class);
        omax.setRequired(true);
        options.addOption(omax);

        Option oyear = new Option("y", "year", true, "filter for files published after this year");
        oyear.setType(Number.class);
        oyear.setRequired(true);
        options.addOption(oyear);

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

        String inputFilePath = cmd.getOptionValue("input");
        String outputFilePath = cmd.getOptionValue("output");
        int maxdoc = -1;
		try {
			maxdoc = ((Number)cmd.getParsedOptionValue("maxdoc")).intValue();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        int yearfilter = -1;
		try {
			yearfilter = ((Number)cmd.getParsedOptionValue("year")).intValue();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
    	log.info("Using parameters:");
    	log.info("\tinput: " + inputFilePath);
    	log.info("\toutput: " + outputFilePath);
    	log.info("\tyear: " + yearfilter);
    	log.info("\tmaxdoc : " + maxdoc);
        
        // TODO Auto-generated method stub

    	setupMongoConnection();
        List <BioASQDocument> sbod = new ArrayList <BioASQDocument>();
        try {
            FileInputStream fis = new FileInputStream(
                    //"C:\\Users\\Muellerb.ZB_MED\\Documents\\BioASQ2019\\BioASQ-SampleDataA.json"
                    //"C:\\Users\\Muellerb.ZB_MED\\Documents\\BioASQ2019\\allMeSH_2019\\allMeSH_2019.json"
            		inputFilePath
            );
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            log.info("Reading File line by line using BufferedReader");

            String line = reader.readLine();
            int counter = 0;
            while(line != null){
                line = reader.readLine();
                if (line!= null) {

                    if (line.length() > 2) {
                            BioASQDocument bod = new BioASQDocument();
                            bod = BioASQDocument.parseJSON(line);
                            if (!bod.getYear().equals("null"))
                                if (Integer.parseInt(bod.getYear())>yearfilter)
                                    sbod.add(bod);
                        if (counter++%100000==0) {
                            log.info("#" + counter);
                            writeDocuments2MongoDB(sbod,outputFilePath, yearfilter);
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

            if (sbod.size()>0) {
            	writeDocuments2MongoDB(sbod,outputFilePath, yearfilter);
            }
            log.info("Done reading documents...");
            closeMongoConnection();
            reader.close();
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
    
    public static void setupMongoConnection () {
    	log.info("Setting up MongoDB connection");
    	String host = "mongodb://snoke:27017";
    	String db = "medline";
    	String collection = "bioasq2019to2020";
    	log.info("\t host: " + host);
    	log.info("\t db: " + db);
    	log.info("\t collection: " + collection);
    	mongoClient = new MongoClient(new MongoClientURI(host));
    	database = mongoClient.getDatabase(db);
    	coll = database.getCollection(collection);
    	
    }
    
    public static void closeMongoConnection () {
    	log.info("Closing connection to MongoDB");
    	mongoClient.close();
    }
    
    public static List<String> preProcess (String p) {
        EnglishSentenceSplitter ess = new EnglishSentenceSplitter();
        List <String> sentences = ess.splitParagraph(p);
        return sentences;
    }

    public static Document convertBioASQDocument2MongoDbDocument (BioASQDocument bdoc) {
    	
		Document doc = new Document();
		doc.append("AbstractText", bdoc.getAbstractText());
		doc.append("MeshMajor", bdoc.getMeshMajor());
		doc.append("Journal", bdoc.getJournal());
		doc.append("pmid", bdoc.getPmid());
		doc.append("Title", bdoc.getTitle());
		doc.append("Year", bdoc.getYear());
		doc.append("Paragraph", bdoc.getTitle() + "\t\t" + bdoc.getAbstractText());
		String meshlabels = "";
		int length = bdoc.getLabels().size();
		int counter = 0;
		for (String l : bdoc.getLabels()) {
			meshlabels += l;
			if (counter++ < length-1) {
				meshlabels += "; ";
			}
		}
		doc.append("MeSH", meshlabels);
		return doc;
    }
    
    public static void writeDocuments2MongoDB (List <BioASQDocument> sbod, String basepath, int yearfilter) {

    	log.info("Processing to write #documents: " + sbod.size());
        Iterator<BioASQDocument> biter = sbod.iterator();
        List <Document> docs = new ArrayList <Document> ();
        while (biter.hasNext()) {
            BioASQDocument bsd = biter.next();
            if (bsd.getYear()!= null) {
                if (!bsd.getYear().equals("null")) {
                	Document doc = convertBioASQDocument2MongoDbDocument (bsd);
                	docs.add(doc);
                }
            }
        }
        if (docs.size()>0) {
        	log.info("Writing to MongoDb #documents: " + docs.size());
        	coll.insertMany(docs);
        } else {
        	log.info("Skipping to write because nothing is there...");
        }
        

    }
}
