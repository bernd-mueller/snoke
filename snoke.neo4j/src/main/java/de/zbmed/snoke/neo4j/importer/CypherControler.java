package de.zbmed.snoke.neo4j.importer;

import org.neo4j.driver.v1.AuthTokens;

import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;
import static org.neo4j.driver.v1.Values.parameters;

/**
 * CypherControler
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class CypherControler implements AutoCloseable {
    private final Driver driver;
    private final Session session;
    private final String nodeType;
    
    private String 
    			bolt = "bolt://134.95.56.146:7687",
    			user = "test",
    			password = "test";
    
    String query = "CREATE (n:Person { name: 'Bernd', title: 'Developer' })" ;

    public CypherControler(String nodeType)
    {
    	this.nodeType= nodeType;
        driver = GraphDatabase.driver( bolt, AuthTokens.basic( user, password ) );
        session = driver.session();
    }

    @Override
    public void close() throws Exception
    {
    	session.close();
        driver.close();
    }

    public void sendCypher( final String query ) {
        String greeting = session.writeTransaction( new TransactionWork<String>()
            {
                @Override
                public String execute( Transaction tx )
                {
                    StatementResult result = tx.run( query);
                    return result.toString();
                }
            } );
    
         
    }
    
	public void createNode (String conceptId, String nodeName, String treeId) {
		

		this.sendCypher("CREATE (n:"+nodeType+" {name: '"+nodeName+"', conceptid: '"+conceptId+"', treeid: '"+treeId+"'})");
	}
	
	public void createNodeOrUpdate (String conceptId, String nodeName, String treeId) {
		if (nodeName != null && nodeName.length()==0) {
			nodeName = treeId;
		}
		if (conceptId != null && conceptId.length()==0) {
			conceptId = treeId;
		}
		
		nodeName = nodeName.replaceAll("'", "");
		treeId = treeId.replaceAll("'", "");
		conceptId = conceptId.replaceAll("'", "");
			this.sendCypher("MERGE (n:"+nodeType+" {treeid: '"+treeId+"'})"
					+ "ON CREATE SET n.name = '"+nodeName+"', n.conceptid = '"+conceptId+"', n.treeid = '"+treeId+"'"
					+ "ON MATCH SET n.name = '"+nodeName+"', n.conceptid = '"+conceptId+"'"
					+ "RETURN n.name, n.treeid");
//		} else {
//			this.sendCypher("MERGE (n:"+nodeType+" {treeid: '"+treeId+"'})"
//					+ "ON CREATE SET n.treeid = '"+treeId+"', n.asd = 'qwe'"
//					+ "ON MATCH SET n.treeid = '"+treeId+"' n.asd = 'asd'"
//					+ "RETURN n.name, n.treeid");
//		}

	}
	

	
	public void createRelation (String nodetreeidA, String nodetreeidB, String relationName) {
		this.sendCypher(""
				+ "MATCH (a:" + nodeType + "),(b:" + nodeType + ")"
				+ "WHERE a.treeid = '"+nodetreeidA+"' AND b.treeid ='"+nodetreeidB+"'"
				+ "CREATE UNIQUE (a)-[r:"+relationName+"]->(b)"
				+ "RETURN r");
	}
	

	public void clearAllNodesFromDatabase () {
		this.sendCypher("MATCH (n:" + nodeType + ") DETACH DELETE n");
	}
	
    public static void main( String... args ) throws Exception
    {
        try ( CypherControler cc = new CypherControler("test") )
        {
        	// 
            cc.sendCypher(cc.query);
        }
        
        
    }
}