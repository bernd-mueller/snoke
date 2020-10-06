package de.zbmed.snoke.ontology.esso;

import java.util.Iterator;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.ValidityReport;
import org.apache.jena.shared.JenaException;

import de.zbmed.snoke.ontology.epso.EpsoHandler;
/**
 * EssoHandler
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class EssoHandler {
	OntModel esso;
	String essofile = "C:\\Users\\muellerb\\Desktop\\Epilepsy2017\\ESSO\\ESSO V1.03.owl";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EssoHandler e = new EssoHandler ();
		e.getOntologyModel("");

	}
	public void getOntologyModel(String ontoFile) {
		if (ontoFile.equals("")) {
			ontoFile = essofile;
		}
		esso = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_RULE_INF);
		
		try {
			esso.read(ontoFile);

			System.out.println("Ontology " + ontoFile + " loaded.");
		} catch (JenaException je) {
			System.err.println("ERROR" + je.getMessage());
			je.printStackTrace();
			System.exit(0);
		}
		ValidityReport validity = esso.validate();
		if (validity.isValid()) {
		    System.out.println("OK");
		} else {
		    System.out.println("Conflicts");
		    for (Iterator i = validity.getReports(); i.hasNext(); ) {
		        System.out.println(" - " + i.next());
		    }
		}
	}
}
