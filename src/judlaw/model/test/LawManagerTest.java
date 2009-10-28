package judlaw.model.test;

import judlaw.model.lei.ElementoNorma;
import judlaw.model.util.Constantes;

import org.junit.Before;
import org.junit.Test;

/**
 * Classe de Testes para a classe LawManager
 * @author Halley Freitas
 *
 */
public class LawManagerTest {

	ElementoNorma artigo, paragrafo, inciso;
	
	@Before public void setUp(){
		// Setando as propriedades dos elementos da norma
		
		// Inciso
		inciso = new ElementoNorma();
		inciso.setIdentificadorUnico("cp_art120_par2_inc1");
		inciso.setTipoElemento(Constantes.INCISO);
		inciso.setTexto("Texto do inciso");
		inciso.setData("28/10/2009");
		inciso.setVigencia("28/10/2009-99/99/9999");
		
	}

	@Test public void simpleAdd() {
	}
	

}
