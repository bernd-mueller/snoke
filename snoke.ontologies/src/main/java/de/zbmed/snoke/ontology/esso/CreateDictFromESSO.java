package de.zbmed.snoke.ontology.esso;

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
import org.w3c.dom.Element;

import de.zbmed.snoke.ontology.common.DictHandler;
/**
 * CreateDictFromESSO
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class CreateDictFromESSO extends DictHandler {
	OntModel esso;
	String essofile = "";
	String outfile = "";
	
	 private static final Logger log = LoggerFactory.getLogger(CreateDictFromESSO.class);
	
	// esso-rdf-owl.owl

	CreateDictFromESSO () {
		super();
	}
	
	public void getOntologyModel(String ontoFile) {
		essofile = ontoFile;
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
		CreateDictFromESSO cdfe = new CreateDictFromESSO();
		cdfe.getOntologyModel(inputFilePath);
		cdfe.createConceptMapperDictionary(outputFilePath);
	}
	
	private Element createTokenFromOntClass(OntClass oc) {
		Element token = dict_doc.createElement("token");
		
		String localName = oc.getLocalName();
		String uri = oc.getURI();
		
		this.addCodeTypeToToken ("ESSO", token);
		this.addCodeValueToToken (uri, token);
		this.addCanonicalToToken (localName, token);
		
		String label = oc.getLabel(null);

		if (label != null && !label.equals("")) {
			this.addSynonymToToken(label, token);
			this.addStemmedSynonymToToken(label, token);
		} else {
			this.genSynonymFromLocalName (localName, token);
		}
		
		// this.processPropertySynonym (oc, token);
		
		//this.processPropertySeeAlso (oc, token);
		
		return token;
	}
	
	public void createConceptMapperDictionary (String outputfile) {

		try {
			Element rootElement = dict_doc.createElement("synonym");
			dict_doc.appendChild(rootElement);

		    
			ExtendedIterator<OntClass> eiter = ont.listNamedClasses();
			
			while (eiter.hasNext()) {
				OntClass oc = eiter.next();
				
				Element token = createTokenFromOntClass (oc);

				log.info("Processing " + oc.getLocalName());
				rootElement.appendChild(token);
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer;

			transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(dict_doc);
			StreamResult result = new StreamResult(new File(outputfile));
			transformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
