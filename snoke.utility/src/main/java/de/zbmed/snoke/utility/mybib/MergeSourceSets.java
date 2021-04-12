package de.zbmed.snoke.utility.mybib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MergeSourceSets {
	private static final Logger log = LoggerFactory.getLogger(MergeSourceSets.class);
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MergeSourceSets mss = new MergeSourceSets ();
		
		String filename = "resources/mybib/MyBib_orders_and_archived_orders.txt";

		List <MyBibOrder> mbo = mss.getOrdersFromFile(filename);
		
		String mapfile = "resources/mybib/Liste-Staedte-in-Deutschland.csv";
		Map <String, City> mc = mss.getMap(mapfile);
		
		
	}
	
	public Map <String, City> getMap (String mapfile) {
		Map <String, City> mc = new HashMap <String, City> ();
		try {
			InputStream is = new FileInputStream (new File (mapfile));
	        BufferedReader br = new BufferedReader(new InputStreamReader(is));
	 
	        String line;
	        // Schlüsselnummer;Stadt;PLZ;Bundesland;Landkreis
	        while ((line = br.readLine()) != null) {
	            String [] lineSplit = line.split(";");
	            if (lineSplit.length>4) {
	            	City c = new City ();
	            	c.setKey(lineSplit[0]);
	            	c.setStadt(lineSplit[1]);
	            	c.setPlz(lineSplit[2]);
	            	c.setBland(lineSplit[3]);
	            	c.setKreis(lineSplit[4]);
	            	mc.put(c.getPlz(), c);
	            }
	        }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mc;
	}

	public List <MyBibOrder> getOrdersFromFile (String filename) {
        List <MyBibOrder> mbib = new ArrayList <MyBibOrder> ();
		try {
			InputStream is = new FileInputStream (new File (filename));
	        BufferedReader br = new BufferedReader(new InputStreamReader(is));
	 
	        String line;

	        while ((line = br.readLine()) != null) {
	            String [] lineSplit = line.split("\t");
	            if (lineSplit.length>17) {
	            	MyBibOrder mb = new MyBibOrder ();
	            	mb.setPlz(lineSplit[13]);
	            	mb.setCcode(lineSplit[15]);
	            	mb.setCity(lineSplit[14]);
	            	mb.setDate(lineSplit[16]);
	            	mbib.add(mb);
	            }
	        }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mbib;
	}
}
