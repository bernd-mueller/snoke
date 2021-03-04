package de.zbmed.snoke.ontology.analysis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.Set;


public class PrintDictionariesAsCSV {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DictLoader dl = new DictLoader ();
		Map <String, Set <String>> mepso = dl.getMapForDictWithPrefix("dictionaries/Dict_EpSO.xml", "EpSO");
		Map <String, Set <String>> messo = dl.getMapForDictWithPrefix("dictionaries/Dict_ESSO.xml", "ESSO");
		Map <String, Set <String>> mepilont = dl.getMapForDictWithPrefix("dictionaries/Dict_EPILONT.xml", "EPILONT");
		Map <String, Set <String>> mepisem = dl.getMapForDictWithPrefix("dictionaries/Dict_EPISEM.xml", "EPISEM");
		Map <String, Set <String>> mfenics = dl.getMapForDictWithPrefix("dictionaries/Dict_FENICS.xml", "FENICS");
		
		printToFile ("resources/dictcsv/epso.csv", mepso);
		printToFile ("resources/dictcsv/esso.csv", messo);
		printToFile ("resources/dictcsv/epilont.csv", mepilont);
		printToFile ("resources/dictcsv/episem.csv", mepisem);
		printToFile ("resources/dictcsv/fenics.csv", mfenics);
	}

	public static void printToFile (String filename, Map <String, Set <String>> m) {
		FileWriter fw;
		try {
			fw = new FileWriter (filename);
			BufferedWriter bwr = new BufferedWriter(fw); 
			for (String k : m.keySet()) {
				String p = k;
				for (String s : m.get(k)) {
					p += ";" + s;
				}
				bwr.write(p + "\n");
			} 
			bwr.close(); 	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
