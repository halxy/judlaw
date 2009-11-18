/* 
 * Universidade Federal de Campina Grande
 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas	
 */
package judlaw.model.test.logic;

import static org.junit.Assert.assertEquals;
import judlaw.model.logic.time.TimeLogic;
import judlaw.model.util.Constantes;

import org.junit.Before;
import org.junit.Test;

/**
 * Classe TimeLogicTest - testes das operacoes da classe TimeLogic
 * @author Halley Freitas
 *
 */
public class TimeLogicTest {
	TimeLogic tm = TimeLogic.getInstance();
	
	@Before public void setUp(){
	}

	/**
	 * Test method for {@link judlaw.model.logic.time.TimeLogic#comparaDatas(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testDataMaisAtual() throws Exception {
		
		//Data1 > Data2
		assertEquals(1, tm.comparaDatas("12/10/2009", "12/10/2008", Constantes.DELIMITADOR_DATA));
		assertEquals(1, tm.comparaDatas("12/11/2009", "12/10/2009", Constantes.DELIMITADOR_DATA));
		assertEquals(1, tm.comparaDatas("13/11/2009", "12/11/2009", Constantes.DELIMITADOR_DATA));
		
		//Data1 < Data2
		assertEquals(-1, tm.comparaDatas("12/10/2008", "12/10/2009", Constantes.DELIMITADOR_DATA));
		assertEquals(-1, tm.comparaDatas("12/10/2009", "12/11/2009", Constantes.DELIMITADOR_DATA));
		assertEquals(-1, tm.comparaDatas("12/11/2009", "13/11/2009", Constantes.DELIMITADOR_DATA));
		
		//Data1 = Data2
		assertEquals(0, tm.comparaDatas("12/10/2008", "12/10/2008", Constantes.DELIMITADOR_DATA));
	}
	
	/**
	 * Compara uma Vigencia com uma Data
	 * @throws Exception
	 */
	@Test
	public void comparaVigenciaComData() throws Exception {
		// Vigencia dentro do prazo de validade
		assertEquals(1, tm.comparaVigenciaComData("12/10/2009-12/10/2010", Constantes.DELIMITADOR_VIGENCIA, 
												  "12/10/2009", Constantes.DELIMITADOR_DATA) );
		assertEquals(1, tm.comparaVigenciaComData("12/10/2009-12/10/2010", Constantes.DELIMITADOR_VIGENCIA, 
				  								   "12/09/2010", Constantes.DELIMITADOR_DATA) );
		assertEquals(1, tm.comparaVigenciaComData("12/10/2009-12/10/2010", Constantes.DELIMITADOR_VIGENCIA, 
				   								  "11/10/2010", Constantes.DELIMITADOR_DATA) );
		
		assertEquals(0, tm.comparaVigenciaComData("12/10/2009-12/10/2010", Constantes.DELIMITADOR_VIGENCIA, 
												  "12/10/2010", Constantes.DELIMITADOR_DATA) );
		
		// Vigencia expirada
		assertEquals(-1, tm.comparaVigenciaComData("12/10/2009-12/10/2010", Constantes.DELIMITADOR_VIGENCIA, 
				  								  "12/10/2011", Constantes.DELIMITADOR_DATA) );
		assertEquals(-1, tm.comparaVigenciaComData("12/10/2009-12/10/2010", Constantes.DELIMITADOR_VIGENCIA, 
				   								  "12/11/2010", Constantes.DELIMITADOR_DATA) );
		assertEquals(-1, tm.comparaVigenciaComData("12/10/2009-12/10/2010", Constantes.DELIMITADOR_VIGENCIA, 
												   "13/10/2010", Constantes.DELIMITADOR_DATA) );
	}
	
	/**
	 * Compara vigencias
	 * @throws Exception
	 */
	public void testComparaVigencias() throws Exception {
		// Vigencias com fins iguais
		assertEquals(0, tm.comparaVigencias("12/10/2009-12/10/2010", "13/10/2009-12/10/2010", 
											Constantes.DELIMITADOR_VIGENCIA, Constantes.DELIMITADOR_DATA) );
		// Vigencia1 com datafim maior que Vigencia2
		assertEquals(1, tm.comparaVigencias("12/10/2009-12/10/2010", "12/10/2009-11/10/2010", 
				Constantes.DELIMITADOR_VIGENCIA, Constantes.DELIMITADOR_DATA) );
		// Vigencia2 com datafim maior que Vigencia1
		assertEquals(-1, tm.comparaVigencias("12/10/2009-11/10/2010", "12/10/2009-12/10/2010", 
				Constantes.DELIMITADOR_VIGENCIA, Constantes.DELIMITADOR_DATA) );
	}
}
