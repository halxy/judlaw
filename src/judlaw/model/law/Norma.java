/* 
 * Universidade Federal de Campina Grande

 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Informatica
 * Sistema JudLaw
 * Mestrando: Halley Freitas
 * 		
 */
package judlaw.model.law;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import judlaw.model.reference.Referencia;

/**
 * Classe norma
 * @author Halley Freitas
 *
 */
@Entity
@SequenceGenerator(name="SEQ_NORMA", sequenceName="norma_sequence")
public class Norma implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Id
	private Long id;
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
	private Date data; //dd/MM/aa
	private String vigencia; // dd/MM/aa-dd2/MM2/aa2
	
	// ReferÍncias
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

	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_NORMA")
	@Column(name="norma_id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	@Temporal(TemporalType.DATE)
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
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
