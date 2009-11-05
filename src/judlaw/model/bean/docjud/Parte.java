/* 
 * Universidade Federal de Campina Grande


 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas
 * 		
 */
package judlaw.model.bean.docjud;


/**
 * Define o comportamento das partes;
 * @author hal
 *
 */
public class Parte {

	private Integer id;
	
	private String titulo;
	private String nome;
	
	private DocumentoJuridico documentoJuridico;
	
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public DocumentoJuridico getDocumentoJuridico() {
		return documentoJuridico;
	}

	public void setDocumentoJuridico(DocumentoJuridico documentoJuridico) {
		this.documentoJuridico = documentoJuridico;
	}
}
