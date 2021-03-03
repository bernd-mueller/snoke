package de.zbmed.snoke.ontology.analysis;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for calculating dictionary statistics comprising concepts, synonyms, documents, etc. The result
 * ist written into tex-format.
 * 
 * @author Muellerb
 * @since 2021
 */
public class DictStats {
	private static final Logger log = LoggerFactory.getLogger(DictStats.class);
	DictLoader dl;
	
	DictStats () {
		dl = new DictLoader ();
	}
	
	/**
	 * Get instance of DictLoader
	 * 
	 * @return DictLoader
	 */
	public DictLoader getDl() {
		return dl;
	}
	
	/**
	 * Set instance of DictLoader
	 * 
	 * @param dl DictLoader
	 */
	public void setDl(DictLoader dl) {
		this.dl = dl;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DictStats ds = new DictStats ();
		Map <String, Set <String>> mepso = ds.getDl().getMapForDict("dictionaries/Dict_EpSO.xml");
		Map <String, Set <String>> messo = ds.getDl().getMapForDict("dictionaries/Dict_ESSO.xml");
		Map <String, Set <String>> mepilont = ds.getDl().getMapForDict("dictionaries/Dict_EPILONT.xml");
		Map <String, Set <String>> mepisem = ds.getDl().getMapForDict("dictionaries/Dict_EPISEM.xml");
		Map <String, Set <String>> mfenics = ds.getDl().getMapForDict("dictionaries/Dict_FENICS.xml");
		Map <String, Set <String>> mdrugnames = ds.getDl().getMapForDict("dictionaries/Dict_DrugNames.xml");
		
		int epsolabels = mepso.keySet().size();
		int epsouniqsyn = ds.getDl().createSynSetFromMap(mepso).size();
		int epsototal = ds.getDl().countSynonyms(mepso);
		
		log.info("EpSO Labels: " + epsolabels);
		log.info("EpSO U Syns: " + epsouniqsyn);
		log.info("EpSO T Syns: " + epsototal);
		
		int essolabels = messo.keySet().size();
		int essouniqsyn = ds.getDl().createSynSetFromMap(messo).size();
		int essototal = ds.getDl().countSynonyms(messo);
		
		log.info("ESSO Labels: " + essolabels);
		log.info("ESSO U Syns: " + essouniqsyn);
		log.info("ESSO T Syns: " + essototal);
		
		int epilontlabels = mepilont.keySet().size();
		int epilontuniqsyn = ds.getDl().createSynSetFromMap(mepilont).size();
		int epilonttotal = ds.getDl().countSynonyms(mepilont);
		
		log.info("EPILONT Labels: " + epilontlabels);
		log.info("EPILONT U Syns: " + epilontuniqsyn);
		log.info("EPILONT T Syns: " + epilonttotal);
		
		int episemlabels = mepisem.keySet().size();
		int episemuniqsyn = ds.getDl().createSynSetFromMap(mepisem).size();
		int episemtotal = ds.getDl().countSynonyms(mepisem);
		
		log.info("EPISEM Labels: " + episemlabels);
		log.info("EPISEM U Syns: " + episemuniqsyn);
		log.info("EPISEM T Syns: " + episemtotal);
		
		int fenicslabels = mfenics.keySet().size();
		int fenicsuniqsyn = ds.getDl().createSynSetFromMap(mfenics).size();
		int fenicstotal = ds.getDl().countSynonyms(mfenics);
		
		log.info("FENICS Labels: " + fenicslabels);
		log.info("FENICS U Syns: " + fenicsuniqsyn);
		log.info("FENICS T Syns: " + fenicstotal);
		
		int drugslabels = mdrugnames.keySet().size();
		int drugsuniqsyn = ds.getDl().createSynSetFromMap(mdrugnames).size();
		int drugstotal = ds.getDl().countSynonyms(mdrugnames);
		
		log.info("Drugs Labels: " + drugslabels);
		log.info("Drugs U Syns: " + drugsuniqsyn);
		log.info("Drugs T Syns: " + drugstotal);
		
		Set <String> depso 		= ds.getDl().readPmidsFromFile("resources/pmids/EpSO.txt");
		Set <String> desso 		= ds.getDl().readPmidsFromFile("resources/pmids/ESSO.txt");
		Set <String> depilont	= ds.getDl().readPmidsFromFile("resources/pmids/EPILONT.txt");
		Set <String> depisem 	= ds.getDl().readPmidsFromFile("resources/pmids/EPISEM.txt");
		Set <String> dfenics	= ds.getDl().readPmidsFromFile("resources/pmids/FENICS.txt");
		Set <String> ddrugs	= ds.getDl().readPmidsFromFile("resources/pmids/DrugNames.txt");
		
		String numberformat = "%,6f";
		String integerformat = "%,d";
		String percentageformat = "%d,2%%";
		
		String sepsolabels = String.format(Locale.US, integerformat, epsolabels);
		String sessolabels = String.format(Locale.US, integerformat, essolabels);
		String sepilontlabels = String.format(Locale.US, integerformat, epilontlabels);
		String sepisemlabels = String.format(Locale.US, integerformat, episemlabels);
		String sfenicslabels = String.format(Locale.US, integerformat, fenicslabels);
		String sdrugslabels = String.format(Locale.US, integerformat, drugslabels);
		
		String sepsouniqsyn = String.format(Locale.US, integerformat, epsouniqsyn);
		String sessouniqsyn = String.format(Locale.US, integerformat, essouniqsyn);
		String sepilontuniqsyn = String.format(Locale.US, integerformat, epilontuniqsyn);
		String sepisemuniqsyn = String.format(Locale.US, integerformat, episemuniqsyn);
		String sfenicsuniqsyn = String.format(Locale.US, integerformat, fenicsuniqsyn);
		String sdrugsuniqsyn = String.format(Locale.US, integerformat, drugsuniqsyn);
		
		String sepsototal = String.format(Locale.US, integerformat, epsototal);
		String sessototal = String.format(Locale.US, integerformat, essototal);
		String sepilonttotal = String.format(Locale.US, integerformat, epilonttotal);
		String sepisemtotal = String.format(Locale.US, integerformat, episemtotal);
		String sfenicstotal = String.format(Locale.US, integerformat, fenicstotal);
		String sdrugstotal = String.format(Locale.US, integerformat, drugstotal);
		
		String avepso = 	 String.format(Locale.US, numberformat, Double.valueOf(ds.getDl().averageConceptPerSynonym(mepso)));
		String avesso = 	String.format(Locale.US, numberformat, Double.valueOf(ds.getDl().averageConceptPerSynonym(messo)));
		String avepilont = 	String.format(Locale.US, numberformat, Double.valueOf(ds.getDl().averageConceptPerSynonym(mepilont)));
		String avepisem = 	String.format(Locale.US, numberformat, Double.valueOf(ds.getDl().averageConceptPerSynonym(mepisem)));
		String avfenics = 	String.format(Locale.US, numberformat, Double.valueOf(ds.getDl().averageConceptPerSynonym(mfenics)));
		String avdrugs = 	String.format(Locale.US, numberformat, Double.valueOf(ds.getDl().averageConceptPerSynonym(mdrugnames)));
		
		int dbepso = depso.size();
		int dbesso = desso.size();
		int dbepilont = depilont.size();
		int dbepisem = depisem.size();
		int dbfenics = dfenics.size();
		int dbdrugs = ddrugs.size();
		
		String sdbepso =  String.format(Locale.US, integerformat, dbepso);
		String sdbesso =  String.format(Locale.US, integerformat, dbesso);
		String sdbepilont =  String.format(Locale.US, integerformat, dbepilont);
		String sdbepisem =  String.format(Locale.US, integerformat, dbepisem);
		String sdbfenics =  String.format(Locale.US, integerformat, dbfenics);
		String sdbdrugs =  String.format(Locale.US, integerformat, dbdrugs);
		
		Set <String> dcepso = new HashSet <String> (depso);
		dcepso.retainAll(ddrugs);
		Set <String> dcesso = new HashSet <String> (desso);
		dcesso.retainAll(ddrugs);
		Set <String> dcepilont = new HashSet <String> (depilont);
		dcepilont.retainAll(ddrugs);
		Set <String> dcepisem = new HashSet <String> (depisem);
		dcepisem.retainAll(ddrugs);
		Set <String> dcfenics = new HashSet <String> (dfenics);
		dcfenics.retainAll(ddrugs);		
		
		int sdcepso = dcepso.size(); 
		int sdcesso = dcesso.size();
		int sdcepilont = dcepilont.size();
		int sdcepisem = dcepisem.size();
		int sdcfenics = dcfenics.size();
		
		String ssdcepso =  String.format(Locale.US, integerformat, sdcepso);
		String ssdcesso =  String.format(Locale.US, integerformat, sdcesso);
		String ssdcepilont =  String.format(Locale.US, integerformat, sdcepilont);
		String ssdcepisem =  String.format(Locale.US, integerformat, sdcepisem);
		String ssdcfenics =  String.format(Locale.US, integerformat, sdcfenics);
		
		
		String ldepso = String.format(Locale.US, numberformat, (Double.valueOf(epsolabels) / Double.valueOf(dbepso)));
		String ldesso = String.format(Locale.US, numberformat, (Double.valueOf(essolabels) / Double.valueOf(dbesso)));
		String ldepilont = String.format(Locale.US, numberformat, (Double.valueOf(epilontlabels) / Double.valueOf(dbepilont)));
		String ldepisem = String.format(Locale.US, numberformat, (Double.valueOf(episemlabels) / Double.valueOf(dbepisem)));
		String ldfenics = String.format(Locale.US, numberformat, (Double.valueOf(fenicslabels) / Double.valueOf(dbfenics)));
		
		String lcdepso = String.format(Locale.US, numberformat, (Double.valueOf(epsolabels) / Double.valueOf(sdcepso)));
		String lcdesso = String.format(Locale.US, numberformat, (Double.valueOf(essolabels) / Double.valueOf(sdcesso)));
		String lcdepilont = String.format(Locale.US, numberformat, (Double.valueOf(epilontlabels) / Double.valueOf(sdcepilont)));
		String lcdepisem = String.format(Locale.US, numberformat, (Double.valueOf(episemlabels) / Double.valueOf(sdcepisem)));
		String lcdfenics = String.format(Locale.US, numberformat, (Double.valueOf(fenicslabels) / Double.valueOf(sdcfenics)));
		
		String lcdperldepso = forPercentages ((Double.valueOf(epsolabels) / Double.valueOf(dbepso)) / (Double.valueOf(epsolabels) / Double.valueOf(sdcepso)));
		String lcdperldesso = forPercentages ((Double.valueOf(essolabels) / Double.valueOf(dbesso)) / (Double.valueOf(essolabels) / Double.valueOf(sdcesso)));
		String lcdperldepilont = forPercentages ((Double.valueOf(epilontlabels) / Double.valueOf(dbepilont)) / (Double.valueOf(epilontlabels) / Double.valueOf(sdcepilont)));
		String lcdperldepisem = forPercentages ((Double.valueOf(episemlabels) / Double.valueOf(dbepisem)) / (Double.valueOf(episemlabels) / Double.valueOf(sdcepisem)));
		String lcdperldfenics = forPercentages ((Double.valueOf(fenicslabels) / Double.valueOf(dbfenics)) / (Double.valueOf(fenicslabels) / Double.valueOf(sdcfenics)));
		
		String sdepso =String.format(Locale.US, numberformat,  (Double.valueOf(epsouniqsyn) / Double.valueOf(dbepso)));
		String sdesso = String.format(Locale.US, numberformat,  (Double.valueOf(essouniqsyn) / Double.valueOf(dbesso)));
		String sdepilont = String.format(Locale.US, numberformat,  (Double.valueOf(epilontuniqsyn) / Double.valueOf(dbepilont)));
		String sdepisem = String.format(Locale.US, numberformat,  (Double.valueOf(episemuniqsyn) / Double.valueOf(dbepisem)));
		String sdfenics = String.format(Locale.US, numberformat,  (Double.valueOf(fenicsuniqsyn) / Double.valueOf(dbfenics)));
		
		String scdepso = String.format(Locale.US, numberformat, (Double.valueOf(epsouniqsyn) / Double.valueOf(sdcepso)));
		String scdesso = String.format(Locale.US, numberformat, (Double.valueOf(essouniqsyn) / Double.valueOf(sdcesso)));
		String scdepilont = String.format(Locale.US, numberformat, (Double.valueOf(epilontuniqsyn) / Double.valueOf(sdcepilont)));
		String scdepisem = String.format(Locale.US, numberformat, (Double.valueOf(episemuniqsyn) / Double.valueOf(sdcepisem)));
		String scdfenics = String.format(Locale.US, numberformat,  (Double.valueOf(fenicsuniqsyn) / Double.valueOf(sdcfenics)));
		
		String scdpersdepso = 		forPercentages ((Double.valueOf(epsouniqsyn) / Double.valueOf(dbepso)) / (Double.valueOf(epsouniqsyn) / Double.valueOf(sdcepso)));
		String scdpersdesso = 		forPercentages ((Double.valueOf(essouniqsyn) / Double.valueOf(dbesso)) / (Double.valueOf(essouniqsyn) / Double.valueOf(sdcesso)));
		String scdpersdepilont = 	forPercentages ((Double.valueOf(epilontuniqsyn) / Double.valueOf(dbepilont)) / (Double.valueOf(epilontuniqsyn) / Double.valueOf(sdcepilont)));
		String scdpersdepisem = 	forPercentages ((Double.valueOf(episemuniqsyn) / Double.valueOf(dbepisem)) / (Double.valueOf(episemuniqsyn) / Double.valueOf(sdcepisem)));
		String scdpersdfenics = 	forPercentages ((Double.valueOf(fenicsuniqsyn) / Double.valueOf(dbfenics)) / (Double.valueOf(fenicsuniqsyn) / Double.valueOf(sdcfenics)));
		
		String cpdepso = forPercentages ((Double.valueOf(epsouniqsyn) / Double.valueOf(dbepso)) / (Double.valueOf(epsouniqsyn) / Double.valueOf(sdcepso)));
		String cpdesso = forPercentages ((Double.valueOf(essouniqsyn) / Double.valueOf(dbesso)) / (Double.valueOf(essouniqsyn) / Double.valueOf(sdcesso)));
		String cpdepilont = forPercentages ((Double.valueOf(epilontuniqsyn) / Double.valueOf(dbepilont)) / (Double.valueOf(epilontuniqsyn) / Double.valueOf(sdcepilont)));
		String cpdepisem = forPercentages ((Double.valueOf(episemuniqsyn) / Double.valueOf(dbepisem))/ (Double.valueOf(episemuniqsyn) / Double.valueOf(sdcepisem)));
		String cpdfenics = forPercentages ((Double.valueOf(fenicsuniqsyn) / Double.valueOf(dbfenics)) / (Double.valueOf(fenicsuniqsyn) / Double.valueOf(sdcfenics)));
		
		Map <String, Set <String>> complepso = ds.getDl().calcComplement (
				mepso,
				messo,
				mepilont,
				mepisem,
				mfenics
				);
		//log.info("EpSO complement = " + complepso.keySet().size() + ",");
		
		Map <String, Set <String>> complesso = ds.getDl().calcComplement (
				messo,
				mepso,
				mepilont,
				mepisem,
				mfenics
				);
		//log.info("ESSO complement = " + complesso.keySet().size() + ",");
		
		Map <String, Set <String>> complepilont = ds.getDl().calcComplement (
				mepilont,
				messo,
				mepso,
				mepisem,
				mfenics
				);
		//log.info("EPILONT complement = " + complepilont.keySet().size() + ",");
		
		Map <String, Set <String>> complepisem = ds.getDl().calcComplement (
				mepisem,
				messo,
				mepilont,
				mepso,
				mfenics
				);
		//log.info("EPISEM complement = " + complepisem.keySet().size() + ",");
		
		Map <String, Set <String>> complfenics = ds.getDl().calcComplement (
				mfenics,
				messo,
				mepilont,
				mepisem,
				mepso
				);
		//log.info("FENICS complement = " + complfenics.keySet().size() + ",");
		
		Map <String, Set <String>> compldrugnames = ds.getDl().calcComplement (
				mdrugnames,
				messo,
				mepilont,
				mepisem,
				mepso
				);
		//log.info("FENICS complement = " + complfenics.keySet().size() + ",");
		String usepso = 		forPercentages(Double.valueOf(complepso.keySet().size()) 		/ Double.valueOf(epsolabels));
		String usesso =  		forPercentages(Double.valueOf(complesso.keySet().size()) 		/ Double.valueOf(essolabels));
		String usepilont =  	forPercentages(Double.valueOf(complepilont.keySet().size())		/ Double.valueOf(epilontlabels));
		String usepisem =  		forPercentages(Double.valueOf(complepisem.keySet().size()) 		/ Double.valueOf(episemlabels));
		String usfenics =  		forPercentages(Double.valueOf(complfenics.keySet().size()) 		/ Double.valueOf(fenicslabels));
		String usdrugnames =  	forPercentages(Double.valueOf(compldrugnames.keySet().size()) 	/ Double.valueOf(drugslabels));
		
		String sharedepso =  		forPercentages(Double.valueOf((epsolabels - complepso.keySet().size())) 		/ Double.valueOf(epsolabels));
		String sharedesso =  		forPercentages(Double.valueOf((essolabels - complesso.keySet().size())) 		/ Double.valueOf(essolabels));
		String sharedepilont =  	forPercentages(Double.valueOf((epilontlabels - complepilont.keySet().size())) 	/ Double.valueOf(epilontlabels));
		String sharedepisem =  		forPercentages(Double.valueOf((episemlabels - complepisem.keySet().size())) 	/ Double.valueOf(episemlabels));
		String sharedfenics =  		forPercentages(Double.valueOf((fenicslabels - complfenics.keySet().size())) 	/ Double.valueOf(fenicslabels));	
		String shareddrugnames =  	forPercentages(Double.valueOf((drugslabels - compldrugnames.keySet().size())) 	/ Double.valueOf(drugslabels));
		
		
		
		Set <String> synonymsuperset = new HashSet <String> ();
		synonymsuperset.addAll(ds.getDl().createSynSetFromMap(messo));
		synonymsuperset.addAll(ds.getDl().createSynSetFromMap(mepilont));
		synonymsuperset.addAll(ds.getDl().createSynSetFromMap(mepisem));
		synonymsuperset.addAll(ds.getDl().createSynSetFromMap(mfenics));
		Set <String> sharedsynepso = ds.getDl().createComplementSet(ds.getDl().createSynSetFromMap(mepso), synonymsuperset);
		
		synonymsuperset = new HashSet <String> ();
		synonymsuperset.addAll(ds.getDl().createSynSetFromMap(mepso));
		synonymsuperset.addAll(ds.getDl().createSynSetFromMap(mepilont));
		synonymsuperset.addAll(ds.getDl().createSynSetFromMap(mepisem));
		synonymsuperset.addAll(ds.getDl().createSynSetFromMap(mfenics));
		Set <String> sharedsynesso = ds.getDl().createComplementSet(ds.getDl().createSynSetFromMap(messo), synonymsuperset);
		
		synonymsuperset = new HashSet <String> ();
		synonymsuperset.addAll(ds.getDl().createSynSetFromMap(messo));
		synonymsuperset.addAll(ds.getDl().createSynSetFromMap(mepso));
		synonymsuperset.addAll(ds.getDl().createSynSetFromMap(mepisem));
		synonymsuperset.addAll(ds.getDl().createSynSetFromMap(mfenics));
		Set <String> sharedsynepilont = ds.getDl().createComplementSet(ds.getDl().createSynSetFromMap(mepilont), synonymsuperset);
		
		synonymsuperset = new HashSet <String> ();
		synonymsuperset.addAll(ds.getDl().createSynSetFromMap(messo));
		synonymsuperset.addAll(ds.getDl().createSynSetFromMap(mepilont));
		synonymsuperset.addAll(ds.getDl().createSynSetFromMap(mepso));
		synonymsuperset.addAll(ds.getDl().createSynSetFromMap(mfenics));
		Set <String> sharedsynepisem = ds.getDl().createComplementSet(ds.getDl().createSynSetFromMap(mepisem), synonymsuperset);
		
		synonymsuperset = new HashSet <String> ();
		synonymsuperset.addAll(ds.getDl().createSynSetFromMap(messo));
		synonymsuperset.addAll(ds.getDl().createSynSetFromMap(mepilont));
		synonymsuperset.addAll(ds.getDl().createSynSetFromMap(mepisem));
		synonymsuperset.addAll(ds.getDl().createSynSetFromMap(mepso));
		Set <String> sharedsynfenics = ds.getDl().createComplementSet(ds.getDl().createSynSetFromMap(mfenics), synonymsuperset);
		
		String ussynepso =  forPercentages(Double.valueOf(sharedsynepso.size()) 		/ Double.valueOf(epsouniqsyn));
		String ussynesso =  forPercentages(Double.valueOf(sharedsynesso.size()) 		/ Double.valueOf(essouniqsyn));
		String ussynepilont =  forPercentages(Double.valueOf(sharedsynepilont.size()) 	/ Double.valueOf(epilontuniqsyn));
		String ussynepisem =  forPercentages(Double.valueOf(sharedsynepisem.size()) 	/ Double.valueOf(episemuniqsyn));
		String ussynfenics =  forPercentages(Double.valueOf(sharedsynfenics.size()) 	/ Double.valueOf(fenicsuniqsyn));
		
		
		String ssharedsynepso =  forPercentages(Double.valueOf(epsouniqsyn - sharedsynepso.size()) / Double.valueOf(epsouniqsyn));
		String ssharedsynesso =  forPercentages(Double.valueOf(essouniqsyn - sharedsynesso.size())/ Double.valueOf(essouniqsyn));
		String ssharedsynepilont =  forPercentages(Double.valueOf(epilontuniqsyn - sharedsynepilont.size())/ Double.valueOf(epilontuniqsyn));
		String ssharedsynepisem =  forPercentages(Double.valueOf(episemuniqsyn - sharedsynepisem.size())/ Double.valueOf(episemuniqsyn));
		String ssharedsynfenics =  forPercentages(Double.valueOf(fenicsuniqsyn - sharedsynfenics.size())/ Double.valueOf(fenicsuniqsyn));	
		
		
	
		// WITHOUT DRUG NAMES		
		log.info("\\textbf{}  & \\textbf{EpSO} & \\textbf{ESSO} & \\textbf{EPILONT} & \\textbf{EPISEM} & \\textbf{FENICS} \\\\");
		log.info("\\hline");
		log.info("\\textbf{Concepts} & " 			+ sepsolabels + " & " + sessolabels + " & " + sepilontlabels + " & " + sepisemlabels + " & " + sfenicslabels + "\\\\");
		log.info("\\textbf{Unshared Concepts} & " 	+ usepso + " & " + usesso + " & " + usepilont + " & " + usepisem + " & " + usfenics + "\\\\");
		log.info("\\textbf{Shared Concepts} & " + sharedepso + " & " + sharedesso + " & " + sharedepilont + " & " + sharedepisem + " & " + sharedfenics + "\\\\");
		log.info("\\hline");		
		log.info("\\textbf{Synonyms} & " + sepsouniqsyn + " & " + sessouniqsyn + " & " + sepilontuniqsyn + " & " + sepisemuniqsyn + " & " + sfenicsuniqsyn + " \\\\");
		log.info("\\textbf{Unshared Synonyms} & " 	+ ussynepso + " & " + ussynesso + " & " + ussynepilont + " & " + ussynepisem + " & " + ussynfenics + " \\\\");
		log.info("\\textbf{Shared Synonyms} & " 	+ ssharedsynepso + " & " + ssharedsynesso + " & " + ssharedsynepilont + " & " + ssharedsynepisem + " & " + ssharedsynfenics + "\\\\");		
		log.info("\\hline");			
		// log.info("\\textbf{Total Synonyms} & " + sepsototal + " & " + sessototal + " & " + sepilonttotal + " & " + sepisemtotal + " & " + sfenicstotal + "\\\\");
		log.info("\\textbf{Synonyms per Concept}  & "+ avepso + " & " + avesso + " & " + avepilont + " & " + avepisem + " & " + avfenics + "\\\\");
		log.info("\\hline");
		log.info("\\textbf{Documents}    &                &              &               &               &               \\\\");
		log.info("\\textbf{\\ \\ \\ \\ \\ with B-Terms}&" + sdbepso + " & " + sdbesso + " & " + sdbepilont + " & " + sdbepisem + " & " + sdbfenics + " \\\\");
		log.info("\\textbf{\\ \\ \\ \\ \\ with B- \\& C-Terms}&" + ssdcepso + " & " + ssdcesso + " & " + ssdcepilont + " & " + ssdcepisem + " & " + ssdcfenics + " \\\\");
		log.info("\\textbf{Docs. with B- and C-Terms per}          &               &               &               &               &           \\\\");
		log.info("\\textbf{Docs. with B-Terms}&" + cpdepso + " & " + cpdesso + " & " + cpdepilont + " & " + cpdepisem + " & " + cpdfenics + " \\\\");
		log.info("\\hline");
		log.info("\\textbf{Concepts per Document}          &               &               &               &               &           \\\\");
		log.info("\\textbf{\\ \\ \\ \\ \\ with B-Terms}&" 		 + ldepso  + " & " + ldesso  + " & " + ldepilont + " & " + ldepisem + " & " + ldfenics + " \\\\");
		log.info("\\textbf{\\ \\ \\ \\ \\ with B- \\& C-Terms}&" + lcdepso + " & " + lcdesso + " & " + lcdepilont + " & " + lcdepisem + " & " + lcdfenics + " \\\\");
		log.info("\\hline");
		log.info("\\textbf{Synonyms per Document}          &               &               &               &               &           \\\\");
		log.info("\\textbf{\\ \\ \\ \\ \\ with B-Terms}&" 		 + sdepso  + " & " + sdesso  + " & " + sdepilont + " & "  + sdepisem  + " & " + sdfenics + " \\\\");
		log.info("\\textbf{\\ \\ \\ \\ \\ with B- \\& C-Terms}&" + scdepso + " & " + scdesso + " & " + scdepilont + " & " + scdepisem + " & " + scdfenics + " \\\\");
		log.info("\\hline");
	}
	
	
	public static String forPercentages(double value) {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
		DecimalFormat df = new DecimalFormat("#####.###%", symbols);
		String formattedPercent = df.format(value);
	    return formattedPercent;
	}
}
