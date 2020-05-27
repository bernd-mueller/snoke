package de.zbmed.tdm.uima.consumer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.Feature;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.examples.SourceDocumentInformation;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.jcas.tcas.DocumentAnnotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceProcessException;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;


import de.zbmed.tdm.uima.analysis.TokenAnnotation;

/**
 * A simple CAS consumer that writes the CAS to XMI format.
 * <p>
 * This CAS Consumer takes one parameter:
 * <ul>
 * <li><code>OutputDirectory</code> - path to directory into which output files
 * will be written</li>
 * </ul>
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 * 
 */
public class ConsumerMongoDB extends CasConsumer_ImplBase {
	/**
	 * Name of configuration parameter that must be set to the path of a
	 * directory into which the output files will be written.
	 */
	public static final String PARAM_MONGOSERVER = "MongoServer";

	public static final String PARAM_MONGOPORT = "MongoPort";

	public static final String PARAM_MONGODBNAME = "MongoDB";

	public static final String PARAM_MONGOCOLLECTIONNAME = "MongoCollection";

	private String MongoServer;
	private Integer MongoPort;
	private String MongoDB;
	private String MongoCollection;

	MongoClient mongoClient;

	String SrcField = "";

	Set<String> dictionaryNames = new HashSet<String>();

	MongoCollection<Document> collection;

	public void initialize() throws ResourceInitializationException {

		MongoServer = (String) getConfigParameterValue(PARAM_MONGOSERVER);
		MongoPort = Integer.parseInt((String) getConfigParameterValue(PARAM_MONGOPORT));
		MongoDB = (String) getConfigParameterValue(PARAM_MONGODBNAME);
		MongoCollection = (String) getConfigParameterValue(PARAM_MONGOCOLLECTIONNAME);

		dictionaryNames.add("DictTerm_DrugBank");
		dictionaryNames.add("DictTerm_MeSH");
		dictionaryNames.add("DictTerm_Agrovoc");
		dictionaryNames.add("DictTerm_EpSO");
		dictionaryNames.add("DictTerm_ESSO");
		dictionaryNames.add("DictTerm_EPILONT");

		System.out.println("MongoServer: " + MongoServer);
		System.out.println("MongoPort: " + MongoPort);
		System.out.println("MongoDB: " + MongoDB);
		System.out.println("MongoCollection: " + MongoCollection);
		// TDM-Server: 134.95.56.146
		mongoClient = new MongoClient(MongoServer, MongoPort);

		// Interim-Server: 10.0.4.174
		// mongoClient = new MongoClient( "10.0.4.174" , 27017 );

		System.out.println("Connected!");

		MongoDatabase db = mongoClient.getDatabase(MongoDB);
		System.out.println("Selected " + MongoDB);

		if (db != null) {
			if (db.getCollection(MongoCollection) != null) {
				collection = db.getCollection(MongoCollection);
				System.out.println("Getting collection " + MongoCollection);
			} else {
				db.createCollection(MongoCollection);
				System.out.println("Creating collection " + MongoCollection);
			}
		} else {
			System.err.println("" + MongoCollection + " NULL");
		}

	}

	/**
	 * Called if clean up is needed in case of exit under error conditions.
	 * 
	 * @see org.apache.uima.resource.Resource#destroy()
	 */
	public void destroy() {
		if (mongoClient != null) {

			mongoClient.close();

		}
	}

