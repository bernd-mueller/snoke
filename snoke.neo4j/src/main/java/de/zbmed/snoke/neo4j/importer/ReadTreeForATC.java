package de.zbmed.snoke.neo4j.importer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.ws.rs.core.MediaType;
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
 * ReadTreeForATC
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class ReadTreeForATC {/*
	String nodeType = "ATC";
	WebResource resource;
	ClientResponse response;
	private static String SERVER_ROOT_URI = "http://134.95.56.146:7474/db/data/";
	Map<String, String> atcdrugbankidmap = new TreeMap<String, String>();
	Map<String, String> atcidtoname = new HashMap<String, String>();
	Map<String, String> atcidtodrugname = new HashMap<String, String>();
	Map <String, Map> rootTree = new HashMap <String, Map> ();
	static Map <String, String> didToName;
	
	public void createRootNodes () {
		createNode("ROOT", "ROOT", "ROOT");
		
//		rootTree = new HashMap <String, Map> ();
//		
//		rootTree.put("A", new HashMap<String, Map>());
//		rootTree.put("B", new HashMap<String, Map>());
//		rootTree.put("C", new HashMap<String, Map>());
//		rootTree.put("D", new HashMap<String, Map>());
//		rootTree.put("G", new HashMap<String, Map>());
//		rootTree.put("H", new HashMap<String, Map>());
//		rootTree.put("J", new HashMap<String, Map>());
//		
//		rootTree.put("L", new HashMap<String, Map>());
//		rootTree.put("M", new HashMap<String, Map>());
//		rootTree.put("N", new HashMap<String, Map>());
//		
//		rootTree.put("P", new HashMap<String, Map>());
//		rootTree.put("R", new HashMap<String, Map>());
//		rootTree.put("S", new HashMap<String, Map>());
//		rootTree.put("V", new HashMap<String, Map>());
//		
//		didToName = new HashMap <String, String> ();
//		
//		didToName.put("A", "Alimentary tract and metabolism");
//		this.createNode("A", "Alimentary tract and metabolism", "A");
//		this.createRelation("ROOT", "A", "isParentOf");
//		
//		didToName.put("B", "Blood and blood forming organs");
//		this.createNode("B", "Blood and blood forming organs", "B");
//		this.createRelation("ROOT", "B", "isParentOf");
//		
//		didToName.put("C", "Cardiovascular system");
//		this.createNode("C", "Cardiovascular system", "C");
//		this.createRelation("ROOT", "C", "isParentOf");
//		
//		didToName.put("D", "Dermatologicals");
//		this.createNode("D", "Dermatologicals", "D");
//		this.createRelation("ROOT", "D", "isParentOf");
//		
//		didToName.put("G", "Genito-urinary system and sex hormones");
//		this.createNode("G", "Genito-urinary system and sex hormones", "G");
//		this.createRelation("ROOT", "G", "isParentOf");
//		
//		didToName.put("H", "Systemic hormonal preparations, excluding sex hormones and insulins");
//		this.createNode("H", "Systemic hormonal preparations, excluding sex hormones and insulins", "H");
//		this.createRelation("ROOT", "H", "isParentOf");
//		
//		didToName.put("J", "Antiinfectives for systemic use");
//		this.createNode("J", "Antiinfectives for systemic use", "J");
//		this.createRelation("ROOT", "J", "isParentOf");
//		
//		didToName.put("L", "Antineoplastic and immunomodulating agents");
//		this.createNode("L", "Antineoplastic and immunomodulating agents", "L");
//		this.createRelation("ROOT", "L", "isParentOf");
//		
//		didToName.put("M", "Musculo-skeletal system");
//		this.createNode("M", "Musculo-skeletal system", "M");
//		this.createRelation("ROOT", "M", "isParentOf");
//		
//		didToName.put("N", "Nervous system");
//		this.createNode("N", "Nervous system", "N");
//		this.createRelation("ROOT", "N", "isParentOf");
//		
//		didToName.put("P", "Antiparasitic products, insecticides and repellents");
//		this.createNode("P", "Antiparasitic products, insecticides and repellents", "P");
//		this.createRelation("ROOT", "P", "isParentOf");
//		
//		didToName.put("R", "Respiratory system");
//		this.createNode("R", "Respiratory system", "R");
//		this.createRelation("ROOT", "R", "isParentOf");
//		
//		didToName.put("S", "Sensory organs");
//		this.createNode("S", "Sensory organs", "S");
//		this.createRelation("ROOT", "S", "isParentOf");
//		
//		didToName.put("V", "Various");
//		this.createNode("V", "Various", "V");
//		this.createRelation("ROOT", "V", "isParentOf");
	}
	
public void createNodeOrUpdate (String conceptId, String nodeName, String treeId) {
		if (nodeName != null && nodeName.length()==0) {
			nodeName = treeId;
		}
		if (conceptId != null && conceptId.length()==0) {
			conceptId = treeId;
		}
		
//		if (nodeName.length()>0 && conceptId.length()>0) {
//			System.out.println("asd");
			this.sendCypher("MERGE (n:"+nodeType+" {treeid: '"+treeId+"'})"
					+ "ON CREATE SET n.name = '"+nodeName+"', n.conceptid = '"+conceptId+"', n.treeid = '"+treeId+"'"
					+ "ON MATCH SET n.name = '"+nodeName+"', n.conceptid = '"+conceptId+"'"
					+ "RETURN n.name, n.treeid");
//		} else {
//			this.sendCypher("MERGE (n:"+nodeType+" {treeid: '"+treeId+"'})"
//					+ "ON CREATE SET n.treeid = '"+treeId+"', n.asd = 'qwe'"
//					+ "ON MATCH SET n.treeid = '"+treeId+"' n.asd = 'asd'"
//					+ "RETURN n.name, n.treeid");
//		}

	}
	
	public void createNode (String conceptId, String nodeName, String treeId) {
		

		this.sendCypher("CREATE (n:"+nodeType+" {name: '"+nodeName+"', conceptid: '"+conceptId+"', treeid: '"+treeId+"'})");
	}
	
	public void createRelation (String nodetreeidA, String nodetreeidB, String relationName) {
		this.sendCypher(""
				+ "MATCH (a:" + nodeType + "),(b:" + nodeType + ")"
				+ "WHERE a.treeid = '"+nodetreeidA+"' AND b.treeid ='"+nodetreeidB+"'"
				+ "CREATE UNIQUE (a)-[r:"+relationName+"]->(b)"
				+ "RETURN r");
	}
	private void sendCypher (String query) {


		String payload = "{\"statements\" : [ {\"statement\" : \"" +query + "\"} ]}";
		response = resource
		        .accept( MediaType.APPLICATION_JSON )
		        .type( MediaType.APPLICATION_JSON )
		        .entity( payload )
		        .post( ClientResponse.class );
		response.close();

//		System.out.println( String.format(
//		        "POST [%s] to [%s], status code [%d], returned data: "
//		                + System.lineSeparator() + "%s",
//		        payload, txUri, response.getStatus(),
//		        response.getEntity( String.class ) ) );

		
	}
	
	public void openCypher () {
		final String txUri = SERVER_ROOT_URI + "transaction/commit";
		resource = Client.create().resource( txUri );
	}
	
	public void closeCypher () {
		response.close();
	}

	private void clearAllATCNodesFromDatabase () {
		this.sendCypher("MATCH (n:" + nodeType + ") DETACH DELETE n");
	}
	
	public void doParse () {
		// TODO Auto-generated method stub
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
						String atcrootlevel = "";
						String atcsecondlevel = "";
						String atcthirdlevel = "";
						String atcfourthlevel = "";
						String atcfifthlevel = "";
						
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
													this.createNodeOrUpdate(atccode, atctext, atccode);
													this.createRelation(parent, atccode, "isParentOf");
													parent = atccode;
												}
											}
											
										}
										if (superatc.length()>0) {
											this.createNodeOrUpdate(dbid, drugname, superatc);
											this.createRelation(parent, superatc, "isParentOf");
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
		ReadTreeForATC ia = new ReadTreeForATC ();
		ia.openCypher();
		ia.clearAllATCNodesFromDatabase();
		ia.createRootNodes();
		ia.doParse();

		
//		
//		// 1.: Root level in createRootNodes () with 1 letter
//		String rootlevel = "";
//		
//		// 2.: Therapeutic main group with two digits: C03 Diuretics
//		String secondlevel = "";
//		
//		// 3.: Therapeutic/Pharmacological subgroup with one letter: C03C High-ceiling diuretics
//		String thirdlevel = "";
//		
//		// 4.: Chemical/Therapeutic/Pharmacological subgroup with one letter: C03CA Sulfonamides
//		String fourthlevel = "";
//		
//		// 5.: The chemical substancewith two digits: C03CA01 Furosemide
//		String fifthlevel = "";		
//		for (String treekey : ia.atcdrugbankidmap.keySet()) {
//		
//			//System.out.println(treekey + " / " + ia.atcdrugbankidmap.get(treekey));
//			rootlevel = "" + treekey.substring(0,1);
//			System.out.println(rootlevel);
//			secondlevel = rootlevel + treekey.substring(1,3);
//			System.out.println(secondlevel);
//			thirdlevel = secondlevel + treekey.substring(3,4);
//			System.out.println(thirdlevel);
//			fourthlevel = thirdlevel + treekey.substring(4,5);
//			System.out.println(fourthlevel);
//			fifthlevel = fourthlevel + treekey.substring(5,7);
//			System.out.println(fifthlevel);
//			
//			ia.createNodeOrUpdate(secondlevel, ia.atcidtoname.get(treekey).split("@")[1], secondlevel);
//			ia.createRelation(rootlevel, secondlevel, "isParentOf");
//			
//			ia.createNodeOrUpdate(thirdlevel, ia.atcidtoname.get(treekey).split("@")[2], thirdlevel);
//			ia.createRelation(secondlevel, thirdlevel, "isParentOf");
//			
//			ia.createNodeOrUpdate(fourthlevel, ia.atcidtoname.get(treekey).split("@")[3], fourthlevel);
//			ia.createRelation(thirdlevel, fourthlevel, "isParentOf");
//			
//						
//			ia.createNodeOrUpdate(ia.atcdrugbankidmap.get(treekey), ia.atcidtoname.get(treekey).split("@")[4], treekey);
//			ia.createRelation(fourthlevel, treekey, "isParentOf");
//			
//		}
		ia.closeCypher();
		
	}

	*/
}
