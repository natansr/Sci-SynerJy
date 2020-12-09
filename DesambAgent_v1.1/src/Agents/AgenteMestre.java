package Agents;


import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import bdNeo4j.ConnectNeo4j;
import desambiguation.CombinedDesambiguationAgents;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import publication.AcessPublications;
import publication.NewPublication;
import publication.Publicacao;
import publication.WriterFile;
import publication.Autor;
import webcrawler.DBLPCrawler;
import webcrawler.InstitutionSeeds;


import java.util.LinkedList; 
import java.util.Queue;
import java.util.Random;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;








public class AgenteMestre extends Agent {
	
	int cont=0;
	int cont2 = 0;
	
	//Configuração do agente
	protected void setup() {
		System.out.println("Alo Mundo! ");
		System.out.println("Meu nome: "+ getLocalName()); 
	
		 ConnectNeo4j cn = new ConnectNeo4j("bolt://localhost:7687", "neo4j", "scisynergy");
		
	
		 //Fila com os autores e suas redes de co_autoria por publicação.
		 Queue<Autor> fila_autores = new LinkedList<>(); 
		 
		 
		 //Fila com as publicações
		 Queue<Publicacao> fila_publicacao = new LinkedList<>(); 
		 
		//Chamando o metodo que gera os seeds
		InstitutionSeeds isee = new InstitutionSeeds();
	
		
		//Gerando os Seeds
		addBehaviour(new SimpleBehaviour(){
			@Override
			public void action() {
				try {
					isee.generateSeeds();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public boolean done() {
				// TODO Auto-generated method stub
				return true;
			}
		});
		
		
		
		//Pega os autores de cada publicação para enviar para os agentes executores.
				addBehaviour(new SimpleBehaviour() {
					@Override
					public void action() {
						
						
						
						//Acessando a pasta onde estão os bibtex
						AcessPublications a = new AcessPublications();
						
						//Usada para fazer a leitura do BibTex
						NewPublication n = new NewPublication();
						
						File[] files = a.PathOfBibTex();
						//Para cada Bibtex fazer a operacao de escrita no neo4j
						
						
						
						
						
						
				        for(int i=0;i<files.length;i++){
							
							String path_of_bib = files[i].toString();
							ArrayList<String[]> authors = new ArrayList<String[]>();
							try {
								authors = n.LeBibTex(path_of_bib);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							

							
							
							
					        //Adicionando os autores, acessa o arraylist com as publications
					        for(int j=0;j<authors.size();j++){
					        	
					        	//Determinados autores da publicacao
					        	String[] new_authors = authors.get(j);
					        	
					        	
					        	Publicacao pub = new Publicacao();
					        	

					        
					        	for(int k=0;k<new_authors.length;k++){
					        		
					        		//Adicionando o autor e suas co_autorias.
					        		Autor au = new Autor();
					        		au.setNome(new_authors[k]);
					        		au.setRede_coautoria(new_authors);
					        		fila_autores.add(au);
					        		
					        		pub.autores.add(au);
					        	
					        		
					        	}

					        	//Adicionando a publicacao na lista de publicacao
					        	fila_publicacao.add(pub);
					        }		
				        }
						
						
						
					}

					@Override
					public boolean done() {
						// TODO Auto-generated method stub
						return true;
					}
				});
		
				

				
		
		
				//Pegando a lista de autores e então inserindo no BD
				addBehaviour(new CyclicBehaviour(){
					@Override
					public void action() {
						
						//Iterator<Publicacao> itr = fila_publicacao.iterator();
				            
						//Iterando lista de publicacoes
				            if (!fila_publicacao.isEmpty()) {
				                
				            	
				            	
				            	
				            	
				            	
				            	Publicacao publ = fila_publicacao.poll();
				            	ArrayList<Autor> autores = publ.autores;
				            	
				            	
				            	//ArrayList que guardará o ID dos autores para executar as CoAuthorias
					        	ArrayList<Integer> AuthorIDList = new ArrayList<Integer>();
				            	
				            	Iterator<Autor> aut = autores.iterator();	
				            	
				            	
				            	// if(!fila_autores.isEmpty()) {
									
				            	
				            	while(aut.hasNext()) {
				            	
										Autor a1 = aut.next();
					                	
					                	
										//System.out.println(a1.getNome());
					                	//Enviando para os agentes executores
					                	
									
										
					                	//Enviando o autor e suas informações para um agente
						        		
						        		//NOME
						        		ACLMessage msgTx1 = new ACLMessage(ACLMessage.PROPOSE);
						        		ACLMessage msgTx2 = new ACLMessage(ACLMessage.PROPOSE);
						        		ACLMessage msgTx3 = new ACLMessage(ACLMessage.PROPOSE);
						        		ACLMessage msgTx4 = new ACLMessage(ACLMessage.PROPOSE);
										
						        		msgTx1.addReceiver(new AID("AgenteExecutor1", false));
										msgTx2.addReceiver(new AID("AgenteExecutor2", false));
										msgTx3.addReceiver(new AID("AgenteExecutor3", false));	
										msgTx4.addReceiver(new AID("AgenteExecutor4", false));	
										//REDE DE COAUTORIA
										//ACLMessage msgRx2 = new ACLMessage(ACLMessage.INFORM);
										//msgRx2.addReceiver(new AID("AgenteExecutor1", false));
										//msgRx2.addReceiver(new AID("AgenteExecutor2", false));
										

										
										
										
										//msgRx1.setContent(a1.getNome() + cont2);
										try {
											msgTx1.setContentObject(a1);
											msgTx2.setContentObject(a1);
											msgTx3.setContentObject(a1);
											msgTx4.setContentObject(a1);
										} catch (IOException e) {
											// TODO Auto-generated catch block
											//e.printStackTrace();
										}

										cont2++;
										send(msgTx1);
										send(msgTx2);
										send(msgTx3);
										send(msgTx4);
										//send(msgRx2);
					                	
					                	
										//Espera a resposta dos agentes
										ACLMessage msgRx1 = receive();
										ACLMessage msgRx2 = receive();
										ACLMessage msgRx3 = receive();
										ACLMessage msgRx4 = receive();
										//while(msgRx1 == null || msgRx2 == null || !msgRx1.getSender().getName().equalsIgnoreCase("AgenteExecutor1") || !msgRx2.getSender().getName().equalsIgnoreCase("AgenteExecutor2")) {
										
										//AGENTE 1
										while(msgRx1 == null) {
											block();
											msgRx1 = receive();
											
											
										}
										
										//System.out.println(msgRx1.getSender().getName());
										restart();
										
										//AGENTE 2
										while(msgRx2 == null) {
											block();
											
											msgRx2 = receive();
											
										}
										
										
										restart();
										
										//AGENTE 3
										while(msgRx3 == null) {
											block();
											
											msgRx3 = receive();
											
										}
										
										
										restart();
										
										
										//AGENTE 4
										while(msgRx4 == null) {
											block();
											
											msgRx4 = receive();
											
										}
										
										
										restart();
										
										
										
										
										if (msgRx1 != null && msgRx2 != null && msgRx3 != null && msgRx4 != null) {
										//if (msgRx1 != null && msgRx2 != null && msgRx3 != null) {
													
											
											
											System.out.println(a1.getNome() + " " + cont);
											System.out.println("Autor do AgenteExecutor 1: " + msgRx1.getContent());
											System.out.println(msgRx1);
											
											System.out.println("Autor do AgenteExecutor 2: " + msgRx2.getContent());
											System.out.println(msgRx2);
											
											System.out.println("Autor do AgenteExecutor 3: " + msgRx3.getContent());
											System.out.println(msgRx3);
											
											System.out.println("Autor do AgenteExecutor 4: " + msgRx4.getContent());
											System.out.println(msgRx4);
											
											
									
											//Adicionando o author no bdgrafo, fazendo um método de desambiguation
							        		CombinedDesambiguationAgents cd = new CombinedDesambiguationAgents();
							        		
							        		
							        		
							        		//COMPARA OS METODOS DE DESAMBIGUAÇÃO UTILIZADOS PELOS AGENTES E RETORNA O MENOR
							        		
							        		int agente_1, agente_2, agente_3, agente_4;
							        		
							        		agente_1 = Integer.parseInt(msgRx1.getContent());
							        		agente_2 = Integer.parseInt(msgRx2.getContent());
							        		agente_3 = Integer.parseInt(msgRx3.getContent());
							        		agente_4 = Integer.parseInt(msgRx4.getContent());
							        		
							        		int idd;
							        		
							        		
							        		String escrita_arq = "AG1: " + agente_1 + " AG2: " + agente_2  + " AG3: " + agente_3  + " AG4: " + agente_4 + " \n";
							        		
							        		//String escrita_arq = "AG1: " + agente_1 + " AG2: " + agente_2  + " AG3: " + agente_3  + "\n";
							        		
							        		
							        		
							        		//Calculando o melhor indice dos agentes
							        		
							        		int coef1 = (agente_1-agente_2) + (agente_1-agente_3) + (agente_1-agente_4);
							        		
							        		int coef2 = (agente_2-agente_1) + (agente_2-agente_3) + (agente_2-agente_4);
							        		
							        		int coef3 = (agente_3-agente_1) + (agente_3-agente_2) + (agente_3-agente_4);
							        		
							        		int coef4 = (agente_4-agente_1) + (agente_4-agente_2) + (agente_4-agente_3);
							        		
							        		
							        		
							        		escrita_arq = escrita_arq + "COEF1: " + coef1 + " COEF2: " + coef2 + " COEF3: " + coef3  + " COEF4: " + coef4 + " \n";
							        		
							        		/*
							        		
							        		int coef1 = (agente_1-agente_2) + (agente_1-agente_3);
							        		
							        		int coef2 = (agente_2-agente_1) + (agente_2-agente_3);
							        		
							        		int coef3 = (agente_3-agente_1) + (agente_3-agente_2);
							        		
							        		
							        		
							        		escrita_arq = escrita_arq + "COEF1: " + coef1 + " COEF2: " + coef2 + " COEF3: " + coef3  +  " \n";
							        		
							        		*/
							        		
							        		if(coef2 < 0) {
							        			
							        			idd = cd.Combination2(a1.getNome(), cn, a1.getRede_coautoria());
							        			
							        			escrita_arq = escrita_arq + "+== Foi utilizado o método do Agente 2 ==+ \n";

							        		}else if(coef3 < 0) {
							        			
							        			idd = cd.Combination3(a1.getNome(), cn, a1.getRede_coautoria());
							        			escrita_arq = escrita_arq + "+== Foi utilizado o método do Agente 3 ==+ \n";
							        		
							        		}	
							        		else if(coef4 < 0) {
							        			
							        			idd = cd.Combination4(a1.getNome(), cn, a1.getRede_coautoria());
							        			escrita_arq = escrita_arq + "+== Foi utilizado o método do Agente 4 ==+ \n";
							        			
							        		}else if(coef1 < 0) {
							        			
							        			idd = cd.Combination1(a1.getNome(), cn, a1.getRede_coautoria());
							        			escrita_arq = escrita_arq + "+== Foi utilizado o método do Agente 1 ==+ \n";
							        				
							        		}else {
							        			
							        			
							        			//instância um objeto da classe Random usando o construtor padrão
							        	        Random gerador = new Random();

							        	       
							        	        int numero = gerador.nextInt(3);
							        	        
							        	        if(numero==0) {
							        	        	idd = cd.Combination1(a1.getNome(), cn, a1.getRede_coautoria());
							        	        	escrita_arq = escrita_arq + "+== Foi utilizado o método do Agente 1 (Random) ==+ \n";
							        	        
							        	        }else if(numero==1){
							        	        	idd = cd.Combination4(a1.getNome(), cn, a1.getRede_coautoria());
							        	        	escrita_arq = escrita_arq + "+== Foi utilizado o método do Agente 2 (Random)==+ \n";
							        	        
							        	        } 
							        	        else if(numero==2){
							        	        	idd = cd.Combination3(a1.getNome(), cn, a1.getRede_coautoria());
							        	        	escrita_arq = escrita_arq + "+== Foi utilizado o método do Agente 3 (Random)==+ \n";
							        	        
							        	        }
							        	        
							        	        
							        	        else {
							        	        	idd = cd.Combination4(a1.getNome(), cn, a1.getRede_coautoria());
							        	        	escrita_arq = escrita_arq + "+== Foi utilizado o método do Agente 4 (Random)==+ \n";
							        	        }
							        	        
							        			
							        	       
							        			
							        			
							        			
							        			
							        		}
							        		
							        		
							        		WriterFile wt = new WriterFile();
							        		wt.WriteFile(escrita_arq);
							        		
							        		
							        		//int idd = cd.SimulatedCombination1(new_authors[k], cn, new_authors);
							        		
							        		//System.out.println("Taxa de desambiguação: " + idd);
							        		AuthorIDList.add(idd);
							        		
							        		
							        		//System.out.println(new_authors[k]);
											
										} 
					                	
					                	
					                
										cont++;
									}
				                	
				
				            	//Adicionando coautorias, percorrendo o ArrayList
					        	for(int p=0;p<AuthorIDList.size();p++){
					        		
					        		for(int q=0;q<AuthorIDList.size();q++){
						        		if(p!=q){
						        			
						        			cn.addCoAuthor(AuthorIDList.get(p), AuthorIDList.get(q));
						        		}
						        	}

					        	}
				            	
				            	
				            }else {
				            	
				            	System.out.println("Acabou a fila de publicação...");
				            	block(10000);
				            	
				            }
				            	
				            	
				            	
				            	
				            	
				            	
				            	
				            }
						
				                
					
				});
				
		
		
		
		
		
				
		
		//Fechando a conexão do NEO4J.
		//cn.close();
	}
}
