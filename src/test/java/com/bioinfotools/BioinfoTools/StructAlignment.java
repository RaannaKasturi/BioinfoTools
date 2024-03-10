package com.bioinfotools.BioinfoTools;

import java.io.IOException;
import java.util.*;
import org.biojava.nbio.structure.Atom;
import org.biojava.nbio.structure.*;
import org.biojava.nbio.structure.align.StructureAlignment;
import org.biojava.nbio.structure.align.StructureAlignmentFactory;
import org.biojava.nbio.structure.align.ce.CeMain;
import org.biojava.nbio.structure.align.gui.MultipleAlignmentJmolDisplay;
import org.biojava.nbio.structure.align.multiple.MultipleAlignment;
import org.biojava.nbio.structure.align.multiple.mc.MultipleMcMain;
import org.biojava.nbio.structure.align.multiple.util.MultipleAlignmentWriter;
import org.biojava.nbio.structure.align.util.AtomCache;

public class StructAlignment {
    public static void main(String[] args) throws IOException {
        // Localizing PDB Files
        System.setProperty("PDB_DIR", "C:/Users/raanna/Desktop/BioinfoTools/BioinfoTools/assets/structures");
        // Initialize & Set AtomCache for StructureIO
        //AtomCache cache = new AtomCache();
        //StructureIO.setAtomCache(cache);
        
        try (Scanner sc = new Scanner(System.in)) {
        	//System.out.print("Enter PDB ID: ");
			//String PDBID = sc.next();
			try {
			    // Aligning Structure
			    //Specify the structures to align: some ASP-proteinases
			    List<String> names = Arrays.asList("3app", "4ape", "5pep", "1psn", "4cms", "1bbs.A", "1smr.A");

			    //Load the CA atoms of the structures and create the structure identifiers
			    AtomCache cache = new AtomCache();
			    List<Atom[]> atomArrays = new ArrayList<Atom[]>();
			    List<StructureIdentifier> identifiers = new ArrayList<StructureIdentifier>();
			    for (String name:names)	{
			      atomArrays.add(cache.getAtoms(name));
			      identifiers.add(new SubstructureIdentifier(name));
			    }

			    //Generate the multiple alignment algorithm with the chosen pairwise algorithm
			    StructureAlignment pairwise  = StructureAlignmentFactory.getAlgorithm(CeMain.algorithmName);
			    MultipleMcMain multiple = new MultipleMcMain(pairwise);

			    //Perform the alignment
			    MultipleAlignment result = multiple.align(atomArrays);

			    // Set the structure identifiers, so that each atom array can be identified in the outputs
			    result.getEnsemble().setStructureIdentifiers(identifiers);

			    //Output the FASTA sequence alignment
			    System.out.println(MultipleAlignmentWriter.toFASTA(result));

			    //Display the results in a 3D view
			    MultipleAlignmentJmolDisplay.display(result);
			} catch (Exception e) {
			    e.printStackTrace();
			}
		} catch (Exception e) {
		    e.printStackTrace();
		}
    }
}
