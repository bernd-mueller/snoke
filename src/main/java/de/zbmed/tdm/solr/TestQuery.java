package de.zbmed.tdm.solr;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
/**
 * TestQuery
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class TestQuery {

	public static void main (String [] args) {
		TestQuery tq = new TestQuery ();
		try {
			tq.execQuery(args);
		} catch (SolrServerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void execQuery(String[] args) throws SolrServerException, IOException {
		SolrClient client = new HttpSolrClient.Builder("http://134.95.56.158:8080/solr/default").build();
		SolrQuery query = new SolrQuery();

		




		
		int pagesize = 1000;
		// query.setQuery("ABSTRACT:epilepsy OR ABSTRACT:seizure");

		query.setQuery("Alzheimer");


		query.setParam("wt", "json");
		query.setFilterQueries("");
		
		QueryResponse response = client.query(query);	
		SolrDocumentList results = response.getResults();
		Long hitNumber = results.getNumFound();
		
		int end = Integer.parseInt(""+hitNumber);
		


			FileWriter fw = new FileWriter("result.txt");
			BufferedWriter writer = new BufferedWriter(fw);
			System.out.print("Total numbers of found documents:\t" + end + "\n");

			
		

				response = client.query(query);	
				results = response.getResults();
				
				
				for (int j = 0; j < results.size(); ++j) {
					
					SolrDocument doc = results.get(j);
					
					//System.out.println(doc.getFieldNames());
					//System.out.println(doc.getFieldValue("MESH"));
					if (doc.getFieldValue("ABSTRACT") != null) {
						System.out.println("asd"+doc.getFieldValue("ABSTRACT").toString());
						System.out.println(doc.getFieldValue("id").toString() + 
								"\t" + doc.getFieldValue("TITLE").toString() + 
								"\t" + doc.getFieldValue("ABSTRACT").toString());
						if (doc.getFieldValue("language") != null) {
							if (doc.getFieldValue("language").toString().contains("en")) {
								System.out.println("#");
							}
							

							
						}
						

					} else {

					}
					
				}

				client.close();
			
			


		String [] fieldNames = {
			"id",
			"TITLE",
			"ABSTRACT",
			"PUBLDATE",
			"ARTICLELANGUAGE",
			"DATABASE"
		};
		

		System.out.println("Hitnumber: " + hitNumber);
		
		// System.exit(0);
		query.setRows(100000);
		
		response = client.query(query);
		
		results = response.getResults();
		
		int qt = response.getQTime();
		
		System.out.println("Result: " + results.size() + " hits in " + qt + "ms");

		int num = 0;
		for (int i = 0; i < results.size(); ++i) {
			
			SolrDocument doc = results.get(i);
			
			if (doc.getFieldValue("ARTICLELANGUAGE") == "eng") {
				System.out.println(doc);
			}
				
				
			//System.out.println(doc.getFieldValue("ARTICLELANGUAGE"));
			
			if (doc.getFieldValue("ABSTRACT")  != null ) {
				//System.out.println(i + " ###############################");
				//System.out.println(doc.getFieldNames());
				//System.out.println(doc.getFieldValue("MESH"));
				//System.out.println(doc.getFieldValue("id").toString() + 
				//		"\t" + doc.getFieldValue("TITLE").toString() + 
				//		"\t" + doc.getFieldValue("ABSTRACT").toString());
				
				num++;
			}
			
		}
		System.out.println(num);

		client.close();
	}
}
