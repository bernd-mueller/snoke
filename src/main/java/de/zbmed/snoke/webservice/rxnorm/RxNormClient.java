package de.zbmed.snoke.webservice.rxnorm;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import BeanService.*;
import gov.nih.nlm.mor.axis.services.RxNormDBService.*;
/**
 * RxNormClient
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class RxNormClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String rxhost = "http://mor.nlm.nih.gov";
		String rxURI = rxhost + "/axis/services/RxNormDBService";

		// Locate the RxNorm API web service
		URL rxURL;
		try {
			rxURL = new URL(rxURI);
			DBManagerService rxnormService = new DBManagerServiceLocator();
			DBManager dbmanager = rxnormService.getRxNormDBService(rxURL);
			
			// Get the concept unique identifier for a string
			String [] rxcuis = dbmanager.findRxcuiByString("aspirin");
			// print results
			for (int j = 0; j < rxcuis.length; j++)
			    System.out.println("RXCUI = " + rxcuis[j]);
			if (rxcuis.length == 0) 
			    System.out.println("No concept found");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
