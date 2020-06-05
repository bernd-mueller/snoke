package de.zbmed.snoke.uima.consumer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.Feature;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.examples.SourceDocumentInformation;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceProcessException;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;

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
 */
public class ConsumerBioASQ extends CasConsumer_ImplBase {
	/**
	 * Name of configuration parameter that must be set to the path of a
	 * directory into which the output files will be written.
	 */
	public static final String PARAM_OUTPUTFILE = "OutputFile";
	

	private Map <String, Set <String>> docmeshannotations;
	
	private String OutputFile;
	
	private PrintWriter writer;


	private int mDocNum;

	MongoClient mongoClient;
	
	String srcField = "";

	Set<String> dictionaryNames = new HashSet<String>();

	MongoCollection<Document> collection;

	public void initialize() throws ResourceInitializationException {
		
		OutputFile = (String) getConfigParameterValue(PARAM_OUTPUTFILE);
		
		docmeshannotations = new HashMap <String, Set<String>>();
		
		dictionaryNames.add("DictTerm_MeSH");
		
		mDocNum = 0;

		System.out.println("OutputFile: " + OutputFile);

		// TDM-Server: 134.95.56.146

		// Interim-Server: 10.0.4.174
		// mongoClient = new MongoClient( "10.0.4.174" , 27017 );
		
		try{
		    writer = new PrintWriter(OutputFile, "UTF-8");
//		    writer.println("The first line");
//		    writer.println("The second line");

		} catch (IOException e) {
		   // do something
		}
		
		

		
	}

	/**
	 * Called if clean up is needed in case of exit under error conditions.
	 * 
	 * @see org.apache.uima.resource.Resource#destroy()
	 */
	public void destroy() {
		// System.out.println("RESULTS");
		
		int curdoccount = docmeshannotations.size();
		int doccounter = 0;
		writer.println("{\"documents\": [");
		for (String pmid : docmeshannotations.keySet()) {
			// System.out.println(pmid + ": " + docmeshannotations.get(pmid).toString());
			writer.print("{\"labels\":[");
			int curdesccount = docmeshannotations.get(pmid).size();
			int desccounter = 0;
			for (String descriptor : docmeshannotations.get(pmid)) {
				writer.print("\"" + descriptor + "\"");
				if (++desccounter<curdesccount) {
					writer.print(",");
				}
			}
			writer.print("], \"pmid\": " + pmid + "}");
			if (++doccounter<curdoccount) {
				writer.print(",\n");
			}
		}
		writer.println("]}");
	    writer.close();
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

		Set <String> meshannotations = new HashSet <String> ();
		JCas jcas;
		JCas cas;
		
		try {
			jcas = aCAS.getJCas();
			cas = aCAS.getJCas();
			
			Iterator <CAS> it = aCAS.getViewIterator();

			while (it.hasNext()) {
				CAS c = it.next();
//				System.out.println("### " + c.getViewName());
//				if (!c.getViewName().equals("_InitialView")) {
					srcField = c.getViewName();
//					System.out.println("### " + c.getAnnotationIndex().size());
					AnnotationIndex<Annotation> canno = c.getAnnotationIndex();
					
					for (Annotation can : canno) {
						Type t = can.getType();
						// if (language.contains("en")) {
							String curDictName = t.getShortName();
//							System.out.println("### curDictName " + t.getName());
							if (dictionaryNames.contains(curDictName)) {
								
						
								String conceptid = "";
								String conceptclass = "";
								String conceptname ="";
								String startoffset = ""+can.getBegin();
								String endoffset = ""+can.getEnd();

								conceptname = can.getCoveredText();				
								
								for (Feature f : t.getFeatures()) {
				
									if (f.getShortName().toString().equals("ConceptIdentifier")) {
										conceptid = can.getFeatureValueAsString(f);
										// System.out.println(an.getFeatureValueAsString(f));
									} else if (f.getShortName().toString().equals("ConceptClass")) {
										conceptclass = can.getFeatureValueAsString(f);
										// System.out.println(an.getFeatureValueAsString(f));
									}
								}
//								System.out.println(srcField+"### conceptid "+ conceptid);
//								System.out.println(srcField+"### conceptclass "+ conceptclass);
//								System.out.println(srcField+"### conceptname "+conceptname);
//								System.out.println(srcField+"### startoffset "+ startoffset);
//								System.out.println(srcField+"### endoffset "+ endoffset);
								meshannotations.add(conceptid);
							}
					}
				}
				
//			}
			
			
		} catch (CASException e) {
			throw new ResourceProcessException(e);
		}

		// retreive the filename of the input file from the CAS
		FSIterator it = jcas.getAnnotationIndex(SourceDocumentInformation.type).iterator();
		
		
		//System.out.println(jcas.getDocumentText());
		String _id = "nodbrecordid";
		if (it.hasNext()) {
			SourceDocumentInformation fileLoc = (SourceDocumentInformation) it.next();
			
			
			
			_id = fileLoc.getUri().toString();
			if (fileLoc.getOffsetInSource() > 0) {
				_id += ("_" + fileLoc.getOffsetInSource());
			}
			_id += "";

		}
		docmeshannotations.put(_id, meshannotations);
	
	}


}
