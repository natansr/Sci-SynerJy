package publication;

import java.io.File;
import java.io.FileFilter;

public class AcessPublications {

	
	
	//Metodo para retornar todos os bibtex 
	//que estão na pasta designada
	public File[] PathOfBibTex(){
		
		FileFilter filter = new FileFilter() {
			public boolean accept(File file) {
				return file.getName().endsWith(".bib");
			}
		};

		
		//File dir = new File("/Users/natanrodrigues/Documents/workspace/DesambAgent/src/bibfolder/professores_ufmg");
		//File dir = new File("/Users/natanrodrigues/Documents/workspace/DesambAgent/src/bibfolder/professores_unb");
		//File dir = new File("/Users/natanrodrigues/Documents/workspace/DesambAgent/src/bibfolder/professores_usp");
		//File dir = new File("/Users/natanrodrigues/Documents/workspace/DesambAgent/src/bibfolder/professores_ufam");
		//File dir = new File("/Users/natanrodrigues/Documents/workspace/DesambAgent/src/bibfolder/professores_ufrn");
		
		File dir = new File("C:\\Users\\Usuário\\eclipse-workspace\\DesambAgent\\src\\bibfolder\\todas_unis");
		

		
		File[] files = dir.listFiles(filter);
		
		return files;
	}
}
