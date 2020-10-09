package de.zbmed.snoke.ontology.epso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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

import gov.nih.nlm.uts.webservice.AtomDTO;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.NodeIterator;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.shared.JenaException;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.zbmed.snoke.ontology.common.DictHandler;
import de.zbmed.snoke.ontology.esso.CreateDictFromESSO;
import de.zbmed.snoke.util.SnowballStemmer;

/**
 * CreateDictFromEpSO
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class CreateDictFromEpSO extends DictHandler {
	String CodeType = "EpSO";
	private static final Logger log = LoggerFactory.getLogger(CreateDictFromEpSO.class);
	
	String nemourl = "http://purl.bioontology.org/NEMO/ontology/NEMO.owl";
	String fmaurl = "http://sig.uw.edu/fma";

	OntModel nemo;

	// RxNormClient rx;
	// Model rxnorm;
	
	DrugBankMapper dbm;
	UMLS_Client umls;
	OntHandlerFMA fma;

	public CreateDictFromEpSO () {
		super();
		// rx = new RxNormClient ();
	}

	public static void readCLI(String args[]) {
		Options options = new Options();

		Option input = new Option("i", "input", true, "input file (Ontology XRDF file)");
		input.setRequired(true);
		options.addOption(input);

		Option output = new Option("o", "output", true, "output file (Dictionary as XML file)");
		output.setRequired(true);
		options.addOption(output);

		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd = null;

		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			formatter.printHelp("utility-name", options);

			System.exit(1);
		}

		inputFilePath = cmd.getOptionValue("input");
		outputFilePath = cmd.getOptionValue("output");

		log.info("Using parameters:");
		log.info("\tinput: " + inputFilePath);
		log.info("\toutput: " + outputFilePath);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		readCLI(args);
		CreateDictFromEpSO cep = new CreateDictFromEpSO ();
		

		
		// cep.printNamedClasses();
		
		cep.getOntologyModel(inputFilePath);
		// cep.createConceptMapperDictionary(cep.ont, outputFilePath);
		cep.createConceptMapperDictionary(cep.ont, outputFilePath, "EpSO");
	}
	
	public Set <String> processPropertySeeAlso (OntClass oc, Set <String> synset) {
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
				if (synonyms.startsWith(nemourl)) {
//					System.out.println("Processing NEMO... " + synonyms);
					
					/**
					 * Resolve NEMO ontology to retrieve synonyms
					 */
					synset = processSynonymsFromNemo (synonyms,synset);
				} else if (synonyms.startsWith(fmaurl)) {
//					System.out.println("Processing FMA... " + synonyms);
					/*
					 * EpSo refers to incorrect class names in FMA
					 * Anyways... FMA has not many usable synonyms
					 */
					/**
					 * Resolve FMA ontology to retrieve synonyms
					 */
					synset = processSynonymsFromFMA (synonyms, synset);
					
				} else {
					
				}
			}
		}
		return synset;
	}
	
	
	

	

	
	public Set <String> processSynonymsFromFMA (String synonyms, Set <String> synset) {
		if (synonyms.startsWith(fmaurl)) {
			
			String fmaid = synonyms.replace(fmaurl + "#", "");
			String synFromLabel = fmaid.replaceAll("_", " ");
			String fmasyn = fma.labelsynmap.get(synFromLabel);
			System.out.println("#FMA\t\t" + fmaid + "\t" + fmasyn);
			if (fmaid != null && fmaid.length()>0) {
				synset = addSynonymToToken (synFromLabel.toLowerCase(), synset);
				synset= addStemmedSynonymToToken (synFromLabel.toLowerCase(), synset);
				System.out.println("Added as synonym from FMA label:\t" + synFromLabel);
			}
			if (fmasyn != null && fmasyn.length()>0) {
				synset = addSynonymToToken (fmasyn.toLowerCase(), synset);
				synset = addStemmedSynonymToToken (fmasyn.toLowerCase(), synset);
				System.out.println("Added as synonym from FMA synonym:\t" + fmasyn);
			}
		}

		return synset;
	}

