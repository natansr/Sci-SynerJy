package desambiguation;

import java.io.IOException;
import java.util.ArrayList;

import bdNeo4j.ConnectNeo4j;
import publication.NewPublication;

//Classe que insere os autores com metodos de desambiguacao a partir de varias fontes
public class InsertAuthorDesambiguation {
	
	public void FromBibTexData(ArrayList<String> list_bibtex) throws IOException{
		//Conectando ao BDNeo4J
        ConnectNeo4j cn = new ConnectNeo4j("bolt://localhost:7687", "neo4j", "scisynergy");
        
        //Para todos trabalho bibtex da lista recebida faz o tratamento 

			
        	//Usada para fazer a leitura do BibTex padrão ORCID
			NewPublication n = new NewPublication();
			ArrayList<String[]> authors = n.LeContributorsOrcid(list_bibtex);
			
			DesambiguationTypes d = new DesambiguationTypes();
			
			
			
	        //Adicionando os autores, acessa o arraylist com as publications
	        for(int j=0;j<authors.size();j++){
	        	
	        	//Determinados autores da publicacao
	        	String[] new_authors = authors.get(j);
	        	
	        	//ArrayList que guardará o ID dos autores para executar as CoAuthorias
	        	ArrayList<Integer> AuthorIDList = new ArrayList<Integer>();;
	        	
	        	for(int k=0;k<new_authors.length;k++){
	        		
	        		
	        		//Se o nome tiver virgula, muda para padrão BR
	            	if(new_authors[k].contains(",")){
	            		
	            		String[] part3_authors = new_authors[k].split(",");
	            		
	            		new_authors[k] = part3_authors[1].trim() + " " + part3_authors[0].trim();
	            		
	            	}
	        		
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
				        					idd = d.TypeSeven(new_authors[k], cn);
		        							if(idd ==-1){
				        						
			        							//Não achou nenhuma possibilidade, então adiciona esse Author
						        				cn.addAuthor(new_authors[k]);
						        				AuthorIDList.add(cn.printAuthor(new_authors[k]));
				        						
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
	        
        //}
		//*/
		cn.close();
		
	}
}
