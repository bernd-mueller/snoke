package de.zbmed.snoke.utility.mongodb;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ImportByPMIDtoMongoDB {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    String REST_URI 
	      = "https://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=pubmed&id=19393038";
	 
	    Client client = ClientBuilder.newClient();
	    
	    WebTarget target = client.target(REST_URI);

        String response = target.path("rest").
                            path("hello").
                            request().
                            accept(MediaType.TEXT_PLAIN).
                            get(Response.class)
                            .toString();
	 
	    System.out.println(response);
	    
	    
	}

}
