package de.zbmed.snoke.dl.json;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.nd4j.shade.jackson.core.JsonFactory;
import org.nd4j.shade.jackson.core.JsonParseException;
import org.nd4j.shade.jackson.core.JsonParser;
import org.nd4j.shade.jackson.core.JsonToken;
import org.nd4j.shade.jackson.databind.JsonNode;
import org.nd4j.shade.jackson.databind.ObjectMapper;
import org.nd4j.shade.jackson.databind.ObjectWriter;
import org.nd4j.shade.jackson.databind.node.ArrayNode;
import org.nd4j.shade.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ConvertJSON2MesHFolders
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2019
 */
public class CreateBioASQResult {
    private static final Logger log = LoggerFactory.getLogger(CreateBioASQResult.class);
    public static void main (String [] args) {
        String snokejsonfolder = args[0];
        //File.separator +
        Map <String, List<String>> snoke1 = CreateBioASQResult.readJsonFile(
            snokejsonfolder + File.separator + "snoke1.json"
        );
        Map <String, List<String>> snoke2 = CreateBioASQResult.readJsonFile(
            snokejsonfolder + File.separator + "snoke2.json"
        );
        Map <String, List<String>> snoke3 = CreateBioASQResult.readJsonFile(
            snokejsonfolder + File.separator + "snoke3.json"
        );

        Map <String, List<String>> snoke_pvall = CreateBioASQResult.readJsonFile(
            snokejsonfolder + File.separator + "snoke_pvall.json"
        );
        Map <String, List<String>> finals9all = new HashMap<String, List<String>>();
        Map <String, List<String>> finals9top500 = new HashMap<String, List<String>>();
        Map <String, List<String>> finals9top1000 = new HashMap<String, List<String>>();
        Map <String, List<String>> finals9top5000 = new HashMap<String, List<String>>();
        Map <String, List<String>> finals9top10000 = new HashMap<String, List<String>>();

        ObjectMapper mappertop10000 = new ObjectMapper();
        JsonNode rootnodetop10000 = mappertop10000.createObjectNode();
        ArrayNode rootarraytop10000 = mappertop10000.createArrayNode();

        ObjectMapper mappertop5000 = new ObjectMapper();
        JsonNode rootnodetop5000 = mappertop5000.createObjectNode();
        ArrayNode rootarraytop5000 = mappertop5000.createArrayNode();

        ObjectMapper mappertop1000 = new ObjectMapper();
        JsonNode rootnodetop1000 = mappertop1000.createObjectNode();
        ArrayNode rootarraytop1000 = mappertop1000.createArrayNode();

        ObjectMapper mappertop500 = new ObjectMapper();
        JsonNode rootnodetop500 = mappertop500.createObjectNode();
        ArrayNode rootarraytop500 = mappertop500.createArrayNode();

        ObjectMapper mapperall = new ObjectMapper();
        JsonNode rootnodeall = mapperall.createObjectNode();
        ArrayNode rootarrayall = mapperall.createArrayNode();

        List<String> s1Copy;
        List<String> s2Copy;
        List<String> s3Copy;
        List<String> s4Copy;
        List<String> s5Copy;

        List<String> snoke10000;
        List<String> snoke10000res;
        List<String> snoke5000;
        List<String> snoke5000res;
        List<String> snoke1000;
        List<String> snoke1000res;
        List<String> snoke500;
        List<String> snoke500res;
        List<String> snokeall;
        List<String> snokeallres;

        Set <String> mergeTop10000;
        Set <String> mergeTop5000;
        Set <String> mergeTop1000;
        Set <String> mergeTop500;
        Set <String> mergeAll;
        for (String pmid : snoke1.keySet()) {
            mergeTop10000 = new HashSet<String>();
            mergeTop5000 = new HashSet<String>();
            mergeTop1000 = new HashSet<String>();
            mergeTop500 = new HashSet<String>();
            mergeAll = new HashSet<String>();
            s1Copy = snoke1.get(pmid);
            s2Copy = snoke2.get(pmid);
            s3Copy = snoke3.get(pmid);
            s4Copy = snoke3.get(pmid);
            s5Copy = snoke3.get(pmid);

            snokeall = snoke_pvall.get(pmid);

            // top500
            if (snokeall.size()>500) {
                snoke500 = snokeall.subList(0, 500);
            } else {
                snoke500 = snokeall.subList(0, snokeall.size());
            }

            // top 1000
            if (snokeall.size()>1000) {
                snoke1000 = snokeall.subList(0, 1000);
            } else {
                snoke1000 = snokeall.subList(0, snokeall.size());
            }

            // top5000
            if (snokeall.size()>5000) {
                snoke5000 = snokeall.subList(0, 5000);
            } else {
                snoke5000 = snokeall.subList(0, snokeall.size());
            }

            // top10000
            if (snokeall.size()>10000) {
                snoke10000 = snokeall.subList(0, 10000);
            } else {
                snoke10000 = snokeall.subList(0, snokeall.size());
            }

            for (String s : s1Copy) {
                if (snoke10000.contains(s)) {
                    mergeTop10000.add(s);
                }
                if (snoke5000.contains(s)) {
                    mergeTop5000.add(s);
                }
                if (snoke1000.contains(s)) {
                    mergeTop1000.add(s);
                }
                if (snoke500.contains(s)) {
                    mergeTop500.add(s);
                }
                if (snokeall.contains(s)) {
                    mergeAll.add(s);
                }
            }
            for (String s : s2Copy) {
                if (snoke10000.contains(s)) {
                    mergeTop10000.add(s);
                }
                if (snoke5000.contains(s)) {
                    mergeTop5000.add(s);
                }
                if (snoke1000.contains(s)) {
                    mergeTop1000.add(s);
                }
                if (snoke500.contains(s)) {
                    mergeTop500.add(s);
                }
                if (snokeall.contains(s)) {
                    mergeAll.add(s);
                }
            }
            for (String s : s3Copy) {
                if (snoke10000.contains(s)) {
                    mergeTop10000.add(s);
                }
                if (snoke5000.contains(s)) {
                    mergeTop5000.add(s);
                }
                if (snoke1000.contains(s)) {
                    mergeTop1000.add(s);
                }
                if (snoke500.contains(s)) {
                    mergeTop500.add(s);
                }
                if (snokeall.contains(s)) {
                    mergeAll.add(s);
                }
            }
            for (String s : s4Copy) {
                if (snoke10000.contains(s)) {
                    mergeTop10000.add(s);
                }
                if (snoke5000.contains(s)) {
                    mergeTop5000.add(s);
                }
                if (snoke1000.contains(s)) {
                    mergeTop1000.add(s);
                }
                if (snoke500.contains(s)) {
                    mergeTop500.add(s);
                }
                if (snokeall.contains(s)) {
                    mergeAll.add(s);
                }
            }
            for (String s : s5Copy) {
                if (snoke10000.contains(s)) {
                    mergeTop10000.add(s);
                }
                if (snoke5000.contains(s)) {
                    mergeTop5000.add(s);
                }
                if (snoke1000.contains(s)) {
                    mergeTop1000.add(s);
                }
                if (snoke500.contains(s)) {
                    mergeTop500.add(s);
                }
                if (snokeall.contains(s)) {
                    mergeAll.add(s);
                }
            }

            snoke10000res  = new ArrayList<String>(mergeTop10000);
            snoke5000res = new ArrayList<String>(mergeTop5000);
            snoke1000res = new ArrayList<String>(mergeTop1000);
            snoke500res = new ArrayList<String>(mergeTop500);
            snokeallres = new ArrayList<String>(mergeAll);

            finals9top10000.put(pmid, snoke10000res);
            finals9top5000.put(pmid, snoke5000res);
            finals9top1000.put(pmid, snoke1000res);
            finals9top500.put(pmid, snoke500res);
            finals9all.put(pmid, snokeallres);


            log.info("Processing " + pmid +
                " top10000: " + snoke10000res.size() +
                " top5000: " + snoke5000res.size() +
                " top1000: " + snoke1000res.size() +
                " top500: " + snoke500res.size() +
                " all: " + snokeallres.size() +
                " total: " + snokeall.size()
            );

            // top 10000
            ArrayNode arraytop10000 = mappertop10000.createArrayNode();
            for (String mlabeltop10000 : snoke10000res) {
                arraytop10000.add(mlabeltop10000);
            }
            JsonNode childNodetop10000 = mappertop10000.createObjectNode();
            ((ObjectNode) childNodetop10000).set("labels", arraytop10000);
            ((ObjectNode) childNodetop10000).put("pmid", Integer.parseInt(pmid));
            rootarraytop10000.add(childNodetop10000);

            // top 5000
            ArrayNode arraytop5000 = mappertop5000.createArrayNode();
            for (String mlabeltop5000 : snoke5000res) {
                arraytop5000.add(mlabeltop5000);
            }
            JsonNode childNodetop5000 = mappertop500.createObjectNode();
            ((ObjectNode) childNodetop5000).set("labels", arraytop5000);
            ((ObjectNode) childNodetop5000).put("pmid", Integer.parseInt(pmid));
            rootarraytop5000.add(childNodetop5000);

            // top 1000
            ArrayNode arraytop1000 = mappertop1000.createArrayNode();
            for (String mlabeltop1000 : snoke1000res) {
                arraytop1000.add(mlabeltop1000);
            }
            JsonNode childNodetop1000 = mappertop1000.createObjectNode();
            ((ObjectNode) childNodetop1000).set("labels", arraytop1000);
            ((ObjectNode) childNodetop1000).put("pmid", Integer.parseInt(pmid));
            rootarraytop1000.add(childNodetop1000);

            // top 500
            ArrayNode arraytop500 = mappertop500.createArrayNode();
            for (String mlabeltop500 : snoke500res) {
                arraytop500.add(mlabeltop500);
            }
            JsonNode childNodetop500 = mappertop500.createObjectNode();
            ((ObjectNode) childNodetop500).set("labels", arraytop500);
            ((ObjectNode) childNodetop500).put("pmid", Integer.parseInt(pmid));
            rootarraytop500.add(childNodetop500);

            // all
            ArrayNode arrayall = mapperall.createArrayNode();
            for (String mlabelall : snokeallres) {
                arrayall.add(mlabelall);
            }
            JsonNode childNodeall = mapperall.createObjectNode();
            ((ObjectNode) childNodeall).set("labels", arrayall);
            ((ObjectNode) childNodeall).put("pmid", Integer.parseInt(pmid));
            rootarrayall.add(childNodeall);
        }




        // 500 -> snoke_submission1_pvtop500.json
        try {
            ((ObjectNode) rootnodetop500).set("documents", rootarraytop500);
            // ObjectWriter jwritertop500 = mappertop500.writerWithDefaultPrettyPrinter();
            ObjectWriter jwritertop500 = mappertop500.writer();
            String jsonStringtop500 = jwritertop500.writeValueAsString(rootnodetop500);

            File ftop500 = new File (
                snokejsonfolder + File.separator +"snoke_submission1_pvtop500.json");

            log.debug("Writing snoke_submission1_pvtop500.json");
            BufferedWriter writertop500 = new BufferedWriter(new FileWriter(ftop500));
            writertop500.write(jsonStringtop500+"\n");
            writertop500.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 1000 -> snoke_submission2_pvtop1000.json
        try {
            ((ObjectNode) rootnodetop1000).set("documents", rootarraytop1000);
            // ObjectWriter jwritertop1000 = mappertop1000.writerWithDefaultPrettyPrinter();
            ObjectWriter jwritertop1000 = mappertop1000.writer();
            String jsonStringtop1000 = jwritertop1000.writeValueAsString(rootnodetop1000);

            File ftop1000 = new File (
                snokejsonfolder + File.separator +"snoke_submission2_pvtop1000.json");

            log.debug("Writing snoke_submission1_pvtop1000.json");
            BufferedWriter writertop1000 = new BufferedWriter(new FileWriter(ftop1000));
            writertop1000.write(jsonStringtop1000+"\n");
            writertop1000.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 5000 -> snoke_submission3_pvtop5000.json
        try {
            ((ObjectNode) rootnodetop5000).set("documents", rootarraytop5000);
            // ObjectWriter jwritertop5000 = mappertop5000.writerWithDefaultPrettyPrinter();
            ObjectWriter jwritertop5000 = mappertop5000.writer();
            String jsonStringtop5000 = jwritertop5000.writeValueAsString(rootnodetop5000);

            File ftop5000 = new File (
                snokejsonfolder + File.separator +"snoke_submission3_pvtop5000.json");

            log.debug("Writing snoke_submission1_pvtop5000.json");
            BufferedWriter writertop5000 = new BufferedWriter(new FileWriter(ftop5000));
            writertop5000.write(jsonStringtop5000+"\n");
            writertop5000.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        // 10000 -> snoke_submission4_pvtop10000.json
        try {
            ((ObjectNode) rootnodetop10000).set("documents", rootarraytop10000);
            // ObjectWriter jwritertop10000 = mappertop10000.writerWithDefaultPrettyPrinter();
            ObjectWriter jwritertop10000 = mappertop10000.writer();
            String jsonStringtop10000 = jwritertop10000.writeValueAsString(rootnodetop10000);

            File ftop10000 = new File (
                snokejsonfolder + File.separator +"snoke_submission4_pvtop10000.json");

            log.debug("Writing snoke_submission1_pvtop10000.json");
            BufferedWriter writertop10000 = new BufferedWriter(new FileWriter(ftop10000));
            writertop10000.write(jsonStringtop10000+"\n");
            writertop10000.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // all -> snoke_submission5_pvall.json
        try {
            ((ObjectNode) rootnodeall).set("documents", rootarrayall);
            // ObjectWriter jwriterall = mappertop500.writerWithDefaultPrettyPrinter();
            ObjectWriter jwriterall = mappertop500.writer();
            String jsonStringall = jwriterall.writeValueAsString(rootnodeall);
            File fall = new File (
                snokejsonfolder + File.separator +"snoke_submission5_pvall.json");

            log.debug("Writing snoke_submission1_pvall.json");
            BufferedWriter writerall = new BufferedWriter(new FileWriter(fall));
            writerall.write(jsonStringall+"\n");
            writerall.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Map <String, List<String>> readJsonFile (String filename) {
        Map<String, List<String>> resmap = new HashMap<String, List<String>>();
        try {
            InputStream fis = new FileInputStream(
                filename
            );

            boolean nextTokenPmid = false;
            // boolean nextLabelArray = false;
            String curpmid = "";
            List<String> curLabels = new ArrayList<String>();
            JsonParser jsonParser = new JsonFactory().createParser(fis);
            while(!jsonParser.isClosed()){
                JsonToken jsonToken = jsonParser.nextToken();
                if (jsonParser.getText() != null && jsonParser.getText().equals("pmid")) {
                    nextTokenPmid = true;
                } else {
                    if (nextTokenPmid) {
                        curpmid = jsonParser.getText();
                        nextTokenPmid = false;
                        resmap.put(curpmid, curLabels);
                        //log.info("Saving pmid " + curpmid + ": " + curLabels);
                        curpmid = "";
                        curLabels = new ArrayList<String>();
                        //if (resmap.size()>10)break;
                    }
                }

                if (jsonToken != null && jsonToken.name().equals("START_ARRAY")) {
                    // nextLabelArray = true;
                } else if (jsonToken != null && jsonToken.name().equals("VALUE_STRING")) {
                    curLabels.add(jsonParser.getText());
                }

/*                System.out.println ("Text " + jsonParser.getText());
                System.out.println("id: " + jsonToken.id());
                System.out.println("String: " + jsonToken.asString());
                System.out.println("name: " + jsonToken.name());
                System.out.println("isend: " + jsonToken.isStructEnd());
                System.out.println("isstart: " + jsonToken.isStructStart());*/
            }


            /*
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            log.info("Reading File line by line using BufferedReader from " + filename);

            String line = reader.readLine();
            int counter = 0;
            while(line != null){
                line = reader.readLine();
                if (line!= null) {
                    log.info("#" + counter++);
                    if (counter == 100) break;
                    if (line.length() > 2) {

                        JsonParser jsonParser = new JsonFactory().createParser(line);
                        BioASQSubmissionDocument bos = new BioASQSubmissionDocument();
                        bos = BioASQSubmissionDocument.parseJSON(line);
                        resmap.put(bos.getPmid(), bos.getMeshlabels());
                    } else {
                        log.info("Line length smaller than 2: " + line);
                    }

                }
            }*/
            log.info("Done reading " + resmap.size() + " documents from " + filename);



        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



        return resmap;
    }
}
