package de.zbmed.snoke.ontology.icd;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;


/**
 * Implementation of REST client for ICD API
 * 
 * @author Muellerb
 * @since 2020
 */
public class ICDAPIclient {

	Set <ICDentity> allents = new HashSet <ICDentity> ();
	Set <String> processed = new HashSet <String> ();
	Set <String> rootents = new HashSet <String> ();
	int counter = 0;

	private final String TOKEN_ENPOINT = "https://icdaccessmanagement.who.int/connect/token";
	private static String CLIENT_ID = "";
	private static String CLIENT_SECRET = "";
	private final String SCOPE = "icdapi_access";
	private final String GRANT_TYPE = "client_credentials";
	ICDAPIclient () {
		readCredentials ();
	}

	/**
	 * Reads ICD API credentials for getting a valid token
	 */
	public void readCredentials () {
		try (BufferedReader br = new BufferedReader(new FileReader("resources/credentials/creds.txt"))) {
			String line;
			String lines = "";
			while ((line = br.readLine()) != null) {
				lines += line + "\n";
			}
			CLIENT_ID = lines.split("\n")[0];
			CLIENT_SECRET = lines.split("\n")[1];
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Read the top level entities in the ICD tree from file
	 */
	public void readRootEntities () {
		try (BufferedReader br = new BufferedReader(new FileReader("resources/icd/rootentities.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				rootents.add(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws Exception {
		
		ICDAPIclient api = new ICDAPIclient();

		String token = api.getToken();
		api.readRootEntities();

		int num = 0;
		for (String rootids : api.getRootents()) {
			System.out.println("Processing root id " + rootids + " " + ++num + "/" + api.getRootents().size());
			int res = api.getEntityInformation(token, rootids);
		}
		api.writeToDict();
	}

	/**
	 * Writes the extracted Set <ICDentity> as dictionary file
	 */
	public void writeToDict () {
		CreateDictFromICD dh = new CreateDictFromICD ();
		dh.createConceptMapperDictionary(this.getAllents(), "dictionaries/Dict_ICD.xml", "ICD");
	}

	/**
	 * Calls the ICD API for retrieving information for a given entity id
	 * @param token the valid token for accessing the IP
	 * @param id the identifier of the entity
	 * @return the return code from the REST call
	 * @throws Exception
	 */
	private int getEntityInformation(String token, String id) throws Exception {
		if (!processed.contains(id)) {
			if (++counter%100==0) {
				System.out.println("Processing " + counter + "...");
			}
			ICDentity ie = new ICDentity();
			// ie.setId(rootents.toArray()[0].toString());
			// "250688797"
			ie.setId(id);
			//System.out.println("Rootents: " + rootents.size());
			String uri = "https://id.who.int/icd/entity/" + ie.getId();
			//System.out.println("Getting URI...");
	
			URL url = new URL(uri);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
	
			// HTTP header fields to set
			con.setRequestProperty("Authorization", "Bearer " + token);
			con.setRequestProperty("Accept", "application/json");
			con.setRequestProperty("Accept-Language", "en");
			con.setRequestProperty("API-Version", "v2");
	
			// response
			
			int responseCode = con.getResponseCode();
			// System.out.println("URI Response Code : " + responseCode + "\n");
	
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	
			while ((inputLine = in.readLine()) != null) {
	
				ie.processJSON(inputLine);
				response.append(inputLine);
			}
			in.close();
	
			//System.out.println("###" + ie.toString());
			this.getAllents().add(ie);
	
			for (String identifier : ie.getChildren()) {
				this.getEntityInformation(token, identifier);
			}
			processed.add(id);
			return responseCode;
		} else {
			System.out.println("Duplicate id: " + id);
			return -1;
		}
	}

	// get the OAUTH2 token
	/**
	 * Retrieve a valid API token for accessing the ICD API
	 * @return
	 * @throws Exception
	 */
	private String getToken() throws Exception {

		System.out.println("Getting token...");

		URL url = new URL(TOKEN_ENPOINT);
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		con.setRequestMethod("POST");

		// set parameters to post
		String urlParameters =
        		"client_id=" + URLEncoder.encode(CLIENT_ID, "UTF-8") +
        		"&client_secret=" + URLEncoder.encode(CLIENT_SECRET, "UTF-8") +
			"&scope=" + URLEncoder.encode(SCOPE, "UTF-8") +
			"&grant_type=" + URLEncoder.encode(GRANT_TYPE, "UTF-8");
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		// response
		int responseCode = con.getResponseCode();
		System.out.println("Token Response Code : " + responseCode + "\n");

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// parse JSON response
		JSONObject jsonObj = new JSONObject(response.toString());
		return jsonObj.getString("access_token");
	}


	// access ICD API
	private String getURI(String token) throws Exception {
		String uri = "https://id.who.int/icd/entity";
		// System.out.println("Getting URI...");

		URL url = new URL(uri);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");

		// HTTP header fields to set
		con.setRequestProperty("Authorization", "Bearer "+token);
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("Accept-Language", "en");
		con.setRequestProperty("API-Version", "v2");

		// response
		int responseCode = con.getResponseCode();
		// System.out.println("URI Response Code : " + responseCode + "\n");

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return response.toString();
	}

	// access ICD API
	private String getRootChildren(String token) throws Exception {
		String uri = "https://id.who.int/icd/entity";
		

		URL url = new URL(uri);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");

		// HTTP header fields to set
		con.setRequestProperty("Authorization", "Bearer "+token);
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("Accept-Language", "en");
		con.setRequestProperty("API-Version", "v2");

		// response
		int responseCode = con.getResponseCode();
		System.out.println("URI Response Code : " + responseCode + "\n");
		StringBuffer response = new StringBuffer();
		if (responseCode == 200) {
	
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;

	
			
			while ((inputLine = in.readLine()) != null) {
				System.out.println("####\t" + inputLine);
				JSONTokener tokener = new JSONTokener(inputLine);
				JSONObject root = new JSONObject(tokener);
				System.out.println("child: " + root.has("child") + "\t" + root.get("child").getClass());
				JSONArray children = (JSONArray)root.get("child");
				
				for (int i=0; i<children.length(); i++) {
					rootents.add(children.getString(i).replace("http://id.who.int/icd/entity/", ""));
				}
				System.out.println("####\t" + children);
				response.append(inputLine);
			}
			in.close();
	
			PrintWriter writer = new PrintWriter("resources/icd/rootentities.txt", "UTF-8");
			for (String ents : rootents) {
				writer.println(ents);
			}
			writer.close();
			}
		return response.toString();
	}
	public Set<ICDentity> getAllents() {
		return allents;
	}

	public void setAllents(Set<ICDentity> allents) {
		this.allents = allents;
	}
	public Set<String> getRootents() {
		return rootents;
	}

	public void setRootents(Set<String> rootents) {
		this.rootents = rootents;
	}
}