package publication;

import java.util.ArrayList;
import java.util.ListIterator;

public class ParserBibTex {
	
	
	//Metodo para remove os acentos do bibtex
	public String RemoveAccent(String string1){
		
		string1 = string1.replace("{\\'{a}}", "á");
		string1 = string1.replace("{\\'{e}}", "é");
		string1 = string1.replace("{\\'{i}}", "í");
		string1 = string1.replace("{\\'{o}}", "ó");
		string1 = string1.replace("{\\'{u}}", "ú");
		string1 = string1.replace("{\\`{a}}", "à");
		
	
		
		string1 = string1.replace("{\\'{\\a}}", "á");
		string1 = string1.replace("{\\'{\\e}}", "é");
		string1 = string1.replace("{\\'{\\i}}", "í");
		string1 = string1.replace("{\\'{\\o}}", "ó");
		string1 = string1.replace("{\\'{\\u}}", "ú");
		
		string1 = string1.replace("{\\aa}", "å");
		
		string1 = string1.replace("{\\o}", "ø");
		string1 = string1.replace("{\\O}", "Ø");
		
		
		string1 = string1.replace("{\\`{a}}", "à");
		string1 = string1.replace("{\\`{A}}", "À");		
		string1 = string1.replace("{\\`{e}}", "è");
		string1 = string1.replace("{\\`{E}}", "È");		
		string1 = string1.replace("{\\`{i}}", "ì");
		string1 = string1.replace("{\\`{I}}", "Ì");		
		string1 = string1.replace("{\\`{o}}", "ò");
		string1 = string1.replace("{\\`{O}}", "Ò");
		string1 = string1.replace("{\\`{U}}", "Ù");	
		string1 = string1.replace("{\\'{a}}l'", "ál");	

		
		string1 = string1.replace("{\\`{u}}", "ù");
		string1 = string1.replace("{\\\"{o}}", "ö");
		
		string1 = string1.replace("{\\\"{u}}", "ü");
		string1 = string1.replace("{\\\"{\\u}}", "ü");
		string1 = string1.replace("{\\\"{a}}", "ä");
		string1 = string1.replace("{\\\"{\\a}}", "ä");		
		string1 = string1.replace("{\\\"{i}}", "ï");
		string1 = string1.replace("{\\\"{\\i}}", "ï");
		string1 = string1.replace("{\\\"{e}}", "ë");
		string1 = string1.replace("{\\\"{\\e}}", "ë");		
		string1 = string1.replace("{\\\"{o}}", "ö");
		string1 = string1.replace("{\\\"{\\o}}", "ö");
		
		string1 = string1.replace("{\\\"{U}}", "Ü");
		string1 = string1.replace("{\\\"{\\U}}", "Ü");		
		string1 = string1.replace("{\\\"{O}}", "Ö");	
		string1 = string1.replace("{\\\"{\\O}}", "Ö");		
		string1 = string1.replace("{\\\"{I}}", "Ï");
		string1 = string1.replace("{\\\"{\\I}}", "Ï");		
		string1 = string1.replace("{\\\"{E}}", "Ë");	
		string1 = string1.replace("{\\\"{\\E}}", "Ë");		
		string1 = string1.replace("{\\\"{A}}", "Ä");		
		string1 = string1.replace("{\\\"{\\A}}", "Ä");	
		
		string1 = string1.replace("{\\'{A}}", "Á");
		string1 = string1.replace("{\\'{E}}", "É");
		string1 = string1.replace("{\\'{I}}", "Í");
		string1 = string1.replace("{\\'{O}}", "Ó");
		string1 = string1.replace("{\\'{U}}", "Ú");
		string1 = string1.replace("{\\'{\\A}}", "Á");
		string1 = string1.replace("{\\'{\\E}}", "É");
		string1 = string1.replace("{\\'{\\I}}", "Í");
		string1 = string1.replace("{\\'{\\O}}", "Ó");
		string1 = string1.replace("{\\'{\\U}}", "Ú");

		
		
		string1 = string1.replace("{\\~{a}}", "ã");
		string1 = string1.replace("{\\~{A}}", "Ã");
		string1 = string1.replace("{\\~{\\a}}", "ã");
		string1 = string1.replace("{\\~{\\A}}", "Ã");
		string1 = string1.replace("{\\~{o}}", "õ");
		string1 = string1.replace("{\\~{O}}", "Õ");
		string1 = string1.replace("{\\~{\\o}}", "õ");
		string1 = string1.replace("{\\~{\\O}}", "Õ");
		string1 = string1.replace("{\\~{n}}", "ñ");
		string1 = string1.replace("{\\~{N}}", "Ñ");		
		string1 = string1.replace("{\\~{\\n}}", "ñ");
		string1 = string1.replace("{\\~{\\N}}", "Ñ");
		
		string1 = string1.replace("{\\^{a}}", "â");
		string1 = string1.replace("{\\^{\\a}}", "â");
		string1 = string1.replace("{\\^{e}}", "ê");
		string1 = string1.replace("{\\^{\\e}}", "ê");
		string1 = string1.replace("{\\^{i}}", "î");
		string1 = string1.replace("{\\^{\\i}}", "î");
		string1 = string1.replace("{\\^{o}}", "ô");
		string1 = string1.replace("{\\^{\\o}}", "ô");
		string1 = string1.replace("{\\^{u}}", "û");
		string1 = string1.replace("{\\^{\\u}}", "û");
		string1 = string1.replace("{\\^{A}}", "Â");
		string1 = string1.replace("{\\^{\\A}}", "Â");
		string1 = string1.replace("{\\^{E}}", "Ê");
		string1 = string1.replace("{\\^{\\E}}", "Ê");
		string1 = string1.replace("{\\^{I}}", "Î");
		string1 = string1.replace("{\\^{\\I}}", "Î");		
		string1 = string1.replace("{\\^{O}}", "Ô");
		string1 = string1.replace("{\\^{\\O}}", "Ô");		
		string1 = string1.replace("{\\^{U}}", "Û");
		string1 = string1.replace("{\\^{\\U}}", "Û");		
		

		
		
		string1 = string1.replace("{\\c{c}}", "ç");
		string1 = string1.replace("{\\c{C}}", "Ç");
		
		string1 = string1.replace("{-}", "-");

		string1 = string1.replace("Jr.", "Junior");
		
		string1 = string1.replace("{\\u{a}}", "ua");
		string1 = string1.replace("{\\ss}", "ß");		


		string1 = string1.replace("A'", "A`");
		string1 = string1.replace("B'", "B`");
		string1 = string1.replace("C'", "C`");
		string1 = string1.replace("D'", "D`");
		string1 = string1.replace("E'", "E`");
		string1 = string1.replace("F'", "F`");
		string1 = string1.replace("G'", "G`");
		string1 = string1.replace("H'", "H`");
		string1 = string1.replace("I'", "I`");
		string1 = string1.replace("J'", "J`");
		string1 = string1.replace("K'", "K`");
		string1 = string1.replace("L'", "L`");
		string1 = string1.replace("M'", "M`");
		string1 = string1.replace("N'", "N`");
		string1 = string1.replace("O'", "O`");
		string1 = string1.replace("P'", "P`");		
		string1 = string1.replace("Q'", "Q`");
		string1 = string1.replace("R'", "R`");
		string1 = string1.replace("S'", "S`");
		string1 = string1.replace("T'", "T`");
		string1 = string1.replace("U'", "U`");
		string1 = string1.replace("V'", "V`");
		string1 = string1.replace("W'", "W`");
		string1 = string1.replace("X'", "X`");
		string1 = string1.replace("Y'", "Y`");
		string1 = string1.replace("Z'", "Z`");
		
		string1 = string1.replace("a'", "a`");
		string1 = string1.replace("b'", "b`");
		string1 = string1.replace("c'", "c`");
		string1 = string1.replace("d'", "d`");
		string1 = string1.replace("e'", "e`");
		string1 = string1.replace("f'", "f`");
		string1 = string1.replace("g'", "g`");
		string1 = string1.replace("h'", "h`");
		string1 = string1.replace("i'", "i`");
		string1 = string1.replace("j'", "j`");
		string1 = string1.replace("k'", "k`");
		string1 = string1.replace("l'", "l`");
		string1 = string1.replace("m'", "m`");
		string1 = string1.replace("n'", "n`");
		string1 = string1.replace("o'", "o`");
		string1 = string1.replace("p'", "p`");
		string1 = string1.replace("q'", "q`");
		string1 = string1.replace("r'", "r`");
		string1 = string1.replace("s'", "s`");
		string1 = string1.replace("t'", "t`");
		string1 = string1.replace("u'", "u`");
		string1 = string1.replace("v'", "v`");
		string1 = string1.replace("w'", "w`");
		string1 = string1.replace("x'", "x`");
		string1 = string1.replace("y'", "y`");
		string1 = string1.replace("z'", "z`");
		

		
		return string1;
	}
	
