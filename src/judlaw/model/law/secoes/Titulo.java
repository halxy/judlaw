package judlaw.model.law.secoes;

import java.util.ArrayList;

import judlaw.model.law.ElementoNorma;


public class Titulo extends ElementoNorma {

	public Titulo() {
		setElementosNorma(new ArrayList<Capitulo>());
	}
}
