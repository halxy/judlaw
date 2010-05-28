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
 * Classe ElementoNorma - superclasse que representa os elementos de uma norma:
 * Partes, Livros, Titulos, Capitulos, Secoes, Subsecoes, Artigos, Paragrafos, Incisos, Alineas e Itens.
 * @author Halley Freitas
 *
 */
@Entity
@Table(name = "elementonorma")
@SequenceGenerator(name = "elementonorma_seq", sequenceName = "elementonorma_seq", initialValue = 1, allocationSize = 1)
public class ElementoNorma extends TextoLegal {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="elementonorma_seq")
	@Column(name="elementonorma_id")
	private Integer id;

	private String identificadorUnico; // cp_art120; lei1234; cc_art1_par2.
	private String tipo; // tipo do textoLegal
	private String identificador; // tem que ser String por causa do incisos e alineas.
	
	// Parte Temporal
	private String dataPublicacao; // dd/MM/yyy
	private String validade; // dd/MM/yyyy-dd2/MM2/yyy2
	private String vigencia; // dd/MM/yyyy-dd2/MM2/yyy2
	private String versao; //numVersao#data
	
	// Texto do Elemento da Norma
	private String texto;
	
	/*
	 * O relacionamento é de M-N porque quando o ElementoNorma pai de um ElementoNorma eh modificado
	 * (sofre uma AlteracaoModificacao), entao por ser um BD Temporal, eh criada uma nova 
	 * entrada na tabela de elementosNorma com uma dataPublicacao diferente, sendo assim, o ElementoNorma
	 * tem uma lista de ElementosNormaPai, mas na verdade sao varias versoes de uma mesmo ElementoNorma
	 */
	@ManyToMany(mappedBy="elementosNorma")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<ElementoNorma> elementosNormaPai;
	
	/*
	 * O relacionamento é de M-N porque quando a norma pai de um ElementoNorma eh modificado
	 * (sofre uma AlteracaoModificacao), entao por ser um BD Temporal, eh criada uma nova 
	 * entrada na tabela de normas com uma dataPublicacao diferente, sendo assim, o ElementoNorma
	 * tem uma lista de NormasPai, mas na verdade sao varias versoes de uma mesma Norma
	 */
	@ManyToMany(mappedBy="elementosNorma")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Norma> normasPai;
	
	//ElementoNorma que compoem o elementoNorma
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "ElementosNormaPaiElementoNorma",
			joinColumns = {
				@JoinColumn(name="elementoNormaPai_id", nullable = true)           
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
	@OneToMany(mappedBy="elementoNormaOrigem", cascade = CascadeType.ALL)
    @Column(name="citacoesfeitas_fk")
    @LazyCollection(LazyCollectionOption.FALSE)
	private List<CitacaoTextLeg> citacoesFeitas;
	
	//Recebidas
	@OneToMany(mappedBy="elementoNormaDestino", cascade = CascadeType.ALL)
    @Column(name="citacoesrecebidasdocjud_fk")
    @LazyCollection(LazyCollectionOption.FALSE)
	private List<CitacaoDocJud> citacoesRecebidasDocJud;
	
	@OneToMany(mappedBy="elementoNormaDestino", cascade = CascadeType.ALL)
    @Column(name="citacoesrecebidastextleg_fk")
    @LazyCollection(LazyCollectionOption.FALSE)
	private List<CitacaoTextLeg> citacoesRecebidasTextLeg;
	
	/*
	 * ALTERACOES
	 */
	//Feitas
	@OneToMany(mappedBy="elementoNormaOrigem", cascade = CascadeType.ALL)
    @Column(name="alteracoesfeitas_fk")
    @LazyCollection(LazyCollectionOption.FALSE)
	private List<Alteracao> alteracoesFeitas;
	
	//Recebidas
	@OneToMany(mappedBy="elementoNormaDestino", cascade = CascadeType.ALL)
    @Column(name="alteracoesrecebidas_fk")
    @LazyCollection(LazyCollectionOption.FALSE)
	private List<Alteracao> alteracoesRecebidas;
	
	/**
	 * 
	 * @param texto
	 * @param identificadorUnico
	 * @param tipo
	 * @param dataPublicacao
	 * @param vigencia
	 * @param elementosNorma
	 * @param textoLegalPai
	 */
	public ElementoNorma(String texto, String identificadorUnico, String tipo,
			String dataPublicacao, String vigencia) {
		this.texto = texto;
		this.identificadorUnico = identificadorUnico;
		this.tipo = tipo;
		this.dataPublicacao = dataPublicacao;
		this.vigencia = vigencia;
		this.elementosNorma = new ArrayList<ElementoNorma>();
		this.elementosNormaPai = new ArrayList<ElementoNorma>();
		this.normasPai = new ArrayList<Norma>();
		//Referencias
		this.citacoesFeitas = new ArrayList<CitacaoTextLeg>();
		this.citacoesRecebidasDocJud = new ArrayList<CitacaoDocJud>();
		this.citacoesRecebidasTextLeg = new ArrayList<CitacaoTextLeg>();
		this.alteracoesFeitas = new ArrayList<Alteracao>();
		this.alteracoesRecebidas = new ArrayList<Alteracao>();
	}
	
	/**
	 * 
	 * @param texto
	 * @param identificadorUnico
	 * @param tipo
	 * @param dataPublicacao
	 * @param vigencia
	 * @param elementosNorma
	 */
	public ElementoNorma(String texto, String identificadorUnico, String tipo,
			String dataPublicacao, String vigencia, List<ElementoNorma> elementosNorma) {
		this.texto = texto;
		this.identificadorUnico = identificadorUnico;
		this.tipo = tipo;
		this.dataPublicacao = dataPublicacao;
		this.vigencia = vigencia;
		this.elementosNorma = elementosNorma;
	}

	/**
	 * Construtor sem parâmetros
	 */
	public ElementoNorma() {
		this.elementosNorma = new ArrayList<ElementoNorma>();
		//Referencias
		this.citacoesFeitas = new ArrayList<CitacaoTextLeg>();
		this.citacoesRecebidasDocJud = new ArrayList<CitacaoDocJud>();
		this.citacoesRecebidasTextLeg = new ArrayList<CitacaoTextLeg>();
		this.alteracoesFeitas = new ArrayList<Alteracao>();
		this.alteracoesRecebidas = new ArrayList<Alteracao>();
		this.elementosNormaPai = new ArrayList<ElementoNorma>();
		this.normasPai = new ArrayList<Norma>();
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
	public String getTexto() {
		return texto;
	}

	/**
	 * 
	 * @param texto
	 */
	public void setTexto(String texto) {
		this.texto = texto;
	}

	/**
	 * 
	 */
	public String getIdentificadorUnico() {
		return identificadorUnico;
	}

	/**
	 * 
	 */
	public void setIdentificadorUnico(String identificadorUnico) {
		this.identificadorUnico = identificadorUnico;
	}

	/**
	 * 
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * 
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * 
	 * @return
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * 
	 * @param numero
	 */
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	/**
	 * 
	 */
	public String getDataPublicacao() {
		return dataPublicacao;
	}

	/**
	 * 
	 */
	public void setDataPublicacao(String dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}

	/**
	 * 
	 */
	public String getVigencia() {
		return vigencia;
	}

	/**
	 * 
	 */
	public void setVigencia(String vigencia) {
		this.vigencia = vigencia;
	}

	/**
	 * 
	 * @return
	 */
	public List<ElementoNorma> getElementosNormaPai() {
		return elementosNormaPai;
	}

	/**
	 * 
	 * @param elementosNormaPai
	 */
	public void setElementosNormaPai(List<ElementoNorma> elementosNormaPai) {
		this.elementosNormaPai = elementosNormaPai;
	}

	/**
	 * 
	 * @return
	 */
	public List<Norma> getNormasPai() {
		return normasPai;
	}

	/**
	 * 
	 * @param normasPai
	 */
	public void setNormasPai(List<Norma> normasPai) {
		this.normasPai = normasPai;
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
	 */
	public List<CitacaoTextLeg> getCitacoesFeitas() {
		return citacoesFeitas;
	}

	/**
	 * 
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

	/**
	 * 
	 */
	public List<Alteracao> getAlteracoesFeitas() {
		return alteracoesFeitas;
	}

	/**
	 * 
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
