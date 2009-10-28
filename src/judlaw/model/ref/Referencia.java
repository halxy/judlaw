package judlaw.model.ref;

import java.io.Serializable;

import judlaw.model.manager.DBManager;

/**
 * 
 * @author Halley Freitas
 *
 */
public class Referencia implements Serializable {
	private static final long serialVersionUID = 1L;

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
}
