

public class RunAgents {

	public static void main(String[] args) {	
			
		

		
		//INICIANDO AGENTES 
		
		String[] parametros = {
				"-gui", "AgenteExecutor1:Agents.AgenteExecutor1;"
				+ "AgenteExecutor2:Agents.AgenteExecutor2;"
				+ "AgenteExecutor3:Agents.AgenteExecutor3;"
				+ "AgenteExecutor4:Agents.AgenteExecutor4;"
				+ "AgenteMestre2222:Agents.AgenteMestre;",};		
		 jade.Boot.main(parametros);
			 
			 
			 
		/* String[] novoContainer = {
		"-container", "-container-name", "Meu-Container",				
		 "AgenteExecutor:Agents.AgenteExecutor1",};
		jade.Boot.main(novoContainer);
		*/	 
		
		}

	}


