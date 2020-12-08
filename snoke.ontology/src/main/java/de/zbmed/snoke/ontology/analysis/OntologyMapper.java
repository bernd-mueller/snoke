package de.zbmed.snoke.ontology.analysis;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Set;

import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OntologyMapper {
	private static final Logger log = LoggerFactory.getLogger(OntologyMapper.class);
	DictLoader dl;
	
	OntologyMapper () {
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
		
		OntologyMapper om = new OntologyMapper ();
		OntModel m = om.createOntMappingModel();
		Map <String, Set <String>> mepso = om.getDl().getCodeMapForDict("dictionaries/Dict_EpSO.xml");
		Map <String, Set <String>> messo = om.getDl().getCodeMapForDict("dictionaries/Dict_ESSO.xml");
		Map <String, Set <String>> mepilont = om.getDl().getCodeMapForDict("dictionaries/Dict_EPILONT.xml");
		Map <String, Set <String>> mepisem = om.getDl().getCodeMapForDict("dictionaries/Dict_EPISEM.xml");
		Map <String, Set <String>> mfenics = om.getDl().getCodeMapForDict("dictionaries/Dict_FENICS.xml");
		
		// EpSO
		Map <String, Set <String>> mepso_messo = om.getDl().createIntersection(mepso, messo);
		int n12 = mepso_messo.keySet().size();
		log.info("n12 EpSO and ESSO: " + n12);
		
		for (String keys : mepso_messo.keySet()) {
			String [] s_keys = keys.split("#SAMEAS#");
			String k1 = s_keys[0];
			String k2 = s_keys[1];
			//log.info("Adding " + k1 + " sameAs " + k2);
			om.addEquivalenceToModel(m, k1, k2);
		}
		
		

		Map <String, Set <String>> mepso_mepilont = om.getDl().createIntersection(mepso, mepilont);
		int n13 = mepso_mepilont.keySet().size();
		log.info("n13 EpSO and EPILONT: " + n13);
		for (String keys : mepso_mepilont.keySet()) {
			String [] s_keys = keys.split("#SAMEAS#");
			String k1 = s_keys[0];
			String k2 = s_keys[1];
			//log.info("Adding " + k1 + " sameAs " + k2);
			om.addEquivalenceToModel(m, k1, k2);
		}
		
		
		
		Map <String, Set <String>> mepso_mepisem = om.getDl().createIntersection(mepso, mepisem);
		int n14 = mepso_mepisem.keySet().size();
		log.info("n14 EpSO and EPISEM: " + n14);
		for (String keys : mepso_mepisem.keySet()) {
			String [] s_keys = keys.split("#SAMEAS#");
			String k1 = s_keys[0];
			String k2 = s_keys[1];
			//log.info("Adding " + k1 + " sameAs " + k2);
			om.addEquivalenceToModel(m, k1, k2);
		}
		
		Map <String, Set <String>> mepso_mfenics = om.getDl().createIntersection(mepso, mfenics);
		int n15 = mepso_mfenics.keySet().size();
		log.info("n15 EpSO and FENICS: " + n15);
		for (String keys : mepso_mfenics.keySet()) {
			String [] s_keys = keys.split("#SAMEAS#");
			String k1 = s_keys[0];
			String k2 = s_keys[1];
			//log.info("Adding " + k1 + " sameAs " + k2);
			om.addEquivalenceToModel(m, k1, k2);
		}

		// ESSO
		Map <String, Set <String>> messo_mepilont = om.getDl().createIntersection(messo, mepilont);
		int n23 = messo_mepilont.keySet().size();
		log.info("n23 ESSO and EPILONT: " + n23);
		for (String keys : messo_mepilont.keySet()) {
			String [] s_keys = keys.split("#SAMEAS#");
			String k1 = s_keys[0];
			String k2 = s_keys[1];
			//log.info("Adding " + k1 + " sameAs " + k2);
			om.addEquivalenceToModel(m, k1, k2);
		}

		Map <String, Set <String>> messo_mepisem = om.getDl().createIntersection(messo, mepisem);
		int n24 = messo_mepisem.keySet().size();
		log.info("n24 ESSO and EPISEM: " + n24);
		for (String keys : messo_mepisem.keySet()) {
			String [] s_keys = keys.split("#SAMEAS#");
			String k1 = s_keys[0];
			String k2 = s_keys[1];
			//log.info("Adding " + k1 + " sameAs " + k2);
			om.addEquivalenceToModel(m, k1, k2);
		}

		
		Map <String, Set <String>> messo_mfenics = om.getDl().createIntersection(messo, mfenics);
		int n25 = messo_mfenics.keySet().size();
		log.info("n25 ESSO and FENICS: " + n25);
		for (String keys : messo_mfenics.keySet()) {
			String [] s_keys = keys.split("#SAMEAS#");
			String k1 = s_keys[0];
			String k2 = s_keys[1];
			//log.info("Adding " + k1 + " sameAs " + k2);
			om.addEquivalenceToModel(m, k1, k2);
		}
		
		
		// EPILONT
		Map <String, Set <String>> mepilont_mepisem = om.getDl().createIntersection(mepilont, mepisem);
		int n34 = mepilont_mepisem.keySet().size();
		log.info("n34 EPILONT and EPISEM: " + n34);
		for (String keys : mepilont_mepisem.keySet()) {
			String [] s_keys = keys.split("#SAMEAS#");
			String k1 = s_keys[0];
			String k2 = s_keys[1];
			//log.info("Adding " + k1 + " sameAs " + k2);
			om.addEquivalenceToModel(m, k1, k2);
		}
		
		Map <String, Set <String>> mepilont_mfenics = om.getDl().createIntersection(mepilont, mfenics);
		int n35 = mepilont_mfenics.keySet().size();
		log.info("n35 EPILONT and FENICS: " + n35);
		for (String keys : mepilont_mfenics.keySet()) {
			String [] s_keys = keys.split("#SAMEAS#");
			String k1 = s_keys[0];
			String k2 = s_keys[1];
			//log.info("Adding " + k1 + " sameAs " + k2);
			om.addEquivalenceToModel(m, k1, k2);
		}
		
		om.writeModelToFile(m);
	}
	
	OntModel createOntMappingModel() {
		String uri = "https://www.zbmed.de/mepo";
		String ns = uri + "#";
		OntModel m = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM );
		
		String nsEpSO = "http://www.case.edu/EpSO.owl#";
		m.setNsPrefix("EpSO", nsEpSO);
		String nsESSO = "http://www.semanticweb.org/rjyy/ontologies/2015/5/ESSO#";
		m.setNsPrefix("ESSO", nsESSO);
		String nsEPILONT = "http://www.semanticweb.org/ontologies/2009/3/EpilepsyOntology.owl#";
		m.setNsPrefix("EPILONT", nsEPILONT);
		String nsEPISEM = "http://www.semanticweb.org/danielhier/ontologies/2019/3/untitled-ontology-57/";
		m.setNsPrefix("EPISEM", nsEPISEM);
		String nsFENICS = "http://webprotege.stanford.edu/";
		m.setNsPrefix("FENICS", nsFENICS);
		String nsOWL = "http://www.w3.org/2002/07/owl#";
		m.setNsPrefix("owl", nsOWL);
		return m;
	}

	Model addEquivalenceToModel (OntModel m, String a, String b) {
		if (!a.equals(b)) {
			 String nsOWL = "http://www.w3.org/2002/07/owl#";
			 Resource left = m.createResource(a);
			 Property sameAs = m.createProperty(nsOWL + "sameAs");
			 Resource right = m.createResource(b);
			 m.add(left, sameAs, right);
		}
		 return m;
	}
	
	void writeModelToFile (OntModel m) {
		try {
			FileOutputStream fos = new FileOutputStream("resources/ontologies/MEPOS.xrdf");
			//m.write(fos);
			m.write(fos, "RDF/XML-ABBREV");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
