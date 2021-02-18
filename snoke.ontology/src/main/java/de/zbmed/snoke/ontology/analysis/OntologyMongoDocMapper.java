package de.zbmed.snoke.ontology.analysis;

import static com.mongodb.client.model.Filters.eq;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class OntologyMongoDocMapper {
	MongoClientURI connectionString;
	MongoClient mongoClient;
	MongoDatabase database;
	MongoCollection<Document> collection;

	
	public void connectToDatabase () {
		 connectionString = new MongoClientURI("mongodb://localhost:27017");
		 mongoClient = new MongoClient(connectionString);
		 
		 database = mongoClient.getDatabase("bioasq");
		 
		 collection = database.getCollection("snoke2021");
	}

	public MongoCursor<Document> searchDatabase (String dictName) {
		MongoCursor<Document> cursor = collection.find(eq("tdm.concepts.dict", dictName)).iterator();
		
		// long c = collection.count(eq("tdm.concepts.dict", dictName));
		
		return cursor;
	}
	
	public void writePMIDs (MongoCursor<Document> cursor, String dictName) {
		BufferedWriter bw = null;
		FileWriter  writer;
		try {
			writer = new FileWriter ("resources/pmids/" + dictName + ".txt");
			bw = new BufferedWriter(writer);
			while (cursor.hasNext()) {
				Document d = cursor.next();
				// String pmid = d.g
				String pmid = ((Document) d.get("tdm")).getString("PMID");

				// System.out.println(pmid);
				bw.write(pmid + "\n");
			}
			bw.close();
			writer.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		OntologyMongoDocMapper o = new OntologyMongoDocMapper ();
		o.connectToDatabase();
		o.writePMIDs(o.searchDatabase("DrugNames"), "DrugNames");
		
	}

}
