package desambiguation;

import bdNeo4j.ConnectNeo4j;



//Clase que irá reunir todas as combinações de desambiguação.
public class CombinedDesambiguationAgents {
	
	//Primeira combinação de desambiguação
	public int Combination1(String autor, ConnectNeo4j cn, String[] new_authors) {

		
		
		DesambiguationTypes d = new DesambiguationTypes();
		
		//DESAMBIGUATION
		//Se o Autor não existir e nem o Alias, vai tentando outro método. Adiciona esse autor
		//Tipo 1
		Integer idd;
		idd = d.TypeOne(autor,cn);
		if(idd==-1){
			
			idd = d.TypeTwo(autor, cn);
			//Tipo 2 - Author Abreviado
			if(idd ==-1){
				
				idd = d.TypeThree(autor, cn);
				//Tipo 3 - Author na base de dados abreviado e no Bib por extenso
				
				if(idd ==-1){
						       
					idd = d.TypeFour(autor, cn);
					//Tipo 4 - Retirando os acentos

					if(idd ==-1){
						
						//Tipo 5 - Jaccard
    					idd = d.TypeFive(autor, cn);
						if(idd ==-1){
    						
							//Tipo 6 - Comparando a rede de coautoria com o possivel author
        					idd = d.TypeSix(autor, new_authors, cn);
							if(idd ==-1){

								//Tipo 7 - Verifica o primeiro e ultimo nome - 
								//Pode haver erros nesse tipo desamb, pode ter falso positivo,
								//Usar com cuidado
	        					
								//idd = d.TypeSeven(autor, cn);
    							//if(idd ==-1){
	        						
        							//Não achou nenhuma possibilidade, então adiciona esse Author
			        				cn.addAuthor(autor);
			        				return cn.printAuthor(autor);
	        						
	        					//}else{
	        						//return idd;	        						
	        					//}
								
        					}else{
        						return idd;	        						
        					}
							
    						
    					}else{
    						return idd;	        						
    					}
						
					}else{
						return idd;	        						
					}
					
				}else{
					return idd;
				}

			}else{
				return idd;
			}

		}else{		
			return idd;
		}

	}
		
	//Segunda combinação de desambiguação
	public int Combination2(String autor, ConnectNeo4j cn, String[] new_authors) {

		
		
		DesambiguationTypes d = new DesambiguationTypes();
		
		//DESAMBIGUATION
		//Se o Autor não existir e nem o Alias, vai tentando outro método. Adiciona esse autor
		//Tipo 1
		Integer idd;
		idd = d.TypeOne(autor,cn);
		if(idd==-1){
			
			idd = d.TypeTwo(autor, cn);
			//Tipo 2 - Author Abreviado
			if(idd ==-1){
				
				idd = d.TypeThree(autor, cn);
				//Tipo 3 - Author na base de dados abreviado e no Bib por extenso
				
				if(idd ==-1){
						       
					idd = d.TypeFour(autor, cn);
					//Tipo 4 - Retirando os acentos

					if(idd ==-1){
						
						//Tipo 8 - Similaridade de Cosseno
    					idd = d.TypeEight(autor, cn);
						if(idd ==-1){
    						
							//Tipo 6 - Comparando a rede de coautoria com o possivel author
        					idd = d.TypeSix(autor, new_authors, cn);
							if(idd ==-1){

								//Tipo 7 - Verifica o primeiro e ultimo nome - 
								//Pode haver erros nesse tipo desamb, pode ter falso positivo,
								//Usar com cuidado
	        					
								//idd = d.TypeSeven(autor, cn);
    							//if(idd ==-1){
	        						
        							//Não achou nenhuma possibilidade, então adiciona esse Author
			        				cn.addAuthor(autor);
			        				return cn.printAuthor(autor);
	        						
	        					//}else{
	        						//return idd;	        						
	        					//}
								
        					}else{
        						return idd;	        						
        					}
							
    						
    					}else{
    						return idd;	        						
    					}
						
					}else{
						return idd;	        						
					}
					
				}else{
					return idd;
				}

			}else{
				return idd;
			}

		}else{		
			return idd;
		}

	}
		
