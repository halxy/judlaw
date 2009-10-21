package judlaw.model.lei.secoes;

import java.util.ArrayList;

import judlaw.model.lei.ElementoNorma;


public class Titulo extends ElementoNorma {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Titulo() {
		setElementosNorma(new ArrayList<Capitulo>());
	}
}
