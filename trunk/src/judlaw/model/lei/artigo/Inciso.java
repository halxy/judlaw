package judlaw.model.lei.artigo;

import java.util.ArrayList;

import judlaw.model.lei.ElementoNorma;



public class Inciso extends ElementoNorma {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Inciso(){
		setElementosNorma(new ArrayList<Alinea>());
	}
}
