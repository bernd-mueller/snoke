package de.zbmed.tdm.lod.ctd;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

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
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * CTDDiseaseParser
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class CTDDiseaseParser {

	public void readCLI (String [] args) {
		
		// create Options object
		Options options = new Options();
		Option info = new Option( "info", "Parser for the Comparative Taxicogenomics Database (CTD)"+
				"written by Bernd Müller in 2015");
		options.addOption(info);
		
		Option ctd = Option.builder( "c" )
				.required(true)
				.desc("CTD Chemicals vocabulary CTD_chemicals.xml")
				.argName("ctd-file")
				.hasArg()
				.build();
		options.addOption(ctd);
		
		Option dr = Option.builder( "d" )
				.required(true)
				.desc("CTD Chemicals vocabulary CTD_chemicals.xml")
				.argName("ctd-file")
				.hasArg()
				.build();
		options.addOption(dr);
		
		Option out = Option.builder( "o" )
				.required(true)
				.desc("output file")
				.argName("output-file")
				.hasArg()
				.build();
		options.addOption(out);
		
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine cmd = parser.parse(options, args);
			
			if (cmd.hasOption("c") && cmd.hasOption("o")) {
				parseCTDForDrugBankMeSHMapping (cmd.getOptionValue("c"), cmd.getOptionValue("d"), cmd.getOptionValue("o"));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(this.getClass().getName(), options );
		}
	}
	
	public void parseCTDForDrugBankMeSHMapping (String ctdfile, String drugbankMeSHmapping, String outfile) {
		System.err.println("Opening drugbank map from " + drugbankMeSHmapping+"...");
		Map <String, String> drugmap = new CTDDiseaseParser().readDrugBankMap (drugbankMeSHmapping);
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = builderFactory.newDocumentBuilder();

			
			System.err.println("Opening intput file from " + ctdfile+"...");
			Document document = builder.parse(new FileInputStream(ctdfile));
					
			// new FileInputStream("/home/muellerb/test.xml"));

			XPath xPath = XPathFactory.newInstance().newXPath();

			String expression = "/RowSet//Row";

			System.err.println(outfile);
			PrintWriter writer = new PrintWriter(outfile);

			// read a string value
			// String email = xPath.compile(expression).evaluate(document);
			System.err.println("Parsing...");
			int c=10;
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);
			for (int i = 0; i < nodeList.getLength(); i++) {
				if (i%c==0) {
					System.err.println("Processing node "+i+"/"+nodeList.getLength());
				}
				Node n = nodeList.item(i);
				NodeList nl = n.getChildNodes();
				String chemid = "";
				String disease = "";
				String diseaseID = "";
				boolean directEvidence = false;
				for (int j = 0; j < nl.getLength(); j++) {
					Node subn = nl.item(j);
					// System.out.println(subn.getNodeName());
					if (subn.getNodeName().equals("ChemicalID")) {
						chemid = subn.getTextContent();
					} else if (subn.getNodeName().equals("DiseaseName")) {
						disease = subn.getTextContent();
					} else if (subn.getNodeName().equals("DiseaseID")) {
						diseaseID = subn.getTextContent();
					} else if (subn.getNodeName().equals("DirectEvidence")) {
						if (subn.getTextContent().equalsIgnoreCase("therapeutic")) {
							directEvidence = true;
						}
					}
				}
				if (chemid.length() > 0 && 
						disease.length() > 0 && 
						drugmap.containsKey(chemid) &&
						directEvidence) {
					if (drugmap.get(chemid).contains("|")) {
						String [] d = drugmap.get(chemid).split("\\|");
						for (String s : d) {
							writer.println("MESH:"+chemid + "\t" + s + "\t" + diseaseID + "\t" + disease);
						}
					} else {
						writer.println("MESH:" + chemid + "\t" + drugmap.get(chemid) + "\t" + diseaseID + "\t" + disease);
					}

				}

			}

			writer.close();

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
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CTDDiseaseParser cp = new CTDDiseaseParser ();
		cp.readCLI(args);	
	}

	public Map<String, String> readDrugBankMap (String file) {
		Map <String, String> drugmap = new HashMap <String, String> ();
		
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = builderFactory.newDocumentBuilder();

			Document document = builder.parse(new FileInputStream(file));
			// new FileInputStream("/home/muellerb/test.xml"));

			XPath xPath = XPathFactory.newInstance().newXPath();

			String expression = "/RowSet//Row";



			// read a string value
			// String email = xPath.compile(expression).evaluate(document);
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);

			for (int i = 0; i < nodeList.getLength(); i++) {
				Node n = nodeList.item(i);
				NodeList nl = n.getChildNodes();
				String chemid = "";
				String drugids = "";
				for (int j = 0; j < nl.getLength(); j++) {
					Node subn = nl.item(j);
					// System.out.println(subn.getNodeName());
					if (subn.getNodeName().equals("ChemicalID")) {
						chemid = subn.getTextContent().replace("MESH:", "");

					} else if (subn.getNodeName().equals("DrugBankIDs")) {
						drugids = subn.getTextContent();
					}
				}
				if (chemid.length()>0 && drugids.length()>0) {
					drugmap.put(chemid, drugids);
				}
				
			}

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
		return drugmap;
	}
}
