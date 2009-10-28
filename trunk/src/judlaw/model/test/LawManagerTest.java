package judlaw.model.test;

import static org.junit.Assert.*;
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
	/**
	 * Test method for {@link judlaw.model.lei.ElementoNorma#getPai}.
	 * @throws Exception 
	 */
	@Test public void testGetPai() {
		ElementoNorma ele1 = new ElementoNorma();
		ele1.setIdentificadorUnico("cp_art120_par1");
		assertEquals("cp_art120", ele1.getPai());
		assertEquals("cp_art120", ele1.getPai());
	}
	

}