/*
	public Element processSynonymsFromRxNORM (String synonyms, Element token) {
		if (synonyms.startsWith("RxCUI")) {
			String rxcui = synonyms.replace("RxCUI ", "");
			String meshid = rx.getMeSH(rxcui);
			String umlscui = rx.getUMLS(rxcui);
			String drugbankid = getDrunkBankId (umlscui);
			HashMap <String, ArrayList <String>> drugbanksyns = dbm.getDrugbankmap().get(drugbankid);
			for (String curdrugname : drugbanksyns.keySet()) {
				ArrayList <String> drugsyns = drugbanksyns.get(curdrugname);
				addSynonymToToken (curdrugname, token);
				for (String curdrugsyn : drugsyns) {
					addSynonymToToken(curdrugsyn, token);
				}
			}
		}
		return token;
	}
	*/
	public String getDrunkBankId (String umlscui) {
		List<AtomDTO> atoms = umls.getConceptAtoms(umls.getCurrentUMLSVersion(), umlscui);
		for (AtomDTO a : atoms) {
		    String aui = a.getUi();
		    String tty = a.getTermType();
		    String atomname = a.getTermString().getName();
		    String sourceId = a.getCode().getUi();
		    String rsab = a.getRootSource();
		    // System.out.println(aui + "\t" + tty + "\t" + atomname + "\t" + sourceId + "\t" + rsab);
  
		    if (rsab.equals("DRUGBANK")) {
		    	return sourceId;
			}
		}
		return "";
	}
	
	public Set <String> processSynonymsFromNemo (String synonyms, Set <String> synset) {
		System.out.println("Doing something in NEMO:\t" + synonyms);
		if (synonyms.startsWith(nemourl)) {
			String nemoid = synonyms.replace(nemourl + "#", "");
			OntClass nemoclass = nemo.getOntClass(synonyms);
			if (nemoclass != null) {
	
				String nemolabel = nemoclass.getLabel(null);
				String nemosyn = "";
				String synonymFromLabel = "";
				
				if (nemolabel.contains("_")) {
					synonymFromLabel = nemolabel.replaceAll("_", " ");
				}
				
				Property pnemosyn = nemo.getOntProperty("http://purl.bioontology.org/NEMO/ontology/NEMO_annotation_properties.owl#synonym");
				if (nemoclass.getPropertyValue(pnemosyn) != null) {
					RDFNode rnemosyn = nemoclass.getPropertyValue(pnemosyn);
					
					if (rnemosyn.isLiteral()) {
						nemosyn = rnemosyn.asLiteral().getString();
						//System.out.println("\t#\t" + nemoclass.getLocalName() + "\t" + nemolabel + "#\t" + nemosyn);
					} else {
						//System.out.println(rnemosyn.isResource());
					}
						
				} else {
					// System.out.println("\t#\t" + nemoclass.getLocalName() + "\t" + nemolabel);
				}
				if (nemosyn.length()>0) {
					System.out.println("Adding synonym retrieved from NEMO:\t" + nemosyn);
					
					synset = addSynonymToToken(nemosyn.toLowerCase(), synset);
					synset = addStemmedSynonymToToken(nemosyn.toLowerCase(), synset);
				}
				if (synonymFromLabel.length()>0) {
					synset = addSynonymToToken(synonymFromLabel.toLowerCase(), synset);
					synset = addStemmedSynonymToToken(synonymFromLabel.toLowerCase(), synset);
				}
			} else {
				System.err.println("NEMO class cannot be processed:\t" + synonyms);
			}
		}
		return synset;
	}
	public String replaceUnderScoreBySpace (String s) {
		return s.replaceAll("_", " ");
	}
	
	/*
	public Element addSynonymToToken (String syn, Element t) {
		syn = replaceUnderScoreBySpace (syn);
		Element variantsyn = dict_doc.createElement("variant");
		Attr attr_base = dict_doc.createAttribute("base");
		attr_base.setValue(syn.toLowerCase());
		variantsyn.setAttributeNode(attr_base);
		t.appendChild(variantsyn);
		
		addStemmedSynonymToToken(syn, t);
		
		return t;
	}
	*/
	/*
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
	*/
	
	public void printNamedClasses () {
		ExtendedIterator<OntClass> eiter = ont.listNamedClasses();
		Property psyn = ont.getOntProperty("synonym");
		int counter = 0;
		while (eiter.hasNext()) {
			OntClass oc = eiter.next();
			System.out.println(++counter + 
					"\t" + oc.getLocalName() + 
					"\t" + oc.getPropertyValue(psyn) + 
					"\t" + oc.getLabel(null) + 
					"\t" + oc.getURI());
		}
	}
	
	public void printObjects () {
		NodeIterator iter = ont.listObjects();
		int counter = 0;
		while (iter.hasNext()) {
			RDFNode rdf = iter.nextNode();
			System.out.println(++counter + "\t" + rdf.toString());
		}
	}
	
	public void printClasses () {
		ExtendedIterator<OntClass> eiter = ont.listClasses();
		int counter = 0;
		while (eiter.hasNext()) {
			OntClass oc = eiter.next();
			System.out.println(++counter + "\t" + oc.getLocalName());
		}
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
		
		/**
		 * Load RXNORM ontology
		 */
		// loadRxNorm ("resources/ontologies/RXNORM.ttl");
		
		/**
		 * Load NEMO ontology
		 */
		loadNemo ("resources/ontologies/NEMO.owl");

		/**
		 * Load FMA ontology
		 */
		
		loadFMA ("resources/ontologies/fma.owl");

	}
	
	public void loadNemo (String nemoontoFile) {
		nemo = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		
		try {
			InputStream is = new FileInputStream (new File (nemoontoFile));
			//ont.read(ontoFile);
			nemo.read(is, "");
			log.info("Ontology " + nemoontoFile + " loaded.");
		} catch (JenaException je) {
			log.error("ERROR" + je.getMessage());
			je.printStackTrace();
			System.exit(0);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadFMA (String fmaontoFile) {
		fma = new OntHandlerFMA(fmaontoFile);
	}
	/*
	public void loadRxNorm (String rxnormFile) {
		rxnorm = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		
		try {
			InputStream is = new FileInputStream (new File (rxnormFile));
			//ont.read(ontoFile);
			rxnorm.read(is, null,"TTL") ;
			log.info("Ontology " + rxnormFile + " loaded.");
		} catch (JenaException je) {
			log.error("ERROR" + je.getMessage());
			je.printStackTrace();
			System.exit(0);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
*/
}
