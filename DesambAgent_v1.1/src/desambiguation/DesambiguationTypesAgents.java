package desambiguation;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.CosineSimilarity;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;

import bdNeo4j.ConnectNeo4j;


//Classe que irá englobar algumas metodologias de desambiguação
public class DesambiguationTypesAgents {
	
	//Nesse primeiro tipo é pesquisado se existe um Autor ou Alias correspondente
		//Se existir retorna o ID, senão retorna -1
		public Integer TypeOne (String name, ConnectNeo4j cn){
			

			//Pesquisando o Author ou Alias
					
					
					//Procurando Author
					Integer id = cn.printAuthor(name);
					if(id==-1){
						
						//Procurando Alias
						id = cn.printAuthorAlias(name);
						
						if(id==-1){
							return -1;
						}else{
							
							
							return id;
						}

					}else{
						
						return id;
					}
					
					
		}

		//Nesse segundo tipo é pesquisado se existe um Autor ou Alias correspondente com nome abreviado
		//Se existir retorna o ID, senão retorna -1
		public Integer TypeTwo (String name, ConnectNeo4j cn){
			
			//Abreviando o nome
			ShortenerName s = new ShortenerName();
			String shortname = s.Shortener(name);
			
			
			
			//Procurando Author
			Integer id = cn.printAuthor(shortname);
			if(id==-1){
				
				//Procurando Alias
				id = cn.printAuthorAlias(shortname);
				
				if(id==-1){
					return -1;
				}else{
					
					return id;
				}

			}else{

				return id;
			}
			
			
		}

		//Nesse terceiro tipo é recebido um Author e então verifica se o Author na base de dados está abreviado
		public Integer TypeThree (String name, ConnectNeo4j cn){
			
			//Abreviando o nome
					ShortenerName s = new ShortenerName();
					String shortname = s.Shortener(name);
					
					//System.out.println("abreviado: " + shortname);
					
					//Procurando Author
					Integer id = cn.printAuthorwShortName(shortname);
					if(id==-1){
						
						//Procurando Alias
						id = cn.printAuthorAlias(shortname);
						
						if(id==-1){
							return -1;
						}else{
							
							return id;
						}
						
						
						//return id;

					}else{
						
						return id;
					}
					
			
			
		}

		//Nesse quarto é utilizada retirada dos acentos
		public Integer TypeFour (String name, ConnectNeo4j cn){
			//Retirando os acentos
			
			String strip_acc_name = StringUtils.stripAccents(name);
			
			//System.out.println("abreviado: " + shortname);
			
			//Procurando Author
			Integer id = cn.printAuthor(strip_acc_name);
			if(id==-1){
				
				//Procurando Alias
				id = cn.printAuthorAlias(strip_acc_name);
				
				if(id==-1){
					return -1;
				}else{
					
					return id;
				}
				
				

			}else{
				
				
				return id;
			}
		}
		
