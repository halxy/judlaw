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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * Define o comportamento das partes (relator, acusador, etc.);
 * @author Halley Freitas
 *
 */
@Entity
@Table(name = "parte")
@SequenceGenerator(name = "parte_seq", sequenceName = "parte_seq", initialValue = 1, allocationSize = 1)
public class Parte {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="parte_seq")
	@Column(name="parte_id")
	private Integer id;
	
	private String titulo;
	private String nome;
	
	@ManyToMany(mappedBy="partes")
	private List<DocumentoJuridico> documentosJuridicos;
	
	/**
	 * Construtor default
	 * @param titulo
	 * @param nome
	 * @param documentosJuridicos
	 */
	public Parte(String titulo, String nome, List<DocumentoJuridico> documentosJuridicos) {
		this.titulo = titulo;
		this.nome = nome;
		this.documentosJuridicos = documentosJuridicos;
	}
	
	/**
	 * 
	 * @param titulo
	 * @param nome
	 */
	public Parte(String titulo, String nome) {
		this.titulo = titulo;
		this.nome = nome;
		this.documentosJuridicos =  new ArrayList<DocumentoJuridico>();
	}

	/**
	 * Construtor vazio
	 */
	public Parte() {
		this.documentosJuridicos = new ArrayList<DocumentoJuridico>();
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<DocumentoJuridico> getDocumentosJuridicos() {
		return documentosJuridicos;
	}

	public void setDocumentosJuridicos(List<DocumentoJuridico> documentosJuridicos) {
		this.documentosJuridicos = documentosJuridicos;
	}
}
