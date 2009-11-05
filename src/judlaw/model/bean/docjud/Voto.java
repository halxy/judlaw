/* 
 * Universidade Federal de Campina Grande


 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas
 * 		
 */
package judlaw.model.bean.docjud;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * 
 * @author halley
 *
 */
@Entity
@Table(name = "voto")
@SequenceGenerator(name = "voto_seq", sequenceName = "voto_seq", initialValue = 1, allocationSize = 1)
public class Voto {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="voto_seq")
	@Column(name="voto_id")
	private Integer id;
	
	private String texto;
	
	//Documento Juridico
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="documentojuridico_id")
	private DocumentoJuridico documentoJuridico;
	
	/**
	 * 
	 * @param texto
	 */
	public Voto(String texto) {
		this.texto = texto;
	}
	
	/**
	 * Construtor vazio
	 */
	public Voto(){}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public DocumentoJuridico getDocumentoJuridico() {
		return documentoJuridico;
	}

	public void setDocumentoJuridico(DocumentoJuridico documentoJuridico) {
		this.documentoJuridico = documentoJuridico;
	}
}
