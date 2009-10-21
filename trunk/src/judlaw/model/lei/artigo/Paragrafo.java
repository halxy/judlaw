package judlaw.model.lei.artigo;

import java.util.ArrayList;

import judlaw.model.lei.ElementoNorma;



public class Paragrafo extends ElementoNorma {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Paragrafo(){
		setElementosNorma(new ArrayList<Inciso>());
	}
}
