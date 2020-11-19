package de.zbmed.snoke.ontology.analysis;

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
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

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
 * OntologyVennDiagramm is for generating set statistics for the epilepsy dictionaries
 * 
 * @author Muellerb
 * @since 2020
 */
public class OntologyVennDiagramm {
	private static final Logger log = LoggerFactory.getLogger(OntologyVennDiagramm.class);
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		OntologyVennDiagramm ovd = new OntologyVennDiagramm ();
		Map <String, Set <String>> mepso = ovd.getMapForDict("dictionaries/Dict_EpSO.xml");
		Map <String, Set <String>> messo = ovd.getMapForDict("dictionaries/Dict_ESSO.xml");
		Map <String, Set <String>> mepilont = ovd.getMapForDict("dictionaries/Dict_EPILONT.xml");
		Map <String, Set <String>> mepisem = ovd.getMapForDict("dictionaries/Dict_EPISEM.xml");
		Map <String, Set <String>> mfenics = ovd.getMapForDict("dictionaries/Dict_FENICS.xml");
		
		int area1 = mepso.keySet().size();
		int area2 = messo.keySet().size();
		int area3 = mepilont.keySet().size();
		int area4 = mepisem.keySet().size();
		int area5 = mfenics.keySet().size();
		
		log.info("EpSO: " + area1);
		log.info("ESSO: " + area2);
		log.info("EPILONT: " + area3);
		log.info("EPISEM: " + area4);
		log.info("FENICS: " + area5);
		
		// EpSO
		Map <String, Set <String>> mepso_messo = ovd.createIntersection(mepso, messo);
		log.info("n12 EpSO and ESSO: " + mepso_messo.keySet().size());
		int n12 = mepso_messo.keySet().size();
		
		Map <String, Set <String>> mepso_mepilont = ovd.createIntersection(mepso, mepilont);
		log.info("n13 EpSO and EPILONT: " + mepso_mepilont.keySet().size());
		int n13 = mepso_mepilont.keySet().size();
		
		Map <String, Set <String>> mepso_mepisem = ovd.createIntersection(mepso, mepisem);
		log.info("n14 EpSO and EPISEM: " + mepso_mepisem.keySet().size());
		int n14 = mepso_mepisem.keySet().size();
		
		Map <String, Set <String>> mepso_mfenics = ovd.createIntersection(mepso, mfenics);
		log.info("n15 EpSO and FENICS: " + mepso_mfenics.keySet().size());
		int n15 = mepso_mfenics.keySet().size();
		
		
		// ESSO
		Map <String, Set <String>> messo_mepilont = ovd.createIntersection(messo, mepilont);
		log.info("n23 ESSO and EPILONT: " + messo_mepilont.keySet().size());
		int n23 = messo_mepilont.keySet().size();

		Map <String, Set <String>> messo_mepisem = ovd.createIntersection(messo, mepisem);
		log.info("n24 ESSO and EPISEM: " + messo_mepisem.keySet().size());
		int n24 = messo_mepisem.keySet().size();
		
		Map <String, Set <String>> messo_mfenics = ovd.createIntersection(messo, mfenics);
		log.info("n25 ESSO and FENICS: " + messo_mfenics.keySet().size());
		int n25 = messo_mfenics.keySet().size();
		
		
		// EPILONT
		Map <String, Set <String>> mepilont_mepisem = ovd.createIntersection(mepilont, mepisem);
		log.info("n34 EPILONT and EPISEM: " + mepilont_mepisem.keySet().size());
		int n34 = mepilont_mepisem.keySet().size();
		
		Map <String, Set <String>> mepilont_mfenics = ovd.createIntersection(mepilont, mfenics);
		log.info("n35 EPILONT and FENICS: " + mepilont_mfenics.keySet().size());
		int n35 = mepilont_mfenics.keySet().size();
		
		
		// EPISEM
		Map <String, Set <String>> mepisem_mfenics = ovd.createIntersection(mepisem, mfenics);
		log.info("n45 EPISEM and FENICS: " + mepisem_mfenics.keySet().size());
		int n45 = mepisem_mfenics.keySet().size();
		
		
		// n123
		Map <String, Set <String>> mepso_messo_mepilont = ovd.createIntersection(mepso_messo, mepilont);
		log.info("n123 EpSO and ESSO and EPILONT: " + mepso_messo_mepilont.keySet().size());
		int n123 = mepso_messo_mepilont.keySet().size();
		
		// n124
		Map <String, Set <String>> mepso_messo_mepisem = ovd.createIntersection(mepso_messo, mepisem);
		log.info("n124 EpSO and ESSO and EPISEM: " + mepso_messo_mepisem.keySet().size());
		int n124 = mepso_messo_mepisem.keySet().size();
		
		// n125
		Map <String, Set <String>> mepso_messo_mfenics = ovd.createIntersection(mepso_messo, mfenics);
		log.info("n125 EpSO and ESSO and FENICS: " + mepso_messo_mfenics.keySet().size());
		int n125 = mepso_messo_mfenics.keySet().size();
		
		// n134
		Map <String, Set <String>> mepso_mepilont_mepisem = ovd.createIntersection(mepso_mepilont, mepisem);
		log.info("n134 EpSO and EPILONT and EPISEM: " + mepso_mepilont_mepisem.keySet().size());
		int n134 = mepso_mepilont_mepisem.keySet().size();
		
