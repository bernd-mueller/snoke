package de.zbmed.snoke.uima.reader;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader_ImplBase;
import org.apache.uima.examples.SourceDocumentInformation;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.StringArray;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

/**
 * MongoDBReader
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class MongoDBReader extends CollectionReader_ImplBase{
	
	private int end;
	private MongoCursor<Document> docCursor;
	
	
	
	public static final String PARAM_MONGOSERVER = "MongoServer";
	
	public static final String PARAM_MONGOPORT = "MongoPort";
	
	public static final String PARAM_MONGODBNAME = "MongoDB";
	
	public static final String PARAM_MONGOCOLLECTIONNAME = "MongoCollection";
	
	//public static final String PARAM_SRCFIELD = "SrcField";
	
	public static final String PARAM_CONFIG_PARAM_SETTINGS = "SrcField";
	//public static final ArrayList PARAM_SRCFIELD = new ArrayList<>();
	

	private String MongoServer;
	private Integer count;
	private Integer MongoPort;
	private String MongoDB;
	private String MongoCollection;
	//private String SrcField;
	private String SrcField;
	

	MongoClient mongoClient;
	MongoCollection<Document> collection;
	MongoCollection<Document> collectionConsumer;
	/**
	 * @see org.apache.uima.collection.CollectionReader_ImplBase#initialize()
	 */
	public void initialize() throws ResourceInitializationException {
		
		
		SrcField = (String) getConfigParameterValue(PARAM_CONFIG_PARAM_SETTINGS);


		
		MongoServer = (String) getConfigParameterValue(PARAM_MONGOSERVER);
		MongoPort = Integer.parseInt((String) getConfigParameterValue(PARAM_MONGOPORT));
		MongoDB = (String) getConfigParameterValue(PARAM_MONGODBNAME);
		MongoCollection = (String) getConfigParameterValue(PARAM_MONGOCOLLECTIONNAME);
		
		System.out.println("SrcField: " + SrcField);

		System.out.println("MongoServer: " + MongoServer);
		System.out.println("MongoPort: " + MongoPort);
		System.out.println("MongoDB: " + MongoDB);
		System.out.println("MongoCollection: " + MongoCollection);
		
		System.out.println("--- Reading files in Mongo ---");
		
		mongoClient = new MongoClient(MongoServer, MongoPort);
		
		MongoDatabase db = mongoClient.getDatabase(MongoDB);
		System.out.println("Selected " + MongoDB);
		count = 0;
				
		
		collection = db.getCollection(MongoCollection);
		
		
		end = Integer.MAX_VALUE;
		
		BasicDBObject andQuery = new BasicDBObject();
		ArrayList<BasicDBObject> obj = new ArrayList<BasicDBObject>();
		// ({"research.fulltext_gst" : { "$exists":true }  })

		obj.add(new BasicDBObject(SrcField, new BasicDBObject("$exists", true)));
		
		andQuery.put("$and", obj);

		System.out.println("query processing");
		System.out.println(andQuery);

		docCursor = collection.find((Bson) andQuery).limit(end).iterator();


		
	}
	/**
	 * @see org.apache.uima.collection.CollectionReader#hasNext()
	 */
	public boolean hasNext() {
		
		if(count>end){
			return false;
		}
		return docCursor.hasNext();
		
	}
	/**
	 * @see org.apache.uima.collection.CollectionReader#getNext(org.apache.uima.cas.CAS)
	 */
	

	public void getNext(CAS aCAS) throws IOException, CollectionException {
		
		
		
		count++;
		
		Document currentDoc = docCursor.next();
		//System.out.println(currentDoc);
		//System.out.println("TYPE"+ currentDoc.getClass().getName());
		String _id = "noid";
		String docText = "";
		String lang = "";
		
		
		if (currentDoc.containsKey("_id")){
			_id = currentDoc.get("_id").toString();
		
			
		}


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
		
		 System.out.println("# " + count + "\tid: " + _id + "\tlength: " + docText.length());
		
	}
	
	private Object getFieldFromCursor(org.bson.Document o, String fieldName) {

	    final String[] fieldParts = fieldName.split("\\.");

	    int i = 1;
	    Object val = o.get(fieldParts[0]);

	    while(i < fieldParts.length && val instanceof org.bson.Document) {
	        val = ((org.bson.Document)val).get(fieldParts[i]);
	        i++;
	    }

	    return val;
	}
	
	
	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub	
	}
	
	@Override
	public Progress[] getProgress() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
