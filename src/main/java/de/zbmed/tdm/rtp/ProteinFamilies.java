package de.zbmed.tdm.rtp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
/**
 * ProteinFamilies
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class ProteinFamilies {
	Map <String, Set <String>> proteinsInFamilies;
	Map <String, String> familyToPrefixes;
	Map <String, String> prefixesToFamilies;
	
	String [] protFam = {
			"ion_channel_calcium",
			"ion_channel_potassium",
			"ion_channel_sodium",
			"ion_channel_cys_loop_anionic",
			"ion_channel_cys_loop_cationic",
			"ionotropic_glutamate_receptor",
			"carbonic_anhydrase",
			"flavine_amine_oxidoreductase"
	};
	
	ProteinFamilies () {
		fillProteinsInFamilies();
	}

	private void fillProteinsInFamilies () {
		familyToPrefixes = new HashMap <String, String> ();
		prefixesToFamilies = new HashMap <String, String> ();
		proteinsInFamilies = new HashMap<String, Set <String>> ();
		

		Set <String> ion_channel_calcium = fill_ion_channel_calcium ();
		proteinsInFamilies.put("ion_channel_calcium", ion_channel_calcium);
		familyToPrefixes.put("ion_channel_calcium", "CAC");
		prefixesToFamilies.put("CAC", "ion_channel_calcium");
		
		Set <String> ion_channel_potassium = fill_ion_channel_potassium ();
		proteinsInFamilies.put("ion_channel_potassium", ion_channel_potassium);
		familyToPrefixes.put("ion_channel_potassium", "KCN");
		prefixesToFamilies.put("KCN", "ion_channel_potassium");
		
		Set <String> ion_channel_sodium = fill_ion_channel_sodium ();
		proteinsInFamilies.put("ion_channel_sodium", ion_channel_sodium);
		familyToPrefixes.put("ion_channel_sodium", "SCN");
		prefixesToFamilies.put("SCN", "ion_channel_sodium");

		Set <String> ion_channel_cys_loop_anionic = fill_ion_channel_cys_loop_anionic ();
		proteinsInFamilies.put("ion_channel_cys_loop_anionic", ion_channel_cys_loop_anionic);
		familyToPrefixes.put("ion_channel_cys_loop_anionic", "GAB");
		prefixesToFamilies.put("GAB", "ion_channel_cys_loop_anionic");
		
		Set <String> ion_channel_cys_loop_cationic = fill_ion_channel_cys_loop_cationic ();
		proteinsInFamilies.put("ion_channel_cys_loop_cationic", ion_channel_cys_loop_cationic);
		familyToPrefixes.put("ion_channel_cys_loop_cationic", "CHR");
		prefixesToFamilies.put("CHR", "ion_channel_cys_loop_cationic");
		
		Set <String> ionotropic_glutamate_receptor = fill_ionotropic_glutamate_receptor ();
		proteinsInFamilies.put("ionotropic_glutamate_receptor", ionotropic_glutamate_receptor);
		familyToPrefixes.put("ionotropic_glutamate_receptor", "GRI");
		prefixesToFamilies.put("GRI", "ionotropic_glutamate_receptor");
		
		Set <String> carbonic_anhydrase = fill_carbonic_anhydrase ();
		proteinsInFamilies.put("carbonic_anhydrase", carbonic_anhydrase);
		familyToPrefixes.put("carbonic_anhydrase", "CA");
		prefixesToFamilies.put("CA", "carbonic_anhydrase");
		
		Set <String> flavine_amine_oxidoreductase = fill_flavine_amine_oxidoreductase ();
		proteinsInFamilies.put("flavine_amine_oxidoreductase", flavine_amine_oxidoreductase);
		familyToPrefixes.put("flavine_amine_oxidoreductase", "MAO");
		prefixesToFamilies.put("MAO", "flavine_amine_oxidoreductase");
	}
	
	private Set <String> fill_ion_channel_calcium () {
		// ion_channel_calcium
		Set <String> ion_channel_calcium = new HashSet <String> ();
		ion_channel_calcium.add("CACNA1A");
		ion_channel_calcium.add("CACNA1B");
		ion_channel_calcium.add("CACNA1G");
		ion_channel_calcium.add("CACNA1H");
		ion_channel_calcium.add("CACNA1I");
		ion_channel_calcium.add("CACNA2D1");
		ion_channel_calcium.add("CACNA2D2");
		ion_channel_calcium.add("CACNA1B");
		return ion_channel_calcium;
	}
	
	private Set <String> fill_ion_channel_potassium () {
		// ion_channel_potassium
		Set <String> ion_channel_potassium = new HashSet <String> ();
		ion_channel_potassium.add("KCNQ2");
		ion_channel_potassium.add("KCNQ3");
		ion_channel_potassium.add("KCNQ4");
		ion_channel_potassium.add("KCNQ5");

		return ion_channel_potassium;
	}
	
	private Set <String> fill_ion_channel_sodium () {
		// ion_channel_sodium
		Set <String> ion_channel_sodium = new HashSet <String> ();
		ion_channel_sodium.add("SCN1A");
		ion_channel_sodium.add("SCN1B");
		ion_channel_sodium.add("SCN2A");
		ion_channel_sodium.add("SCN2B");
		ion_channel_sodium.add("SCN3A");
		ion_channel_sodium.add("SCN3B");
		ion_channel_sodium.add("SCN4A");
		ion_channel_sodium.add("SCN4B");
		ion_channel_sodium.add("SCN5A");
		ion_channel_sodium.add("SCN7A");
		ion_channel_sodium.add("SCN8A");
		ion_channel_sodium.add("SCN9A");
		ion_channel_sodium.add("SCN10A");
		ion_channel_sodium.add("SCN11A");

		return ion_channel_sodium;
	}
	
	private Set <String> fill_ion_channel_cys_loop_anionic () {
		// ion_channel_cys_loop_anionic
		Set <String> ion_channel_cys_loop_anionic = new HashSet <String> ();
		ion_channel_cys_loop_anionic.add("GABRA1");
		ion_channel_cys_loop_anionic.add("GABRA2");
		ion_channel_cys_loop_anionic.add("GABRA3");
		ion_channel_cys_loop_anionic.add("GABRA4");
		ion_channel_cys_loop_anionic.add("GABRA5");
		ion_channel_cys_loop_anionic.add("GABRA6");
		ion_channel_cys_loop_anionic.add("GABRB1");
		ion_channel_cys_loop_anionic.add("GABRB2");
		ion_channel_cys_loop_anionic.add("GABRB3");
		ion_channel_cys_loop_anionic.add("GABRD");
		ion_channel_cys_loop_anionic.add("GABRE");
		ion_channel_cys_loop_anionic.add("GABRG1");
		ion_channel_cys_loop_anionic.add("GABRG2");
		ion_channel_cys_loop_anionic.add("GABRG3");
		ion_channel_cys_loop_anionic.add("GABRP");
		ion_channel_cys_loop_anionic.add("GABRQ");
		return ion_channel_cys_loop_anionic;
	}
	
	private Set <String> fill_ion_channel_cys_loop_cationic () {
		// ion_channel_cys_loop_cationic
		Set <String> ion_channel_cys_loop_cationic = new HashSet <String> ();
		ion_channel_cys_loop_cationic.add("CHRNA2");
		ion_channel_cys_loop_cationic.add("CHRNA4");
		ion_channel_cys_loop_cationic.add("CHRNA7");		
		return ion_channel_cys_loop_cationic;
	}
	
	private Set <String> fill_ionotropic_glutamate_receptor () {
		// ion_channel_cys_loop_cationic
		Set <String> ionotropic_glutamate_receptor = new HashSet <String> ();
		ionotropic_glutamate_receptor.add("GRIA1");
		ionotropic_glutamate_receptor.add("GRIA2");
		ionotropic_glutamate_receptor.add("GRIK1");
		ionotropic_glutamate_receptor.add("GRIK2");
		ionotropic_glutamate_receptor.add("GRIN1");
		ionotropic_glutamate_receptor.add("GRIN2A");
		ionotropic_glutamate_receptor.add("GRIN2B");
		ionotropic_glutamate_receptor.add("GRIN2C");
		ionotropic_glutamate_receptor.add("GRIN2D");
		ionotropic_glutamate_receptor.add("GRIN3A");
		ionotropic_glutamate_receptor.add("GRIN3B");		
		return ionotropic_glutamate_receptor;
	}
	
	
	private Set <String> fill_carbonic_anhydrase () {
		// carbonic_anhydrase
		Set <String> carbonic_anhydrase = new HashSet <String> ();
		carbonic_anhydrase.add("CA1");
		carbonic_anhydrase.add("CA2");
		carbonic_anhydrase.add("CA3");
		carbonic_anhydrase.add("CA4");
		carbonic_anhydrase.add("CA4");
		carbonic_anhydrase.add("CA5A");
		carbonic_anhydrase.add("CA5B");
		carbonic_anhydrase.add("CA6");
		carbonic_anhydrase.add("CA7");
		carbonic_anhydrase.add("CA8");
		carbonic_anhydrase.add("CA9");
		carbonic_anhydrase.add("CA10");
		carbonic_anhydrase.add("CA11");
		carbonic_anhydrase.add("CA12");
		carbonic_anhydrase.add("CA13");
		carbonic_anhydrase.add("CA14");		
		return carbonic_anhydrase;
	}
	
	private Set <String> fill_flavine_amine_oxidoreductase () {
		// flavine_amine_oxidoreductase
		Set <String> flavine_amine_oxidoreductase = new HashSet <String> ();
		flavine_amine_oxidoreductase.add("MAOA");
		flavine_amine_oxidoreductase.add("MAOB");
		return flavine_amine_oxidoreductase;
	}
}
