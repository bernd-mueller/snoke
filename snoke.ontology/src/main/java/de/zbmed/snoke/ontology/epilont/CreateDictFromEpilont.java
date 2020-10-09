package de.zbmed.snoke.ontology.epilont;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.shared.JenaException;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

import de.zbmed.snoke.ontology.common.DictHandler;
import de.zbmed.snoke.ontology.fenics.CreateDictFromFENICS;
import de.zbmed.snoke.util.SnowballStemmer;

/**
 * CreateDictFromEpilont
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class CreateDictFromEpilont extends DictHandler {
	 private static final Logger log = LoggerFactory.getLogger(CreateDictFromEpilont.class);
	OntModel epilont;
	String epilontfile = "";
	CreateDictFromEpilont () {
		super();
	}

	
	public static void main (String args []) {
    	readCLI (args);
    	CreateDictFromEpilont cdfepilont = new CreateDictFromEpilont();
    	cdfepilont.getOntologyModel(inputFilePath);
    	cdfepilont.createConceptMapperDictionary(cdfepilont.ont, outputFilePath, "EPILONT");
	}
	
	public void getOntologyModel(String ontoFile) {
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
	
}
