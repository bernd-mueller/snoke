package de.zbmed.snoke.ontology.drugbank;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

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
 * ConceptDictFromDrugBank
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class ConceptDictFromDrugBank {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

				DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = null;
				
				SnowballStemmer snow = new SnowballStemmer();
				
				
				try {
					builder = builderFactory.newDocumentBuilder();

					Document document = builder.parse(new FileInputStream(
							"resources/drugbank/drugbank_2018.xml"));
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
					Element rootElement = dict_doc.createElement("synonym");
					dict_doc.appendChild(rootElement);
					
					
					
					for (int i = 0; i < nodeList.getLength(); i++) {
						Node n = nodeList.item(i);
						NodeList nl = n.getChildNodes();

						Element token = dict_doc.createElement("token");
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
										Attr attr_codeval = dict_doc.createAttribute("CodeValue");
										attr_codeval.setValue(drugbankid);
										token.setAttributeNode(attr_codeval);	
										
										Attr attr_codetype = dict_doc.createAttribute("CodeType");
										attr_codetype.setValue("DrugBank");
										token.setAttributeNode(attr_codetype);
									}
								}
							} else if (subn.getNodeName().equals("name")) {
								drugname = subn.getTextContent();
								Attr attr_canonical = dict_doc.createAttribute("canonical");
								attr_canonical.setValue(drugname);
								token.setAttributeNode(attr_canonical);	
							} else if (subn.getNodeName().equals("synonyms")) {
								NodeList syn_nl = subn.getChildNodes();
								for (int k = 0; k < nl.getLength(); k++) {
									Node syn_n = syn_nl.item(k);
									if (syn_n != null) {
										String s = syn_n.getTextContent();
										if (s.trim().length()>0) {
											Element variant = dict_doc.createElement("variant");
											Attr attr_base = dict_doc.createAttribute("base");
											attr_base.setValue(s);
											variant.setAttributeNode(attr_base);
											token.appendChild(variant);
										}
										String stemsyn = snow.doTheSnowballStem(s.trim());
										if (!stemsyn.equals(s.trim()) && stemsyn != null) {
											Element variant = dict_doc.createElement("variant");
											Attr attr_base = dict_doc.createAttribute("base");
											attr_base.setValue(stemsyn.trim());
											variant.setAttributeNode(attr_base);
											token.appendChild(variant);
										}
										
									}
									
								}
							}
						}
						rootElement.appendChild(token);
					}
					TransformerFactory transformerFactory = TransformerFactory.newInstance();
					Transformer transformer = transformerFactory.newTransformer();
					transformer.setOutputProperty(OutputKeys.INDENT, "yes");
					DOMSource source = new DOMSource(dict_doc);
					StreamResult result = new StreamResult(new File("resources/drugbank/Dict_DrugBank2018.xml"));

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
