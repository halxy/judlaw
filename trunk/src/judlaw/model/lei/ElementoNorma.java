package judlaw.model.lei;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import judlaw.model.ref.Referencia;


/**
 * Classe ElementoNorma - superclasse que representa os elementos de uma norma:
 * Partes, Livros, Titulos, Capitulos, Secoes, Subsecoes, Artigos, Paragrafos, Incisos, Alineas e Itens.
 * @author Halley Freitas
 *
 */
@Entity
@SequenceGenerator(name="SEQ_ELENORMA", sequenceName="elenorma_sequence")
public class ElementoNorma implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//id
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="elementonorma_id")
	private Long id;
	private String identificadorUnico; // ex: cp_art120_par2.
	
	//informacoes
	private String tipoElemento; // Artigo, Paragrafo, Inciso, etc.
	private String texto;
	private String data;
	private String vigencia;
	// O elemento/normal no qual o elementoDaNorma está inserido. Ex: inciso está inserido no paragrafo.
	private String pai;
	
	// Listas de referencias associadas ao ElementoNorma
	@OneToMany(cascade=CascadeType.ALL)
	private List<Referencia> referenciasFeitas;
	@OneToMany(cascade=CascadeType.ALL)
	private List<Referencia> referenciasRecebidas;
		
	// Lista de elementos que compoem o ElementoNorma
	@OneToMany(cascade=CascadeType.ALL, mappedBy="elementosNorma", targetEntity=ElementoNorma.class)
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
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
}
