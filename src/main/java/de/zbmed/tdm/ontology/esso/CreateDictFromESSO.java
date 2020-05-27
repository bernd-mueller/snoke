package de.zbmed.tdm.ontology.esso;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.shared.JenaException;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.zbmed.tdm.ontology.common.DictHandler;
import de.zbmed.tdm.ontology.epso.DrugBankMapper;
import de.zbmed.tdm.ontology.epso.RxNormClient;
import de.zbmed.tdm.ontology.epso.UMLS_Client;
/**
 * CreateDictFromESSO
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class CreateDictFromESSO extends DictHandler {
	OntModel esso;
	String essofile = "C:\\Users\\muellerb\\Desktop\\Epilepsy2017\\ESSO\\esso-func-owl.owl2";
	
	// esso-rdf-owl.owl

	CreateDictFromESSO (String ontologyfilename) {
		super();
		this.getOntologyModel(ontologyfilename);
		
	}
	
	public void getOntologyModel(String ontoFile) {
		essofile = ontoFile;
		ont = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		
		try {
			ont.read(ontoFile);

			System.out.println("Ontology " + ontoFile + " loaded.");
		} catch (JenaException je) {
			System.err.println("ERROR" + je.getMessage());
			je.printStackTrace();
			System.exit(0);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CreateDictFromESSO cdfe = new CreateDictFromESSO("resources/ontologies/ESSO/esso-rdf-owl.owl");
		
		cdfe.createConceptMapperDictionary();
	}
	
	private Element createTokemFromOntClass(OntClass oc) {
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
//			System.out.println(label);
		} else {
			this.genSynonymFromLocalName (localName, token);
		}
		
		// this.processPropertySynonym (oc, token);
		
		//this.processPropertySeeAlso (oc, token);
		
		return token;
	}
	
	public void createConceptMapperDictionary () {

		try {
			Element rootElement = dict_doc.createElement("synonym");
			dict_doc.appendChild(rootElement);

		    
			ExtendedIterator<OntClass> eiter = ont.listNamedClasses();
			
			while (eiter.hasNext()) {
				OntClass oc = eiter.next();
				
				Element token = createTokemFromOntClass (oc);

				System.out.println("Processing " + oc.getLocalName());
				rootElement.appendChild(token);
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer;

			transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(dict_doc);
			StreamResult result = new StreamResult(new File("dictionaries/Dict_ESSO-stemmed.xml"));
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
