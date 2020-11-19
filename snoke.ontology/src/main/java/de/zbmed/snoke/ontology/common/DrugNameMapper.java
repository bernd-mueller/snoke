package de.zbmed.snoke.ontology.common;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import de.zbmed.snoke.ontology.analysis.DictLoader;

/**
 * Loads dictionary for drug names in order to generate maps with concepts and synonyms
 * 
 * @author Muellerb
 * @since 2020
 */
public class DrugNameMapper {
	DictLoader drugnameloader;
	Map <String, String> drugnameidmap;
	Map <String, Set <String>> drugmap;
	Map <String, String> drugsynmap;
	
	/**
	 * Default constructor
	 */
	public DrugNameMapper () {
		drugnameloader = new DictLoader();
		drugmap = drugnameloader.getMapForDict("dictionaries/Dict_DrugNames.xml");
		drugsynmap = drugnameloader.getValueMapForDict("dictionaries/Dict_DrugNames.xml");
		drugnameidmap = readMappingFile("resources/drugbank/db-atc.map");
	}
	
	
	/**
	 * Getter for Map of Drug Names
	 * 
	 * @return Map with concepts as keys and synonyms as Set of values
	 */
	public Map<String, Set<String>> getDrugmap() {
		return drugmap;
	}

	/**
	 * Setter for Map of Drug Names 
	 * 
	 * @param drugmap Map with concepts as keys and synonyms as Set of values
	 */
	public void setDrugmap(Map<String, Set<String>> drugmap) {
		this.drugmap = drugmap;
	}


	/**
	 * Getter for Map with Synonyms of Drug Names 
	 * 
	 * return drugmap Map with synonyms as keys and concepts as values
	 */
	public Map<String, String> getDrugsynmap() {
		return drugsynmap;
	}


	/**
	 * Setter for Map with Synonyms of Drug Names 
	 * 
	 * @param drugmap Map with synonyms as keys and concepts as values
	 */
	public void setDrugsynmap(Map<String, String> drugsynmap) {
		this.drugsynmap = drugsynmap;
	}

	/**
	 * Getter for Map with Identifiers and names of drugs 
	 * 
	 * @return drugnameidmap Map with names as keys and identifiers as values
	 */
	public Map<String, String> getDrugnameidmap() {
		return drugnameidmap;
	}

	/**
	 * Setter for Map with Identifiers and names of drugs 
	 * 
	 * @param drugnameidmap Map with names as keys and identifiers as values
	 */
	public void setDrugnameidmap(Map<String, String> drugnameidmap) {
		this.drugnameidmap = drugnameidmap;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

		DrugNameMapper dbm = new DrugNameMapper ();
		Map <String, String> m = dbm.readMappingFile("resources/drugbank/db-atc.map");
		System.out.println(m.keySet().size());
	}

	/**
	 * Reads mapping file into a Map with identifiers as keys and names as synonyms
	 * @param mappingfile tab-separated columns with information about drug names
	 * @return map ith identifiers as keys and names as synonyms
	 */
	public Map <String, String> readMappingFile (String mappingfile) {
		BufferedReader reader;
		Map <String, String> m = new HashMap <String, String> ();
		try {
			reader = new BufferedReader(new FileReader(mappingfile));
			String row = "";

			while ((row = reader.readLine()) != null) {
			    String[] data = row.split("\t");
			    m.put(data[0], data[3]);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return m;
	}
}
