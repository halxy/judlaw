/* 
 * Universidade Federal de Campina Grande


 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas
 * 		
 */
package judlaw.model.bean.docjud;

/**
 * 
 * @author Halley Freitas
 *
 */
public class Cabecalho {

	//id (chave estrangeira - DocumentoJuridico)
	private Integer id;
	
	private String tribunal; // STF, STJ, TJRS, etc.
	private String codRegistro; // ver Certidao de Julgamento STJ
	private String orgaoJulgador; // Pleno, Primeira Turma, etc.
	
	//Documento Juridico
	private DocumentoJuridico documentojuridico;

	public DocumentoJuridico getDocumentojuridico() {
		return documentojuridico;
	}

	public void setDocumentojuridico(DocumentoJuridico documentojuridico) {
		this.documentojuridico = documentojuridico;
	}

	public Cabecalho(String tribunal, String codRegistro, String orgaoJulgador) {
		this.tribunal = tribunal;
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
}
