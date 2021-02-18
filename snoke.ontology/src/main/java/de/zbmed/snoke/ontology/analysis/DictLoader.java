package de.zbmed.snoke.ontology.analysis;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.jena.ext.com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.zbmed.snoke.ontology.common.SnowballStemmer;


/**
 * Loader for ConceptMapper dictionary files to convert concepts and synonyms into HashMaps
 * 
 * @author Muellerb
 *
 */
/**
 * @author Muellerb
 *
 */
/**
 * @author Muellerb
 *
 */
/**
 * @author Muellerb
 *
 */
public class DictLoader {
	private static final Logger log = LoggerFactory.getLogger(DictLoader.class);
    static public String inputFilePath = "";
    public DictLoader () {
    }
    
	/**
	 * Pass command line arguments for parsing
	 * 
	 * @param args
	 */
	public static void readCLI(String args[]) {
		Options options = new Options();

		Option input = new Option("i", "input", true, "input dictionary");
		input.setRequired(true);
		options.addOption(input);


		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd = null;

		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			formatter.printHelp("utility-name", options);

			System.exit(1);
		}

		inputFilePath = cmd.getOptionValue("input");

		log.info("Using parameters:");
		log.info("\tinput: " + inputFilePath);
	}
	
	/**
	 * Loads dictionary file into a HashMap with Synonyms as keys and Concept names as values
	 * 
	 * @param dictFile the dictionary file path
	 * @return
	 */
	public Map <String, String> getValueMapForDict (String dictFile) {
		Map <String, Set <String>> m = getMapForDict(dictFile);
		Map <String, String> vmap = new HashMap <String, String> ();
		for (String k : m.keySet()) {
			Set <String> s = m.get(k);
			for (String syn : s) {
				vmap.put(syn, k);
			}
		}
		return vmap;
	}
	
	/**
	 * Parses the XML file and saves concepts with synonyms as HashMap. Keys are generated as pattern
	 * with CodeValue and canonical separated by the sign "@"
	 * 
	 * @param dictFile the dictionary file path
	 * @return HashMap with concepts as keys and synonyms as values
	 */
	public Map <String, Set <String>> getCodeMapForDict (String dictFile) {
		Map <String, Set <String>> dictMap = new HashMap <String, Set <String>> ();
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		
		
		log.info("Reading " + dictFile + "...");
		try {
			builder = builderFactory.newDocumentBuilder();

			Document document = builder.parse(new FileInputStream(dictFile));
			// new FileInputStream("/home/muellerb/test.xml"));

			XPath xPath = XPathFactory.newInstance().newXPath();

			String expression = "/synonym//token";
			

			// read a string value
			// String email = xPath.compile(expression).evaluate(document);
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);
								
			for (int i = 0; i < nodeList.getLength(); i++) {
				String codevalue = "";
				String canonical = "";
				Node n = nodeList.item(i);
				
				NamedNodeMap atts = n.getAttributes();
				for (int j=0; j<atts.getLength(); j++) {
					Node an = atts.item(j);
					if (an.getNodeName().equals("CodeValue")) {
						codevalue = an.getNodeValue();
					} else if (an.getNodeName().equals("canonical")) {
						canonical = an.getNodeValue();
					}
				}
				Set <String> tokens = new HashSet <String> ();
				NodeList nl = n.getChildNodes();
				for (int j=0; j<nl.getLength(); j++) {
					Node nj = nl.item(j);
					if (nj.hasAttributes()) {
						NamedNodeMap atts_nj = nj.getAttributes();
						for (int k=0; k<atts_nj.getLength(); k++) {
							Node an = atts_nj.item(k);
							if (an.getNodeName().equals("base")) {
								tokens.add(an.getNodeValue());
							}
						}
					}
				}
				//System.out.println("i\t" + n.getNodeName());
				if (codevalue.equals("")) {
					dictMap.put(i+"@"+canonical, tokens);
				} else {
					dictMap.put(codevalue+"@"+canonical, tokens);
				}
			}
			
			log.info("" + dictMap.keySet().size());
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
		return dictMap;
	}
	/**
	 * Finds the intersection for two HashMap based on either same concepts or synonyms
	 * 
	 * @param m1 the first HashMap
	 * @param m2 the second HashMap
	 * @return HashMap with intersections
	 */
	Map <String, Set <String>> createIntersection (Map <String, Set <String>> m1, Map <String, Set <String>> m2) {
		Map <String, Set <String>> mintersection = new HashMap <String, Set <String>> ();
		
		for (String k1 : m1.keySet()) {
			Set <String> cursyn1 = new HashSet <String> (m1.get(k1));
			
			for (String k2 : m2.keySet()) {
				Set <String> cursyn2 = new HashSet <String> (m2.get(k2));
				
				Set <String> cursyn1copy = new HashSet <String> (cursyn1);
				
				cursyn1copy.add(k1);
				cursyn2.add(k2);
				
				cursyn1copy.retainAll(cursyn2);
				
				if (cursyn1copy.size()>0) {
					Set <String> unionset1 = new HashSet <String> (m1.get(k1));
//					unionset1.add(k1);
//					String k1canonical = k1;
//					if (k1.split("@").length>1) {
//						k1canonical = k1.split("@")[1];
//					}
//					unionset1.add(k1canonical);
					
					Set <String> unionset2 = new HashSet <String> (m2.get(k2));
//					unionset2.add(k2);
//					String k2canonical = k2;
//					if (k2.split("@").length>1) {
//						k2canonical = k2.split("@")[1];
//					}
//					unionset2.add(k2canonical);
					
					unionset1.addAll(unionset2);

					mintersection.put(k1 + "#SAMEAS#" + k2, unionset1);
				}
			}
		}
		
		return mintersection;
	}
	
	Map <String, Set <String>> createTripleIntersection (
			Map <String, Set <String>> m1, 
			Map <String, Set <String>> m2,
			Map <String, Set <String>> m3) {
		
		Map <String, Set <String>> mintersection = new HashMap <String, Set <String>> ();
		
		for (String k1 : m1.keySet()) {
			Set <String> cursyn1 = new HashSet <String> (m1.get(k1));
			
			for (String k2 : m2.keySet()) {
				Set <String> cursyn2 = new HashSet <String> (m2.get(k2));
				
				Set <String> cursyn1copy = new HashSet <String> (cursyn1);
				
				cursyn1copy.add(k1);
				cursyn2.add(k2);
				
				cursyn1copy.retainAll(cursyn2);
				
				if (cursyn1copy.size()>0) {
					
					Set <String> cursyn1copycopy = new HashSet <String> (cursyn1copy);
					
					for (String k3 : m3.keySet()) {
						Set <String> cursyn3 = new HashSet <String> (m3.get(k3));
						
						cursyn1copycopy.retainAll(cursyn3);
						
						if (cursyn1copycopy.size()>0) {
							Set <String> unionset1 = new HashSet <String> (m1.get(k1));
//							unionset1.add(k1);
//							String k1canonical = k1;
//							if (k1.split("@").length>1) {
//								k1canonical = k1.split("@")[1];
//							}
//							unionset1.add(k1canonical);
							
							Set <String> unionset2 = new HashSet <String> (m2.get(k2));
//							unionset2.add(k2);
//							String k2canonical = k2;
//							if (k2.split("@").length>1) {
//								k2canonical = k2.split("@")[1];
//							}
//							unionset2.add(k2canonical);
							
							Set <String> unionset3 = new HashSet <String> (m3.get(k3));
//							unionset3.add(k3);
//							String k3canonical = k3;
//							if (k3.split("@").length>1) {
//								k3canonical = k3.split("@")[1];
//							}
//							unionset3.add(k3canonical);
							
							unionset1.addAll(unionset2);
							unionset1.addAll(unionset3);

							mintersection.put(k1 + "#SAMEAS#" + k2 + "#SAMEAS#" + k3, unionset1);
						}
						

					}
					
				}
			}
		}
		
		return mintersection;
	}
	
	Map <String, Set <String>> createQuadIntersection (
			Map <String, Set <String>> m1, 
			Map <String, Set <String>> m2,
			Map <String, Set <String>> m3,
			Map <String, Set <String>> m4) {
		
		Map <String, Set <String>> mintersection = new HashMap <String, Set <String>> ();
		
		for (String k1 : m1.keySet()) {
			Set <String> cursyn1 = new HashSet <String> (m1.get(k1));
			
			for (String k2 : m2.keySet()) {
				Set <String> cursyn2 = new HashSet <String> (m2.get(k2));
				
				Set <String> cursyn1copy = new HashSet <String> (cursyn1);
				
				cursyn1copy.add(k1);
				cursyn2.add(k2);
				
				cursyn1copy.retainAll(cursyn2);
				
				if (cursyn1copy.size()>0) {
					
					Set <String> cursyn1copycopy = new HashSet <String> (cursyn1copy);
					
					for (String k3 : m3.keySet()) {
						Set <String> cursyn3 = new HashSet <String> (m3.get(k3));
						
						cursyn1copycopy.retainAll(cursyn3);
						
						if (cursyn1copycopy.size()>0) {
							
							Set <String> cursyn1copycopycopy = new HashSet <String> (cursyn1copycopy);
							
							for (String k4 : m4.keySet()) {
								Set <String> cursyn4 = new HashSet <String> (m4.get(k4));
								
								cursyn1copycopycopy.retainAll(cursyn4);
								
								if (cursyn1copycopycopy.size()>0) {
									Set <String> unionset1 = new HashSet <String> (m1.get(k1));
//									unionset1.add(k1);
//									String k1canonical = k1;
//									if (k1.split("@").length>1) {
//										k1canonical = k1.split("@")[1];
//									}
//									unionset1.add(k1canonical);
									
									Set <String> unionset2 = new HashSet <String> (m2.get(k2));
//									unionset2.add(k2);
//									String k2canonical = k2;
//									if (k2.split("@").length>1) {
//										k2canonical = k2.split("@")[1];
//									}
//									unionset2.add(k2canonical);
									
									Set <String> unionset3 = new HashSet <String> (m3.get(k3));
//									unionset3.add(k3);
//									String k3canonical = k3;
//									if (k3.split("@").length>1) {
//										k3canonical = k3.split("@")[1];
//									}
//									unionset3.add(k3canonical);
									
									Set <String> unionset4 = new HashSet <String> (m4.get(k4));
//									unionset4.add(k4);
//									String k4canonical = k4;
//									if (k4.split("@").length>1) {
//										k4canonical = k4.split("@")[1];
//									}
//									unionset4.add(k4canonical);
									
									
									unionset1.addAll(unionset2);
									unionset1.addAll(unionset3);
									unionset1.addAll(unionset4);
									
									mintersection.put(k1 + "#SAMEAS#" + k2 + "#SAMEAS#" + k3 + "#SAMEAS" + k4, unionset1);
								}
								
							}
						
						}
						

					}
					
				}
			}
		}
		
		return mintersection;
	}
	
	Map <String, Set <String>> createQuintupleIntersection (
			Map <String, Set <String>> m1, 
			Map <String, Set <String>> m2,
			Map <String, Set <String>> m3,
			Map <String, Set <String>> m4,
			Map <String, Set <String>> m5) {
		
		Map <String, Set <String>> mintersection = new HashMap <String, Set <String>> ();
		
		for (String k1 : m1.keySet()) {
			Set <String> cursyn1 = new HashSet <String> (m1.get(k1));
			
			for (String k2 : m2.keySet()) {
				Set <String> cursyn2 = new HashSet <String> (m2.get(k2));
				
				Set <String> cursyn1copy = new HashSet <String> (cursyn1);
				
				cursyn1copy.add(k1);
				cursyn2.add(k2);
				
				cursyn1copy.retainAll(cursyn2);
				
				if (cursyn1copy.size()>0) {
					
					Set <String> cursyn1copycopy = new HashSet <String> (cursyn1copy);
					
					for (String k3 : m3.keySet()) {
						Set <String> cursyn3 = new HashSet <String> (m3.get(k3));
						
						cursyn1copycopy.retainAll(cursyn3);
						
						if (cursyn1copycopy.size()>0) {
							
							Set <String> cursyn1copycopycopy = new HashSet <String> (cursyn1copycopy);
							
							for (String k4 : m4.keySet()) {
								Set <String> cursyn4 = new HashSet <String> (m4.get(k4));
								
								cursyn1copycopycopy.retainAll(cursyn4);
								
								if (cursyn1copycopycopy.size()>0) {
									Set <String> cursyn1copycopycopycopy = new HashSet <String> (cursyn1copycopycopy);
									
									for (String k5 : m5.keySet()) {
										Set <String> cursyn5 = new HashSet <String> (m5.get(k5));
										
										cursyn1copycopycopycopy.retainAll(cursyn5);
										
										if (cursyn1copycopycopycopy.size()>0) {

											Set <String> unionset1 = new HashSet <String> (m1.get(k1));
//											unionset1.add(k1);
//											String k1canonical = k1;
//											if (k1.split("@").length>1) {
//												k1canonical = k1.split("@")[1];
//											}
//											unionset1.add(k1canonical);
											
											Set <String> unionset2 = new HashSet <String> (m2.get(k2));
//											unionset2.add(k2);
//											String k2canonical = k2;
//											if (k2.split("@").length>1) {
//												k2canonical = k2.split("@")[1];
//											}
//											unionset2.add(k2canonical);
											
											Set <String> unionset3 = new HashSet <String> (m3.get(k3));
//											unionset3.add(k3);
//											String k3canonical = k3;
//											if (k3.split("@").length>1) {
//												k3canonical = k3.split("@")[1];
//											}
//											unionset3.add(k3canonical);
											
											Set <String> unionset4 = new HashSet <String> (m4.get(k4));
//											unionset4.add(k4);
//											String k4canonical = k4;
//											if (k4.split("@").length>1) {
//												k4canonical = k4.split("@")[1];
//											}
//											unionset4.add(k4canonical);
											
											Set <String> unionset5 = new HashSet <String> (m5.get(k5));
//											unionset5.add(k5);
//											String k5canonical = k5;
//											if (k5.split("@").length>1) {
//												k5canonical = k5.split("@")[1];
//											}
//											unionset5.add(k5canonical);
											
											
											unionset1.addAll(unionset2);
											unionset1.addAll(unionset3);
											unionset1.addAll(unionset4);
											unionset1.addAll(unionset5);
											
											mintersection.put(k1 + "#SAMEAS#" + k2 + "#SAMEAS#" + k3 + "#SAMEAS" + k4 + "#SAMEAS#" + k5, unionset1);
										}
									}
									
								}
								
							}
						
						}
						

					}
					
				}
			}
		}
		
		return mintersection;
	}
	
	Set <String> readPmidsFromFile (String filename) {
		Set <String> pmids = new HashSet <String> ();
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		       // process the line.
		    	pmids.add(line);
		    }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pmids;
	}
	
	Set <String> createSynSetFromMap (Map <String, Set <String>> m) {
		Set <String> synSet = new HashSet <String> ();
		
		for (String k : m.keySet()) {
			Set <String> cursyns = new HashSet <String> (m.get(k));
			//synSet.add(k);
			synSet.addAll(cursyns);
		}
		return synSet;
	}
	
	int countSynonyms (Map <String, Set <String>> m) {
		int c = 0;
		
		for (String k : m.keySet()) {
			Set <String> cursyns = new HashSet <String> (m.get(k));
			//synSet.add(k);
			c += cursyns.size();
		}
		return c;
	}
	
	double averageSynPerLabel (Map <String, Set <String>> m) {
		double c = .0;
		
		for (String k : m.keySet()) {
			Set <String> cursyns = new HashSet <String> (m.get(k));
			//synSet.add(k);
			c += cursyns.size();
		}
		return (c/m.keySet().size());
	}
	
	/**
	 * Parses the XML file and saves concepts with synonyms as HashMap. Keys are the canonical names of concepts.
	 * 
	 * @param dictFile dictionary file path
	 * @return
	 */
	public Map <String, Set <String>> getMapForDict (String dictFile) {
		Map <String, Set <String>> dictMap = new HashMap <String, Set <String>> ();
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		
		
		log.info("Reading " + dictFile + "...");
		try {
			builder = builderFactory.newDocumentBuilder();

			Document document = builder.parse(new FileInputStream(dictFile));
			// new FileInputStream("/home/muellerb/test.xml"));

			XPath xPath = XPathFactory.newInstance().newXPath();

			String expression = "/synonym//token";
			

			// read a string value
			// String email = xPath.compile(expression).evaluate(document);
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);
			int valCounter = 0;					
			for (int i = 0; i < nodeList.getLength(); i++) {
				String conceptname = "";
				Node n = nodeList.item(i);
				
				NamedNodeMap atts = n.getAttributes();
				for (int j=0; j<atts.getLength(); j++) {
					Node an = atts.item(j);
					if (an.getNodeName().equals("canonical")) {
						conceptname = an.getNodeValue();
					}
				}
				Set <String> tokens = new HashSet <String> ();
				NodeList nl = n.getChildNodes();
				for (int j=0; j<nl.getLength(); j++) {
					Node nj = nl.item(j);
					if (nj.hasAttributes()) {
						NamedNodeMap atts_nj = nj.getAttributes();
						for (int k=0; k<atts_nj.getLength(); k++) {
							Node an = atts_nj.item(k);
							if (an.getNodeName().equals("base")) {
								tokens.add(an.getNodeValue());
								valCounter++;
							}
						}
					}
				}
				//System.out.println("i\t" + n.getNodeName());
				if (conceptname.equals("")) {
					dictMap.put(i+"", tokens);
				} else {
					dictMap.put(conceptname, tokens);
				}
			}
			
			log.info("" + dictMap.keySet().size());
			log.info("#Values: " + valCounter);
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
		return dictMap;
	}
	public static void main(String[] args) {
		readCLI(args);

	}

}
