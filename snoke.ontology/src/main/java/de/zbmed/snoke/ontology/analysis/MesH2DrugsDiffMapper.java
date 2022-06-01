package de.zbmed.snoke.ontology.analysis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MesH2DrugsDiffMapper extends OntologyMapper {
	private static final Logger log = LoggerFactory.getLogger(MesH2DrugsDiffMapper.class);
	static int inStemmedButNotInUnstemmed = 0;
	static int inUnstemmedButNotInStemmed = 0;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MesH2DrugsDiffMapper mdm = new MesH2DrugsDiffMapper ();
		mdm.doTheDiff();
		log.info("In Stemmed but not in Unstemmed: " + inStemmedButNotInUnstemmed);
		log.info("In Unstemmed but not in Stemmed: " + inUnstemmedButNotInStemmed);
	}
	public Map <String, Set <String>> diffingUnstemmed () {
		Map <String, Set <String>> mmesh = getDl().getCodeMapForDict("dictionaries/Dict_MeSH.xml");
		Map <String, Set <String>> mdrug = getDl().getCodeMapForDict("dictionaries/Dict_DrugNames.xml");
		
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
		}
		int n12 = mmesh_mdrug.keySet().size();
		log.info("#Intersection Unstemmed MeSH, Drug: " + n12);
		return mmesh_mdrug;
	}
	
	public Map <String, Set <String>> diffingStemmed () {
		Map <String, Set <String>> mmesh = getDl().getCodeMapForDict("dictionaries/Dict_MeSH_stemmed.xml");
		Map <String, Set <String>> mdrug = getDl().getCodeMapForDict("dictionaries/Dict_DrugNames_stemmed.xml");
		
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
		}
		int n12 = mmesh_mdrug.keySet().size();
		log.info("#Intersection Unstemmed MeSH, Drug: " + n12);
		return mmesh_mdrug;
	}
	
	public void doTheDiff () {
		Map <String, Set <String>> stemmed = this.diffingStemmed();
		Map <String, Set <String>> unstemmed = this.diffingUnstemmed();
		
		
		for (String ks : stemmed.keySet()) {
			if (!unstemmed.containsKey(ks)) {
				log.info("Stemmed but not in unstemmed: " + ks);
				inStemmedButNotInUnstemmed++;
			}
		}
		
		for (String ku : stemmed.keySet()) {
			if (!stemmed.containsKey(ku)) {
				log.info("Unstemmed but not in stemmed: " + ku);
				inUnstemmedButNotInStemmed++;
			}
		}
	}
	@Override
	public void conductMapping() {
		// TODO Auto-generated method stub
		
	}

}
