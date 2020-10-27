package publication;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriterFile {

	//Escrevendo no arquivo
	public void WriteFile(String texto){
		
		try {
		     
			String fileName = "C:\\Users\\Usuário\\eclipse-workspace\\DesambAgent\\src\\bibfolder\\desambiguados.txt";
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
			writer.append(texto + "\n");
		    
		    writer.close();
			
		 
		      
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		
	}
	

    
}
