package de.zbmed.snoke.utility.mongodb;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class ImportByPMIDtoMongoDB {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		String pmids = "";
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(
					"resources/PubMedIDs.csv"));
			String line = reader.readLine();
			while (line != null) {
				System.out.println(line);
				// read next line
				line = reader.readLine();
				pmids += line + ",";
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    String REST_URI 
	      = "https://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=pubmed&id=" + pmids + "&retmode=xml";
	 
	    Client client = ClientBuilder.newClient();
	    
	    WebTarget target = client.target(REST_URI);

        String response = target.path("rest").
                            path("hello").
                            request().
                            accept(MediaType.TEXT_XML).
                            get(String.class);
	 
        // System.out.println(response);
	    
	    DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		
		MongoClient mongoClient;
		MongoDatabase db;
		MongoCollection collection = null;
		System.out.println("Starting...");
		try {
			mongoClient = new MongoClient("localhost", 27017);
			db = mongoClient.getDatabase("kroll");
			
			if (db != null) {
				if (db.getCollection("hermann") != null) {
					collection = db.getCollection("hermann");
					System.out.println("Getting collection " + "hermann");
				} else {
					db.createCollection("hermann");
					System.out.println("Creating collection " + "hermann");
				}
			} else {
				System.err.println("" + "hermann" + " NULL");
			}
			
			builder = builderFactory.newDocumentBuilder();

			System.out.println("Parsing...");
			Document document = builder.parse(new InputSource( new StringReader( response ) ));
			// new FileInputStream("/home/muellerb/test.xml"));
			System.out.println("Done...");
			XPath xPath = XPathFactory.newInstance().newXPath();

			String expression = "/PubmedArticleSet//PubmedArticle";


			// read a string value
			// String email = xPath.compile(expression).evaluate(document);
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);
			String pmid = "";
			String title = "";
			String abstractText = "";
			String year = "";
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node n = nodeList.item(i);
				NodeList nol = n.getChildNodes();

				for (int j=0; j<nol.getLength(); j++ ) {
					Node nj = nol.item(j);
					if (nj.hasChildNodes()) {
						for (int k=0; k<nj.getChildNodes().getLength(); k++) {
							Node nk = nj.getChildNodes().item(k);
							if (nk.getNodeName().equals("PMID")) {
								pmid = nk.getTextContent();
							} 
							if (nk.hasChildNodes()) {
								for (int l=0; l<nk.getChildNodes().getLength(); l++) {
									Node nl = nk.getChildNodes().item(l);
									if (nl.getNodeName().equals("Year")) {
										year = nl.getTextContent();
									} else if (nl.getNodeName().equals("ArticleTitle")) {
										title = nl.getTextContent();
									} else if (nl.getNodeName().equals("Abstract")) {
										abstractText = nl.getTextContent();
									}
								}
							}
							
						}
					}
				}
				
				System.out.println("PMID: " + pmid + "\tTitle: " + title + "\tAbstract: "+ abstractText);
				org.bson.Document d = new org.bson.Document ();
				d.append("pmid", pmid);
				d.append("year", year);
				d.append("title", title);
				d.append("abstractText", abstractText);
				
				collection.insertOne (d);
			}


			mongoClient.close();
			
			System.out.println("Muhaha!");

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    
	}

}
