package de.zbmed.snoke.ontology.epso;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	private static final Logger log = LoggerFactory.getLogger(CreateDictFromEpSO.class);
	
	String nemourl = "http://purl.bioontology.org/NEMO/ontology/NEMO.owl";
	String fmaurl = "http://sig.uw.edu/fma";
	OntModel ont;
	OntModel nemo;
	
	SnowballStemmer snow;

	Model rxnorm;
	
	DocumentBuilderFactory builderFactory;
	DocumentBuilder builder;
	
	Document dict_doc;
	
	DrugBankMapper dbm;
	UMLS_Client umls;
	RxNormClient rx;
	OntHandlerFMA fma;

	public CreateDictFromEpSO () {
		super();
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
		
		/**
		 * Load NEMO ontology
		 */
		cep.loadNemo ("C:\\Users\\muellerb\\Desktop\\Epilepsy2017\\EpSO\\NEMO.owl");

		/**
		 * Load FMA ontology
		 */
		cep.loadFMA ("C:\\Users\\muellerb\\Desktop\\Epilepsy2017\\FMA\\fma.owl");
		
		// cep.printNamedClasses();
		
		
		cep.createConceptMapperDictionary();
		

		
	}
	
	public void createConceptMapperDictionary () {

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
			StreamResult result = new StreamResult(new File("C:\\Users\\muellerb\\Desktop\\Epilepsy2017\\EpSO\\Dict_EpSO-2018-stemmed.xml"));
			transformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
				if (synonyms.startsWith(nemourl)) {
//					System.out.println("Processing NEMO... " + synonyms);
					
					/**
					 * Resolve NEMO ontology to retrieve synonyms
					 */
					processSynonymsFromNemo (synonyms,token);
				} else if (synonyms.startsWith(fmaurl)) {
//					System.out.println("Processing FMA... " + synonyms);
					/*
					 * EpSo refers to incorrect class names in FMA
					 * Anyways... FMA has not many usable synonyms
					 */
					/**
					 * Resolve FMA ontology to retrieve synonyms
					 */
					processSynonymsFromFMA (synonyms, token);
					
				} else {
					
				}
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
					processSynonymsFromRxNORM (synonyms, token);
				} else {
					addSynonymToToken(synonyms, token);
				}
			}
		}
		return token;
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
			synblock = synblock.trim();
			lowersynblock = lowersynblock.trim();
			
			this.addSynonymToToken(synblock, token);

		} else {
			this.addSynonymToToken(localName, token);
			
		}
		return token;
	}

	public Element processSynonymsFromFMA (String synonyms, Element token) {
		if (synonyms.startsWith(fmaurl)) {
			
			String fmaid = synonyms.replace(fmaurl + "#", "");
			String synFromLabel = fmaid.replaceAll("_", " ");
			String fmasyn = fma.labelsynmap.get(synFromLabel);
			System.out.println("#FMA\t\t" + fmaid + "\t" + fmasyn);
			if (fmaid != null && fmaid.length()>0) {
				addSynonymToToken (synFromLabel, token);
				System.out.println("Added as synonym from FMA label:\t" + synFromLabel);
			}
			if (fmasyn != null && fmasyn.length()>0) {
				addSynonymToToken (fmasyn, token);
				System.out.println("Added as synonym from FMA synonym:\t" + fmasyn);
			}
		}

		return token;
	}


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
	
	public Element processSynonymsFromNemo (String synonyms, Element token) {
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
					addSynonymToToken(nemosyn, token);
				}
				if (synonymFromLabel.length()>0) {
					addSynonymToToken(synonymFromLabel, token);
				}
			} else {
				System.err.println("NEMO class cannot be processed:\t" + synonyms);
			}
		}
		return token;
	}
	public String replaceUnderScoreBySpace (String s) {
		return s.replaceAll("_", " ");
	}
	
	public Element addSynonymToToken (String syn, Element t) {
		syn = replaceUnderScoreBySpace (syn);
		Element variantsyn = dict_doc.createElement("variant");
		Attr attr_base = dict_doc.createAttribute("base");
		attr_base.setValue(syn);
		variantsyn.setAttributeNode(attr_base);
		t.appendChild(variantsyn);
		
		addStemmedSynonymToToken(syn, t);
		
		return t;
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
			ont.read(ontoFile);

			System.out.println("Ontology " + ontoFile + " loaded.");
		} catch (JenaException je) {
			System.err.println("ERROR" + je.getMessage());
			je.printStackTrace();
			System.exit(0);
		}
	}
	
	public void loadNemo (String nemoontoFile) {
		nemo = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		
		try {
			nemo.read(nemoontoFile);

			System.out.println("Ontology " + nemoontoFile + " loaded.");
		} catch (JenaException je) {
			System.err.println("ERROR" + je.getMessage());
			je.printStackTrace();
			System.exit(0);
		}
	}
	
	public void loadFMA (String fmaontoFile) {
		fma = new OntHandlerFMA(fmaontoFile);
	}
	
	public void loadRxNorm (String rxnormFile) {
		rxnorm = ModelFactory.createDefaultModel();
		
		try {
			rxnorm.read(rxnormFile);

			System.out.println("Ontology " + rxnormFile + " loaded.");
		} catch (JenaException je) {
			System.err.println("ERROR" + je.getMessage());
			je.printStackTrace();
			System.exit(0);
		}
	}

}
