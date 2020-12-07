package publication;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;



public class NewPublication{


	 private static final String VIRGULA = "=";

	 //Lendo o BibTex com a publicacao
	    public ArrayList<String[]> LeBibTex(String pathbibtex) throws IOException{

	    	//LENDO O ARQUIVO E TRANSFORMANDO EM UMA LINHA
	        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(pathbibtex)));
	        String linha = null;
	        String newLine = null;
	        while ((linha = reader.readLine()) != null) {
	        	
	        	//Limpando os espaços em branco
	        	linha = " " + linha.trim();
	        	newLine = newLine+ linha;
	            
	            
	        }
	        reader.close();
	        
	
	        //Quebrando a linha criada
	        String[] publicacao = newLine.split(",");
            
	        //Vai buscar o campo autores no StringArray e adicionar em um arrayList para tratar
	        
	        //Primeiro é criada uma ArrayList
	        ArrayList<String> listaAuthors=new ArrayList<String>();//Creating arraylist    
	        
	        //Busca os autores na StringArray do BibTex
	        for(int i=0; i<publicacao.length;i++){
	        
	        	
	        	if(publicacao[i].contains("author    = {")){
	        		
	        		//Tratando os acentos do bibtex
	                publicacao[i] = new ParserBibTex().RemoveAccent(publicacao[i]);
	        		//Adicionando na arraylist
	                listaAuthors.add(publicacao[i]);
	        		
	        	}
	        	
	        }
	        
	        

            
        	
           //Tratando o conjunto de autores, removendo os "and"
	        ArrayList<String[]> setauthors = new ParserBibTex().GetAuthors(listaAuthors);
        	
            
            //Retornando os autores do BibTex
            return setauthors;
            
          
	        
	    }
	    
	  //Lendo o BibTex com as publicacoes recebidos em uma lista
	    public ArrayList<String[]> LeContributorsOrcid(ArrayList<String> bibtex) throws IOException{

	    	//Primeiro é criada uma ArrayList
    		ArrayList<String> listaAuthors=new ArrayList<String>();//Creating arraylist 
	    	
    		
    		
    		for(int h=0;h<bibtex.size();h++){
    			
    			//System.out.println(bibtex.get(h).toString());
    			
	    		//Quebrando a linha recebida
	    		String publicacao = bibtex.get(h);
            
	    		
	    		
	    				
	    				//Tratando os acentos do bibtex
	    				publicacao = new ParserBibTex().RemoveAccent(publicacao);
	    				//Adicionando na arraylist
	    				listaAuthors.add(publicacao);

	        
	    		
	    	}
           //Tratando o conjunto de autores, removendo os "and"
	        ArrayList<String[]> setauthors = new ParserBibTex().GetAuthorsOrcid(listaAuthors);
        	
            
            //Retornando os autores do BibTex
            return setauthors;

	        
	    }
	    
}

