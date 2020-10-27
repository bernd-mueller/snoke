package de.zbmed.snoke.ontology.drugnames;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * DrugBankTargetConverter
 *
 * @author Bernd Mueller
 * @version 0.1
 * @since 2016
 */
public class DrugNameTargetConverter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
				// PrintWriter writer = new PrintWriter("/home/muellerb/Desktop/drugtarget.map", "UTF-8");
				// BufferedReader br = new BufferedReader(new FileReader(new File(
				// "/media/muellerb/Daten/EpilepsyData/Results with DrugBank target filter/DrugBank_all_target_ids_all.csv")));
			
			BufferedReader br = new BufferedReader(new FileReader(new File(
					"D:\\eclipse-workspace\\TDM\\resources\\drugbank\\DrugBank_all_target_ids_all.csv")));
			PrintWriter writer = new PrintWriter("D:\\eclipse-workspace\\TDM\\resources\\drugbank\\results\\drugtarget.map", "UTF-8");
			
			String line;
			int c=1;
			while ((line = br.readLine()) != null) {
				// process the line. "('[^']*?')"
				if (c>0) {
					System.out.println(c);
					
					String clear = line.replaceAll ("(\"[^\"]*?\")", "asd");
	
					String [] lineSplit = clear.split(",");
					String gene = "";
					if (lineSplit.length>12) {
						gene = lineSplit[2];
						String drugs = lineSplit[12];
						
						if (gene.length()>0) {
							if (drugs.contains(";")) {
								String [] drugSplit = drugs.split("; ");
								for (String d : drugSplit) {
									writer.println(gene + "\t" + d);
								}
								
							} else {
								writer.println(gene + "\t" + drugs);
							}
							System.out.println(lineSplit[12]);					
						}
					} else {
						System.out.println(c + " broken line");
						System.out.println(line);
					}

				}
				if (c++>10) {
					//break;
				}
			}
			br.close();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
