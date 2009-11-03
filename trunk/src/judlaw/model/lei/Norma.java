/* 
 * Universidade Federal de Campina Grande

 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Informatica
 * Sistema JudLaw
 * Mestrando: Halley Freitas
 * 		
 */
package judlaw.model.lei;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import judlaw.model.ref.Referencia;

/**
 * Classe norma
 * @author Halley Freitas
 *
 */
public class Norma implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String identificadorUnico; // ex: cp
	
	// Parte Preliminar
	private String epigrafe;
	private String ementa;
	private String autoria;
	private String tipo;
	
	// Parte Normativa
	private List<ElementoNorma> elementosNorma;
	
	// Parte Final
	private String local;
	private String data; //dd/MM/aa
	private String vigencia; // dd/MM/aa-dd2/MM2/aa2
	
	// Referências
	private List<Referencia> referenciasFeitas;
	private List<Referencia> referenciasRecebidas;
	
	/**
	 * 
	 */
	public Norma() {
		inicializaListas();	
	}
	
	private void inicializaListas(){
		this.referenciasFeitas = new ArrayList<Referencia>();
		this.referenciasRecebidas = new ArrayList<Referencia>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getEpigrafe() {
		return epigrafe;
	}

	public void setEpigrafe(String epigrafe) {
		this.epigrafe = epigrafe;
	}

	public String getEmenta() {
		return ementa;
	}

	public void setEmenta(String ementa) {
		this.ementa = ementa;
	}

	public String getAutoria() {
		return autoria;
	}

	public void setAutoria(String autoria) {
		this.autoria = autoria;
	}
	
	public List<ElementoNorma> getElementosNorma() {
		return elementosNorma;
	}

	public void setElementosNorma(List<ElementoNorma> elementosNorma) {
		this.elementosNorma = elementosNorma;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getVigencia() {
		return vigencia;
	}

	public void setVigencia(String vigencia) {
		this.vigencia = vigencia;
	}

	public List<Referencia> getReferenciasFeitas() {
		return referenciasFeitas;
	}

	public void setReferenciasFeitas(List<Referencia> referenciasFeitas) {
		this.referenciasFeitas = referenciasFeitas;
	}

	public List<Referencia> getReferenciasRecebidas() {
		return referenciasRecebidas;
	}

	public void setReferenciasRecebidas(List<Referencia> referenciasRecebidas) {
		this.referenciasRecebidas = referenciasRecebidas;
	}

	public String getIdentificadorUnico() {
		return identificadorUnico;
	}

	public void setIdentificadorUnico(String identificadorUnico) {
		this.identificadorUnico = identificadorUnico;
	}
}
