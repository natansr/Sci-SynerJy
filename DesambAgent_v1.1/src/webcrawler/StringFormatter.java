package webcrawler;

public class StringFormatter {
	public String capitalizeWord(String str){  
	    String words[]=str.trim().split(" ");  
	    String capitalizeWord="";  
	    for(String w:words){  
	        String first=w.substring(0,1);  
	        String afterfirst=w.substring(1);  
	        capitalizeWord+=first.toUpperCase()+afterfirst+" ";  
	    }  
	    return capitalizeWord.trim();  
	}
}