	//Terceira combinação de desambiguação
	public int Combination3(String autor, ConnectNeo4j cn, String[] new_authors) {

		
		
		DesambiguationTypes d = new DesambiguationTypes();
		
		//DESAMBIGUATION
		//Se o Autor não existir e nem o Alias, vai tentando outro método. Adiciona esse autor
		//Tipo 1
		Integer idd;
		idd = d.TypeOne(autor,cn);
		if(idd==-1){
			
			idd = d.TypeTwo(autor, cn);
			//Tipo 2 - Author Abreviado
			if(idd ==-1){
				
				idd = d.TypeThree(autor, cn);
				//Tipo 3 - Author na base de dados abreviado e no Bib por extenso
				
				if(idd ==-1){
						       
					idd = d.TypeFour(autor, cn);
					//Tipo 4 - Retirando os acentos

					if(idd ==-1){
						
						//Tipo 9 - Dice Distance
    					idd = d.TypeNine(autor, cn);
						if(idd ==-1){
    						
							//Tipo 6 - Comparando a rede de coautoria com o possivel author
        					idd = d.TypeSix(autor, new_authors, cn);
							if(idd ==-1){

								//Tipo 7 - Verifica o primeiro e ultimo nome - 
								//Pode haver erros nesse tipo desamb, pode ter falso positivo,
								//Usar com cuidado
	        					
								//idd = d.TypeSeven(autor, cn);
    							//if(idd ==-1){
	        						
        							//Não achou nenhuma possibilidade, então adiciona esse Author
			        				cn.addAuthor(autor);
			        				return cn.printAuthor(autor);
	        						
	        					//}else{
	        						//return idd;	        						
	        					//}
								
        					}else{
        						return idd;	        						
        					}
							
    						
    					}else{
    						return idd;	        						
    					}
						
					}else{
						return idd;	        						
					}
					
				}else{
					return idd;
				}

			}else{
				return idd;
			}

		}else{		
			return idd;
		}

	}
		
	//Primeira combinação de desambiguação
	public int Combination4(String autor, ConnectNeo4j cn, String[] new_authors) {

			
			
			DesambiguationTypes d = new DesambiguationTypes();
			
			//DESAMBIGUATION
			//Se o Autor não existir e nem o Alias, vai tentando outro método. Adiciona esse autor
			//Tipo 1
			Integer idd;
			idd = d.TypeOne(autor,cn);
			if(idd==-1){
				
				idd = d.TypeTwo(autor, cn);
				//Tipo 2 - Author Abreviado
				if(idd ==-1){
					
					idd = d.TypeThree(autor, cn);
					//Tipo 3 - Author na base de dados abreviado e no Bib por extenso
					
					if(idd ==-1){
							       
						idd = d.TypeFour(autor, cn);
						//Tipo 4 - Retirando os acentos

						if(idd ==-1){
							
							//Tipo 10 - Leveshtein
	    					idd = d.TypeTen(autor, cn);
							if(idd ==-1){
	    						
								//Tipo 6 - Comparando a rede de coautoria com o possivel author
	        					idd = d.TypeSix(autor, new_authors, cn);
								if(idd ==-1){

									//Tipo 7 - Verifica o primeiro e ultimo nome - 
									//Pode haver erros nesse tipo desamb, pode ter falso positivo,
									//Usar com cuidado
		        					
									//idd = d.TypeSeven(autor, cn);
	    							//if(idd ==-1){
		        						
	        							//Não achou nenhuma possibilidade, então adiciona esse Author
				        				cn.addAuthor(autor);
				        				return cn.printAuthor(autor);
		        						
		        					//}else{
		        						//return idd;	        						
		        					//}
									
	        					}else{
	        						return idd;	        						
	        					}
								
	    						
	    					}else{
	    						return idd;	        						
	    					}
							
						}else{
							return idd;	        						
						}
						
					}else{
						return idd;
					}

				}else{
					return idd;
				}

			}else{		
				return idd;
			}

		}
			
	//Simulação de desambiguação pelo agente executor;
	

	
	
