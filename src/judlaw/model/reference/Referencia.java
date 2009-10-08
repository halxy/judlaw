package judlaw.model.reference;

/**
 * 
 * @author Halley Freitas
 *
 */
public class Referencia {
	
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
}
