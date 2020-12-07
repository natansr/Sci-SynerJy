package desambiguation;

import java.util.HashSet;
import java.util.Set;

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
	
	
	
	public static double diceCoefficient(String s1, String s2)
	{
		Set<String> nx = new HashSet<String>();
		Set<String> ny = new HashSet<String>();

		for (int i=0; i < s1.length()-1; i++) {
			char x1 = s1.charAt(i);
			char x2 = s1.charAt(i+1);
			String tmp = "" + x1 + x2;
			nx.add(tmp);
		}
		for (int j=0; j < s2.length()-1; j++) {
			char y1 = s2.charAt(j);
			char y2 = s2.charAt(j+1);
			String tmp = "" + y1 + y2;
			ny.add(tmp);
		}

		Set<String> intersection = new HashSet<String>(nx);
		intersection.retainAll(ny);
		double totcombigrams = intersection.size();

		return (2*totcombigrams) / (nx.size()+ny.size());
	}
	
	
}
