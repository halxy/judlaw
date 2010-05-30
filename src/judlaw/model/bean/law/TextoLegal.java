/* 
 * Universidade Federal de Campina Grande
 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas	
 */
package judlaw.model.bean.law;

import java.util.List;

import judlaw.model.bean.ref.Alteracao;
import judlaw.model.bean.ref.CitacaoTextLeg;



/**
 * Classe Abstrata TextoLegal - Superclasse de Norma e ElementoNorma
 * @author Halley Freitas
 *
 */
public abstract class TextoLegal {

	private String identificadorUnico; // cp_art120; lei1234; cc_art1_par2.
	private String tipo; // tipo do textoLegal
	private String dataPublicacao; // dd/MM/yyy
	private String validade; // dd/MM/yyyy-dd2/MM2/yyy2
	private String vigencia; // dd/MM/yyyy-dd2/MM2/yyy2
	private int versao;
	
	private List<CitacaoTextLeg> citacoesFeitas;
	private List<Alteracao> alteracoesFeitas;
	
	/* -------------------------------------------------
	 * --------------- GETTERS e SETTERS ---------------
	 * -------------------------------------------------
	 */
	
	/**
	 * 
	 * @return
	 */
	public String getIdentificadorUnico() {
		return identificadorUnico;
	}
	
	/**
	 * 
	 * @param identificadorUnico
	 */
	public void setIdentificadorUnico(String identificadorUnico) {
		this.identificadorUnico = identificadorUnico;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getTipo() {
		return tipo;
	}
	
	/**
	 * 
	 * @param tipo
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getDataPublicacao() {
		return dataPublicacao;
	}
	
	/**
	 * 
	 * @param dataPublicacao
	 */
	public void setDataPublicacao(String dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getVigencia() {
		return vigencia;
	}
	
	/**
	 * 
	 * @param vigencia
	 */
	public void setVigencia(String vigencia) {
		this.vigencia = vigencia;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<CitacaoTextLeg> getCitacoesFeitas() {
		return citacoesFeitas;
	}
	
	/**
	 * 
	 * @param citacoesFeitas
	 */
	public void setCitacoesFeitas(List<CitacaoTextLeg> citacoesFeitas) {
		this.citacoesFeitas = citacoesFeitas;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Alteracao> getAlteracoesFeitas() {
		return alteracoesFeitas;
	}
	
	/**
	 * 
	 * @param alteracoesFeitas
	 */
	public void setAlteracoesFeitas(List<Alteracao> alteracoesFeitas) {
		this.alteracoesFeitas = alteracoesFeitas;
	}

	/**
	 * 
	 * @return
	 */
	public String getValidade() {
		return validade;
	}

	/**
	 * 
	 * @param validade
	 */
	public void setValidade(String validade) {
		this.validade = validade;
	}

	public int getVersao() {
		return versao;
	}

	public void setVersao(int versao) {
		this.versao = versao;
	}
}
