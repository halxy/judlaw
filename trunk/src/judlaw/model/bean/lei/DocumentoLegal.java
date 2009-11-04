package judlaw.model.bean.lei;

import judlaw.model.bean.Documento;

/**
 * Classe Abstrata DocumentoLegal - Superclasse de Norma e ElementoNorma
 * @author Halley Freitas
 *
 */
public abstract class DocumentoLegal extends Documento {

	private String tipo; // lei, mp, artigo, inciso, paragrafo, etc.
	private String vigencia; // dd/MM/yyyy-dd2/MM2/yyy2
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getVigencia() {
		return vigencia;
	}
	
	public void setVigencia(String vigencia) {
		this.vigencia = vigencia;
	}
}
