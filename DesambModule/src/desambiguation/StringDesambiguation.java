package desambiguation;

import org.apache.commons.text.similarity.*;

public class StringDesambiguation {

	//Metodo para executar o coeficiente Jaccard
	public Double Jaccard(String str1, String str2){
		
		JaccardSimilarity j = new JaccardSimilarity();
		
		return j.apply(str1, str2);
		
	}
	
	//Metodo para executar a distancia de levenshtein
	public Integer Leveshtein(String str1, String str2){
	
		LevenshteinDistance l = new LevenshteinDistance();
		
		return l.apply(str1, str2);
	
	}
}
