package judlaw.model.law.secoes;

import java.util.ArrayList;

import judlaw.model.law.ElementoNorma;
import judlaw.model.law.artigo.Artigo;


public class Subsecao extends ElementoNorma{

	public Subsecao(){
		setElementosNorma(new ArrayList<Artigo>());
	}
}
