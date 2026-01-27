package de.zbmed.snoke.dl.json;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.nd4j.shade.jackson.core.JsonParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.nactem.tools.sentencesplitter.EnglishSentenceSplitter;

/**
 * ConvertJSON2MesHFolders - An input JSON file that contains Medline citations with MeSH labels is converted
 * into a folder structure where the MeSH labels build the subfolders with the text of citations as 
 * single files. The folder structure is created in order to have an appropriate input for training a 
 * Paragraph Vector model with labels. The class can be used as standalone runnable jar with command line
 * options.
 *   -i input file in JSON format with a bulk of Medline citations
 *   -o the root folder for the structure of subfolder where each name of a subfolder is a MeSH Heading
 *   -m maxdocs of documents to be processed
 *   -year filter publications being published after the given year
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2019
 */
public class ConvertJSON2MesHFolders {
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
            
            String inputFilePath = cmd.getOptionValue("input");
            String outputFilePath = cmd.getOptionValue("output");
            int maxdoc = -1;
            try {
                maxdoc = ((Number)cmd.getParsedOptionValue("maxdoc")).intValue();
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            int yearfilter = -1;
            try {
                yearfilter = ((Number)cmd.getParsedOptionValue("year")).intValue();
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            
            System.out.println("Using parameters:");
            System.out.println("\tinput: " + inputFilePath);
            System.out.println("\toutput: " + outputFilePath);
            System.out.println("\tyear: " + yearfilter);
            System.out.println("\tmaxdoc : " + maxdoc);
            

            Set<String> meshLabels = new HashSet<String>();

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
                                writeDocuments(sbod,outputFilePath, yearfilter);
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

                File opath = new File (outputFilePath); 
                if (!opath.exists()) {
                    opath.mkdirs();
                }
                File meshlabels = new File (outputFilePath +
                    File.separator +
                    "meshlabels.txt");

                BufferedWriter writer = new BufferedWriter(new FileWriter(meshlabels));
                log.debug("Writing meshlabels...");
                for (String label : meshLabels) {
                    writer.write(label + "\n");
                }

                writer.close();
                reader.close();
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);

            System.exit(1);
        }




    }
    public static List<String> preProcess (String p) {
        EnglishSentenceSplitter ess = new EnglishSentenceSplitter();
        List <String> sentences = ess.splitParagraph(p);
        return sentences;
    }

    public static void writeDocuments (List <BioASQDocument> sbod, String basepath, int yearfilter) {
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
                            "meshlabeldocs" +
                            Integer.toString(yearfilter) +
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
