package judlaw.model.reference;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
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
	@Column(name="referencia_id")
	private Long id;
	
	private String origem;
	private String destino;
	
	public Referencia(String origem, String destino) {
		this.origem = origem;
		this.destino = destino;
	}
	
	public Referencia() {}

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
