package de.zbmed.snoke.neo4j.importer;

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

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * ReadTreeForMeSH
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class ReadTreeForMeSH {/*
	String nodeType = "MeSH";
	CypherControler cc;
	WebResource resource;
	ClientResponse response;
	Map <String, Map> rootTree;
	
	static Map <String, String> didToName;
	
	static Map <String, String> treeToDid;
	
	public void createRootNodes () {
		
		cc.createNode("ROOT", "ROOT", "ROOT");
		
		
		rootTree = new HashMap <String, Map> ();
		
		rootTree.put("A", new HashMap<String, Map>());
		rootTree.put("B", new HashMap<String, Map>());
		rootTree.put("C", new HashMap<String, Map>());
		rootTree.put("D", new HashMap<String, Map>());
		rootTree.put("E", new HashMap<String, Map>());
		rootTree.put("F", new HashMap<String, Map>());
		rootTree.put("G", new HashMap<String, Map>());
		rootTree.put("H", new HashMap<String, Map>());
		rootTree.put("I", new HashMap<String, Map>());
		rootTree.put("J", new HashMap<String, Map>());
		rootTree.put("K", new HashMap<String, Map>());
		rootTree.put("L", new HashMap<String, Map>());
		rootTree.put("M", new HashMap<String, Map>());
		rootTree.put("N", new HashMap<String, Map>());
		rootTree.put("V", new HashMap<String, Map>());
		rootTree.put("Z", new HashMap<String, Map>());
		
		didToName = new HashMap <String, String> ();
		
		didToName.put("A", "Anatomy [A]");
		cc.createNode("A", "Anatomy [A]", "A");
		cc.createRelation("ROOT", "A", "isParentOf");
		
		didToName.put("B", "Organisms [B]");
		cc.createNode("B", "Organisms [B]", "B");
		cc.createRelation("ROOT", "B", "isParentOf");
		
		didToName.put("C", "Diseases [C]");
		cc.createNode("C", "Diseases [C]", "C");
		cc.createRelation("ROOT", "C", "isParentOf");
		
		didToName.put("D", "Chemicals Chemicals and Drugs [D]");
		cc.createNode("D", "Chemicals Chemicals and Drugs [D]", "D");
		cc.createRelation("ROOT", "D", "isParentOf");
		
		didToName.put("E", "Analytical, Diagnostic and Therapeutic Techniques and Equipment [E]");
		cc.createNode("E", "Analytical, Diagnostic and Therapeutic Techniques and Equipment [E]", "E");
		cc.createRelation("ROOT", "E", "isParentOf");
		
		didToName.put("F", "Psychiatry and Psychology [F]");
		cc.createNode("F", "Psychiatry and Psychology [F]", "F");
		cc.createRelation("ROOT", "F", "isParentOf");
		
		didToName.put("G", "Phenomena and Processes [G]");
		cc.createNode("G", "Phenomena and Processes [G]", "G");
		cc.createRelation("ROOT", "G", "isParentOf");
		
		didToName.put("H", "Disciplines and Occupations [H]");
		cc.createNode("H", "Disciplines and Occupations [H]", "H");
		cc.createRelation("ROOT", "H", "isParentOf");
		
		didToName.put("I", "Anthropology, Education, Sociology and Social Phenomena [I]");
		cc.createNode("I", "Anthropology, Education, Sociology and Social Phenomena [I]", "I");
		cc.createRelation("ROOT", "I", "isParentOf");
		
		didToName.put("J", "Technology, Industry, Agriculture [J]");
		cc.createNode("J", "Technology, Industry, Agriculture [J]", "J");
		cc.createRelation("ROOT", "J", "isParentOf");
		
		didToName.put("K", "Humanities [K]");
		cc.createNode("K", "Humanities [K]", "K");
		cc.createRelation("ROOT", "K", "isParentOf");
		
		didToName.put("L", "Information Science [L]");
		cc.createNode("L", "Information Science [L]", "L");
		cc.createRelation("ROOT", "L", "isParentOf");
		
		didToName.put("M", "Named Groups [M]");
		cc.createNode("M", "Named Groups [M]", "M");
		cc.createRelation("ROOT", "M", "isParentOf");
		
		didToName.put("N", "Health Care [N]");
		cc.createNode("N", "Health Care [N]", "N");
		cc.createRelation("ROOT", "N", "isParentOf");
		
		didToName.put("V", "Publication Characteristics [V]");
		cc.createNode("V", "Publication Characteristics [V]", "V");
		cc.createRelation("ROOT", "V", "isParentOf");
		
		didToName.put("Z", "Geographicals [Z]");
		cc.createNode("Z", "Geographicals [Z]", "Z");
		cc.createRelation("ROOT", "Z", "isParentOf");		
		
		treeToDid = new HashMap<String,String> ();
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub

				DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = null;
				
				
				ReadTreeForMeSH tdfm = new ReadTreeForMeSH ();
				tdfm.cc = new CypherControler("MeSH");
				tdfm.cc.clearAllNodesFromDatabase();
				tdfm.createRootNodes();
				
				
				try {
					builder = builderFactory.newDocumentBuilder();
					builder.setEntityResolver(new DTDEntityResolver());
					
//					String meshfilename = "D:\\eclipse-workspace\\TDM\\resources\\mesh\\desc2016.xml";
//					String meshfilename = "D:\\eclipse-workspace\\TDM\\resources\\mesh\\test.xml";
					String meshfilename = "resources/mesh/desc2018.xml";
					System.out.println("Reading file\t\t\"" + meshfilename + "\"...");
					Document document = builder.parse(new FileInputStream(meshfilename));
					System.out.println("Read file\t\t\"" + meshfilename + "\" !");

					XPath xPath = XPathFactory.newInstance().newXPath();

					String expression = "/DescriptorRecordSet//DescriptorRecord";



					System.out.println("Executing expression\t\"" + expression + "\"...");
					NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);
					System.out.println("Executed expression\t\"" + expression + "\" !");

					
					
					
					for (int i = 0; i < nodeList.getLength(); i++) {
						Node no = nodeList.item(i);
						// System.out.println(n.getNodeName() + "\t" + n.getTextContent());
						NodeList nl = no.getChildNodes();

						String descriptorUI = "";
						String conceptName = "";
						Set <String> treenodes = new HashSet<String>();
						
						// Element token = dict_doc.createElement("token");
						for (int j = 0; j < nl.getLength(); j++) {

							Node subn = nl.item(j);
							
							
							if (subn.getNodeName().equals("DescriptorUI")){
								descriptorUI = subn.getTextContent();
//								System.out.println(descriptorUI);
								
								
//								Attr attr_descrUI = dict_doc.createAttribute("CodeValue");
//								attr_descrUI.setValue(subn.getTextContent());
//								 token.setAttributeNode(attr_descrUI);
//								
//								Attr attr_codetype = dict_doc.createAttribute("CodeType");
//								attr_codetype.setValue("MeSH");
//								 token.setAttributeNode(attr_codetype);

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
//												System.out.println(prefered);
												if (prefered.equalsIgnoreCase("y")) {
													//System.out.println(prefered);
													NodeList nlll = subnn.getChildNodes();
													for (int m = 0; m < nlll.getLength(); m++) {
														Node subnnn = nlll.item(m);
														//System.out.println("----->"+subnnn.getNodeName());
														
														if (subnnn.getNodeName().equals("ConceptName")) {
															conceptName = subnnn.getTextContent();
															// System.out.println(conceptName);
															
//															Attr attr_canonical = dict_doc.createAttribute("canonical");
//															attr_canonical.setValue(conceptName);
//															token.setAttributeNode(attr_canonical);
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
//																				Element variant = dict_doc.createElement("variant");
//																				Attr attr_base = dict_doc.createAttribute("base");
//																				attr_base.setValue(synonymTerm.trim());
//																				variant.setAttributeNode(attr_base);
//																				token.appendChild(variant);
																			}
																		}
																		
																	}
																}
																
															}
														}
													}
												}
											}
										}
									}
								}

											
							} else if (subn.getNodeName().equals("TreeNumberList")) {
//								System.out.println(subn.getNodeName());
								NodeList nll = subn.getChildNodes();
								for (int l = 0; l < nll.getLength(); l++) {
									Node treeNumberNode = nll.item(l);
									String treenode = treeNumberNode.getTextContent().replaceAll("\\s", "");
									if (treenode.length()>0) {
										treenodes.add(treenode);
//										System.out.println(treenode);
									}
									
									
								}
								
								
							}								
						}
						
						if (treenodes.size() > 0 &&
								conceptName.length()>0 &&
								descriptorUI.length()>0) {

							tdfm.addNode(conceptName, descriptorUI, treenodes);
						}
						System.out.println("Concept "+ i + "/" + nodeList.getLength() + ":\t" +conceptName);
						
					}

					try {
						tdfm.cc.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
////					System.out.println("didToName: "+didToName.size());
//					for (String s : didToName.keySet()) {
////						System.out.println("didToName\t" + s + " " + didToName.get(s));
//					}
////					System.out.println("treeToDid: "+treeToDid.size());
//					for (String s : treeToDid.keySet()) {
////						System.out.println("treeToDid\t" + s + " " + treeToDid.get(s));
//					}
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
	
	public void addNode (String conceptName, String descriptorUi, Set <String> treenodes) {
		
		if (!didToName.containsKey(descriptorUi)) {
			didToName.put(descriptorUi, conceptName);
			
		}
		// this.createNodeOrUpdate("", "", "D03.438.221.173");
//		System.out.println(treenodes.size());

		for (String treenode : treenodes) {
			
//			System.out.println("#treenode: " + treenode);
			if (treenode.length() > 0 && !treeToDid.containsKey(treenode)) {
				
				
				
				treeToDid.put(treenode, descriptorUi);
				String rootNode = treenode.substring(0, 1);
//				System.out.println("#rootNode: " + rootNode);
				Map<String, Map> subCursor = rootTree.get(rootNode);

				
				if (treenode.contains(".")) {
					int end = treenode.split("\\.").length;
					if (subCursor.get(treenode.split("\\.")[0]) == null) {
						cc.createNodeOrUpdate("", "", treenode.split("\\.")[0]);
						cc.createRelation(rootNode, treenode.split("\\.")[0], "isParentOf");
					}
					String cursor = treenode.split("\\.")[0];
					String prev = "";
					for (int i = 1; i < end; i++) {
						String nodeLevel = treenode.split("\\.")[i];
						prev=cursor;
						if (i!=0) {
							cursor+="." + nodeLevel;
						} 
//						System.out.println(i + ": " + prev + "\t" + cursor);
						if (subCursor.containsKey(nodeLevel)) {
						} else {
							subCursor.put(nodeLevel, new HashMap<String, Map>());
//							t
						}
						subCursor = subCursor.get(nodeLevel);

						if (cursor.equals(treenode)) {
							cc.createNodeOrUpdate(descriptorUi, conceptName, treenode);
							
						} else {
							cc.createNodeOrUpdate("", "", cursor);
							
						}
						cc.createRelation(prev, cursor, "isParentOf");
						
					}
				}
			}

		}
	}
	private static String SERVER_ROOT_URI = "http://134.95.56.146:7474/db/data/";
	
	private void checkConnection () {
		WebResource resource = Client.create()
		        .resource(SERVER_ROOT_URI);
		ClientResponse response = resource.get( ClientResponse.class );

		System.out.println( String.format( "GET on [%s], status code [%d]",
		        SERVER_ROOT_URI, response.getStatus() ) );
		response.close();
	}*/
}