		//Nesse quinto é utilizada Jaccard
		//Vai receber o nome, pesquisar os nomes similares no BD e então retornar
		//o Author que tem o maior coeficiente Jaccard
		public Integer TypeFive (String name, ConnectNeo4j cn){
			
			//Obtendo firstname
			String[] name_split = name.split(" ");
			String shortname = name_split[0].trim();
			
			StringDesambiguation s = new StringDesambiguation();
			StatementResult srAuthor = cn.returnAuthorsStartWith(shortname);
			StatementResult srAlias = cn.returnAliasStartWith(shortname);
			
			
			StatementResult srAuthorAccents = cn.returnAuthorsStartWith(StringUtils.stripAccents(shortname));
			StatementResult srAliasAccents = cn.returnAliasStartWith(StringUtils.stripAccents(shortname));
			
			
			
			
			Double idAuthor = -1.0;
			Double valor_desambiguacao = Double.MAX_VALUE;
			
			// Each Cypher execution returns a stream of records.
	        while (srAuthor.hasNext())
	        {
	            Record record = srAuthor.next();
	            
	            Double jaccard = (1-s.Jaccard(record.get("name").asString(), name));
	            
	            Double valor = jaccard; 

	            if((valor<valor_desambiguacao) && (jaccard<=0.10)){
	                
	            //if((valor<valor_desambiguacao) && (jaccard<=0.15) && (leveshtein<7)){
	            	valor_desambiguacao = valor;
	            	idAuthor = record.get("id").asDouble();
	            }
	            
	        }
	        
	        while (srAlias.hasNext())
	        {
	            Record record = srAlias.next();
	            Double jaccard = (1-s.Jaccard(record.get("name").asString(), name));
	            
	            Double valor = jaccard; 

	            if((valor<valor_desambiguacao) && (jaccard<=0.10)){
	                
	            //if((valor<valor_desambiguacao) && (jaccard<=0.15) && (leveshtein<7)){
	            	valor_desambiguacao = valor;
	            	idAuthor = record.get("id").asDouble();
	            }
	            
	        }
	        
	        
	        //Sem acento
	        
	        
	     // Each Cypher execution returns a stream of records.
	        while (srAuthorAccents.hasNext())
	        {
	            Record record = srAuthorAccents.next();
	            
	            Double jaccard = (1-s.Jaccard(record.get("name").asString(), name));
	            
	            Double valor = jaccard; 

	            if((valor<valor_desambiguacao) && (jaccard<=0.10)){
	                
	            //if((valor<valor_desambiguacao) && (jaccard<=0.15) && (leveshtein<7)){
	            	valor_desambiguacao = valor;
	            	idAuthor = record.get("id").asDouble();
	            }
	            
	        }
	        
	        while (srAliasAccents.hasNext())
	        {
	            Record record = srAliasAccents.next();
	            Double jaccard = (1-s.Jaccard(record.get("name").asString(), name));
	            
	            Double valor = jaccard; 

	            if((valor<valor_desambiguacao) && (jaccard<=0.10)){
	                
	            //if((valor<valor_desambiguacao) && (jaccard<=0.15) && (leveshtein<7)){
	            	valor_desambiguacao = valor;
	            	idAuthor = record.get("id").asDouble();
	            }
	            
	        }
	        
	        
	        
	        
	        
	        return idAuthor.intValue();
			
			
		}
		
		
		/*
		
		//Nesse quinto são utilizadas Jaccard e Leveshtein
			//Vai receber o nome, pesquisar os nomes similares no BD e então retornar
			//o Author que tem o maior coeficiente Jaccard e o menor Leveshtein
			public Integer TypeFive (String name, ConnectNeo4j cn){
				
				//Obtendo firstname
				String[] name_split = name.split(" ");
				String shortname = name_split[0].trim();
				
				StringDesambiguation s = new StringDesambiguation();
				StatementResult srAuthor = cn.returnAuthorsStartWith(shortname);
				StatementResult srAlias = cn.returnAliasStartWith(shortname);
				
				Double idAuthor = -1.0;
				Double valor_desambiguacao = Double.MAX_VALUE;
				
				// Each Cypher execution returns a stream of records.
		        while (srAuthor.hasNext())
		        {
		            Record record = srAuthor.next();
		            
		            Double jaccard = (1-s.Jaccard(record.get("name").asString(), name));
		            Integer leveshtein = s.Leveshtein(record.get("name").asString(), name);
		            
		            Double valor = jaccard + leveshtein; 

		            if((valor<valor_desambiguacao) && (jaccard<=0.10) && (leveshtein<=5)){
		                
		            //if((valor<valor_desambiguacao) && (jaccard<=0.15) && (leveshtein<7)){
		            	valor_desambiguacao = valor;
		            	idAuthor = record.get("id").asDouble();
		            }
		            
		        }
		        
		        while (srAlias.hasNext())
		        {
		            Record record = srAlias.next();
		            Double jaccard = (1-s.Jaccard(record.get("name").asString(), name));
		            Integer leveshtein = s.Leveshtein(record.get("name").asString(), name);
		            
		            Double valor = jaccard + leveshtein; 

		            if((valor<valor_desambiguacao) && (jaccard<=0.10) && (leveshtein<=5)){
		                
		            //if((valor<valor_desambiguacao) && (jaccard<=0.15) && (leveshtein<7)){
		            	valor_desambiguacao = valor;
		            	idAuthor = record.get("id").asDouble();
		            }
		            
		        }
		        
		        //Se achou o Author então adiciona o Alias
		        if(idAuthor.intValue()!=-1){
		        	cn.addAlias(name, idAuthor.intValue());
		        	cn.addAlias(StringUtils.stripAccents(name),idAuthor.intValue());
					
		        }
		        
		        return idAuthor.intValue();
				
				
			}
		
		
		*/

		
		//Nesse sexto tipo são verificadas as coautorias do author para desambiguar,
		public Integer TypeSix (String name, String[] co_authors, ConnectNeo4j cn){
			
			//Obtendo firstname
			String[] name_split = name.split(" ");
			String shortname = name_split[0].trim();
			String lastname = name_split[name_split.length-1].trim();
			
			Integer id = -1;
			
			Integer co_author_hits = 0;
			
			String shortnameDot = shortname.trim().charAt(0) + ".";
			
			//Para cada coAuthor do Author recebido procurar na rede social
			for(int i=0;i<co_authors.length;i++){
				
				StatementResult coAuthors = cn.returnCoAuthors(shortname, lastname);
				StatementResult coAlias = cn.returnCoAuthorsAlias(shortname, lastname);
				
				//Procurando com o primeiro nome abreviado também
				StatementResult coAuthorsDot = cn.returnCoAuthors(shortnameDot, lastname);
				StatementResult coAliasDot = cn.returnCoAuthorsAlias(shortnameDot, lastname);
				
				//Comparando name de Author
				while(coAuthors.hasNext()){
					Record record = coAuthors.next();
					
					//Comparando os coautores com os possiveis coautores obtidos na rede social
					if(co_authors[i].equalsIgnoreCase(record.get("name").asString())){
						co_author_hits ++;
						id = record.get("id").asInt();
					}
					
				}
				
				//Comparando name de Author com primeiro nome abreviado
				while(coAuthorsDot.hasNext()){
					Record record = coAuthorsDot.next();
					
					//Comparando os coautores com os possiveis coautores obtidos na rede social
					if(co_authors[i].equalsIgnoreCase(record.get("name").asString())){
						co_author_hits ++;
						id = record.get("id").asInt();
					}
					
				}
				
			
				
				//Comparando name de Alias de Author
				while(coAlias.hasNext()){
					Record record = coAlias.next();
					
					//Comparando os coautores com os possiveis coautores obtidos na rede social
					if(co_authors[i].equalsIgnoreCase(record.get("name").asString())){
						co_author_hits ++;
						id = record.get("id").asInt();
					}
					
				}
				
				//Comparando name de Alias de Author com primeiro nome abreviado
				while(coAliasDot.hasNext()){
					Record record = coAliasDot.next();
					
					//Comparando os coautores com os possiveis coautores obtidos na rede social
					if(co_authors[i].equalsIgnoreCase(record.get("name").asString())){
						co_author_hits ++;
						id = record.get("id").asInt();
					}
					
				}
				
			}
			
			//Se a quantidade de coautores for maior ou igual que 2 retorna e adiciona esse author como Alias
			if((co_author_hits>=2) && (id!=-1)){
				
				return id;
			}
			
			return -1;
		}
		
