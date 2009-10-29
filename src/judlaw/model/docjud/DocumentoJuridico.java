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
import java.util.List;

import judlaw.model.manager.DocJudManager;

/**
 * 
 * @author halley
 *
 */
public class DocumentoJuridico implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//id
	private Integer id;
	
	private Cabecalho cabecalho;
	private Ementa ementa;
	private Relatorio relatorio;
	private List<Voto> votos;
	private Encerramento encerramento;
	private List<Parte> partes; //inclui o relator, partes, etc.
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Cabecalho getCabecalho() {
		return cabecalho;
	}
	
	public void setCabecalho(Cabecalho cabecalho) {
		this.cabecalho = cabecalho;
	}
	
	public Ementa getEmenta() {
		return ementa;
	}
	
	public void setEmenta(Ementa ementa) {
		this.ementa = ementa;
	}
	
	public Relatorio getRelatorio() {
		return relatorio;
	}
	
	public void setRelatorio(Relatorio relatorio) {
		this.relatorio = relatorio;
	}
	
	public List<Voto> getVotos() {
		return votos;
	}
	
	public void setVotos(List<Voto> votos) {
		this.votos = votos;
	}
	
	public Encerramento getEncerramento() {
		return encerramento;
	}
	
	public void setEncerramento(Encerramento encerramento) {
		this.encerramento = encerramento;
	}
	
	public List<Parte> getPartes() {
		return partes;
	}
	
	public void setPartes(List<Parte> partes) {
		this.partes = partes;
	}
	
	public static void main(String[] args) {
		DocumentoJuridico docjud = new DocumentoJuridico();
		Cabecalho cabecalho = new Cabecalho();
		cabecalho.setIdDocumento("12");
		cabecalho.setIdTribunal("4234234");
		cabecalho.setDocumentojuridico(docjud);
		docjud.setCabecalho(cabecalho);
		
		DocJudManager.getInstance().saveDocumentoJuridico(docjud);
		
	}
}
