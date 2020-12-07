package webcrawler;

import java.io.File;
import java.io.IOException;

import org.json.JSONException;

import bdNeo4j.ConnectNeo4j;
import publication.AcessPublications;
import publication.NewPublication;

public class InstitutionSeeds {
	
	//Gerando os Seeds
	public void generateSeeds() throws IOException, JSONException {
		

		
		//Adicionando autores de universidades 		
		DBLPCrawler crawler = new DBLPCrawler();
		
		
		
		//Conectando ao BDNeo4J
        ConnectNeo4j cn = new ConnectNeo4j("bolt://localhost:7687", "neo4j", "scisynergy");
        
        //UFMG
        //cn.addSeedsAuthorInstitution(crawler.getPageAutoresUFMG("http://ppgcc.dcc.ufmg.br/docentes/"));
		
        //UNB
        cn.addSeedsAuthorInstitution(crawler.getPageAutoresUnB("http://ppgi.unb.br/index.php?option=com_content&view=article&id=78&Itemid=471&lang=pt"));
        
        //USP
        cn.addSeedsAuthorInstitution(crawler.getPageAutoresUSP("http://www.ime.usp.br/pos-computacao/orientadores/"));
        
        //UFAM
        //cn.addSeedsAuthorInstitution(crawler.getPageAutoresUFAM("http://www.ppgi.ufam.edu.br/people-pig/corpo-docente"));
        
        //UFRN
        cn.addSeedsAuthorInstitution(crawler.getPageAutoresUFRN("http://sigaa.ufrn.br/sigaa/public/programa/equipe.jsf?lc=pt_BR&id=73"));
        
        		
        
        cn.close();
		
		
	}
}
