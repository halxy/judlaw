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
@Table(name = "citacaodocleg")
@SequenceGenerator(name = "citacaodocleg_seq", sequenceName = "citacaodocleg_seq", initialValue = 1, allocationSize = 1)
public class CitacaoDocLeg extends Referencia {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="citacaodocleg_seq")
	@Column(name="citacaodocleg_id")
	private Integer id;

	// Atributos advindos de Referencia
	private String origem; // identificadorUnico do Documento que fez a referência
	private String destino; // identificadorUnico do Documento que recebeu a referência
	private String data; // data em que foi feita a referência
	
	
	public CitacaoDocLeg(String origem, String destino, String data) {
		this.origem = origem;
		this.destino = destino;
		this.data = data;
	}
	
	/**
	 * Construtor vazio
	 */
	public CitacaoDocLeg(){}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
