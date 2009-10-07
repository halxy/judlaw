package judlaw.model.law.artigo;

import java.util.ArrayList;

import law.ElementoNorma;


public class Paragrafo extends ElementoNorma {

	public Paragrafo(){
		setElementosNorma(new ArrayList<Inciso>());
	}
}
