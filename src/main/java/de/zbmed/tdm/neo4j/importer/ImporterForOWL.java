package de.zbmed.tdm.neo4j.importer;


import java.io.File;
import java.util.Set;

import javax.ws.rs.core.MediaType;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * ImporterForOWL
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class ImporterForOWL {
	OWLOntology ontology;
	private String prefNode = "";
	private int c = 0;
	private static String SERVER_ROOT_URI = "http://134.95.56.146:7474/db/data/";
	
	private void checkConnection () {
		WebResource resource = Client.create()
		        .resource(SERVER_ROOT_URI);
		ClientResponse response = resource.get( ClientResponse.class );

		System.out.println( String.format( "GET on [%s], status code [%d]",
		        SERVER_ROOT_URI, response.getStatus() ) );
		response.close();
	}

	private void sendCypher (String query) {
		final String txUri = SERVER_ROOT_URI + "transaction/commit";
		WebResource resource = Client.create().resource( txUri );

		String payload = "{\"statements\" : [ {\"statement\" : \"" +query + "\"} ]}";
		ClientResponse response = resource
		        .accept( MediaType.APPLICATION_JSON )
		        .type( MediaType.APPLICATION_JSON )
		        .entity( payload )
		        .post( ClientResponse.class );

		System.out.println( String.format(
		        "POST [%s] to [%s], status code [%d], returned data: "
		                + System.lineSeparator() + "%s",
		        payload, txUri, response.getStatus(),
		        response.getEntity( String.class ) ) );

		response.close();
	}
	private void importOntology(OWLOntology ontology) throws Exception {		
	    OWLReasoner reasoner = new Reasoner(ontology);
	    this.ontology = ontology;
	    clearAllNodesFromDatabase ();
		if (!reasoner.isConsistent()) {
			System.err.println("Ontology is inconsistent");
			// Throw your exception of choice here
			throw new Exception("Ontology is inconsistent");
		}

		//getOrCreateNodeWithUniqueFactory("owl:Thing");

		for (OWLClass c : ontology.getClassesInSignature(true)) {
			String classString = c.toString();
			if (classString.contains("#")) {
				classString = classString.substring(classString.indexOf("#") + 1, classString.lastIndexOf(">"));
			}
			
			System.out.println(classString);
			
			createNodesAndRelationships (c, classString);
			
			
			
		}
		
	}
	
	private void createNodesAndRelationships (OWLClass c, String classname) {
		getOrCreateNodeWithUniqueFactory(classname);
		createRelationshipsForClass (c, classname);
	}
	
	private void createRelationshipsForClass (OWLClass c, String classname) {
		
		Set <OWLAnnotation> s = c.getAnnotations(ontology);
		for (OWLAnnotation oa : s) {
			System.out.println("#A#"+oa.toString());
			OWLAnnotationProperty oap = oa.getProperty();
			IRI iri = oap.getIRI();
			OWLAnnotationValue oaval = oa.getValue();
			System.out.println(iri + "@" + oaval);
			String val = oaval.toString();
			if (val.contains("@")) {
				val = val.split("@")[0];
			}
			val = val.replaceAll("\"", "");
			val = val.replaceAll(":", "_");
			val = val.replaceAll("_", "");
			classname = classname.replaceAll("\"", "");
			classname = classname.replaceAll(":", "_");
			classname = classname.replaceAll("_", "");
			this.sendCypher("CREATE (n:Thing { name:'" + val + "'}) RETURN n");
			

			this.sendCypher("MATCH (a:Thing),(b:Thing) "
					+ "WHERE a.name = '" + classname + "' AND b.name = '" + iri.toString() + "' "
					+ "CREATE (a)-[r:"+ val +"]->(b) "
					+ "RETURN r");
		}

	}
	
	private void clearAllNodesFromDatabase () {
		this.sendCypher("MATCH (n) OPTIONAL MATCH (n)-[HasRelation]-() DELETE n,HasRelation");
	}
	private void getOrCreateNodeWithUniqueFactory (String nodename) {
		//CREATE (n:Person { name : 'Andres', title : 'Developer' })
		if (nodename.equals("owl:Thing")) {
			nodename = "Thing";
		}
		this.sendCypher("CREATE (n:Thing { name:'" + nodename + "'}) RETURN n");
		

		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ImporterForOWL ofi = new ImporterForOWL ();
		ofi.checkConnection();
//		MATCH (n) RETURN n LIMIT 100
		// ofi.sendCypher("CREATE (n {name:'World'}) RETURN 'hello', n.name");
		//ofi.sendCypher("MATCH (n) RETURN n LIMIT 100");
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntology epo;
		try {
			epo = manager.loadOntologyFromOntologyDocument(new File("D:\\EpilepsyOntology.owl"));
			ofi.importOntology(epo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
