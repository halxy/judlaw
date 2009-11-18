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
import javax.persistence.ManyToOne;
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

	// Texto do Elemento da Norma
	private String texto;
	
	private String identificadorUnico; // cp_art120; lei1234; cc_art1_par2.
	private String tipo; // tipo do textoLegal
	private String dataPublicacao; // dd/MM/yyy
	private String vigencia; // dd/MM/yyyy-dd2/MM2/yyy2
	
	//ElementoNorma que compoem o elementoNorma
	@OneToMany(mappedBy="elementoNormaPai", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
	private List<ElementoNorma> elementosNorma;
	
	/*
	 * Pai do Elemento Norma
	 */
	//Quando o pai do ElementoNorma � outro ElementoNorma
	@ManyToOne
	@JoinColumn(name="elementonormapai_id", nullable = true)
	private ElementoNorma elementoNormaPai;
	
	//Quando o pai do ElementoNorma � uma Norma
	@ManyToOne
	@JoinColumn(name="normapai_id", nullable = true)
	private Norma normaPai;
	
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
	 * Construtor sem par�metros
	 */
	public ElementoNorma() {
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

	public String getTexto() {
		return texto;
	}
	
	public void setTexto(String texto) {
		this.texto = texto;
	}

	public List<ElementoNorma> getElementosNorma() {
		return elementosNorma;
	}

	public void setElementosNorma(List<ElementoNorma> elementosNorma) {
		this.elementosNorma = elementosNorma;
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

	public ElementoNorma getElementoNormaPai() {
		return elementoNormaPai;
	}

	public void setElementoNormaPai(ElementoNorma elementoNormaPai) {
		this.elementoNormaPai = elementoNormaPai;
	}

	public Norma getNormaPai() {
		return normaPai;
	}

	public void setNormaPai(Norma normaPai) {
		this.normaPai = normaPai;
	}

	public List<CitacaoDocJud> getCitacoesRecebidasDocJud() {
		return citacoesRecebidasDocJud;
	}

	public void setCitacoesRecebidasDocJud(
			List<CitacaoDocJud> citacoesRecebidasDocJud) {
		this.citacoesRecebidasDocJud = citacoesRecebidasDocJud;
	}

	public List<CitacaoTextLeg> getCitacoesFeitas() {
		return citacoesFeitas;
	}

	public void setCitacoesFeitas(List<CitacaoTextLeg> citacoesFeitas) {
		this.citacoesFeitas = citacoesFeitas;
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
}
