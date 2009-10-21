package judlaw.model.law.secoes;

import java.util.ArrayList;

import judlaw.model.law.ElementoNorma;


public class Capitulo extends ElementoNorma {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Capitulo(){
		setElementosNorma(new ArrayList<Secao>());
	}
}
