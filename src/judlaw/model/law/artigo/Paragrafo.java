package judlaw.model.law.artigo;

import java.util.ArrayList;

import judlaw.model.law.ElementoNorma;



public class Paragrafo extends ElementoNorma {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Paragrafo(){
		setElementosNorma(new ArrayList<Inciso>());
	}
}
