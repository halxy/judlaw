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
 * Classe Encerramento
 * @author Halley Freitas
 *
 */
@Entity
@Table(name = "encerramento")
@SequenceGenerator(name = "encerramento_seq", sequenceName = "encerramento_seq", initialValue = 1, allocationSize = 1)
public class Encerramento {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="encerramento_seq")
	@Column(name="encerramento_id")
	private Integer id;
	
	private String decisao; // no STJ esta como certidao
	private String orgaoJulgador; // Pleno, Primeira Turma, etc.
	
	//Documento Juridico
	@OneToOne(mappedBy = "encerramento")
	private DocumentoJuridico documentoJuridico;
	
	/**
	 * 
	 * @param decisao
	 * @param local
	 */
	public Encerramento(String decisao) {
		this.decisao = decisao;
	}
	
	/**
	 * Construtor Vazio
	 */
	public Encerramento(){}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDecisao() {
		return decisao;
	}
	
	public void setDecisao(String decisao) {
		this.decisao = decisao;
	}

	public String getOrgaoJulgador() {
		return orgaoJulgador;
	}

	public void setOrgaoJulgador(String orgaoJulgador) {
		this.orgaoJulgador = orgaoJulgador;
	}

	public DocumentoJuridico getDocumentoJuridico() {
		return documentoJuridico;
	}

	public void setDocumentoJuridico(DocumentoJuridico documentoJuridico) {
		this.documentoJuridico = documentoJuridico;
	}
}
