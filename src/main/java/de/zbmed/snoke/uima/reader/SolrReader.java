package de.zbmed.snoke.uima.reader;

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
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader_ImplBase;
import org.apache.uima.examples.SourceDocumentInformation;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.DocumentAnnotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;

/**
 * A simple collection reader that reads documents from a directory in the
 * filesystem. It can be configured with the following parameters:
 * <ul>
 * <li><code>InputDirectory</code> - path to directory containing files</li>
 * <li><code>Encoding</code> (optional) - character encoding of the input files
 * </li>
 * <li><code>Language</code> (optional) - language of the input documents</li>
 * </ul>
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class SolrReader extends CollectionReader_ImplBase {
	/**
	 * Name of configuration parameter that must be set to the path of a
	 * directory containing input files.
	 */
	public static final String PARAM_SOLR = "SolrCore";

	public static final String PARAM_QUERY = "SolrQuery";

	private String mLanguage;

	private int end;
	private int pagesize;
	private Set<String> ids;
	private SolrClient client;
	private SolrQuery query;
	private QueryResponse response;
	private SolrDocumentList results;
	private int docCursor;

	private String SolrServerAddress = "";
	private String SolrQuery = "";

	/**
	 * @see org.apache.uima.collection.CollectionReader_ImplBase#initialize()
	 */
	public void initialize() throws ResourceInitializationException {

		SolrServerAddress = (String) getConfigParameterValue(PARAM_SOLR);
		SolrQuery = (String) getConfigParameterValue(PARAM_QUERY);

		System.out.println("PARAM_SOLR: " + SolrServerAddress);
		System.out.println("PARAM_QUERY: " + SolrQuery);
		ids = new HashSet<String>();
		client = new HttpSolrClient.Builder(SolrServerAddress).build();
		query = new SolrQuery();

		pagesize = 1000;
		// query.setQuery("ABSTRACT:epilepsy OR ABSTRACT:seizure");
		query.setQuery(SolrQuery);
		query.setParam("wt", "json");
		query.setFilterQueries("");

		try {
			response = client.query(query);
			results = response.getResults();

			Long hitNumber = results.getNumFound();

			end = Integer.parseInt("" + hitNumber);
			
			System.out.println("Number of found documents: " + end);
			
			docCursor = 0;
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @see org.apache.uima.collection.CollectionReader#hasNext()
	 */
	public boolean hasNext() {
		return docCursor < end;
	}

	/**
	 * @see org.apache.uima.collection.CollectionReader#getNext(org.apache.uima.cas.CAS)
	 */
	public void getNext(CAS aCAS) throws IOException, CollectionException {
		System.out.println("Processing document number " + (docCursor+1) + "/" + end);
		int error = 1;
		JCas jcas;
		try {
			jcas = aCAS.getJCas();
		} catch (CASException e) {
			throw new CollectionException(e);
		}

		try {
			query.setStart(docCursor++);
			query.setRows(1);
			response = client.query(query);
			results = response.getResults();

			SourceDocumentInformation srcDocInfo = new SourceDocumentInformation(jcas);

			if (results.size() == 1) {
				SolrDocument doc = results.get(0);
				String docTitle = "";
				String docAbstract = "";
				String docDbrecordid = "";
				String language = "";
				if (doc.getFieldValue("TITLE") != null) {
					docTitle = doc.getFieldValue("TITLE").toString();
				}
				if (doc.getFieldValue("ABSTRACT") != null) {
					docAbstract = doc.getFieldValue("ABSTRACT").toString();
				}
				jcas.setDocumentText(docTitle + "\t\t"
						+ docAbstract);
				
				if (doc.getFieldValue("DBRECORDID") != null) {
					docDbrecordid = doc.getFieldValue("DBRECORDID").toString();
				}
				
				if (doc.getFieldValue("language") != null) {
					if (doc.getFieldValue("language").toString().contains("en")) {
						language = doc.getFieldValue("language").toString();
						((DocumentAnnotation) jcas.getDocumentAnnotationFs()).setLanguage(language);
					}					
				}
				srcDocInfo.setUri(docDbrecordid);
				srcDocInfo.setOffsetInSource(0);
				srcDocInfo.setDocumentSize((int) jcas.getDocumentText().length());
				srcDocInfo.setLastSegment(docCursor == end);
				srcDocInfo.addToIndexes();
//				if (doc.getFieldValue("ABSTRACT") != null) {
//					if (doc.getFieldValue("ABSTRACT").toString().length() > 0) {
//						if (doc.getFieldValue("language") != null) {
//							if (doc.getFieldValue("language").toString().contains("en")) {
//								error = 0;
//
//								if (mLanguage != null) {
//									((DocumentAnnotation) jcas.getDocumentAnnotationFs()).setLanguage("en");
//								}
//
//								System.out.println("Processing document with id: "
//										+ doc.getFieldValue("DBRECORDID").toString() + "\t" + docCursor + "/" + end);
//
//								jcas.setDocumentText(doc.getFieldValue("TITLE").toString() + "\t\t"
//										+ doc.getFieldValue("ABSTRACT").toString());
//								srcDocInfo.setUri(doc.getFieldValue("DBRECORDID").toString());
//								srcDocInfo.setOffsetInSource(0);
//								srcDocInfo.setDocumentSize((int) jcas.getDocumentText().length());
//								srcDocInfo.setLastSegment(docCursor == end);
//								srcDocInfo.addToIndexes();
//
//							} else {
//								System.err.println("Field language is not english");
//							}
//
//						} else {
//							System.err.println("Field language is null");
//						}
//					} else {
//						System.err.println("Field ABSTRACT is empty");
//					}
//				} else {
//					System.err.println("Field ABSTRACT is null");
//				}
			} else {
				System.err.println("Results size != 1");
			}

		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}




		// docCursor = end;

	}

	/**
	 * @see org.apache.uima.collection.base_cpm.BaseCollectionReader#close()
	 */
	public void close() throws IOException {
		client.close();
	}

	/**
	 * @see org.apache.uima.collection.base_cpm.BaseCollectionReader#getProgress()
	 */
	public Progress[] getProgress() {
		return new Progress[] { new ProgressImpl(docCursor, end, Progress.ENTITIES) };
	}

	/**
	 * Gets the total number of documents that will be returned by this
	 * collection reader. This is not part of the general collection reader
	 * interface.
	 * 
	 * @return the number of documents in the collection
	 */
	public int getNumberOfDocuments() {
		return this.end;
	}
}
