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
	
	/**
	 * 
	 * @param origem
	 * @param destino
	 * @param data
	 * @param tipo
	 * @param caracteristica
	 */
	public Alteracao(String origem, String destino, String data, String tipo,
			String caracteristica) {
		super(origem, destino, data);
		this.tipo = tipo;
		this.caracteristica = caracteristica;
	}

	/**
	 * Construtor vazio
	 */
	public Alteracao(){super();}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCaracteristica() {
		return caracteristica;
	}

	public void setCaracteristica(String caracteristica) {
		this.caracteristica = caracteristica;
	}
}
