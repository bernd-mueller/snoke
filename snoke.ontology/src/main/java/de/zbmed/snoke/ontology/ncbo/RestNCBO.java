package de.zbmed.snoke.ontology.ncbo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.nd4j.shade.jackson.core.JsonProcessingException;
import org.nd4j.shade.jackson.databind.JsonNode;
import org.nd4j.shade.jackson.databind.ObjectMapper;

public class RestNCBO {
	Set <String> onts = new HashSet <String> ();
    static final String REST_URL = "http://data.bioontology.org";
    static String API_KEY = "";
    static final ObjectMapper mapper = new ObjectMapper();

    public void readCredentials () {
    	try {
			BufferedReader reader = new BufferedReader(new FileReader ("resources/credentials/ncboapikey.txt"));
			API_KEY = reader.readLine();
		    reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    public static void main(String[] args) {
    	
    	RestNCBO rn = new RestNCBO ();
    	rn.readCredentials();
    	
        // Get the available resources
        String resourcesString = rn.get(REST_URL + "/");
        JsonNode resources = rn.jsonToNode(resourcesString);

        // Follow the ontologies link by looking for the media type in the list of links
        String link = resources.get("links").findValue("ontologies").asText();

        // Get the ontologies from the link we found
        JsonNode ontologies = rn.jsonToNode(rn.get(link));

        // Get the name and ontology id from the returned list
        List<String> ontNames = new ArrayList<String>();
        for (JsonNode ontology : ontologies) {
        	//String s = ontology.get("name").asText() + "\n" + ontology.get("@id").asText() + "\n\n";
        	//System.out.println("#\t" + ontology.get("name").asText());
        	//System.out.println("#\t" + ontology.get("@id").asText());
            rn.onts.add(ontology.get("@id").asText());
        }
        
        Writer fw = null;


        try {
			fw = new FileWriter("resources/ontstats.txt" );


	        // Print the names and ids
	        for (String ont : rn.onts) {
	        	System.out.println("#" + ont);
	        	JsonNode j = rn.jsonToNode(rn.get(ont + "/submissions"));
	        	int created = 9999;
	        	Set <String> releaseDates = new HashSet<String>();
	        	if (j.isArray()) {
	        		for (JsonNode jn : j) {
	        			String s = jn.get("released").asText().substring(0,4);
	        			System.out.println("#1\t" + s);
	        			if (s != null && !s.equals("null")) {
		        			int current = Integer.parseInt(s);
		        			if (current < created) {
		        				created = current;
		        			}
	        			}
	        		}
	        	} else {
	        		System.out.println("#2\t" + j.get("released"));
	        	}
	        	String ontstring = ont.substring(39);
	        	fw.write(ontstring + "\t" + created + "\n");
	        }
	        fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

    private JsonNode jsonToNode(String json) {
        JsonNode root = null;
        try {
            root = mapper.readTree(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return root;
    }

    private String get(String urlToGet) {
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        String result = "";
        try {
            url = new URL(urlToGet);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "apikey token=" + API_KEY);
            conn.setRequestProperty("Accept", "application/json");
            rd = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result += line;
            }
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}