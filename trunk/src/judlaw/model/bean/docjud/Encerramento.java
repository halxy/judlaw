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
 * Classe Encerramento. Representa o encerramento de um documento juridico.
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
	
	private String acordao; // nao deixa de ser uma decisao tambem
	private String decisao; // no STJ esta na certidao, no STF em extrato da ata
	private String orgaoJulgador; // Pleno, Primeira Turma, etc.
	
	//Documento Juridico
	@OneToOne(mappedBy = "encerramento")
	private DocumentoJuridico documentoJuridico;
	
	/**
	 * 
	 * @param acordao
	 * @param decisao
	 * @param orgaoJulgador
	 */
	public Encerramento(String acordao, String decisao, String orgaoJulgador) {
		this.acordao = acordao;
		this.decisao = decisao;
		this.orgaoJulgador = orgaoJulgador;
	}

	/**
	 * Construtor Vazio
	 */
	public Encerramento(){}

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
	public String getDecisao() {
		return decisao;
	}
	
	/**
	 * 
	 * @param decisao
	 */
	public void setDecisao(String decisao) {
		this.decisao = decisao;
	}

	/**
	 * 
	 * @return
	 */
	public String getOrgaoJulgador() {
		return orgaoJulgador;
	}

	/**
	 * 
	 * @param orgaoJulgador
	 */
	public void setOrgaoJulgador(String orgaoJulgador) {
		this.orgaoJulgador = orgaoJulgador;
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

	/**
	 * 
	 * @return
	 */
	public String getAcordao() {
		return acordao;
	}

	/**
	 * 
	 * @param acordao
	 */
	public void setAcordao(String acordao) {
		this.acordao = acordao;
	}
}
