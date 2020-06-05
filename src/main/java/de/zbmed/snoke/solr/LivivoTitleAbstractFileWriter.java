package de.zbmed.snoke.solr;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
 * LivivoTitleAbstractFileWriter
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class LivivoTitleAbstractFileWriter {

	public static void main(String[] args) {
		LivivoTitleAbstractFileWriter tq = new LivivoTitleAbstractFileWriter();
		try {
			tq.execQuery(args);
		} catch (SolrServerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void execQuery(String[] args) throws SolrServerException, IOException {
		Set <String> ids = new HashSet <String> ();
		SolrClient client = new HttpSolrClient.Builder("http://134.95.56.158:8080/solr/default").build();
		SolrQuery query = new SolrQuery();

		int pagesize = 1000;
		// query.setQuery("ABSTRACT:epilepsy OR ABSTRACT:seizure");
		query.setQuery("*");
		query.setParam("wt", "json");
		query.setFilterQueries("");

		QueryResponse response = client.query(query);
		SolrDocumentList results = response.getResults();
		Long hitNumber = results.getNumFound();

		int end = Integer.parseInt("" + hitNumber);

		try {

			FileWriter fw = new FileWriter("result.txt");
			BufferedWriter writer = new BufferedWriter(fw);
			writer.write("Total numbers of found documents:\t" + end + "\n");
			writer.write("Pagesize:\t\t\t\t" + pagesize + "\n");
			writer.write("Pages:\t\t\t\t\t" + end / pagesize + "\n");

			int totalNumOfAbstracts = 0;
			int totalNumOfNoAbstracts = 0;
			int totalNumOfEnAbstracts = 0;
			for (int i = 0; i < end / pagesize; i++) {
				query.setStart(i*pagesize);
				query.setRows(pagesize);
				response = client.query(query);
				results = response.getResults();

				int absnum = 0;
				int noabsnum = 0;
				int enabs = 0;
				for (int j = 0; j < results.size(); ++j) {
					SolrDocument doc = results.get(j);
					if (doc.getFieldValue("ABSTRACT") != null) {
						if (doc.getFieldValue("ABSTRACT").toString().length()>0) {
							if (doc.getFieldValue("language") != null) {
								if (doc.getFieldValue("language").toString().contains("en")) {
									enabs++;
									ids.add(doc.getFieldValue("DBRECORDID").toString());
								}

							}
						}
						absnum++;
					} else {
						noabsnum++;
					}

				}
				totalNumOfAbstracts += absnum;
				totalNumOfNoAbstracts += noabsnum;
				totalNumOfEnAbstracts += enabs;
				writer.write("Page " + (i + 1) + "/" + (end / pagesize) + "\t\t" + absnum + "/" + pagesize
						+ " Abstracts" + "\t" + enabs + "/" + absnum + " English Abstract" + "\tID-Count:" + ids.size() + "\n");
				System.out.println("Page " + (i + 1) + "/" + (end / pagesize) + "\t\t" + absnum + "/" + pagesize
						+ " Abstracts" + "\t" + enabs + "/" + absnum + " English Abstract" + "\tID-Count:" + ids.size() + "\n");
				writer.flush();
			}

			writer.write("Total number of abstracts:\t\t" + totalNumOfAbstracts + "\n");
			writer.write("Total number of english abstracts:\t\t" + totalNumOfEnAbstracts + "\n");

			writer.close();

		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		client.close();
	}

}
