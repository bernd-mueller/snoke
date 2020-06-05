package de.zbmed.snoke.mongodb;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

/**
 * TestSNOKE
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class TestSNOKE {
	public static String PARAM_MONGOSERVER = "MongoServer";
	
	public static int PARAM_MONGOPORT = 27017;
	
	public static String PARAM_MONGODBNAME = "MongoDB";
	
	public static String PARAM_MONGOCOLLECTIONNAME = "MongoCollection";
	
	private int mDocNum;

	MongoClient mongoClient;

	Set<String> dictionaryNames = new HashSet<String>();

	MongoCollection<Document> collection;
	
	public TestSNOKE () {
		PARAM_MONGOSERVER = "134.95.56.146";
		PARAM_MONGOPORT = 27017;
		PARAM_MONGODBNAME = "mongotdm";
		PARAM_MONGOCOLLECTIONNAME = "tdm";
		
		mongoClient = new MongoClient(PARAM_MONGOSERVER, PARAM_MONGOPORT);
		
		System.out.println("Connected!");

		MongoDatabase db = mongoClient.getDatabase(PARAM_MONGODBNAME);
		System.out.println("Selected " + PARAM_MONGODBNAME);

		if (db != null) {
			if (db.getCollection(PARAM_MONGOCOLLECTIONNAME) != null) {
				collection = db.getCollection(PARAM_MONGOCOLLECTIONNAME);
				System.out.println("Getting collection " + PARAM_MONGOCOLLECTIONNAME);
			} else {
				db.createCollection("bernd");
				System.out.println("Creating collection "+ PARAM_MONGOCOLLECTIONNAME);
			}
		} else {
			System.err.println("" + PARAM_MONGOCOLLECTIONNAME + " NULL");
		}
	}
	
	public void createTestDoc () {
		Document testdoc = new Document ();
		Document doc = new Document ();
		
		testdoc.append("DBRECORDID", "123abc");

		
		doc.append("DICTIONARYNAME", "DictTerm_Agrovoc");
		
		doc.append("DOCLANGUAGE", "en");
		
		doc.append("STARTOFFSET", "1740");
		doc.append("ENDOFFSET", "1751");
		doc.append("MATCHEDTEXT", "Information");

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		doc.append("ts_insert", dateFormat.format(date));
		doc.append("ts_update", dateFormat.format(date));
		
		testdoc.append("ts_insert", dateFormat.format(date));
		testdoc.append("ts_update", dateFormat.format(date));
		
		testdoc.append("tdm", doc);
		collection.insertOne(testdoc);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestSNOKE ts = new TestSNOKE ();
		
		// ts.createTestDoc();
		
		BasicDBObject fields = new BasicDBObject();
		fields.put("DBRECORDID", "123abc");
		MongoCursor <Document> cursor = ts.collection.find(fields).iterator();
		while(cursor.hasNext()) {
		    Document d = cursor.next();
		    System.out.println(d);
		    System.out.println(d.get("DBRECORDID"));
		    Document tdmdoc = (Document) d.get("tdm");
		    
		    
		    System.out.println(tdmdoc.get("MATCHEDTEXT"));
		}
	}

}
