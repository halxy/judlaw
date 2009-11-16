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
@Table(name = "citacaodocjud")
@SequenceGenerator(name = "citacaodocjud_seq", sequenceName = "citacaodocjud_seq", initialValue = 1, allocationSize = 1)
public class CitacaoDocJud extends Referencia {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="citacaodocjud_seq")
	@Column(name="citacaodocjud_id")
	private Integer id;

	/* --------- Atributos --------- */
	private String data; // data em que foi feita a referência
	
	/* --------- Referencias --------- */
	/*
	 * ORIGEM
	 */
	//A origem sempre eh um documento juridico
	@ManyToOne
	@JoinColumn(name="origemdocjud_id")
	private DocumentoJuridico documentoJuridicoOrigem;
	
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
	 * Quando o destino eh um documento juridico
	 * @param documentoJuridicoOrigem
	 * @param destino
	 * @param data
	 */
	public CitacaoDocJud(DocumentoJuridico documentoJuridicoOrigem, DocumentoJuridico documentoJuridicoDestino,
			String data) {
		this.documentoJuridicoOrigem = documentoJuridicoOrigem;
		this.documentoJuridicoDestino = documentoJuridicoDestino;
		this.data = data;
	}
	
	/**
	 * Quando o destino eh uma Norma
	 * @param documentoJuridicoOrigem
	 * @param normaDestino
	 * @param data
	 */
	public CitacaoDocJud(DocumentoJuridico documentoJuridicoOrigem, Norma normaDestino,
			String data) {
		this.documentoJuridicoOrigem = documentoJuridicoOrigem;
		this.normaDestino = normaDestino;
		this.data = data;
	}
	
	/**
	 * Quando o destino eh um ElementoNorma
	 * @param documentoJuridicoOrigem
	 * @param elementoNormaDestino
	 * @param data
	 */
	public CitacaoDocJud(DocumentoJuridico documentoJuridicoOrigem, ElementoNorma elementoNormaDestino,
			String data) {
		this.documentoJuridicoOrigem = documentoJuridicoOrigem;
		this.elementoNormaDestino = elementoNormaDestino;
		this.data = data;
	}

	/**
	 * 
	 * @param data
	 */
	public CitacaoDocJud(String data){
		this.data = data;
	}

	/**
	 * Construtor vazio
	 */
	public CitacaoDocJud(){}
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public DocumentoJuridico getDocumentoJuridicoOrigem() {
		return documentoJuridicoOrigem;
	}

	public void setDocumentoJuridicoOrigem(DocumentoJuridico documentoJuridicoOrigem) {
		this.documentoJuridicoOrigem = documentoJuridicoOrigem;
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
