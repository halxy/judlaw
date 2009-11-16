/* 
 * Universidade Federal de Campina Grande


 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas
 * 		
 */
package judlaw.model.bean.ref;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import judlaw.model.bean.docjud.DocumentoJuridico;
import judlaw.model.bean.lei.ElementoNorma;
import judlaw.model.bean.lei.Norma;

@Entity
@Table(name = "citacaotextleg")
@SequenceGenerator(name = "citacaotextleg_seq", sequenceName = "citacaotextleg_seq", initialValue = 1, allocationSize = 1)
public class CitacaoTextLeg extends Referencia {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="citacaotextleg_seq")
	@Column(name="citacaotextleg_id")
	private Integer id;

	/* --------- Atributos --------- */
	private String data; // data em que foi feita a referência
	
	/* --------- Referencias --------- */
	/*
	 * ORIGENS
	 */
	//Norma
	@ManyToOne
	@JoinColumn(name="origemnorma_id")
	private Norma normaOrigem;
	
	//Norma
	@ManyToOne
	@JoinColumn(name="origemelementonorma_id")
	private ElementoNorma elementoNormaOrigem;
	
	/*
	 * DESTINOS
	 */
	//Quando o destino da citacao eh um documento juridico
	@ManyToOne
	@JoinColumn(name="destinodocjud_id")
	private DocumentoJuridico documentoJuridicoDestino;
	
	//Quando o destino da citacao eh uma norma
	@ManyToOne
	@JoinColumn(name="destinonorma_id")
	private Norma normaDestino;
	
	//Quando o destino da citacao eh um elementoNorma
	@ManyToOne
	@JoinColumn(name="destinoelementonorma_id")
	private ElementoNorma elementoNormaDestino;
	
	/**
	 * 
	 * @param normaOrigem
	 * @param normaDestino
	 * @param data
	 */
	public CitacaoTextLeg(Norma normaOrigem, Norma normaDestino, String data) {
		this.normaOrigem = normaOrigem;
		this.normaDestino = normaDestino;
		this.data = data;
	}
	
	/**
	 * 
	 * @param normaOrigem
	 * @param elementoNormaDestino
	 * @param data
	 */
	public CitacaoTextLeg(Norma normaOrigem, ElementoNorma elementoNormaDestino, String data) {
		this.normaOrigem = normaOrigem;
		this.elementoNormaDestino = elementoNormaDestino;
		this.data = data;
	}
	
	/**
	 * 
	 * @param normaOrigem
	 * @param documentoJuridicoDestino
	 * @param data
	 */
	public CitacaoTextLeg(Norma normaOrigem, DocumentoJuridico documentoJuridicoDestino, String data) {
		this.normaOrigem = normaOrigem;
		this.documentoJuridicoDestino = documentoJuridicoDestino;
		this.data = data;
	}
	
	/**
	 * 
	 * @param elementoNormaOrigem
	 * @param normaDestino
	 * @param data
	 */
	public CitacaoTextLeg(ElementoNorma elementoNormaOrigem, Norma normaDestino, String data) {
		this.elementoNormaOrigem = elementoNormaOrigem;
		this.normaDestino = normaDestino;
		this.data = data;
	}
	
	/**
	 * 
	 * @param elementoNormaOrigem
	 * @param elementoNormaDestino
	 * @param data
	 */
	public CitacaoTextLeg(ElementoNorma elementoNormaOrigem, ElementoNorma elementoNormaDestino, String data) {
		this.elementoNormaOrigem = elementoNormaOrigem;
		this.elementoNormaDestino = elementoNormaDestino;
		this.data = data;
	}
	
	/**
	 * 
	 * @param elementoNormaOrigem
	 * @param documentoJuridicoDestino
	 * @param data
	 */
	public CitacaoTextLeg(ElementoNorma elementoNormaOrigem, DocumentoJuridico documentoJuridicoDestino, String data) {
		this.elementoNormaOrigem = elementoNormaOrigem;
		this.documentoJuridicoDestino = documentoJuridicoDestino;
		this.data = data;
	}

	/**
	 * Construtor vazio
	 */
	public CitacaoTextLeg(){}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Norma getNormaOrigem() {
		return normaOrigem;
	}

	public void setNormaOrigem(Norma normaOrigem) {
		this.normaOrigem = normaOrigem;
	}

	public ElementoNorma getElementoNormaOrigem() {
		return elementoNormaOrigem;
	}

	public void setElementoNormaOrigem(ElementoNorma elementoNormaOrigem) {
		this.elementoNormaOrigem = elementoNormaOrigem;
	}

	public DocumentoJuridico getDocumentoJuridicoDestino() {
		return documentoJuridicoDestino;
	}

	public void setDocumentoJuridicoDestino(
			DocumentoJuridico documentoJuridicoDestino) {
		this.documentoJuridicoDestino = documentoJuridicoDestino;
	}

	public Norma getNormaDestino() {
		return normaDestino;
	}

	public void setNormaDestino(Norma normaDestino) {
		this.normaDestino = normaDestino;
	}

	public ElementoNorma getElementoNormaDestino() {
		return elementoNormaDestino;
	}

	public void setElementoNormaDestino(ElementoNorma elementoNormaDestino) {
		this.elementoNormaDestino = elementoNormaDestino;
	}
}
