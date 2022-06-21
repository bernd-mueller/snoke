package de.zbmed.snoke.ontology.analysis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MeSH2DrugsOntologyMapper extends OntologyMapper {
	private static final Logger log = LoggerFactory.getLogger(MeSH2DrugsOntologyMapper.class);
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MeSH2DrugsOntologyMapper m2d = new MeSH2DrugsOntologyMapper ();
		m2d.conductMapping();
	} 
	/**
	 * Creates namespace for DrugBank, MeSH and ICD
	 */
	OntModel createOntMappingModel() {

		OntModel m = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM );
		
		String nsMeSH = "http://id.nlm.nih.gov/mesh/";
		m.setNsPrefix("MeSH", nsMeSH);
		
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
		
		int mesh_total_synonyms_beforemerge = 0;
		int drug_total_synonyms_beforemerge = 0;
		
		int mesh_added_merged_synonyms = 0;
		int drug_added_merged_synonyms = 0;
		
		int mesh_synonyms_aftermerge = 0;
		int drug_synonyms_aftermerge = 0;
		
		int mesh_total_synonyms_aftermerge = 0;
		int drug_total_synonyms_aftermerge = 0;
		
		setOntprefix("MDM");
		OntModel m = createOntMappingModel();
		Map <String, Set <String>> mmesh = getDl().getCodeMapForDict("dictionaries/Dict_MeSH_stemmed.xml");
		Map <String, Set <String>> mdrug = getDl().getCodeMapForDict("dictionaries/Dict_DrugNames_stemmed.xml");
		//Map <String, Set <String>> mmesh = getDl().getCodeMapForDict("dictionaries/Dict_MeSH.xml");
		//Map <String, Set <String>> mdrug = getDl().getCodeMapForDict("dictionaries/Dict_DrugNames.xml");		
		// MeSH vs. ICD vs. Drugs
		Map <String, Set <String>> mmesh_mdrug = getDl().createIntersection(mmesh, mdrug);		
		Map <String, Set <String>> mmeshmerge = new HashMap <String, Set <String>> (mmesh);
		Map <String, Set <String>> mdrugmerge = new HashMap <String, Set <String>> (mdrug);
		
		Set <String> keysdrug = new HashSet <String> ();
		Set <String> keysmesh = new HashSet <String> ();
		
		
		for (String keys : mmesh_mdrug.keySet()) {
			String [] s_keys = keys.split("#SAMEAS#");
			String k1 = s_keys[0];
			String k2 = s_keys[1];
			
			Set <String> mergedSet = mmesh_mdrug.get(keys);
			
			mmeshmerge.put(k1, mergedSet);
			mdrugmerge.put(k2, mergedSet);
			
			keysmesh.add(k1);
			keysdrug.add(k2);
			
			addEquivalenceToModel(m, k1, k2);
		}
		

	
		addLabels(m, mmeshmerge);
		addLabels(m, mdrugmerge);
		
		writeModelToFile(m);
		
		
		int n12 = mmesh_mdrug.keySet().size();
		log.info("#Intersection MeSH, Drug: " + n12);
				
		int n1 = mmesh.keySet().size();
		log.info("#MeSH: " + n1);
		
		int n3 = mdrug.keySet().size();
		log.info("#Drug: " + n3);
		
		Set <String> superBeforeMergeMeSH = new HashSet <String> ();
		for (String km : mmesh.keySet()) {
			Set <String> s = mmesh.get(km);
			superBeforeMergeMeSH.addAll(s);
		}
		mesh_total_synonyms_beforemerge = superBeforeMergeMeSH.size();
		
		Set <String> superBeforeMergeDrug = new HashSet <String> ();
		for (String km : mdrug.keySet()) {
			Set <String> s = mdrug.get(km);
			superBeforeMergeDrug.addAll(s);
		}
		drug_total_synonyms_beforemerge = superBeforeMergeDrug.size();
		
		for (String kmesh : mmesh.keySet()) {
			Set <String> premerge 	= mmesh.get(kmesh);
			Set <String> postmerge 	= mmeshmerge.get(kmesh);
			mesh_added_merged_synonyms += (postmerge.size() - premerge.size());
		}
		
		for (String kdrug : mdrug.keySet()) {
			Set <String> premerge 	= mdrug.get(kdrug);
			Set <String> postmerge 	= mdrugmerge.get(kdrug);
			drug_added_merged_synonyms += (postmerge.size() - premerge.size());
		}
		

		for (String mk : mmeshmerge.keySet()) {
			Set <String> s = mmeshmerge.get(mk);
			superBeforeMergeMeSH.addAll(s);
		}
		mesh_total_synonyms_aftermerge = superBeforeMergeMeSH.size();
		
		for (String md : mdrugmerge.keySet()) {
			Set <String> s = mdrugmerge.get(md);
			superBeforeMergeDrug.addAll(s);
		}
		drug_total_synonyms_aftermerge = superBeforeMergeDrug.size();
		
		log.info("Added Synonyms for MeSH from Drug Names: " + mesh_added_merged_synonyms);
		log.info("Added Synonyms for Drug Names from MeSH: " + drug_added_merged_synonyms);
		log.info("\n\n");
		log.info("MeSH having concepts with #sameas#: " 	 + keysmesh.size());
		log.info("MeSH having synonyms with #sameas# concepts: " 	 + mesh_synonyms_aftermerge);
		log.info("\n\n");
		log.info("Drug Names having concepts with #sameas#: " + keysdrug.size());
		log.info("Drug having synonyms with #sameas# concepts: " 	 + drug_synonyms_aftermerge);
		log.info("\n\n");
		log.info("MeSH Names total # of synonyms before merge: " + mesh_total_synonyms_beforemerge);
		log.info("Drug Names total # of synonyms before merge: " + drug_total_synonyms_beforemerge);	
		log.info("\n\n");
		log.info("MeSH Names total # of synonyms after merge: " + mesh_total_synonyms_aftermerge);
		log.info("Drug Names total # of synonyms after merge: " + drug_total_synonyms_aftermerge);	
	}
}