	//AGENTE EXECUTOR 1
	// Quanto maior o valor, mais longe foi o algoritmo.
	public int SimulatedCombination1(String autor, ConnectNeo4j cn, String[] new_authors) {
	
		
		

		
		
		DesambiguationTypesAgents d = new DesambiguationTypesAgents();
		
		//DESAMBIGUATION
		//Se o Autor não existir e nem o Alias, vai tentando outro método. Adiciona esse autor
		//Quanto maior o retorno, mais longe o algoritmo vai para achar o Autor.
		Integer idd;
		idd = d.TypeOne(autor,cn);
		if(idd==-1){
			
			idd = d.TypeTwo(autor, cn);
			//Tipo 2 - Author Abreviado
			if(idd ==-1){
				
				idd = d.TypeThree(autor, cn);
				//Tipo 3 - Author na base de dados abreviado e no Bib por extenso
				
				if(idd ==-1){
						       
					idd = d.TypeFour(autor, cn);
					//Tipo 4 - Retirando os acentos

					if(idd ==-1){
						
						//Tipo 5 - Jaccard
    					idd = d.TypeFive(autor, cn);
						if(idd ==-1){
    						
							//Tipo 6 - Comparando a rede de coautoria com o possivel author
        					idd = d.TypeSix(autor, new_authors, cn);
							if(idd ==-1){

								//Tipo 7 - Verifica o primeiro e ultimo nome - 
								//Pode haver erros nesse tipo desamb, pode ter falso positivo,
								//Usar com cuidado
	        					
								//idd = d.TypeSeven(autor, cn);
    							//if(idd ==-1){
	        						
        							//Não achou nenhuma possibilidade, então adiciona esse Author
			        				//cn.addAuthor(autor);
									
			        				return 7;
	        						
	        					//}else{
	        						//return idd;	        						
	        					//}
								
        					}else{
        						
        						return 6;	        						
        					}
							
    						
    					}else{
    						
    						return 5;	        						
    					}
						
					}else{
						
						return 4;	        						
					}
					
				}else{
					
					return 3;
				}

			}else{
				
				return 2;
			}

		}else{		
			
			return 1;
		}

		
	
	}	
	
	
	
	//AGENTE EXECUTOR 2
	// Quanto maior o valor, mais longe foi o algoritmo.
		public int SimulatedCombination2(String autor, ConnectNeo4j cn, String[] new_authors) {
		
			
			

			
			
			DesambiguationTypesAgents d = new DesambiguationTypesAgents();
			
			//DESAMBIGUATION
			//Se o Autor não existir e nem o Alias, vai tentando outro método. Adiciona esse autor
			//Quanto maior o retorno, mais longe o algoritmo vai para achar o Autor.
			Integer idd;
			idd = d.TypeOne(autor,cn);
			if(idd==-1){
				
				idd = d.TypeTwo(autor, cn);
				//Tipo 2 - Author Abreviado
				if(idd ==-1){
					
					idd = d.TypeThree(autor, cn);
					//Tipo 3 - Author na base de dados abreviado e no Bib por extenso
					
					if(idd ==-1){
							       
						idd = d.TypeFour(autor, cn);
						//Tipo 4 - Retirando os acentos

						if(idd ==-1){
							
							//Tipo 5 - Similaridade de Coseno
	    					idd = d.TypeEight(autor, cn);
							if(idd ==-1){
	    						
								//Tipo 6 - Comparando a rede de coautoria com o possivel author
	        					idd = d.TypeSix(autor, new_authors, cn);
								if(idd ==-1){

									//Tipo 7 - Verifica o primeiro e ultimo nome - 
									//Pode haver erros nesse tipo desamb, pode ter falso positivo,
									//Usar com cuidado
		        					
									//idd = d.TypeSeven(autor, cn);
	    							//if(idd ==-1){
		        						
	        							//Não achou nenhuma possibilidade, então adiciona esse Author
				        				//cn.addAuthor(autor);
										
				        				return 7;
		        						
		        					//}else{
		        						//return idd;	        						
		        					//}
									
	        					}else{
	        						
	        						return 6;	        						
	        					}
								
	    						
	    					}else{
	    						
	    						return 5;	        						
	    					}
							
						}else{
							
							return 4;	        						
						}
						
					}else{
						
						return 3;
					}

				}else{
					
					return 2;
				}

			}else{		
				
				return 1;
			}

			
		
		}	
		
		
		
