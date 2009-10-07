package judlaw.model.law.secoes;

import java.util.ArrayList;

import law.ElementoNorma;
import law.artigo.Artigo;

public class Subsecao extends ElementoNorma{

	public Subsecao(){
		setElementosNorma(new ArrayList<Artigo>());
	}
}
