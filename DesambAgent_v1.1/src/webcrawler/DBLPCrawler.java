package webcrawler;



import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import dblp.ConnectApiDBLP;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;

import javax.xml.bind.JAXBException;

public class DBLPCrawler {


    private HashSet<String> links;

    public DBLPCrawler() {
        links = new HashSet<String>();
    }

   

    
    public  ArrayList<String> getPageAutoresUnB(String URL) throws IOException, JSONException {
        
    	// Faz o parse da String e tenta transformá-la em um documento.
    	Document document = Jsoup.connect(URL).get();
        
    	
        String titulo = document.title();
        Elements tabela = document.select("table"); // a with href
       
        //System.out.println("Titulo: " + titulo.toString());
        
        String autores = null;
        for(Element element : tabela){
        	
        	autores = element.select(".docentes").select("td").select("td").select("p").text();
        	
        	autores = autores.replace("CV Lattes, Orientadora M", " ");
        	autores = autores.replace("CV Lattes, Orientador M", " ");
        	autores = autores.replace("e D", " ");
        	autores = autores.replace(")", " ");
        	autores = autores.replace("(", ",");
        	//System.out.println(autores);
        }
       
       String[] autores_split = autores.split(",");
       
       System.out.println(autores);
       
       ArrayList<String> lista_autores = new ArrayList<String>();
       
       lista_autores.add("UnB");
       
       String institution = "UnB";
       
       for(int i=0; i<autores_split.length-1;i++){
    	  
    	   lista_autores.add(autores_split[i].trim());
    	   
    	   
    	   //Gerando o BiB direto do DBLP API de cada autor Seed
    	   ConnectApiDBLP ct = new ConnectApiDBLP();
    	   
    	   
    	   //Tratando alguns autores que estão com nome diferente no DBLP. Pois ainda não foi feita desambiguação
    	   if(autores_split[i].trim().equalsIgnoreCase("Bruno Luiggi Macchiavello Espinoz")) {
    		   
    		   ct.generateAuthorBibFile(ct.getPidAuthor("Bruno Macchiavello") + ".bib?param=1", "Bruno Macchiavello", institution);
    		  
    		   
    	   }else if(autores_split[i].trim().equalsIgnoreCase("Camilo Chang Dorea")){
    		   
    		   
    		   ct.generateAuthorBibFile(ct.getPidAuthor("Camilo C. Dorea") + ".bib?param=1", "Camilo C. Dorea", institution);
    		   
    		   
    	   }else if(autores_split[i].trim().equalsIgnoreCase("Jorge Carlos Lucero")){
    		   
    		   
    		   ct.generateAuthorBibFile(ct.getPidAuthor("Jorge C. Lucero") + ".bib?param=1", "Jorge C. Lucero", institution);
    		   
    		   
    	   }else if(autores_split[i].trim().equalsIgnoreCase("Aletéia Patrícia Favacho de Araújo")){
    		   
    		   
    		   ct.generateAuthorBibFile(ct.getPidAuthor("Aletéia Patrícia Favacho de Araújo") + ".bib?param=1", "Aletéia Patrícia Favacho de Araújo_1", institution);
    		   
    		   ct.generateAuthorBibFile(ct.getPidAuthor("Aleteia Patricia F. De Araujo") + ".bib?param=1", "Aletéia Patrícia Favacho de Araújo_2", institution);
    		   
    		   //ct.generateAuthorBibFile(ct.getPidAuthor("Aleteia Favacho") + ".bib?param=1", "Aletéia Patrícia Favacho de Araújo_3", institution);
    		   
    		   //ct.generateAuthorBibFile(ct.getPidAuthor("Aleteia Araujo") + ".bib?param=1", "Aletéia Patrícia Favacho de Araújo_4", institution);
    		

    		   
    	   }else if(autores_split[i].trim().equalsIgnoreCase("Maria Emília Machado Telles Walter")){
    		   
    		   
    		   ct.generateAuthorBibFile(ct.getPidAuthor("Maria Emilia Telles Walter") + ".bib?param=1", "Maria Emília Machado Telles Walter", institution);
    		   
    		   
    	   }else if(autores_split[i].trim().equalsIgnoreCase("Ricardo Lopes de Queiroz")){
    		   
    		   
    		   ct.generateAuthorBibFile(ct.getPidAuthor("Ricardo L. de Queiroz") + ".bib?param=1", "Ricardo Lopes de Queiroz", institution);
    		   
    		   
    	   }else if(autores_split[i].trim().equalsIgnoreCase("Rodrigo Bonifácio de Almeida")){
    		   
    		   
    		   ct.generateAuthorBibFile(ct.getPidAuthor("Rodrigo Bonifácio") + ".bib?param=1", "Rodrigo Bonifácio de Almeida", institution);
    		   
    		   
    	   }else if(autores_split[i].trim().equalsIgnoreCase("Vander Ramos Alves")){
    		   
    		   
    		   ct.generateAuthorBibFile(ct.getPidAuthor("Vander Alves") + ".bib?param=1", "Vander Ramos Alves", institution);
    		   
    		   
    	   }else if(autores_split[i].trim().equalsIgnoreCase("Célia Ghedini Ralha")){
    		   
    		   
    		   ct.generateAuthorBibFile(ct.getPidAuthor("Célia Ghedini Ralha") + ".bib?param=1", "Célia Ghedini Ralha_1", institution);
    		   ct.generateAuthorBibFile(ct.getPidAuthor("Celia G. Ralha") + ".bib?param=1", "Célia Ghedini Ralha_2", institution);
    		   
    		   
    	   }

    	   else {
    		   
    		   ct.generateAuthorBibFile(ct.getPidAuthor(autores_split[i].trim()) + ".bib?param=1", autores_split[i].trim(), institution);
    		   
    	   }
    	   
    	   
    	  
    	   
    	   
    	   
    	   
       }
       //System.out.println("Total de autores: " + i);
        
        return lista_autores;
    }
    
    
    public  ArrayList<String> getPageAutoresUFMG(String URL) throws IOException, JSONException {
        
    	//Criando a ArrayList de Strings com o nome dos professores da universidade.
    	ArrayList<String> listaAuthors=new ArrayList<String>();
    	
    	
    	// Faz o parse da String e tenta transformá-la em um documento.
    	Document document = Jsoup.connect(URL).get();
        

        //String titulo = document.title();
        Elements tabela_professores = document.select("p"); // a with href
       
        //System.out.println("Titulo: " + titulo.toString());
        
        listaAuthors.add("UFMG");
        String institution = "UFMG";
        
        for(Element element : tabela_professores){
        	
        	if(!element.select(".professor-nome").text().isEmpty()){
        		listaAuthors.add(element.select(".professor-nome").text());
        		System.out.println(element.select(".professor-nome").text());
        		
        		
        		
        		//Gerando os Bibs direto do DBLP

         	    //Gerando o BiB direto do DBLP API de cada autor Seed
         	    ConnectApiDBLP ct = new ConnectApiDBLP();
        		
         	   if(element.select(".professor-nome").text().trim().equalsIgnoreCase("Alberto Henrique Frade Laender")) {
        		   
        		   ct.generateAuthorBibFile(ct.getPidAuthor("Alberto H. F. Laender") + ".bib?param=1", "Alberto Henrique Frade Laender", institution);
        		  
        		   
        		   
        		   
        	   }else if(element.select(".professor-nome").text().trim().equalsIgnoreCase("André Cavalcante Hora")) {
        		   
        		   ct.generateAuthorBibFile(ct.getPidAuthor("André C. Hora") + ".bib?param=1", "André Cavalcante Hora", institution);
         		  
        	   }else if(element.select(".professor-nome").text().trim().equalsIgnoreCase("Berthier Ribeiro de Araújo Neto")) {
      		   
        		   ct.generateAuthorBibFile(ct.getPidAuthor("Berthier A. Ribeiro-Neto") + ".bib?param=1", "Berthier Ribeiro de Araújo Neto", institution);
       		  
        	   }else if(element.select(".professor-nome").text().trim().equalsIgnoreCase("Fabricio Murai Ferreira")) {
      		   
        		   ct.generateAuthorBibFile(ct.getPidAuthor("Fabricio Murai") + ".bib?param=1", "Fabricio Murai Ferreira", institution);
       		  
        	   }else if(element.select(".professor-nome").text().trim().equalsIgnoreCase("Gabriel de Morais Coutinho")) {
      		   
        		   ct.generateAuthorBibFile(ct.getPidAuthor("Gabriel Coutinho") + ".bib?param=1", "Gabriel de Morais Coutinho", institution);
       		  
        	   }else if(element.select(".professor-nome").text().trim().equalsIgnoreCase("Haniel Moreira Barbosa")) {
      		   
        		   ct.generateAuthorBibFile(ct.getPidAuthor("Haniel Barbosa") + ".bib?param=1", "Haniel Moreira Barbosa", institution);
       		  
        	   }else if(element.select(".professor-nome").text().trim().equalsIgnoreCase("Ítalo Fernando Scotá Cunha")) {
      		   
        		   ct.generateAuthorBibFile(ct.getPidAuthor("Ítalo S. Cunha") + ".bib?param=1", "Ítalo Fernando Scotá Cunha", institution);
       		  
        	   }else if(element.select(".professor-nome").text().trim().equalsIgnoreCase("Jeroen Antonius Maria van de Graaf")) {
      		   
        		   ct.generateAuthorBibFile(ct.getPidAuthor("Jeroen van de Graaf") + ".bib?param=1", "Jeroen Antonius Maria van de Graaf", institution);
        		   
        	   }else if(element.select(".professor-nome").text().trim().equalsIgnoreCase("João Guilherme Maia de Menezes")) {
      		   
        		   System.out.println("Não encontrado no DBLP");
        		   //ct.generateAuthorBibFile(ct.getPidAuthor("Jeroen van de Graaf") + ".bib?param=1", "Jeroen Antonius Maria van de Graaf");
       		  
        	   }else if(element.select(".professor-nome").text().trim().equalsIgnoreCase("Jussara Marques de Almeida Gonçalves")) {
      		   
        		   ct.generateAuthorBibFile(ct.getPidAuthor("Jussara M. Almeida") + ".bib?param=1", "Jussara Marques de Almeida Gonçalves", institution);
        		   
        	   }else if(element.select(".professor-nome").text().trim().equalsIgnoreCase("Loic Pascal Gilles Cerf")) {
      		   
        		   ct.generateAuthorBibFile(ct.getPidAuthor("Loïc Cerf") + ".bib?param=1", "Loic Pascal Gilles Cerf", institution);
        		  
        	   }else if(element.select(".professor-nome").text().trim().equalsIgnoreCase("Mario Sérgio Ferreira Alvim Júnior")) {
      		   
        		   ct.generateAuthorBibFile(ct.getPidAuthor("Mário S. Alvim") + ".bib?param=1", "Mario Sérgio Ferreira Alvim Júnior", institution);
        		   
        	   }else if(element.select(".professor-nome").text().trim().equalsIgnoreCase("Phablo Fernando Soares Moura")) {
      		   
        		   ct.generateAuthorBibFile(ct.getPidAuthor("Phablo F. S. Moura") + ".bib?param=1", "Phablo Fernando Soares Moura", institution);
        		   
        	   }else if(element.select(".professor-nome").text().trim().equalsIgnoreCase("Olga Nikolaevna Goussevskaia")) {
      		   
        		   ct.generateAuthorBibFile(ct.getPidAuthor("Olga Goussevskaia") + ".bib?param=1", "Olga Nikolaevna Goussevskaia", institution);
        		   
        	   }else if(element.select(".professor-nome").text().trim().equalsIgnoreCase("Rodrygo Luis Teodoro Santos")) {
      		   
        		   ct.generateAuthorBibFile(ct.getPidAuthor("Rodrygo L. T. Santos") + ".bib?param=1", "Rodrygo Luis Teodoro Santos", institution);
        		  
        	   }else if(element.select(".professor-nome").text().trim().equalsIgnoreCase("Sebastián Alberto Urrutia")) {
      		   
        		   ct.generateAuthorBibFile(ct.getPidAuthor("Sebastián Urrutia") + ".bib?param=1", "Sebastián Alberto Urrutia", institution);
        		   
        	   }else if(element.select(".professor-nome").text().trim().equalsIgnoreCase("Thiago Ferreira de Noronha")) {
      		   
        		   ct.generateAuthorBibFile(ct.getPidAuthor("Thiago F. Noronha") + ".bib?param=1", "Thiago Ferreira de Noronha", institution);
        		    		
        	   }else if(element.select(".professor-nome").text().trim().equalsIgnoreCase("Wagner Meira Júnior")) {
      		   
        		   ct.generateAuthorBibFile(ct.getPidAuthor("Wagner Meira Jr.") + ".bib?param=1", "Wagner Meira Júnior", institution);
        		    		
        	   }
        	   
        	   else {

           			ct.generateAuthorBibFile(ct.getPidAuthor(element.select(".professor-nome").text().trim()) + ".bib?param=1", element.select(".professor-nome").text().trim(), institution);

        	   }
         	  
         	  
        	}
        	
        	
        }
       
        return listaAuthors;
        
    }
    
