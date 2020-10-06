package de.zbmed.snoke.ontology.ctd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
/**
 * CTDConverter
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class CTDConverter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CTDConverter c = new CTDConverter ();
		Map <String, String> dis = c.readFileIntoMap("/media/muellerb/Daten/EpilepsyData/CTD Comparative Taxicogenomics Database/diseases.txt");
		Map <String, String> dru = c.readFileIntoMap("/media/muellerb/Daten/EpilepsyData/CTD Comparative Taxicogenomics Database/chem.txt");
		
		try {
			PrintWriter writer = new PrintWriter("/home/muellerb/Desktop/drugdiseases.map", "UTF-8");
			
			for (String k : dis.keySet()) {
				if (dru.containsKey(k)) {
					String disease = dis.get(k);
					String drug = dru.get(k);
					writer.println(drug + "\t" + disease);
					
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
	
	public Map <String, String> readFileIntoMap (String filename) {
		Map <String, String> ret = new HashMap <String, String> ();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(new File(filename)));
			String line;
			int c=0;
			while ((line = br.readLine()) != null) {
				// process the line. "('[^']*?')"
				String [] s = line.split(";");
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
