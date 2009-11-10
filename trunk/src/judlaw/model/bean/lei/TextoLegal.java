/* 
 * Universidade Federal de Campina Grande


 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas
 * 		
 */
package judlaw.model.bean.lei;


/**
 * Classe Abstrata TextoLegal - Superclasse de Norma e ElementoNorma
 * @author Halley Freitas
 *
 */
public abstract class TextoLegal {

	private String identificadorUnico; // cp_art120; lei1234; cc_art1_par2.
	private String tipo; // tipo do textoLegal
	private String dataPublicacao; // dd/MM/yyy
	private String vigencia; // dd/MM/yyyy-dd2/MM2/yyy2
	
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
	
	public String getVigencia() {
		return vigencia;
	}
	
	public void setVigencia(String vigencia) {
		this.vigencia = vigencia;
	}
}
