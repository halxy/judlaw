package judlaw.model.law.secoes;

import java.util.ArrayList;

import judlaw.model.law.ElementoNorma;
import judlaw.model.law.artigo.Artigo;


public class Subsecao extends ElementoNorma{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Subsecao(){
		setElementosNorma(new ArrayList<Artigo>());
	}
}
