package judlaw.model.lei.secoes;

import java.util.ArrayList;

import judlaw.model.lei.ElementoNorma;


public class Capitulo extends ElementoNorma {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Capitulo(){
		setElementosNorma(new ArrayList<Secao>());
	}
}
