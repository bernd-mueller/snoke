package de.zbmed.snoke.ontology.common;

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
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.shared.JenaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DefaultOntologyConverter extends DictHandler {
	static String CodeValue = "";
	OntModel o;
	String ofile = "";
	String outfile = "";
	
	 private static final Logger log = LoggerFactory.getLogger(DefaultOntologyConverter.class);
	
	// esso-rdf-owl.owl

	 DefaultOntologyConverter () {
		super();
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
	        
	        Option codevalue = new Option("c", "codevalue", true, "CodeValue for dictionary");
	        codevalue.setRequired(true);
	        options.addOption(codevalue);
	        

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
	        CodeValue = cmd.getOptionValue("codevalue");
	        
	        log.info("Using parameters:");
	        log.info("\tinput: " + inputFilePath);
	        log.info("\toutput: " + outputFilePath);
	    }
	    
	public void getOntologyModel(String ontoFile) {
		ofile = ontoFile;
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
    	DefaultOntologyConverter doc = new DefaultOntologyConverter();
    	doc.getOntologyModel(inputFilePath);
    	doc.createConceptMapperDictionary(doc.ont, outputFilePath, CodeValue);
	}

	@Override
	public Set<String> processPropertySeeAlso(OntClass oc, Set<String> synset) {
		// TODO Auto-generated method stub
		return synset;
	}
	

}
