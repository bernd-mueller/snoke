package de.zbmed.snoke.neo4j.importer;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.glassfish.jersey.client.ClientResponse;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * ImporterForATC
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class ImporterForATC {
	String nodeType = "ATC";
	ClientResponse response;
	CypherControler cc;
	Map<String, String> atcdrugbankidmap = new TreeMap<String, String>();
	Map<String, String> atcidtoname = new HashMap<String, String>();
	Map<String, String> atcidtodrugname = new HashMap<String, String>();
	Map <String, Map<String, Map<String, String>>> rootTree = new HashMap <String, Map<String, Map<String, String>>> ();
	static Map <String, String> didToName;
	
	public void createRootNodes () {
		cc.createNode("ROOT", "ROOT", "ROOT");
	}
	

	


	
	public void doParse () {
				DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = null;
				try {
					builder = builderFactory.newDocumentBuilder();

					// Document document = builder.parse(new FileInputStream(
					//		"/media/muellerb/Daten/EpilepsyData/Results with DrugBank target filter/drugbank.xml"));
					
					String drugbankxmlfile = "resources" + File.separator + "drugbank"+ File.separator + "drugbank_2018.xml";
					System.out.println("Reading " + drugbankxmlfile + "...");
					Document document = builder.parse(new FileInputStream(
							drugbankxmlfile));
							
					// new FileInputStream("/home/muellerb/test.xml"));

					XPath xPath = XPathFactory.newInstance().newXPath();

					String expression = "/drugbank//drug";

					
					String outfile = "resources" + File.separator + 
							"drugbank" + File.separator + 
							"results" + File.separator +
							"test-db-atc.map";
					System.out.println("File " + outfile + " opened for writing results...");
					PrintWriter writer = new PrintWriter(outfile, "UTF-8");

					// read a string value
					// String email = xPath.compile(expression).evaluate(document);
					NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);

					for (int i = 0; i < nodeList.getLength(); i++) {
						
						String atc = "";
						String atcname = "";
						String dbid = "";
						String drugname = "";
						Node n = nodeList.item(i);
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

							}  else if (subn.getNodeName().equals("name")) {
								drugname = subn.getTextContent();
							} else if (subn.getNodeName().equals("atc-codes")) {
								
								NodeList subnl = subn.getChildNodes();
								for (int k=0; k<subnl.getLength(); k++) {
									Node curn = subnl.item(k);
									if (curn.getNodeName().equals("atc-code")) {
										String superatc = "";
										String parent = "ROOT";
										
										NamedNodeMap superatcatmap = curn.getAttributes();
										for (int z=0; z<superatcatmap.getLength(); z++) {
											superatc = superatcatmap.item(z).getTextContent();
											
										}
										NodeList atcnodelist = curn.getChildNodes();
										for (int l=0; l<atcnodelist.getLength(); l++) {
											Node atcnode = atcnodelist.item(l);
											if (atcnode.getNodeName().equals("level")) {
												String atctext = atcnode.getTextContent();
												NamedNodeMap atcatmap = atcnode.getAttributes();
												for (int m=0; m<atcatmap.getLength(); m++) {
													String atccode = atcatmap.item(m).getTextContent();
													System.out.println(drugname + " / " + superatc + " : " + atccode + " / " + atctext);
													cc.createNodeOrUpdate(atccode, atctext, atccode);
													cc.createRelation(parent, atccode, "isParentOf");
													parent = atccode;
												}
											}
											
										}
										if (superatc.length()>0) {
											cc.createNodeOrUpdate(dbid, drugname, superatc);
											cc.createRelation(parent, superatc, "isParentOf");
										}
										
									}						
								}
							}
						}
						if (atc.length()==7) {
							// atc = atc.substring(0,5);
						}
						if (dbid.length()>0 && atc.length()>0) {
							writer.println(dbid + "\t" + atc + "\t" + atcname);
							atcdrugbankidmap.put(atc, dbid);
							atcidtoname.put(atc, atcname);
							atcidtodrugname.put(atc, drugname);
						} else {
							System.err.println("No DB id or atc: " + dbid + " / " + atc);
						}
					}
					//System.out.println(dbid + "\t" + atc + "\t" + atcname);
					writer.close();

				} catch (Exception e) {
					e.printStackTrace();
				} 
	}
	public static void main(String[] args) {
		ImporterForATC ia = new ImporterForATC ();
		ia.cc = new CypherControler ("ATC");
		ia.cc.clearAllNodesFromDatabase();
		ia.createRootNodes();
		ia.doParse();

		try {
			ia.cc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
}