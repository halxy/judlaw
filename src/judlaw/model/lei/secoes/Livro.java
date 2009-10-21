package judlaw.model.lei.secoes;

import java.util.ArrayList;

import judlaw.model.lei.ElementoNorma;


public class Livro extends ElementoNorma {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Livro() {
		setElementosNorma(new ArrayList<Titulo>());
	}

}