    public  ArrayList<String> getPageAutoresUSP(String URL) throws IOException, JSONException {
        
    	//Criando a ArrayList de Strings com o nome dos professores da universidade.
    	ArrayList<String> listaAuthors=new ArrayList<String>();
    	
    	
    	// Faz o parse da String e tenta transformá-la em um documento.
    	Document document = Jsoup.connect(URL).get();
        

        //String titulo = document.title();
        Elements tabela_professores = document.select("div").select("a").select("span"); // a with href
       
        //System.out.println(document.select("a").select("destaque_nome").text());
        
        
        
        
        listaAuthors.add("USP");
        String institution = "USP";
        
        for(Element element : tabela_professores){
        	
        	if(!element.text().isEmpty() && !element.text().equalsIgnoreCase("Facebook") && !element.text().equalsIgnoreCase("Twitter") && !element.text().equalsIgnoreCase("Youtube") && !element.text().equalsIgnoreCase("Contatos") && !element.text().equalsIgnoreCase("Intranet")){
        		
        		boolean ja_existe = false;
        		
        		for(int i=0;i<listaAuthors.size();i++) {
        			
        			if(listaAuthors.get(i).equalsIgnoreCase(element.text())) {
        				ja_existe = true;
        			}
        			
        		}
        		
        		if(ja_existe == false) {
            		listaAuthors.add(element.text());
            		
            		
            		/*Buscando BIBS no DBLP
            		else {
					
              			ct.generateAuthorBibFile(ct.getPidAuthor(element.select(".professor-nome").text().trim()) + ".bib?param=1", element.select(".professor-nome").text().trim());

             	   }*/
            		//Gerando o BiB direto do DBLP API de cada autor Seed
            		System.out.println(element.text());
            		
             	    ConnectApiDBLP ct = new ConnectApiDBLP();
            		
             	    
             	    if(element.text().trim().equalsIgnoreCase("Ernesto Julian Goldberg Birgin")) {
             		   
             	    	ct.generateAuthorBibFile(ct.getPidAuthor("Ernesto G. Birgin") + ".bib?param=1", "Ernesto Julian Goldberg Birgin", institution);
             		  

             	   }else if(element.text().trim().equalsIgnoreCase("Walter Figueiredo Mascarenhas")) {
             		   
            	    	ct.generateAuthorBibFile(ct.getPidAuthor("Walter F. Mascarenhas") + ".bib?param=1", "Walter Figueiredo Mascarenhas", institution);
             	   }else {
             		   
             		   ct.generateAuthorBibFile(ct.getPidAuthor(element.text().trim()) + ".bib?param=1", element.text().trim(), institution);

             	   }
             	    
             	    
             	    
             	    
            		
            		
            		
        			
        		}

        	}
        	
        	
        }
       
        return listaAuthors;
        
    }
    
