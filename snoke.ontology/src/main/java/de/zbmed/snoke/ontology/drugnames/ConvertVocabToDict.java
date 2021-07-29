package de.zbmed.snoke.ontology.drugnames;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.jena.ontology.OntClass;

import de.zbmed.snoke.ontology.common.DictHandler;
import de.zbmed.snoke.ontology.common.SnowballStemmer;
import de.zbmed.snoke.ontology.common.Token;

public class ConvertVocabToDict extends DictHandler {
	SnowballStemmer snow = new SnowballStemmer();
	
	public static void main(String[] args) {
		ConvertVocabToDict cvd = new ConvertVocabToDict ();
		cvd.readCLI (args);
		cvd.loadVocab();
		
	}
	
	public void loadVocab () {
		String drugbankurl = "https://go.drugbank.com/drugs/";
		BufferedReader reader;
		int stemcounter = 0;
		try {
			Set <Token> tokens = new HashSet <Token> ();
			reader = new BufferedReader(new FileReader(this.inputFilePath));
			String line = reader.readLine();
			while (line != null) {
				String [] splitline = line.split("\t");
				Token t = new Token ();
				if (splitline.length>2) {
					String drugbankid = splitline[0];
					t.setCodeValue(drugbankurl+drugbankid);
					String canonical = splitline[2];
					t.setCanonical(canonical.trim());
					if (splitline.length>5) {
						String synonyms = splitline[5];
						Set <String> syns = new HashSet <String> ();
						for (String s : synonyms.split("\\|")) {
							syns.add(s.trim());
							String stemmedSynonym = snow.doTheSnowballStem(s.trim());
							if (!syns.contains(stemmedSynonym)) {
								syns.add(stemmedSynonym);
								stemcounter++;
							}
						}
						t.setSynonyms(syns);
					}


					tokens.add(t);
				} else {
					System.out.println("Dropped line: " + line);
				}
				// System.out.println(line);
				// read next line
				line = reader.readLine();
			}
			reader.close();
			this.createConceptMapperDict(tokens, this.outputFilePath, "DrugNames");
			System.out.println("Number of added stemmed synonyms: " + stemcounter);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public Set<String> processPropertySeeAlso(OntClass oc, Set<String> synset) {
		// TODO Auto-generated method stub
		return null;
	}

}
