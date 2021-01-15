package de.zbmed.snoke.ontology.analysis;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Set;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Common superclass for creating mappings of ontologies based on their lexical features as ConceptMapper dictionary
 * files.
 * 
 * @author Muellerb
 *
 */
/**
 * @author Muellerb
 *
 */
/**
 * @author Muellerb
 *
 */
public abstract class OntologyMapper {
	public String ontprefix = "DEFAULT";
	public String getOntprefix() {
		return ontprefix;
	}
	public void setOntprefix(String ontprefix) {
		this.ontprefix = ontprefix;
	}

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
	
	/**
	 * Creates namespace prefixes.
	 * 
	 * @return the OntModel
	 */
	OntModel createOntMappingModel() {
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
		String nsRDF = "http://www.w3.org/2000/01/rdf-schema#";
		m.setNsPrefix("rdfs", nsRDF);
		
		String nsSKOS = "http://www.w3.org/2004/02/skos/core#";
		m.setNsPrefix("SKOS", nsSKOS);
		
		return m;
	}

	
	/**
	 * Creates sameas relationship for two classes with URIs specified by parameter and and b
	 * 
	 * @param m the OntModel
	 * @param a class a
	 * @param b class b
	 * @return OntModel
	 */
	Model addEquivalenceToModel (OntModel m, String a, String b) {
		String acleaned = a.substring(0, a.indexOf("@"));
		String bcleaned = b.substring(0, b.indexOf("@"));
		if (!a.equals(b)) {
//			 String nsOWL = "http://www.w3.org/2002/07/owl#";
//			 
//			 
//			 Resource left = m.createResource(acleaned);
//			 Property sameAs = m.createProperty(nsOWL + "sameAs");
//			 Resource right = m.createResource(bcleaned);
			 //m.add(left, sameAs, right);
			 
			 OntClass oc = m.createClass(acleaned);
			 oc.addSameAs(m.createClass(bcleaned));
		}
		 return m;
	}
	
	/**
	 * Add label literals for ontology classes
	 * @param m OntModel
	 * @param map HashMap of a ConceptMapper dictionary with concept names as keys and synonyms as values
	 * @return the OntModel
	 */
	Model addLabels (OntModel m, Map<String, Set<String>> map) {
		for (String keys : map.keySet()) {
			if (keys.contains("@")) {
				String key = keys.split("@")[0];
				if (keys.split("@").length>1) {
					String canonical = keys.split("@")[1];
					 String nsRDF = "http://www.w3.org/2000/01/rdf-schema#";
					 //log.info("Adding " + key + " /label/ " + canonical);

					 OntClass oc = m.createClass(key);
					 oc.addLabel(canonical, "EN");
					 
					 Set <String> synonyms = map.get(keys);
					 
					 String nsSKOS = "http://www.w3.org/2004/02/skos/core#";
					 Property pref = m.createProperty(nsSKOS + "prefLabel");
					 
					 oc.addProperty(pref, canonical);
					 
					 Property alt = m.createProperty(nsSKOS + "altLabel");
					 
					 for (String syn : synonyms) {
						 oc.addProperty(alt, syn);
					 }
					 // oc.addLiteral(label, literal);
					 
					 // m.add(left, label, literal);
				}
			}
		}
		 return m;
	}
	
	/**
	 * Conduct the actual mapping
	 */
	public abstract void conductMapping ();
	
	/**
	 * Writes the OntModel to file
	 * 
	 * @param m the OntModel to write
	 */
	void writeModelToFile (OntModel m) {
		try {
			String filename = "resources/ontologies/" + this.getOntprefix() + ".xrdf";
			log.info("Writing model to file " + filename);
			FileOutputStream fos = new FileOutputStream(filename);
			//m.write(fos);
			m.write(fos, "RDF/XML-ABBREV");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
