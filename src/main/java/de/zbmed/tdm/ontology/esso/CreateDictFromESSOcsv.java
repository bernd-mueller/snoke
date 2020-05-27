package de.zbmed.tdm.ontology.esso;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
/**
 * CreateDictFromESSOcsv
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class CreateDictFromESSOcsv {
	
	public static void main (String [] args) {
		String essofile = "C:\\Users\\muellerb\\Desktop\\Epilepsy2017\\ESSO\\ESSO.csv";
	    BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(essofile));
		    String line =  null;
		    HashMap<String,String> map = new HashMap<String, String>();

		    while((line=br.readLine())!=null){
		        String arr[] = line.split(",");
		        if (arr.length>2) {
		         	map.put(arr[0], arr[2]);
		           	System.out.println(arr[0] + "\t" + arr[2]);
		        }
		    }
		 
		 } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

