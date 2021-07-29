package de.zbmed.snoke.ontology.mesh;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.jena.ontology.OntClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.zbmed.snoke.ontology.common.DictHandler;
import de.zbmed.snoke.ontology.common.Token;

/**
 * ConceptDictFromMeSH Converts an input MeSH XML file into a dictionary file in XML format that can be used
 * by the UIMA ConceptMapper. The MeSH XML file can be downloaded from 
 * https://www.nlm.nih.gov/databases/download/mesh.html (as of 2020-06-25).
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class ConceptDictFromMeSH extends DictHandler {
    private static final Logger log = LoggerFactory.getLogger(ConceptDictFromMeSH.class);
    static String inputFilePath = "";
    static String outputFilePath = "";
    static String outputMapFilePath = "";
    int stemsyncounter = 0;
    
    Set <Token> tokens = new HashSet <Token> ();
    
    /**
     * Read command line arguments for the input file and the outpt files
     * 
     * @param args separated command line parameters
     */
    public static void readCLI (String args[]) {
    	Options options = new Options();

        Option input = new Option("i", "input", true, "input file (MeSH XML file)");
        input.setRequired(true);
        options.addOption(input);

        Option output = new Option("od", "output dictionary", true, "output file (Dictionary as XML file)");
        output.setRequired(true);
        options.addOption(output);
        
        Option outputm = new Option("om", "output mapping", true, "output mapping file between MeSH ids and terms");
        outputm.setRequired(true);
        options.addOption(outputm);
        

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
        outputFilePath = cmd.getOptionValue("output dictionary");
        outputMapFilePath = cmd.getOptionValue("output mapping");
        
        log.info("Using parameters:");
        log.info("\tinput: " + inputFilePath);
        log.info("\toutput dictionary: " + outputFilePath);
        log.info("\toutput mapping: " + outputMapFilePath);
    }
	public static void main(String[] args) {
		ConceptDictFromMeSH cdm = new ConceptDictFromMeSH ();
		cdm.readCLI (args);
		cdm.processXML();
		
	}
	
	/**
	 * Read the MeSH descriptor file in XML format comprising XML parsing for extracting concepts with synonyms.
	 * Writes concepts with synonyms into ConceptMapper dictionary format.
	 */
	public void processXML () {
		String meshurl = "http://id.nlm.nih.gov/mesh/";
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		
		Map <String, String> mname2id = new HashMap <String, String> ();
		
		try {
			builder = builderFactory.newDocumentBuilder();
			builder.setEntityResolver(new DTDEntityResolver());

			// "D:\\eclipse-workspace\\TDM\\resources\\mesh\\desc2016.xml";
			String meshfilename = inputFilePath;

			log.info("Reading file\t\t\"" + meshfilename + "\"...");
			Document document = builder.parse(new FileInputStream(meshfilename));
			log.info("Read file\t\t\"" + meshfilename + "\" !");

			XPath xPath = XPathFactory.newInstance().newXPath();

			String expression = "/DescriptorRecordSet//DescriptorRecord";

			log.info("Executing expression\t\"" + expression + "\"...");
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);
			log.info("Executed expression\t\"" + expression + "\" !");


			for (int i = 0; i < nodeList.getLength(); i++) {
				Node no = nodeList.item(i);
				// System.out.println(n.getNodeName() + "\t" + n.getTextContent());
				NodeList nl = no.getChildNodes();

				String mid = "";
				String mname = "";
				Token t = new Token ();
				for (int j = 0; j < nl.getLength(); j++) {
					Node subn = nl.item(j);

					if (subn.getNodeName().equals("DescriptorUI")) {
						mid = subn.getTextContent();
						t.setCodeValue(meshurl + mid);
						t.setCodeType("MeSH");;
					} else if (subn.getNodeName().equals("ConceptList")) {
						NodeList nll = subn.getChildNodes();
						for (int k = 0; k < nll.getLength(); k++) {
							Node subnn = nll.item(k);
							NamedNodeMap nnm = subnn.getAttributes();
							if (nnm != null) {
								for (int l = 0; l < nnm.getLength(); l++) {
									String at = nnm.item(l).getNodeName();
									if (at.equals("PreferredConceptYN")) {
										String prefered = nnm.item(l).getNodeValue();
										log.info(prefered);
											NodeList nlll = subnn.getChildNodes();
											for (int m = 0; m < nlll.getLength(); m++) {
												Node subnnn = nlll.item(m);
												// System.out.println("----->"+subnnn.getNodeName());
												String conceptName = "";
												if (subnnn.getNodeName().equals("ConceptName")) {
													conceptName = subnnn.getTextContent();
													//System.out.println(conceptName);
													mname = conceptName;
													t.setCanonical(conceptName);
													if (!t.getSynonyms().contains(conceptName)) {
														t.getSynonyms().add(conceptName);
													}
													// snowball synonym counter: Added stemmed concept name as synonym
													String stemmedConceptName = snow.doTheSnowballStem(conceptName);
													if (!t.getSynonyms().contains(stemmedConceptName)) {
														stemsyncounter++;
														t.getSynonyms().add(stemmedConceptName);
													}
												} else if (subnnn.getNodeName().equals("TermList")) {
													NodeList nllll = subnnn.getChildNodes();
													for (int n = 0; n < nllll.getLength(); n++) {
														Node subnnnn = nllll.item(n);
														// System.out.println(subnnnn.getNodeName());
														if (subnnnn.getNodeName().equals("Term")) {
															// System.out.println(subnnnn.getNodeName());
															NodeList nlllll = subnnnn.getChildNodes();
															for (int o = 0; o < nlllll.getLength(); o++) {
																Node subnnnnn = nlllll.item(o);
																// System.out.println(subnnnnn.getNodeName());
																if (subnnnnn.getNodeName().equals("String")) {
																	String synonymTerm = subnnnnn.getTextContent();
																	t.getSynonyms().add(synonymTerm);
																	// snowball synonym counter: Added stemmed concept name as synonym
																	String stemmedSynonym = snow.doTheSnowballStem(synonymTerm);
																	if (!t.getSynonyms().contains(stemmedSynonym)) {
																		stemsyncounter++;
																		t.getSynonyms().add(stemmedSynonym);
																	}
																	
																}

															}
														}

													}
												}
											}
										//}
									}
								}
							}
						}

					}
					tokens.add(t);
				}
				mname2id.put(mname, mid);
			}
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter(outputMapFilePath));
                for (String k : mname2id.keySet()) {
                	String v = mname2id.get(k);
                	writer.write(k + "\t" + v + "\n");
                }
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
			
            this.createConceptMapperDict (tokens, outputFilePath, "MeSH");

			System.out.println("File saved!");
			
			System.out.println("Number of added stemmed synonyms: " + stemsyncounter);

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public Set<String> processPropertySeeAlso(OntClass oc, Set<String> synset) {
		// TODO Auto-generated method stub
		return null;
	}
}
