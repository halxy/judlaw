/* 
 * Universidade Federal de Campina Grande


 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas
 * 		
 */
package judlaw.model.bean.ref;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "alteracao")
@SequenceGenerator(name = "alteracao_seq", sequenceName = "alteracao_seq", initialValue = 1, allocationSize = 1)
public class Alteracao extends Referencia {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="alteracao_seq")
	@Column(name="alteracao_id")
	private Integer id;

	private String tipo; // inclusao, revogacao, alteracao;
	private String caracteristica; // mais branda, mais punitiva, etc.
	
	// Atributos advindos de Referencia
	private String origem; // identificadorUnico do Documento que fez a referência
	private String destino; // identificadorUnico do Documento que recebeu a referência
	private String data; // data em que foi feita a referência
	
	public Alteracao(String origem, String destino, String data, String tipo,
			String caracteristica) {
		this.origem = origem;
		this.destino = destino;
		this.data = data;
		this.tipo = tipo;
		this.caracteristica = caracteristica;
	}

	/**
	 * Construtor vazio
	 */
	public Alteracao(){}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 
	 * @return
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * 
	 * @param tipo
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * 
	 * @return
	 */
	public String getCaracteristica() {
		return caracteristica;
	}

	/**
	 * 
	 * @param caracteristica
	 */
	public void setCaracteristica(String caracteristica) {
		this.caracteristica = caracteristica;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
