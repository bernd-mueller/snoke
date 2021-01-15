package de.zbmed.snoke.ontology.analysis;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import org.apache.jena.ext.com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.zbmed.snoke.ontology.common.SnowballStemmer;


/**
 * Loader for ConceptMapper dictionary files to convert concepts and synonyms into HashMaps
 * 
 * @author Muellerb
 *
 */
/**
 * @author Muellerb
 *
 */
/**
 * @author Muellerb
 *
 */
/**
 * @author Muellerb
 *
 */
public class DictLoader {
	private static final Logger log = LoggerFactory.getLogger(DictLoader.class);
    static public String inputFilePath = "";
    public DictLoader () {
    }
    
	/**
	 * Pass command line arguments for parsing
	 * 
	 * @param args
	 */
	public static void readCLI(String args[]) {
		Options options = new Options();

		Option input = new Option("i", "input", true, "input dictionary");
		input.setRequired(true);
		options.addOption(input);


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

		log.info("Using parameters:");
		log.info("\tinput: " + inputFilePath);
	}
	
	/**
	 * Loads dictionary file into a HashMap with Synonyms as keys and Concept names as values
	 * 
	 * @param dictFile the dictionary file path
	 * @return
	 */
	public Map <String, String> getValueMapForDict (String dictFile) {
		Map <String, Set <String>> m = getMapForDict(dictFile);
		Map <String, String> vmap = new HashMap <String, String> ();
		for (String k : m.keySet()) {
			Set <String> s = m.get(k);
			for (String syn : s) {
				vmap.put(syn, k);
			}
		}
		return vmap;
	}
	
	/**
	 * Parses the XML file and saves concepts with synonyms as HashMap. Keys are generated as pattern
	 * with CodeValue and canonical separated by the sign "@"
	 * 
	 * @param dictFile the dictionary file path
	 * @return HashMap with concepts as keys and synonyms as values
	 */
	public Map <String, Set <String>> getCodeMapForDict (String dictFile) {
		Map <String, Set <String>> dictMap = new HashMap <String, Set <String>> ();
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		
		
		log.info("Reading " + dictFile + "...");
		try {
			builder = builderFactory.newDocumentBuilder();

			Document document = builder.parse(new FileInputStream(dictFile));
			// new FileInputStream("/home/muellerb/test.xml"));

			XPath xPath = XPathFactory.newInstance().newXPath();

			String expression = "/synonym//token";
			

			// read a string value
			// String email = xPath.compile(expression).evaluate(document);
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);
								
			for (int i = 0; i < nodeList.getLength(); i++) {
				String codevalue = "";
				String canonical = "";
				Node n = nodeList.item(i);
				
				NamedNodeMap atts = n.getAttributes();
				for (int j=0; j<atts.getLength(); j++) {
					Node an = atts.item(j);
					if (an.getNodeName().equals("CodeValue")) {
						codevalue = an.getNodeValue();
					} else if (an.getNodeName().equals("canonical")) {
						canonical = an.getNodeValue();
					}
				}
				Set <String> tokens = new HashSet <String> ();
				NodeList nl = n.getChildNodes();
				for (int j=0; j<nl.getLength(); j++) {
					Node nj = nl.item(j);
					if (nj.hasAttributes()) {
						NamedNodeMap atts_nj = nj.getAttributes();
						for (int k=0; k<atts_nj.getLength(); k++) {
							Node an = atts_nj.item(k);
							if (an.getNodeName().equals("base")) {
								tokens.add(an.getNodeValue());
							}
						}
					}
				}
				//System.out.println("i\t" + n.getNodeName());
				if (codevalue.equals("")) {
					dictMap.put(i+"@"+canonical, tokens);
				} else {
					dictMap.put(codevalue+"@"+canonical, tokens);
				}
			}
			
			log.info("" + dictMap.keySet().size());
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
		return dictMap;
	}
	/**
	 * Finds the intersection for two HashMap based on either same concepts or synonyms
	 * 
	 * @param m1 the first HashMap
	 * @param m2 the second HashMap
	 * @return HashMap with intersections
	 */
	Map <String, Set <String>> createIntersection (Map <String, Set <String>> m1, Map <String, Set <String>> m2) {
		Map <String, Set <String>> mintersection = new HashMap <String, Set <String>> ();
		for (String k1 : m1.keySet()) {
			String k1canonical = k1;
			if (k1.split("@").length>1) {
				k1canonical = k1.split("@")[1];
			}
			Set <String> v1 = m1.get(k1);
			for (String k2 : m2.keySet()) {
				Set <String> v2 = m2.get(k2);
				String k2canonical = k2;
				if (k2.split("@").length>1) {
					k2canonical = k2.split("@")[1];
				}
				if (v1.contains(k2canonical) | v2.contains(k1canonical) | Sets.intersection(v1, v2).size()>0) {
					mintersection.put(k1 + "#SAMEAS#" + k2, Sets.union(v1, v2));
				}
			}			
			
		}
		
		return mintersection;
	}
	
	/**
	 * Parses the XML file and saves concepts with synonyms as HashMap. Keys are the canonical names of concepts.
	 * 
	 * @param dictFile dictionary file path
	 * @return
	 */
	public Map <String, Set <String>> getMapForDict (String dictFile) {
		Map <String, Set <String>> dictMap = new HashMap <String, Set <String>> ();
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		
		SnowballStemmer snow = new SnowballStemmer();
		
		log.info("Reading " + dictFile + "...");
		try {
			builder = builderFactory.newDocumentBuilder();

			Document document = builder.parse(new FileInputStream(dictFile));
			// new FileInputStream("/home/muellerb/test.xml"));

			XPath xPath = XPathFactory.newInstance().newXPath();

			String expression = "/synonym//token";
			

			// read a string value
			// String email = xPath.compile(expression).evaluate(document);
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);
			int valCounter = 0;					
			for (int i = 0; i < nodeList.getLength(); i++) {
				String conceptname = "";
				Node n = nodeList.item(i);
				
				NamedNodeMap atts = n.getAttributes();
				for (int j=0; j<atts.getLength(); j++) {
					Node an = atts.item(j);
					if (an.getNodeName().equals("canonical")) {
						conceptname = an.getNodeValue();
					}
				}
				Set <String> tokens = new HashSet <String> ();
				NodeList nl = n.getChildNodes();
				for (int j=0; j<nl.getLength(); j++) {
					Node nj = nl.item(j);
					if (nj.hasAttributes()) {
						NamedNodeMap atts_nj = nj.getAttributes();
						for (int k=0; k<atts_nj.getLength(); k++) {
							Node an = atts_nj.item(k);
							if (an.getNodeName().equals("base")) {
								tokens.add(an.getNodeValue());
								valCounter++;
							}
						}
					}
				}
				//System.out.println("i\t" + n.getNodeName());
				if (conceptname.equals("")) {
					dictMap.put(i+"", tokens);
				} else {
					dictMap.put(conceptname, tokens);
				}
			}
			
			log.info("" + dictMap.keySet().size());
			log.info("#Values: " + valCounter);
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
		return dictMap;
	}
	public static void main(String[] args) {
		readCLI(args);

	}

}
