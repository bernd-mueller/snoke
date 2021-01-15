package de.zbmed.snoke.ontology.analysis;

import java.util.Map;
import java.util.Set;

import org.apache.jena.ontology.OntModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of OntologyMapper for epilepsy ontologies
 * 
 * @author Muellerb
 *
 */
public class EpilepsyOntologyMapper extends OntologyMapper {
	private static final Logger log = LoggerFactory.getLogger(EpilepsyOntologyMapper.class);
	
	/**
	 * Conduct the mapping of the epilepsy ontologies EpSO, ESSO, EPILONT, EPISEM, and FENICS
	 * with the final model stored as MEPO.xrdf
	 */
	public void conductMapping () {
		setOntprefix("MEPO");
		OntModel m = createOntMappingModel();
		Map <String, Set <String>> mepso = getDl().getCodeMapForDict("dictionaries/Dict_EpSO.xml");
		Map <String, Set <String>> messo = getDl().getCodeMapForDict("dictionaries/Dict_ESSO.xml");
		Map <String, Set <String>> mepilont = getDl().getCodeMapForDict("dictionaries/Dict_EPILONT.xml");
		Map <String, Set <String>> mepisem = getDl().getCodeMapForDict("dictionaries/Dict_EPISEM.xml");
		Map <String, Set <String>> mfenics = getDl().getCodeMapForDict("dictionaries/Dict_FENICS.xml");
		

		
		// EpSO
		Map <String, Set <String>> mepso_messo = getDl().createIntersection(mepso, messo);
		int n12 = mepso_messo.keySet().size();
		log.info("n12 EpSO and ESSO: " + n12);
		
		for (String keys : mepso_messo.keySet()) {
			String [] s_keys = keys.split("#SAMEAS#");
			String k1 = s_keys[0];
			String k2 = s_keys[1];
			//log.info("Adding " + k1 + " sameAs " + k2);
			addEquivalenceToModel(m, k1, k2);
		}
		
		

		Map <String, Set <String>> mepso_mepilont = getDl().createIntersection(mepso, mepilont);
		int n13 = mepso_mepilont.keySet().size();
		log.info("n13 EpSO and EPILONT: " + n13);
		for (String keys : mepso_mepilont.keySet()) {
			String [] s_keys = keys.split("#SAMEAS#");
			String k1 = s_keys[0];
			String k2 = s_keys[1];
			//log.info("Adding " + k1 + " sameAs " + k2);
			addEquivalenceToModel(m, k1, k2);
		}
		
		
		
		Map <String, Set <String>> mepso_mepisem = getDl().createIntersection(mepso, mepisem);
		int n14 = mepso_mepisem.keySet().size();
		log.info("n14 EpSO and EPISEM: " + n14);
		for (String keys : mepso_mepisem.keySet()) {
			String [] s_keys = keys.split("#SAMEAS#");
			String k1 = s_keys[0];
			String k2 = s_keys[1];
			//log.info("Adding " + k1 + " sameAs " + k2);
			addEquivalenceToModel(m, k1, k2);
		}
		
		Map <String, Set <String>> mepso_mfenics = getDl().createIntersection(mepso, mfenics);
		int n15 = mepso_mfenics.keySet().size();
		log.info("n15 EpSO and FENICS: " + n15);
		for (String keys : mepso_mfenics.keySet()) {
			String [] s_keys = keys.split("#SAMEAS#");
			String k1 = s_keys[0];
			String k2 = s_keys[1];
			//log.info("Adding " + k1 + " sameAs " + k2);
			addEquivalenceToModel(m, k1, k2);
		}

		// ESSO
		Map <String, Set <String>> messo_mepilont = getDl().createIntersection(messo, mepilont);
		int n23 = messo_mepilont.keySet().size();
		log.info("n23 ESSO and EPILONT: " + n23);
		for (String keys : messo_mepilont.keySet()) {
			String [] s_keys = keys.split("#SAMEAS#");
			String k1 = s_keys[0];
			String k2 = s_keys[1];
			//log.info("Adding " + k1 + " sameAs " + k2);
			addEquivalenceToModel(m, k1, k2);
		}

		Map <String, Set <String>> messo_mepisem = getDl().createIntersection(messo, mepisem);
		int n24 = messo_mepisem.keySet().size();
		log.info("n24 ESSO and EPISEM: " + n24);
		for (String keys : messo_mepisem.keySet()) {
			String [] s_keys = keys.split("#SAMEAS#");
			String k1 = s_keys[0];
			String k2 = s_keys[1];
			//log.info("Adding " + k1 + " sameAs " + k2);
			addEquivalenceToModel(m, k1, k2);
		}

		
		Map <String, Set <String>> messo_mfenics = getDl().createIntersection(messo, mfenics);
		int n25 = messo_mfenics.keySet().size();
		log.info("n25 ESSO and FENICS: " + n25);
		for (String keys : messo_mfenics.keySet()) {
			String [] s_keys = keys.split("#SAMEAS#");
			String k1 = s_keys[0];
			String k2 = s_keys[1];
			//log.info("Adding " + k1 + " sameAs " + k2);
			addEquivalenceToModel(m, k1, k2);
		}
		
		
		// EPILONT
		Map <String, Set <String>> mepilont_mepisem = getDl().createIntersection(mepilont, mepisem);
		int n34 = mepilont_mepisem.keySet().size();
		log.info("n34 EPILONT and EPISEM: " + n34);
		for (String keys : mepilont_mepisem.keySet()) {
			String [] s_keys = keys.split("#SAMEAS#");
			String k1 = s_keys[0];
			String k2 = s_keys[1];
			//log.info("Adding " + k1 + " sameAs " + k2);
			addEquivalenceToModel(m, k1, k2);
		}
		
		Map <String, Set <String>> mepilont_mfenics = getDl().createIntersection(mepilont, mfenics);
		int n35 = mepilont_mfenics.keySet().size();
		log.info("n35 EPILONT and FENICS: " + n35);
		for (String keys : mepilont_mfenics.keySet()) {
			String [] s_keys = keys.split("#SAMEAS#");
			String k1 = s_keys[0];
			String k2 = s_keys[1];
			//log.info("Adding " + k1 + " sameAs " + k2);
			addEquivalenceToModel(m, k1, k2);
		}
		
		addLabels(m, mepso);
		addLabels(m, messo);
		addLabels(m, mepisem);
		addLabels(m, mepilont);
		addLabels(m, mfenics);
		//om.addLabels(m, messo);
		
		writeModelToFile(m);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		EpilepsyOntologyMapper om = new EpilepsyOntologyMapper ();
		om.conductMapping();
		
	}

}
