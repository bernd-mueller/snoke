package de.zbmed.snoke.dl.json;

import org.deeplearning4j.text.documentiterator.LabelledDocument;
import org.nd4j.shade.jackson.core.JsonParseException;
import org.nd4j.shade.jackson.databind.JsonNode;
import org.nd4j.shade.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * BioASQDocument
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2019
 */
public class BioASQDocument extends LabelledDocument {
	private static final Logger log = LoggerFactory.getLogger(BioASQDocument.class);
	private String abstractText, journal, meshMajor, pmid, title, year;


	
	public String getAbstractText() {
		return abstractText;
	}



	public void setAbstractText(String abstractText) {
		this.abstractText = abstractText;
	}



	public String getJournal() {
		return journal;
	}



	public void setJournal(String journal) {
		this.journal = journal;
	}



	public String getMeshMajor() {
		return meshMajor;
	}



	public void setMeshMajor(String meshMajor) {
		this.meshMajor = meshMajor;
	}



	public String getPmid() {
		return pmid;
	}



	public void setPmid(String pmid) {
		this.pmid = pmid;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getYear() {
		return year;
	}



	public void setYear(String year) {
		this.year = year;
	}



	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("***** Document *****\n");
		sb.append("pmid="+this.getPmid()+"\n");
		sb.append("title="+this.getTitle()+"\n");
		sb.append("year="+this.getYear()+"\n");
		sb.append("abstractText="+this.getAbstractText()+"\n");
		sb.append("journal="+this.getJournal()+"\n");
		sb.append("meshMajor="+this.getMeshMajor()+"\n");
		sb.append("*****************************");
		
		return sb.toString();
	}
	public BioASQDocument parseJSON(String jsonString) throws JsonParseException, IOException {
		BioASQDocument bod = new BioASQDocument ();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jon = mapper.readTree(jsonString);
		if (jon.get("pmid") != null) {
            bod.setPmid(jon.get("pmid").asText());
        }
        if (jon.get("title") != null) {
            bod.setTitle(jon.get("title").asText());
        }
        if (jon.get("abstractText") != null) {
            bod.setAbstractText(jon.get("abstractText").asText());
        }
        if (jon.get("journal") != null) {
            bod.setJournal(jon.get("journal").asText());
        }
        if (jon.get("year") != null) {
            bod.setYear(jon.get("year").asText());
        }
        if (jon.get("meshMajor") != null) {
            JsonNode mesharray = jon.get("meshMajor");
            List<String> l = new ArrayList<String>();
            if (mesharray.isArray()) {
                for (final JsonNode objNode : mesharray) {
                    l.add(objNode.asText());
                    bod.addLabel(objNode.asText());
                    log.debug ("Adding label: " + objNode.asText());
                }
            }
            bod.setMeshMajor(l.toString());
        }
		// System.out.println("#"+bod.getMeshMajor());
		//log.debug(bod.getLabels().toString());
		//System.out.println("@"+bod);


		return bod;
	}
}
