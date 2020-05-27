package de.zbmed.tdm.mongodb;

import java.io.FileWriter;
import java.util.HashSet;
import java.util.Set;

import org.bson.BSON;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.Cursor;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
/**
 * CalcRetrievalProjects
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2019
 */
public class CalcRetrievalProjects {
	public static String PARAM_MONGOSERVER = "MongoServer";
	
	public static int PARAM_MONGOPORT = 27017;
	
	public static String PARAM_MONGODBNAME = "MongoDB";
	
	public static String PARAM_MONGOCOLLECTIONNAME = "MongoCollection";
	
	private int mDocNum;

	static MongoClient mongoClient;

	Set<String> dictionaryNames = new HashSet<String>();

	static MongoCollection<Document> collection;
	
	static MongoCollection<Document> zbmedke;
	
	static Set <ObjectId> sid = new HashSet<ObjectId>();
	static Set <String> did = new HashSet<String>();
	
	
	public static void main(String[] args) {
		
		// String [] meshids = {"D013964","D009566","D002648","D000293","D011827"};
		// String [] meshids = {"D009566"};
		
		String [] meshids = {
				"D013964",
				"D016606",
				"D002648",
				"D032721",
				"D000293",
				"D011627",
				"D000293",
				"D009566",
				"D060766",
				"D060587",
				"D014867",
				"D011827",
				"D001417"
			};
		
		for (String meshid : meshids) {
			sid = new HashSet<ObjectId>();
			did = new HashSet<String>();
			System.out.println("Processing " + meshid);
			PARAM_MONGOSERVER = "134.95.56.146";
			PARAM_MONGOPORT = 27017;
			PARAM_MONGODBNAME = "snoke";
			PARAM_MONGOCOLLECTIONNAME = "snoke260618";
			
			// TODO Auto-generated method stub
			mongoClient = new MongoClient(PARAM_MONGOSERVER, PARAM_MONGOPORT);
			
			System.out.println("Connected!");
	
			MongoDatabase db = mongoClient.getDatabase(PARAM_MONGODBNAME);
			System.out.println("Selected " + PARAM_MONGODBNAME);
	
			if (db != null) {
				if (db.getCollection(PARAM_MONGOCOLLECTIONNAME) != null) {
					collection = db.getCollection(PARAM_MONGOCOLLECTIONNAME);
					System.out.println("Getting collection " + PARAM_MONGOCOLLECTIONNAME);
					
					int counter = 0;
					for (int i=0; i<1500000; i=i+1000) {
						FindIterable<Document> fit = collection
								.find(Filters.eq ("tdm.concepts.cvid", meshid))
								.limit(1000)
								.skip(i);
						MongoCursor <Document> mc = fit.iterator();
						
						while (mc.hasNext()) {
							counter++;
							Document d = mc.next();
							ObjectId oi = d.get("_id", ObjectId.class);
							if (counter%1000==0) {
								System.out.println("Processing " + counter);
							}
							sid.add(oi);
						}
						System.out.println("Found " + counter + " in snoke");
						mc.close();
					}
					System.out.println("Saved " + sid.size());
					
				} 
			} else {
				System.err.println("" + PARAM_MONGOCOLLECTIONNAME + " NULL");
			}
			
			db = mongoClient.getDatabase("zbmedke");
			System.out.println("Selected zbmedke");
	
			if (db != null) {
				if (db.getCollection("livivodata260618") != null) {
					collection = db.getCollection("livivodata260618");
					System.out.println("Getting collection livivodata260618");
					BSON b = new BSON ();
					int counter = 0;
					for (ObjectId oi : sid) {
						FindIterable<Document> fit = collection.find(Filters.eq ("_id", oi));
						MongoCursor <Document> mc = fit.iterator();
						
						while (mc.hasNext()) {
							counter++;
							Document d = mc.next();
							String sdid = (String) d
									.get("liv", Document.class)
									.get("orig_data", Document.class)
									.get("DBRECORDID", String.class);
							did.add(sdid);
							// System.out.println(did);
						}
						mc.close();
						if (counter%1000==0) {
							System.out.println("Processing " + counter);
						}
					}
					System.out.println("Found " + counter + " in zbmedke");
				} 
			} else {
				System.err.println("livivodata260618 NULL");
			}
			writeSetToFile(did, meshid);
		}	
	}

	public static void writeSetToFile(Set<String> sid, String meshid) {
		System.out.println("Writing " + meshid);
		try {
			FileWriter fw = new FileWriter("resources/retr/" + meshid);
			for (String s : sid) {
				fw.write(s+"\n");	
			}
			fw.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
