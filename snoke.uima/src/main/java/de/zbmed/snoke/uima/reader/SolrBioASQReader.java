package de.zbmed.snoke.uima.reader;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.examples.SourceDocumentInformation;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.DocumentAnnotation;

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
public class SolrBioASQReader extends de.zbmed.snoke.uima.reader.ZBMEDSolrReader {
	

	/**
	 * @see org.apache.uima.collection.CollectionReader#getNext(org.apache.uima.cas.CAS)
	 */
	public void getNext(CAS aCAS) throws IOException, CollectionException {
		
		JCas jcas;
		try {
			jcas = aCAS.getJCas();
		} catch (CASException e) {
			throw new CollectionException(e);
		}

		try {
			this.getQuery().setStart(this.getDocCursor());
			this.setDocCursor(this.getDocCursor()+1);
			this.getQuery().setRows(1);
			this.setResponse(this.getClient().query(this.getQuery()));
			this.setResults(this.getResponse().getResults());

			SourceDocumentInformation srcDocInfo = new SourceDocumentInformation(jcas);

			if (this.getResults().size() == 1) {
				SolrDocument doc = this.getResults().get(0);
				String docTitle = "";
				String docAbstract = "";
				String docDbrecordid = "";
				String language = "";
				
				if (doc.getFieldValue("title") != null) {
					docTitle = doc.getFieldValue("title").toString();
				}
				
				
				if (doc.getFieldValue("abstractText") != null) {
					docAbstract = doc.getFieldValue("abstractText").toString();
				}
				
				jcas.setDocumentText(docTitle + "\t\t"
						+ docAbstract);
				

				if (doc.getFieldValue("pmid") != null) {	
					docDbrecordid = doc.getFieldValue("pmid").toString();
					System.out.println("Processing document number " + (this.getDocCursor()+1) + "/" + this.getEnd() + " with id " + docDbrecordid); 
					//System.out.println("Processing " + docDbrecordid); 
				}
				
				if (doc.getFieldValue("language") != null) {
					if (doc.getFieldValue("language").toString().contains("en")) {
						language = doc.getFieldValue("language").toString();
						((DocumentAnnotation) jcas.getDocumentAnnotationFs()).setLanguage(language);
					}					
				} else {
					language = "en";
					((DocumentAnnotation) jcas.getDocumentAnnotationFs()).setLanguage(language);
				}
				srcDocInfo.setUri(docDbrecordid);
				srcDocInfo.setOffsetInSource(0);
				srcDocInfo.setDocumentSize((int) jcas.getDocumentText().length());
				srcDocInfo.setLastSegment(this.getDocCursor() == this.getEnd());
				srcDocInfo.addToIndexes();
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

}
