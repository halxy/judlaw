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

@Entity
@Table(name = "citacaodocjud")
@SequenceGenerator(name = "citacaodocjud_seq", sequenceName = "citacaodocjud_seq", initialValue = 1, allocationSize = 1)
public class CitacaoDocJud extends Referencia {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="citacaodocjud_seq")
	@Column(name="citacaodocjud_id")
	private Integer id;

	/* --------- Atributos --------- */
	private String destino; // identificadorUnico do Documento que recebeu a referência
	private String data; // data em que foi feita a referência
	
	@ManyToOne
	@JoinColumn(name="origem_id")
	private DocumentoJuridico documentoJuridicoOrigem;
	
	/**
	 * 
	 * @param documentoJuridicoOrigem
	 * @param destino
	 * @param data
	 */
	public CitacaoDocJud(DocumentoJuridico documentoJuridicoOrigem, String destino,
			String data) {
		this.documentoJuridicoOrigem = documentoJuridicoOrigem;
		this.destino = destino;
		this.data = data;
	}

	public CitacaoDocJud(){}

	public DocumentoJuridico getDocumentoJuridicoOrigem() {
		return documentoJuridicoOrigem;
	}

	public void setDocumentoJuridicoOrigem(DocumentoJuridico documentoJuridicoOrigem) {
		this.documentoJuridicoOrigem = documentoJuridicoOrigem;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

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
}
