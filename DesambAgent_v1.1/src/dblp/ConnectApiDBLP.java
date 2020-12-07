package dblp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.json.*;

public class ConnectApiDBLP {

	
	
	  private static String readAll(Reader rd) throws IOException {
		    StringBuilder sb = new StringBuilder();
		    int cp;
		    while ((cp = rd.read()) != -1) {
		      sb.append((char) cp);
		    }
		    return sb.toString();
		  }
	
	
	

	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
	    InputStream is = new URL(url).openStream();
	    try {
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
	      
	      //System.out.println(jsonText);
	      
	      JSONObject json = new JSONObject(jsonText);
	      return json;
	    } finally {
	      is.close();
	    }
	  }
		
	
		
		 public String getPidAuthor(String nome) throws IOException, JSONException {
		        
			 
			 String new_nome = nome.replace(" ", "%20");
			 
			 
			 
			 //System.out.println(nome);
			 
			 
			 JSONObject obj = readJsonFromUrl("https://dblp.org/search/author/api?q=" + new_nome + "&format=json");
			 
			 
			 
			 String result = obj.getJSONObject("result").getJSONObject("hits").getJSONArray("hit").getJSONObject(0).getJSONObject("info").getString("url");
			 
			 
			 JSONArray array = obj.getJSONObject("result").getJSONObject("hits").getJSONArray("hit");
			 
			 
			 for(int i=0;i<array.length();i++) {
				 
				 //System.out.println(obj.getJSONObject("result").getJSONObject("hits").getJSONArray("hit").getJSONObject(i).getJSONObject("info").getString("author"));
				 
				 if(obj.getJSONObject("result").getJSONObject("hits").getJSONArray("hit").getJSONObject(i).getJSONObject("info").getString("author").equalsIgnoreCase(nome)) {
					 
					 
					
					 
					 result = obj.getJSONObject("result").getJSONObject("hits").getJSONArray("hit").getJSONObject(i).getJSONObject("info").getString("url"); 
				 }

			 }
			 
			 
			 
			 
			 
			 
			 System.out.println(result);
			 
		        
		        return result;
		    }

		 
		 
		 
		 
		 //Metodo para pegar o arquivo .bib de cada autor no DBLP
		 public void generateAuthorBibFile(String FILE_URL, String autor_name, String institution) throws IOException {
			 
			 String FILE_NAME = "C:\\Users\\Usuário\\eclipse-workspace\\DesambAgent_v3\\src\\bibfolder\\todas_unis\\" + autor_name + "_" + institution + ".bib";
			 
			 InputStream in = new URL(FILE_URL).openStream();
			 Files.copy(in, Paths.get(FILE_NAME), StandardCopyOption.REPLACE_EXISTING);
			 
			 
		 }
		 
		 
		 
		 
		 
}
