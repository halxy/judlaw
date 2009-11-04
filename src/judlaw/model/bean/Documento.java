/* 
 * Universidade Federal de Campina Grande


 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas
 * 		
 */
package judlaw.model.bean;

/**
 * Classe Abstrata Documento - Superclasse dos documentos jurídicos e legais
 * @author Halley Freitas
 *
 */
public abstract class Documento {
	
	private String identificadorUnico; // cp_art120; lei1234; cc_art1_par2.
	private String tipo;
	private String dataPublicacao; // dd/MM/yyy
	
	public String getIdentificadorUnico() {
		return identificadorUnico;
	}
	
	public void setIdentificadorUnico(String identificadorUnico) {
		this.identificadorUnico = identificadorUnico;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDataPublicacao() {
		return dataPublicacao;
	}
	
	public void setDataPublicacao(String dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}
}
