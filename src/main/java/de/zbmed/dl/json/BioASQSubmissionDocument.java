package de.zbmed.dl.json;

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
 * BioASQSubmissionDocument
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2019
 */
public class BioASQSubmissionDocument extends LabelledDocument {
    private static final Logger log = LoggerFactory.getLogger(BioASQSubmissionDocument.class);
    private String pmid;

    public List<String> getMeshlabels() {
        return meshlabels;
    }

    public void setMeshlabels(List<String> meshlabels) {
        this.meshlabels = meshlabels;
    }

    List <String> meshlabels = new ArrayList<String>();


    public void addMeSHLabel(String meshlabel) {
        if (!meshlabels.contains(meshlabel)) {
            this.meshlabels.add(meshlabel);
        }
    }

    public String getPmid() {
        return pmid;
    }

    public void setPmid(String pmid) {
        this.pmid = pmid;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("***** Document *****\n");
        sb.append("pmid="+this.getPmid()+"\n");
        sb.append("labels="+this.getLabels()+"\n");
        sb.append("*****************************");

        return sb.toString();
    }
    public static BioASQSubmissionDocument parseJSON(String jsonString) throws JsonParseException, IOException {
        BioASQSubmissionDocument bos = new BioASQSubmissionDocument ();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jon = mapper.readTree(jsonString);
        if (jon.get("pmid") != null) {
            bos.setPmid(jon.get("pmid").asText());
        }

        if (jon.get("labels") != null) {
            JsonNode labelarray = jon.get("labels");
            if (labelarray.isArray()) {
                for (final JsonNode objNode : labelarray) {
                    bos.addMeSHLabel(objNode.asText());
                    //log.debug ("Adding label: " + objNode.asText());
                }
            }
        }
        // System.out.println("#"+bod.getMeshMajor());
        //log.debug(bod.getLabels().toString());
        //System.out.println("@"+bod);


        return bos;
    }
}