	//Retorna um array de autores retirados de uma entrada ORCID
	public ArrayList<String[]> GetAuthorsOrcid(ArrayList<String> authors){
		
		
		ArrayList<String[]> new_authors = new ArrayList<String[]>();
		
		 //Cada item da lista recebida é uma publicacao com autores e co-autores

        for(int i = 0;i<authors.size();i++)  
        {  
            

    		//Pegando os autores e os colocando em um vetor
    		String[] part2_authors = authors.get(i).split(";");
    		//Tratando cada autor

            for(int j=0;j<part2_authors.length;j++){
         	   
            	part2_authors[j] = part2_authors[j].trim();
            	
            	
            	//Se o nome do autor tiver ponto de abreviação então acrescenta um espaço, isso irá auxiliar na desamb
            	part2_authors[j] = part2_authors[j].replace(".", ". ");
            	
            	//Se o nome do autor tiver ponto de abreviação então acrescenta um espaço, isso irá auxiliar na desamb
            	part2_authors[j] = new ParserBibTex().AddDots(part2_authors[j]);
            	
            	//Se o nome do autor tiver espaços duplicados entre os nomes, remove          	
            	part2_authors[j] = part2_authors[j].replace("  ", " ");

            	
            	part2_authors[j] = part2_authors[j].trim();
            	
            }
    		
            

            for(int j=0;j<part2_authors.length;j++){
         	   
            	part2_authors[j] = part2_authors[j].trim();

            }
            
            
            
            new_authors.add(part2_authors);
            
        }  
		
        return new_authors;
	}

