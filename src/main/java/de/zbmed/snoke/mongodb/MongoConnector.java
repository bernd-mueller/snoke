package de.zbmed.snoke.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map.Entry;
import java.util.Set;

/**
 * MongoConnector
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class MongoConnector {
	MongoClient mongoClient;
	MongoCursor<Document> cursor;
	PrintWriter writer;
	
	String [] keyToParse = {
		".0.front.article-meta.title-group.article-title",
		".0.body.sec"
	};
	
	public void openServerConnection () {
		mongoClient = new MongoClient( "134.95.56.146" , 27017 );
	}
	
	public void readCollectionToCursor () {
		cursor = mongoClient.getDatabase("bernd").getCollection("xml").find().iterator();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MongoConnector mc = new MongoConnector ();
		mc.openWriter();
		mc.openServerConnection();
		mc.readCollectionToCursor();
		mc.parseCursor();
		mc.closeWriter();
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




