package de.zbmed.tdm.ontology.epso;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import BeanService.RxPropertyConcept;
import gov.nih.nlm.mor.axis.services.RxNormDBService.DBManager;
import gov.nih.nlm.mor.axis.services.RxNormDBService.DBManagerService;
import gov.nih.nlm.mor.axis.services.RxNormDBService.DBManagerServiceLocator;
/**
 * RxNormClient
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class RxNormClient {
	String rxhost = "http://mor.nlm.nih.gov";
	String rxURI = rxhost + "/axis/services/RxNormDBService";

	// Locate the RxNorm API web service
	URL rxURL;
	DBManagerService rxnormService;
	DBManager dbmanager;
	
	RxNormClient () {
		try {
			rxURL = new URL(rxURI);
			rxnormService = new DBManagerServiceLocator();
			dbmanager = rxnormService.getRxNormDBService(rxURL);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RxNormClient rnc = new RxNormClient ();
		rnc.getAllPropertiesByIdentifier("218304");

		System.out.println(rnc.getUMLS("218304"));
		System.out.println(rnc.getMeSH("218304"));
	}
	
	public String getUMLS (String rxcui) {
		String umlscui = "";
		RxPropertyConcept[] rxconcept;
		String [] props = {"UMLSCUI"};
		try {
			rxconcept = dbmanager.getRxProperty(rxcui, props[0]);
			for (RxPropertyConcept r : rxconcept) {
				// System.out.println(r.getNAME() + "\t" + r.getVALUE());
				umlscui = r.getVALUE();
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return umlscui;
	}
	
	public String getMeSH (String rxcui) {
		String mesh = "";
		RxPropertyConcept[] rxconcept;
		String [] props = {"MESH"};
		try {
			rxconcept = dbmanager.getRxProperty(rxcui, props[0]);
			for (RxPropertyConcept r : rxconcept) {
				// System.out.println(r.getNAME() + "\t" + r.getVALUE());
				mesh = r.getVALUE();
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mesh;
	}
	
	public void getAllPropertiesByIdentifier (String rxcui) {
		RxPropertyConcept[] rxconcept;
		String [] props = {"ALL"};
		try {
			rxconcept = dbmanager.getAllProperties(rxcui, props);
			for (RxPropertyConcept r : rxconcept) {
				System.out.println(r.getNAME() + "\t" + r.getVALUE());	
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getIdentifierForConceptName (String conceptname) {
		// Get the concept unique identifier for a string
		String[] rxcuis;
		try {
			rxcuis = dbmanager.findRxcuiByString(conceptname);
			// print results
			for (int j = 0; j < rxcuis.length; j++)
			    System.out.println("RXCUI = " + rxcuis[j]);
			if (rxcuis.length == 0) 
			    System.out.println("No concept found");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
