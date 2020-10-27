package institution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.orcid.jaxb.model.record_v2.OtherName;
import org.orcid.jaxb.model.record_v2.Record;

import bdNeo4j.ConnectNeo4j;
import desambiguation.InsertAuthorDesambiguation;
import orcid.ClientPubApiOrcid;
import webcrawler.DBLPCrawler;

public class UnBOrcid {
	
	//Carrega no BD NEO4J as informacoes dos autores que estão no ORCID
	//com base nos nomes que estão na página do programa de pos graduacao
	public void CarregaAutores() throws IOException, JAXBException{
		
		//Conectando ao BDNeo4J
        ConnectNeo4j cn = new ConnectNeo4j("bolt://localhost:7687", "neo4j", "scisynergy");
        
        ClientPubApiOrcid clt = new ClientPubApiOrcid();
    	
    	ArrayList<String> lista_unb = new DBLPCrawler().getPageAutoresUnB("http://ppgi.unb.br/index.php?option=com_content&view=article&id=78&Itemid=471&lang=pt");
    	
    	//Adicionando UnB e seus Aliases
    	cn.addInstitution("Universidade de Brasília");	
    	cn.addInstitutionAlias("University of Brasilia", cn.printInstitution("Universidade de Brasília"));
    	cn.addInstitutionAlias("University of Brasília", cn.printInstitution("Universidade de Brasília"));
    	
    	
    	for(int i=0;i<lista_unb.size();i++){
    		
    		//Arraylist com aliases da UnB para pesquisa na API Orcid
    		ArrayList<String> pesquisa_afiliation = new ArrayList<String>();
    		
    		
    		
    		pesquisa_afiliation.add("Universidade de Brasília");
    		pesquisa_afiliation.add("University of Brasilia");
    		pesquisa_afiliation.add("University of Brasília");
    		
    		//Pegando as informações do Author no Orcid
    		String orcid = clt.SearchOrcidByNameAndAfiliation(lista_unb.get(i).toString(), pesquisa_afiliation);
    		
    		//Se adiciona o autor no banco se ele existir na base ORCID
    		if(!orcid.equalsIgnoreCase("404")){
    			Record rec = clt.GetProfile(orcid);
    		
    		
    			cn.addAuthor(lista_unb.get(i).toString());
    		
    		
    			//Se o autor tiver Aliases no perfil do Orcid adiciona no BD
    			if(rec.getPerson().getOtherNames().getOtherNames()!=null){
    				List<OtherName> aliases = rec.getPerson().getOtherNames().getOtherNames();
    		
    				//Adicionando os Aliases do Author presentes no Orcid
    				for(int j=0;j<aliases.size();j++){
    			
    					cn.addAlias(aliases.get(j).getContent(), cn.printAuthor(lista_unb.get(i).toString()));
    			
    				}
    			}
    		
    		}
    		
    	}
    	
    	
    	
        //new DBLPCrawler().getPageAutoresUFMG("http://ppgcc.dcc.ufmg.br/docentes/");
    	
		
		
		
		cn.close();
	}

	//Carrega no BD NEO4J as informacoes de publicacoes que estão no ORCID dos autores
	public void CarregaBibTexAutores() throws IOException, JAXBException{
		//Conectando ao BDNeo4J
        ConnectNeo4j cn = new ConnectNeo4j("bolt://localhost:7687", "neo4j", "scisynergy");
        
        ClientPubApiOrcid clt = new ClientPubApiOrcid();
    	
    	ArrayList<String> lista_unb = new DBLPCrawler().getPageAutoresUnB("http://ppgi.unb.br/index.php?option=com_content&view=article&id=78&Itemid=471&lang=pt");
    	
    	//Adicionando UnB e seus Aliases
    	cn.addInstitution("Universidade de Brasília");	
    	cn.addInstitutionAlias("University of Brasilia", cn.printInstitution("Universidade de Brasília"));
    	cn.addInstitutionAlias("University of Brasília", cn.printInstitution("Universidade de Brasília"));
    	
    	
    	for(int i=0;i<lista_unb.size();i++){
    		
    		//Arraylist com aliases da UnB para pesquisa na API Orcid
    		ArrayList<String> pesquisa_afiliation = new ArrayList<String>();
    		
    		
    		
    		pesquisa_afiliation.add("Universidade de Brasília");
    		pesquisa_afiliation.add("University of Brasilia");
    		pesquisa_afiliation.add("University of Brasília");
    		
    		//Pegando as informações do Author no Orcid
    		String orcid = clt.SearchOrcidByNameAndAfiliation(lista_unb.get(i).toString(), pesquisa_afiliation);
    		
    		//Se adiciona o autor no banco se ele existir na base ORCID
    		if(!orcid.equalsIgnoreCase("404")){
    			Record rec = clt.GetProfile(orcid);
    		

    			
    			
    			//clt.GetWorks(orcid);
    			ArrayList<String> lista_bibtex = clt.GetWorksContributorsByOrcid(orcid);
    					//Adicionando os BibTex no NEO4J
        		if(!lista_bibtex.isEmpty()){
        			System.out.println("passou aqui");
    				InsertAuthorDesambiguation insert = new InsertAuthorDesambiguation();
    				insert.FromBibTexData(lista_bibtex);
    				//Para cada bibtex recebido trata e retorna os autores para insercao no BD
    					
    					
    			}
    		}
    		
    	}
    	
    	
    	
        //new DBLPCrawler().getPageAutoresUFMG("http://ppgcc.dcc.ufmg.br/docentes/");
    	
		
		
		
		cn.close();
		
	}


}
