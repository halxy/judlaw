/* 
 * Universidade Federal de Campina Grande
 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas	
 */
package judlaw.model.bean.docjud;

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import judlaw.model.bean.ref.CitacaoDocJud;
import judlaw.model.bean.ref.CitacaoTextLeg;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * Classe DocumentoJuridico. Representa os documentos juridicos.
 * @author Halley Freitas
 *
 */
@Entity
@Table(name = "documentojuridico")
@SequenceGenerator(name = "documentojuridico_seq", sequenceName = "documentojuridico_seq", initialValue = 1, allocationSize = 1)
public class DocumentoJuridico {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="documentojuridico_seq")
	@Column(name="documentojuridico_id")
	private Integer id;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="cabecalho_fk", nullable = true)
	private Cabecalho cabecalho;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="ementa_fk")
	private Ementa ementa;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="relatorio_fk")
	private Relatorio relatorio;
	
	@OneToMany(mappedBy="documentoJuridico", cascade = CascadeType.ALL)
    @Column(name="voto_fk")
    @LazyCollection(LazyCollectionOption.FALSE)
	private List<Voto> votos;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="encerramento_fk")
	private Encerramento encerramento;
	
	// DocumentoJuridico é o Mapping Owner.
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "docjudpartes",
			joinColumns = {
				@JoinColumn(name="documentojuridico_id")           
    		},
    		inverseJoinColumns = {
				@JoinColumn(name="parte_id")
    		}
	)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Parte> partes; //As partes envolvidas, menos o relator
	
	/* --------- Atributos ----------- */
	private String identificadorUnico; // HC123, etc.
	private String dataJulgamento; // dd/MM/yyy - data que foi feito o documento
	private String dataPublicacao; // dd/MM/yyy - data que foi publicado oficialmente - quando comeca a valer
	
	/* --------- Referencias --------- */
	// Citacoes feitas
	@OneToMany(mappedBy="documentoJuridicoOrigem", cascade = CascadeType.ALL)
    @Column(name="citacoesfeitas_fk")
    @LazyCollection(LazyCollectionOption.FALSE)
	private List<CitacaoDocJud> citacoesFeitas;
	
	//Citacoes recebidas de outros documentos juridicos
	@OneToMany(mappedBy="documentoJuridicoDestino", cascade = CascadeType.ALL)
    @Column(name="citacoesrecebidasdocjud_fk")
    @LazyCollection(LazyCollectionOption.FALSE)
	private List<CitacaoDocJud> citacoesRecebidasDocJud;
	
	//Citacoes recebidas de normas/elementosNorma
	@OneToMany(mappedBy="documentoJuridicoDestino", cascade = CascadeType.ALL)
    @Column(name="citacoesrecebidastextleg_fk")
    @LazyCollection(LazyCollectionOption.FALSE)
	private List<CitacaoTextLeg> citacoesRecebidasTextLeg;
	
	/**
	 * 
	 * @param cabecalho
	 * @param ementa
	 * @param relatorio
	 * @param votos
	 * @param encerramento
	 * @param partes
	 * @param identificadorUnico
	 * @param tipo
	 * @param dataPublicacao
	 */
	public DocumentoJuridico(Cabecalho cabecalho, Ementa ementa,
			Relatorio relatorio, List<Voto> votos, Encerramento encerramento,
			List<Parte> partes, String identificadorUnico,
			String dataPublicacao) {
		this.cabecalho = cabecalho;
		this.ementa = ementa;
		this.relatorio = relatorio;
		this.votos = votos;
		this.encerramento = encerramento;
		this.partes = partes;
		this.identificadorUnico = identificadorUnico;
		this.dataJulgamento = dataPublicacao;
	}

	/**
	 * Construtor vazio que apenas inicializa as listas
	 */
	public DocumentoJuridico() {
		this.cabecalho = new Cabecalho();
		this.ementa = new Ementa();
		this.relatorio = new Relatorio();
		this.encerramento = new Encerramento();
		this.votos = new ArrayList<Voto>();
		this.partes = new ArrayList<Parte>();
		this.citacoesFeitas = new ArrayList<CitacaoDocJud>();
		this.citacoesRecebidasDocJud = new ArrayList<CitacaoDocJud>();
		this.citacoesRecebidasTextLeg = new ArrayList<CitacaoTextLeg>();
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
	public Cabecalho getCabecalho() {
		return cabecalho;
	}
	
	/**
	 * 
	 * @param cabecalho
	 */
	public void setCabecalho(Cabecalho cabecalho) {
		this.cabecalho = cabecalho;
	}
	
	/**
	 * 
	 * @return
	 */
	public Ementa getEmenta() {
		return ementa;
	}
	
	/**
	 * 
	 * @param ementa
	 */
	public void setEmenta(Ementa ementa) {
		this.ementa = ementa;
	}
	
	public Relatorio getRelatorio() {
		return relatorio;
	}
	
	/**
	 * 
	 * @param relatorio
	 */
	public void setRelatorio(Relatorio relatorio) {
		this.relatorio = relatorio;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Voto> getVotos() {
		return votos;
	}
	
	/**
	 * 
	 * @param votos
	 */
	public void setVotos(List<Voto> votos) {
		this.votos = votos;
	}
	
	/**
	 * 
	 * @return
	 */
	public Encerramento getEncerramento() {
		return encerramento;
	}
	
	/**
	 * 
	 * @param encerramento
	 */
	public void setEncerramento(Encerramento encerramento) {
		this.encerramento = encerramento;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Parte> getPartes() {
		return partes;
	}
	
	/**
	 * 
	 * @param partes
	 */
	public void setPartes(List<Parte> partes) {
		this.partes = partes;
	}

	/**
	 * 
	 * @return
	 */
	public String getIdentificadorUnico() {
		return identificadorUnico;
	}

	/**
	 * 
	 * @param identificadorUnico
	 */
	public void setIdentificadorUnico(String identificadorUnico) {
		this.identificadorUnico = identificadorUnico;
	}

	/**
	 * 
	 * @return
	 */
	public String getDataJulgamento() {
		return dataJulgamento;
	}

	/**
	 * 
	 * @param dataJulgamento
	 */
	public void setDataJulgamento(String dataJulgamento) {
		this.dataJulgamento = dataJulgamento;
	}

	/**
	 * 
	 * @return
	 */
	public List<CitacaoDocJud> getCitacoesFeitas() {
		return citacoesFeitas;
	}

	/**
	 * 
	 * @param citacoesFeitas
	 */
	public void setCitacoesFeitas(List<CitacaoDocJud> citacoesFeitas) {
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
	 * @return
	 */
	public String getDataPublicacao() {
		return dataPublicacao;
	}

	/**
	 * 
	 * @param dataPublicacao
	 */
	public void setDataPublicacao(String dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}
}
