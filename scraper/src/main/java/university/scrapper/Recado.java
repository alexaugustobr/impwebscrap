package university.scrapper;

public class Recado {
	
	private final String titulo;
	private final String descricao;
	
	public Recado(String titulo, String descricao) {
		this.titulo = titulo;
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public String getTitulo() {
		return titulo;
	}
}
