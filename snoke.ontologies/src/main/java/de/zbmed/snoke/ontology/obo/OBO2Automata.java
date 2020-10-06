package de.zbmed.snoke.ontology.obo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.zbmed.snoke.util.SnowballStemmer;

/**
 * OBO2Automata
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class OBO2Automata {


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String [] acronyms= {
				"bfo",
				"chebi",
				"doid",
				"go",
				"pro_reasoned",
				"pato",
				"po",
				"xao",
				"zfa"
				};
		
		OBO2Automata o2a = new OBO2Automata ();
		for (String a : acronyms) {
			o2a.transformOBOintoAutomata(a);
		}
		// o2a.transformOBOintoAutomata("go");
	}
	public void transformOBOintoAutomata (String acronym) {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		
		SnowballStemmer snow = new SnowballStemmer();
		System.out.println("Processing " + acronym);
		
		try {
			builder = builderFactory.newDocumentBuilder();
			Document dict_doc = builder.newDocument();
			Element rootElement = dict_doc.createElement("mwt");
			String namespace = "https://www.zbmed.de/z";
			String prefix = "xmlns:z";
			rootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", prefix, namespace);			
			Element et = dict_doc.createElement("template");			
			Element ezm = dict_doc.createElement("z:" + acronym);
			ezm.setTextContent("%0");
			Attr attr_ids = dict_doc.createAttribute("ids");
			attr_ids.setValue("%1");
			ezm.setAttributeNode(attr_ids);
			et.appendChild(ezm);
			rootElement.appendChild(et);
			dict_doc.appendChild(rootElement);
			
			
			BufferedReader br = new BufferedReader(new FileReader("resources/obo/" + acronym + ".obo"));
			StringBuffer sb = new StringBuffer();
			String line;
		    while ((line = br.readLine()) != null) {
		       // process the line.
		    	sb.append(line + "\n");
		    }
		    
		    String [] blocks = sb.toString().split("\\[Term\\]\n");
		    
		    //int c=0;
		    for (String block : blocks) {
		    	//if (c++>3)break;
		    	String [] blocklines = block.split("\n");
		    	String identifier = "", 
		    			name = "";
		    	Set <String> synonyms = new HashSet<String> ();
		    	boolean hadId = false;
		    	for (String blockline : blocklines) {
		    		if (blockline.length()==0)break;
		    		if (blockline.startsWith("id: ")) {
		    			hadId = true;
		    			identifier = blockline.split(": ")[1];
		    		}
		    		if(hadId) {
			    		if (blockline.startsWith("name: ")) {
			    			name = blockline.split(": ")[1];
			    			synonyms.add(name);
			    			synonyms.add(snow.doTheSnowballStem(name));
			    		} else if (blockline.startsWith("synonym: ")) {
			    			String synonym = blockline.split(": ")[1];
			    			String [] synparts = synonym.split("\"");
			    			int num = 0;
			    			for (String s : synparts) {
			    				String scleaned = s.replaceAll("\\(.*", "");
			    				if (num++==1) {
			    					synonyms.add(scleaned);
			    					synonyms.add(snow.doTheSnowballStem(scleaned));
			    				}
			    			}
			    		}
		    		}
		    	}
		    	
		    	if (hadId) {
					Iterator <String> it = synonyms.iterator();
					while (it.hasNext()) {
						String termsyn = it.next();
						if (termsyn.length()>0) {
							Element variant = dict_doc.createElement("t");
							Attr attr_base = dict_doc.createAttribute("p1");
							attr_base.setValue(identifier);
							variant.setAttributeNode(attr_base);
							variant.setTextContent(termsyn);
							rootElement.appendChild(variant);
						}
					}
		    	}
		    }
		    
		    
		    Element et2 = dict_doc.createElement("template");	
			et2.setTextContent("%0");
			rootElement.appendChild(et2);
			
			Element er = dict_doc.createElement("r");	
			er.setTextContent("&lt;z:[^&gt;]*&gt;(.*&lt;/z)!:[^&gt;]*>");
			rootElement.appendChild(er);
			
			Element er2 = dict_doc.createElement("r");	
			er2.setTextContent("&lt;protname[^&gt;]*&gt;(.*&lt;/protname)![^&gt;]*>");
			rootElement.appendChild(er2);
			
			Element er3 = dict_doc.createElement("r");	
			er3.setTextContent("&lt;/?[A-Za-z_0-9\\-]+(&gt;|[\\r\\n\\t ][^&gt;]+)");
			rootElement.appendChild(er3);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(dict_doc);
			StreamResult result = new StreamResult(new File("resources/obo/" + acronym + ".mwt"));
			transformer.transform(source, result);

			System.out.println(acronym + " is ready!");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
