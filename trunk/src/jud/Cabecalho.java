package jud;

import java.util.ArrayList;
import java.util.List;

public class Cabecalho {

	private String idTribunal; // STF, STJ, TJRS, etc.
	private String tipoDocumento; // HC, ACO, AI, etc.
	private String idDocumento;
	private String numRegistro; // ver Certidao de Julgamento STJ
	private String local;
	private List<Parte> partes;
	
	/**
	 * 
	 * @param idTribunal
	 * @param tipoDocumento
	 * @param idDocumento
	 * @param numRegistro
	 * @param local
	 * @param partes
	 */
	public Cabecalho(String idTribunal, String tipoDocumento,
			String idDocumento, String numRegistro, String local,
			List<Parte> partes) {
		this.idTribunal = idTribunal;
		this.tipoDocumento = tipoDocumento;
		this.idDocumento = idDocumento;
		this.numRegistro = numRegistro;
		this.local = local;
		inicializaListas();
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

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public List<Parte> getPartes() {
		return partes;
	}

	public void setPartes(List<Parte> partes) {
		this.partes = partes;
	}
}
