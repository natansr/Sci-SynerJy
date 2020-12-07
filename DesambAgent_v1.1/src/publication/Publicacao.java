package publication;

import java.util.ArrayList;

public class Publicacao {
	private String titulo;
	public ArrayList<Autor> autores = new ArrayList<Autor>();
	
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

}
