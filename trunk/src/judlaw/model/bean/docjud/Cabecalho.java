/* 
 * Universidade Federal de Campina Grande

 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Informatica
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
	private String tipoDocumento; // HC, ACO, AI, etc.
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

	public Cabecalho(String tribunal, String tipoDocumento, String codRegistro, String orgaoJulgador) {
		this.tribunal = tribunal;
		this.tipoDocumento = tipoDocumento;
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

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
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
