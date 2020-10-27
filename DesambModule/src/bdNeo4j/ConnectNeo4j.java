package bdNeo4j;
import org.neo4j.driver.v1.*;

import desambiguation.ShortenerName;

import static org.neo4j.driver.v1.Values.parameters;

import java.util.ArrayList;

import publication.WriterFile;

public class ConnectNeo4j
{
    // Driver objects are thread-safe and are typically made available application-wide.
    Driver driver;

    public ConnectNeo4j(String uri, String user, String password)
    {
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
       
    }

   
    
    
    //Retorna o Author a partir de um ID
    public String printAuthorbyId(int id_author)
    {
        try (Session session = driver.session())
        {
            // Auto-commit transactions are a quick and easy way to wrap a read.
            StatementResult result = session.run(
                    "MATCH (a:Author) WHERE ID(a) = " + id_author + " RETURN a.name AS name",
                    parameters("x", id_author));
            // Each Cypher execution returns a stream of records.
            
            //Se achou o author retorna verdadeiro, senao falso
            if(result.hasNext()){
            	
            	
            	
            	
                     Record record = result.next();
                     String author = record.get("name").asString();
                 
            	return author;
            }else{

            	//System.out.println("Author NÃO encontrado");
            	return "Não encontrado";
            }
           
            
        }
    }

    
    
    
    //Adiciona um author ao Banco de dados.
    public void addAuthor(String name)
    {
        // Sessions are lightweight and disposable connection wrappers.
        try (Session session = driver.session())
        {
            // Wrapping Cypher in an explicit transaction provides atomicity
            // and makes handling errors much easier.
            try (Transaction tx = session.beginTransaction())
            {
               
            	tx.run("MERGE (a:Author {name: {x}})", parameters("x", name));
                tx.success();  // Mark this write as successful.
            }
        }
    }
    
    
    //Adiciona um Alias ao Banco de dados.
    public void addAlias(String name, Integer id_author){
    	 // Sessions are lightweight and disposable connection wrappers.
        try (Session session = driver.session())
        {
            // Wrapping Cypher in an explicit transaction provides atomicity
            // and makes handling errors much easier.
            try (Transaction tx = session.beginTransaction())
            {
               
            	tx.run("MERGE (a:Alias {name: {x}})", parameters("x", name));
            	
            	tx.run("MATCH (a:Alias {name: '" + name + "'}) MATCH (b:Author) WHERE ID(b) = " + id_author + " MERGE (a)-[:ALIAS]->(b)");
            	
           
                tx.success();  // Mark this write as successful.
                
                WriterFile w = new WriterFile();
                w.WriteFile(printAuthorbyId(id_author));
                
                //Gravando no arquivo o nome do Author desambiguado
                
                
                
                
                
            }
        }
    	
    	
    }
    
    //Adiciona uma Instituição ou Afiliation
    public void addInstitution(String institution){
    	// Sessions are lightweight and disposable connection wrappers.
		try (Session session = driver.session())
		{
			// Wrapping Cypher in an explicit transaction provides atomicity
			// and makes handling errors much easier.
			try (Transaction tx = session.beginTransaction())
			{
           
				
				tx.run("MERGE (i:Institution {name: {x}})", parameters("x", institution));
				
				tx.success();  // Mark this write as successful.
			}
		}	
    }
    
    //Adiciona Alias de uma Instituição ou Afiliation
    public void addInstitutionAlias(String institution_alias, Integer id_institution){
    	// Sessions are lightweight and disposable connection wrappers.
		try (Session session = driver.session())
		{
			// Wrapping Cypher in an explicit transaction provides atomicity
			// and makes handling errors much easier.
			try (Transaction tx = session.beginTransaction())
			{
           
				
				
				
				tx.run("MERGE (a:Alias {name: {x}})", parameters("x", institution_alias));
            	
            	tx.run("MATCH (a:Alias {name: '" + institution_alias + "'}) MATCH (i:Institution) WHERE ID(i) = " + id_institution + " MERGE (a)-[:ALIAS]->(i)");
          
				
				tx.success();  // Mark this write as successful.
			}
		}	
    }
    
    
    //Adiciona um relacao de COAUTHORIA ao Banco de dados.
    public void addCoAuthor(Integer author1, Integer author2)

