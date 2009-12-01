/* 
 * Universidade Federal de Campina Grande
 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas	
 */
package judlaw.model.bean.doutrina;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import judlaw.model.bean.ref.CitacaoDocJud;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * Classe Doutrina
 * @author Halley Freitas
 *
 */
@Entity
@Table(name = "doutrina")
@SequenceGenerator(name = "doutrina_seq", sequenceName = "doutrina_seq", initialValue = 1, allocationSize = 1)
public class Doutrina {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="doutrina_seq")
	@Column(name="doutrina_id")
	private Integer id;
	
	//Atributos
	private String titulo;
	private String edicao;
	private String editora;
	private String local;
	private String anoPublicacao;
	private String isbn;
	private String autor;	
	
	//Citacoes recebidas de outros documentos juridicos
	@OneToMany(mappedBy="doutrinaDestino", cascade = CascadeType.ALL)
    @Column(name="citacoesrecebidasdocjud_fk")
    @LazyCollection(LazyCollectionOption.FALSE)
	private List<CitacaoDocJud> citacoesRecebidasDocJud;
	
	/**
	 * 
	 * @param titulo
	 * @param edicao
	 * @param editora
	 * @param local
	 * @param anoPublicacao
	 * @param isbn
	 * @param autor
	 */
	public Doutrina(String titulo, String edicao, String editora, String local,
			String anoPublicacao, String isbn, String autor) {
		this.titulo = titulo;
		this.edicao = edicao;
		this.editora = editora;
		this.local = local;
		this.anoPublicacao = anoPublicacao;
		this.isbn = isbn;
		this.autor = autor;
		this.citacoesRecebidasDocJud = new ArrayList<CitacaoDocJud>();
	}

	/**
	 * Construtor vazio
	 */
	public Doutrina(){
		this.citacoesRecebidasDocJud = new ArrayList<CitacaoDocJud>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getEdicao() {
		return edicao;
	}

	public void setEdicao(String edicao) {
		this.edicao = edicao;
	}

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getAnoPublicacao() {
		return anoPublicacao;
	}

	public void setAnoPublicacao(String anoPublicacao) {
		this.anoPublicacao = anoPublicacao;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public List<CitacaoDocJud> getCitacoesRecebidasDocJud() {
		return citacoesRecebidasDocJud;
	}

	public void setCitacoesRecebidasDocJud(
			List<CitacaoDocJud> citacoesRecebidasDocJud) {
		this.citacoesRecebidasDocJud = citacoesRecebidasDocJud;
	}
}
