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


public class ICDAPIclient {

	Set <String> rootents = new HashSet <String> ();
	private final String TOKEN_ENPOINT = "https://icdaccessmanagement.who.int/connect/token";
	private static String CLIENT_ID = "";
	private static String CLIENT_SECRET = "";
	private final String SCOPE = "icdapi_access";
	private final String GRANT_TYPE = "client_credentials";


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
	public static void main(String[] args) throws Exception {

		
		String uri = "https://id.who.int/icd/entity";

		ICDAPIclient api = new ICDAPIclient();
		api.readCredentials();
		String token = api.getToken();
		System.out.println("URI Response JSON : \n" + api.getRootChildren(token, uri));
	}


	// get the OAUTH2 token
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
	private String getURI(String token, String uri) throws Exception {

		System.out.println("Getting URI...");

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
	private String getRootChildren(String token, String uri) throws Exception {

		System.out.println("Getting URI...");

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

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		
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
		return response.toString();
	}
	
}