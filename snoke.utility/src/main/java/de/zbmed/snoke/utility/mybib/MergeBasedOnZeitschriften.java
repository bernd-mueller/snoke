package de.zbmed.snoke.utility.mybib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.WordUtils;

public class MergeBasedOnZeitschriften {
	Zeitschrift z = new Zeitschrift ();


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MergeBasedOnZeitschriften moz = new MergeBasedOnZeitschriften ();
		
		String filename = "resources/mybib/MyBib_all_2021-05-31.csv";

		Map <String, Zeitschrift> mbo = moz.getZeitschriften(filename);
		
		System.out.println("Read " + mbo.size() + " Zeitschriften...");
		
		String fileordernum = "resources/mybib/zbmed-alle-zsen-mit-besteller-20200101-20210101.txt";
		
		Map <String, Zeitschrift> mbos = moz.getZeitschriftenCount (fileordernum, mbo);
		
		System.out.println("Read " + mbos.size() + " Zeitschriften...");
		
		moz.printZeitschriften(mbos);
		
	}
	
	public void printZeitschriften (Map <String, Zeitschrift> mbos) {
	    FileWriter fileWriter;
		try {
			fileWriter = new FileWriter("resources/All_orders_issn_2020.txt");
		    PrintWriter printWriter = new PrintWriter(fileWriter);
		    
		    printWriter.println("norm.Sign." + "\t"
		    		+ "Signatur" + "\t"
		    		+ "Verlag" + "\t"
		    		+ "ISSN" + "\t"
		    		+ "Titel" + "\t"
		    		+ "Autor" + "\t"
		    		+ "Artikel-Titel - ggfs mehrere" + "\t"
		    		+ "Artikel-Autor - ggfs mehrere" + "\t"
		    		+ "Seiten" + "\t"
		    		+ "Usergroup" + "\t"
		    		+ "PLZ" + "\t"
		    		+ "Count"
		    	);
		    
		    for (String k : mbos.keySet()) {
		    	Zeitschrift curz = mbos.get(k);
		    	
		    	printWriter.println (curz.getNormsig() + "\t" 
		    			+ curz.getSig() + "\t"
		    			+ curz.getVerlag() + "\t"
		    			+ curz.getIssn() + "\t" 
		    			+ curz.getTitel() + "\t"
		    			+ curz.getAutor() + "\t"
		    			+ curz.getArtitel() + "\t"
		    			+ curz.getArtautor() + "\t"
		    			+ curz.getSeiten() + "\t"
		    			+ curz.getUsergroup() + "\t"
		    			+ curz.getPlz() + "\t"
		    			+ curz.getCount()
		    		);
		    }
		    
		    printWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public Map <String, Zeitschrift> getZeitschriftenCount (String fileordernum, Map <String, Zeitschrift> mbo) {
		Map <String, Zeitschrift> allZ = new HashMap <String, Zeitschrift> (mbo);

		try {
			FileInputStream fis = new FileInputStream(fileordernum);
			InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.ISO_8859_1);
			BufferedReader br = new BufferedReader(isr);
	        String line;

	        while ((line = br.readLine()) != null) {
	            String [] lineSplit = line.split("\t");
	            
	            String title = lineSplit[4].replaceAll("\"", "");
	            title = WordUtils.capitalize(title.trim());
	            Integer count = Integer.parseInt(lineSplit[6]);
	            
	            if (allZ.containsKey(title)) {
		            Zeitschrift curz = allZ.get(title);
		            // System.out.println(title + "\t" + curz.getCount());
		            curz.setCount(curz.getCount() + count);
		            allZ.put(title, curz);
	            } else {
	            	Zeitschrift newz = new Zeitschrift ();
	            	newz.setTitel(title);
	            	newz.setCount(count);
	            	allZ.put(title, newz);
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
		return allZ;
	}
	
	public Map <String, Zeitschrift> getZeitschriften (String filename) {
		Map <String, Zeitschrift> titZeitschrift = new HashMap <String, Zeitschrift> ();

		try {
			FileInputStream fis = new FileInputStream(filename);
			InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.ISO_8859_1);
			BufferedReader br = new BufferedReader(isr);
	        String line;
	        int brokencounter = 0;
	        while ((line = br.readLine()) != null) {
	            String [] lineSplit = line.split("\\|");
	            if (lineSplit.length>20) {
	            	String curtitel = lineSplit[4].replaceAll("\"", "");
	            	curtitel = WordUtils.capitalize(curtitel.trim());
	            	String curartitel = lineSplit[5];
	            	String curdate = lineSplit [19];
	            	//System.out.println(curtitel + "\t" + curartitel + "\t" + curdate);
		            if (curdate.contains("2020")) {
		            	//System.out.println(curtitel);
		            	if (titZeitschrift.containsKey(curtitel)) {
		            		Zeitschrift curz = titZeitschrift.get(curtitel);
		            		curz.setCount(curz.getCount()+1);
		            		curz.setArtitel(curz.getArtitel() + ";" + curartitel);
		            		titZeitschrift.put(curtitel, curz);
		            	} else {
			            	Zeitschrift z = new Zeitschrift ();
			            	z.setTitel(curtitel);
			            	z.setCount(1);
			            	z.setArtitel(curartitel);
			            	z.setNormsig(lineSplit[0]);
			            	z.setSig(lineSplit[1]);
			            	z.setVerlag(lineSplit[2]);
			            	z.setAutor(lineSplit[6]);
			            	z.setArtautor(lineSplit[9]);
			            	z.setSeiten(lineSplit[10]);
			            	z.setPlz(lineSplit[12]);
			            	z.setUsergroup(lineSplit[11]);
			            	if (lineSplit.length>21) {
			            		z.setIssn(lineSplit [21]);
			            	}
			            	titZeitschrift.put(curtitel, z);
		            	}
		            }
	            } else {
	            	System.err.println("Broken line: " + line + " with length " + lineSplit.length);
	            	brokencounter++;
	            }
	        }
	        br.close();
	        System.out.println("Lines not being able to process: " + brokencounter);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return titZeitschrift;
	}

}
