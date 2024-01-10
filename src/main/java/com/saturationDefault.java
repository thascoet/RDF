package com;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.reasoner.rulesys.Rule.Parser;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.ReasonerVocabulary;


public class saturationDefault {
	// classe de saturation d'un graphe RDF (data + schema) aux formats supportés par Jena : rdf, owl, n3, ttl
	// puis écriture du modèle saturé dans un fichier .n3 (N-Triples).
	
	private Model data; // graphe RDF qui sera chargé
	private Reasoner reasoner; // Raisonneur Jena
	private Model res; // graph rdf saturé
	
	// definition des regles d'inference
	// syntaxe predefinie "[libelle: (t1), (t2) -> (t3)]"
	private String rules = 
                "to complete"
			    ;
	
	
	// import du graphe RDF situé à l'adresse dataadr dans data
	// creation de l'instance de raisonneur
	public saturationDefault(String dataadr) throws IOException {
            
		// raisonneur par défaut
                
        // get a reasoner
        reasoner = ReasonerRegistry.getRDFSReasoner(); 
        // set the parameters : "full" , "default", "simple"
        reasoner.setParameter(ReasonerVocabulary.PROPsetRDFSLevel,"default"); 
        //reasoner.setParameter(ReasonerVocabulary.PROPsetRDFSLevel,ReasonerVocabulary.RDFS_SIMPLE);
		
                
        // raisonneur personnalise
		/*
        //Creation d un raisonneur adhoc à un ensemble des regles definis dans rules   
		reasoner = new GenericRuleReasoner(Rule.parseRules(rules));
		
		// paramétrage du raisonneur en chainage avant.
		reasoner.setParameter(ReasonerVocabulary.PROPruleMode,"forward");
        */
                
                
    	//	chargement du graphe RDF
        data = RDFDataMgr.loadModel(dataadr);
        System.out.println("nombre de triplets chargés"+data.size());
	}
	
	
	
	public void SaturationDataUSchema(){
		// saturation du graphe d'entree dans res
		res = ModelFactory.createInfModel(reasoner, data);
		System.out.println("taille du graphe sature--- "+res.size());
	}
	
	
	public static void main(String[] args) throws IOException {
		// prend en entree le schema, les donnees et l'adresse du graphe sature
		
		//RDF data to saturate
		//String dataadr = args[0];
		String dataadr = "out/rdf.n3";

		//RDF saturated data address
		//String modelsatadr = args[1];
		String modelsatadr = "out/rdf_sat.n3";
		
		
		//chargement des donnees et du schema
		saturationDefault S = new saturationDefault(dataadr);
		System.out.println("Debut saturation ...");
		S.SaturationDataUSchema();
		System.out.println("Fin saturation. ");
		// ecriture des triplets dans le fichier modelsatadr
		PrintWriter writer ;
		writer = new PrintWriter(new FileWriter(modelsatadr, true));
		System.out.println("Ecriture ...");
		S.res.write(writer, "N-TRIPLE");
		writer.write("\n");
		System.out.println("Fin ecriture ...");
		writer.flush();
		writer.close();
	}

}
