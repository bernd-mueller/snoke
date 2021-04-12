package de.zbmed.snoke.utility.livivo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.zbmed.snoke.utility.mybib.City;
import de.zbmed.snoke.utility.mybib.MergeSourceSets;

public class LivivoStats {
	private static final Logger log = LoggerFactory.getLogger(LivivoStats.class);
	public String clearNetworkAddressSuffix (String ipaddress) {
		String res = "";
		if (ipaddress.contains(".")) {
			String [] dotSplit = ipaddress.split("\\.");
			if (dotSplit.length>2) {
				res = dotSplit[0] + "." + dotSplit[1] + "." + dotSplit[2];
			}
		}
		// log.info("Replaced " + ipaddress + " with " + res);
		return res;
	}
	
	public Map <String, String> getMapKeyValueNetworkAddress (String mapfile) {
		Map <String, String> mc = new HashMap <String, String> ();
		try {
			InputStream is = new FileInputStream (new File (mapfile));
	        BufferedReader br = new BufferedReader(new InputStreamReader(is));
	 
	        String line;
	        while ((line = br.readLine()) != null) {
	            String [] lineSplit = line.split("\t");
	            if (lineSplit.length>1) {
	            	String key = clearNetworkAddressSuffix(lineSplit[0]);
	            	String value = lineSplit [1];
	            	mc.put(key, value);
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
	
	public Map <String, String> getMapValueKeyNetworkAddress (String mapfile) {
		Map <String, String> mc = new HashMap <String, String> ();
		try {
			InputStream is = new FileInputStream (new File (mapfile));
	        BufferedReader br = new BufferedReader(new InputStreamReader(is));
	 
	        String line;
	        while ((line = br.readLine()) != null) {
	            String [] lineSplit = line.split("\t");
	            if (lineSplit.length>1) {
	            	String key = lineSplit[1];
	            	String value = lineSplit [0];
	            	mc.put(key, value);
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
	
	public Map <String, String> getMapCityLocation (String mapfile) {
		Map <String, String> mc = new HashMap <String, String> ();
		try {
			InputStream is = new FileInputStream (new File (mapfile));
	        BufferedReader br = new BufferedReader(new InputStreamReader(is));
	 
	        String line;
	        while ((line = br.readLine()) != null) {
	            String [] lineSplit = line.split("\t");
	            if (lineSplit.length>10) {
	            	String key = lineSplit[0];
	            	String state = lineSplit [7];
	            	String country = lineSplit [5];
	            	if (country.equals("Deutschland")) {
	            		mc.put(key, state);
	            	}
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
	
	public Map <String, String> getMapCountryCodeLocation (String mapfile) {
		Map <String, String> mc = new HashMap <String, String> ();
		try {
			InputStream is = new FileInputStream (new File (mapfile));
	        BufferedReader br = new BufferedReader(new InputStreamReader(is));
	 
	        String line;
	        while ((line = br.readLine()) != null) {
	            String [] lineSplit = line.split("\t");
	            if (lineSplit.length>10) {
	            	String key = lineSplit[0];
	            	String ccode = lineSplit [4];
	            	mc.put(key, ccode);
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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String pipcounts 	= "D:\\PostgresExports\\ipcounts.csv";
		String pgeomap 		= "D:\\PostgresExports\\city_blocks_ip4.csv";
		String pcity 		= "D:\\PostgresExports\\city_locations_de.csv";
		
		LivivoStats ls = new LivivoStats ();
		
		Map <String, String> mipcounts = ls.getMapValueKeyNetworkAddress(pipcounts);
		
		Map <String, String> mgeomap = ls.getMapKeyValueNetworkAddress(pgeomap);
		
		Map <String, String> mcountry = ls.getMapCountryCodeLocation(pcity);
		
		Map <String, Integer> countrycodecount = new HashMap <String, Integer> ();
		
		
		for (String k : mipcounts.keySet()) {
			String geocode = mgeomap.get(ls.clearNetworkAddressSuffix(k));
			if (mcountry.containsKey(geocode)) {
				String ccode = mcountry.get(geocode);
				log.info(k + "\t" + geocode + "\t" + ccode);
				if (countrycodecount.containsKey(ccode)) {
					Integer curcount = countrycodecount.get(ccode);
					countrycodecount.put(ccode, curcount+1);
				} else {
					countrycodecount.put(ccode, 1);
				}
			}
		}
		

		
		PrintWriter writer;
		try {
			writer = new PrintWriter("D:\\PostgresExports\\livcountrycodecount.csv", "UTF-8");
			for (String ccode : countrycodecount.keySet()) {
				Integer count = countrycodecount.get(ccode);
				log.info(ccode + "\t" + count);
				writer.println(ccode + "\t" + count);
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
	public void processFederalStates () {
		String pipcounts 	= "D:\\PostgresExports\\ipcounts.csv";
		String pgeomap 		= "D:\\PostgresExports\\city_blocks_ip4.csv";
		String pcity 		= "D:\\PostgresExports\\city_locations_de.csv";
		
		LivivoStats ls = new LivivoStats ();
		
		Map <String, String> mipcounts = ls.getMapValueKeyNetworkAddress(pipcounts);
		
		Map <String, String> mgeomap = ls.getMapKeyValueNetworkAddress(pgeomap);
		
		Map <String, String> mcity = ls.getMapCityLocation(pcity);
		
		Map <String, Integer> fedstatecount = new HashMap <String, Integer> ();
		
		fedstatecount.put("Baden-Württemberg", 0);
		fedstatecount.put("Bayern", 0);
		fedstatecount.put("Berlin", 0);
		fedstatecount.put("Brandenburg", 0);
		fedstatecount.put("Bremen", 0);
		fedstatecount.put("Hamburg", 0);
		fedstatecount.put("Hessen", 0);
		fedstatecount.put("Mecklenburg-Vorpommern", 0);
		fedstatecount.put("Niedersachsen", 0);
		fedstatecount.put("Nordrhein-Westfalen", 0);
		fedstatecount.put("Rheinland-Pfalz", 0);
		fedstatecount.put("Saarland", 0);
		fedstatecount.put("Sachsen", 0);
		fedstatecount.put("Sachsen-Anhalt", 0);
		fedstatecount.put("Schleswig-Holstein", 0);
		fedstatecount.put("Thüringen", 0);
		
		
		for (String k : mipcounts.keySet()) {
			String geocode = mgeomap.get(ls.clearNetworkAddressSuffix(k));
			if (mcity.containsKey(geocode)) {
				String state = mcity.get(geocode);
				log.info(k + "\t" + geocode + "\t" + state);
				if (fedstatecount.containsKey(state)) {
					Integer curcount = fedstatecount.get(state);
					fedstatecount.put(state, curcount+1);
				}
			}
		}
		

		
		PrintWriter writer;
		try {
			writer = new PrintWriter("D:\\PostgresExports\\livblaendercount.csv", "UTF-8");
			for (String state : fedstatecount.keySet()) {
				Integer count = fedstatecount.get(state);
				log.info(state + "\t" + count);
				writer.println(state + "\t" + count);
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

}
