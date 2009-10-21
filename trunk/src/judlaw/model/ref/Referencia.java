package judlaw.model.ref;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 
 * @author Halley Freitas
 *
 */
@Entity
public class Referencia implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//id 
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="referencia_id")
	private Long id;
	
	private String origem;
	private String destino;
	private String tipo;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
