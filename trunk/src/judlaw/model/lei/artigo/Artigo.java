package judlaw.model.lei.artigo;

import java.util.ArrayList;

import judlaw.model.lei.ElementoNorma;



public class Artigo extends ElementoNorma {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String caput;
	
	public Artigo() {
		setElementosNorma(new ArrayList<Paragrafo>());
	}
	
	public String getCaput() {
		return caput;
	}

	public void setCaput(String caput) {
		this.caput = caput;
	}
	
}
