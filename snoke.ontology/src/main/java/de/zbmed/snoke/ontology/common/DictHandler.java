package de.zbmed.snoke.ontology.common;

import java.io.File;
import java.util.HashSet;
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

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * DictHandler is the common super class for the conversion of ontology resources into dictionary files that can
 * be loaded by the UIMA Analysis Engine ConceptMapper
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public abstract class DictHandler {

	String drugbankdict = "dictionaries/Dict_DrugNames.xml";

	DrugNameMapper dbm;
	DocumentBuilderFactory builderFactory;
	DocumentBuilder builder;
	public SnowballStemmer snow;
	public OntModel ont;	
	public Document dict_doc;
	
    private static final Logger log = LoggerFactory.getLogger(DictHandler.class);
    static public String inputFilePath = "";
    static public String outputFilePath = "";
    
    /**
     * Default constructor creating instances of member variables
     * 
     */
	public DictHandler () {
		snow = new SnowballStemmer ();
		dbm = new DrugNameMapper ();
		try {
			builderFactory = DocumentBuilderFactory.newInstance();
			builder = builderFactory.newDocumentBuilder();
			dict_doc = builder.newDocument();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public DrugNameMapper getDbm() {
		return dbm;
	}


	public void setDbm(DrugNameMapper dbm) {
		this.dbm = dbm;
	}
	
    /**
     * Method for processing command line parameters. This can be used when running an exported jar file.
     * 
     * @param args the command line parameters.
     */
    public static void readCLI (String args[]) {
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
    

    /**
     * Method for adding a new synonym to the current set of synonyms
     * 
     * @param syn the synonym that should be processed and added to the synset
     * @param synset the current set of synonyms
     * @return the extended set of synonyms
     */
	public Set <String> addStemmedSynonymToToken (String syn, Set <String> synset) {
		syn = replaceUnderScoreBySpace (syn);
		
		String procsyn = "";
		// Check if multi-phrase word
		if (syn.contains(" ")) {
			String [] spacesplitsyn = syn.split(" ");
			for (int i=0; i<spacesplitsyn.length; i++) {
				String stemsyn = snow.doTheSnowballStem(spacesplitsyn[i]);
				if (i == spacesplitsyn.length - 1) {
					procsyn += stemsyn;
				} else {
					procsyn += stemsyn + " ";
				}
			}
		} else {
			procsyn = snow.doTheSnowballStem(syn);
		}
		if (!syn.equals(procsyn)) {
			if (procsyn.length()>3) {
				synset.add(procsyn);
			}
		}
		return synset;
	}
	
	/**
	 * Method for generating a synonym out of the local name of an ontology class
	 * 
	 * @param localName the local name that should be used for the synonym generation
     * @param synset the current set of synonyms
     * @return the extended set of synonyms
	 */
	public Set <String> genSynonymFromLocalName (String localName, Set <String> synset) {
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
			
			synset = this.addSynonymToToken(synblock.toLowerCase(), synset);
			synset = this.addStemmedSynonymToToken(synblock.toLowerCase(), synset);

		} else {
			synset = this.addSynonymToToken(localName.toLowerCase(), synset);
			synset = this.addStemmedSynonymToToken(localName.toLowerCase(), synset);
		}
		return synset;
	}

	
	public Element createTokenFromOntClass(OntClass oc, String CodeType) {
		Element token = dict_doc.createElement("token");
		
		String localName = oc.getLocalName();
		String uri = oc.getURI();
		
		this.addCodeTypeToToken (CodeType, token);
		this.addCodeValueToToken (uri, token);
		this.addCanonicalToToken (localName, token);
		
		String label = oc.getLabel(null);
		Set <String> synset = new HashSet <String> ();
		if (label != null && !label.equals("")) {
			synset = this.addSynonymToToken(label.toLowerCase(), synset);
			synset = this.addStemmedSynonymToToken(label.toLowerCase(), synset);
		} 
		synset = this.genSynonymFromLocalName (localName, synset);
		
		synset = this.processPropertySynonym (oc, synset);
		
		synset = this.processPropertySeeAlso (oc, synset);
		
		synset = this.addDrugNamesToSynonymSet (synset);
		
		token = createTokensFromSynSet(synset, token);
		
		return token;
	}
	
	abstract public Set <String> processPropertySeeAlso (OntClass oc, Set <String> synset);
	
	public Set <String> addDrugNamesToSynonymSet (Set <String> synset) {
		Set <String> locSynSet = new HashSet <String> (synset);;
		for (String syn : locSynSet) {
			if (this.dbm.drugmap.containsKey(syn)) {
				Set <String> dset = this.dbm.drugmap.get(syn);
				for (String dsyn : dset) {
					synset = this.addSynonymToToken(dsyn, synset);
				}
			} else if (this.dbm.drugsynmap.containsKey(syn)) {
				String dkey = this.dbm.drugsynmap.get(syn);
				Set <String> dset = this.dbm.drugmap.get(dkey);
				for (String dsyn : dset) {
					synset = this.addSynonymToToken(dsyn, synset);
				}
			}
		}
		return synset;
	}
	
	
	public Element createTokensFromSynSet (Set <String> synset, Element t) {
		for (String syn : synset) {
			Element variantsyn = dict_doc.createElement("variant");
			Attr attr_base = dict_doc.createAttribute("base");
			attr_base.setValue(syn);
			variantsyn.setAttributeNode(attr_base);
			t.appendChild(variantsyn);
		}
		return t;
	}
	
	public Set <String> processPropertySynonym (OntClass oc, Set <String> synset) {
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
					synset = addSynonymToToken(synonyms, synset);
					synset = addStemmedSynonymToToken(synonyms, synset);
				}
			}
		}
		return synset;
	}

	public void createConceptMapperDictionary (OntModel ont, String dictfilename, String CodeValue) {
		this.ont = ont;
		try {
			Element rootElement = dict_doc.createElement("synonym");
			dict_doc.appendChild(rootElement);

		    
			ExtendedIterator<OntClass> eiter = ont.listNamedClasses();
			
			while (eiter.hasNext()) {
				OntClass oc = eiter.next();
				
				Element token = createTokenFromOntClass (oc, CodeValue);

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
	public Set <String> addSynonymToToken (String syn, Set <String> synset) {
		String usyn = replaceUnderScoreBySpace (syn);
		if (usyn.length()>3) {
			synset.add(usyn.toLowerCase());
		}
		return synset;
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
