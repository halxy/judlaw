package judlaw.model.law.secoes;

import java.util.ArrayList;

import judlaw.model.law.ElementoNorma;


public class Secao extends ElementoNorma {

	public Secao(){
		setElementosNorma(new ArrayList<Subsecao>());
	}
}
