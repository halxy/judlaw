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
	
	// URL
	private String url;
	
	// Parte Preliminar
	private String epigrafe;
	private String ementa;
	private String autoria;
	
	// Parte Final
	private String local;
	
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

	/* --------- Atributos --------- */
	private String identificadorUnico; // cp_art120; lei1234; cc_art1_par2.
	private String tipo; // tipo do textoLegal
	private String dataPublicacao; // dd/MM/yyy
	private String vigencia; // dd/MM/yyyy-dd2/MM2/yyy2
	
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
	
	public Norma(String epigrafe, String ementa, String autoria, String local,
			List<ElementoNorma> elementosNorma, String identificadorUnico,
			String tipo, String dataPublicacao, String vigencia) {
		this.epigrafe = epigrafe;
		this.ementa = ementa;
		this.autoria = autoria;
		this.local = local;
		this.elementosNorma = elementosNorma;
		this.identificadorUnico = identificadorUnico;
		this.tipo = tipo;
		this.dataPublicacao = dataPublicacao;
		this.vigencia = vigencia;
	}

	public Norma(String epigrafe, String ementa, String autoria, String local,
			String identificadorUnico, String tipo, String dataPublicacao,
			String vigencia) {
		this.epigrafe = epigrafe;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getIdentificadorUnico() {
		return identificadorUnico;
	}

	public void setIdentificadorUnico(String identificadorUnico) {
		this.identificadorUnico = identificadorUnico;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDataPublicacao() {
		return dataPublicacao;
	}

	public void setDataPublicacao(String dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}

	public String getVigencia() {
		return vigencia;
	}

	public void setVigencia(String vigencia) {
		this.vigencia = vigencia;
	}

	public List<CitacaoTextLeg> getCitacoesFeitas() {
		return citacoesFeitas;
	}

	public void setCitacoesFeitas(List<CitacaoTextLeg> citacoesFeitas) {
		this.citacoesFeitas = citacoesFeitas;
	}

	public List<CitacaoDocJud> getCitacoesRecebidasDocJud() {
		return citacoesRecebidasDocJud;
	}

	public void setCitacoesRecebidasDocJud(
			List<CitacaoDocJud> citacoesRecebidasDocJud) {
		this.citacoesRecebidasDocJud = citacoesRecebidasDocJud;
	}

	public List<CitacaoTextLeg> getCitacoesRecebidasTextLeg() {
		return citacoesRecebidasTextLeg;
	}

	public void setCitacoesRecebidasTextLeg(
			List<CitacaoTextLeg> citacoesRecebidasTextLeg) {
		this.citacoesRecebidasTextLeg = citacoesRecebidasTextLeg;
	}

	public List<Alteracao> getAlteracoesFeitas() {
		return alteracoesFeitas;
	}

	public void setAlteracoesFeitas(List<Alteracao> alteracoesFeitas) {
		this.alteracoesFeitas = alteracoesFeitas;
	}

	public List<Alteracao> getAlteracoesRecebidas() {
		return alteracoesRecebidas;
	}

	public void setAlteracoesRecebidas(List<Alteracao> alteracoesRecebidas) {
		this.alteracoesRecebidas = alteracoesRecebidas;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
