package de.zbmed.tdm.lod.mesh;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.zbmed.tdm.util.SnowballStemmer;
/**
 * XMLTest
 *
 * @author Alexandra Hagelstein
 * @version 0.1
 * @since 2016
 */
public class XMLTest {

	public static void main(String[] args) {
			// TODO Auto-generated method stub
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = null;
			
			SnowballStemmer snow = new SnowballStemmer ();
			
			
			try {
				builder = builderFactory.newDocumentBuilder();
				builder.setEntityResolver(new DTDEntityResolver());
				
				Document dict_doc = builder.newDocument();
				Element rootElement = dict_doc.createElement("mwt");
				String namespace = "https://www.zbmed.de/z";
				String prefix = "xmlns:z";
				rootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", prefix, namespace);			
				Element et = dict_doc.createElement("template");			
				Element ezm = dict_doc.createElement("z:MeSH");
				ezm.setTextContent("%0");
				Attr attr_ids = dict_doc.createAttribute("ids");
				attr_ids.setValue("%1");
				ezm.setAttributeNode(attr_ids);
				et.appendChild(ezm);
				rootElement.appendChild(et);
				dict_doc.appendChild(rootElement);
				
				
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				DOMSource source = new DOMSource(dict_doc);
				StreamResult result = new StreamResult(new File("resources/mesh/MeSH.mwt"));
				

				// Output to console for testing
				// StreamResult result = new StreamResult(System.out);

				transformer.transform(source, result);

				System.out.println("File saved!");

			} catch (ParserConfigurationException e) {
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
