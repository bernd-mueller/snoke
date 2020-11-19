package de.zbmed.snoke.ontology.epso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * RxNormClient
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class RxNormClient {
	static HttpClient client;
	static HttpGet request;
	static HttpResponse response;
	static String rxhost = "https://rxnav.nlm.nih.gov/REST";
	static String rxURI = rxhost + "/rxcui?name=";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RxNormClient rx = new RxNormClient ();

		

		System.out.println(rx.getIdentifierForConceptName("aspirin"));
		System.out.println(rx.getDrugBankMapping("1191"));
		System.out.println(rx.getConceptnameForIdentifier("1191"));
	}

	public String getDrugBankMapping (String rxcui) {
		//https://rxnav.nlm.nih.gov/REST/rxcui/7052/property?propName=ATC

		client = HttpClientBuilder.create().build();
		request = new HttpGet("https://rxnav.nlm.nih.gov/REST/rxcui/" + rxcui + "/property?propName=DrugBank");
		
		String res = "";
		try {
			response = client.execute(request);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = "";

			while ((line = rd.readLine()) != null) {
				res+=line;
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		res = res.replaceAll("^.*<propValue>", "");
		res = res.replaceAll("</propValue>.*$", "");
		return res;
	}
	
	public String getIdentifierForConceptName (String conceptname) {
		String rxhost = "https://rxnav.nlm.nih.gov/REST";
		String rxURI = rxhost + "/rxcui?name=";
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(rxURI + conceptname);
		HttpResponse response;
		String res = "";
		try {
			response = client.execute(request);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = "";

			while ((line = rd.readLine()) != null) {
				res+=line;
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		res = res.replaceAll("^.*<rxnormId>", "");
		res = res.replaceAll("</rxnormId>.*$", "");
		return res;
	}
	public String getConceptnameForIdentifier (String rxcui) {
		//https://rxnav.nlm.nih.gov/REST/rxcui/161
		String rxhost = "https://rxnav.nlm.nih.gov/REST";
		String rxURI = rxhost + "/rxcui/";
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(rxURI + rxcui);
		HttpResponse response;
		String res = "";
		try {
			response = client.execute(request);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = "";

			while ((line = rd.readLine()) != null) {
				res+=line;
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		res = res.replaceAll("^.*<name>", "");
		res = res.replaceAll("</name>.*$", "");
		return res;
	}
}
