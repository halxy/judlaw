/* 
 * Universidade Federal de Campina Grande
 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas	
 */
package judlaw.model.bean.docjud;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Classe Ementa. Representa a ementa de um documento juridico.
 * @author Halley Freitas
 *
 */
@Entity
@Table(name = "ementa")
@SequenceGenerator(name = "ementa_seq", sequenceName = "ementa_seq", initialValue = 1, allocationSize = 1)
public class Ementa {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ementa_seq")
	@Column(name="ementa_id")
	private Integer id;

	private String texto;
	
	//Documento Juridico
	@OneToOne(mappedBy = "ementa")
	@JoinColumn(name="documentojuridico_id")
	private DocumentoJuridico documentoJuridico;
	
	/**
	 * 
	 * @param texto
	 */
	public Ementa(String texto) {
		this.texto = texto;
	}
	
	/**
	 * Construtor Vazio
	 */
	public Ementa() {}

	/**
	 * 
	 * @return
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 
	 * @return
	 */
	public String getTexto() {
		return texto;
	}

	/**
	 * 
	 * @param texto
	 */
	public void setTexto(String texto) {
		this.texto = texto;
	}

	/**
	 * 
	 * @return
	 */
	public DocumentoJuridico getDocumentoJuridico() {
		return documentoJuridico;
	}

	/**
	 * 
	 * @param documentoJuridico
	 */
	public void setDocumentoJuridico(DocumentoJuridico documentoJuridico) {
		this.documentoJuridico = documentoJuridico;
	}
}
