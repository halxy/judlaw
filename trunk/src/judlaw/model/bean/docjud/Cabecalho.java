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
 * Classe Cabecalho. Parte inicial de um DocumentoJuridico
 * @author Halley Freitas
 *
 */
@Entity
@Table(name = "cabecalho")
@SequenceGenerator(name = "cabecalho_seq", sequenceName = "cabecalho_seq", initialValue = 1, allocationSize = 1)
public class Cabecalho {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="cabecalho_seq")
	@Column(name="cabecalho_id")
	private Integer id;
	
	//Atributos
	private String tribunal; // STF, STJ, TJRS, etc.
	private String tipo; // tipos de Documentos Juridicos
	private int numero;
	private String local;
	private String codRegistro; // ver Certidao de Julgamento STJ
	
	//Mapeamento 1-1
	@OneToOne(mappedBy = "cabecalho")
	@JoinColumn(name="documentojuridico_id")
	private DocumentoJuridico documentoJuridico;

	/**
	 * Construtor default
	 * @param tribunal
	 * @param tipo
	 * @param numero
	 * @param local
	 * @param codRegistro
	 * @param relator
	 */
	public Cabecalho(String tribunal, String tipo, int numero, String local,
			String codRegistro, String relator) {
		this.tribunal = tribunal;
		this.tipo = tipo;
		this.numero = numero;
		this.local = local;
		this.codRegistro = codRegistro;
	}

	/**
	 * Construtor Vazio
	 */
	public Cabecalho() {}
	
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
	public String getTribunal() {
		return tribunal;
	}

	/**
	 * 
	 * @param tribunal
	 */
	public void setTribunal(String tribunal) {
		this.tribunal = tribunal;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getCodRegistro() {
		return codRegistro;
	}

	/**
	 * 
	 * @param codRegistro
	 */
	public void setCodRegistro(String codRegistro) {
		this.codRegistro = codRegistro;
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
	public String getTipo() {
		return tipo;
	}

	/**
	 * 
	 * @param tipo
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * 
	 * @return
	 */
	public int getNumero() {
		return numero;
	}

	/**
	 * 
	 * @param numero
	 */
	public void setNumero(int numero) {
		this.numero = numero;
	}

	/**
	 * 
	 * @return
	 */
	public String getLocal() {
		return local;
	}

	/**
	 * 
	 * @param local
	 */
	public void setLocal(String local) {
		this.local = local;
	}
}
