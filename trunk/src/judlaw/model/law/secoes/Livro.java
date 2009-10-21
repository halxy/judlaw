package judlaw.model.law.secoes;

import java.util.ArrayList;

import judlaw.model.law.ElementoNorma;


public class Livro extends ElementoNorma {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Livro() {
		setElementosNorma(new ArrayList<Titulo>());
	}

}
