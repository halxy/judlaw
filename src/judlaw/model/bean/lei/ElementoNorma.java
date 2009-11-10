/* 
 * Universidade Federal de Campina Grande


 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas
 * 		
 */
package judlaw.model.bean.lei;

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
		
	@OneToMany(mappedBy="textoLegalPai", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
	private List<ElementoNorma> elementosNorma;
	
	//TextoLegal pai do ElementoNorma (pode ser uma Norma ou outro ElementoNorma
	@ManyToOne
	@JoinColumn(name="textolegaipai_id")
	private TextoLegal textoLegalPai;
	
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
			String dataPublicacao, String vigencia,
			List<ElementoNorma> elementosNorma) {
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

	public TextoLegal getTextoLegalPai() {
		return textoLegalPai;
	}

	public void setTextoLegalPai(TextoLegal textoLegalPai) {
		this.textoLegalPai = textoLegalPai;
	}
	
//	/*
//	 * Retorna o pai do elemento usando como parametro seu identificador unico
//	 */
//	private String getPaiPeloIdUnico() {
//		String idUnico = getIdentificadorUnico();
//		StringTokenizer tokenId = new StringTokenizer(idUnico, "_");
//		String tokenAux = "";
//		while( tokenId.hasMoreTokens() ) {
//			tokenAux = tokenId.nextToken();
//		}
//		int posicaoId = idUnico.indexOf("_"+tokenAux);	
//		return idUnico.substring(0, posicaoId);
//	}
}