    {
        // Sessions are lightweight and disposable connection wrappers.
        try (Session session = driver.session())
        {

        	// Wrapping Cypher in an explicit transaction provides atomicity
            // and makes handling errors much easier.
            try (Transaction tx = session.beginTransaction())
            {
               
            	String query = "MATCH (a:Author) WHERE ID(a) = "+ author1 +" \n";
            	query += "MATCH (p:Author) WHERE ID(p) = "+ author2 +" \n";
            	query += "CREATE (a)-[r:COAUTHOR]->(p)\n";
            	tx.run(query);
                tx.success();  // Mark this write as successful.
            }
        }
    }
    
    //Recebe uma lista do crawler com a instituição na cabeça da fila e o restante com os autores
    public void addSeedsAuthorInstitution(ArrayList<String> lista_autores){
    	
    	String institution = lista_autores.get(0);
    	
    	//Adicionando os autores
    	for(int i=0;i<lista_autores.size();i++){
    	
    	// Sessions are lightweight and disposable connection wrappers.
    		try (Session session = driver.session())
    		{
    			// Wrapping Cypher in an explicit transaction provides atomicity
    			// and makes handling errors much easier.
    			try (Transaction tx = session.beginTransaction())
    			{
               
    				tx.run("MERGE (a:Author {name: {x}})", parameters("x", lista_autores.get(i).toString()));
    				tx.run("MERGE (i:Institution {name: {x}})", parameters("x", institution));
    				tx.run("MATCH (a:Author {name: '" + lista_autores.get(i).toString() + "'}) MATCH (i:Institution {name: '" + institution + "'}) MERGE (a)-[:ASSOCIATED_TO]->(i)");
            	
    				tx.success();  // Mark this write as successful.
    			}
    		}	
    	}
    }
    
  
    
  //Deleta um author do Banco de dados.
    private void deleteAuthor(String name)
    {
        // Sessions are lightweight and disposable connection wrappers.
        try (Session session = driver.session())
        {
            // Wrapping Cypher in an explicit transaction provides atomicity
            // and makes handling errors much easier.
            try (Transaction tx = session.beginTransaction())
            {
               
            	tx.run("MERGE (a:Author {name: {x}})", parameters("x", name));
                tx.success();  // Mark this write as successful.
            }
        }
    }
    
    //Retorna o ID de determinado Institution
    public Integer printInstitution(String initial){
    	{
            try (Session session = driver.session())
            {
                // Auto-commit transactions are a quick and easy way to wrap a read.
                StatementResult result = session.run(
                        "MATCH (i:Institution) WHERE i.name = {x} RETURN ID(i) AS id, i.name AS name",
                        parameters("x", initial));
                // Each Cypher execution returns a stream of records.
                
                //Se achou o author retorna verdadeiro, senao falso
                if(result.hasNext()){
                	
                	Double id = 0.0;
                	
                	//System.out.println("Author encontrado");
                	 while (result.hasNext())
                     {
                         Record record = result.next();
                         // Values can be extracted from a record by index or name.
                         //System.out.println(record.get("id") + "|" + record.get("name").asString());
                         id = record.get("id").asDouble();
                     }
                	return id.intValue();
                }else{

                	//System.out.println("Author NÃO encontrado");
                	return -1;
                }
               
                
            }
            
    	}
    	
    }
    
    //Retorna o ID de Author de um determinado Alias
    public Integer printAuthorAlias(String initial)
    {
        try (Session session = driver.session())
        {
            // Auto-commit transactions are a quick and easy way to wrap a read.
            StatementResult result = session.run(
                    "MATCH (a:Author)-[r:ALIAS]-(b:Alias) WHERE b.name = {x} RETURN ID(a) AS id, b.name AS name",
                    parameters("x", initial));
            // Each Cypher execution returns a stream of records.
            //Se achou o author retorna verdadeiro, senao falso
            if(result.hasNext()){
            	
            	Double id = 0.0;
            	
            	//System.out.println("Alias encontrado");
            	 while (result.hasNext())
                 {
                     Record record = result.next();
                     // Values can be extracted from a record by index or name.
                     //System.out.println(record.get("id") + "|" + record.get("name").asString());
                     id = record.get("id").asDouble();
                 }
            	return id.intValue();
            }else{

            	//System.out.println("Alias NÃO encontrado");
            	return -1;
            }
        }
    }
    
