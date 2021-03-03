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
	DictLoader dl;
	
	OntologyVennDiagramm () {
		dl = new DictLoader ();
	}
	public DictLoader getDl() {
		return dl;
	}
	public void setDl(DictLoader dl) {
		this.dl = dl;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		OntologyVennDiagramm ovd = new OntologyVennDiagramm ();
		Map <String, Set <String>> mepso = ovd.getDl().getMapForDictWithPrefix("dictionaries/Dict_EpSO.xml", "EpSO");
		Map <String, Set <String>> messo = ovd.getDl().getMapForDictWithPrefix("dictionaries/Dict_ESSO.xml", "ESSO");
		Map <String, Set <String>> mepilont = ovd.getDl().getMapForDictWithPrefix("dictionaries/Dict_EPILONT.xml", "EPILONT");
		Map <String, Set <String>> mepisem = ovd.getDl().getMapForDictWithPrefix("dictionaries/Dict_EPISEM.xml", "EPISEM");
		Map <String, Set <String>> mfenics = ovd.getDl().getMapForDictWithPrefix("dictionaries/Dict_FENICS.xml", "FENICS");
		

		
		log.info("area1 = " + mepso.keySet().size() + ",");
		log.info("area2 = " + messo.keySet().size() + ",");
		log.info("area3 = " + mepilont.keySet().size() + ",");
		log.info("area4 = " + mepisem.keySet().size() + ",");
		log.info("area5 = " + mfenics.keySet().size() + ",");
		
		Map <String, Set <String>> complepso = ovd.getDl().calcComplement (
				mepso,
				messo,
				mepilont,
				mepisem,
				mfenics
				);
		log.info("EpSO complement = " + complepso.keySet().size() + ",");
		
		Map <String, Set <String>> complesso = ovd.getDl().calcComplement (
				messo,
				mepso,
				mepilont,
				mepisem,
				mfenics
				);
		log.info("ESSO complement = " + complesso.keySet().size() + ",");
		
		Map <String, Set <String>> complepilont = ovd.getDl().calcComplement (
				mepilont,
				messo,
				mepso,
				mepisem,
				mfenics
				);
		log.info("EPILONT complement = " + complepilont.keySet().size() + ",");
		
		Map <String, Set <String>> complepisem = ovd.getDl().calcComplement (
				mepisem,
				messo,
				mepilont,
				mepso,
				mfenics
				);
		log.info("EPISEM complement = " + complepisem.keySet().size() + ",");
		
		Map <String, Set <String>> complfenics = ovd.getDl().calcComplement (
				mfenics,
				messo,
				mepilont,
				mepisem,
				mepso
				);
		log.info("FENICS complement = " + complfenics.keySet().size() + ",");
		
		
		
		
		
		
		
		// EpSO
		Map <String, Set <String>> mepso_messo = ovd.getDl().createIntersection(mepso, messo);
		int n12 = mepso_messo.keySet().size();
		log.info("n12 = " + n12 + ",");

		Map <String, Set <String>> mepso_mepilont = ovd.getDl().createIntersection(mepso, mepilont);
		int n13 = mepso_mepilont.keySet().size();
		log.info("n13 = " + n13 + ",");
		
		
		Map <String, Set <String>> mepso_mepisem = ovd.getDl().createIntersection(mepso, mepisem);
		int n14 = mepso_mepisem.keySet().size();
		log.info("n14 = " + n14 + ",");

		
		Map <String, Set <String>> mepso_mfenics = ovd.getDl().createIntersection(mepso, mfenics);
		int n15 = mepso_mfenics.keySet().size();
		log.info("n15 = " + n15 + ",");

		// ESSO
		Map <String, Set <String>> messo_mepilont = ovd.getDl().createIntersection(messo, mepilont);
		int n23 = messo_mepilont.keySet().size();
		log.info("n23 = " + n23 + ",");


		Map <String, Set <String>> messo_mepisem = ovd.getDl().createIntersection(messo, mepisem);
		int n24 = messo_mepisem.keySet().size();
		log.info("n24 = " + n24 + ",");

		
		Map <String, Set <String>> messo_mfenics = ovd.getDl().createIntersection(messo, mfenics);
		int n25 = messo_mfenics.keySet().size();
		log.info("n25 = " + n25 + ",");

		
		
		// EPILONT
		Map <String, Set <String>> mepilont_mepisem = ovd.getDl().createIntersection(mepilont, mepisem);
		int n34 = mepilont_mepisem.keySet().size();
		log.info("n34 = " + n34 + ",");

		
		Map <String, Set <String>> mepilont_mfenics = ovd.getDl().createIntersection(mepilont, mfenics);
		int n35 = mepilont_mfenics.keySet().size();
		log.info("n35 = " + n35 + ",");

		
		
		// EPISEM
		Map <String, Set <String>> mepisem_mfenics = ovd.getDl().createIntersection(mepisem, mfenics);
		int n45 = mepisem_mfenics.keySet().size();
		log.info("n45 = " + n45 + ",");

		
		
		// n123
		Map <String, Set <String>> mepso_messo_mepilont = ovd.getDl().createTripleIntersection(mepso, messo, mepilont);
		Set <String> k1 = new HashSet <String> ();
		for (String triple_k : mepso_messo_mepilont.keySet()) {
			if (triple_k.contains("#SAMEAS#")) {
				if (triple_k.split("#SAMEAS#").length==4) {
					String [] ksplit = triple_k.split("#SAMEAS#");
					k1.add(ksplit[1]);
					k1.add(ksplit[2]);
					k1.add(ksplit[3]);
				} else {
					log.error("inner n123: " + triple_k);
				}
			} else {
				log.error("outer n123: " + triple_k);
			}
			
		}
		int n123 = k1.size();
		log.info("n123 = " + n123 + ",");

		
		// n124
		Map <String, Set <String>> mepso_messo_mepisem = ovd.getDl().createTripleIntersection(mepso, messo, mepisem);
		k1 = new HashSet <String> ();
		for (String triple_k : mepso_messo_mepisem.keySet()) {
			if (triple_k.contains("#SAMEAS#")) {
				if (triple_k.split("#SAMEAS#").length==4) {
					String [] ksplit = triple_k.split("#SAMEAS#");
					k1.add(ksplit[1]);
					k1.add(ksplit[2]);
					k1.add(ksplit[3]);
				}
			} else {
				log.error("n14");
			}
			
		}
		int n124 = k1.size();
		log.info("n124 = " + n124 + ",");

		
		// n125
		Map <String, Set <String>> mepso_messo_mfenics = ovd.getDl().createTripleIntersection(mepso, messo, mfenics);
		k1 = new HashSet <String> ();
		for (String triple_k : mepso_messo_mfenics.keySet()) {
			if (triple_k.contains("#SAMEAS#")) {
				if (triple_k.split("#SAMEAS#").length==4) {
					String [] ksplit = triple_k.split("#SAMEAS#");
					k1.add(ksplit[1]);
					k1.add(ksplit[2]);
					k1.add(ksplit[3]);
				}
			}
			
		}
		int n125 = k1.size();
		log.info("n125 = " + n125 + ",");

		
		// n134
		Map <String, Set <String>> mepso_mepilont_mepisem = ovd.getDl().createTripleIntersection(mepso, mepilont, mepisem);
		k1 = new HashSet <String> ();
		for (String triple_k : mepso_mepilont_mepisem.keySet()) {
			if (triple_k.contains("#SAMEAS#")) {
				if (triple_k.split("#SAMEAS#").length==4) {
					String [] ksplit = triple_k.split("#SAMEAS#");
					k1.add(ksplit[1]);
					k1.add(ksplit[2]);
					k1.add(ksplit[3]);
				}
			}
			
		}
		int n134 = k1.size();
		log.info("n134 = " + n134 + ",");
		
		// n135
		Map <String, Set <String>> mepso_mepilont_mfenics = ovd.getDl().createTripleIntersection(mepso, mepilont, mfenics);
		k1 = new HashSet <String> ();
		for (String triple_k : mepso_mepilont_mfenics.keySet()) {
			if (triple_k.contains("#SAMEAS#")) {
				if (triple_k.split("#SAMEAS#").length==4) {
					String [] ksplit = triple_k.split("#SAMEAS#");
					k1.add(ksplit[1]);
					k1.add(ksplit[2]);
					k1.add(ksplit[3]);
				}
			}
			
		}
		int n135 = k1.size();
		log.info("n135 = " + n135 + ",");
		
		
		// n145
		Map <String, Set <String>> mepso_mepisem_mfenics = ovd.getDl().createTripleIntersection(mepso, mepisem, mfenics);
		k1 = new HashSet <String> ();
		for (String triple_k : mepso_mepisem_mfenics.keySet()) {
			if (triple_k.contains("#SAMEAS#")) {
				if (triple_k.split("#SAMEAS#").length==4) {
					String [] ksplit = triple_k.split("#SAMEAS#");
					k1.add(ksplit[1]);
					k1.add(ksplit[2]);
					k1.add(ksplit[3]);
				}
			}
			
		}
		int n145 = k1.size();
		log.info("n145 = " + n145 + ",");
		
		// n234
		Map <String, Set <String>> messo_mepilont_mepisem = ovd.getDl().createTripleIntersection(messo, mepilont, mepisem);
		k1 = new HashSet <String> ();
		for (String triple_k : messo_mepilont_mepisem.keySet()) {
			if (triple_k.contains("#SAMEAS#")) {
				if (triple_k.split("#SAMEAS#").length==4) {
					String [] ksplit = triple_k.split("#SAMEAS#");
					k1.add(ksplit[1]);
					k1.add(ksplit[2]);
					k1.add(ksplit[3]);
				}
			}
			
		}
		int n234 = k1.size();
		log.info("n234 = " + n234 + ",");
		
		// n235
		Map <String, Set <String>> messo_mepilont_mfenics = ovd.getDl().createTripleIntersection(messo, mepilont, mfenics);
		k1 = new HashSet <String> ();
		for (String triple_k : messo_mepilont_mfenics.keySet()) {
			if (triple_k.contains("#SAMEAS#")) {
				if (triple_k.split("#SAMEAS#").length==4) {
					String [] ksplit = triple_k.split("#SAMEAS#");
					k1.add(ksplit[1]);
					k1.add(ksplit[2]);
					k1.add(ksplit[3]);
				}
			}
			
		}
		int n235 = k1.size();
		log.info("n235 = " + n235 + ",");

		
		// n245
		Map <String, Set <String>> messo_mepisem_mfenics = ovd.getDl().createTripleIntersection(messo, mepisem, mfenics);
		k1 = new HashSet <String> ();
		for (String triple_k : messo_mepisem_mfenics.keySet()) {
			if (triple_k.contains("#SAMEAS#")) {
				if (triple_k.split("#SAMEAS#").length==4) {
					String [] ksplit = triple_k.split("#SAMEAS#");
					k1.add(ksplit[1]);
					k1.add(ksplit[2]);
					k1.add(ksplit[3]);
				}
			}
			
		}
		int n245 = k1.size();
		log.info("n245 = " + n245 + ",");
		
		// n345
		Map <String, Set <String>> mepilont_mepisem_mfenics = ovd.getDl().createTripleIntersection(mepilont, mepisem, mfenics);
		k1 = new HashSet <String> ();
		for (String triple_k : mepilont_mepisem_mfenics.keySet()) {
			if (triple_k.contains("#SAMEAS#")) {
				if (triple_k.split("#SAMEAS#").length==4) {
					String [] ksplit = triple_k.split("#SAMEAS#");
					k1.add(ksplit[1]);
					k1.add(ksplit[2]);
					k1.add(ksplit[3]);
				}
			}
			
		}
		int n345 = k1.size();
		log.info("n345 = " + n345 + ",");
		
		// n1234
		Map <String, Set <String>> mepso_messo_mepilont_mepisem = ovd.getDl().createQuadIntersection(mepso, messo, mepilont, mepisem);
		k1 = new HashSet <String> ();
		for (String triple_k : mepso_messo_mepilont_mepisem.keySet()) {
			if (triple_k.contains("#SAMEAS#")) {
				if (triple_k.split("#SAMEAS#").length==5) {
					String [] ksplit = triple_k.split("#SAMEAS#");
					k1.add(ksplit[1]);
					k1.add(ksplit[2]);
					k1.add(ksplit[3]);
					k1.add(ksplit[4]);
				}
			}
			
		}
		int n1234 = k1.size();
		log.info("n1234 = " + n1234 + ",");
		
		// n1235
		Map <String, Set <String>> mepso_messo_mepilont_mfenics = ovd.getDl().createQuadIntersection(mepso, messo, mepilont, mfenics);
		k1 = new HashSet <String> ();

		for (String triple_k : mepso_messo_mepilont_mfenics.keySet()) {
			if (triple_k.contains("#SAMEAS#")) {
				if (triple_k.split("#SAMEAS#").length==5) {
					String [] ksplit = triple_k.split("#SAMEAS#");
					k1.add(ksplit[1]);
					k1.add(ksplit[2]);
					k1.add(ksplit[3]);
					k1.add(ksplit[4]);
				}
			}
			
		}
		int n1235 = k1.size();
		log.info("n1235 = " + n1235 + ",");
		
		// n1245
		Map <String, Set <String>> mepso_messo_mepisem_mfenics = ovd.getDl().createQuadIntersection(mepso, messo, mepisem, mfenics);
		k1 = new HashSet <String> ();
		for (String triple_k : mepso_messo_mepisem_mfenics.keySet()) {
			if (triple_k.contains("#SAMEAS#")) {
				if (triple_k.split("#SAMEAS#").length==5) {
					String [] ksplit = triple_k.split("#SAMEAS#");
					k1.add(ksplit[1]);
					k1.add(ksplit[2]);
					k1.add(ksplit[3]);
					k1.add(ksplit[4]);
				}
			}
			
		}
		int n1245 = k1.size();
		log.info("n1245 = " + n1245 + ",");
		
		// n1345
		Map <String, Set <String>> mepso_mepilont_mepisem_mfenics = ovd.getDl().createQuadIntersection(mepso, mepilont, mepisem, mfenics);
		k1 = new HashSet <String> ();
		for (String triple_k : mepso_mepilont_mepisem_mfenics.keySet()) {
			if (triple_k.contains("#SAMEAS#")) {
				if (triple_k.split("#SAMEAS#").length==5) {
					String [] ksplit = triple_k.split("#SAMEAS#");
					k1.add(ksplit[1]);
					k1.add(ksplit[2]);
					k1.add(ksplit[3]);
					k1.add(ksplit[4]);
				}
			}
			
		}
		int n1345 = k1.size();
		log.info("n1345 = " + n1345 + ",");
		
		// n2345
		Map <String, Set <String>> messo_mepilont_mepisem_mfenics = ovd.getDl().createQuadIntersection(messo, mepilont, mepisem, mfenics);
		k1 = new HashSet <String> ();
		for (String triple_k : messo_mepilont_mepisem_mfenics.keySet()) {
			if (triple_k.contains("#SAMEAS#")) {
				if (triple_k.split("#SAMEAS#").length==5) {
					String [] ksplit = triple_k.split("#SAMEAS#");
					k1.add(ksplit[1]);
					k1.add(ksplit[2]);
					k1.add(ksplit[3]);
					k1.add(ksplit[4]);
				}
			}
			
		}
		int n2345 = k1.size();
		log.info("n2345 = " + n2345 + ",");
		
		// n12345
		Map <String, Set <String>> mepso_messo_mepilont_mepisem_mfenics = ovd.getDl().createQuintupleIntersection(mepso, messo, mepilont, mepisem, mfenics);
		k1 = new HashSet <String> ();
		for (String triple_k : mepso_messo_mepilont_mepisem_mfenics.keySet()) {
			if (triple_k.contains("#SAMEAS#")) {
				if (triple_k.split("#SAMEAS#").length==6) {
					String [] ksplit = triple_k.split("#SAMEAS#");
					k1.add(ksplit[1]);
					k1.add(ksplit[2]);
					k1.add(ksplit[3]);
					k1.add(ksplit[4]);
					k1.add(ksplit[5]);
				}
			}
			
		}
		int n12345 = k1.size();
		log.info("n12345 = " + n12345 + ",");
		
		int carea1 = n12 + n13 + n14 + n15 + n123 + n124 + n125 + n134 + n135 + n145 + n1234 + n1235 + n1245 + n1345 + n12345;
		
		int carea2 = n12 + n23 + n24 + n25 + n123 + n124 + n125 + n234 + n235 + n245 + n345 +n1234 + n1235 + n1245 + n2345 + n12345;
		
		int carea3 = n13 + n23 + n34 + n35 + n123 + n134 + n135 + n234 + n235 + n345 + n1234 + n1235 + n1345 + n2345 + n12345;
		
		int carea4 = n14 + n24 + n34 + n45 + n124 + n134 + n145 + n234 + n245 + n345 + n1234 + n1245 + n1345 + n12345;
		
		int carea5 = n15 + n25 + n35 + n45 + n125 + n135 + n145 + n235 + n245 + n345 + n1235 + n1245 + n1345 + n2345 + n12345;
		
		log.info("Corrected area1 = " + carea1 + ",");
		log.info("Corrected area2 = " + carea2 + ",");
		log.info("Corrected area3 = " + carea3 + ",");
		log.info("Corrected area4 = " + carea4 + ",");
		log.info("Corrected area5 = " + carea5 + ",");
		
		for (String k : mepso_messo_mepilont_mepisem.keySet()) {
			log.info(k);
			Set <String> synset = mepso_messo_mepilont_mepisem.get(k);
			for (String s : synset) {
				log.info("\t" + s);
			}
		}
	}
	
	

}
