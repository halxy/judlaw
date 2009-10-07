package judlaw.model.docjud;

/**
 * Define o comportamento das partes;
 * @author hal
 *
 */
public class Parte {

	private String titulo;
	private String nome;
	
	/**
	 * 
	 * @param titulo
	 * @param nome
	 */
	public Parte(String titulo, String nome) {
		this.titulo = titulo;
		this.nome = nome;
	}
	
	/**
	 * Construtor vazio
	 */
	public Parte() {}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}	
}
