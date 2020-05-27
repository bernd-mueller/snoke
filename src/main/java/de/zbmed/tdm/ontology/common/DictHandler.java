package de.zbmed.tdm.ontology.common;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

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
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.zbmed.tdm.util.SnowballStemmer;

/**
 * DictHandler
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class DictHandler {
	DocumentBuilderFactory builderFactory;
	DocumentBuilder builder;
	SnowballStemmer snow;
	public OntModel ont;
	
	public Document dict_doc;
	
	public DictHandler () {
		snow = new SnowballStemmer ();
		try {
			builderFactory = DocumentBuilderFactory.newInstance();
			builder = builderFactory.newDocumentBuilder();
			dict_doc = builder.newDocument();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Element addStemmedSynonymToToken (String syn, Element t) {
		syn = replaceUnderScoreBySpace (syn);
		syn = snow.doTheSnowballStem(syn);
		Element variantsyn = dict_doc.createElement("variant");
		Attr attr_base = dict_doc.createAttribute("base");
		attr_base.setValue(syn);
		variantsyn.setAttributeNode(attr_base);
		t.appendChild(variantsyn);
		return t;
	}
	
	
	public Element genSynonymFromLocalName (String localName, Element token) {
		/**
		 * Get synonyms from local name by splitting on capital letters and inserting blanks between the words
		 */
		String[] r = localName.split("(?=\\p{Lu})");
		String synblock = "";
		
		if (r.length>1) {
			String lowersynblock = "";
			for (String sub : r) {
				synblock += sub + " ";
				lowersynblock += sub.toLowerCase() + " ";
			}
			synblock = synblock.replaceAll("\\s+", " ").trim();
			lowersynblock = lowersynblock.replaceAll("\\s+", " ").trim();
			
			this.addSynonymToToken(synblock, token);
			this.addStemmedSynonymToToken(synblock, token);

		} else {
			this.addSynonymToToken(localName, token);
			this.addStemmedSynonymToToken(localName, token);
		}
		return token;
	}
	private Element createTokemFromOntClass(OntClass oc) {
		Element token = dict_doc.createElement("token");
		
		String localName = oc.getLocalName();
		String uri = oc.getURI();
		
		this.addCodeTypeToToken ("EpSO", token);
		this.addCodeValueToToken (uri, token);
		this.addCanonicalToToken (localName, token);
		
		String label = oc.getLabel(null);

		if (label != null && !label.equals("")) {
			this.addSynonymToToken(label, token);
//			System.out.println(label);
		} else {
			this.genSynonymFromLocalName (localName, token);
			
		}
		
		this.processPropertySynonym (oc, token);
		
		this.processPropertySeeAlso (oc, token);
		
		return token;
	}
	public Element processPropertySeeAlso (OntClass oc, Element token) {
		Property palso = ont.getOntProperty("http://www.w3.org/2000/01/rdf-schema#seeAlso");	
		RDFNode ralso = oc.getPropertyValue(palso);
		String label = oc.getLabel(null);

		String synonyms = "";
		if (ralso != null) {
			if (ralso.isLiteral()) {
				synonyms = ralso.toString();
			} else if (ralso.isResource()) {				
			}
		}
		if (synonyms != null && !synonyms.equals(label) && !synonyms.equals("")) {
//			System.out.println(synonyms);
			if (synonyms.startsWith("http")) {
//				System.out.println(localName + "\t\t\t" + synonyms);
				// if (synonyms.startsWith(nemourl)) {
//					System.out.println("Processing NEMO... " + synonyms);
					
					/**
					 * Resolve NEMO ontology to retrieve synonyms
					 */
					// processSynonymsFromNemo (synonyms,token);
				// } else if (synonyms.startsWith(fmaurl)) {
//					System.out.println("Processing FMA... " + synonyms);
					/*
					 * EpSo refers to incorrect class names in FMA
					 * Anyways... FMA has not many usable synonyms
					 */
					/**
					 * Resolve FMA ontology to retrieve synonyms
					 */
					// processSynonymsFromFMA (synonyms, token);

			}
		}
		return token;
	}
	public Element processPropertySynonym (OntClass oc, Element token) {
		Property psyn = ont.getOntProperty("http://www.case.edu/EpSO.owl#synonym");	
		RDFNode rsyn = oc.getPropertyValue(psyn);

		String synonyms = "";
		if (rsyn != null) {
			if (rsyn.isLiteral()) {
				synonyms = rsyn.toString();
			} else if (rsyn.isResource()) {
				System.out.println("RSYN IS RESOURCE");
			}
		}
		if (synonyms != null && !synonyms.equals("")) {
			System.out.println(synonyms);
			if (!synonyms.startsWith("http")) {
				
				if (synonyms.startsWith("RxCUI")) {
					// processSynonymsFromRxNORM (synonyms, token);
				} else {
					addSynonymToToken(synonyms, token);
				}
			}
		}
		return token;
	}

	public void createConceptMapperDictionary (OntModel ont, String dictfilename) {
		this.ont = ont;
		try {
			Element rootElement = dict_doc.createElement("synonym");
			dict_doc.appendChild(rootElement);

		    
			ExtendedIterator<OntClass> eiter = ont.listNamedClasses();
			
			while (eiter.hasNext()) {
				OntClass oc = eiter.next();
				
				Element token = createTokemFromOntClass (oc);

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
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public String replaceUnderScoreBySpace (String s) {
		return s.replaceAll("_", " ").replaceAll("\\s+", " ");
	}
	public Element addSynonymToToken (String syn, Element t) {
		syn = replaceUnderScoreBySpace (syn);
		Element variantsyn = dict_doc.createElement("variant");
		Attr attr_base = dict_doc.createAttribute("base");
		attr_base.setValue(syn);
		variantsyn.setAttributeNode(attr_base);
		t.appendChild(variantsyn);
		return t;
	}
	public Element addCodeTypeToToken (String codetype, Element t) {
		Attr attr_codetype = dict_doc.createAttribute("CodeType");
		attr_codetype.setValue(codetype);
		t.setAttributeNode(attr_codetype);
		return t;
	}
	
	public Element addCodeValueToToken (String codevalue, Element t) {
		Attr attr_codeval = dict_doc.createAttribute("CodeValue");
		attr_codeval.setValue(codevalue);
		t.setAttributeNode(attr_codeval);	
		return t;
	}
	
	public Element addCanonicalToToken (String canon, Element t) {
		Attr attr_canonical = dict_doc.createAttribute("canonical");
		attr_canonical.setValue(canon);
		t.setAttributeNode(attr_canonical);
		return t;
	}
}
