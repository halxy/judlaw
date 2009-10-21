package judlaw.model.law;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import judlaw.model.reference.Referencia;


/**
 * Classe ElementoNorma - superclasse que representa os elementos de uma norma:
 * Partes, Livros, Titulos, Capitulos, Secoes, Subsecoes, Artigos, Paragrafos, Incisos, Alineas e Itens.
 * @author Halley Freitas
 *
 */
@Entity
@SequenceGenerator(name="SEQ_ELENORMA", sequenceName="elenorma_sequence")
public abstract class ElementoNorma implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//id
	private Long id;
	private String identificadorUnico; // ex: cp_art120_par2.
	
	//dados
	private String texto;
	private String data;
	private String vigencia;
	// O elemento no qual o elementoDaNorma está inserido. Ex: inciso está inserido no paragrafo.
	private String elementoPai;
	
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
	}

	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ELENORMA")
	@Column(name="elementonorma_id")
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
	
	public String getElementoPai() {
		return elementoPai;
	}

	public void setElementoPai(String elementoPai) {
		this.elementoPai = elementoPai;
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
}
