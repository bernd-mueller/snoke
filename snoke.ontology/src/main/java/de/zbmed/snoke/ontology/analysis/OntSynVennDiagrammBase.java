package de.zbmed.snoke.ontology.analysis;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OntSynVennDiagrammBase {
	private static final Logger log = LoggerFactory.getLogger(OntSynVennDiagrammBase.class);
	DictLoader dl;
	
	OntSynVennDiagrammBase () {
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
		
		
		OntSynVennDiagrammBase ovd = new OntSynVennDiagrammBase ();
		Map <String, Set <String>> mepso = ovd.getDl().getMapForDict("dictionaries/Dict_EpSO.xml");
		Map <String, Set <String>> messo = ovd.getDl().getMapForDict("dictionaries/Dict_ESSO.xml");
		Map <String, Set <String>> mepilont = ovd.getDl().getMapForDict("dictionaries/Dict_EPILONT.xml");
		Map <String, Set <String>> mepisem = ovd.getDl().getMapForDict("dictionaries/Dict_EPISEM.xml");
		Map <String, Set <String>> mfenics = ovd.getDl().getMapForDict("dictionaries/Dict_FENICS.xml");
		

		
		log.info("area1 = " + ovd.getDl().createSynSetFromMap(mepso).size() + ",");
		log.info("area2 = " + ovd.getDl().createSynSetFromMap(messo).size() + ",");
		log.info("area3 = " + ovd.getDl().createSynSetFromMap(mepilont).size() + ",");
		log.info("area4 = " + ovd.getDl().createSynSetFromMap(mepisem).size() + ",");
		log.info("area5 = " + ovd.getDl().createSynSetFromMap(mfenics).size() + ",");
		
		// EpSO
		Map <String, Set <String>> mepso_messo = ovd.getDl().createIntersection(mepso, messo);
		int n12 = ovd.getDl().createSynSetFromMap(mepso_messo).size();
		log.info("n12 = " + n12 + ",");

		Map <String, Set <String>> mepso_mepilont = ovd.getDl().createIntersection(mepso, mepilont);
		int n13 = ovd.getDl().createSynSetFromMap(mepso_mepilont).size();
		log.info("n13 = " + n13 + ",");
		
		
		Map <String, Set <String>> mepso_mepisem = ovd.getDl().createIntersection(mepso, mepisem);
		int n14 = ovd.getDl().createSynSetFromMap(mepso_mepisem).size();
		log.info("n14 = " + n14 + ",");

		
		Map <String, Set <String>> mepso_mfenics = ovd.getDl().createIntersection(mepso, mfenics);
		int n15 = ovd.getDl().createSynSetFromMap(mepso_mfenics).size();
		log.info("n15 = " + n15 + ",");

		// ESSO
		Map <String, Set <String>> messo_mepilont = ovd.getDl().createIntersection(messo, mepilont);
		int n23 = ovd.getDl().createSynSetFromMap(messo_mepilont).size();
		log.info("n23 = " + n23 + ",");


		Map <String, Set <String>> messo_mepisem = ovd.getDl().createIntersection(messo, mepisem);
		int n24 = ovd.getDl().createSynSetFromMap(messo_mepisem).size();
		log.info("n24 = " + n24 + ",");

		
		Map <String, Set <String>> messo_mfenics = ovd.getDl().createIntersection(messo, mfenics);
		int n25 = ovd.getDl().createSynSetFromMap(messo_mfenics).size();
		log.info("n25 = " + n25 + ",");

		
		
		// EPILONT
		Map <String, Set <String>> mepilont_mepisem = ovd.getDl().createIntersection(mepilont, mepisem);
		int n34 = ovd.getDl().createSynSetFromMap(mepilont_mepisem).size();
		log.info("n34 = " + n34 + ",");

		
		Map <String, Set <String>> mepilont_mfenics = ovd.getDl().createIntersection(mepilont, mfenics);
		int n35 = ovd.getDl().createSynSetFromMap(mepilont_mfenics).size();
		log.info("n35 = " + n35 + ",");

		
		
		// EPISEM
		Map <String, Set <String>> mepisem_mfenics = ovd.getDl().createIntersection(mepisem, mfenics);
		int n45 = ovd.getDl().createSynSetFromMap(mepisem_mfenics).size();
		log.info("n45 = " + n45 + ",");

		
		
		// n123
		Map <String, Set <String>> mepso_messo_mepilont = ovd.getDl().createTripleIntersection(mepso, messo, mepilont);
		int n123 = ovd.getDl().createSynSetFromMap(mepso_messo_mepilont).size();
		log.info("n123 = " + n123 + ",");

		
		// n124
		Map <String, Set <String>> mepso_messo_mepisem = ovd.getDl().createTripleIntersection(mepso, messo, mepisem);
		int n124 = ovd.getDl().createSynSetFromMap(mepso_messo_mepisem).size();
		log.info("n124 = " + n124 + ",");

		
		// n125
		Map <String, Set <String>> mepso_messo_mfenics = ovd.getDl().createTripleIntersection(mepso, messo, mfenics);
		int n125 = ovd.getDl().createSynSetFromMap(mepso_messo_mfenics).size();
		log.info("n125 = " + n125 + ",");

		
		// n134
		Map <String, Set <String>> mepso_mepilont_mepisem = ovd.getDl().createTripleIntersection(mepso, mepilont, mepisem);
		int n134 = ovd.getDl().createSynSetFromMap(mepso_mepilont_mepisem).size();
		log.info("n134 = " + n134 + ",");
		
		// n135
		Map <String, Set <String>> mepso_mepilont_mfenics = ovd.getDl().createTripleIntersection(mepso, mepilont, mfenics);
		int n135 = ovd.getDl().createSynSetFromMap(mepso_mepilont_mfenics).size();
		log.info("n135 = " + n135 + ",");
		
		
		// n145
		Map <String, Set <String>> mepso_mepisem_mfenics = ovd.getDl().createTripleIntersection(mepso, mepisem, mfenics);
		int n145 = ovd.getDl().createSynSetFromMap(mepso_mepisem_mfenics).size();
		log.info("n145 = " + n145 + ",");
		
		// n234
		Map <String, Set <String>> messo_mepilont_mepisem = ovd.getDl().createTripleIntersection(messo, mepilont, mepisem);
		int n234 = ovd.getDl().createSynSetFromMap(messo_mepilont_mepisem).size();
		log.info("n234 = " + n234 + ",");
		
		// n235
		Map <String, Set <String>> messo_mepilont_mfenics = ovd.getDl().createTripleIntersection(messo, mepilont, mfenics);
		int n235 = ovd.getDl().createSynSetFromMap(messo_mepilont_mfenics).size();
		log.info("n235 = " + n235 + ",");

		
		// n245
		Map <String, Set <String>> messo_mepisem_mfenics = ovd.getDl().createTripleIntersection(messo, mepisem, mfenics);
		int n245 = ovd.getDl().createSynSetFromMap(messo_mepisem_mfenics).size();
		log.info("n245 = " + n245 + ",");
		
		// n345
		Map <String, Set <String>> mepilont_mepisem_mfenics = ovd.getDl().createTripleIntersection(mepilont, mepisem, mfenics);
		int n345 = ovd.getDl().createSynSetFromMap(mepilont_mepisem_mfenics).size();
		log.info("n345 = " + n345 + ",");
		
		// n1234
		Map <String, Set <String>> mepso_messo_mepilont_mepisem = ovd.getDl().createQuadIntersection(mepso, messo, mepilont, mepisem);
		int n1234 = ovd.getDl().createSynSetFromMap(mepso_messo_mepilont_mepisem).size();
		log.info("n1234 = " + n1234 + ",");
		
		// n1235
		Map <String, Set <String>> mepso_messo_mepilont_mfenics = ovd.getDl().createQuadIntersection(mepso, messo, mepilont, mfenics);
		int n1235 = ovd.getDl().createSynSetFromMap(mepso_messo_mepilont_mfenics).size();
		log.info("n1235 = " + n1235 + ",");
		
		// n1245
		Map <String, Set <String>> mepso_messo_mepisem_mfenics = ovd.getDl().createQuadIntersection(mepso, messo, mepisem, mfenics);
		int n1245 = ovd.getDl().createSynSetFromMap(mepso_messo_mepisem_mfenics).size();
		log.info("n1245 = " + n1245 + ",");
		
		// n1345
		Map <String, Set <String>> mepso_mepilont_mepisem_mfenics = ovd.getDl().createQuadIntersection(mepso, mepilont, mepisem, mfenics);
		int n1345 = ovd.getDl().createSynSetFromMap(mepso_mepilont_mepisem_mfenics).size();
		log.info("n1345 = " + n1345 + ",");
		
		// n2345
		Map <String, Set <String>> messo_mepilont_mepisem_mfenics = ovd.getDl().createQuadIntersection(messo, mepilont, mepisem, mfenics);
		int n2345 = ovd.getDl().createSynSetFromMap(messo_mepilont_mepisem_mfenics).size();
		log.info("n2345 = " + n2345 + ",");
		
		// n12345
		Map <String, Set <String>> mepso_messo_mepilont_mepisem_mfenics = ovd.getDl().createQuintupleIntersection(mepso, messo, mepilont, mepisem, mfenics);
		int n12345 = ovd.getDl().createSynSetFromMap(mepso_messo_mepilont_mepisem_mfenics).size();
		log.info("n12345 = " + n12345 + ",");
	}
}