		//AGENTE EXECUTOR 3
		//Quanto maior o valor, mais longe foi o algoritmo.
		public int SimulatedCombination3(String autor, ConnectNeo4j cn, String[] new_authors) {
				
					
					

					
					
			DesambiguationTypesAgents d = new DesambiguationTypesAgents();
					
					//DESAMBIGUATION
					//Se o Autor não existir e nem o Alias, vai tentando outro método. Adiciona esse autor
					//Quanto maior o retorno, mais longe o algoritmo vai para achar o Autor.
					Integer idd;
					idd = d.TypeOne(autor,cn);
					if(idd==-1){
						
						idd = d.TypeTwo(autor, cn);
						//Tipo 2 - Author Abreviado
						if(idd ==-1){
							
							idd = d.TypeThree(autor, cn);
							//Tipo 3 - Author na base de dados abreviado e no Bib por extenso
							
							if(idd ==-1){
									       
								idd = d.TypeFour(autor, cn);
								//Tipo 4 - Retirando os acentos

								if(idd ==-1){
									
									//Tipo 9 - Dice Distance
			    					idd = d.TypeNine(autor, cn);
									if(idd ==-1){
			    						
										//Tipo 6 - Comparando a rede de coautoria com o possivel author
			        					idd = d.TypeSix(autor, new_authors, cn);
										if(idd ==-1){

											//Tipo 7 - Verifica o primeiro e ultimo nome - 
											//Pode haver erros nesse tipo desamb, pode ter falso positivo,
											//Usar com cuidado
				        					
											//idd = d.TypeSeven(autor, cn);
			    							//if(idd ==-1){
				        						
			        							//Não achou nenhuma possibilidade, então adiciona esse Author
						        				//cn.addAuthor(autor);
												
						        				return 7;
				        						
				        					//}else{
				        						//return idd;	        						
				        					//}
											
			        					}else{
			        						
			        						return 6;	        						
			        					}
										
			    						
			    					}else{
			    						
			    						return 5;	        						
			    					}
									
								}else{
									
									return 4;	        						
								}
								
							}else{
								
								return 3;
							}

						}else{
							
							return 2;
						}

					}else{		
						
						return 1;
					}

					
				
				}	
				
		
		//AGENTE EXECUTOR 1
		// Quanto maior o valor, mais longe foi o algoritmo.
		public int SimulatedCombination4(String autor, ConnectNeo4j cn, String[] new_authors) {
		
			
			

			
			
			DesambiguationTypesAgents d = new DesambiguationTypesAgents();
			
			//DESAMBIGUATION
			//Se o Autor não existir e nem o Alias, vai tentando outro método. Adiciona esse autor
			//Quanto maior o retorno, mais longe o algoritmo vai para achar o Autor.
			Integer idd;
			idd = d.TypeOne(autor,cn);
			if(idd==-1){
				
				idd = d.TypeTwo(autor, cn);
				//Tipo 2 - Author Abreviado
				if(idd ==-1){
					
					idd = d.TypeThree(autor, cn);
					//Tipo 3 - Author na base de dados abreviado e no Bib por extenso
					
					if(idd ==-1){
							       
						idd = d.TypeFour(autor, cn);
						//Tipo 4 - Retirando os acentos

						if(idd ==-1){
							
							//Tipo 10 - Leveshtein
	    					idd = d.TypeTen(autor, cn);
							if(idd ==-1){
	    						
								//Tipo 6 - Comparando a rede de coautoria com o possivel author
	        					idd = d.TypeSix(autor, new_authors, cn);
								if(idd ==-1){

									//Tipo 7 - Verifica o primeiro e ultimo nome - 
									//Pode haver erros nesse tipo desamb, pode ter falso positivo,
									//Usar com cuidado
		        					
									//idd = d.TypeSeven(autor, cn);
	    							//if(idd ==-1){
		        						
	        							//Não achou nenhuma possibilidade, então adiciona esse Author
				        				//cn.addAuthor(autor);
										
				        				return 7;
		        						
		        					//}else{
		        						//return idd;	        						
		        					//}
									
	        					}else{
	        						
	        						return 6;	        						
	        					}
								
	    						
	    					}else{
	    						
	    						return 5;	        						
	    					}
							
						}else{
							
							return 4;	        						
						}
						
					}else{
						
						return 3;
					}

				}else{
					
					return 2;
				}

			}else{		
				
				return 1;
			}

			
		
		}	
		
		
		
}
