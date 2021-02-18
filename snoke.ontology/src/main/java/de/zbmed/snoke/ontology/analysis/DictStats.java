package de.zbmed.snoke.ontology.analysis;

import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DictStats {
	private static final Logger log = LoggerFactory.getLogger(DictStats.class);
	DictLoader dl;
	
	DictStats () {
		dl = new DictLoader ();
	}
	public DictLoader getDl() {
		return dl;
	}
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
		
		String avepso = 	String.format(Locale.US, numberformat, ds.getDl().averageSynPerLabel(mepso));
		String avesso = 	String.format(Locale.US, numberformat, ds.getDl().averageSynPerLabel(messo));
		String avepilont = 	String.format(Locale.US, numberformat, ds.getDl().averageSynPerLabel(mepilont));
		String avepisem = 	String.format(Locale.US, numberformat, ds.getDl().averageSynPerLabel(mepisem));
		String avfenics = 	String.format(Locale.US, numberformat, ds.getDl().averageSynPerLabel(mfenics));
		String avdrugs = 	String.format(Locale.US, numberformat, ds.getDl().averageSynPerLabel(mdrugnames));
		
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
		
		
		String ldepso = String.format(Locale.US, numberformat, Double.valueOf(epsolabels) / Double.valueOf(dbepso));
		String ldesso = String.format(Locale.US, numberformat, Double.valueOf(essolabels) / Double.valueOf(dbesso));
		String ldepilont = String.format(Locale.US, numberformat, Double.valueOf(epilontlabels) / Double.valueOf(dbepilont));
		String ldepisem = String.format(Locale.US, numberformat, Double.valueOf(episemlabels) / Double.valueOf(dbepisem));
		String ldfenics = String.format(Locale.US, numberformat, Double.valueOf(fenicslabels) / Double.valueOf(dbfenics));
		
		String lcdepso = String.format(Locale.US, numberformat, Double.valueOf(epsolabels) / Double.valueOf(sdcepso));
		String lcdesso = String.format(Locale.US, numberformat, Double.valueOf(essolabels) / Double.valueOf(sdcesso));
		String lcdepilont = String.format(Locale.US, numberformat, Double.valueOf(epilontlabels) / Double.valueOf(sdcepilont));
		String lcdepisem = String.format(Locale.US, numberformat, Double.valueOf(episemlabels) / Double.valueOf(sdcepisem));
		String lcdfenics = String.format(Locale.US, numberformat, Double.valueOf(fenicslabels) / Double.valueOf(sdcfenics));
		
		String sdepso = String.format(Locale.US, numberformat, Double.valueOf(epsototal) / Double.valueOf(dbepso));
		String sdesso = String.format(Locale.US, numberformat, Double.valueOf(essototal) / Double.valueOf(dbesso));
		String sdepilont = String.format(Locale.US, numberformat, Double.valueOf(epilonttotal) / Double.valueOf(dbepilont));
		String sdepisem = String.format(Locale.US, numberformat, Double.valueOf(episemtotal) / Double.valueOf(dbepisem));
		String sdfenics = String.format(Locale.US, numberformat, Double.valueOf(fenicstotal) / Double.valueOf(dbfenics));
		
		String scdepso = String.format(Locale.US, numberformat, Double.valueOf(epsototal) / Double.valueOf(sdcepso));
		String scdesso = String.format(Locale.US, numberformat, Double.valueOf(essototal) / Double.valueOf(sdcesso));
		String scdepilont = String.format(Locale.US, numberformat, Double.valueOf(epilonttotal) / Double.valueOf(sdcepilont));
		String scdepisem = String.format(Locale.US, numberformat, Double.valueOf(episemtotal) / Double.valueOf(sdcepisem));
		String scdfenics = String.format(Locale.US, numberformat, Double.valueOf(fenicstotal) / Double.valueOf(sdcfenics));
		
		String cpdepso = String.format(Locale.US, numberformat, Double.valueOf(sdepso) / Double.valueOf(scdepso));
		String cpdesso = String.format(Locale.US, numberformat, Double.valueOf(sdesso) / Double.valueOf(scdesso));
		String cpdepilont = String.format(Locale.US, numberformat, Double.valueOf(sdepilont) / Double.valueOf(scdepilont));
		String cpdepisem = String.format(Locale.US, numberformat, Double.valueOf(sdepisem) / Double.valueOf(scdepisem));
		String cpdfenics = String.format(Locale.US, numberformat, Double.valueOf(sdfenics) / Double.valueOf(scdfenics));
		
		
		log.info("\\textbf{}  & \\textbf{EpSO} & \\textbf{ESSO} & \\textbf{EPILONT} & \\textbf{EPISEM} & \\textbf{FENICS} & \\textbf{DrugNames}\\\\");
		log.info("\\hline");
		log.info("\\textbf{Concepts} & " 			+ sepsolabels + " & " + sessolabels + " & " + sepilontlabels + " & " + sepisemlabels + " & " + sfenicslabels + " & " + sdrugslabels + "\\\\");
		log.info("\\textbf{Unique Synonyms} & " + sepsouniqsyn + " & " + sessouniqsyn + " & " + sepilontuniqsyn + " & " + sepisemuniqsyn + " & " + sfenicsuniqsyn + " & " + sdrugsuniqsyn + "\\\\");
		log.info("\\textbf{Total Synonyms} & " + sepsototal + " & " + sessototal + " & " + sepilonttotal + " & " + sepisemtotal + " & " + sfenicstotal + " & " + sdrugstotal + "\\\\");
		log.info("\\textbf{Synonyms per Concept}  & "+ avepso + " & " + avesso + " & " + avepilont + " & " + avepisem + " & " + avfenics + " & " + avdrugs + "\\\\");
		log.info("\\hline");
		log.info("\\textbf{Documents}    &                &              &               &               &               &           \\\\");
		log.info("\\textbf{\\ \\ \\ \\ \\ with B-Terms}&" + sdbepso + " & " + sdbesso + " & " + sdbepilont + " & " + sdbepisem + " & " + sdbfenics + " & N/A\\\\");
		log.info("\\textbf{\\ \\ \\ \\ \\ with B- \\& C-Terms}&" + ssdcepso + " & " + ssdcesso + " & " + ssdcepilont + " & " + ssdcepisem + " & " + ssdcfenics + " & N/A\\\\");
		log.info("\\hline");
		log.info("\\textbf{Concepts per Document}          &               &               &               &               &           &           \\\\");
		log.info("\\textbf{\\ \\ \\ \\ \\ with B-Terms}&" 		 + ldepso  + " & " + ldesso  + " & " + ldepilont + " & " + ldepisem + " & " + ldfenics + " & N/A\\\\");
		log.info("\\textbf{\\ \\ \\ \\ \\ with B- \\& C-Terms}&" + lcdepso + " & " + lcdesso + " & " + lcdepilont + " & " + lcdepisem + " & " + lcdfenics + " & N/A\\\\");
		log.info("\\hline");
		log.info("\\textbf{Synonyms per Document}          &               &               &               &               &           &           \\\\");
		log.info("\\textbf{\\ \\ \\ \\ \\ with B-Terms}&" 		 + sdepso  + " & " + sdesso  + " & " + sdepilont + " & "  + sdepisem  + " & " + sdfenics + " & N/A\\\\");
		log.info("\\textbf{\\ \\ \\ \\ \\ with B- \\& C-Terms}&" + scdepso + " & " + scdesso + " & " + scdepilont + " & " + scdepisem + " & " + scdfenics + " & N/A\\\\");
		log.info("\\hline");
		log.info("\\textbf{Docs. with B-Terms per}          &               &               &               &               &           &         \\\\");
		log.info("\\textbf{Docs. with B- and C-Terms}&" + cpdepso + " & " + cpdesso + " & " + cpdepilont + " & " + cpdepisem + " & " + cpdfenics + " & N/A\\\\");
		log.info("\\hline");
	}
	
	

}
