/* 
 * Universidade Federal de Campina Grande

 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Informatica
 * Sistema JudLaw
 * Mestrando: Halley Freitas
 * 		
 */
package judlaw.model.docjud;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cabecalho implements Serializable {

	private static final long serialVersionUID = 1L;

	//id (chave estrangeira - DocumentoJuridico)
	private Integer id;
	
	private String tribunal; // STF, STJ, TJRS, etc.
	private String tipoDocumento; // HC, ACO, AI, etc.
	private String numRegistro; // ver Certidao de Julgamento STJ
	private String orgaoJulgador; // Pleno, Primeira Turma, etc.
	private List<Parte> partes;
	
	//Documento Juridico
	private DocumentoJuridico documentojuridico;

	public DocumentoJuridico getDocumentojuridico() {
		return documentojuridico;
	}

	public void setDocumentojuridico(DocumentoJuridico documentojuridico) {
		this.documentojuridico = documentojuridico;
	}

	public Cabecalho(String tribunal, String tipoDocumento, String numRegistro, String orgaoJulgador,
			List<Parte> partes) {
		super();
		this.tribunal = tribunal;
		this.tipoDocumento = tipoDocumento;
		this.numRegistro = numRegistro;
		this.orgaoJulgador = orgaoJulgador;
		this.partes = partes;
	}

	/**
	 * 
	 */
	public Cabecalho() {
		inicializaListas();
	}
	
	/*
	 * Metodo que inicializa as listas da classe.
	 */
	private void inicializaListas(){
		this.partes = new ArrayList<Parte>();
	}

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

	public String getNumRegistro() {
		return numRegistro;
	}

	public void setNumRegistro(String numRegistro) {
		this.numRegistro = numRegistro;
	}
	
	public String getOrgaoJulgador() {
		return orgaoJulgador;
	}

	public void setOrgaoJulgador(String orgaoJulgador) {
		this.orgaoJulgador = orgaoJulgador;
	}
	
	public List<Parte> getPartes() {
		return partes;
	}

	public void setPartes(List<Parte> partes) {
		this.partes = partes;
	}
}
