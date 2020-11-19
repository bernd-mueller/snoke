package de.zbmed.snoke.ontology.common;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import de.zbmed.snoke.ontology.analysis.DictLoader;

public class DrugNameMapper {
	DictLoader drugnameloader;
	public Map<String, Set<String>> getDrugmap() {
		return drugmap;
	}


	public void setDrugmap(Map<String, Set<String>> drugmap) {
		this.drugmap = drugmap;
	}


	public Map<String, String> getDrugsynmap() {
		return drugsynmap;
	}


	public void setDrugsynmap(Map<String, String> drugsynmap) {
		this.drugsynmap = drugsynmap;
	}
	Map <String, String> drugnameidmap;
	Map <String, Set <String>> drugmap;
	Map <String, String> drugsynmap;
	
	public DrugNameMapper () {
		drugnameloader = new DictLoader();
		drugmap = drugnameloader.getMapForDict("dictionaries/Dict_DrugNames.xml");
		drugsynmap = drugnameloader.getValueMapForDict("dictionaries/Dict_DrugNames.xml");
		drugnameidmap = readMappingFile("resources/drugbank/db-atc.map");
	}
	
	
	public Map<String, String> getDrugnameidmap() {
		return drugnameidmap;
	}


	public void setDrugnameidmap(Map<String, String> drugnameidmap) {
		this.drugnameidmap = drugnameidmap;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

		DrugNameMapper dbm = new DrugNameMapper ();
		Map <String, String> m = dbm.readMappingFile("resources/drugbank/db-atc.map");
		System.out.println(m.keySet().size());
	}

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
