package de.zbmed.snoke.ontology.icd;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class ICDentity {
	String id = "";
	String parent = "";
	String title = "";
	Set <String> children = new HashSet<String> ();
	Set <String> synonyms = new HashSet<String> ();
	
	public Set<String> getSynonyms() {
		return synonyms;
	}

	public void setSynonyms(Set<String> synonyms) {
		this.synonyms = synonyms;
	}



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
	
	public void processJSON (String jsonstring) {
		JSONTokener tokener = new JSONTokener(jsonstring);
		JSONObject root = new JSONObject(tokener);
		JSONTokener titletokener = new JSONTokener(root.get("title").toString());
		JSONObject tj = new JSONObject(titletokener);		
		this.setTitle(tj.getString("@value"));
		if (root.has("child")) {
			JSONArray children = (JSONArray)root.get("child");
			for (int i=0; i<children.length(); i++) {
				this.getChildren().add(children.getString(i).replace("http://id.who.int/icd/entity/", ""));
			}
		}
		if (root.has("synonym")) {
			JSONArray synonyms = (JSONArray)root.get("synonym");
			for (int i=0; i<synonyms.length(); i++) {
				JSONTokener labletokener = new JSONTokener(synonyms.get(i).toString());
				JSONObject lj = new JSONObject(labletokener);	
				JSONTokener valtokener = new JSONTokener(lj.get("label").toString());
				JSONObject vj = new JSONObject(valtokener);
				String syn = vj.get("@value").toString();
				this.getSynonyms().add(syn);
			}
		}
	}
	
	public String toString () {
		String res = "";
		res += "ID:\t" + this.getId() + "\n";
		res += "Title:\t" + this.getTitle() + "\n";
		res += "Synonyms:\t" + "\n";
		for (String s : this.getSynonyms()) {
			res += "\t" + s + "\n";
		}
		res += "Children:\t" + "\n";
		for (String s : this.getChildren()) {
			res += "\t" + s + "\n";
		}
		return res;
	}
}
