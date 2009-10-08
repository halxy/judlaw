package judlaw.model.law.artigo;

import java.util.ArrayList;

import judlaw.model.law.ElementoNorma;



public class Artigo extends ElementoNorma {

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