    //Retorna o ID de determinado Author
    public Integer printAuthor(String initial)
    {
        try (Session session = driver.session())
        {
            // Auto-commit transactions are a quick and easy way to wrap a read.
            StatementResult result = session.run(
                    "MATCH (a:Author) WHERE a.name = {x} RETURN ID(a) AS id, a.name AS name",
                    parameters("x", initial));
            // Each Cypher execution returns a stream of records.
            
            //Se achou o author retorna verdadeiro, senao falso
            if(result.hasNext()){
            	
            	Double id = 0.0;
            	
            	//System.out.println("Author encontrado");
            	 while (result.hasNext())
                 {
                     Record record = result.next();
                     // Values can be extracted from a record by index or name.
                     //System.out.println(record.get("id") + "|" + record.get("name").asString());
                     id = record.get("id").asDouble();
                 }
            	return id.intValue();
            }else{

            	//System.out.println("Author NÃO encontrado");
            	return -1;
            }
           
            
        }
    }

    //Recebe o nome abreviado, tira o primeiro nome e pesquisa p vê se é igual ao nome abreviado
    public Integer printAuthorwShortName(String initial){
    	
    	 //Quebrando o nome
        String[] name = initial.split(" ");
    	String shortname = name[0].trim();
    	
    	
    	
    	try (Session session = driver.session())
        {
            // Auto-commit transactions are a quick and easy way to wrap a read.
            StatementResult result = session.run(
                    "MATCH (a:Author) WHERE a.name STARTS WITH '" + shortname +"' RETURN ID(a) AS id, a.name AS name",
                    parameters("x", shortname.toString()));
            // Each Cypher execution returns a stream of records.
            	
            //Se achou o author retorna verdadeiro, senao falso
            if(result.hasNext()){
            	
            	Double id = 0.0;
            	
            	
            	 while (result.hasNext())
                 {
                     
            		
            		 Record record = result.next();
                     // Values can be extracted from a record by index or name.
                     //System.out.println(record.get("id") + "|" + record.get("name").asString());
                     
                     //Se o nome abreviado coincidir com o nome de Author abreviado retorna ID
            		 ShortenerName s = new ShortenerName();
            		 String shortenername = s.Shortener(record.get("name").asString());
            		 
            		 //System.out.println(record.get("id") + "|" + record.get("name").asString()+ "|" + shortenername);
                 	
                     if(initial.contentEquals(shortenername)){
                    	 
                    	 
                    	 id = record.get("id").asDouble();
                    	 
                    	 return id.intValue();
                     }
                     
                     
                 }
            	return -1;
            }else{

            	
            	return -1;
            }
           
            
        }
    	
    }

    //Retorna ID e Nome de Author que comecem com a string recebida
    public StatementResult returnAuthorsStartWith (String name){
    	try (Session session = driver.session())
        {

                // Auto-commit transactions are a quick and easy way to wrap a read.
                StatementResult result = session.run(
                        "MATCH (a:Author) WHERE a.name STARTS WITH '" + name +"' RETURN ID(a) AS id, a.name AS name",
                        parameters("x", name.toString()));
                // Each Cypher execution returns a stream of records.
                	
                return result;
        }
   }
    	
    //Retorna ID de Author e Nome de Alias que comecem com a string recebida
    public StatementResult returnAliasStartWith (String name){
    	try (Session session = driver.session())
        {

                // Auto-commit transactions are a quick and easy way to wrap a read.
                StatementResult result = session.run(
                        "MATCH (a:Author)-[ALIAS]-(b:Alias) WHERE b.name STARTS WITH '" + name +"' RETURN ID(a) AS id, b.name AS name",
                        parameters("x", name.toString()));
                // Each Cypher execution returns a stream of records.
                	
                return result;
        }
    }
    
    
    //Retorna os co-autores que publicaram com determinado autor X
    public StatementResult returnCoAuthors(String initial, String finall){
    	 try (Session session = driver.session())
         {
             // Auto-commit transactions are a quick and easy way to wrap a read.
             StatementResult result = session.run(
                     "MATCH (p:Author)-[r:COAUTHOR]->(c:Author) where p.name STARTS WITH '" + initial +"' and p.name ENDS WITH '" + finall +"' RETURN  DISTINCT c.name AS name, ID(p) as id ORDER BY name",
                     parameters("x", initial));
             
             
             return result;
             
            
         }
    }
    
