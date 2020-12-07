package Agents;

import bdNeo4j.ConnectNeo4j;
import desambiguation.CombinedDesambiguationAgents;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import publication.Autor;

public class AgenteExecutor4 extends Agent{
	protected void setup() {
		System.out.println("Alo Mundo! Sou o Agente Executor ");
		System.out.println("Meu nome: "+ getLocalName()); 
	
		 ConnectNeo4j cn = new ConnectNeo4j("bolt://localhost:7687", "neo4j", "scisynergy");  
		//Recebendo nome do autor que será desambiguado!
		
		addBehaviour(new CyclicBehaviour() {
			public void action() {
				
				//Nome do Autor X
				ACLMessage msgRx1 = receive();
				
				
				while(msgRx1 == null) {
					block();
					msgRx1 = receive();
				}
				
				
				restart();
				
				
				
				if (msgRx1 != null) {
				
					
				Autor a1 = null;
				try {
					a1 = (Autor) msgRx1.getContentObject();
				} catch (UnreadableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
				
				  //System.out.println(msgRx);
				   
					ACLMessage msgTx1 = msgRx1.createReply();
				  // msgTx1.setPerformative(ACLMessage.);
				   

				   String autor = a1.getNome();
				   String[] rede_autor = a1.getRede_coautoria();
				
				   
				   
				   //Calculando o valor de desambiguação.
				   

				   
				   
				 //Faz uma simulação de desambiguação e retorna o valor
	        		CombinedDesambiguationAgents cd = new CombinedDesambiguationAgents();
	        		
	        		
	        		int idd = cd.SimulatedCombination4(autor,cn, rede_autor);
				   

				   msgTx1.setContent("" + idd);
				   send(msgTx1);
				   
				   
				  
				} 
			}
		});
		
	}
}
