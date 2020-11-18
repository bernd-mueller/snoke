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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.zbmed.snoke.util.SnowballStemmer;


public class DictLoader {
	private static final Logger log = LoggerFactory.getLogger(DictLoader.class);
    static public String inputFilePath = "";
    DictLoader () {
    }
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
	
	Map <String, Set <String>> getMapForDict (String dictFile) {
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