    public  ArrayList<String> getPageAutoresUFAM(String URL) throws IOException {
        
    	//Criando a ArrayList de Strings com o nome dos professores da universidade.
    	ArrayList<String> listaAuthors=new ArrayList<String>();
    	
    	
    	// Faz o parse da String e tenta transformá-la em um documento.
    	Document document = Jsoup.connect(URL).get();
        
    
        //String titulo = document.title();
        Elements tabela_professores = document.select("table").select("tbody").select("tr").select("td").select("td").select("strong"); // a with href
       
        //System.out.println(document.select("a").select("destaque_nome").text());
        
        listaAuthors.add("UFAM");
        
        int cont = 0;
        for(Element element : tabela_professores){
        	
        	
        	
        	if((!element.text().equalsIgnoreCase("Área de Atuação".trim())) && (!element.text().equalsIgnoreCase("Formação".trim())) && (!element.text().equalsIgnoreCase("Linha de Pesquisa".trim())) && (!element.text().equalsIgnoreCase("Local".trim())) && (!element.text().equalsIgnoreCase("Área de Atuação:".trim())) && (!element.text().equalsIgnoreCase("Lattes".trim())) && (!element.text().equalsIgnoreCase("Linha de Pesquisa".trim())) && (!element.text().equalsIgnoreCase("Linha de Pesquisa:".trim()))){
        		listaAuthors.add(element.text());
        		System.out.println(element.text());
        	}
        	
        	cont++;
        	
        }
       
        return listaAuthors;
        
    }
    
