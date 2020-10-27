package de.zbmed.snoke.ontology.drugnames;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
/**
 * DrugBankIdMapper
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class DrugNameIdMapper {
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = builderFactory.newDocumentBuilder();

			Document document = builder.parse(new FileInputStream(
					"D:\\eclipse-workspace\\TDM\\resources\\drugbank\\drugbank.xml"));
			// new FileInputStream("/home/muellerb/test.xml"));

			XPath xPath = XPathFactory.newInstance().newXPath();

			String expression = "/drugbank//drug";

			PrintWriter writer = new PrintWriter("D:\\eclipse-workspace\\TDM\\resources\\drugbank\\results\\DrugBankIdName.map", "UTF-8");

			// read a string value
			// String email = xPath.compile(expression).evaluate(document);
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);
			String name = "";
			String dbid = "";
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node n = nodeList.item(i);
				NodeList nl = n.getChildNodes();
				for (int j = 0; j < nl.getLength(); j++) {
					Node subn = nl.item(j);
					// System.out.println(subn.getNodeName());
					if (subn.getNodeName().equals("drugbank-id")) {
						NamedNodeMap nnm = subn.getAttributes();
						for (int k = 0; k < nnm.getLength(); k++) {
							String at = nnm.item(k).getNodeName();
							if (at.equals("primary")) {
								dbid = subn.getTextContent();
							}
						}

					} else if (subn.getNodeName().equals("name")) {
						name = subn.getTextContent();
					}
				}
				writer.println(dbid + "\t" + name);
			}
			System.out.println(dbid + "\t" + name);
			writer.close();

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
