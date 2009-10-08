package judlaw.model.law.artigo;

import java.util.ArrayList;

import judlaw.model.law.ElementoNorma;



public class Paragrafo extends ElementoNorma {

	public Paragrafo(){
		setElementosNorma(new ArrayList<Inciso>());
	}
}
