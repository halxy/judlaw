/* 
 * Universidade Federal de Campina Grande


 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas
 * 		
 */
package judlaw.model.bean.docjud;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import judlaw.model.bean.Documento;

/**
 * 
 * @author halley
 *
 */
@Entity
@Table(name = "documentojuridico")
@SequenceGenerator(name = "documentojuridico_seq", sequenceName = "documentojuridico_seq", initialValue = 1, allocationSize = 1)
public class DocumentoJuridico extends Documento {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="documentojuridico_seq")
	@Column(name="documentojuridico_id")
	private Integer id;
	
	private Cabecalho cabecalho;
	private Ementa ementa;
	private Relatorio relatorio;
	private List<Voto> votos;
	private Encerramento encerramento;
	private List<Parte> partes; // relator, partes, etc.
	
	/**
	 * Construtor vazio que apenas inicializa as listas
	 */
	public DocumentoJuridico() {
		inicializaListas();
	}
	
	/*
	 * Inicializa as listas da classe
	 */
	private void inicializaListas() {
		this.votos = new ArrayList<Voto>();
		this.partes = new ArrayList<Parte>();
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