package de.zbmed.tdm.lod.drugbank;

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

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
/**
 * DrugBankATCParser
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class DrugBankATCParser {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = builderFactory.newDocumentBuilder();

			// Document document = builder.parse(new FileInputStream(
			//		"/media/muellerb/Daten/EpilepsyData/Results with DrugBank target filter/drugbank.xml"));
			
			Document document = builder.parse(new FileInputStream(
					"resources/drugbank/drugbank_2018.xml"));
			
					
			// new FileInputStream("/home/muellerb/test.xml"));

			XPath xPath = XPathFactory.newInstance().newXPath();

			String expression = "/drugbank//drug";

			
			// PrintWriter writer = new PrintWriter("/home/muellerb/Desktop/atc.map", "UTF-8");
			PrintWriter writer = new PrintWriter("resources/drugbank/db-atc.map", "UTF-8");
			
			

			// read a string value
			// String email = xPath.compile(expression).evaluate(document);
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);

			Map <String, String> secondlevelatc = new HashMap <String, String>();
			
			Map <String, String> thirdlevelatc = new HashMap <String, String>();
			
			Map <String, String> fourthlevelatc = new HashMap <String, String>();
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				String atc = "";
				String atcname = "";
				String dbid = "";
				String drugname = "";
				Node n = nodeList.item(i);
				Boolean approved = false;
				NodeList nl = n.getChildNodes();
				for (int j = 0; j < nl.getLength(); j++) {
					Node subn = nl.item(j);
					// System.out.println(subn.getNodeName());
					if (subn.getNodeName().equals("drugbank-id")) {
						NamedNodeMap nnm = subn.getAttributes();
						for (int k = 0; k < nnm.getLength(); k++) {
							String at = nnm.item(k).getNodeName();
							if (at.equals("primary")) {
								dbid = subn.getTextContent();
							}
						}

					} else if (subn.getNodeName().equals("name")) {
						drugname = subn.getTextContent();
					} else if (subn.getNodeName().equals("atc-codes")) {
						NodeList subnlist = subn.getChildNodes();
						System.out.println("#a#\t" + subn.getTextContent());
						for (int k = 0; k < subnlist.getLength(); k++) {
							Node na = subnlist.item(k);
							System.out.println("#b#\t" + na.getTextContent());
							NamedNodeMap nnm = na.getAttributes();
							if (nnm != null) {
								for (int l = 0; l < nnm.getLength(); l++) {
									String at = nnm.item(l).getNodeName();
									System.out.println("#c#\t" + at);
									if (at.equals("code")) {
										atc = nnm.item(l).getNodeValue();
										System.out.println("#d#\t" + atc);
									}
								}
							}
							NodeList nlatc = na.getChildNodes();
							for (int m=0; m<nlatc.getLength(); m++) {
								Node natc = nlatc.item(m);
								String atcn = natc.getTextContent();
								System.out.println("b1" + atcn);
								NamedNodeMap nnmatc = natc.getAttributes();
								if (nnmatc != null) {
									for (int l = 0; l < nnmatc.getLength(); l++) {
										String at = nnmatc.item(l).getNodeName();
										System.out.println("#c1#\t" + at);
										if (at.equals("code")) {
											atc = nnmatc.item(l).getNodeValue().trim();
											System.out.println("#d1#\t" + atc);
											if (atc.length()==3) {
												secondlevelatc.put(atc, atcn);
											} else if (atc.length()==4) {
												thirdlevelatc.put(atc, atcn);
											} if (atc.length()==5) {
												fourthlevelatc.put(atc, atcn);
											}
										}
									}
								}
							}
							
							if (nlatc.getLength()>1) {
								Node natc = nlatc.item(nlatc.getLength()-2);
								if (natc != null) {
									atcname = natc.getTextContent();	
								}	
							}
							
							
						}

					} else if (subn.getNodeName().equals("groups")) {
						NodeList subnlist = subn.getChildNodes();
						System.out.println("#f#\t" + subn.getTextContent());
						if (subn.getTextContent().contains("approved")) {
							approved = true;
						}
						for (int k = 0; k < subnlist.getLength(); k++) {
							Node na = subnlist.item(k);
							System.out.println("#g#\t" + na.getTextContent());
						}
					}
				}
				if (atc.length()==7) {
					atc = atc.substring(0,5);
				}
				if (dbid.length()>0 && atc.length()>0) {
					writer.println(dbid + "\t" + atc + "\t" + atcname + "\t" + drugname + "\t" + approved.toString().toUpperCase());
				} else {
					System.err.println("No DB id or atc: " + dbid + " / " + atc);
				}
			}
			writer.close();
			
			PrintWriter writer2 = new PrintWriter("resources/drugbankatc-secondlevel.map", "UTF-8");
			for (String code : secondlevelatc.keySet()) {
				writer2.println(code + "\t" + secondlevelatc.get(code));
			}
			writer2.close();
			
			PrintWriter writer3 = new PrintWriter("resources/drugbankatc-thirdlevel.map", "UTF-8");
			for (String code : thirdlevelatc.keySet()) {
				writer3.println(code + "\t" + thirdlevelatc.get(code));
			}
			writer3.close();
			
			PrintWriter writer4 = new PrintWriter("resources/drugbankatc-fourthlevel.map", "UTF-8");
			for (String code : fourthlevelatc.keySet()) {
				writer4.println(code + "\t" + fourthlevelatc.get(code));
			}
			writer4.close();
			//System.out.println(dbid + "\t" + atc + "\t" + atcname);
			

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

}
