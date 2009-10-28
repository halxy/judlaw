package judlaw.model.lei;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import judlaw.model.ref.Referencia;


/**
 * Classe ElementoNorma - superclasse que representa os elementos de uma norma:
 * Partes, Livros, Titulos, Capitulos, Secoes, Subsecoes, Artigos, Paragrafos, Incisos, Alineas e Itens.
 * @author Halley Freitas
 *
 */
public class ElementoNorma implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String identificadorUnico; // ex: cp_art120_par2.
	
	//informacoes
	private String tipoElemento; // Artigo, Paragrafo, Inciso, etc.
	private String texto;
	private String data;
	private String vigencia;
	// O elemento/normal no qual o elementoDaNorma está inserido. Ex: inciso está inserido no paragrafo.
	private String pai;
	
	// Listas de referencias associadas ao ElementoNorma
	private List<Referencia> referenciasFeitas;
	private List<Referencia> referenciasRecebidas;
		
	// Lista de elementos que compoem o ElementoNorma
	private List<? extends ElementoNorma> elementosNorma;
	
	
	/**
	 * Construtor sem parâmetros
	 */
	public ElementoNorma() {
		inicializaListas();
	}
	
	private void inicializaListas(){
		this.referenciasFeitas = new ArrayList<Referencia>();
		this.referenciasRecebidas = new ArrayList<Referencia>();
		this.elementosNorma = new ArrayList<ElementoNorma>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIdentificadorUnico() {
		return identificadorUnico;
	}

	public void setIdentificadorUnico(String identificadorUnico) {
		this.identificadorUnico = identificadorUnico;
	}

	public String getTexto() {
		return texto;
	}
	
	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	public String getVigencia() {
		return vigencia;
	}
	
	public void setVigencia(String vigencia) {
		this.vigencia = vigencia;
	}
	
	/**
	 * Retorna o pai do elemento
	 * @return
	 */
	public String getPai() {
		if(this.pai == null && this.identificadorUnico != null) {
			setPai( getPaiPeloIdUnico() );
		}
		return this.pai;
	}
	
	public void setPai(String pai) {
		this.pai = pai;
	}
	
	public List<Referencia> getReferenciasFeitas() {
		return referenciasFeitas;
	}
	
	public void setReferenciasFeitas(List<Referencia> referenciasFeitas) {
		this.referenciasFeitas = referenciasFeitas;
	}
	
	public List<Referencia> getReferenciasRecebidas() {
		return referenciasRecebidas;
	}
	
	public void setReferenciasRecebidas(List<Referencia> referenciasRecebidas) {
		this.referenciasRecebidas = referenciasRecebidas;
	}
	
	public List<? extends ElementoNorma> getElementosNorma() {
		return elementosNorma;
	}

	public void setElementosNorma(List<? extends ElementoNorma> elementosNorma) {
		this.elementosNorma = elementosNorma;
	}
	
	public String getTipoElemento() {
		return tipoElemento;
	}

	public void setTipoElemento(String tipoElemento) {
		this.tipoElemento = tipoElemento;
	}
	
	/*
	 * Retorna o pai do elemento usando como parametro seu identificador unico
	 */
	private String getPaiPeloIdUnico() {
		String idUnico = getIdentificadorUnico();
		StringTokenizer tokenId = new StringTokenizer(idUnico, "_");
		String tokenAux = "";
		while( tokenId.hasMoreTokens() ) {
			tokenAux = tokenId.nextToken();
		}
		int posicaoId = idUnico.indexOf("_"+tokenAux);	
		return idUnico.substring(0, posicaoId);
	}
	
	/**
	 * Adiciona uma referencia feita
	 * @param ref
	 */
	public void addReferenciaFeita(Referencia ref) {
		getReferenciasFeitas().add(ref);
	}
	
	/**
	 * Remove uma referencia feita
	 * @param ref
	 */
	public void removeReferenciaFeita(Referencia ref) {
		getReferenciasFeitas().remove(ref);
	}
	
	/**
	 * Adiciona uma referencia recebida
	 * @param ref
	 */
	public void addReferenciaRecebida(Referencia ref) {
		getReferenciasRecebidas().add(ref);
	}
	
	/**
	 * Remove uma referencia recebida
	 * @param ref
	 */
	public void removeReferenciaRecebida(Referencia ref) {
		getReferenciasFeitas().remove(ref);
	}
}
