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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import judlaw.model.bean.lei.Norma;

@Entity
@Table(name = "citacaotextleg")
@SequenceGenerator(name = "citacaotextleg_seq", sequenceName = "citacaotextleg_seq", initialValue = 1, allocationSize = 1)
public class CitacaoTextLeg extends Referencia {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="citacaotextleg_seq")
	@Column(name="citacaotextleg_id")
	private Integer id;

	/* --------- Atributos --------- */
	private String destino; // identificadorUnico do Documento que recebeu a referência
	private String data; // data em que foi feita a referência
	
	@ManyToOne
	@JoinColumn(name="origemNorma_id")
	private Norma normaOrigem;
	
	/**
	 * 
	 * @param normaOrigem
	 * @param destino
	 * @param data
	 */
	public CitacaoTextLeg(Norma normaOrigem, String destino, String data) {
		this.normaOrigem = normaOrigem;
		this.destino = destino;
		this.data = data;
	}

	/**
	 * Construtor vazio
	 */
	public CitacaoTextLeg(){}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Norma getNormaOrigem() {
		return normaOrigem;
	}

	public void setNormaOrigem(Norma normaOrigem) {
		this.normaOrigem = normaOrigem;
	}
}
