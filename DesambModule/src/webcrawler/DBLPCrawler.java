package webcrawler;



import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import orcid.ClientPubApiOrcid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.JAXBException;

public class DBLPCrawler {


    private HashSet<String> links;

    public DBLPCrawler() {
        links = new HashSet<String>();
    }

   

    
    public  ArrayList<String> getPageAutoresUnB(String URL) throws IOException {
        
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
       
       for(int i=0; i<autores_split.length-1;i++){
    	  
    	   lista_autores.add(autores_split[i].trim());
       }
       //System.out.println("Total de autores: " + i);
        
        return lista_autores;
    }
    
    
    public  ArrayList<String> getPageAutoresUFMG(String URL) throws IOException {
        
    	//Criando a ArrayList de Strings com o nome dos professores da universidade.
    	ArrayList<String> listaAuthors=new ArrayList<String>();
    	
    	
    	// Faz o parse da String e tenta transformá-la em um documento.
    	Document document = Jsoup.connect(URL).get();
        

        //String titulo = document.title();
        Elements tabela_professores = document.select("p"); // a with href
       
        //System.out.println("Titulo: " + titulo.toString());
        
        listaAuthors.add("UFMG");
        
        for(Element element : tabela_professores){
        	
        	if(!element.select(".professor-nome").text().isEmpty()){
        		listaAuthors.add(element.select(".professor-nome").text());
        		System.out.println(element.select(".professor-nome").text());
        	}
        	
        	
        }
       
        return listaAuthors;
        
    }
    
    public  ArrayList<String> getPageAutoresUSP(String URL) throws IOException {
        
    	//Criando a ArrayList de Strings com o nome dos professores da universidade.
    	ArrayList<String> listaAuthors=new ArrayList<String>();
    	
    	
    	// Faz o parse da String e tenta transformá-la em um documento.
    	Document document = Jsoup.connect(URL).get();
        

        //String titulo = document.title();
        Elements tabela_professores = document.select("div").select("a").select("a").select(".destaque_nome"); // a with href
       
        //System.out.println(document.select("a").select("destaque_nome").text());
        
        listaAuthors.add("USP");
        
        for(Element element : tabela_professores){
        	
        	if(!element.text().isEmpty()){
        		listaAuthors.add(element.text());
        		System.out.println(element.text());
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
    
 public  ArrayList<String> getPageAutoresUFRN(String URL) throws IOException {
        
    	//Criando a ArrayList de Strings com o nome dos professores da universidade.
    	ArrayList<String> listaAuthors=new ArrayList<String>();
    	
    	
    	// Faz o parse da String e tenta transformá-la em um documento.
    	Document document = Jsoup.connect(URL).get();
        
    
        //String titulo = document.title();
        Elements tabela_professores = document.select("div").select("table").select("tr").select("td").select("a"); // a with href
       
        //System.out.println(document.select("a").select("destaque_nome").text());
        
        listaAuthors.add("UFRN");
        
        int cont = 0;
        for(Element element : tabela_professores){
        	
        	
        	if(!element.text().isEmpty()){
        	
        		String elementname = element.text().toLowerCase();
        		elementname = new StringFormatter().capitalizeWord(elementname);
        	//if((!element.text().equalsIgnoreCase("Área de Atuação".trim())) && (!element.text().equalsIgnoreCase("Formação".trim())) && (!element.text().equalsIgnoreCase("Linha de Pesquisa".trim())) && (!element.text().equalsIgnoreCase("Local".trim())) && (!element.text().equalsIgnoreCase("Área de Atuação:".trim())) && (!element.text().equalsIgnoreCase("Lattes".trim())) && (!element.text().equalsIgnoreCase("Linha de Pesquisa".trim())) && (!element.text().equalsIgnoreCase("Linha de Pesquisa:".trim()))){
        		listaAuthors.add(elementname);
        		System.out.println(elementname);
        	}
        	
        	cont++;
        	
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