/* 
 * Universidade Federal de Campina Grande


 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas
 * 		
 */
package judlaw.model.bean.ref;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import judlaw.model.bean.lei.ElementoNorma;
import judlaw.model.bean.lei.Norma;

@Entity
@Table(name = "alteracao")
@SequenceGenerator(name = "alteracao_seq", sequenceName = "alteracao_seq", initialValue = 1, allocationSize = 1)
public class Alteracao extends Referencia {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="alteracao_seq")
	@Column(name="alteracao_id")
	private Integer id;
	
	// Atributos
	private String data; // data em que foi feita a referência
	private String tipo; // inclusao, revogacao, alteracao;
	private String caracteristica; // mais branda, mais punitiva, etc.
	
	/* --------- Referencias --------- */
	/*
	 * ORIGENS
	 */
	//Norma
	@ManyToOne
	@JoinColumn(name="origemnorma_id", nullable = true)
	private Norma normaOrigem;
	
	//Norma
	@ManyToOne
	@JoinColumn(name="origemelementonorma_id", nullable = true)
	private ElementoNorma elementoNormaOrigem;
	
	/*
	 * DESTINOS
	 */
	//Quando o destino da alteracao eh uma norma
	@ManyToOne
	@JoinColumn(name="destinonorma_id", nullable = true)
	private Norma normaDestino;
	
	//Quando o destino da alteracao eh um elementoNorma
	@ManyToOne
	@JoinColumn(name="destinoelementonorma_id", nullable = true)
	private ElementoNorma elementoNormaDestino;
	
	/**
	 * 
	 * @param normaOrigem
	 * @param normaDestino
	 * @param data
	 * @param tipo
	 * @param caracteristica
	 */
	public Alteracao(Norma normaOrigem, Norma normaDestino, String data, String tipo,
			String caracteristica) {
		this.normaOrigem = normaOrigem;
		this.normaDestino = normaDestino;
		this.data = data;
		this.tipo = tipo;
		this.caracteristica = caracteristica;
	}
	
	/**
	 * 
	 * @param normaOrigem
	 * @param elementoNormaDestino
	 * @param data
	 * @param tipo
	 * @param caracteristica
	 */
	public Alteracao(Norma normaOrigem, ElementoNorma elementoNormaDestino, String data, String tipo,
			String caracteristica) {
		this.normaOrigem = normaOrigem;
		this.elementoNormaDestino = elementoNormaDestino;
		this.data = data;
		this.tipo = tipo;
		this.caracteristica = caracteristica;
	}
	
	/**
	 * 
	 * @param elementoNormaOrigem
	 * @param normaDestino
	 * @param data
	 * @param tipo
	 * @param caracteristica
	 */
	public Alteracao(ElementoNorma elementoNormaOrigem, Norma normaDestino, String data, String tipo,
			String caracteristica) {
		this.elementoNormaOrigem = elementoNormaOrigem;
		this.normaDestino = normaDestino;
		this.data = data;
		this.tipo = tipo;
		this.caracteristica = caracteristica;
	}
	
	/**
	 * 
	 * @param elementoNormaOrigem
	 * @param elementoNormaDestino
	 * @param data
	 * @param tipo
	 * @param caracteristica
	 */
	public Alteracao(ElementoNorma elementoNormaOrigem, ElementoNorma elementoNormaDestino, String data, String tipo,
			String caracteristica) {
		this.elementoNormaOrigem = elementoNormaOrigem;
		this.elementoNormaDestino = elementoNormaDestino;
		this.data = data;
		this.tipo = tipo;
		this.caracteristica = caracteristica;
	}

	/**
	 * Construtor vazio
	 */
	public Alteracao(){}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 
	 * @return
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * 
	 * @param tipo
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * 
	 * @return
	 */
	public String getCaracteristica() {
		return caracteristica;
	}

	/**
	 * 
	 * @param caracteristica
	 */
	public void setCaracteristica(String caracteristica) {
		this.caracteristica = caracteristica;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Norma getNormaOrigem() {
		return normaOrigem;
	}

	public void setNormaOrigem(Norma normaOrigem) {
		this.normaOrigem = normaOrigem;
	}

	public ElementoNorma getElementoNormaOrigem() {
		return elementoNormaOrigem;
	}

	public void setElementoNormaOrigem(ElementoNorma elementoNormaOrigem) {
		this.elementoNormaOrigem = elementoNormaOrigem;
	}

	public Norma getNormaDestino() {
		return normaDestino;
	}

	public void setNormaDestino(Norma normaDestino) {
		this.normaDestino = normaDestino;
	}

	public ElementoNorma getElementoNormaDestino() {
		return elementoNormaDestino;
	}

	public void setElementoNormaDestino(ElementoNorma elementoNormaDestino) {
		this.elementoNormaDestino = elementoNormaDestino;
	}
}
