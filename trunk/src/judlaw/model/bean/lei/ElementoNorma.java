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
import java.util.StringTokenizer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import judlaw.model.manager.DBManager;


/**
 * Classe ElementoNorma - superclasse que representa os elementos de uma norma:
 * Partes, Livros, Titulos, Capitulos, Secoes, Subsecoes, Artigos, Paragrafos, Incisos, Alineas e Itens.
 * @author Halley Freitas
 *
 */
@Entity
@Table(name = "elementonorma")
@SequenceGenerator(name = "elementonorma_seq", sequenceName = "elementonorma_seq", initialValue = 1, allocationSize = 1)
public class ElementoNorma extends DocumentoLegal {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="elementonorma_seq")
	@Column(name="elementonorma_id")
	private Integer id;

	// Texto do Elemento da Norma
	private String texto;
		
	// ESTUDAR PARA REFAZER ESSA PARTE DE BAIXO
	// Lista de elementos que compoem o ElementoNorma
	private List<? extends ElementoNorma> elementosNorma; //TODO
	// O elemento/normal no qual o elementoDaNorma está inserido. Ex: inciso está inserido no paragrafo.
	private String pai; //TODO
	
	/**
	 * Construtor sem parâmetros
	 */
	public ElementoNorma() {
		inicializaListas();
	}
	
	private void inicializaListas(){
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
	
	/**
	 * Retorna o pai do elemento
	 * @return
	 */
	public String getPai() {
		if(this.pai == null && getIdentificadorUnico() != null) {
			setPai( getPaiPeloIdUnico() );
		}
		return this.pai;
	}
	
	public void setPai(String pai) {
		this.pai = pai;
	}
	
	public List<? extends ElementoNorma> getElementosNorma() {
		return elementosNorma;
	}

	public void setElementosNorma(List<? extends ElementoNorma> elementosNorma) {
		this.elementosNorma = elementosNorma;
	}
	
	/*
	 * Retorna o pai do elemento usando como parametro seu identificador unico
	 */
	private String getPaiPeloIdUnico() {
		String idUnico = getIdentificadorUnico();
		StringTokenizer tokenId = new StringTokenizer(idUnico, "_");
		String tokenAux = "";
		while( tokenId.hasMoreTokens() ) {
			tokenAux = tokenId.nextToken();
		}
		int posicaoId = idUnico.indexOf("_"+tokenAux);	
		return idUnico.substring(0, posicaoId);
	}
	
	public static void main(String[] args) {
		DBManager.getInstance().save(new ElementoNorma());
	}
}
