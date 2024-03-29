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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Classe Relatorio. Representa o relatorio de um documento juridico
 * @author Halley Freitas
 *
 */
@Entity
@Table(name = "relatorio")
@SequenceGenerator(name = "relatorio_seq", sequenceName = "relatorio_seq", initialValue = 1, allocationSize = 1)
public class Relatorio {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="relatorio_seq")
	@Column(name="relatorio_id")
	private Integer id;
	
	private String texto;
	
	//Documento Juridico
	@OneToOne(mappedBy = "relatorio")
	private DocumentoJuridico documentoJuridico;

	/**
	 * 
	 * @param texto
	 */
	public Relatorio(String texto) {
		this.texto = texto;
	}
	
	/**
	 * Construtor Vazio
	 */
	public Relatorio(){}

	/* -------------------------------------------------
	 * --------------- GETTERS e SETTERS ---------------
	 * -------------------------------------------------
	 */
	
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
