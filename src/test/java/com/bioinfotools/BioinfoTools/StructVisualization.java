package com.bioinfotools.BioinfoTools;

import java.io.IOException;
import java.util.Scanner;

import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.StructureIO;
import org.biojava.nbio.structure.StructureTools;
import org.biojava.nbio.structure.align.gui.jmol.StructureAlignmentJmol;
import org.biojava.nbio.structure.align.util.AtomCache;

public class StructVisualization {
    public static void main(String[] args) throws IOException {
        // Localizing PDB Files
        System.setProperty("PDB_DIR", "C:/Users/raanna/Desktop/BioinfoTools/BioinfoTools/assets/structures");
        // Initialize & Set AtomCache for StructureIO
        AtomCache cache = new AtomCache();
        StructureIO.setAtomCache(cache);
        
        try (Scanner sc = new Scanner(System.in)) {
        	System.out.print("Enter PDB ID: ");
			String PDBID = sc.next();
			try {
			    // Load the structure for PDB ID "4hhb"
			    Structure struct = StructureIO.getStructure(PDBID);
			    // Print the number of atoms in the structure
			    System.out.println("Number of Atoms: " + StructureTools.getNrAtoms(struct));
			    
			    // Visualizing Structure
			    StructureAlignmentJmol jmolPanel = new StructureAlignmentJmol();
			    jmolPanel.setStructure(struct);
			    jmolPanel.evalString("select * ; color chain;");            
		        jmolPanel.evalString("select *; spacefill off; wireframe off; cartoon on;  ");
		        jmolPanel.evalString("select ligands; cartoon off; wireframe 0.3; spacefill 0.5; color cpk;");
		        jmolPanel.evalString("spin on;");        
			} catch (Exception e) {
			    e.printStackTrace();
			}
		} catch (Exception e) {
		    e.printStackTrace();
		}
    }
}
