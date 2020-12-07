package publication;

import java.io.Serializable;

public class Autor implements Serializable{
	private String nome;
	private String[] rede_coautoria;
	
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String[] getRede_coautoria() {
		return rede_coautoria;
	}
	public void setRede_coautoria(String[] rede_coautoria) {
		this.rede_coautoria = rede_coautoria;
	}
}
