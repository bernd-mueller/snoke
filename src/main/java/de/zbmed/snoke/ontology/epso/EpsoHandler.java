package de.zbmed.snoke.ontology.epso;

import java.util.Iterator;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.ValidityReport;
import org.apache.jena.shared.JenaException;
/**
 * EpsoHandler
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class EpsoHandler {
	OntModel epso;
	String epsofile = "C:\\Users\\muellerb\\Desktop\\Epilepsy2017\\EpSO\\EpSO_200717.owl";
	
	EpsoHandler () {
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EpsoHandler e = new EpsoHandler ();
		e.getOntologyModel("");

	}
	
	public void getOntologyModel(String ontoFile) {
		if (ontoFile.equals("")) {
			ontoFile = epsofile;
		}
		epso = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_RULE_INF);
		
		try {
			epso.read(ontoFile);

			System.out.println("Ontology " + ontoFile + " loaded.");
		} catch (JenaException je) {
			System.err.println("ERROR" + je.getMessage());
			je.printStackTrace();
			System.exit(0);
		}
		ValidityReport validity = epso.validate();
		if (validity.isValid()) {
		    System.out.println("OK");
		} else {
		    System.out.println("Conflicts");
		    for (Iterator i = validity.getReports(); i.hasNext(); ) {
		        System.out.println(" - " + i.next());
		    }
		}
	}
	
	public void addReaser () {
		
	}
	
	public void addOntologyModel (String anotherontofile) {
		
	}

}
