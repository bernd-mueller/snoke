package de.zbmed.tdm.neo4j.importer;

import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;

/**
 * TransactionWorkZBMED
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class TransactionWorkZBMED implements TransactionWork {
	private String query;
	
	TransactionWorkZBMED () {
		super ();
		query = "";
	}
	
	public String getQuery() {
		return query;
	}

	public void setQuery (String query) {
		this.query = query;
	}

	 @Override
     public String execute( Transaction tx )
     {
         StatementResult result = tx.run(query);
         return result.single().get( 0 ).asString();
     }

}
