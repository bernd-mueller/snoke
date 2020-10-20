package de.zbmed.snoke.uima.reader;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.examples.SourceDocumentInformation;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class BioASQMongoDBReader extends MongoDBReader {
	
	private static final Logger log = LoggerFactory.getLogger(BioASQMongoDBReader.class);
	
	public static final String PARAM_STARTYEAR = "StartYear";
	
	public static final String PARAM_MAXDOC = "MaxDoc";
	public int StartYear;
	
	public int MaxDoc;
	
	/**
	 * @see org.apache.uima.collection.CollectionReader_ImplBase#initialize()
	 */
	public void initialize() throws ResourceInitializationException {
		
		
		SrcField = (String) getConfigParameterValue(PARAM_CONFIG_PARAM_SETTINGS);
		MongoServer = (String) getConfigParameterValue(PARAM_MONGOSERVER);
		MongoPort = Integer.parseInt((String) getConfigParameterValue(PARAM_MONGOPORT));
		MongoDB = (String) getConfigParameterValue(PARAM_MONGODBNAME);
		MongoCollection = (String) getConfigParameterValue(PARAM_MONGOCOLLECTIONNAME);
		StartYear = Integer.parseInt((String) getConfigParameterValue(PARAM_STARTYEAR));
		MaxDoc = Integer.parseInt((String) getConfigParameterValue(PARAM_MAXDOC));
		
		log.info("SrcField: " + SrcField);

		log.info("MongoServer: " + MongoServer);
		log.info("MongoPort: " + MongoPort);
		log.info("MongoDB: " + MongoDB);
		log.info("MongoCollection: " + MongoCollection);
		log.info("StartYear: " + StartYear);
		log.info("MaxDoc: " + MaxDoc);
		
		log.info("--- Reading files in Mongo ---");
		
		mongoClient = new MongoClient(MongoServer, MongoPort);
		
		MongoDatabase db = mongoClient.getDatabase(MongoDB);
		System.out.println("Selected " + MongoDB);
		count = 0;
				
		
		collection = db.getCollection(MongoCollection);
		
		
		// end = Integer.MAX_VALUE;
		end = MaxDoc;
		
		BasicDBObject andQuery = new BasicDBObject();
		ArrayList<BasicDBObject> obj = new ArrayList<BasicDBObject>();
		// ({"research.fulltext_gst" : { "$exists":true }  })

		/*
		if (SrcField.contains("@")) {
			for (String field : SrcField.split("@")) {
				obj.add(new BasicDBObject(field, new BasicDBObject("$exists", true)));
			}
		} else {
			obj.add(new BasicDBObject(SrcField, new BasicDBObject("$exists", true)));
		}
		*/
		
		for (int i = StartYear; i<= 2020; i++) {
			obj.add(new BasicDBObject("year" , i+""));
		}
		
		
		andQuery.put("$or", obj);

		System.out.println("query processing");
		System.out.println(andQuery);

		docCursor = collection.find((Bson) andQuery).noCursorTimeout(true).batchSize(1000).iterator();
	}
	
	public void getNext(CAS aCAS) throws IOException, CollectionException {
		count++;
		Document currentDoc = docCursor.next();
		//System.out.println(currentDoc);
		//System.out.println("TYPE"+ currentDoc.getClass().getName());
		String _id = "noid";
		String docText = "";
		String lang = "";
		int year = 0;
		
		if (currentDoc.containsKey("_id")){
			_id = currentDoc.get("_id").toString();
		}
		
		// Overwrite _id to PMID
		if (currentDoc.containsKey("pmid")){
			_id = currentDoc.get("pmid").toString();
		}

		if (currentDoc.containsKey("year") && currentDoc.get("year") != null ){
			year = Integer.parseInt(currentDoc.get("year").toString());
			if (year >= StartYear ) {
				if (SrcField.contains("@")) {
					for (String fields : SrcField.split("@")) {
						Object o = (Object) this.getFieldFromCursor(currentDoc, fields);
						if (o instanceof String) {
							docText = o.toString();
						} else if (o instanceof java.util.ArrayList) {
							String iteratedText = "";
							for (String sub : (ArrayList<String>)o) {
								iteratedText += sub;
							}
							docText = iteratedText + "\n";
						}
					}
				} else {
					Object o = (Object) this.getFieldFromCursor(currentDoc, SrcField);
					if (o instanceof String) {
						docText = o.toString();
					} else if (o instanceof java.util.ArrayList) {
						String iteratedText = "";
						for (String sub : (ArrayList<String>)o) {
							iteratedText += sub;
						}
						docText = iteratedText;
					}
				}
			} else {
				log.info("Skipping " + _id + " because " + year + " < " + StartYear);
			}
		} else {
			log.info("No date field in " + _id);
		}

	
		 
		aCAS.createView(SrcField);

		JCas jcas = null;
		try {
			jcas = aCAS.getJCas().getView("_InitialView");
			
		} catch (CASException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				

		jcas.setDocumentLanguage(lang);
		if (docText.length()>0) {
			jcas.setDocumentText(docText);	
		} else {
			jcas.setDocumentText(docText);	
		}

		
		SourceDocumentInformation srcDocInfo = new SourceDocumentInformation(jcas);

		srcDocInfo.setUri(_id);
		srcDocInfo.setOffsetInSource(0);
		srcDocInfo.setDocumentSize((int) jcas.getDocumentText().length());
		srcDocInfo.setLastSegment(count == end);
		srcDocInfo.addToIndexes();
		
		 log.info("# " + count + "\tpmid: " + _id + "\tlength: " + docText.length() + "\tdate: " + year);
		
	}
	
}
