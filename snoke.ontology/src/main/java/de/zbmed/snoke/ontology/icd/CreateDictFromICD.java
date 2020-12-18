package de.zbmed.snoke.ontology.icd;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.w3c.dom.Element;

import de.zbmed.snoke.ontology.common.DictHandler;

/**
 * Implementation for creating a dictionary for ICD with inherited methods from DictHandler
 * 
 * @author Muellerb
 * @since 2020
 */
public class CreateDictFromICD extends DictHandler {

	/**
	 * Similar method to the one that calls it with a signature for OntClass. Creates a dictionary from a set
	 * of ICDentities
	 * 
	 * @param icdents Set of ICDentity produced by the class ICDAPIclient
	 * @param dictfilename the output file name for the dictionary
	 * @param CodeValue the letter code for the dictionary
	 */
	public void createConceptMapperDictionary (Set<ICDentity> icdents, String dictfilename, String CodeValue) {
		try {
			Element rootElement = dict_doc.createElement("synonym");
			dict_doc.appendChild(rootElement);

			for (ICDentity ie : icdents) {
				Element token = createTokenFromICDentity (ie, CodeValue);
				rootElement.appendChild(token);
			}
		   
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer;

			transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(dict_doc);
			StreamResult result = new StreamResult(new File(dictfilename));
			transformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * Creates tokens from an ICDentity
	 * 
	 * @param ie the ICDentity to be processed
	 * @param CodeType the letter code for the dictionary
	 * @return token element for the dictionary 
	 */
	private Element createTokenFromICDentity(ICDentity ie, String CodeType) {
		Element token = dict_doc.createElement("token");

		String localName = ie.getTitle();
		String uri = "https://id.who.int/icd/entity/" + ie.getId();

		this.addCodeTypeToToken(CodeType, token);
		this.addCodeValueToToken(uri, token);
		this.addCanonicalToToken(localName, token);
		Set <String> synset = new HashSet <String> ();
		if (localName != null && !localName.equals("")) {
			synset = this.addSynonymToToken(localName.toLowerCase(), synset);
			synset = this.addStemmedSynonymToToken(localName.toLowerCase(), synset);
		}
		
		Set<String> synonyms = ie.getSynonyms();
		
		for (String synonym : synonyms) {
			Set <String> copyset = new HashSet <String> (synset);
			synset = this.addSynonymToToken(synonym.toLowerCase(), copyset);
			copyset = new HashSet <String> (synset);
			synset = this.addStemmedSynonymToToken(synonym.toLowerCase(), copyset);
		}
		Set <String> copyset = new HashSet <String> (synset);
		synset = this.genSynonymFromLocalName(localName, copyset);
		copyset = new HashSet <String> (synset);
		synset = this.addDrugNamesToSynonymSet(copyset);

		token = createTokensFromSynSet(synset, token);

		return token;

	}

	/**
	 * Empty override
	 */
	@Override
	public Set<String> processPropertySeeAlso(OntClass oc, Set<String> synset) {
		// TODO Auto-generated method stub
		return null;
	}

}
