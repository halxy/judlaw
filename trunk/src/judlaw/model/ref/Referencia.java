package judlaw.model.ref;


/**
 * 
 * @author Halley Freitas
 *
 */
public abstract class Referencia {
	
	private String origem; // identificadorUnico do Documento que fez a refer�ncia
	private String destino; // identificadorUnico do Documento que recebeu a refer�ncia
	private String data; // data em que foi feita a refer�ncia
	
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
