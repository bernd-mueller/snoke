package de.zbmed.snoke.ontology.icd;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;

public class ICDentity {
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public Set<String> getChildren() {
		return children;
	}

	public void setChildren(Set<String> children) {
		this.children = children;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	String id = "";
	String parent = "";
	Set <String> children = new HashSet<String> ();
	String title = "";

	public void processJSON (JSONObject j) {
		
	}
}
