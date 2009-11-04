package judlaw.model.bean.ref;


/**
 * 
 * @author Halley Freitas
 *
 */
public abstract class Referencia {
	
	private String origem; // identificadorUnico do Documento que fez a referÍncia
	private String destino; // identificadorUnico do Documento que recebeu a referÍncia
	private String data; // data em que foi feita a referÍncia
	
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
