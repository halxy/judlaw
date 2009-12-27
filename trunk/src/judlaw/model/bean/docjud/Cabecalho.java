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
 * Classe Cabecalho - parte do DocumentoJuridico
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
	private String orgaoJulgador; // Pleno, Primeira Turma, etc.
	
	//Mapeamento 1-1
	@OneToOne(mappedBy = "cabecalho")
	@JoinColumn(name="documentojuridico_id")
	private DocumentoJuridico documentoJuridico;

	public Cabecalho(String tribunal, String tipo, int numero, String local,
			String codRegistro, String orgaoJulgador, String relator) {
		super();
		this.tribunal = tribunal;
		this.tipo = tipo;
		this.numero = numero;
		this.local = local;
		this.codRegistro = codRegistro;
		this.orgaoJulgador = orgaoJulgador;
	}

	/**
	 * Construtor Vazio
	 */
	public Cabecalho() {}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTribunal() {
		return tribunal;
	}

	public void setTribunal(String tribunal) {
		this.tribunal = tribunal;
	}
	
	public String getCodRegistro() {
		return codRegistro;
	}

	public void setCodRegistro(String codRegistro) {
		this.codRegistro = codRegistro;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}
}
