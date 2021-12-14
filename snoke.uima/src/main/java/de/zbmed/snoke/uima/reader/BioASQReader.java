package de.zbmed.snoke.uima.reader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
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
import org.nd4j.shade.jackson.core.JsonFactory;
import org.nd4j.shade.jackson.core.JsonParseException;
import org.nd4j.shade.jackson.core.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * BioASQReader
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class BioASQReader extends CollectionReader_ImplBase {
	private static final Logger log = LoggerFactory.getLogger(BioASQReader.class);
	public static final String PARAM_JSON = "JSON";
	private String json = "";
	List <BioASQDocument> sbod = new ArrayList <BioASQDocument>();
	JsonParser jsonParser;
	int docCursor = 0;
	int end;
	@Override
	public void initialize() throws ResourceInitializationException {
		 json = (String) getConfigParameterValue(PARAM_JSON);
		 
		 
	        try {
	            FileInputStream fis = new FileInputStream(
	                    json
	            );

	            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

	            log.info("Reading File line by line using BufferedReader");

	            String line = reader.readLine();
	            while(line != null){
	                line = reader.readLine();
	                if (line!= null) {

	                    if (line.length() > 2) {

	                            jsonParser = new JsonFactory().createParser(line);
	                            BioASQDocument bod = new BioASQDocument();
	                            bod = BioASQDocument.parseJSON(line);
	                            sbod.add(bod);

	                    }

	                }
	            }

	            log.info("Done reading documents...");
				docCursor = 0;
				end = sbod.size();
	        } catch (JsonParseException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        
	 }
	@Override
	public void getNext(CAS aCAS) throws IOException, CollectionException {
		log.info("Processing document number " + (docCursor+1) + "/" + end);
		int error = 1;
		JCas jcas;
		try {
			jcas = aCAS.getJCas();
		} catch (CASException e) {
			throw new CollectionException(e);
		}

		BioASQDocument bod = sbod.get(docCursor++);

		SourceDocumentInformation srcDocInfo = new SourceDocumentInformation(jcas);

		if (bod != null) {
		
		jcas.setDocumentText(bod.getTitle() + "\t\t" + bod.getAbstractText());

		((DocumentAnnotation) jcas.getDocumentAnnotationFs()).setLanguage("en");

		srcDocInfo.setUri(bod.getPmid());
		srcDocInfo.setOffsetInSource(0);
		srcDocInfo.setDocumentSize((int) jcas.getDocumentText().length());
		srcDocInfo.setLastSegment(docCursor == end);
		srcDocInfo.addToIndexes();
	} else {
		log.info("Doc is null!");
	}

	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Progress[] getProgress() {
		// TODO Auto-generated method stub
		return new Progress[] { new ProgressImpl(docCursor, end, Progress.ENTITIES) };
	}

	@Override
	public boolean hasNext() throws IOException, CollectionException {
		return docCursor < end;
	}

}
