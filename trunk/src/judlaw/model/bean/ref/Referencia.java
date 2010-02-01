/* 
 * Universidade Federal de Campina Grande
 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas 		
 */
package judlaw.model.bean.ref;


/**
 * Classe Referencia - representa uma referencia de forma generica
 * @author Halley Freitas
 *
 */
public abstract class Referencia {
	
	private String origem; // identificadorUnico do Documento que fez a referência
	private String destino; // identificadorUnico do Documento que recebeu a referência
	private String data; // data em que foi feita a referência
	
	/**
	 * 
	 * @param origem
	 * @param destino
	 * @param data
	 */
	public Referencia(String origem, String destino, String data) {
		this.origem = origem;
		this.destino = destino;
		this.data = data;
	}
	
	/**
	 * Construtor vazio
	 */
	public Referencia(){}

	/**
	 *
	 * @return
	 */
	public String getOrigem() {
		return origem;
	}
	
	/**
	 * 
	 * @param origem
	 */
	public void setOrigem(String origem) {
		this.origem = origem;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getDestino() {
		return destino;
	}
	
	/**
	 * 
	 * @param destino
	 */
	public void setDestino(String destino) {
		this.destino = destino;
	}

	/**
	 * 
	 * @return
	 */
	public String getData() {
		return data;
	}

	/**
	 * 
	 * @param data
	 */
	public void setData(String data) {
		this.data = data;
	}
}
