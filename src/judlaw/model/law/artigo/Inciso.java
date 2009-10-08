package judlaw.model.law.artigo;

import java.util.ArrayList;

import judlaw.model.law.ElementoNorma;



public class Inciso extends ElementoNorma {

	public Inciso(){
		setElementosNorma(new ArrayList<Alinea>());
	}
}
