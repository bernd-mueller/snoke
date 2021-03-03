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
					Set <String> union1 = m1.get(k1);
					Set <String> union2 = m2.get(k2);
					union1.addAll(union2);
					mintersection.put(k1 + "#SAMEAS#" + k2, union1);
				}
			}
		}
		
		return mintersection;
	}
	
	public void synchronizeMaps (
			Map <String, Set <String>> m1,
			Map <String, Set <String>> m2,
			Map <String, Set <String>> m3,
			Map <String, Set <String>> m4,
			Map <String, Set <String>> m5 
			) {
		
		
	}
	
	Map <String, Set<String>> addMatchingSynonyms (
			Map <String, Set <String>> m1, 
			Map <String, Set <String>> m2) {
		Map <String, Set <String>> res = new HashMap <String, Set <String>> (m1);
		
		for (String k1 : m1.keySet()) {
			Set <String> syn1 = m1.get(k1);
			for (String k2 : m2.keySet()) {
				Set <String> syn2 = m2.get(k2);
				Set <String> match = new HashSet <String> (syn1);
				match.retainAll(new HashSet <String>(syn2));
				if (match.size()>0) {
					Set <String> union = new HashSet <String> (syn1);
					union.addAll(syn2);
					res.put(k1, union);
				} 
			}
		}
		return res;
	}
	
	Map <String, Set<String>> createUnion (
			Map <String, Set <String>> m1, 
			Map <String, Set <String>> m2) {
		Map <String, Set <String>> munion = new HashMap <String, Set <String>> ();
		
		for (String k1 : m1.keySet()) {
			Set <String> syn1 = m1.get(k1);
			for (String k2 : m2.keySet()) {
				Set <String> syn2 = m2.get(k2);
				Set <String> match = new HashSet <String> (syn1);
				match.retainAll(new HashSet <String>(syn2));
				if (match.size()>0) {
					Set <String> union = new HashSet <String> (syn1);
					union.addAll(syn2);
					m1.put(k1, union);
					m2.put(k2, union);
				} 
			}
		}
		
		for (String k1 : m1.keySet()) {
			munion.put(k1, m1.get(k1));
		}
		for (String k2 : m2.keySet()) {
			munion.put(k2, m2.get(k2));
		}
		
		return munion;
	}
	
	Map <String, Set <String>> createTripleIntersection (
			Map <String, Set <String>> m1, 
			Map <String, Set <String>> m2,
			Map <String, Set <String>> m3) {
		
		
		Map <String, Set <String>> mintersection = new HashMap <String, Set <String>> (m1);
		mintersection.putAll(m2);
		mintersection.putAll(m3);
		
		mintersection = createIntersection (new HashMap<String, Set <String>>(mintersection), m1);
		mintersection = createIntersection (new HashMap<String, Set <String>>(mintersection), m2);
		mintersection = createIntersection (new HashMap<String, Set <String>>(mintersection), m3);
		
		
		return mintersection;
	}
	
	Map <String, Set <String>> createQuadIntersection (
			Map <String, Set <String>> m1, 
			Map <String, Set <String>> m2,
			Map <String, Set <String>> m3,
			Map <String, Set <String>> m4) {
			
		Map <String, Set <String>> mintersection = new HashMap <String, Set <String>> (m1);
		mintersection.putAll(m2);
		mintersection.putAll(m3);
		mintersection.putAll(m4);
		
		mintersection = createIntersection (new HashMap<String, Set <String>>(mintersection), m1);
		mintersection = createIntersection (new HashMap<String, Set <String>>(mintersection), m2);
		mintersection = createIntersection (new HashMap<String, Set <String>>(mintersection), m3);
		mintersection = createIntersection (new HashMap<String, Set <String>>(mintersection), m4);
			
		return mintersection;
	}
	
	Map <String, Set <String>> createQuintupleIntersection (
			Map <String, Set <String>> m1, 
			Map <String, Set <String>> m2,
			Map <String, Set <String>> m3,
			Map <String, Set <String>> m4,
			Map <String, Set <String>> m5) {
		
		Map <String, Set <String>> mintersection = new HashMap <String, Set <String>> (m1);
		mintersection.putAll(m2);
		mintersection.putAll(m3);
		mintersection.putAll(m4);
		mintersection.putAll(m5);
		
		mintersection = createIntersection (new HashMap<String, Set <String>>(mintersection), m1);
		mintersection = createIntersection (new HashMap<String, Set <String>>(mintersection), m2);
		mintersection = createIntersection (new HashMap<String, Set <String>>(mintersection), m3);
		mintersection = createIntersection (new HashMap<String, Set <String>>(mintersection), m4);
		mintersection = createIntersection (new HashMap<String, Set <String>>(mintersection), m5);
		
		return mintersection;
	}
	
	Map <String, Set <String>> calcComplement (
			Map <String, Set <String>> targetComplement, 
			Map <String, Set <String>> m2,
			Map <String, Set <String>> m3,
			Map <String, Set <String>> m4,
			Map <String, Set <String>> m5) {
		Map <String, Set <String>> superset = new HashMap <String, Set <String>> (m2);
		superset.putAll(m3);
		superset.putAll(m4);
		superset.putAll(m5);
		
		Map <String, Set <String>> targetComplementcopy = new HashMap <String, Set <String>> (targetComplement);
		Map <String, Set <String>> mintersection = new HashMap <String, Set <String>> (targetComplement);
		mintersection = createIntersection (new HashMap<String, Set <String>>(mintersection), superset);
		
		for (String keyinter : mintersection.keySet()) {
			Set<String> sinter = mintersection.get(keyinter);
			for (String keycompl : targetComplement.keySet()) {
				Set<String> scompl = targetComplement.get(keycompl);
				Set<String> sintercopy = new HashSet <String> (sinter);
				sintercopy.retainAll(scompl);
				if (sintercopy.size()>0) {
					targetComplementcopy.remove(keycompl);
				}
			}
		}
		
		return targetComplementcopy;
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
	
	double averageConceptPerSynonym (Map <String, Set <String>> m) {
		double c = .0;
		
		for (String k : m.keySet()) {
			Set <String> cursyns = new HashSet <String> (m.get(k));
			//synSet.add(k);
			c += cursyns.size();
		}
		return (c/m.keySet().size());
	}
	
	Set <String> createComplementSet (Set <String> toCompl, Set <String> compl) {
		Set <String> resSet = new HashSet <String> (toCompl);
		
		for (String s : compl) {
			resSet.remove(s);
		}
		return resSet;
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
	
	public Map <String, Set <String>> getMapForDictWithPrefix (String dictFile, String prefix) {
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
					dictMap.put(i+"" + prefix, tokens);
				} else {
					dictMap.put(conceptname + prefix, tokens);
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
