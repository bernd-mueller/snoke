package de.zbmed.snoke.ontology.drugbank;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.zbmed.snoke.util.SnowballStemmer;
/**
 * WhatizitAutomataFromDrugBank
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class WhatizitAutomataFromDrugBank {

	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		
		SnowballStemmer snow = new SnowballStemmer();
		
		
		try {
			builder = builderFactory.newDocumentBuilder();

			Document document = builder.parse(new FileInputStream(
					"resources/drugbank/drugbank_2018-2.xml"));
			// new FileInputStream("/home/muellerb/test.xml"));

			XPath xPath = XPathFactory.newInstance().newXPath();

			String expression = "/drugbank//drug";


			// read a string value
			// String email = xPath.compile(expression).evaluate(document);
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);
			String drugname = "";
			String synonyms = "";
			String drugbankid = "";
			
			Document dict_doc = builder.newDocument();
			Element rootElement = dict_doc.createElement("mwt");
			String namespace = "https://www.zbmed.de/z";
			String prefix = "xmlns:z";
			rootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", prefix, namespace);			
			Element et = dict_doc.createElement("template");			
			Element ezm = dict_doc.createElement("z:DrugBank");
			ezm.setTextContent("%0");
			Attr attr_ids = dict_doc.createAttribute("ids");
			attr_ids.setValue("%1");
			ezm.setAttributeNode(attr_ids);
			et.appendChild(ezm);
			rootElement.appendChild(et);
			dict_doc.appendChild(rootElement);
			

			
			for (int i = 0; i < nodeList.getLength(); i++) {
				
				Node n = nodeList.item(i);
				NodeList nl = n.getChildNodes();

				boolean drugnameadded = false;
				String identifier = "";
				Set <String> termsynonyms = new HashSet <String> ();
				for (int j = 0; j < nl.getLength(); j++) {
					boolean nameNotvisited = true;
					Node subn = nl.item(j);
					// System.out.println(subn.getNodeName());
					if (subn.getNodeName().equals("drugbank-id")) {
						NamedNodeMap nnm = subn.getAttributes();
						for (int a=0; a<nnm.getLength(); a++) {
							Node atnode = nnm.item(a);
							if (atnode.getNodeName().equals("primary") &&
									atnode.getNodeValue().equals("true")) {
								drugbankid = subn.getTextContent();
								identifier = drugbankid;
							}
						}
					} else if (subn.getNodeName().equals("name")) {
						if (drugnameadded) {
							termsynonyms.add(subn.getTextContent());
							drugnameadded = true;
							System.out.println("Processing " + subn.getTextContent() + "\t" + i);
						}
						
					} else if (subn.getNodeName().equals("synonyms")) {
						NodeList syn_nl = subn.getChildNodes();
						for (int k = 0; k < nl.getLength(); k++) {
							Node syn_n = syn_nl.item(k);
							if (syn_n != null) {
								String s = syn_n.getTextContent();
								if (s.trim().length()>0) {
									termsynonyms.add(s.trim());
								}
								String stemsyn = snow.doTheSnowballStem(s.trim());
								if (!stemsyn.equals(s.trim()) && stemsyn != null) {
									termsynonyms.add(stemsyn.trim());
								}
								
							}
							
						}
					}
				}
				
				Iterator <String> it = termsynonyms.iterator();
				while (it.hasNext()) {
					String termsyn = it.next();
					Element variant = dict_doc.createElement("t");
					Attr attr_base = dict_doc.createAttribute("p1");
					attr_base.setValue(identifier);
					variant.setAttributeNode(attr_base);
					variant.setTextContent(termsyn);
					rootElement.appendChild(variant);
				}
			}
			
			Element et2 = dict_doc.createElement("template");	
			et2.setTextContent("%0");
			rootElement.appendChild(et2);
			
			Element er = dict_doc.createElement("r");	
			er.setTextContent("&lt;z:[^&gt;]*&gt;(.*&lt;/z)!:[^&gt;]*>");
			rootElement.appendChild(er);
			
			Element er2 = dict_doc.createElement("r");	
			er2.setTextContent("&lt;protname[^&gt;]*&gt;(.*&lt;/protname)![^&gt;]*>");
			rootElement.appendChild(er2);
			
			Element er3 = dict_doc.createElement("r");	
			er3.setTextContent("&lt;/?[A-Za-z_0-9\\-]+(&gt;|[\\r\\n\\t ][^&gt;]+)");
			rootElement.appendChild(er3);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(dict_doc);
			StreamResult result = new StreamResult(new File("resources/drugbank/DrugBank.mwt"));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

			System.out.println("File saved!");

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
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
