package desambiguation;

public class ShortenerName {

		//Metodo para abreviar determinado nome
		public String Shortener(String entrada){
			
			//Criando a saida
			String saida = "";
			
			 //Quebrando a linha criada
	        String[] name = entrada.split(" ");
	        
	        for(int i=0; i<name.length;i++){
	        	
	        	//Removendo De ou Da
	        	if(!(name[i].equalsIgnoreCase("de") || name[i].equalsIgnoreCase("da"))){
	        		//Se não for o primeiro ou o último nome abrevia,
	        		if(!(i==0 || i==name.length-1)){
	        			name[i] = name[i].charAt(0) + ".";
	        			saida += " " + name[i];
	        		}else{
	        			if(i==0){
	        				saida += name[i];
	        			}else{
	        				saida += " " + name[i];
	        			}
	        		}

	        		
	        	}
	        }
	        saida.trim();
			return saida;
		}
		
		
		public String GetLastName(String fullname){
			
			String[] name = fullname.split(" ");
			
			String lastname = name[name.length-1];
			
			return lastname;
			
		}
		
		
		//Metodo que recebe um full name e retorna o primeiro nome
		public String GetFirstName(String fullname){
			
			String[] name = fullname.split(" ");
			
			String firstname = name[0];
			
			return firstname;
		}
}
