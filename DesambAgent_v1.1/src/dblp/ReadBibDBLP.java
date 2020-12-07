package dblp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.FileUtils;


public class ReadBibDBLP {



    private static int HTTP_COD_SUCESSO = 200;
 

    
    //Retorna os Titulos dos trabalhos do autor a partir de um orcid
    public String GetBibTex(String pid) throws JAXBException{
    	
    	String bibtex = "";
    	
        try {
        	
        	
        	//Acessando o site do DBLP e recuperando bib
            URL url = new URL("https://dblp.org/pid/" + pid + ".bib");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            
            System.out.println("Conectou ao BIB DBLP!");
          
            System.out.println(url.toString());
            File file = new File("C:\\Users\\Usuário\\eclipse-workspace\\DesambAgent\\src\\temp.bib");

            FileUtils.copyURLToFile(url, file);
            

            
            if (con.getResponseCode() != HTTP_COD_SUCESSO) {
                throw new RuntimeException("HTTP error code : "+ con.getResponseCode());
            }
 
    
          //LENDO O ARQUIVO E TRANSFORMANDO EM UMA LINHA
	        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
	        String linha = null;
	        String newLine = null;
	        while ((linha = reader.readLine()) != null) {
	        	
	        	System.out.println(linha.toString());
	            
	            
	        }
	        reader.close();
           
            
            
            con.disconnect();
           
            
        
            
 
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    
    	return bibtex;
    }
    
  
    		


}