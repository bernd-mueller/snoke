package de.zbmed.snoke.solr;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
/**
 * ProteinFamilies
 *
 * @author Alexandra Hagelstein
 * @version 0.1
 * @since 2016
 */
public class Alex_TestQuery {

	public static void main(String[] args) {
		Alex_TestQuery aq = new Alex_TestQuery();
		try {
			aq.execQuery(args);
		} catch (SolrServerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void execQuery(String[] args) throws SolrServerException, IOException {
		Set <String> ids = new HashSet <String> ();
		SolrClient client = new HttpSolrClient.Builder("http://134.95.56.158:8080/solr/default").build();
		SolrQuery query = new SolrQuery();

		int pagesize = 10000;
		// query.setQuery("ABSTRACT:epilepsy OR ABSTRACT:seizure");
		query.setQuery("Zika virus");
		query.setParam("wt", "json");
		query.setFilterQueries("");

		QueryResponse response = client.query(query);
		SolrDocumentList results = response.getResults();
		Long hitNumber = results.getNumFound();

		int end = Integer.parseInt("" + hitNumber);




			System.out.println("hello"+ hitNumber);

			
				for (int j = 0; j < end; ++j) {
					SolrDocument doc = results.get(j);
					if (doc.getFieldValue("ABSTRACT") != null) {
						System.out.println("y");
						System.out.println(doc+"\n");
						if (doc.getFieldValue("ABSTRACT").toString().length()>0) {
							System.out.println("y2");
							System.out.println(doc+"2\n");
							if (doc.getFieldValue("language") != null) {
								if (doc.getFieldValue("language").toString().contains("en")) {
									ids.add(doc.getFieldValue("DBRECORDID").toString());
								}

							}
						}
						
					} else {
						
					}

				}



		client.close();
	}

}