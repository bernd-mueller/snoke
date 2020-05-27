package de.zbmed.tdm.lod.drugbank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * DrugBankMappingDiseaseFilter
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class DrugBankMappingDiseaseFilter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String drugmapFile = "/home/muellerb/Desktop/N03-172_Drug-Disease-Gene-Network/Complete/N03-172_DrugDisease.map";
		String diseaseFile = "/home/muellerb/Desktop/N03-172_Drug-Disease-Gene-Network/Complete/N03-172_DiseaseNames.txt";
		System.err.println("Starting...");
		//System.err.println("Opening drugbank map from " + args[0]+"...");
		Map <String, String> drugmap = new DrugBankMappingDiseaseFilter().readFileIntoMap (drugmapFile);
		Set <String> diseaseNames = new DrugBankMappingDiseaseFilter().readFileIntoSet (diseaseFile);
		
		try {
			PrintWriter writer = new PrintWriter("/home/muellerb/Desktop/N03-172_Drug-Disease-Gene-Network/Complete/RESULT_N03-172_DrugDisease.map", "UTF-8");
			
			for (String drug : drugmap.keySet()) {
				String diseaseName = drugmap.get(drug);
				boolean hasDisease = false;
				for (String diseases : diseaseNames) {
					if (diseaseName.equalsIgnoreCase(diseases)) {
						hasDisease = true;
					}
				}
				if (true) {
					System.out.println(drug + " " + diseaseName);
					writer.println(drug + "\t" + diseaseName);
				}
			}
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	public Set <String> readFileIntoSet (String filename) {
		Set <String> ret = new HashSet <String> ();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(new File(filename)));
			String line;
			int c=0;
			while ((line = br.readLine()) != null) {
				// process the line. "('[^']*?')"
				ret.add(line);
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ret;
	}
	
	public Map <String, String> readFileIntoMap (String filename) {
		Map <String, String> ret = new HashMap <String, String> ();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(new File(filename)));
			String line;
			int c=0;
			while ((line = br.readLine()) != null) {
				// process the line. "('[^']*?')"
				String [] s = line.split("\t");
				if (s.length>1) {
					ret.put(s[0], s[1]);
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ret;
	}
}
