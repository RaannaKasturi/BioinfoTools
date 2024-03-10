package com.bioinfotools.BioinfoTools;

import java.awt.Container; 
import java.awt.Dimension; 
import java.awt.Graphics; 
import java.awt.Rectangle; 
import java.awt.event.WindowAdapter; 
import java.awt.event.WindowEvent;
import java.util.Scanner;

import javax.swing.JFrame; 
import javax.swing.JPanel; 
import org.biojava.nbio.structure.Structure; 
import org.biojava.nbio.structure.io.PDBFileReader; 
import org.jmol.adapter.smarter.SmarterJmolAdapter; 
import org.jmol.api.JmolAdapter;
import org.jmol.api.JmolViewer; 

public class StructVisualization2<JmolSimpleViewer> {

   JmolViewer viewer;  
   Structure structure; 

   JmolPanel jmolPanel;  
   JFrame frame ;

   public static void main(String[] args){  
	   try (Scanner sc = new Scanner(System.in)) {
       	System.out.print("Enter PDB ID: ");
			String PDBID = sc.next();
	       try {
	
	           PDBFileReader pdbr = new PDBFileReader();            
	           pdbr.setPath("/Path/To/PDBFiles/");
	
	           String pdbCode = PDBID;
	
	           Structure struc = pdbr.getStructureById(pdbCode);
	
	           StructVisualization2 ex = new StructVisualization2();  
	           ex.setStructure(struc);  
	            
	             
	       } catch (Exception e){  
	           e.printStackTrace();  
	       }
	   }
   }

   public StructVisualization2() {  
       frame = new JFrame();  
       frame.addWindowListener(new ApplicationCloser());  
       Container contentPane = frame.getContentPane();  
       jmolPanel = new JmolPanel();  
    
       jmolPanel.setPreferredSize(new Dimension(200,200));  
       contentPane.add(jmolPanel);

       frame.pack();  
       frame.setVisible(true); 

   }  
   public void setStructure(Structure s) {  
         
       frame.setName(s.getPDBCode());

       // actually this is very simple  
       // just convert the structure to a PDB file  
   
       String pdb = s.toPDB();  
        
       structure = s;  
       JmolViewer viewer = jmolPanel.getViewer();

       // Jmol could also read the file directly from your file system  
       //viewer.openFile("/Path/To/PDB/1tim.pdb");  
   
       // send the PDB file to Jmol.  
       // there are also other ways to interact with Jmol, but they require more  
       // code. See the link to SPICE above...  
       ((JmolViewer) viewer).openStringInline(pdb);  
       ((JmolViewer) viewer).evalString("select *; spacefill off; wireframe off; backbone 0.4;  ");  
       ((JmolViewer) viewer).evalString("color chain;  spin on;");  
       this.viewer = viewer;

   }

   public void setTitle(String label){  
       frame.setTitle(label);  
   }

   public JmolSimpleViewer getViewer(){

       return jmolPanel.getViewer1();  
   }

   static class ApplicationCloser extends WindowAdapter {  
       public void windowClosing(WindowEvent e) {  
           System.exit(0);  
       }  
   }

   static class JmolPanel extends JPanel {  
       /**  
        *   
        */  
       private static final long serialVersionUID = -3661941083797644242L;  
       JmolViewer viewer;  
       JmolAdapter adapter;  
       JmolPanel() {  
           adapter = new SmarterJmolAdapter();  
           viewer = JmolViewer.allocateViewer(this, adapter);  
             
       }

       public <JmolSimpleViewer> JmolSimpleViewer getViewer1() {
		// TODO Auto-generated method stub
		return null;
	}

	public JmolViewer getViewer() {  
           return viewer;  
       }

       public void executeCmd(String rasmolScript){  
           viewer.evalString(rasmolScript);  
       }

       final Dimension currentSize = new Dimension();  
       final Rectangle rectClip = new Rectangle();

       public void paint(Graphics g) {  
           getSize(currentSize);  
           g.getClipBounds(rectClip);  
           viewer.renderScreenImage(g, currentSize, rectClip);  
       }  
   }

}
