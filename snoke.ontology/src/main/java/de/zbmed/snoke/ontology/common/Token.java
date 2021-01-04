package de.zbmed.snoke.ontology.common;

import java.util.HashSet;
import java.util.Set;

public class Token {
	Set <String> synonyms = new HashSet <String> ();
	String canonical;
	String CodeType;
	String CodeValue;
	public Set<String> getSynonyms() {
		return synonyms;
	}
	public void setSynonyms(Set<String> synonyms) {
		this.synonyms = synonyms;
	}
	public String getCanonical() {
		return canonical;
	}
	public void setCanonical(String canonical) {
		this.canonical = canonical;
	}
	public String getCodeType() {
		return CodeType;
	}
	public void setCodeType(String codeType) {
		CodeType = codeType;
	}
	public String getCodeValue() {
		return CodeValue;
	}
	public void setCodeValue(String codeValue) {
		CodeValue = codeValue;
	}

}
