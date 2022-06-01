package de.zbmed.snoke.ontology.ohd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.shared.JenaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.zbmed.snoke.ontology.common.DictHandler;
import de.zbmed.snoke.ontology.common.OntHandlerFMA;


public class CreateDictFromOHD extends DictHandler {
	private static final Logger log = LoggerFactory.getLogger(CreateDictFromOHD.class);
	
	OntHandlerFMA fma;
	
	// String fmaurl = "http://sig.uw.edu/fma";
	String fmaurl = "http://purl.obolibrary.org/obo/FMA";
	
	public static void main(String[] args) {
		CreateDictFromOHD cohd = new CreateDictFromOHD ();
		cohd.readCLI(args);
		cohd.getOntologyModel(inputFilePath);
		// cep.createConceptMapperDictionary(cep.ont, outputFilePath);
		cohd.createConceptMapperDictionary(cohd.ont, outputFilePath, "OHD");
		
	}
	public void loadFMA (String fmaontoFile) {
		fma = new OntHandlerFMA(fmaontoFile);
	}

	public Set <String> processSynonymsFromFMA (String synonyms, Set <String> synset) {
		if (synonyms.startsWith(fmaurl)) {
			
			String fmaid = synonyms.replace(fmaurl + "_", "");
			String synFromLabel = fmaid.replaceAll("_", " ");
			String fmasyn = fma.getLabelsynmap().get(synFromLabel);
			log.debug("#FMA\t\t" + fmaid + "\t" + fmasyn);
			if (fmaid != null && fmaid.length()>0) {
				synset = addSynonymToToken (synFromLabel.toLowerCase(), synset);
				synset= addStemmedSynonymToToken (synFromLabel.toLowerCase(), synset);
				System.out.println("Added as synonym from FMA label:\t" + synFromLabel);
			}
			if (fmasyn != null && fmasyn.length()>0) {
				synset = addSynonymToToken (fmasyn.toLowerCase(), synset);
				synset = addStemmedSynonymToToken (fmasyn.toLowerCase(), synset);
				log.debug("Added as synonym from FMA synonym:\t" + fmasyn);
			}
		}

		return synset;
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
			log.error(e.getMessage());
			formatter.printHelp("utility-name", options);

			System.exit(1);
		}

		inputFilePath = cmd.getOptionValue("input");
		outputFilePath = cmd.getOptionValue("output");

		log.info("Using parameters:");
		log.info("\tinput: " + inputFilePath);
		log.info("\toutput: " + outputFilePath);
	}
	@Override
	public Set<String> processPropertySeeAlso(OntClass oc, Set<String> synset) {
		// TODO Auto-generated method stub
		return synset;
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
			System.exit(-1);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/**
		 * Load FMA ontology
		 */
		
		loadFMA ("resources/ontologies/fma.owl");

	}
}
