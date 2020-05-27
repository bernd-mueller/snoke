package de.zbmed.tdm.neo4j.showcase;

import java.util.List;
import java.util.Properties;

import iot.jcypher.database.DBAccessFactory;
import iot.jcypher.database.DBProperties;
import iot.jcypher.database.DBType;
import iot.jcypher.database.DBVersion;
import iot.jcypher.database.IDBAccess;
import iot.jcypher.graph.GrNode;
import iot.jcypher.query.JcQuery;
import iot.jcypher.query.JcQueryResult;
import iot.jcypher.query.api.IClause;
import iot.jcypher.query.factories.clause.MATCH;
import iot.jcypher.query.factories.clause.RETURN;
import iot.jcypher.query.values.JcNode;
/**
 * ZikaShowCase
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class ZikaShowCase {
	private static String SERVER_ROOT_URI = "http://134.95.56.146:7474/db/data/";

	
	Properties props = new Properties();

	
	/*** additional properties for in memory and embedded db access ********/
	/*** an excerpt from class DBProperties ********************************/
	/** OPTIONAL  e.g. "512M" */
	public static final String PAGECACHE_MEMORY = "pagecache_memory";
	/** OPTIONAL  e.g. "60" */
	public static final String STRING_BLOCK_SIZE = "string_block_size";
	/** OPTIONAL  e.g. "300" */
	public static final String ARRAY_BLOCK_SIZE = "array_block_size";

	IDBAccess remote;
	IDBAccess embedded;
	IDBAccess inMemory;
	public ZikaShowCase () {
		props.setProperty(DBProperties.SERVER_ROOT_URI, "http://localhost:7474");
		// create an IDBAccess instance 
		DBVersion.Neo4j_Version = "2.3.2"; 
		remote =
		   DBAccessFactory.createDBAccess(DBType.REMOTE, props);
	}
	



	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ZikaShowCase zika = new ZikaShowCase ();

		JcNode actor = new JcNode("actor"); 
		JcQuery query = new JcQuery(); 
		query.setClauses(new IClause[] { 
		   MATCH.node(actor).relation().out().type("ACTS_IN").node(), 
		   RETURN.value(actor) 
		}); 
		JcQueryResult result = zika.remote.execute(query); 
		List<GrNode> actors = result.resultOf(actor);
	}
	

}
