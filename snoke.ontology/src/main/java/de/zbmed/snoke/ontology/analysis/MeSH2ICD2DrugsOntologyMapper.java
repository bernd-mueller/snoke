package de.zbmed.snoke.ontology.analysis;

import java.util.Map;
import java.util.Set;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creates Mapping of DrugBank vocabulary, ICD and MeSH
 * 
 * @author Muellerb
 *
 */
public class MeSH2ICD2DrugsOntologyMapper extends OntologyMapper {
	private static final Logger log = LoggerFactory.getLogger(MeSH2ICD2DrugsOntologyMapper.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MeSH2ICD2DrugsOntologyMapper mdim = new MeSH2ICD2DrugsOntologyMapper ();
		mdim.conductMapping();
	}

	/**
	 * Creates namespace for DrugBank, MeSH and ICD
	 */
	OntModel createOntMappingModel() {

		OntModel m = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM );
		
		String nsMeSH = "http://id.nlm.nih.gov/mesh/";
		m.setNsPrefix("MeSH", nsMeSH);
		
		String nsICD = "http://id.who.int/icd/entity/";
		m.setNsPrefix("ICD", nsICD);
		
		String nsDrugNames = "https://go.drugbank.com/drugs/";
		m.setNsPrefix("DrugNames", nsDrugNames);
		
		String nsOWL = "http://www.w3.org/2002/07/owl#";
		m.setNsPrefix("owl", nsOWL);
		
		String nsRDF = "http://www.w3.org/2000/01/rdf-schema#";
		m.setNsPrefix("rdfs", nsRDF);
		
		String nsSKOS = "http://www.w3.org/2004/02/skos/core#";
		m.setNsPrefix("SKOS", nsSKOS);
		
		return m;
	}

	@Override
	public void conductMapping() {
		// TODO Auto-generated method stub
		setOntprefix("MDIM");
		OntModel m = createOntMappingModel();
		Map <String, Set <String>> mmesh = getDl().getCodeMapForDict("dictionaries/Dict_MeSH_2021.xml");
		Map <String, Set <String>> micd = getDl().getCodeMapForDict("dictionaries/Dict_ICD.xml");
		Map <String, Set <String>> mdrug = getDl().getCodeMapForDict("dictionaries/Dict_DrugNames_2021.xml");
		
		// MeSH vs. ICD
		Map <String, Set <String>> mmesh_micd = getDl().createIntersection(mmesh, micd);
		int n12 = mmesh_micd.keySet().size();
		log.info("#Intersection: " + n12);
		
		for (String keys : mmesh_micd.keySet()) {
			String [] s_keys = keys.split("#SAMEAS#");
			String k1 = s_keys[0];
			String k2 = s_keys[1];
			log.info("Adding " + k1 + " sameAs " + k2);
			addEquivalenceToModel(m, k1, k2);
		}
		
		// MeSH vs. DrugNames
		Map <String, Set <String>> mmesh_mdrug = getDl().createIntersection(mmesh, mdrug);
		int n13 = mmesh_mdrug.keySet().size();
		log.info("#Intersection: " + n13);
		
		for (String keys : mmesh_mdrug.keySet()) {
			String [] s_keys = keys.split("#SAMEAS#");
			String k1 = s_keys[0];
			String k2 = s_keys[1];
			log.info("Adding " + k1 + " sameAs " + k2);
			addEquivalenceToModel(m, k1, k2);
		}
		
		// ICD vs. Drugs
		Map <String, Set <String>> micd_mdrug = getDl().createIntersection(micd, mdrug);
		int n23 = micd_mdrug.keySet().size();
		log.info("#Intersection: " + n23);
		
		for (String keys : micd_mdrug.keySet()) {
			String [] s_keys = keys.split("#SAMEAS#");
			String k1 = s_keys[0];
			String k2 = s_keys[1];
			log.info("Adding " + k1 + " sameAs " + k2);
			addEquivalenceToModel(m, k1, k2);
		}

		addLabels(m, mmesh);
		addLabels(m, micd);
		addLabels(m, mdrug);
		
		writeModelToFile(m);
	}

	
}
