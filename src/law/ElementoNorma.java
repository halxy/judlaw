package law;

import java.util.ArrayList;
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
	// O elemento no qual o elementoDaNorma está inserido. Ex: inciso está inserido no paragrafo.
	private String elementoPai;
	
	public ElementoNorma(int identificador, String texto, String data,
			String vigencia, List<Referencia> referenciasFeitas,
			List<Referencia> referenciasRecebidas, String elementoPai) {
		this.identificador = identificador;
		this.texto = texto;
		this.data = data;
		this.vigencia = vigencia;
		this.referenciasFeitas = referenciasFeitas;
		this.referenciasRecebidas = referenciasRecebidas;
		this.elementoPai = elementoPai;
	}

	public ElementoNorma() {
		inicializaListas();
	}
	
	private void inicializaListas(){
		this.referenciasFeitas = new ArrayList<Referencia>();
		this.referenciasRecebidas = new ArrayList<Referencia>();
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
	
	public String getElementoPai() {
		return elementoPai;
	}

	public void setElementoPai(String elementoPai) {
		this.elementoPai = elementoPai;
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
