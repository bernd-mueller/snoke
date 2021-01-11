package de.zbmed.snoke.ontology.drugnames;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.jena.ontology.OntClass;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.zbmed.snoke.ontology.common.DictHandler;
import de.zbmed.snoke.ontology.common.SnowballStemmer;
import de.zbmed.snoke.ontology.common.Token;
/**
 * ConceptDictFromDrugBank
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class ConceptDictFromDrugNames extends DictHandler {
	
	public void processXML () {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		
		SnowballStemmer snow = new SnowballStemmer();
		
		System.out.println("Starting...");
		Set <Token> tokens = new HashSet <Token> ();
		
		try {
			builder = builderFactory.newDocumentBuilder();
			InputStream fileStream = new FileInputStream(this.inputFilePath);
			Document document = builder.parse(fileStream);
			
			
			// new FileInputStream("/home/muellerb/test.xml"));

			XPath xPath = XPathFactory.newInstance().newXPath();

			String expression = "/drugbank//drug";


			// read a string value
			// String email = xPath.compile(expression).evaluate(document);
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);


			
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node n = nodeList.item(i);
				NodeList nl = n.getChildNodes();

				String drugname = "";
				String drugbankid = "";		
				String drugbankidprimary = "";
				
				Token t = new Token ();
				Set <String> syns = new HashSet <String> ();
				t.setCodeType("DrugNames");
				
				for (int j = 0; j < nl.getLength(); j++) {
					Node subn = nl.item(j);
					// System.out.println(subn.getNodeName());
					if (subn.getNodeName().equals("drugbank-id")) {
						drugbankid = subn.getTextContent();
						NamedNodeMap nnm = subn.getAttributes();
						for (int a=0; a<nnm.getLength(); a++) {
							Node atnode = nnm.item(a);
							if (atnode.getNodeName().equals("primary") &&
									atnode.getNodeValue().equals("true")) {
								drugbankidprimary = subn.getTextContent();
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
								//if (s.trim().length()>3) {
								syns.add(s);
								//}								
							}
							
						}
					}
				}
				t.setCanonical(drugname);	
				if (drugbankidprimary.length()>1) {
					t.setCodeValue(drugbankidprimary);
				} else {
					t.setCodeValue(drugbankid);
				}
				t.setSynonyms(syns);
				tokens.add(t);
			}

			fileStream.close();
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
		this.createConceptMapperDict(tokens, this.outputFilePath, "DrugNames");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ConceptDictFromDrugNames cdd = new ConceptDictFromDrugNames ();
		cdd.readCLI(args);
		cdd.processXML();
		
	}

	@Override
	public Set<String> processPropertySeeAlso(OntClass oc, Set<String> synset) {
		// TODO Auto-generated method stub
		return null;
	}

}
