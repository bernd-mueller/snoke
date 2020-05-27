package de.zbmed.tdm.ontology.epso;

import java.util.List;

import gov.nih.nlm.uts.webservice.AtomDTO;
import gov.nih.nlm.uts.webservice.ConceptDTO;
import gov.nih.nlm.uts.webservice.Psf;
import gov.nih.nlm.uts.webservice.UiLabel;
import gov.nih.nlm.uts.webservice.UtsFault_Exception;
import gov.nih.nlm.uts.webservice.UtsWsContentController;
import gov.nih.nlm.uts.webservice.UtsWsContentControllerImplService;
import gov.nih.nlm.uts.webservice.UtsWsFinderController;
import gov.nih.nlm.uts.webservice.UtsWsFinderControllerImplService;
import gov.nih.nlm.uts.webservice.UtsWsMetadataController;
import gov.nih.nlm.uts.webservice.UtsWsMetadataControllerImplService;
import gov.nih.nlm.uts.webservice.UtsWsSecurityController;
import gov.nih.nlm.uts.webservice.UtsWsSecurityControllerImplService;

/**
 * UMLS_Client
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class UMLS_Client {
	private String serviceName = "http://umlsks.nlm.nih.gov";
	String api = "2c0239f6-1943-4aa9-8ee7-b9c4ea2ff52e";
	

	
	private UtsWsContentController utsContentService;
	private UtsWsMetadataController utsMetadataService;
	private UtsWsSecurityController utsSecurityService;
	private UtsWsFinderController utsFinderService;
	
	public UMLS_Client() {
		try {
			utsContentService = (new UtsWsContentControllerImplService()).getUtsWsContentControllerImplPort();
			utsMetadataService = (new UtsWsMetadataControllerImplService()).getUtsWsMetadataControllerImplPort();
			utsSecurityService = (new UtsWsSecurityControllerImplService()).getUtsWsSecurityControllerImplPort();
			utsFinderService = (new UtsWsFinderControllerImplService()).getUtsWsFinderControllerImplPort();
			
			
			
			    
		}

		catch (Exception e) {
			System.out.println("Error!!!" + e.getMessage());
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UMLS_Client umls = new UMLS_Client ();
		
		String ticket;
		

		
	}
	

	
	public ConceptDTO getConcept (String version, String umlscui) {
		try {
			return utsContentService.getConcept(getProxyTicket(), version, umlscui);
		} catch (UtsFault_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public List<AtomDTO> getConceptAtoms (String version, String umlscui) {
		Psf myPsf = new Psf();
		//exclude suppressible + obsolete term matches
		myPsf.setIncludeObsolete(false);
		myPsf.setIncludeSuppressible(false);
		try {
			return utsContentService.getConceptAtoms(getProxyTicket(), version, umlscui, myPsf);
		} catch (UtsFault_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String getAllUMLSVersions () {
		try {
			return utsMetadataService.getAllUMLSVersions(getProxyTicket());
		} catch (UtsFault_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String getCurrentUMLSVersion () {
		try {
			return utsMetadataService.getCurrentUMLSVersion(getProxyTicket());
		} catch (UtsFault_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public int getNumberOfConceptsFromVersion (String version) {
		try {
			return utsContentService.getNewConceptsCount(getProxyTicket(), version);
		} catch (UtsFault_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	

	public void findConcepts () {
		Psf myPsf = new Psf();
		int pageNum = 1;
		//exclude suppressible + obsolete term matches
		myPsf.setIncludeObsolete(false);
		myPsf.setIncludeSuppressible(false);
		List<UiLabel> results;
		String currentUmlsRelease;
		try {
			currentUmlsRelease = utsMetadataService.getCurrentUMLSVersion(getProxyTicket());
		    do {

			myPsf.setPageNum(pageNum);
			results = utsFinderService.findConcepts(getProxyTicket(), currentUmlsRelease, "atom", "aglossia", "words", myPsf);
				    
		        for (UiLabel result:results) {
					
			    String ui = result.getUi();
			    String name = result.getLabel();

			    System.out.println(ui);
			    System.out.println(name);
			}
		        pageNum++;
				
		    } while (results.size() > 0);
		} catch (UtsFault_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		        

		
	}

	private String getProxyTicket() {
		try {
			String ticketGrantingTicket = utsSecurityService.getProxyGrantTicketWithApiKey(api);
			return utsSecurityService.getProxyTicket(ticketGrantingTicket, serviceName);
		} catch (Exception e) {
			return "";
		}
	}

}
