package de.zbmed.snoke.utility.mybib;

// norm.Sign.	Signatur	Verlag	ISSN	Titel	Autor	Artikel-Titel - ggfs mehrere	Artikel-Autor - ggfs mehrere	Seiten	Usergroup	PLZ

public class Zeitschrift {
	String normsig = "";
	String sig = "";
	String Verlag = "";
	String issn = "";
	String titel = "";
	String autor = "";
	String artitel = "";
	String artautor = "";
	String seiten = "";
	String usergroup = "";
	String plz = "";
	Integer count = 0; 
	

	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getNormsig() {
		return normsig;
	}
	public void setNormsig(String normsig) {
		this.normsig = normsig;
	}
	public String getSig() {
		return sig;
	}
	public void setSig(String sig) {
		this.sig = sig;
	}
	public String getVerlag() {
		return Verlag;
	}
	public void setVerlag(String verlag) {
		Verlag = verlag;
	}
	public String getIssn() {
		return issn;
	}
	public void setIssn(String issn) {
		this.issn = issn;
	}
	public String getTitel() {
		return titel;
	}
	public void setTitel(String titel) {
		this.titel = titel;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public String getArtitel() {
		return artitel;
	}
	public void setArtitel(String artitel) {
		this.artitel = artitel;
	}
	public String getArtautor() {
		return artautor;
	}
	public void setArtautor(String artautor) {
		this.artautor = artautor;
	}
	public String getSeiten() {
		return seiten;
	}
	public void setSeiten(String seiten) {
		this.seiten = seiten;
	}
	public String getUsergroup() {
		return usergroup;
	}
	public void setUsergroup(String usergroup) {
		this.usergroup = usergroup;
	}
	public String getPlz() {
		return plz;
	}
	public void setPlz(String plz) {
		this.plz = plz;
	}
}