    //Retorna os Alias de co-autores que publicaram com determinado autor X
    public StatementResult returnCoAuthorsAlias(String initial, String finall){
    	 try (Session session = driver.session())
         {
             // Auto-commit transactions are a quick and easy way to wrap a read.
             StatementResult result = session.run(
                     "MATCH (p:Author)-[r:COAUTHOR]->(c:Author)-[s:ALIAS]-(d:Alias) where p.name STARTS WITH '" + initial +"' and p.name ENDS WITH '" + finall +"' RETURN  DISTINCT d.name AS name, ID(p) as id ORDER BY name",
                     parameters("x", initial));
             
             
             return result;
             
            
         }
    }

    
  //Retorna os Autores que comecam com firstname e terminam com lastname
    public Integer printAuthorsFirstLastName(String initial, String finall){
    	 try (Session session = driver.session())
         {
             // Auto-commit transactions are a quick and easy way to wrap a read.
             StatementResult result = session.run(
                     "MATCH (p:Author) where p.name STARTS WITH '" + initial +"' and p.name ENDS WITH '" + finall +"' RETURN  DISTINCT p.name AS name, ID(p) as id ORDER BY name",
                     parameters("x", initial));
          // Each Cypher execution returns a stream of records.
             
             //Se achou o author retorna verdadeiro, senao falso
             if(result.hasNext()){
             	
             	Double id = 0.0;
             	
             	//System.out.println("Author encontrado");
             	 while (result.hasNext())
                  {
                      Record record = result.next();
                      // Values can be extracted from a record by index or name.
                      //System.out.println(record.get("id") + "|" + record.get("name").asString());
                      id = record.get("id").asDouble();
                  }
             	return id.intValue();
             }else{

             	//System.out.println("Author NÃO encontrado");
             	return -1;
             }
            
             
         }
    	 
    	 
    	 
    	 
    	 
    	 
    	 
    }
    
  //Retorna os Autores de determinado Alias que comecam com firstname e terminam com lastname
    public Integer printAliasFirstLastName(String initial, String finall)
    {
        try (Session session = driver.session())
        {
            // Auto-commit transactions are a quick and easy way to wrap a read.
            StatementResult result = session.run(
                    "MATCH (a:Author)-[r:ALIAS]-(b:Alias) WHERE b.name STARTS WITH '" + initial +"' and b.name ENDS WITH '" + finall +"' RETURN ID(a) AS id, b.name AS name",
                    parameters("x", initial));
            // Each Cypher execution returns a stream of records.
            //Se achou o author retorna verdadeiro, senao falso
            if(result.hasNext()){
            	
            	Double id = 0.0;
            	
            	//System.out.println("Alias encontrado");
            	 while (result.hasNext())
                 {
                     Record record = result.next();
                     // Values can be extracted from a record by index or name.
                     //System.out.println(record.get("id") + "|" + record.get("name").asString());
                     id = record.get("id").asDouble();
                 }
            	return id.intValue();
            }else{

            	//System.out.println("Alias NÃO encontrado");
            	return -1;
            }
        }
    }
    
    
    //Retorna as publicacoes de determinado autor X
    public void returnPublications(String initial){
    	 try (Session session = driver.session())
         {
             // Auto-commit transactions are a quick and easy way to wrap a read.
             StatementResult result = session.run(
                     "MATCH (p:Author)-[r:AUTHORING]->(c:Publication) where p.name STARTS WITH {x} RETURN  DISTINCT c.title AS Title ORDER BY Title",
                     parameters("x", initial));
                          
             // Each Cypher execution returns a stream of records.
             while (result.hasNext())
             {
                 Record record = result.next();
                 // Values can be extracted from a record by index or name.
                 System.out.println(record.get("Title").asString());
             }
         }
    }
    
    
    

    public void close()
    {
        // Closing a driver immediately shuts down all open connections.
        driver.close();
    }

   
}
