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

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * 
 * @author halley
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
	
	// DocumentoJuridico � o Mapping Owner.
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
	private List<Parte> partes; // relator, partes, etc.
	
	private String identificadorUnico; // cp_art120; lei1234; cc_art1_par2.
	private String tipo; // tipos de Documentos Juridicos e Documentos Legais
	private String dataPublicacao; // dd/MM/yyy
	
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
			List<Parte> partes, String identificadorUnico, String tipo,
			String dataPublicacao) {
		this.cabecalho = cabecalho;
		this.ementa = ementa;
		this.relatorio = relatorio;
		this.votos = votos;
		this.encerramento = encerramento;
		this.partes = partes;
		this.identificadorUnico = identificadorUnico;
		this.tipo = tipo;
		this.dataPublicacao = dataPublicacao;
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
}