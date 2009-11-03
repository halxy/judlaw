/* 
 * Universidade Federal de Campina Grande

 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Informatica
 * Sistema JudLaw
 * Mestrando: Halley Freitas
 * 		
 */
package judlaw.model.docjud;

import java.util.List;

/**
 * 
 * @author halley
 *
 */
public class DocumentoJuridico {
	
	//id
	private Integer id;
	
	private String identificadorUnico;
	private Cabecalho cabecalho;
	private Ementa ementa;
	private Relatorio relatorio;
	private List<Voto> votos;
	private Encerramento encerramento;
	private List<Parte> partes; //inclui o relator, partes, etc.
	
	
	public String getIdentificadorUnico() {
		return identificadorUnico;
	}

	public void setIdentificadorUnico(String identificadorUnico) {
		this.identificadorUnico = identificadorUnico;
	}

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
}
