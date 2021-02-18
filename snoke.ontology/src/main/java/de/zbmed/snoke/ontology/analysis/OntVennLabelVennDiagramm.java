package de.zbmed.snoke.ontology.analysis;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OntVennLabelVennDiagramm {
	private static final Logger log = LoggerFactory.getLogger(OntVennLabelVennDiagramm.class);
	DictLoader dl;
	
	OntVennLabelVennDiagramm () {
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
		OntVennLabelVennDiagramm ovd = new OntVennLabelVennDiagramm ();
		Map <String, Set <String>> mepso = ovd.getDl().getMapForDict("dictionaries/Dict_EpSO.xml");
		Map <String, Set <String>> messo = ovd.getDl().getMapForDict("dictionaries/Dict_ESSO.xml");
		Map <String, Set <String>> mepilont = ovd.getDl().getMapForDict("dictionaries/Dict_EPILONT.xml");
		Map <String, Set <String>> mepisem = ovd.getDl().getMapForDict("dictionaries/Dict_EPISEM.xml");
		Map <String, Set <String>> mfenics = ovd.getDl().getMapForDict("dictionaries/Dict_FENICS.xml");
		// Map <String, Set <String>> mdrugnames = ovd.getDl().getMapForDict("dictionaries/Dict_DrugNames.xml");
		
		Set <String> sepso 		= mepso.keySet();
		Set <String> sesso 		= messo.keySet();
		Set <String> sepilont	= mepilont.keySet();
		Set <String> sepisem 	= mepisem.keySet();
		Set <String> sfenics	= mfenics.keySet();
		// Set <String> sdrugs		= ovd.createSynSetFromMap(mdrugnames);
		

		
		log.info("area1 = " + sepso.size() + ",");
		log.info("area2 = " + sesso.size() + ",");
		log.info("area3 = " + sepilont.size() + ",");
		log.info("area4 = " + sepisem.size() + ",");
		log.info("area5 = " + sfenics.size() + ",");
		
		// EpSO
		Set<String> sepso_inter_sesso = new HashSet<String>(sepso); // use the copy constructor
		sepso_inter_sesso.retainAll(sesso);
		int n12 = sepso_inter_sesso.size();
		log.info("n12 = " + n12 + ",");

		Set<String> sepso_inter_sepilont = new HashSet<String>(sepso);
		sepso_inter_sepilont.retainAll(sepilont);
		int n13 = sepso_inter_sepilont.size();
		log.info("n13 = " + n13 + ",");
		
		Set<String> sepso_inter_sepisem = new HashSet<String>(sepso);
		sepso_inter_sepisem.retainAll(sepisem);
		int n14 = sepso_inter_sepisem.size();
		log.info("n14 = " + n14 + ",");

		
		Set <String> sepso_inter_sfenics = new HashSet<String>(sepso);
		sepso_inter_sfenics.retainAll(sfenics);
		int n15 = sepso_inter_sfenics.size();
		log.info("n15 = " + n15 + ",");

		// ESSO
		Set <String> sesso_inter_sepilont = new HashSet<String>(sesso);
		sesso_inter_sepilont.retainAll(sepilont);
		int n23 = sesso_inter_sepilont.size();
		log.info("n23 = " + n23 + ",");


		Set <String> sesso_inter_sepisem = new HashSet<String>(sesso);
		sesso_inter_sepisem.retainAll(sepisem);
		int n24 = sesso_inter_sepisem.size();
		log.info("n24 = " + n24 + ",");

		
		Set <String> sesso_inter_sfenics = new HashSet<String>(sesso);
		sesso_inter_sfenics.retainAll(sfenics);
		int n25 = sesso_inter_sfenics.size();
		log.info("n25 = " + n25 + ",");

		
		
		// EPILONT
		Set <String> sepilont_inter_sepisem = new HashSet<String>(sepilont);
		sepilont_inter_sepisem.retainAll(sepisem);
		int n34 = sepilont_inter_sepisem.size();
		log.info("n34 = " + n34 + ",");

		
		Set <String> sepilont_inter_sfenics = new HashSet<String>(sepilont);
		sepilont_inter_sfenics.retainAll(sfenics);
		int n35 = sepilont_inter_sfenics.size();
		log.info("n35 = " + n35 + ",");

		
		
		// EPISEM
		Set <String> sepisem_inter_sfenics = new HashSet<String>(sepisem);
		sepisem_inter_sfenics.retainAll(sfenics);
		int n45 = sepisem_inter_sfenics.size();
		log.info("n45 = " + n45 + ",");

		
		
		// n123
		Set <String> sepso_i_sesso_i_sepilont = new HashSet <String> (sepso_inter_sesso);
		sepso_i_sesso_i_sepilont.retainAll(sepilont);
		int n123 = sepso_i_sesso_i_sepilont.size();
		log.info("n123 = " + n123 + ",");

		
		// n124
		Set <String> sepso_i_sesso_i_sepisem = new HashSet <String> (sepso_inter_sesso);
		sepso_i_sesso_i_sepisem.retainAll(sepisem);
		int n124 = sepso_i_sesso_i_sepisem.size();
		log.info("n124 = " + n124 + ",");

		
		// n125
		Set <String> sepso_i_sesso_i_sfenics = new HashSet <String> (sepso_inter_sesso);
		sepso_i_sesso_i_sfenics.retainAll(sfenics);
		int n125 = sepso_i_sesso_i_sfenics.size();
		log.info("n125 = " + n125 + ",");

		
		// n134
		Set <String> sepso_i_sepilont_i_sepisem = new HashSet <String> (sepso_inter_sepilont); 
		sepso_i_sepilont_i_sepisem.retainAll(sepisem);
		int n134 = sepso_i_sepilont_i_sepisem.size();
		log.info("n134 = " + n134 + ",");
		
		// n135
		Set <String> sepso_i_sepilont_i_sfenics = new HashSet <String> (sepso_inter_sepilont);
		sepso_i_sepilont_i_sfenics.retainAll(sfenics);
		int n135 = sepso_i_sepilont_i_sfenics.size();
		log.info("n135 = " + n135 + ",");
		
		
		// n145
		Set <String> sepso_union_sepisem = new HashSet <String> (sepso);
		sepso_union_sepisem.addAll(sepisem);
		Set <String> sepso_i_sepisem_i_sfenics = new HashSet <String> (sepso_inter_sepisem);
		sepso_i_sepisem_i_sfenics.retainAll(sfenics);
		int n145 = sepso_i_sepisem_i_sfenics.size();
		log.info("n145 = " + n145 + ",");
		
		// n234
		Set <String> sesso_i_sepilont_i_sepisem = new HashSet <String> (sesso_inter_sepilont);
		sesso_i_sepilont_i_sepisem.retainAll(sepisem);
		int n234 = sesso_i_sepilont_i_sepisem.size();
		log.info("n234 = " + n234 + ",");
		
		// n235
		Set <String> sesso_i_sepilont_i_sfenics = new HashSet <String> (sesso_inter_sepilont);
		sesso_i_sepilont_i_sfenics.retainAll(sfenics);
		int n235 = sesso_i_sepilont_i_sfenics.size();
		log.info("n235 = " + n235 + ",");

		
		// n245
		Set <String> sesso_i_sepisem_i_sfenics = new HashSet <String> (sesso_inter_sepisem);
		sesso_i_sepisem_i_sfenics.retainAll(sfenics);
		int n245 = sesso_i_sepisem_i_sfenics.size();
		log.info("n245 = " + n245 + ",");
		
		// n345
		Set <String> sepilont_i_sepisem_i_sfenics = new HashSet <String> (sepilont_inter_sepisem);
		sepilont_i_sepisem_i_sfenics.retainAll(sfenics);
		int n345 = sepilont_i_sepisem_i_sfenics.size();
		log.info("n345 = " + n345 + ",");
		
		// n1234
		
		Set <String> sepso_i_sesso_i_sepilont_i_sepisem = new HashSet <String> (sepso_inter_sesso);
		sepso_i_sesso_i_sepilont_i_sepisem.retainAll(sepilont_inter_sepisem);
		int n1234 = sepso_i_sesso_i_sepilont_i_sepisem.size();
		log.info("n1234 = " + n1234 + ",");
		
		// n1235
		Set <String> sepso_i_sesso_i_sepilont_i_sfenics = new HashSet <String> (sepso_inter_sesso);
		Set <String> sepilont_union_sfenics = new HashSet <String> (sepilont);
		sepilont_union_sfenics.addAll(sfenics);
		sepso_i_sesso_i_sepilont_i_sfenics.retainAll(sepilont_union_sfenics);
		int n1235 = sepso_i_sesso_i_sepilont_i_sfenics.size();
		log.info("n1235 = " + n1235 + ",");
		
		// n1245
		Set <String> sepso_i_sesso_i_sepisem_i_sfenics = new HashSet <String> (sepso_inter_sesso);
		Set <String> sepisem_union_sfenics = new HashSet <String> (sepisem);
		sepisem_union_sfenics.addAll(sfenics);
		sepso_i_sesso_i_sepisem_i_sfenics.retainAll(sepisem_union_sfenics);
		int n1245 = sepso_i_sesso_i_sepisem_i_sfenics.size();
		log.info("n1245 = " + n1245 + ",");
		
		// n1345
		Set <String> sepso_i_sepilont_i_sepisem_i_sfenics = new HashSet <String> (sepso_inter_sepilont);
		sepso_i_sepilont_i_sepisem_i_sfenics.retainAll(sepisem_union_sfenics);
		int n1345 = sepso_i_sepilont_i_sepisem_i_sfenics.size();
		log.info("n1345 = " + n1345 + ",");
		
		// n2345
		Set <String> sesso_i_sepilont_i_sepisem_i_sfenics = new HashSet <String> (sesso_inter_sepilont);
		sesso_i_sepilont_i_sepisem_i_sfenics.retainAll(sepisem_union_sfenics);
		int n2345 = sesso_i_sepilont_i_sepisem_i_sfenics.size();
		log.info("n2345 = " + n2345 + ",");
		
		// n12345
		Set <String> sepso_i_sesso_i_sepilont_i_sepisem_i_sfenics = new HashSet <String> (sepso_i_sesso_i_sepilont);
		sepso_i_sesso_i_sepilont_i_sepisem_i_sfenics.retainAll(sepisem_union_sfenics);
		int n12345 = sepso_i_sesso_i_sepilont_i_sepisem_i_sfenics.size();
		log.info("n12345 = " + n12345 + ",");
		

		Set <String> s1 = new HashSet <String> (sepso);
		Set <String> s2 = new HashSet <String> (sesso);
		Set <String> s3 = new HashSet <String> (sepilont);
		Set <String> s4 = new HashSet <String> (sepisem);
		Set <String> s5 = new HashSet <String> (sfenics);
		

		s3.retainAll(s4);
		s3.retainAll(s1);
		s3.retainAll(s2);
		
		for (String s : s3) {
			System.out.println(s);
		}
		
		
		log.info("area1 = " + sepso.size() + ",");
		log.info("area2 = " + sesso.size() + ",");
		log.info("area3 = " + sepilont.size() + ",");
		log.info("area4 = " + sepisem.size() + ",");
		log.info("area5 = " + sfenics.size() + ",");

		
		
		
	}
	
	
}
