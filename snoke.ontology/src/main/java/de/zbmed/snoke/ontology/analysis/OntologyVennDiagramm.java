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
		Map <String, Set <String>> mepso = ovd.getDl().getMapForDict("dictionaries/Dict_EpSO.xml");
		Map <String, Set <String>> messo = ovd.getDl().getMapForDict("dictionaries/Dict_ESSO.xml");
		Map <String, Set <String>> mepilont = ovd.getDl().getMapForDict("dictionaries/Dict_EPILONT.xml");
		Map <String, Set <String>> mepisem = ovd.getDl().getMapForDict("dictionaries/Dict_EPISEM.xml");
		Map <String, Set <String>> mfenics = ovd.getDl().getMapForDict("dictionaries/Dict_FENICS.xml");
		

		
		log.info("area1 = " + mepso.keySet().size() + ",");
		log.info("area2 = " + messo.keySet().size() + ",");
		log.info("area3 = " + mepilont.keySet().size() + ",");
		log.info("area4 = " + mepisem.keySet().size() + ",");
		log.info("area5 = " + mfenics.keySet().size() + ",");
		
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
		int n123 = mepso_messo_mepilont.keySet().size();
		log.info("n123 = " + n123 + ",");

		
		// n124
		Map <String, Set <String>> mepso_messo_mepisem = ovd.getDl().createTripleIntersection(mepso, messo, mepisem);
		int n124 = mepso_messo_mepisem.keySet().size();
		log.info("n124 = " + n124 + ",");

		
		// n125
		Map <String, Set <String>> mepso_messo_mfenics = ovd.getDl().createTripleIntersection(mepso, messo, mfenics);
		int n125 = mepso_messo_mfenics.keySet().size();
		log.info("n125 = " + n125 + ",");

		
		// n134
		Map <String, Set <String>> mepso_mepilont_mepisem = ovd.getDl().createTripleIntersection(mepso, mepilont, mepisem);
		int n134 = mepso_mepilont_mepisem.keySet().size();
		log.info("n134 = " + n134 + ",");
		
		// n135
		Map <String, Set <String>> mepso_mepilont_mfenics = ovd.getDl().createTripleIntersection(mepso, mepilont, mfenics);
		int n135 = mepso_mepilont_mfenics.keySet().size();
		log.info("n135 = " + n135 + ",");
		
		
		// n145
		Map <String, Set <String>> mepso_mepisem_mfenics = ovd.getDl().createTripleIntersection(mepso, mepisem, mfenics);
		int n145 = mepso_mepisem_mfenics.keySet().size();
		log.info("n145 = " + n145 + ",");
		
		// n234
		Map <String, Set <String>> messo_mepilont_mepisem = ovd.getDl().createTripleIntersection(messo, mepilont, mepisem);
		int n234 = messo_mepilont_mepisem.keySet().size();
		log.info("n234 = " + n234 + ",");
		
		// n235
		Map <String, Set <String>> messo_mepilont_mfenics = ovd.getDl().createTripleIntersection(messo, mepilont, mfenics);
		int n235 = messo_mepilont_mfenics.keySet().size();
		log.info("n235 = " + n235 + ",");

		
		// n245
		Map <String, Set <String>> messo_mepisem_mfenics = ovd.getDl().createTripleIntersection(messo, mepisem, mfenics);
		int n245 = messo_mepisem_mfenics.keySet().size();
		log.info("n245 = " + n245 + ",");
		
		// n345
		Map <String, Set <String>> mepilont_mepisem_mfenics = ovd.getDl().createTripleIntersection(mepilont, mepisem, mfenics);
		int n345 = mepilont_mepisem_mfenics.keySet().size();
		log.info("n345 = " + n345 + ",");
		
		// n1234
		Map <String, Set <String>> mepso_messo_mepilont_mepisem = ovd.getDl().createQuadIntersection(mepso, messo, mepilont, mepisem);
		int n1234 = mepso_messo_mepilont_mepisem.keySet().size();
		log.info("n1234 = " + n1234 + ",");
		
		// n1235
		Map <String, Set <String>> mepso_messo_mepilont_mfenics = ovd.getDl().createQuadIntersection(mepso, messo, mepilont, mfenics);
		int n1235 = mepso_messo_mepilont_mfenics.keySet().size();
		log.info("n1235 = " + n1235 + ",");
		
		// n1245
		Map <String, Set <String>> mepso_messo_mepisem_mfenics = ovd.getDl().createQuadIntersection(mepso, messo, mepisem, mfenics);
		int n1245 = mepso_messo_mepisem_mfenics.keySet().size();
		log.info("n1245 = " + n1245 + ",");
		
		// n1345
		Map <String, Set <String>> mepso_mepilont_mepisem_mfenics = ovd.getDl().createQuadIntersection(mepso, mepilont, mepisem, mfenics);
		int n1345 = mepso_mepilont_mepisem_mfenics.keySet().size();
		log.info("n1345 = " + n1345 + ",");
		
		// n2345
		Map <String, Set <String>> messo_mepilont_mepisem_mfenics = ovd.getDl().createQuadIntersection(messo, mepilont, mepisem, mfenics);
		int n2345 = messo_mepilont_mepisem_mfenics.keySet().size();
		log.info("n2345 = " + n2345 + ",");
		
		// n12345
		Map <String, Set <String>> mepso_messo_mepilont_mepisem_mfenics = ovd.getDl().createQuintupleIntersection(mepso, messo, mepilont, mepisem, mfenics);
		int n12345 = mepso_messo_mepilont_mepisem_mfenics.keySet().size();
		log.info("n12345 = " + n12345 + ",");
	}
	
	

}
