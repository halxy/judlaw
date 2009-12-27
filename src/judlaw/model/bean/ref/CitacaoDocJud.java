/* 
 * Universidade Federal de Campina Grande
 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas	
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
import judlaw.model.bean.doutrina.Doutrina;
import judlaw.model.bean.law.ElementoNorma;
import judlaw.model.bean.law.Norma;

/**
 * Classe CitacaoDocJud - representa as citacoes feitas por documentos juridicos
 * @author Halley Freitas
 *
 */
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
	@JoinColumn(name="destinodocjud_id", nullable = true)
	private DocumentoJuridico documentoJuridicoDestino;
	
	//Quando o destino da citacao eh uma norma
	@ManyToOne
	@JoinColumn(name="destinonorma_id", nullable = true)
	private Norma normaDestino;
	
	//Quando o destino da citacao eh um elementoNorma
	@ManyToOne
	@JoinColumn(name="destinoelementonorma_id", nullable = true)
	private ElementoNorma elementoNormaDestino;
	
	//Quando o destino da citacao eh uma doutrina
	@ManyToOne
	@JoinColumn(name="doutrina_id", nullable = true)
	private Doutrina doutrinaDestino;
	
	/**
	 * Quanto o destino eh outro documento juridico
	 * @param documentoJuridicoOrigem
	 * @param documentoJuridicoDestino
	 */
	public CitacaoDocJud(DocumentoJuridico documentoJuridicoOrigem, DocumentoJuridico documentoJuridicoDestino) {
		this.documentoJuridicoOrigem = documentoJuridicoOrigem;
		this.documentoJuridicoDestino = documentoJuridicoDestino;
		this.data = documentoJuridicoOrigem.getDataJulgamento();
	}
	
	/**
	 * Quando o destino eh uma Norma
	 * @param documentoJuridicoOrigem
	 * @param normaDestino
	 */
	public CitacaoDocJud(DocumentoJuridico documentoJuridicoOrigem, Norma normaDestino) {
		this.documentoJuridicoOrigem = documentoJuridicoOrigem;
		this.normaDestino = normaDestino;
		this.data = documentoJuridicoOrigem.getDataJulgamento();
	}
	
	/**
	 * Quando o destino eh um ElementoNorma
	 * @param documentoJuridicoOrigem
	 * @param elementoNormaDestino
	 */
	public CitacaoDocJud(DocumentoJuridico documentoJuridicoOrigem, ElementoNorma elementoNormaDestino) {
		this.documentoJuridicoOrigem = documentoJuridicoOrigem;
		this.elementoNormaDestino = elementoNormaDestino;
		this.data = documentoJuridicoOrigem.getDataJulgamento();
	}
	
	/**
	 * Quando o destino eh uma Doutrina
	 * @param documentoJuridicoOrigem
	 * @param doutrinaDestino
	 */
	public CitacaoDocJud(DocumentoJuridico documentoJuridicoOrigem, Doutrina doutrinaDestino) {
		this.documentoJuridicoOrigem = documentoJuridicoOrigem;
		this.doutrinaDestino = doutrinaDestino;
		this.data = documentoJuridicoOrigem.getDataJulgamento();
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

	public Doutrina getDoutrinaDestino() {
		return doutrinaDestino;
	}

	public void setDoutrinaDestino(Doutrina doutrinaDestino) {
		this.doutrinaDestino = doutrinaDestino;
	}
}
