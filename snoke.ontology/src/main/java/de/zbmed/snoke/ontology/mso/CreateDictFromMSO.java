package de.zbmed.snoke.ontology.mso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Set;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.shared.JenaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.zbmed.snoke.ontology.ado.CreateDictFromADO;
import de.zbmed.snoke.ontology.common.DictHandler;

public class CreateDictFromMSO extends DictHandler {
	OntModel mso;
	String msofile = "";
	String outfile = "";
	
	 private static final Logger log = LoggerFactory.getLogger(CreateDictFromMSO.class);
	
	// esso-rdf-owl.owl

	 CreateDictFromMSO () {
		super();
	}
	
	public void getOntologyModel(String ontoFile) {
		msofile = ontoFile;
		ont = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		
		try {
			InputStream is = new FileInputStream (new File (ontoFile));
			//ont.read(ontoFile);
			ont.read(is, "");
			log.info("Ontology " + ontoFile + " loaded.");
		} catch (JenaException je) {
			log.error("ERROR" + je.getMessage());
			je.printStackTrace();
			System.exit(0);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
    	readCLI (args);
    	CreateDictFromMSO cdfm = new CreateDictFromMSO();
    	cdfm.getOntologyModel(inputFilePath);
    	cdfm.createConceptMapperDictionary(cdfm.ont, outputFilePath, "MSO");
	}

	@Override
	public Set<String> processPropertySeeAlso(OntClass oc, Set<String> synset) {
		// TODO Auto-generated method stub
		return synset;
	}
	

}