 public  ArrayList<String> getPageAutoresUFRN(String URL) throws IOException, JSONException {
        
    	//Criando a ArrayList de Strings com o nome dos professores da universidade.
    	ArrayList<String> listaAuthors=new ArrayList<String>();
    	
    	
    	// Faz o parse da String e tenta transformá-la em um documento.
    	Document document = Jsoup.connect(URL).get();
        
    
        //String titulo = document.title();
        Elements tabela_professores = document.select("div").select("table").select("tr").select("td").select("a"); // a with href
       
        //System.out.println(document.select("a").select("destaque_nome").text());
        
        listaAuthors.add("UFRN");
        String institution = "UFRN";
        
        
        for(Element element : tabela_professores){
        	
        	
        	if(!element.text().isEmpty()){
        	
        		String elementname = element.text().toLowerCase();
        		elementname = new StringFormatter().capitalizeWord(elementname);
        	//if((!element.text().equalsIgnoreCase("Área de Atuação".trim())) && (!element.text().equalsIgnoreCase("Formação".trim())) && (!element.text().equalsIgnoreCase("Linha de Pesquisa".trim())) && (!element.text().equalsIgnoreCase("Local".trim())) && (!element.text().equalsIgnoreCase("Área de Atuação:".trim())) && (!element.text().equalsIgnoreCase("Lattes".trim())) && (!element.text().equalsIgnoreCase("Linha de Pesquisa".trim())) && (!element.text().equalsIgnoreCase("Linha de Pesquisa:".trim()))){
        		listaAuthors.add(elementname);
        		System.out.println(elementname);
        		
        		
        		//Gerando o BiB direto do DBLP API de cada autor Seed
         	   ConnectApiDBLP ct = new ConnectApiDBLP();
         	   
         	   
         	   //Tratando alguns autores que estão com nome diferente no DBLP. Pois ainda não foi feita desambiguação
         	   if(elementname.trim().equalsIgnoreCase("Carlos Alberto Olarte Vega")) {
         		   
         		  ct.generateAuthorBibFile(ct.getPidAuthor("Carlos Olarte") + ".bib?param=1", "Carlos Alberto Olarte Vega", institution);
         		  
         		   
         	   }else if(elementname.trim().equalsIgnoreCase("Elaine Gouvea Pimentel")){
         		  
         		   ct.generateAuthorBibFile(ct.getPidAuthor("Elaine Pimentel") + ".bib?param=1", "Elaine Gouvea Pimentel", institution);
         	   
         	   }else if(elementname.trim().equalsIgnoreCase("Joao Marcos De Almeida")){
          		  
         		   System.out.println("Autor não encontrado no DBLP");
          	   
         	   }else if(elementname.trim().equalsIgnoreCase("Marcia Jacyntha Nunes Rodrigues Lucena")){
         		  
         		   ct.generateAuthorBibFile(ct.getPidAuthor("Márcia Lucena") + ".bib?param=1", "Marcia Jacyntha Nunes Rodrigues Lucena", institution);
         	   
         	   }
         	   
         	  
         	   else {
         		   
         		  ct.generateAuthorBibFile(ct.getPidAuthor(elementname) + ".bib?param=1", elementname, institution);
         		   
         	   }
        		
        		
        		
        		
        		
        	}
        	
        	
        	
        }
       
        return listaAuthors;
        
    }
    
    
    
    /*public static void main(String[] args) throws IOException, JAXBException {
        
    	ClientPubApiOrcid clt = new ClientPubApiOrcid();
    	
    	ArrayList<String> lista_unb = new DBLPCrawler().getPageAutoresUnB("http://ppgi.unb.br/index.php?option=com_content&view=article&id=78&Itemid=471&lang=pt");
    	
    	for(int i=0;i<lista_unb.size();i++){
    		clt.GetWorks(clt.SearchOrcidByName(lista_unb.get(i).toString()));
    	}
    	
    	
    	
        //new DBLPCrawler().getPageAutoresUFMG("http://ppgcc.dcc.ufmg.br/docentes/");
    	
        
    }*/

}