package com;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.util.FileManager;


import java.io.*;

public class Translator {
  
    
    public static void main (String args[]) {
    	// String dataInputFileName  = "./modelesat.n3";
    	// chemin complet vers le fichier d'entrée format n3
    	String dataadr = args[0];
    	// chemin complet vers le fichier de sortie format xml
    	String xmladr = args[1];
    	
        Model data = ModelFactory.createDefaultModel();

        //InputStream in = FileManager.get().open(dataadr);
        data = RDFDataMgr.loadModel(dataadr,Lang.NTRIPLES);
      
        // write it to standard out with N-TRIPLE, RDF/XML or RDF/XML-ABBREV
        data.write(System.out, "RDF/XML");    
        
        // ecriture des triplets dans le fichier xmladr
     	PrintWriter writer ;
     	try {
			writer = new PrintWriter(new FileWriter(xmladr, true));
			data.write(writer, "RDF/XML");
	     	writer.write("\n");
	     	System.out.println("End");
	     	writer.flush();
	     	writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     	
    }
}
