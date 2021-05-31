package de.zbmed.snoke.utility.mybib;

import com.sun.jersey.api.client.Client;

public class ISSN {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 String res = Client.create().resource( "https://journaltransfer.issn.org/api?query=rpcn:cox%20AND%20js:%27American%20Society%20for%20Nutrition%27" ).get( String.class ) ;
		 System.out.println(res);
	}

}
