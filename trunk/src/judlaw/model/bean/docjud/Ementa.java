/* 
 * Universidade Federal de Campina Grande


 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas
 * 		
 */
package judlaw.model.bean.docjud;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
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
	private DocumentoJuridico documentoJuridico;
	
	/**
	 * 
	 * @param texto
	 */
	public Ementa(String texto) {
		this.texto = texto;
		this.documentoJuridico = new DocumentoJuridico();
	}
	
	/**
	 * Construtor Vazio
	 */
	public Ementa() {
		this.documentoJuridico = new DocumentoJuridico();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public DocumentoJuridico getDocumentoJuridico() {
		return documentoJuridico;
	}

	public void setDocumentoJuridico(DocumentoJuridico documentoJuridico) {
		this.documentoJuridico = documentoJuridico;
	}
}
