package law;

import java.util.ArrayList;


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