		// n135
		Map <String, Set <String>> mepso_mepilont_mfenics = ovd.createIntersection(mepso_mepilont, mfenics);
		log.info("n135 EpSO and EPILONT and FENICS: " + mepso_mepilont_mfenics.keySet().size());
		int n135 = mepso_mepilont_mfenics.keySet().size();
		
		// n145
		Map <String, Set <String>> mepso_mepisem_mfenics = ovd.createIntersection(mepso_mepisem, mfenics);
		log.info("n145 EpSO and EPISEM and FENICS: " + mepso_mepisem_mfenics.keySet().size());
		int n145 = mepso_mepisem_mfenics.keySet().size();
		
		// n234
		Map <String, Set <String>> messo_mepilont_mepisem = ovd.createIntersection(messo_mepilont, mepisem);
		log.info("n234 ESSO and EPILONT and EPISEM: " + messo_mepilont_mepisem.keySet().size());
		int n234 = messo_mepilont_mepisem.keySet().size();
		
		// n235
		Map <String, Set <String>> messo_mepilont_mfenics = ovd.createIntersection(messo_mepilont, mfenics);
		log.info("n235 ESSO and EPILONT and FENICS: " + messo_mepilont_mfenics.keySet().size());
		int n235 = messo_mepilont_mfenics.keySet().size();
		
		// n245
		Map <String, Set <String>> messo_mepisem_mfenics = ovd.createIntersection(messo_mepisem, mfenics);
		log.info("n245 ESSO and EPISEM and FENICS: " + messo_mepisem_mfenics.keySet().size());
		int n245 = messo_mepisem_mfenics.keySet().size();
		
		// n345
		Map <String, Set <String>> mepilont_mepisem_mfenics = ovd.createIntersection(mepilont_mepisem, mfenics);
		log.info("n345 EPILONT and EPISEM and FENICS: " + mepilont_mepisem_mfenics.keySet().size());
		int n345 = mepilont_mepisem_mfenics.keySet().size();
		
		// n1234
		Map <String, Set <String>> mepso_messo_mepilont_mepisem = ovd.createIntersection(mepso_messo, mepilont_mepisem);
		log.info("n1234 EpSO and ESSO and EPILONT and EPISEM: " + mepso_messo_mepilont_mepisem.keySet().size());
		int n1234 = mepso_messo_mepilont_mepisem.keySet().size();
		
		// n1235
		Map <String, Set <String>> mepso_messo_mepilont_mfenics = ovd.createIntersection(mepso_messo, mepilont_mfenics);
		log.info("n1235 EpSO and ESSO and EPILONT and FENICS: " + mepso_messo_mepilont_mfenics.keySet().size());
		int n1235 = mepso_messo_mepilont_mfenics.keySet().size();
		
		// n1245
		Map <String, Set <String>> mepso_messo_mepisem_mfenics = ovd.createIntersection(mepso_messo, mepisem_mfenics);
		log.info("n1245 EpSO and ESSO and EPISEM and FENICS: " + mepso_messo_mepisem_mfenics.keySet().size());
		int n1245 = mepso_messo_mepisem_mfenics.keySet().size();
		
		// n1345
		Map <String, Set <String>> mepso_mepilont_mepisem_mfenics = ovd.createIntersection(mepso_mepilont, mepisem_mfenics);
		log.info("n1345 EpSO and EPILONT and EPISEM and FENICS: " + mepso_mepilont_mepisem_mfenics.keySet().size());
		int n1345 = mepso_mepilont_mepisem_mfenics.keySet().size();
		
		// n2345
		Map <String, Set <String>> messo_mepilont_mepisem_mfenics = ovd.createIntersection(messo_mepilont, mepisem_mfenics);
		log.info("n2345 ESSO and EPILONT and EPISEM and FENICS: " + messo_mepilont_mepisem_mfenics.keySet().size());
		int n2345 = messo_mepilont_mepisem_mfenics.keySet().size();
		
		// n12345
		Map <String, Set <String>> mepso_messo_mepilont_mepisem_mfenics = ovd.createIntersection(mepso_messo_mepilont, mepisem_mfenics);
		log.info("n12345 ESSO and EPILONT and EPISEM and FENICS: " + mepso_messo_mepilont_mepisem_mfenics.keySet().size());
		int n12345 = mepso_messo_mepilont_mepisem_mfenics.keySet().size();
	}
	
	
	Map <String, Set <String>> createIntersection (Map <String, Set <String>> m1, Map <String, Set <String>> m2) {
		Map <String, Set <String>> mintersection = new HashMap <String, Set <String>> ();
		for (String k1 : m1.keySet()) {
			Set <String> v1 = m1.get(k1);
			for (String k2 : m2.keySet()) {
				Set <String> v2 = m2.get(k2);
				if (v1.contains(k2) | v2.contains(k1) | Sets.intersection(v1, v2).size()>0) {
					mintersection.put(k1, Sets.union(v1, v2));
				}
			}			
			
		}
		
		return mintersection;
	}
	
	Map <String, Set <String>> getMapForDict (String dictFile) {
		Map <String, Set <String>> dictMap = new HashMap <String, Set <String>> ();
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		
		SnowballStemmer snow = new SnowballStemmer();
		
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
			
			log.info("#Keys: " + dictMap.keySet().size());
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
}
