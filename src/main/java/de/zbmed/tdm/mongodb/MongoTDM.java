package de.zbmed.tdm.mongodb;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Set;
import java.util.Map.Entry;

import org.bson.Document;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
/**
 * MongoTDM
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class MongoTDM {
	MongoClient mongoClient;
	MongoCursor<Document> cursor;
	PrintWriter writer;
	
	MongoCollection<Document> collection;
	
	String [] keyToParse = {
		".0.front.article-meta.title-group.article-title",
		".0.body.sec"
	};
	
	public void openServerConnection () {
		
		mongoClient = new MongoClient( "134.95.56.146" , 27017 );
		System.out.println("Connected!");

		MongoDatabase db = mongoClient.getDatabase("mongotdm");
		System.out.println("Selected mongotdm");

		if (db != null) {
			if (db.getCollection("tdm") != null) {
			       collection = db.getCollection("tdm");
			       System.out.println("Getting collection tdm");
			} else {
			   	db.createCollection("tdm");
			    System.out.println("Creating collection tdm");
			}
		} else {
			System.err.println("DB NULL");
		}
	
	}
	
	public void readCollectionToCursor () {
		cursor = mongoClient.getDatabase("tdm").getCollection("xml").find().iterator();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MongoTDM mc = new MongoTDM ();
		mc.openWriter();
		mc.openServerConnection();
		
		Document doc = new Document();
		doc.append("DBRECORDID", "ABC123");
		doc.append("DICTIONARYNAME", "DrugBank");
		mc.collection.insertOne(doc);
	}
	
	public void openWriter () {
		try {
			writer = new PrintWriter("C:\\Users\\Muellerb\\Desktop\\mongoresults.txt", "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void closeWriter () {
		writer.close();
	}
	public void parseCursor () {
		while (cursor.hasNext()) {
			Document d = cursor.next();
			// writer.println(d);
			recursiveParse ("", d);
		}
	}
	
	public void recursiveParse (String key, java.util.ArrayList array) {
		for (Object o : array) {
			checkTypeToCallRec (key, o);
		}
	}
	
	public void recursiveParse (String key, Document doc) {
		Set <Entry <String, Object>> s = doc.entrySet();
		for (Entry <String, Object> e : s) {
			checkTypeToCallRec (key + "." + e.getKey(), e.getValue());
			// activePrint = false;
		}
	}
	
	public void checkTypeToCallRec (String key, Object o) {
		writer.println(o.getClass().getName());
		if (o instanceof org.bson.Document) {
			recursiveParse(key, (org.bson.Document)o);
		} else if (o instanceof java.util.ArrayList) {
			recursiveParse (key, (java.util.ArrayList) o);
		} else if (o instanceof String) {
			writer.println(key + ":\t" + o);

		} else {
			// writer.println("Unhandled object type");
		}
	}
	



}