		//Procurando primeiro e ultimo nome
		public Integer TypeSeven(String name, ConnectNeo4j cn){
			
			ShortenerName s = new ShortenerName();
			String firstname = s.GetFirstName(name);
			String lastname = s.GetLastName(name);
			
			String finalname = firstname + " " + lastname;
			
			finalname = finalname.trim();
			
			
			//Procurando Author
			Integer id = cn.printAuthorsFirstLastName(firstname, lastname);
			if(id==-1){
						
				//Procurando Alias
				id = cn.printAliasFirstLastName(firstname, lastname);
						
				if(id==-1){
					return -1;
				}else{
					return id;
				}

			}else{
						
				return id;
			}
			
		}
		
		//Coeficiente de Coseno
		public Integer TypeEight(String name, ConnectNeo4j cn) {
			
			
			
			//Obtendo firstname
					String[] name_split = name.split(" ");
					String shortname = name_split[0].trim();
					
					StatementResult srAuthor = cn.returnAuthorsStartWith(shortname);
					StatementResult srAlias = cn.returnAliasStartWith(shortname);
					
					StatementResult srAuthorAccents = cn.returnAuthorsStartWith(StringUtils.stripAccents(shortname));
					StatementResult srAliasAccents = cn.returnAliasStartWith(StringUtils.stripAccents(shortname));
					
					
					
					
					Double idAuthor = -1.0;
					Double valor_desambiguacao = -2.0;
					
					// Each Cypher execution returns a stream of records.
			        while (srAuthor.hasNext())
			        {
			            Record record = srAuthor.next();
			            
			            String nome_autor_bd = record.get("name").asString();
			            
			            //Tratando similaridade de Coseno
			            Map<CharSequence, Integer> vector1 = new HashMap<>();
			    		Map<CharSequence, Integer> vector2 = new HashMap<>();

			    		for (String token : name.split(" ")) {
			    		    vector1.put(token, vector1.getOrDefault(token, 0) + 1);
			    		}
			            
			    		for (String token : nome_autor_bd.split(" ")) {
			    		    vector2.put(token, vector2.getOrDefault(token, 0) + 1);
			    		}
			            
			            
			            
			            
			            CosineSimilarity cosine = new CosineSimilarity();

			            
			            Double valor_cosine = cosine.cosineSimilarity(vector1, vector2);
			            

			            if((valor_cosine>=0.6) && (valor_cosine > valor_desambiguacao)){
			                
			            //if((valor<valor_desambiguacao) && (jaccard<=0.15) && (leveshtein<7)){
			            	
			            	valor_desambiguacao = valor_cosine;
			            	idAuthor = record.get("id").asDouble();
			            }
			            
			        }
			        
			        while (srAlias.hasNext())
			        {
			        	Record record = srAlias.next();
			            
			            String nome_autor_bd = record.get("name").asString();
			            
			            //Tratando similaridade de Coseno
			            Map<CharSequence, Integer> vector1 = new HashMap<>();
			    		Map<CharSequence, Integer> vector2 = new HashMap<>();

			    		for (String token : name.split(" ")) {
			    		    vector1.put(token, vector1.getOrDefault(token, 0) + 1);
			    		}
			            
			    		for (String token : nome_autor_bd.split(" ")) {
			    		    vector2.put(token, vector2.getOrDefault(token, 0) + 1);
			    		}
			            
			            
			            
			            
			            CosineSimilarity cosine = new CosineSimilarity();

			            
			            Double valor_cosine = cosine.cosineSimilarity(vector1, vector2);
			            

			            if((valor_cosine>=0.6) && (valor_cosine > valor_desambiguacao)){
			                
				            //if((valor<valor_desambiguacao) && (jaccard<=0.15) && (leveshtein<7)){
				            	
				            	valor_desambiguacao = valor_cosine;
				            	idAuthor = record.get("id").asDouble();
				        }
			        
					
			        }
			        
			        
			        
			        
			        //Sem acento
			        
			     // Each Cypher execution returns a stream of records.
			        while (srAuthorAccents.hasNext())
			        {
			            Record record = srAuthorAccents.next();
			            
			            String nome_autor_bd = record.get("name").asString();
			            
			            //Tratando similaridade de Coseno
			            Map<CharSequence, Integer> vector1 = new HashMap<>();
			    		Map<CharSequence, Integer> vector2 = new HashMap<>();

			    		for (String token : name.split(" ")) {
			    		    vector1.put(token, vector1.getOrDefault(token, 0) + 1);
			    		}
			            
			    		for (String token : nome_autor_bd.split(" ")) {
			    		    vector2.put(token, vector2.getOrDefault(token, 0) + 1);
			    		}
			            
			            
			            
			            
			            CosineSimilarity cosine = new CosineSimilarity();

			            
			            Double valor_cosine = cosine.cosineSimilarity(vector1, vector2);
			            

			            if((valor_cosine>=0.6) && (valor_cosine > valor_desambiguacao)){
			                
			            //if((valor<valor_desambiguacao) && (jaccard<=0.15) && (leveshtein<7)){
			            	
			            	valor_desambiguacao = valor_cosine;
			            	idAuthor = record.get("id").asDouble();
			            }
			            
			        }
			        
			        while (srAliasAccents.hasNext())
			        {
			        	Record record = srAliasAccents.next();
			            
			            String nome_autor_bd = record.get("name").asString();
			            
			            //Tratando similaridade de Coseno
			            Map<CharSequence, Integer> vector1 = new HashMap<>();
			    		Map<CharSequence, Integer> vector2 = new HashMap<>();

			    		for (String token : name.split(" ")) {
			    		    vector1.put(token, vector1.getOrDefault(token, 0) + 1);
			    		}
			            
			    		for (String token : nome_autor_bd.split(" ")) {
			    		    vector2.put(token, vector2.getOrDefault(token, 0) + 1);
			    		}
			            
			            
			            
			            
			            CosineSimilarity cosine = new CosineSimilarity();

			            
			            Double valor_cosine = cosine.cosineSimilarity(vector1, vector2);
			            

			            if((valor_cosine>=0.6) && (valor_cosine > valor_desambiguacao)){
			                
				            //if((valor<valor_desambiguacao) && (jaccard<=0.15) && (leveshtein<7)){
				            	
				            	valor_desambiguacao = valor_cosine;
				            	idAuthor = record.get("id").asDouble();
				        }
			        
					
			        }
			        
			        

		
			        return idAuthor.intValue();
		}
		
		
		//Dice Distance
		public Integer TypeNine(String name, ConnectNeo4j cn) {
			
			
			
			//Obtendo firstname
					String[] name_split = name.split(" ");
					String shortname = name_split[0].trim();
					
					
					StatementResult srAuthor = cn.returnAuthorsStartWith(shortname);
					StatementResult srAlias = cn.returnAliasStartWith(shortname);
					
					
					StatementResult srAuthorAccents = cn.returnAuthorsStartWith(StringUtils.stripAccents(shortname));
					StatementResult srAliasAccents = cn.returnAliasStartWith(StringUtils.stripAccents(shortname));
					
					
					
					
					
					
					Double idAuthor = -1.0;
					Double valor_desambiguacao = -2.0;
					
					// Each Cypher execution returns a stream of records.
			        while (srAuthor.hasNext())
			        {
			            Record record = srAuthor.next();
			            
			            String nome_autor_bd = record.get("name").asString();
			            
			            //Tratando Dice Distance
			            

			          
	          
			            Double valor_dice = StringDesambiguation.diceCoefficient(name, nome_autor_bd );	            

			            if((valor_dice>=0.7) && (valor_dice > valor_desambiguacao)){
			               
			            	
			            	valor_desambiguacao = valor_dice;
			            	idAuthor = record.get("id").asDouble();
			            }
			            
			        }
			        
			        while (srAlias.hasNext())
			        {
			        	Record record = srAlias.next();
			            
			            
			            String nome_autor_bd = record.get("name").asString();
			            
			            //Tratando Dice Distance
			            

			            CosineSimilarity cosine = new CosineSimilarity();
	          
			            Double valor_dice = StringDesambiguation.diceCoefficient(name, nome_autor_bd );	            

			            if((valor_dice>=0.7) && (valor_dice > valor_desambiguacao)){
			               
			            	
			            	valor_desambiguacao = valor_dice;
			            	idAuthor = record.get("id").asDouble();
			            }
			        
					
			        }
			        
			        
			     // Sem Acentos
			        while (srAuthorAccents.hasNext())
			        {
			            Record record = srAuthorAccents.next();
			            
			            String nome_autor_bd = record.get("name").asString();
			            
			            //Tratando Dice Distance
			            

			          
	          
			            Double valor_dice = StringDesambiguation.diceCoefficient(name, nome_autor_bd );	            

			            if((valor_dice>=0.7) && (valor_dice > valor_desambiguacao)){
			               
			            	
			            	valor_desambiguacao = valor_dice;
			            	idAuthor = record.get("id").asDouble();
			            }
			            
			        }
			        
			        while (srAliasAccents.hasNext())
			        {
			        	Record record = srAliasAccents.next();
			            
			            
			            String nome_autor_bd = record.get("name").asString();
			            
			            //Tratando Dice Distance
			            

			            CosineSimilarity cosine = new CosineSimilarity();
	          
			            Double valor_dice = StringDesambiguation.diceCoefficient(name, nome_autor_bd );	            

			            if((valor_dice>=0.7) && (valor_dice > valor_desambiguacao)){
			               
			            	
			            	valor_desambiguacao = valor_dice;
			            	idAuthor = record.get("id").asDouble();
			            }
			        
					
			        }
			        
			        

			        return idAuthor.intValue();
		}
		
		
		//Leveshtein
		public Integer TypeTen (String name, ConnectNeo4j cn){
			
			//Obtendo firstname
			String[] name_split = name.split(" ");
			String shortname = name_split[0].trim();
			
			StringDesambiguation s = new StringDesambiguation();
			StatementResult srAuthor = cn.returnAuthorsStartWith(shortname);
			StatementResult srAlias = cn.returnAliasStartWith(shortname);
			
			StatementResult srAuthorAccents = cn.returnAuthorsStartWith(StringUtils.stripAccents(shortname));
			StatementResult srAliasAccents = cn.returnAliasStartWith(StringUtils.stripAccents(shortname));
			

			
			Double idAuthor = -1.0;
			Integer valor_desambiguacao = Integer.MAX_VALUE;
			
			// Each Cypher execution returns a stream of records.
	        while (srAuthor.hasNext())
	        {
	            Record record = srAuthor.next();
	            
	            Integer leveshtein = s.Leveshtein(record.get("name").asString(), name);
	            
	            Integer valor =  leveshtein; 

	            if((valor<valor_desambiguacao) &&  (leveshtein<3)){
	                
	            //if((valor<valor_desambiguacao) && (jaccard<=0.15) && (leveshtein<7)){
	            	valor_desambiguacao = valor;
	            	idAuthor = record.get("id").asDouble();
	            }
	            
	        }
	        
	        while (srAlias.hasNext())
	        {
	            Record record = srAlias.next();
	            Integer leveshtein = s.Leveshtein(record.get("name").asString(), name);
	            
	            Integer valor =  leveshtein; 

	            if((valor<valor_desambiguacao) &&  (leveshtein<3)){
	                
	            //if((valor<valor_desambiguacao) && (jaccard<=0.15) && (leveshtein<7)){
	            	valor_desambiguacao = valor;
	            	idAuthor = record.get("id").asDouble();
	            }
	            
	        }
	        
	        
	        //Agora sem acentos
	        while (srAuthorAccents.hasNext())
	        {
	            Record record = srAuthorAccents.next();
	            
	            Integer leveshtein = s.Leveshtein(record.get("name").asString(), name);
	            
	            Integer valor =  leveshtein; 

	            if((valor<valor_desambiguacao) &&  (leveshtein<3)){
	                
	            //if((valor<valor_desambiguacao) && (jaccard<=0.15) && (leveshtein<7)){
	            	valor_desambiguacao = valor;
	            	idAuthor = record.get("id").asDouble();
	            }
	            
	        }
	        
	        while (srAliasAccents.hasNext())
	        {
	            Record record = srAliasAccents.next();
	            Integer leveshtein = s.Leveshtein(record.get("name").asString(), name);
	            
	            Integer valor =  leveshtein; 

	            if((valor<valor_desambiguacao) &&  (leveshtein<3)){
	                
	            //if((valor<valor_desambiguacao) && (jaccard<=0.15) && (leveshtein<7)){
	            	valor_desambiguacao = valor;
	            	idAuthor = record.get("id").asDouble();
	            }
	            
	        }
	        
	        

	        
	        return idAuthor.intValue();
			
			
		}

		
		
		
	
	
	
}

