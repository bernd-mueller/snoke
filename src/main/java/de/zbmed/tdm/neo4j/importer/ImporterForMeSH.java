package de.zbmed.tdm.neo4j.importer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.ws.rs.core.MediaType;
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

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import de.zbmed.tdm.lod.mesh.DTDEntityResolver;

/**
 * ImporterForMeSH
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class ImporterForMeSH {
	private static String SERVER_ROOT_URI = "http://134.95.56.146:7474/db/data/";
	
	private void checkConnection () {
		WebResource resource = Client.create()
		        .resource(SERVER_ROOT_URI);
		ClientResponse response = resource.get( ClientResponse.class );

		System.out.println( String.format( "GET on [%s], status code [%d]",
		        SERVER_ROOT_URI, response.getStatus() ) );
		response.close();
	}
	
	public void createNode (String nodeType, String nodeName, String conceptId) {
		
		
		this.sendCypher("CREATE (n:"+nodeType+" {name: '"+nodeName+"', conceptid: '"+conceptId+"'})");
	}
	
	public void createRelation (String nodeTypeA, String nodeTypeB, String nodeNameA, String nodeNameB, String relationName) {
		this.sendCypher(""
				+ "MATCH (a:"+nodeTypeA+"),(b:"+nodeTypeB+")"
				+ "WHERE a.name = '"+nodeNameA+"' AND b.name ='"+nodeNameB+"'"
				+ "CREATE (a)-[r:"+relationName+" { name : a.name + '<->' + b.name }]->(b)"
				+ "RETURN r");
	}
	private void sendCypher (String query) {
		final String txUri = SERVER_ROOT_URI + "transaction/commit";
		WebResource resource = Client.create().resource( txUri );

		String payload = "{\"statements\" : [ {\"statement\" : \"" +query + "\"} ]}";
		ClientResponse response = resource
		        .accept( MediaType.APPLICATION_JSON )
		        .type( MediaType.APPLICATION_JSON )
		        .entity( payload )
		        .post( ClientResponse.class );

		System.out.println( String.format(
		        "POST [%s] to [%s], status code [%d], returned data: "
		                + System.lineSeparator() + "%s",
		        payload, txUri, response.getStatus(),
		        response.getEntity( String.class ) ) );

		response.close();
	}
	
	private void clearAllMeSHNodesFromDatabase () {
		this.sendCypher("MATCH (n:MeSH) DETACH DELETE n");
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ImporterForMeSH im = new ImporterForMeSH ();
		im.clearAllMeSHNodesFromDatabase();

		
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		
		
		System.exit(0);
		
		try {
			builder = builderFactory.newDocumentBuilder();
			builder.setEntityResolver(new DTDEntityResolver());
			
			String meshfilename = "resources/mesh/desc2018.xml";
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
			
			Document dict_doc = builder.newDocument();
			Element rootElement = dict_doc.createElement("synonym");
			dict_doc.appendChild(rootElement);
			
			
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node no = nodeList.item(i);
				// System.out.println(n.getNodeName() + "\t" + n.getTextContent());
				NodeList nl = no.getChildNodes();

				Element token = dict_doc.createElement("token");
				for (int j = 0; j < nl.getLength(); j++) {
					Node subn = nl.item(j);
					if(subn.getNodeName().equals("DescriptorUI")){
//						String descriptorUI = subn.getTextContent();
//						System.out.println(descriptorUI);
						
						Attr attr_descrUI = dict_doc.createAttribute("CodeValue");
						attr_descrUI.setValue(subn.getTextContent());
						token.setAttributeNode(attr_descrUI);
						
						Attr attr_codetype = dict_doc.createAttribute("CodeType");
						attr_codetype.setValue("MeSH");
						token.setAttributeNode(attr_codetype);

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
										System.out.println(prefered);
										if (prefered.equalsIgnoreCase("y")) {
											System.out.println(prefered);
											NodeList nlll = subnn.getChildNodes();
											for (int m = 0; m < nlll.getLength(); m++) {
												Node subnnn = nlll.item(m);
												//System.out.println("----->"+subnnn.getNodeName());
												String conceptName = "";
												if (subnnn.getNodeName().equals("ConceptName")) {
													conceptName = subnnn.getTextContent();
													System.out.println(conceptName);
													Attr attr_canonical = dict_doc.createAttribute("canonical");
													attr_canonical.setValue(conceptName);
													token.setAttributeNode(attr_canonical);
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
																		Element variant = dict_doc.createElement("variant");
																		Attr attr_base = dict_doc.createAttribute("base");
																		attr_base.setValue(synonymTerm.trim());
																		variant.setAttributeNode(attr_base);
																		token.appendChild(variant);
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

									
					}
				}
				rootElement.appendChild(token);
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(dict_doc);
			StreamResult result = new StreamResult(new File("resources/mesh/test_Dict_MeSH.xml"));

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
