/* 
 * Universidade Federal de Campina Grande
 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas	
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

import judlaw.model.bean.law.ElementoNorma;
import judlaw.model.bean.law.Norma;

/**
 * Classe Alteracao - representa as alteracoes feitas por normas e elementosNorma
 * @author Halley Freitas
 *
 */
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
	private String resultadoAlteracao; // resultante da alteracao.
	
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
	 * @param resultadoAlteracao
	 */
	public Alteracao(Norma normaOrigem, Norma normaDestino, String data, String tipo,
			String resultadoAlteracao) {
		this.normaOrigem = normaOrigem;
		this.normaDestino = normaDestino;
		this.data = data;
		this.tipo = tipo;
		this.resultadoAlteracao = resultadoAlteracao;
	}
	
	/**
	 * 
	 * @param normaOrigem
	 * @param elementoNormaDestino
	 * @param data
	 * @param tipo
	 * @param resultadoAlteracao
	 */
	public Alteracao(Norma normaOrigem, ElementoNorma elementoNormaDestino, String data, String tipo,
			String resultadoAlteracao) {
		this.normaOrigem = normaOrigem;
		this.elementoNormaDestino = elementoNormaDestino;
		this.data = data;
		this.tipo = tipo;
		this.resultadoAlteracao = resultadoAlteracao;
	}
	
	/**
	 * 
	 * @param elementoNormaOrigem
	 * @param normaDestino
	 * @param data
	 * @param tipo
	 * @param resultadoAlteracao
	 */
	public Alteracao(ElementoNorma elementoNormaOrigem, Norma normaDestino, String data, String tipo,
			String resultadoAlteracao) {
		this.elementoNormaOrigem = elementoNormaOrigem;
		this.normaDestino = normaDestino;
		this.data = data;
		this.tipo = tipo;
		this.resultadoAlteracao = resultadoAlteracao;
	}
	
	/**
	 * 
	 * @param elementoNormaOrigem
	 * @param elementoNormaDestino
	 * @param data
	 * @param tipo
	 * @param resultadoAlteracao
	 */
	public Alteracao(ElementoNorma elementoNormaOrigem, ElementoNorma elementoNormaDestino, String data, String tipo,
			String resultadoAlteracao) {
		this.elementoNormaOrigem = elementoNormaOrigem;
		this.elementoNormaDestino = elementoNormaDestino;
		this.data = data;
		this.tipo = tipo;
		this.resultadoAlteracao = resultadoAlteracao;
	}

	/**
	 * Construtor vazio
	 */
	public Alteracao(){}
	
	/* -------------------------------------------------
	 * --------------- GETTERS e SETTERS ---------------
	 * -------------------------------------------------
	 */
	
	/**
	 * 
	 * @return
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 */
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
	public String getResultadoAlteracao() {
		return resultadoAlteracao;
	}

	/**
	 * 
	 * @param resultadoAlteracao
	 */
	public void setResultadoAlteracao(String resultadoAlteracao) {
		this.resultadoAlteracao = resultadoAlteracao;
	}

	/*
	 * (non-Javadoc)
	 * @see judlaw.model.bean.ref.Referencia#getData()
	 */
	public String getData() {
		return data;
	}

	/*
	 * (non-Javadoc)
	 * @see judlaw.model.bean.ref.Referencia#setData(java.lang.String)
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * 
	 * @return
	 */
	public Norma getNormaOrigem() {
		return normaOrigem;
	}

	/**
	 * 
	 * @param normaOrigem
	 */
	public void setNormaOrigem(Norma normaOrigem) {
		this.normaOrigem = normaOrigem;
	}

	/**
	 * 
	 * @return
	 */
	public ElementoNorma getElementoNormaOrigem() {
		return elementoNormaOrigem;
	}

	/**
	 * 
	 * @param elementoNormaOrigem
	 */
	public void setElementoNormaOrigem(ElementoNorma elementoNormaOrigem) {
		this.elementoNormaOrigem = elementoNormaOrigem;
	}

	/**
	 * 
	 * @return
	 */
	public Norma getNormaDestino() {
		return normaDestino;
	}

	/**
	 * 
	 * @param normaDestino
	 */
	public void setNormaDestino(Norma normaDestino) {
		this.normaDestino = normaDestino;
	}

	/**
	 * 
	 * @return
	 */
	public ElementoNorma getElementoNormaDestino() {
		return elementoNormaDestino;
	}

	/**
	 * 
	 * @param elementoNormaDestino
	 */
	public void setElementoNormaDestino(ElementoNorma elementoNormaDestino) {
		this.elementoNormaDestino = elementoNormaDestino;
	}
}
