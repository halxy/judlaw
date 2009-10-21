package judlaw.model.lei.secoes;

import java.util.ArrayList;

import judlaw.model.lei.ElementoNorma;


public class Secao extends ElementoNorma {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Secao(){
		setElementosNorma(new ArrayList<Subsecao>());
	}
}
