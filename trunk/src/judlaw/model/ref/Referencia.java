package judlaw.model.ref;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import judlaw.model.manager.DBManager;

/**
 * 
 * @author Halley Freitas
 *
 */
@Entity
@Table(name = "referencia")
@SequenceGenerator(name = "referencia_seq", sequenceName = "referencia_seq", initialValue = 1, allocationSize = 1)
public class Referencia {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="referencia_seq")
	@Column(name="referencia_id")
	private Integer id;
	
	private String origem;
	private String destino;
	private String tipo; // Simples, Inclusao, Alteracao, etc.
	private String efeito; // mais brando, mais grave, etc.
	
	/**
	 * Construtor Referencia
	 * @param origem
	 * @param destino
	 * @param tipo
	 * @param efeito
	 */
	public Referencia(String origem, String destino, String tipo, String efeito) {
		this.origem = origem;
		this.destino = destino;
		this.tipo = tipo;
		this.efeito = efeito;
	}
	
	/**
	 * 
	 * @param origem
	 * @param destino
	 * @param tipo
	 */
	public Referencia(String origem, String destino, String tipo) {
		this.origem = origem;
		this.destino = destino;
		this.tipo = tipo;
	}

	/**
	 * Construtor vazio
	 */
	public Referencia() {}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getEfeito() {
		return efeito;
	}

	public void setEfeito(String efeito) {
		this.efeito = efeito;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public static void main(String[] args) {
		DBManager.getInstance().save(new Referencia());
	}
}
