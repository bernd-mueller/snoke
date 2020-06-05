package de.zbmed.snoke.rtp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
/**
 * N03ARelationalTargetPredictor
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class N03ARelationalTargetPredictor {
	private Map <String, String> drugbanknames;
	private Map <String, String> drugbankids;
	
	private Map <String, String> atcmap;
	
	private Map <String, Set <String>> N03_drugs_and_targets;
	private Map <String, Set <String>> N03_targets_and_drugs;
	
	private Map <String, Set <String>> rel_drugs_and_targets;
	private Map <String, Set <String>> rel_targets_and_drugs;
	
	private Map <String, Set<String>> N03_subclass_targets;
	
	String [] n03asubclasses = {
			"N03AA",
			"N03AB",
			"N03AC",
			"N03AD",
			"N03AE",
			"N03AF",
			"N03AG",
			"N03AX",
		};

	public void readCLI (String [] args) {
		
		// create Options object
		Options options = new Options();
		Option info = new Option( "info", "Relational Target Predictor for ATC N03A, Antiepileptics\n"+
				"-<nsdpo> Predict Relational Targets\n" +
				"-<nspcao> Print Targets of N03A Subclasses\n" +
				"-<nspdao> Print Similarity Scores For N03A SubClasses\n" +
				"-<nspdafo> Find Partner Drugs\n" +
				"written by Bernd Müller in 2015");
		options.addOption(info);
		
		Option n = Option.builder( "n" )
				.required(true)
				.desc("file with N03A drugs and their targets")
				.argName("N03A-drugs-and-targets.ids")
				.hasArg()
				.build();
		options.addOption(n);
		
		Option s = Option.builder( "s" )
				.required(true)
				.desc("file with targets of N03A drugs and all approved drugs for those targets")
				.argName("N03A-SharedTarget-drugs.ids")
				.hasArg()
				.build();
		options.addOption(s);
		
		Option d = Option.builder( "d" )
				.required(false)
				.desc("Drug name of the drug that should be used for relational target prediction")
				.argName("Lamotrigine")
				.hasArg()
				.build();
		options.addOption(d);
		
		Option p = Option.builder( "p" )
				.required(true)
				.desc("file with mapping of drugbank identifiers and drug prefered names")
				.argName("DrugBankIdName.map")
				.hasArg()
				.build();
		options.addOption(p);
		
		Option c = Option.builder( "c" )
				.required(false)
				.desc("N03A subclass")
				.argName("N03AA-N03AX")
				.hasArg()
				.build();
		options.addOption(c);
		
		Option a = Option.builder( "a" )
				.required(false)
				.desc("file with mapping of drugbank identifiers and ATC class")
				.argName("db-atc.map")
				.hasArg()
				.build();
		options.addOption(a);
		
		Option f = Option.builder( "f" )
				.required(false)
				.desc("another drug name for comparison of two drugs")
				.argName("Primidone")
				.hasArg()
				.build();
		options.addOption(f);
		
		Option out = Option.builder( "o" )
				.required(true)
				.desc("output file")
				.argName("output-file")
				.hasArg()
				.build();
		options.addOption(out);
		
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine cmd = parser.parse(options, args);
			
			// n s d p o
			if (cmd.hasOption("n") && cmd.hasOption("s") && cmd.hasOption("d") && cmd.hasOption("p") && cmd.hasOption("o") && !cmd.hasOption("a")) {
				System.out.println("Starting relational target prediction...");
				predictRelationalTargets (cmd.getOptionValue("n"), cmd.getOptionValue("s"), cmd.getOptionValue("d"), cmd.getOptionValue("p"), cmd.getOptionValue("o"));
			}
			
			// n s c p a o
			if (cmd.hasOption("n") && cmd.hasOption("s") && cmd.hasOption("p") && cmd.hasOption("a") && cmd.hasOption("o")) {
				System.out.println("Starting N03A subclass target printing...");
				printN03ASubClassTargets (cmd.getOptionValue("n"), cmd.getOptionValue("s"), cmd.getOptionValue("p"), cmd.getOptionValue("a"), cmd.getOptionValue("o"));
			}
			
			// n s d p a o
			if (cmd.hasOption("n") && cmd.hasOption("s") && cmd.hasOption("d") && cmd.hasOption("p") && cmd.hasOption("a") && cmd.hasOption("o") && !cmd.hasOption("f")) {
				System.out.println("Starting drug similarity score calculation...");
				printDrugSimilarityScoresForN03AClasses (cmd.getOptionValue("n"), cmd.getOptionValue("s"), cmd.getOptionValue("p"), cmd.getOptionValue("d"), cmd.getOptionValue("a"), cmd.getOptionValue("o"));
			}
			
			// n s d p a f o
			if (cmd.hasOption("n") && cmd.hasOption("s") && cmd.hasOption("d") && cmd.hasOption("p") && cmd.hasOption("a") && cmd.hasOption("f") && cmd.hasOption("o")) {
				System.out.println("Starting drug partner finding...");
				findPartnerDrugs (cmd.getOptionValue("n"), cmd.getOptionValue("s"), cmd.getOptionValue("p"), cmd.getOptionValue("d"), cmd.getOptionValue("a"), cmd.getOptionValue("f"), cmd.getOptionValue("o"));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(this.getClass().getName(), options );
		}
	}
	
	public void findPartnerDrugs (String n, String s, String p, String d, String a, String f, String o) {
		System.out.println("Reading " + p + "...");
		readDrugBankNameMap (p);
		
		System.out.println("Reading " + n + "...");
		createN03DrugTargetMaps (n);
		System.out.println("Reading " + s + "...");
		
		System.out.println("Reading " + a + "...");
		createATCMap (a);

		System.out.println("Finding partners for " + d + "...");
		createN03SubClassTargetMap ();
		matchTwoDrugsByTargets (d, f, o);
		
		System.out.println("Results written to " + o + "...");
	}
	public void predictRelationalTargets (String n, String s, String d, String p, String o)  {
		System.out.println("Reading " + p + "...");
		readDrugBankNameMap (p);
		
		System.out.println("Reading " + n + "...");
		createN03DrugTargetMaps (n);
		System.out.println("Reading " + s + "...");
		createRelDrugTargetMaps (s);

		System.out.println("Conducting relational target prediction for " + d + "...");
		relationalTargetPrediction (d, o);
		
		System.out.println("Results written to " + o + "...");
	}
	
	public void printN03ASubClassTargets (String n, String s, String p, String a, String o) {
		System.out.println("Reading " + p + "...");
		readDrugBankNameMap (p);
		
		System.out.println("Reading " + n + "...");
		createN03DrugTargetMaps (n);
		System.out.println("Reading " + s + "...");
		
		System.out.println("Reading " + a + "...");
		createATCMap (a);

		System.out.println("Conducting N03A subclass printing...");
		N03SubClassPrinter (o);
		
		System.out.println("Results written to " + o + "...");
		
	}
	
	public void printDrugSimilarityScoresForN03AClasses (String n, String s, String p, String d, String a, String o) {
		System.out.println("Reading " + p + "...");
		readDrugBankNameMap (p);
		
		System.out.println("Reading " + n + "...");
		createN03DrugTargetMaps (n);
		System.out.println("Reading " + s + "...");
		
		System.out.println("Reading " + a + "...");
		createATCMap (a);

		System.out.println("Calculating N03A subclass similarity scores for " + d + "...");
		createN03SubClassTargetMap ();
		calcMostDissimilarDrug (d, o);
		
		System.out.println("Results written to " + o + "...");
		
	}
	
	public void matchTwoDrugsByTargets (String d, String f, String o) {
	}
	
	public void createN03SubClassTargetMap () {
		N03_subclass_targets = new HashMap <String, Set<String>> ();
		
		for (String subclass : n03asubclasses) {
			N03_subclass_targets.put(subclass, readN03ASubClassTargets (subclass));
		}
	}
	
	public void calcMostDissimilarDrug (String d, String o) {

		try {
			PrintWriter writer = new PrintWriter(o, "UTF-8");
			
			String curSubClass = atcmap.get(d);
			
			Set <String> curTargets = N03_subclass_targets.get(curSubClass);
			
			for (String subClass : n03asubclasses) {
				//if (!subClass.equals(curSubClass)) {
					Set <String> targets = N03_subclass_targets.get(subClass);
					double score = calcSimilarityOfSubclasses (d, curTargets, targets);
					
					writer.println("The N03A subclass " + curSubClass + " from drug " + d + " has the following similarity score to N03A subclass " + subClass + ": " + score);
				//}
			}

			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public double calcSimilarityOfSubclasses (String drug, Set <String> classA, Set <String> classB) {
		double enrichment = 0.;
		
		Set <String> targets = N03_drugs_and_targets.get(drug);
		
		for (String itemA : classA) {
			if (classB.contains(itemA)) {
				if (targets.contains(itemA)) {
					enrichment = enrichment - 2;
				} else {
					enrichment--;
				}
			} else {
				enrichment++;
			}
		}
		
		double avg = (classA.size() + classB.size()) / 2;
		
		double score = enrichment/avg ;
		
		return score;
	}
	
	private void createATCMap (String a) {
		atcmap = new HashMap <String, String> ();
		BufferedReader br;
		try {
			br = new BufferedReader (new FileReader(new File(a)));
			
			String line;
			while ((line = br.readLine()) != null) {
				if (line.contains("\t")) {
					if (line.split("\t").length > 1) {
						String drug = line.split("\t")[0];
						String atc = line.split("\t")[1];
						drug = drugbanknames.get(drug);
						
						atcmap.put(drug, atc);
					}
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void N03SubClassPrinter (String o) {
		try {
			PrintWriter writer = new PrintWriter(o, "UTF-8");
			
			for (String c : n03asubclasses) {
				
				if (c.startsWith("N03A") && atcmap.containsValue(c)) {
					Set <String> subclassTargets = new TreeSet <String> ();
					for (String drug : atcmap.keySet()) {
						if (atcmap.get(drug).equals(c)) {
							
							//if (!drug.equals("Zonisamide")) {
								Set <String> targets = N03_drugs_and_targets.get(drug);
								
								
								if (targets != null) {
									//if (targets.contains("CA1")) {
										System.out.println(drug + ": " + normalizeTargetsToTargetFamily(targets).toString());
										writer.println (drug + ": " + normalizeTargetsToTargetFamily(targets).toString());
									//}
									for (String target : targets) {
										subclassTargets.add(target);
									}
								}
							//}
						}
					}
					subclassTargets = normalizeTargetsToTargetFamily (subclassTargets);
					System.out.println("N03A subclass " + c + " comprises the following set of targets: " + subclassTargets.toString());
					// writer.println("N03A subclass " + c + " comprises the following set of targets: " + subclassTargets.toString());
					writer.println(c + ": " + subclassTargets.toString()+"\n\n");
				} else {
					writer.println(c + " is no N03A subclass!\n\tAborting NOW");
				}

			}
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Set <String> normalizeTargetsToTargetFamily (Set <String> targets) {
		Set <String> filteredTargets = new TreeSet <String> ();
		ProteinFamilies pf = new ProteinFamilies ();
		
		for (String target : targets) {
			String prefix = target;
			if (prefix.length()>=4) {
				prefix = prefix.substring(0,3);
			}

			prefix = prefix.replaceAll ("\\d+.*", "");
			
			if (pf.prefixesToFamilies.containsKey(prefix)) {
				filteredTargets.add(pf.prefixesToFamilies.get(prefix));
			} else {
				filteredTargets.add (target);
			}
		}
		
		return filteredTargets;
	}
	public Set <String> readN03ASubClassTargets (String c) {
		Set <String> subclassTargets = new TreeSet <String> ();
		if (c.startsWith("N03A") && atcmap.containsValue(c)) {
				
			for (String drug : atcmap.keySet()) {
				if (atcmap.get(drug).equals(c)) {
					Set <String> targets = N03_drugs_and_targets.get(drug);
					if (targets != null) {
						for (String target : targets) {
							subclassTargets.add(target);
						}
					}
				}
			}
		}
		return subclassTargets;
	}
	
	private void readDrugBankNameMap (String p) {
		drugbanknames = new HashMap <String, String> ();
		drugbankids = new HashMap <String, String> ();
		BufferedReader br;
		try {
			br = new BufferedReader (new FileReader(new File(p)));
			
			String line;
			while ((line = br.readLine()) != null) {
				if (line.contains("\t")) {
					if (line.split("\t").length > 1) {
						String id = line.split("\t")[0];
						String name = line.split("\t")[1];
						drugbanknames.put(id, name);
						drugbankids.put(name, id);
					}
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void createN03DrugTargetMaps (String n) {
		N03_drugs_and_targets = new HashMap <String, Set <String>> ();
		N03_targets_and_drugs = new HashMap <String, Set <String>> ();
		
		BufferedReader br;
		try {
			br = new BufferedReader (new FileReader(new File(n)));
			
			String line;
			while ((line = br.readLine()) != null) {
				if (line.contains("\t")) {
					if (line.split("\t").length > 1) {
						String gene = line.split("\t")[0];
						String drug = line.split("\t")[1];
						drug = drugbanknames.get(drug);
						
						if (N03_drugs_and_targets.containsKey(drug)) {
							Set <String> genes = N03_drugs_and_targets.get(drug);
							genes.add (gene);
							N03_drugs_and_targets.put(drug,  genes);
						} else {
							Set <String> genes = new TreeSet <String> ();
							genes.add(gene);
							N03_drugs_and_targets.put(drug,  genes);
						}
						
						if (N03_targets_and_drugs.containsKey(gene)) {
							Set <String> drugs = N03_targets_and_drugs.get(gene);
							drugs.add(drug);
							N03_targets_and_drugs.put(gene, drugs);
						} else {
							Set <String> drugs = new TreeSet <String> ();
							drugs.add(drug);
							N03_targets_and_drugs.put(gene, drugs);
						}
					}
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void createRelDrugTargetMaps (String s) {
		rel_drugs_and_targets = new HashMap <String, Set <String>> ();
		rel_targets_and_drugs = new HashMap <String, Set <String>> ();
		
		BufferedReader br;
		try {
			br = new BufferedReader (new FileReader(new File(s)));
			
			String line;
			while ((line = br.readLine()) != null) {
				if (line.contains("\t")) {
					if (line.split("\t").length > 1) {
						String gene = line.split("\t")[0];
						String drug = line.split("\t")[1];
						drug = drugbanknames.get(drug);
						if (rel_drugs_and_targets.containsKey(drug)) {
							Set <String> genes = rel_drugs_and_targets.get(drug);
							genes.add(gene);
							rel_drugs_and_targets.put(drug, genes);
						} else {
							Set <String> genes = new TreeSet <String> ();
							genes.add(gene);
							rel_drugs_and_targets.put(drug, genes);
						}
						
						if (rel_targets_and_drugs.containsKey(gene)) {
							Set <String> drugs = rel_targets_and_drugs.get(gene);
							drugs.add(drug);
							rel_targets_and_drugs.put(gene, drugs);
						} else {
							Set <String> drugs = new TreeSet <String> ();
							drugs.add(drug);
							rel_targets_and_drugs.put(gene, drugs);
						}
					}
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * rel_drugs_and_targets
	 * rel_targets_and_drugs
	 * N03_drugs_and_targets;
	 * N03_targets_and_drugs;
	 * 
	 * @param drugbankid
	 * @param o
	 */
	private void relationalTargetPrediction (String drugbankid, String o) {
		try {
			PrintWriter writer = new PrintWriter(o, "UTF-8");
			
			if (N03_drugs_and_targets.containsKey(drugbankid)) {
				Set <String> targets = N03_drugs_and_targets.get(drugbankid);
				
				if (targets != null && targets.size()>1) {
					writer.println (drugbankid + " has the following targets: " + targets.toString());
					
					for (String target : targets) {
						Set <String> drugs = rel_targets_and_drugs.get(target);
						
						if (drugs != null && drugs.size()>1) {
							writer.println (drugbankid + " shares the target " + target + " with the drugs " + drugs.toString() + " that are potential repurposing candidates");
							
							for (String otherDrugs : drugs) {
								Set <String> otherTargets = rel_drugs_and_targets.get(otherDrugs);
								
								if (otherTargets != null && otherTargets.contains("@")) {
									writer.println (otherDrugs + " also has the targets " + otherTargets.toString() + " that are potential targets for " + drugbankid);
								} else if (otherTargets != null) {
									writer.println (otherDrugs + " also has the target " + otherTargets.toString() + " that is a potential target for " + drugbankid);
								} else {
								
								}
							}
							
						} else if (drugs != null && drugs.size()==1) {
							Set <String> otherTargets = N03_targets_and_drugs.get(drugs.toArray()[0]);
							writer.println (drugbankid + " shares the target " + target + " with the drug " + drugs + " that is a potential repurposing candidate");
							if (otherTargets.size()>1) {
								writer.println(drugs + " also has the targets " + otherTargets.toString() + " that are potential targets for " + drugbankid);
							} else if (otherTargets.size()==1) {
								writer.println(drugs + " also has the target " + otherTargets.toString() + " that is a potential target for " + drugbankid);
							}
							
						} else {
							writer.println ("The target " + target + " of " + drugbankid + " is not shared with any other non-Antiepileptic drug");
						}
					}
					
				} else if (targets.size()==1) {
					writer.println (drugbankid + " has only this single target: " +targets.toArray()[0]);
					
					writer.println("The following Antiepileptics share " + targets.toArray()[0] + " as target with " + drugbankid + ": "+ N03_targets_and_drugs.get(targets.toArray()[0]).toString());
				}
				
				
			} else {
				writer.println(drugbankid + " is no approved N03A drug!\n\tAborting NOW");
			}
			
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		N03ARelationalTargetPredictor predictor = new N03ARelationalTargetPredictor ();
		predictor.readCLI(args);
	}
}
