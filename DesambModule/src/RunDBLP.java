import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;

import bdNeo4j.ConnectNeo4j;
import desambiguation.DesambiguationTypes;
import desambiguation.ShortenerName;
import desambiguation.StringDesambiguation;
import publication.*;
import webcrawler.DBLPCrawler;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.*;


public class RunDBLP {

	public static void main(String[] args) throws IOException {

		/*
		
		//Conectando ao BDNeo4J
        //ConnectNeo4j cn = new ConnectNeo4j("bolt://localhost:7687", "neo4j", "scisynergy");
        
       
		ShortenerName r = new ShortenerName();
		DesambiguationTypes d = new DesambiguationTypes();
		
		StringDesambiguation st = new StringDesambiguation();
		
		String authorteste1 = "Aletéia P. F. Araújo";
		String authorteste2 = "Aleteia Patricia Favacho de Araujo";
		String authorteste3 = "Aleteia P. F. Araujo";
		
		String authorteste4 = "Li Weigang";
		String authorteste5 = "Li Weng";
	
		
		//cn.addAuthor("Aletéia P. F. Araújo");
		//cn.addAlias("Aleteia Patricia Favacho de Araujo", d.TypeOne("Aletéia P. F. Araújo"));
		//cn.addAuthor(authorteste2);
		//System.out.println(d.TypeOne("Aletéia P. F. Araújo"));
		
		//System.out.println("abreviando");
		
		//System.out.println(StringUtils.stripAccents(authorteste1));
		
		System.out.println("Comparação de autores:");
		System.out.println(authorteste4);
		System.out.println(authorteste5);
		System.out.println("===================================");
		System.out.println("Coeficiente Jaccard: " + st.Jaccard(authorteste4, authorteste5));
		System.out.println("LevenshteinDistance: " + st.Leveshtein(authorteste4, authorteste5));
		
		*/
		
		
		
		
		//Adicionando autores de universidades 
		//UFMG
		
		DBLPCrawler crawler = new DBLPCrawler();
		
		//UNB
		
	
		
		//Acessando a pasta onde estão os bibtex
		AcessPublications a = new AcessPublications();
		
		//Usada para fazer a leitura do BibTex
		NewPublication n = new NewPublication();
		
		File[] files = a.PathOfBibTex();
		//Para cada Bibtex fazer a operacao de escrita no neo4j
		
		//Conectando ao BDNeo4J
        ConnectNeo4j cn = new ConnectNeo4j("bolt://localhost:7687", "neo4j", "scisynergy");
        
        //UFMG
        //cn.addSeedsAuthorInstitution(crawler.getPageAutoresUFMG("http://ppgcc.dcc.ufmg.br/docentes/"));
		
        //UNB
        cn.addSeedsAuthorInstitution(crawler.getPageAutoresUnB("http://ppgi.unb.br/index.php?option=com_content&view=article&id=78&Itemid=471&lang=pt"));
        
        //USP
        cn.addSeedsAuthorInstitution(crawler.getPageAutoresUSP("http://www.ime.usp.br/dcc/pos/orientadores"));
        
        //UFAM
        //cn.addSeedsAuthorInstitution(crawler.getPageAutoresUFAM("http://www.ppgi.ufam.edu.br/people-pig/corpo-docente"));
        
        //UFRN
        cn.addSeedsAuthorInstitution(crawler.getPageAutoresUFRN("http://sigaa.ufrn.br/sigaa/public/programa/equipe.jsf?lc=pt_BR&id=73"));
        
        		
		
		
        for(int i=0;i<files.length;i++){
			
			String path_of_bib = files[i].toString();
			ArrayList<String[]> authors = n.LeBibTex(path_of_bib);
			
			DesambiguationTypes d = new DesambiguationTypes();
			
			
			
	        //Adicionando os autores, acessa o arraylist com as publications
	        for(int j=0;j<authors.size();j++){
	        	
	        	//Determinados autores da publicacao
	        	String[] new_authors = authors.get(j);
	        	
	        	//ArrayList que guardará o ID dos autores para executar as CoAuthorias
	        	ArrayList<Integer> AuthorIDList = new ArrayList<Integer>();;
	        	
	        	for(int k=0;k<new_authors.length;k++){
	        		
	        		System.out.println(new_authors[k]);
	        		//Adicionando o author no bdgrafo, fazendo um método de desambiguation

	        		//DESAMBIGUATION
	        		//Se o Autor não existir e nem o Alias, vai tentando outro método. Adiciona esse autor
	        		//Tipo 1
	        		Integer idd;
	        		idd = d.TypeOne(new_authors[k],cn);
	        		if(idd==-1){
	        			
	        			idd = d.TypeTwo(new_authors[k], cn);
	        			//Tipo 2 - Author Abreviado
	        			if(idd ==-1){
	        				
	        				idd = d.TypeThree(new_authors[k], cn);
	        				//Tipo 3 - Author na base de dados abreviado e no Bib por extenso
	        				if(idd ==-1){
	        						       
	        					idd = d.TypeFour(new_authors[k], cn);
	        					//Tipo 4 - Retirando os acentos
	        					if(idd ==-1){
	        						
	        						//Tipo 5 - Jaccard and Leveshtein
		        					idd = d.TypeFive(new_authors[k], cn);
	        						if(idd ==-1){
		        						
	        							//Tipo 6 - Comparando a rede de coautoria com o possivel author
			        					idd = d.TypeSix(new_authors[k], new_authors, cn);
	        							if(idd ==-1){

	        								//Tipo 7 - Verifica o primeiro e ultimo nome - 
	        								//Pode haver erros nesse tipo desamb, pode ter falso positivo,
	        								//Usar com cuidado
				        					
	        								//idd = d.TypeSeven(new_authors[k], cn);
		        							//if(idd ==-1){
				        						
			        							//Não achou nenhuma possibilidade, então adiciona esse Author
						        				cn.addAuthor(new_authors[k]);
						        				AuthorIDList.add(cn.printAuthor(new_authors[k]));
				        						
				        					//}else{
				        						//AuthorIDList.add(idd);	        						
				        					//}
	        								
			        					}else{
			        						AuthorIDList.add(idd);	        						
			        					}
	        							
		        						
		        					}else{
		        						AuthorIDList.add(idd);	        						
		        					}
	        						
	        					}else{
	        						AuthorIDList.add(idd);	        						
	        					}
	        					
	        				}else{
	        					AuthorIDList.add(idd);
	        				}
	
	        			}else{
	        				AuthorIDList.add(idd);
	        			}
	
	        		}else{		
	        			AuthorIDList.add(idd);
	        		}

	        	}
	        	
	        	
	        	//Adicionando coautorias, percorrendo o ArrayList
	        	for(int p=0;p<AuthorIDList.size();p++){
	        		
	        		for(int q=0;q<AuthorIDList.size();q++){
		        		if(p!=q){
		        			
		        			cn.addCoAuthor(AuthorIDList.get(p), AuthorIDList.get(q));
		        		}
		        	}

	        	}

	        }
	        
		}
		
		
		
		//*/
		cn.close();
	}


}
