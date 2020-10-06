package de.zbmed.snoke.neo4j.showcase;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * ZikaMeSH
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class ZikaMeSH {
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
	
	private void clearAllNodesFromDatabase () {
		this.sendCypher("MATCH (n) OPTIONAL MATCH (n)-[HasRelation]-() DELETE n,HasRelation");
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ZikaMeSH zm = new ZikaMeSH ();
		zm.clearAllNodesFromDatabase();
		
		zm.sendCypher("CREATE (n:MeSH {name: 'Viruses', conceptid: 'D014780'})");
		zm.sendCypher("CREATE (n:MeSH {name: 'RNA Viruses', conceptid: 'D012328'})");
		zm.sendCypher("CREATE (n:MeSH {name: 'Flaviviridae', conceptid: 'D018067'})");
		zm.sendCypher("CREATE (n:MeSH {name: 'Dengue Virus', conceptid: 'D003716'})");
		zm.sendCypher("CREATE (n:MeSH {name: 'Encephalitis Viruses, Japanese', conceptid: 'D018349'})");
		zm.sendCypher("CREATE (n:MeSH {name: 'Encephalitis Viruses, Tick-Borne', conceptid: 'D004669'})");
		zm.sendCypher("CREATE (n:MeSH {name: 'Yellow fever virus', conceptid: 'D015005'})");
		zm.sendCypher("CREATE (n:MeSH {name: 'Virus, Zika', conceptid: 'D000071244'})");
		
		
		zm.sendCypher(""
				+ "MATCH (a:MeSH),(b:MeSH)"
				+ "WHERE a.name = 'Dengue Virus' AND b.name ='Flaviviridae'"
				+ "CREATE (a)-[r:isChild { name : a.name + '<->' + b.name }]->(b)"
				+ "RETURN r");
		
		zm.sendCypher(""
				+ "MATCH (a:MeSH),(b:MeSH)"
				+ "WHERE a.name = 'Encephalitis Viruses, Japanese' AND b.name ='Flaviviridae'"
				+ "CREATE (a)-[r:isChild { name : a.name + '<->' + b.name }]->(b)"
				+ "RETURN r");
		
		zm.sendCypher(""
				+ "MATCH (a:MeSH),(b:MeSH)"
				+ "WHERE a.name = 'Encephalitis Viruses, Tick-Borne' AND b.name ='Flaviviridae'"
				+ "CREATE (a)-[r:isChild { name : a.name + '<->' + b.name }]->(b)"
				+ "RETURN r");
		
		zm.sendCypher(""
				+ "MATCH (a:MeSH),(b:MeSH)"
				+ "WHERE a.name = 'Yellow fever virus' AND b.name ='Flaviviridae'"
				+ "CREATE (a)-[r:isChild { name : a.name + '<->' + b.name }]->(b)"
				+ "RETURN r");
		
		zm.sendCypher(""
				+ "MATCH (a:MeSH),(b:MeSH)"
				+ "WHERE a.name = 'Virus, Zika' AND b.name ='Flaviviridae'"
				+ "CREATE (a)-[r:isChild { name : a.name + '<->' + b.name }]->(b)"
				+ "RETURN r");
		
		zm.sendCypher(""
				+ "MATCH (a:MeSH),(b:MeSH)"
				+ "WHERE a.name = 'Flaviviridae' AND b.name ='RNA Viruses'"
				+ "CREATE (a)-[r:isChild { name : a.name + '<->' + b.name }]->(b)"
				+ "RETURN r");
		
		zm.sendCypher(""
				+ "MATCH (a:MeSH),(b:MeSH)"
				+ "WHERE a.name = 'RNA Viruses' AND b.name ='Viruses'"
				+ "CREATE (a)-[r:isChild { name : a.name + '<->' + b.name }]->(b)"
				+ "RETURN r");
		
		zm.sendCypher(""
				+ "MATCH (a:MeSH),(b:MeSH)"
				+ "WHERE a.name = 'Virus, Zika' AND b.name ='Dengue Virus'"
				+ "CREATE (a)-[r:isSibling { name : a.name + '<->' + b.name }]->(b)"
				+ "RETURN r");
		
		zm.sendCypher(""
				+ "MATCH (a:MeSH),(b:MeSH)"
				+ "WHERE a.name = 'Virus, Zika' AND b.name ='Encephalitis Viruses, Japanese'"
				+ "CREATE (a)-[r:isSibling { name : a.name + '<->' + b.name }]->(b)"
				+ "RETURN r");
		
		zm.sendCypher(""
				+ "MATCH (a:MeSH),(b:MeSH)"
				+ "WHERE a.name = 'Virus, Zika' AND b.name ='Encephalitis Viruses, Tick-Borne'"
				+ "CREATE (a)-[r:isSibling { name : a.name + '<->' + b.name }]->(b)"
				+ "RETURN r");
		
		zm.sendCypher(""
				+ "MATCH (a:MeSH),(b:MeSH)"
				+ "WHERE a.name = 'Virus, Zika' AND b.name ='Yellow fever virus'"
				+ "CREATE (a)-[r:isSibling { name : a.name + '<->' + b.name }]->(b)"
				+ "RETURN r");
		
	}

}
