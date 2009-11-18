/* 
 * Universidade Federal de Campina Grande
 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas	
 */
package judlaw.model.test;

import static org.junit.Assert.assertEquals;
import judlaw.model.logic.TimeLogic;
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
	String data1, data2, data3, data4, data5;
	String vigencia1, vigencia2, vigencia3, vigencia4;
	
	@Before public void setUp(){
		data1 = "12/10/2009";
		data2 = "12/10/2010";
		data3 = "13/10/2009";
		data4 = "13/11/2009";
		data5 = "13/11/2009";
		vigencia1 = "11/10/2009-13/10/2009";
		vigencia2 = "11/10/2009-13/10/2009";
		vigencia3 = "11/10/2009-14/10/2009";
		vigencia3 = "11/10/2009-12/10/2009";
	}

	/**
	 * Test method for {@link judlaw.model.logic.TimeLogic#comparaDatas(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testDataMaisAtual() throws Exception {
		assertEquals(1, tm.comparaDatas(data2, data1, Constantes.DELIMITADOR_DATA));
		assertEquals(-1, tm.comparaDatas(data3, data4, Constantes.DELIMITADOR_DATA));
		assertEquals(0, tm.comparaDatas(data4, data5, Constantes.DELIMITADOR_DATA));
	}
	
	/**
	 * Compara uma Vigencia com uma Data
	 * @throws Exception
	 */
	@Test
	public void comparaVigenciaComData() throws Exception {
		// Vigencia dentro do prazo de validade
		assertEquals(1, tm.comparaVigenciaComData(vigencia1, Constantes.DELIMITADOR_VIGENCIA, 
												  data1, Constantes.DELIMITADOR_DATA) );
		assertEquals(0, tm.comparaVigenciaComData(vigencia1, Constantes.DELIMITADOR_VIGENCIA, 
												  data3, Constantes.DELIMITADOR_DATA) );
		// Vigencia expirada
		assertEquals(-1, tm.comparaVigenciaComData(vigencia1, Constantes.DELIMITADOR_VIGENCIA, data2, 
													Constantes.DELIMITADOR_DATA) );
		assertEquals(-1, tm.comparaVigenciaComData(vigencia1, Constantes.DELIMITADOR_VIGENCIA, data4, 
													Constantes.DELIMITADOR_DATA) );
		assertEquals(-1, tm.comparaVigenciaComData(vigencia1, Constantes.DELIMITADOR_VIGENCIA, data5, 
													Constantes.DELIMITADOR_DATA) );
	}
	
	/**
	 * Compara vigencias
	 * @throws Exception
	 */
	public void testComparaVigencias() throws Exception {
		// Vigencias com fins iguais
		assertEquals(0, tm.comparaVigencias(vigencia1, vigencia2, 
											Constantes.DELIMITADOR_VIGENCIA, Constantes.DELIMITADOR_DATA) );
		// Vigencia1 com datafim maior que Vigencia2
		assertEquals(1, tm.comparaVigencias(vigencia3, vigencia1, 
				Constantes.DELIMITADOR_VIGENCIA, Constantes.DELIMITADOR_DATA) );
		// Vigencia2 com datafim maior que Vigencia1
		assertEquals(-1, tm.comparaVigencias(vigencia1, vigencia3, 
				Constantes.DELIMITADOR_VIGENCIA, Constantes.DELIMITADOR_DATA) );
	}
}
