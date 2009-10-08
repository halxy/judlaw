/* 
 * Universidade Federal de Campina Grande

 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Informatica
 * Sistema JudLaw
 * Mestrando: Halley Freitas
 * 		
 */
package judlaw.model.docjud;

import java.util.ArrayList;
import java.util.List;

public class Cabecalho {

	private String idTribunal; // STF, STJ, TJRS, etc.
	private String tipoDocumento; // HC, ACO, AI, etc.
	private String idDocumento;
	private String numRegistro; // ver Certidao de Julgamento STJ
	private String orgaoJulgador; // Pleno, Primeira Turma, etc.
	private List<Parte> partes;
	
	public Cabecalho(String idTribunal, String tipoDocumento,
			String idDocumento, String numRegistro, String orgaoJulgador,
			List<Parte> partes) {
		super();
		this.idTribunal = idTribunal;
		this.tipoDocumento = tipoDocumento;
		this.idDocumento = idDocumento;
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

	public String getIdTribunal() {
		return idTribunal;
	}

	public void setIdTribunal(String idTribunal) {
		this.idTribunal = idTribunal;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
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
