/* 
 * Universidade Federal de Campina Grande
 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas	
 */
package judlaw.model.bean.law;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import judlaw.model.bean.ref.Alteracao;
import judlaw.model.bean.ref.CitacaoDocJud;
import judlaw.model.bean.ref.CitacaoTextLeg;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * Classe Norma
 * @author Halley Freitas
 *
 */
@Entity
@Table(name = "norma")
@SequenceGenerator(name = "norma_seq", sequenceName = "norma_seq", initialValue = 1, allocationSize = 1)
public class Norma extends TextoLegal {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="norma_seq")
	@Column(name="norma_id")
	private Integer id;

	/* --------- Atributos --------- */
	private String identificadorUnico; // cp_art120; lei1234; cc_art1_par2.
	private String esfera; //Federal, Municipal, Estadual.
	private String apelido;
	private String versao; //numVersao#data
	
	//Epigrafe
	private String tipo;
	private int numero;
	private String dataPublicacao; // dd/MM/yyy
	
	// Parte Preliminar
	private String ementa;
	private String autoria;
	
	// Parte Final
	private String local;
	
	// Parte Temporal
	private String validade; // dd/MM/yyyy-dd2/MM2/yyy2
	private String vigencia; // dd/MM/yyyy-dd2/MM2/yyy2

	
	//Norma é o Mapping Owner.
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "normasPaiElementoNorma",
			joinColumns = {
				@JoinColumn(name="normaPai_id", nullable = true)           
    		},
    		inverseJoinColumns = {
				@JoinColumn(name="elementoNorma_id", nullable = true)
    		}
	)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<ElementoNorma> elementosNorma;
	
	/* --------- Referencias --------- */
	/*
	 * CITACOES
	 */
	//Feitas
	@OneToMany(mappedBy="normaOrigem", cascade = CascadeType.ALL)
    @Column(name="citacoesfeitas_fk")
    @LazyCollection(LazyCollectionOption.FALSE)
	private List<CitacaoTextLeg> citacoesFeitas;
	
	//Recebidas
	@OneToMany(mappedBy="normaDestino", cascade = CascadeType.ALL)
    @Column(name="citacoesrecebidasdocjud_fk")
    @LazyCollection(LazyCollectionOption.FALSE)
	private List<CitacaoDocJud> citacoesRecebidasDocJud;
	
	@OneToMany(mappedBy="normaDestino", cascade = CascadeType.ALL)
    @Column(name="citacoesrecebidastextleg_fk")
    @LazyCollection(LazyCollectionOption.FALSE)
	private List<CitacaoTextLeg> citacoesRecebidasTextLeg;
	
	/*
	 * ALTERACOES
	 */
	//Feitas
	@OneToMany(mappedBy="normaOrigem", cascade = CascadeType.ALL)
    @Column(name="alteracoesfeitas_fk")
    @LazyCollection(LazyCollectionOption.FALSE)
	private List<Alteracao> alteracoesFeitas;
	
	//Recebidas
	@OneToMany(mappedBy="normaDestino", cascade = CascadeType.ALL)
    @Column(name="alteracoesrecebidas_fk")
    @LazyCollection(LazyCollectionOption.FALSE)
	private List<Alteracao> alteracoesRecebidas;
	
	public Norma(String ementa, String autoria, String local,
			List<ElementoNorma> elementosNorma, String identificadorUnico,
			String tipo, String dataPublicacao, String vigencia) {
		this.ementa = ementa;
		this.autoria = autoria;
		this.local = local;
		this.elementosNorma = elementosNorma;
		this.identificadorUnico = identificadorUnico;
		this.tipo = tipo;
		this.dataPublicacao = dataPublicacao;
		this.vigencia = vigencia;
	}

	public Norma(String ementa, String autoria, String local,
			String identificadorUnico, String tipo, String dataPublicacao,
			String vigencia) {
		this.ementa = ementa;
		this.autoria = autoria;
		this.local = local;
		this.identificadorUnico = identificadorUnico;
		this.tipo = tipo;
		this.dataPublicacao = dataPublicacao;
		this.vigencia = vigencia;
		this.elementosNorma = new ArrayList<ElementoNorma>();
		//Referencias
		this.citacoesFeitas = new ArrayList<CitacaoTextLeg>();
		this.citacoesRecebidasDocJud = new ArrayList<CitacaoDocJud>();
		this.citacoesRecebidasTextLeg = new ArrayList<CitacaoTextLeg>();
		this.alteracoesFeitas = new ArrayList<Alteracao>();
		this.alteracoesRecebidas = new ArrayList<Alteracao>();
	}


	/**
	 * Construtor vazio
	 */
	public Norma() {
		this.elementosNorma = new ArrayList<ElementoNorma>();
		//Referencias
		this.citacoesFeitas = new ArrayList<CitacaoTextLeg>();
		this.citacoesRecebidasDocJud = new ArrayList<CitacaoDocJud>();
		this.citacoesRecebidasTextLeg = new ArrayList<CitacaoTextLeg>();
		this.alteracoesFeitas = new ArrayList<Alteracao>();
		this.alteracoesRecebidas = new ArrayList<Alteracao>();
	}

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
	public String getEmenta() {
		return ementa;
	}

	/**
	 * 
	 * @param ementa
	 */
	public void setEmenta(String ementa) {
		this.ementa = ementa;
	}

	/**
	 * 
	 * @return
	 */
	public String getAutoria() {
		return autoria;
	}

	/**
	 * 
	 * @param autoria
	 */
	public void setAutoria(String autoria) {
		this.autoria = autoria;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<ElementoNorma> getElementosNorma() {
		return elementosNorma;
	}

	/**
	 * 
	 * @param elementosNorma
	 */
	public void setElementosNorma(List<ElementoNorma> elementosNorma) {
		this.elementosNorma = elementosNorma;
	}

	/**
	 * 
	 * @return
	 */
	public String getLocal() {
		return local;
	}

	/**
	 * 
	 * @param local
	 */
	public void setLocal(String local) {
		this.local = local;
	}

	/*
	 * (non-Javadoc)
	 * @see judlaw.model.bean.law.TextoLegal#getIdentificadorUnico()
	 */
	public String getIdentificadorUnico() {
		return identificadorUnico;
	}

	/*
	 * (non-Javadoc)
	 * @see judlaw.model.bean.law.TextoLegal#setIdentificadorUnico(java.lang.String)
	 */
	public void setIdentificadorUnico(String identificadorUnico) {
		this.identificadorUnico = identificadorUnico;
	}

	/*
	 * (non-Javadoc)
	 * @see judlaw.model.bean.law.TextoLegal#getTipo()
	 */
	public String getTipo() {
		return tipo;
	}

	/*
	 * (non-Javadoc)
	 * @see judlaw.model.bean.law.TextoLegal#setTipo(java.lang.String)
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/*
	 * (non-Javadoc)
	 * @see judlaw.model.bean.law.TextoLegal#getDataPublicacao()
	 */
	public String getDataPublicacao() {
		return dataPublicacao;
	}

	/*
	 * (non-Javadoc)
	 * @see judlaw.model.bean.law.TextoLegal#setDataPublicacao(java.lang.String)
	 */
	public void setDataPublicacao(String dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}

	/*
	 * (non-Javadoc)
	 * @see judlaw.model.bean.law.TextoLegal#getVigencia()
	 */
	public String getVigencia() {
		return vigencia;
	}

	/*
	 * (non-Javadoc)
	 * @see judlaw.model.bean.law.TextoLegal#setVigencia(java.lang.String)
	 */
	public void setVigencia(String vigencia) {
		this.vigencia = vigencia;
	}

	/*
	 * (non-Javadoc)
	 * @see judlaw.model.bean.law.TextoLegal#getCitacoesFeitas()
	 */
	public List<CitacaoTextLeg> getCitacoesFeitas() {
		return citacoesFeitas;
	}

	/*
	 * (non-Javadoc)
	 * @see judlaw.model.bean.law.TextoLegal#setCitacoesFeitas(java.util.List)
	 */
	public void setCitacoesFeitas(List<CitacaoTextLeg> citacoesFeitas) {
		this.citacoesFeitas = citacoesFeitas;
	}

	/**
	 * 
	 * @return
	 */
	public List<CitacaoDocJud> getCitacoesRecebidasDocJud() {
		return citacoesRecebidasDocJud;
	}

	/**
	 * 
	 * @param citacoesRecebidasDocJud
	 */
	public void setCitacoesRecebidasDocJud(
			List<CitacaoDocJud> citacoesRecebidasDocJud) {
		this.citacoesRecebidasDocJud = citacoesRecebidasDocJud;
	}

	/**
	 * 
	 * @return
	 */
	public List<CitacaoTextLeg> getCitacoesRecebidasTextLeg() {
		return citacoesRecebidasTextLeg;
	}

	/**
	 * 
	 * @param citacoesRecebidasTextLeg
	 */
	public void setCitacoesRecebidasTextLeg(
			List<CitacaoTextLeg> citacoesRecebidasTextLeg) {
		this.citacoesRecebidasTextLeg = citacoesRecebidasTextLeg;
	}

	/*
	 * (non-Javadoc)
	 * @see judlaw.model.bean.law.TextoLegal#getAlteracoesFeitas()
	 */
	public List<Alteracao> getAlteracoesFeitas() {
		return alteracoesFeitas;
	}

	/*
	 * (non-Javadoc)
	 * @see judlaw.model.bean.law.TextoLegal#setAlteracoesFeitas(java.util.List)
	 */
	public void setAlteracoesFeitas(List<Alteracao> alteracoesFeitas) {
		this.alteracoesFeitas = alteracoesFeitas;
	}

	/**
	 * 
	 * @return
	 */
	public List<Alteracao> getAlteracoesRecebidas() {
		return alteracoesRecebidas;
	}

	/**
	 * 
	 * @param alteracoesRecebidas
	 */
	public void setAlteracoesRecebidas(List<Alteracao> alteracoesRecebidas) {
		this.alteracoesRecebidas = alteracoesRecebidas;
	}

	/**
	 * 
	 * @return
	 */
	public int getNumero() {
		return numero;
	}

	/**
	 * 
	 * @param numero
	 */
	public void setNumero(int numero) {
		this.numero = numero;
	}

	/*
	 * @see judlaw.model.bean.law.TextoLegal#getValidade()
	 */
	public String getValidade() {
		return validade;
	}

	/*
	 * @see judlaw.model.bean.law.TextoLegal#setValidade(java.lang.String)
	 */
	public void setValidade(String validade) {
		this.validade = validade;
	}

	public String getEsfera() {
		return esfera;
	}

	public void setEsfera(String esfera) {
		this.esfera = esfera;
	}

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	/*
	 * (non-Javadoc)
	 * @see judlaw.model.bean.law.TextoLegal#getVersao()
	 */
	public String getVersao() {
		return versao;
	}

	/*
	 * (non-Javadoc)
	 * @see judlaw.model.bean.law.TextoLegal#setVersao(java.lang.String)
	 */
	public void setVersao(String versao) {
		this.versao = versao;
	}
	
}
