package com.bioinfotools.BioinfoTools;

import java.util.SortedSet;

import org.biojava.nbio.core.sequence.ProteinSequence;
import org.biojava.nbio.core.sequence.compound.AminoAcidCompoundSet;
import org.biojava.nbio.core.sequence.io.util.IOUtils;
import org.biojava.nbio.core.sequence.loader.UniprotProxySequenceReader;
import org.biojava.nbio.ws.alignment.qblast.*;
import org.biojava.nbio.ws.hmmer.HmmerDomain;
import org.biojava.nbio.ws.hmmer.HmmerResult;
import org.biojava.nbio.ws.hmmer.RemoteHmmerScan;

public class Phylo {

	   public static void main(String[] args){

	       try {
	           // first we get a UniProt sequence
	           String uniProtID = "P26663";
	           ProteinSequence seq = getUniprot(uniProtID);

	           // now we submit this sequence to the Hmmer web site
	           RemoteHmmerScan hmmer = new RemoteHmmerScan();

	           SortedSet results = hmmer.scan(seq);

	           // let's print the obtained annotations
	           
	           System.out.println(String.format("#\t%15s\t%10s\t%s\t%s\t%8s\t%s",
	                   "Domain","ACC", "Start","End","eValue","Description"));
	           
	           int counter = 0;
	           for (HmmerResult hmmerResult : results) {
	               //System.out.println(hmmerResult);

	               for ( HmmerDomain domain : hmmerResult.getDomains()) {
	                   counter++;
	                   System.out.println(String.format("%d\t%15s\t%10s\t%5d\t%5d\t%.2e\t%s",
	                           counter,
	                           hmmerResult.getName(), domain.getHmmAcc(), 
	                           domain.getSqFrom(),domain.getSqTo(),
	                           hmmerResult.getEvalue(), hmmerResult.getDesc()
	                           ));

	               }

	           }

	       } catch (Exception e) {
	           // TODO Auto-generated catch block
	           e.printStackTrace();
	       }
	   }

	   private static HmmerResult[] getResults() {
		// TODO Auto-generated method stub
		return null;
	}

	/** Fetch a protein sequence from the UniProt web site
	    * 
	    * @param uniProtID
	    * @return a Protein Sequence
	    * @throws Exception
	    */
	   private static ProteinSequence getUniprot(String uniProtID) throws Exception {
	       
	       AminoAcidCompoundSet set = AminoAcidCompoundSet.getAminoAcidCompoundSet();
	       UniprotProxySequenceReader uniprotSequence = new UniprotProxySequenceReader(uniProtID,set);
	       ProteinSequence seq = new ProteinSequence(uniprotSequence);
	       return seq;
	   }
}

