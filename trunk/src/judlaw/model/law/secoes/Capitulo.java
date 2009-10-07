package judlaw.model.law.secoes;

import java.util.ArrayList;

import law.ElementoNorma;

public class Capitulo extends ElementoNorma {

	public Capitulo(){
		setElementosNorma(new ArrayList<Secao>());
	}
}
