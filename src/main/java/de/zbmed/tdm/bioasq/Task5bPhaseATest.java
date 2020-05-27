package de.zbmed.tdm.bioasq;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Task5bPhaseATest
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class Task5bPhaseATest {

	public static Integer TYPE_YESNO = 1;
	public static Integer TYPE_FACTOID = 2;
	public static Integer TYPE_LIST = 3;
	public static Integer TYPE_SUMMARY = 4;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		URI uri = new File("C:\\Users\\muellerb\\Desktop\\BioASQ-task5bPhaseA-testset1.json").toURI();
		JSONTokener tokener;
		
		try {
			tokener = new JSONTokener(uri.toURL().openStream());
			JSONObject root = new JSONObject(tokener);
			// System.out.println(root.get("questions").getClass());
			JSONArray ja = (JSONArray) root.get("questions");
			
			for (int i=0; i<ja.length(); i++) {
				 JSONObject item = ja.getJSONObject(i);
				 String question = item.getString("body");
				 String type = item.getString("type");
				 String id = item.getString("id");
				 
				 System.out.println(question);
				 System.out.println(type);
				 System.out.println(id);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
