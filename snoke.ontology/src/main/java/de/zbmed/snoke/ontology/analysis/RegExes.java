package de.zbmed.snoke.ontology.analysis;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;



public class RegExes {

	public static void main (String [] args) {
		String numberformat = "%,6f";
		String integerformat = "%,d";
		String percentageformat = "# \\%";
		Double d =  Double.valueOf(1190 / 1357);
		String test = forPercentages(d);
		
		System.out.println(test);
		
		Set <String> a = new HashSet <String> ();
		Set <String> b = new HashSet <String> ();
		a.add("1");
		a.add("2");
		a.add("3");
		
		b.add("1");
		b.add("2");
		b.add("3");
		
		System.out.println(a.equals(b));
		
	}
	
	public static String forPercentages(double value) {
		DecimalFormat df = new DecimalFormat("#####.##%");
		String formattedPercent = df.format(value);
	    return formattedPercent;
	}
}
