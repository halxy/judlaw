package law;

import java.util.ArrayList;
import java.util.List;

import judlaw.model.referencia.Referencia;


/**
 * Classe ElementoNorma - superclasse que representa os elementos de uma norma:
 * Partes, Livros, Titulos, Capitulos, Secoes, Subsecoes, Artigos, Paragrafos, Incisos, Alineas e Itens.
 * @author Halley Freitas
 *
 */
public abstract class ElementoNorma {
	
	private int identificador;
	private String texto;
	private String data;
	private String vigencia;
	// Listas de referencias associadas ao ElementoNorma
	private List<Referencia> referenciasFeitas;
	private List<Referencia> referenciasRecebidas;
	// O elemento no qual o elementoDaNorma está inserido. Ex: inciso está inserido no paragrafo.
	private String elementoPai;
	// Lista de elementos que compoem o ElementoNorma
	private List<? extends ElementoNorma> elementosNorma;

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
	
	public List<? extends ElementoNorma> getElementosNorma() {
		return elementosNorma;
	}

	public void setElementosNorma(ArrayList<? extends ElementoNorma> arrayList) {
		this.elementosNorma = arrayList;
	}
}
