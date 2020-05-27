package de.zbmed.tdm.neo4j.reader;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * Neo4jConnector
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class Neo4jConnector {
	private String SERVER_ROOT_URI = "http://134.95.56.146:7474/db/data/";
	WebResource resource;
	ClientResponse response;

	public Neo4jConnector () {
		
	}
	
	public Neo4jConnector (String server_root_uri) {
		this.SERVER_ROOT_URI = server_root_uri;
	}

	private void checkConnection () {
		resource = Client.create()
		        .resource(SERVER_ROOT_URI);
		response = resource.get( ClientResponse.class );

		System.out.println( String.format( "GET on [%s], status code [%d]",
		        SERVER_ROOT_URI, response.getStatus() ) );
		response.close();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Neo4jConnector nc = new Neo4jConnector ();
		
		
	}
	public void openCypher () {
		final String txUri = SERVER_ROOT_URI + "transaction/commit";
		resource = Client.create().resource( txUri );
	}
	
	private ClientResponse sendCypher (String query) {

		String payload = "{\"statements\" : [ {\"statement\" : \"" +query + "\"} ]}";
		response = resource
		        .accept( MediaType.APPLICATION_JSON )
		        .type( MediaType.APPLICATION_JSON )
		        .entity( payload )
		        .post( ClientResponse.class );
		return response;

//		System.out.println( String.format(
//		        "POST [%s] to [%s], status code [%d], returned data: "
//		                + System.lineSeparator() + "%s",
//		        payload, txUri, response.getStatus(),
//		        response.getEntity( String.class ) ) );

		
	}
	
	public void closeCypher () {
		response.close();
	}
}
