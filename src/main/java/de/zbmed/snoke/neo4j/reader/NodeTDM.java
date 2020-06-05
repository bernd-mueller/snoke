package de.zbmed.snoke.neo4j.reader;

/**
 * NodeTDM
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class NodeTDM {
	int id;
	String treeid;
	String name;
	String conceptid;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTreeid() {
		return treeid;
	}
	public void setTreeid(String treeid) {
		this.treeid = treeid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getConceptid() {
		return conceptid;
	}
	public void setConceptid(String conceptid) {
		this.conceptid = conceptid;
	}

}
