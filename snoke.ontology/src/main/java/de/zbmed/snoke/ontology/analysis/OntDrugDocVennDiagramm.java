package de.zbmed.snoke.ontology.analysis;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OntDrugDocVennDiagramm {
	private static final Logger log = LoggerFactory.getLogger(OntDrugDocVennDiagramm.class);
	
	
	DictLoader dl;
	
	OntDrugDocVennDiagramm () {
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
		OntDocVennDiagramm odvd = new OntDocVennDiagramm ();
		Set <String> sepso 		= odvd.getDl().readPmidsFromFile("resources/pmids/EpSO.txt");
		Set <String> sesso 		= odvd.getDl().readPmidsFromFile("resources/pmids/ESSO.txt");
		Set <String> sepilont	= odvd.getDl().readPmidsFromFile("resources/pmids/EPILONT.txt");
		Set <String> sepisem 	= odvd.getDl().readPmidsFromFile("resources/pmids/EPISEM.txt");
		Set <String> sfenics	= odvd.getDl().readPmidsFromFile("resources/pmids/FENICS.txt");
		Set <String> sdrugs	= odvd.getDl().readPmidsFromFile("resources/pmids/DrugNames.txt");
		
		sepso.retainAll(sdrugs);
		sesso.retainAll(sdrugs);
		sepilont.retainAll(sdrugs);
		sepisem.retainAll(sdrugs);
		sfenics.retainAll(sdrugs);

		
		int area1 = sepso.size();
		int area2 = sesso.size();
		int area3 = sepilont.size();
		int area4 = sepisem.size();
		int area5 = sfenics.size();
		
		log.info("EpSO: " + area1);
		log.info("ESSO: " + area2);
		log.info("EPILONT: " + area3);
		log.info("EPISEM: " + area4);
		log.info("FENICS: " + area5);
		
		// EpSO
		Set<String> sepso_sesso = new HashSet<String>(sepso); // use the copy constructor
		sepso_sesso.retainAll(sesso);
		int n12 = sepso_sesso.size();
		log.info("n12 EpSO and ESSO: " + n12);

		Set<String> sepso_sepilont = new HashSet<String>(sepso);
		sepso_sepilont.retainAll(sepilont);
		int n13 = sepso_sepilont.size();
		log.info("n13 EpSO and EPILONT: " + n13);
		
		Set<String> sepso_sepisem = new HashSet<String>(sepso);
		sepso_sepisem.retainAll(sepisem);
		int n14 = sepso_sepisem.size();
		log.info("n14 EpSO and EPISEM: " + n14);

		
		Set <String> sepso_sfenics = new HashSet<String>(sepso);
		sepso_sfenics.retainAll(sfenics);
		int n15 = sepso_sfenics.size();
		log.info("n15 EpSO and FENICS: " + n15);

		// ESSO
		Set <String> sesso_sepilont = new HashSet<String>(sesso);
		sesso_sepilont.retainAll(sepilont);
		int n23 = sesso_sepilont.size();
		log.info("n23 ESSO and EPILONT: " + n23);


		Set <String> sesso_sepisem = new HashSet<String>(sesso);
		sesso_sepisem.retainAll(sepisem);
		int n24 = sesso_sepisem.size();
		log.info("n24 ESSO and EPISEM: " + n24);

		
		Set <String> sesso_sfenics = new HashSet<String>(sesso);
		sesso_sfenics.retainAll(sfenics);
		int n25 = sesso_sfenics.size();
		log.info("n25 ESSO and FENICS: " + n25);

		
		
		// EPILONT
		Set <String> sepilont_sepisem = new HashSet<String>(sepilont);
		sepilont_sepisem.retainAll(sepisem);
		int n34 = sepilont_sepisem.size();
		log.info("n34 EPILONT and EPISEM: " + n34);

		
		Set <String> sepilont_sfenics = new HashSet<String>(sepilont);
		sepilont_sfenics.retainAll(sfenics);
		int n35 = sepilont_sfenics.size();
		log.info("n35 EPILONT and FENICS: " + n35);

		
		
		// EPISEM
		Set <String> sepisem_sfenics = new HashSet<String>(sepisem);
		sepisem_sfenics.retainAll(sfenics);
		int n45 = sepisem_sfenics.size();
		log.info("n45 EPISEM and FENICS: " + n45);

		
		
		// n123
		Set <String> sepso_sesso_sepilont = new HashSet <String> (sepso_sesso);
		sepso_sesso_sepilont.retainAll(sepilont);
		int n123 = sepso_sesso_sepilont.size();
		log.info("n123 EpSO and ESSO and EPILONT: " + n123);

		
		// n124
		Set <String> sepso_sesso_sepisem = new HashSet <String> (sepso_sesso);
		sepso_sesso_sepisem.retainAll(sepisem);
		int n124 = sepso_sesso_sepisem.size();
		log.info("n124 EpSO and ESSO and EPISEM: " + n124);

		
		// n125
		Set <String> sepso_sesso_sfenics = new HashSet <String> (sepso_sesso);
		sepso_sesso_sfenics.retainAll(sfenics);
		int n125 = sepso_sesso_sfenics.size();
		log.info("n125 EpSO and ESSO and FENICS: " + n125);

		
		// n134
		Set <String> sepso_sepilont_sepisem = new HashSet <String> (sepso_sepilont); 
		sepso_sepilont_sepisem.retainAll(sepisem);
		int n134 = sepso_sepilont_sepisem.size();
		log.info("n134 EpSO and EPILONT and EPISEM: " + n134);
		
		// n135
		Set <String> sepso_sepilont_sfenics = new HashSet <String> (sepso_sepilont);
		sepso_sepilont_sfenics.retainAll(sfenics);
		int n135 = sepso_sepilont_sfenics.size();
		log.info("n135 EpSO and EPILONT and FENICS: " + n135);
		
		
		// n145
		Set <String> sepso_sepisem_sfenics = new HashSet <String> (sepso_sepisem);
		sepso_sepisem_sfenics.retainAll(sfenics);
		int n145 = sepso_sepisem_sfenics.size();
		log.info("n145 EpSO and EPISEM and FENICS: " + n145);
		
		// n234
		Set <String> sesso_sepilont_sepisem = new HashSet <String> (sesso_sepilont);
		sesso_sepilont_sepisem.retainAll(sepisem);
		int n234 = sesso_sepilont_sepisem.size();
		log.info("n234 ESSO and EPILONT and EPISEM: " + n234);
		
		// n235
		Set <String> sesso_sepilont_sfenics = new HashSet <String> (sesso_sepilont);
		sesso_sepilont_sfenics.retainAll(sfenics);
		int n235 = sesso_sepilont_sfenics.size();
		log.info("n235 ESSO and EPILONT and FENICS: " + n235);

		
		// n245
		Set <String> sesso_sepisem_sfenics = new HashSet <String> (sesso_sepisem);
		sesso_sepisem_sfenics.retainAll(sfenics);
		int n245 = sesso_sepisem_sfenics.size();
		log.info("n245 ESSO and EPISEM and FENICS: " + n245);
		
		// n345
		Set <String> sepilont_sepisem_sfenics = new HashSet <String> (sepilont_sepisem);
		sepilont_sepisem_sfenics.retainAll(sfenics);
		int n345 = sepilont_sepisem_sfenics.size();
		log.info("n345 EPILONT and EPISEM and FENICS: " + n345);
		
		// n1234
		Set <String> sepso_sesso_sepilont_sepisem = new HashSet <String> (sepso_sesso);
		sepso_sesso_sepilont_sepisem.retainAll(sepilont_sepisem);
		int n1234 = sepso_sesso_sepilont_sepisem.size();
		log.info("n1234 EpSO and ESSO and EPILONT and EPISEM: " + n1234);
		
		// n1235
		Set <String> sepso_sesso_sepilont_sfenics = new HashSet <String> (sepso_sesso);
		sepso_sesso_sepilont_sfenics.retainAll(sepilont_sfenics);
		int n1235 = sepso_sesso_sepilont_sfenics.size();
		log.info("n1235 EpSO and ESSO and EPILONT and FENICS: " + n1235);
		
		// n1245
		Set <String> sepso_sesso_sepisem_sfenics = new HashSet <String> (sepso_sesso);
		sepso_sesso_sepisem_sfenics.retainAll(sepisem_sfenics);
		int n1245 = sepso_sesso_sepisem_sfenics.size();
		log.info("n1245 EpSO and ESSO and EPISEM and FENICS: " + n1245);
		
		// n1345
		Set <String> sepso_sepilont_sepisem_sfenics = new HashSet <String> (sepso_sepilont);
		sepso_sepilont_sepisem_sfenics.retainAll(sepisem_sfenics);
		int n1345 = sepso_sepilont_sepisem_sfenics.size();
		log.info("n1345 EpSO and EPILONT and EPISEM and FENICS: " + n1345);
		
		// n2345
		Set <String> sesso_sepilont_sepisem_sfenics = new HashSet <String> (sesso_sepilont);
		sesso_sepilont_sepisem_sfenics.retainAll(sepisem_sfenics);
		int n2345 = sesso_sepilont_sepisem_sfenics.size();
		log.info("n2345 ESSO and EPILONT and EPISEM and FENICS: " + n2345);
		
		// n12345
		Set <String> sepso_sesso_sepilont_sepisem_sfenics = new HashSet <String> (sepso_sesso_sepilont);
		sepso_sesso_sepilont_sepisem_sfenics.retainAll(sepisem_sfenics);
		int n12345 = sepso_sesso_sepilont_sepisem_sfenics.size();
		log.info("n12345 ESSO and EPILONT and EPISEM and FENICS: " + n12345);
	}
	
	
}