	//Retorna um array de autores retirados de uma entrada DBLPBIBTEX
	public ArrayList<String[]> GetAuthors(ArrayList<String> authors){
		
		
		ArrayList<String[]> new_authors = new ArrayList<String[]>();
		
		 //Cada item da lista recebida é uma publicacao com autores e co-autores

        for(int i = 0;i<authors.size();i++)  
        {  
            
        	//Tratando a linha bibtex
    		authors.set(i, authors.get(i).replace("author", ""));
    		authors.set(i, authors.get(i).replace("=", ""));
    		authors.set(i, authors.get(i).replace("{", ""));
    		authors.set(i, authors.get(i).replace("}", ""));
    		
    		
    		//Pegando os autores e os colocando em um vetor
    		String[] part2_authors = authors.get(i).split(" and ");
    		//Tratando cada autor

            for(int j=0;j<part2_authors.length;j++){
         	   
            	part2_authors[j] = part2_authors[j].trim();
            	
            }
    		
            new_authors.add(part2_authors);
            
        }  
		
        return new_authors;
	}

	//Adiciona um ponto de abreviacao em sobrenomes abreviados somente com um caractere
	//Ex.: Sebastião R Maia => Sebastião R. Maia
	public String AddDots(String name){
		
		String[] splitname = name.split(" ");
		
		 for(int i = 0;i<splitname.length;i++){
			 
			 if(splitname[i].length()==1){
				 splitname[i] = splitname[i] + ".";
			 }
			 
		 }
		 
		 String name_return = "";
		 
		 for(int j = 0;j<splitname.length;j++){
			 name_return = name_return + splitname[j] + " ";
		 }
		 return name_return.trim();
		
	}

}
