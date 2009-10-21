package judlaw.model.lei.secoes;

import java.util.ArrayList;

import judlaw.model.lei.ElementoNorma;
import judlaw.model.lei.artigo.Artigo;


public class Subsecao extends ElementoNorma{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Subsecao(){
		setElementosNorma(new ArrayList<Artigo>());
	}
}
