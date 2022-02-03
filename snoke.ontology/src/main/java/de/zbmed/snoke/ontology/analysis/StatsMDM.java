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

public class StatsMDM extends OntologyMapper {
	Set<String> n03drugsm = new HashSet<String>();
	Set<String> n03drugsd = new HashSet<String>();
	private static final Logger log = LoggerFactory.getLogger(MeSH2DrugsOntologyMapper.class);

	StatsMDM () {
		// N03AA01 	methylphenobarbital
		n03drugsd.add("Methylphenobarbital");
		n03drugsm.add("Prominal");
		
		// N03AA02 	phenobarbital
		n03drugsd.add("Phenobarbital");
		n03drugsm.add("Gardenal");
		
		// N03AA03 	primidone
		n03drugsd.add("Primidone");
		n03drugsm.add("Primidon Holsten");
		
		// N03AA04 	barbexaclone
		n03drugsd.add("Barbexaclone");
		// Not available for MeSH: n03drugsm.add("");
		
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MeSH2DrugsOntologyMapper m2d = new MeSH2DrugsOntologyMapper();
		m2d.conductMapping();
	}

	/**
	 * Creates namespace for DrugBank, MeSH and ICD
	 */
	OntModel createOntMappingModel() {

		OntModel m = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);

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
		int mesh_added_merged_synonyms = 0;
		int drug_added_merged_synonyms = 0;

		setOntprefix("MDM");
		OntModel m = createOntMappingModel();
		Map<String, Set<String>> mmesh = getDl().getCodeMapForDict("dictionaries/Dict_MeSH.xml");
		Map<String, Set<String>> mdrug = getDl().getCodeMapForDict("dictionaries/Dict_DrugNames.xml");
		Map<String, Set<String>> mmeshstemmed = getDl().getCodeMapForDict("dictionaries/Dict_MeSH_stemmed.xml");
		Map<String, Set<String>> mdrugstemmed = getDl().getCodeMapForDict("dictionaries/Dict_DrugNames_stemmed.xml");

		// MeSH vs. ICD vs. Drugs
		Map<String, Set<String>> mmesh_mdrug = getDl().createIntersection(mmeshstemmed, mdrugstemmed);
		Map<String, Set<String>> mmeshmerge = new HashMap<String, Set<String>>(mmeshstemmed);
		Map<String, Set<String>> mdrugmerge = new HashMap<String, Set<String>>(mdrugstemmed);

		Set<String> keysdrug = new HashSet<String>();
		Set<String> keysmesh = new HashSet<String>();

		for (String keys : mmesh_mdrug.keySet()) {
			String[] s_keys = keys.split("#SAMEAS#");
			String k1 = s_keys[0];
			String k2 = s_keys[1];

			Set<String> mergedSet = mmesh_mdrug.get(keys);

			Set<String> mergedsynset1 = new HashSet<String>(mmesh.get(k1));
			mergedsynset1.addAll(mergedSet);

			Set<String> mergedsynset2 = new HashSet<String>(mdrug.get(k2));
			mergedsynset2.addAll(mergedSet);

			mmeshmerge.put(k1, mergedsynset1);
			mdrugmerge.put(k2, mergedsynset2);

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

		for (String kmesh : mmesh.keySet()) {
			Set<String> premerge = mmesh.get(kmesh);
			Set<String> postmerge = mmeshmerge.get(kmesh);
			mesh_added_merged_synonyms += (postmerge.size() - premerge.size());
		}

		for (String kdrug : mdrug.keySet()) {
			Set<String> premerge = mdrug.get(kdrug);
			Set<String> postmerge = mdrugmerge.get(kdrug);
			drug_added_merged_synonyms += (postmerge.size() - premerge.size());
		}

		log.info("Added Synonyms for MeSH from Drug Names: " + mesh_added_merged_synonyms);
		log.info("Added Synonyms for Drug Names from MeSH: " + drug_added_merged_synonyms);
		log.info("MeSH having #sameas#: " + keysmesh.size());
		log.info("Drug Names having #sameas#: " + keysdrug.size());

	}
}
