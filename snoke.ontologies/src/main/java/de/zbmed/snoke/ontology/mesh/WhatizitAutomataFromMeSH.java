package de.zbmed.snoke.ontology.mesh;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
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
 * WhatizitAutomataFromMeSH
 *
 * @author Alexandra Hagelstein
 * @version 0.1
 * @since 2016
 */
public class WhatizitAutomataFromMeSH {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		
		SnowballStemmer snow = new SnowballStemmer ();
		
		
		try {
			builder = builderFactory.newDocumentBuilder();
			builder.setEntityResolver(new DTDEntityResolver());
			
			Document dict_doc = builder.newDocument();
			Element rootElement = dict_doc.createElement("mwt");
			String namespace = "https://www.zbmed.de/z";
			String prefix = "xmlns:z";
			rootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", prefix, namespace);			
			Element et = dict_doc.createElement("template");			
			Element ezm = dict_doc.createElement("z:MeSH");
			ezm.setTextContent("%0");
			Attr attr_ids = dict_doc.createAttribute("ids");
			attr_ids.setValue("%1");
			ezm.setAttributeNode(attr_ids);
			et.appendChild(ezm);
			rootElement.appendChild(et);
			dict_doc.appendChild(rootElement);
			
			
			//String meshfilename = "D:\\eclipse-workspace\\TDM\\resources\\mesh\\desc2016.xml";
			String meshfilename = "resources/mesh/desc2019.xml";
			
			System.out.println("Reading file\t\t\"" + meshfilename + "\"...");
			Document document = builder.parse(new FileInputStream(meshfilename));
			System.out.println("Read file\t\t\"" + meshfilename + "\" !");

			XPath xPath = XPathFactory.newInstance().newXPath();

			String expression = "/DescriptorRecordSet//DescriptorRecord";



			System.out.println("Executing expression\t\"" + expression + "\"...");
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);
			System.out.println("Executed expression\t\"" + expression + "\" !");
			String diseasename = "";
			String synonyms = "";	
			Map <String, Set<String>> mwtmap = new HashMap <String, Set<String>> ();
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node no = nodeList.item(i);
				// System.out.println(n.getNodeName() + "\t" + n.getTextContent());
				NodeList nl = no.getChildNodes();
				
				String identifier = "";
				Set <String> termsynonyms = new HashSet <String> ();
				for (int j = 0; j < nl.getLength(); j++) {
					Node subn = nl.item(j);
					if(subn.getNodeName().equals("DescriptorUI")){						
						identifier = subn.getTextContent();
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
										// System.out.println(prefered);
										//if (prefered.equalsIgnoreCase("y")) {
											// System.out.println(prefered);
											NodeList nlll = subnn.getChildNodes();
											for (int m = 0; m < nlll.getLength(); m++) {
												Node subnnn = nlll.item(m);
												//System.out.println("----->"+subnnn.getNodeName());
												String conceptName = "";
												if (subnnn.getNodeName().equals("ConceptName")) {
													conceptName = subnnn.getTextContent();
													termsynonyms.add(conceptName);
												} else if (subnnn.getNodeName().equals("TermList")) {
													NodeList nllll = subnnn.getChildNodes();
													for (int n = 0; n < nllll.getLength(); n++) {
														Node subnnnn = nllll.item(n);
														//System.out.println(subnnnn.getNodeName());
														if (subnnnn.getNodeName().equals("Term")) {
															//System.out.println(subnnnn.getNodeName());
															NodeList nlllll = subnnnn.getChildNodes();
															for (int o = 0; o < nlllll.getLength(); o++) {
																Node subnnnnn = nlllll.item(o);
																//System.out.println(subnnnnn.getNodeName());
																if (subnnnnn.getNodeName().equals("String")) {
																	String synonymTerm = subnnnnn.getTextContent();
																	if (!conceptName.equals (synonymTerm) && synonymTerm != null) {
																		termsynonyms.add(synonymTerm.trim());
																	}
																	String stemsyn = snow.doTheSnowballStem(synonymTerm);
																	if (!stemsyn.equals(synonymTerm) && stemsyn != null) {
																		termsynonyms.add(stemsyn.trim());
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
				}
				/*
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
				*/
				mwtmap.put(identifier,  termsynonyms);
			}
			
			Map <String, String> synmap = new HashMap <String, String> ();
			for (String conc : mwtmap.keySet()) {
				Iterator <String> it = mwtmap.get(conc).iterator();
				while (it.hasNext()) {
					String termsyn = it.next();
					if (!synmap.containsKey(termsyn)) {
						synmap.put(termsyn, conc);
					} else {
						String curkey = synmap.get(termsyn);
						synmap.put(termsyn, curkey + "," + conc);
					}
				}
			}
			
			for (String cursyn : synmap.keySet()) {
				Element variant = dict_doc.createElement("t");
				Attr attr_base = dict_doc.createAttribute("p1");
				attr_base.setValue(synmap.get(cursyn));
				variant.setAttributeNode(attr_base);
				variant.setTextContent(cursyn);
				rootElement.appendChild(variant);
			}
			
			Element et2 = dict_doc.createElement("template");	
			et2.setTextContent("%0");
			rootElement.appendChild(et2);
			
			Element er = dict_doc.createElement("r");	
			er.setTextContent(">&lt;z:[^&gt;]*&gt;(.*&lt;/z)!:[^&gt;]*&gt;");
			rootElement.appendChild(er);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(dict_doc);
			StreamResult result = new StreamResult(new File("resources/mesh/curated-MeSH.mwt"));
			

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