	/**
	 * Processes the CAS which was populated by the TextAnalysisEngines. <br>
	 * In this case, the CAS is converted to XMI and written into the output
	 * file .
	 * 
	 * @param aCAS
	 *            a CAS which has been populated by the TAEs
	 * 
	 * @throws ResourceProcessException
	 *             if there is an error in processing the Resource
	 * 
	 * @see org.apache.uima.collection.base_cpm.CasObjectProcessor#processCas(org.apache.uima.cas.CAS)
	 */
	public void processCas(CAS aCAS) throws ResourceProcessException {

		JCas jcas;
		try {
			jcas = aCAS.getJCas();

			// retreive the filename of the input file from the CAS
			FSIterator<Annotation> iter = jcas.getAnnotationIndex(SourceDocumentInformation.type).iterator();

			// System.out.println(jcas.getDocumentText());
			String _id = "nodbrecordid";
			if (iter.hasNext()) {
				SourceDocumentInformation fileLoc = (SourceDocumentInformation) iter.next();

				_id = fileLoc.getUri().toString();
				if (fileLoc.getOffsetInSource() > 0) {
					_id += ("_" + fileLoc.getOffsetInSource());
				}
				_id += "";

			}

			String language = ((DocumentAnnotation) jcas.getDocumentAnnotationFs()).getLanguage();

			AnnotationIndex<Annotation> anno = jcas.getAnnotationIndex();


			BasicDBObject searchById = new BasicDBObject("_id", new ObjectId(_id));
			searchById.put("_id", new ObjectId(_id));

			
			MongoCursor<Document> cursor = collection.find(searchById).iterator();


			Date date = new Date();

			// go through a conceptterm an from concepts anno, change initialize
			// of List and Array.
			List<Object> MongoConceptArray = new ArrayList<>();
			
			Document dictDoc = new Document ();
			FindIterable<Document> iterDoc;
			iterDoc = collection.find(new Document(searchById));
			if (iterDoc.first() != null && ((Document) iterDoc.first()).containsKey("tdm")) {
				dictDoc = (Document) iterDoc.first().get("tdm");
				System.out.println("Got " + _id + " with existing annotations");
			}
			
			Iterator<CAS> it = aCAS.getViewIterator();

			/**
			 * get name for srcField from a View Name which is NOT "_InitialView"
			 */
			while (it.hasNext()) {
				CAS c = it.next();
				if (!c.getViewName().equals("_InitialView")) {
					this.SrcField = c.getViewName();
				}
			}

			it = aCAS.getViewIterator();
			while (it.hasNext()) {
				CAS c = it.next();
				
				/**
				 * ConceptMapper only works on _InitialView having a single document text
				 * It doesn't work to create multiple views that can be annotated by ConceptMapper
				 * This is why everything works here on _InitialView although the SrcField name is taken
				 * from a fictional view that is supposed to contain the text and the annotations of the
				 * specified SrcField.
				 * 
				 * @Todo: Adjust ConceptMapper to run on multiple views
				 */
				if (c.getViewName().equals("_InitialView")) {
					// System.out.println("6.");
					// srcField = c.getViewName();
					// System.out.println("### " +
					// c.getAnnotationIndex().size());
					AnnotationIndex<Annotation> canno = c.getAnnotationIndex();

					for (Annotation can : canno) {
						
						Type t = can.getType();
						// if (language.contains("en")) {
						String curDictName = t.getShortName();
						
						if (dictionaryNames.contains(curDictName)) {
							// System.out.println("### curDictName " + t.getName());
							String conceptclass = "";
							String conceptid = "";
							String matchedText = "";
							String conceptname = "";
							String semclass = "";
							String pos = "";
							String startoffset = "" + can.getBegin();
							String endoffset = "" + can.getEnd();
							FSArray matchedTokens = null;
							
							String postaggings = "";

							matchedText = can.getCoveredText();
							

							/**
							 * Extracting features for writing the standoff annotations
							 */
							for (Feature f : t.getFeatures()) {
								/**
								 * ConceptIdentifier from dictionary attribute "CodeValue"
								 */
								if (f.getShortName().toString().equals("ConceptIdentifier")) {
									conceptid = can.getFeatureValueAsString(f);
									// System.out.println(an.getFeatureValueAsString(f));
								}
								/**
								 * ConceptClass from dictionary attribute "CodeType"
								 */
								else if (f.getShortName().toString().equals("ConceptClass")) {
									conceptclass = can.getFeatureValueAsString(f);
									// System.out.println(an.getFeatureValueAsString(f));
								}
								/**
								 * DictCanon from dictionary attribute "canonical"
								 */
								else if (f.getShortName().toString().equals("DictCanon")) {
									conceptname = can.getFeatureValueAsString(f); //
								}
								/**
								 * SemanticClass from dictionary attribute "SemClass" (currently not used in any dictionary)
								 */
								else if (f.getShortName().toString().equals("SemanticClass")) {
									semclass = can.getFeatureValueAsString(f); //
								}
								/**
								 * PartOfSpeech from dictionary attribute "POS" (currently not used in any dictionary)
								 */
								else if (f.getShortName().toString().equals("PartOfSpeech")) {
									pos = can.getFeatureValueAsString(f); //
								}
								/**
								 * matchedText from configuration attribute "ResultingAnnotationMatchedTextFeature" 
								 * is always null, doesn't work!
								 */
								else if (f.getShortName().toString().equals("matchedText")) {
									// matchedText = can.getFeatureValueAsString(f); //
								}
								/**
								 * matchedTokens from configuration attribute "MatchedTokensFeatureName" 
								 * this should be an array of TokenAnnotations that have posTags set by the HMM Tagger
								 */
								else if (f.getShortName().toString().equals("matchedTokens")) {
									matchedTokens = (FSArray) can.getFeatureValue(f); //
									for (int i=0; i<matchedTokens.size(); i++) {
										FeatureStructure fs = matchedTokens.get(i);
										if (matchedTokens.get(i).getType().toString().equals("de.zbmed.tdm.uima.analysis.TokenAnnotation")) {
											TokenAnnotation ta = (de.zbmed.tdm.uima.analysis.TokenAnnotation) fs;
										
											// System.out.println("#\t" + ta.getCoveredText() + "\t" + ta.getPosTag());
											postaggings += ta.getPosTag() + " ";
											
											
										}
										
									}
									postaggings = postaggings.trim();
								} 
							}
							

							/**
							 * Appending the current standoff annotations into an embeddedMongoDoc
							 */
							String Dictname = curDictName;
							if (Dictname.contains("DictTerm")) {
								Dictname = curDictName.replace("DictTerm_", "");
							}
							Document embeddedMongoDoc = new Document();
							
							embeddedMongoDoc.append("dict", Dictname);
							embeddedMongoDoc.append("field", this.SrcField);
							embeddedMongoDoc.append("concept", conceptname);
							embeddedMongoDoc.append("text", matchedText);
							embeddedMongoDoc.append("cvid", conceptid);
							embeddedMongoDoc.append("pos", postaggings);

							List<Object> OffsetList = new ArrayList<>();
							OffsetList.add(startoffset);
							OffsetList.add(endoffset);
							embeddedMongoDoc.append("offset", OffsetList);

							MongoConceptArray.add(new BasicDBObject(embeddedMongoDoc));
						}
					}
				} 
			}
			
			//System.out.println("######## Merging concept arrays ##############");
			/**
			 * check if annotations from other fields already exists
			 * if yes, merge existing annotations with the new annotations
			 */
			if (dictDoc.containsKey("concepts")) {
				ArrayList<Object> existingConceptArray = (ArrayList<Object>) dictDoc.get("concepts"); 
				List <Object> mergedList = new ArrayList<Object> ();
				Set <Object> mergedSet = new HashSet <Object> ();
				//System.out.println("# MongoConceptArray");
				for (Object o : MongoConceptArray) {
					mergedSet.add(new Document((Map<String, Object>) o));	
					//System.out.println("\t" + new Document((Map<String, Object>) o));
				}
				//System.out.println("# existingConceptArray");
				for (Object o : existingConceptArray) {
					mergedSet.add(o);
					//System.out.println("\t" + o);
				}
				//System.out.println("# mergedSet");
				for (Object o : mergedSet) {
					mergedList.add(o);
					//System.out.println("\t" + o);
				}
				MongoConceptArray = mergedList;
			}
			/**
			 * Write the merged array into the field concepts
			 */
			dictDoc.append("concepts", MongoConceptArray);
			//System.out.println("######## Merging concept arrays ##############");


			/**
			 * Update the date attribute 
			 */
			if (dictDoc.containsKey("ts_update")) {
				dictDoc.remove("ts_update");
			}
			dictDoc.append("ts_update", date);


			/**
			 * Update the concept array into the tdm field
			 */
			System.out.println(
					"Writing: " + _id + 
					" into the database " + this.MongoDB + 
					" and collection " + this.MongoCollection + 
					" on server " + this.MongoServer + ":" + this.MongoPort) ;
			UpdateOptions update = new UpdateOptions();
			update.upsert(true);
			try {
				collection.updateOne(new Document(searchById), new Document("$set", new Document("tdm", dictDoc)), update);
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}

			// collection.updateOne(new Document(searchById), new Document("$push", new Document("tdm", dictDoc)));

		} catch (CASException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}
