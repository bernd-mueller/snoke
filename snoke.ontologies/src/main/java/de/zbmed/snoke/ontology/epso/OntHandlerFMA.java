package de.zbmed.snoke.ontology.epso;

import java.util.HashMap;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.shared.JenaException;
import org.apache.jena.util.iterator.ExtendedIterator;
/**
 * OntHandlerFMA
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class OntHandlerFMA {
	OntModel fma;
	HashMap <String, String> labelsynmap;
	String fmafilepath = "C:\\Users\\muellerb\\Desktop\\Epilepsy2017\\FMA\\fma.owl";
	
	OntHandlerFMA () {
		loadFMA ("");
	}
	
	OntHandlerFMA (String filepath) {
		loadFMA(filepath);
	}
	
	public void setFmaPath (String filepath) {
		if (!filepath.equals("")) {
			fmafilepath = filepath;
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		OntHandlerFMA fma = new OntHandlerFMA ();

		
	}

	public void loadFMA (String filepath) {
		setFmaPath(filepath);
		fma = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		
		try {
			fma.read(this.fmafilepath);

			System.out.println("Ontology " + fmafilepath + " loaded.");
		} catch (JenaException je) {
			System.err.println("ERROR" + je.getMessage());
			je.printStackTrace();
			System.exit(0);
		}
		labelsynmap = new HashMap <String, String> ();
		
		
		
		ExtendedIterator<OntClass> exit = fma.listClasses();
		OntProperty fmasyn = fma.getOntProperty("http://purl.org/sig/ont/fma/synonym");
		while (exit.hasNext()) {
			OntClass oc = exit.next();
			if (oc.getLocalName() != null) {
				String syn = "";
				if (oc.hasProperty(fmasyn)) {
					syn = oc.getPropertyValue(fmasyn).toString();
					// System.out.println(oc.getLabel(null) + "\t" + oc.getLocalName() + "\t" + syn);
					
				}
				labelsynmap.put(oc.getLabel(null), syn);
			}
		}
	}
}
