package law;

import java.util.List;

import referencia.Referencia;

/**
 * 
 * @author Halley Freitas
 *
 */
public class ElementoNorma {
	
	private int identificador;
	private String texto;
	private String data;
	private String vigencia;
	private List<Referencia> referenciasFeitas;
	private List<Referencia> referenciasRecebidas;
	
	
	public ElementoNorma(int identificador, String texto, String data,
			String vigencia, List<Referencia> referenciasFeitas,
			List<Referencia> referenciasRecebidas) {
		this.identificador = identificador;
		this.texto = texto;
		this.data = data;
		this.vigencia = vigencia;
		this.referenciasFeitas = referenciasFeitas;
		this.referenciasRecebidas = referenciasRecebidas;
	}

	public int getIdentificador() {
		return identificador;
	}
	
	public void setIdentificador(int identificador) {
		this.identificador = identificador;
	}
	
	public String getTexto() {
		return texto;
	}
	
	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	public String getVigencia() {
		return vigencia;
	}
	
	public void setVigencia(String vigencia) {
		this.vigencia = vigencia;
	}
	
	public List<Referencia> getReferenciasFeitas() {
		return referenciasFeitas;
	}
	
	public void setReferenciasFeitas(List<Referencia> referenciasFeitas) {
		this.referenciasFeitas = referenciasFeitas;
	}
	
	public List<Referencia> getReferenciasRecebidas() {
		return referenciasRecebidas;
	}
	
	public void setReferenciasRecebidas(List<Referencia> referenciasRecebidas) {
		this.referenciasRecebidas = referenciasRecebidas;
	}
	
	
}
