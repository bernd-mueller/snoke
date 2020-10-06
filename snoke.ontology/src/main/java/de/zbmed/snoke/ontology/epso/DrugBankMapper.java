package de.zbmed.snoke.ontology.epso;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
/**
 * DrugBankMapper
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class DrugBankMapper {
	HashMap <String, HashMap <String, ArrayList <String>>> drugbankmap;
	
	DocumentBuilderFactory builderFactory;
	DocumentBuilder builder;
	Document document;
	XPath xPath;
	String expression = "/drugbank//drug";
	String defaultpath = "resources/drugbank/drugbank.xml";
	
	public DrugBankMapper () {
		constructFromXML (defaultpath);
	}
	
	public DrugBankMapper (String drugbankxmlfile) {
		
		constructFromXML (drugbankxmlfile);
	}
	
	public HashMap<String, HashMap<String, ArrayList<String>>> getDrugbankmap() {
		return drugbankmap;
	}

	public void setDrugbankmap(HashMap<String, HashMap<String, ArrayList<String>>> drugbankmap) {
		this.drugbankmap = drugbankmap;
	}

	public void constructFromXML (String drugbankxmlfile) {
		if (drugbankxmlfile == null) {
			drugbankxmlfile = defaultpath;
		}
		drugbankmap = new HashMap <String,HashMap<String, ArrayList <String>>> ();
		builderFactory = DocumentBuilderFactory.newInstance();
		try {
			builder = builderFactory.newDocumentBuilder();
			document = builder.parse(new FileInputStream(drugbankxmlfile));
			// new FileInputStream("/home/muellerb/test.xml"));

			xPath = XPathFactory.newInstance().newXPath();

			parseXML();
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
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
		}
	}

	public void parseXML () {
		// read a string value
		// String email = xPath.compile(expression).evaluate(document);
		NodeList nodeList;
		try {
			nodeList = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);

			

			for (int i = 0; i < nodeList.getLength(); i++) {
				String drugname = "";
				ArrayList <String> synonyms = new ArrayList <String> ();
				String drugbankid = "";
				Node n = nodeList.item(i);
				NodeList nl = n.getChildNodes();

				for (int j = 0; j < nl.getLength(); j++) {
					Node subn = nl.item(j);
					// System.out.println(subn.getNodeName());
					if (subn.getNodeName().equals("drugbank-id")) {
						NamedNodeMap nnm = subn.getAttributes();
						for (int a=0; a<nnm.getLength(); a++) {
							Node atnode = nnm.item(a);
							if (atnode.getNodeName().equals("primary") &&
									atnode.getNodeValue().equals("true")) {
								drugbankid = subn.getTextContent();
							}
						}
					} else if (subn.getNodeName().equals("name")) {
						drugname = subn.getTextContent();
					} else if (subn.getNodeName().equals("synonyms")) {
						NodeList syn_nl = subn.getChildNodes();
						for (int k = 0; k < nl.getLength(); k++) {
							Node syn_n = syn_nl.item(k);
							if (syn_n != null) {
								String s = syn_n.getTextContent();
								if (s.trim().length()>0) {
									synonyms.add(s);
								}
								
							}
							
						}
					}
				}
				HashMap <String, ArrayList <String>> drugnamesyn = new HashMap <String, ArrayList <String>> ();
				drugnamesyn.put(drugname, synonyms);
				drugbankmap.put(drugbankid, drugnamesyn);
			}
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		DrugBankMapper dbm = new DrugBankMapper ("D:\\eclipse-workspace\\TDM\\resources\\drugbank\\drugbank.xml");
	
		dbm.parseXML();
			
	}

}
