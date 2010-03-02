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
	 * Verifica se uma data esta contida em um intervalo
	 * @throws Exception
	 */
	@Test
	public void dataContidaEmIntervalo() throws Exception {
		assertEquals(true, tm.dataContidaEmIntervalo("10/10/2010", Constantes.DELIMITADOR_DATA, 
				"10/10/2010-10/10/2010", Constantes.DELIMITADOR_INTERVALO));
		assertEquals(true, tm.dataContidaEmIntervalo("10/10/2010", Constantes.DELIMITADOR_DATA, 
				"10/10/2010-11/10/2010", Constantes.DELIMITADOR_INTERVALO));
		assertEquals(true, tm.dataContidaEmIntervalo("10/10/2010", Constantes.DELIMITADOR_DATA, 
				"09/10/2010-10/10/2010", Constantes.DELIMITADOR_INTERVALO));
		
		assertEquals(false, tm.dataContidaEmIntervalo("09/10/2010", Constantes.DELIMITADOR_DATA, 
				"10/10/2010-11/10/2010", Constantes.DELIMITADOR_INTERVALO));
		assertEquals(false, tm.dataContidaEmIntervalo("12/10/2010", Constantes.DELIMITADOR_DATA, 
				"10/10/2010-11/10/2010", Constantes.DELIMITADOR_INTERVALO));
		assertEquals(false, tm.dataContidaEmIntervalo("10/11/2010", Constantes.DELIMITADOR_DATA, 
				"09/10/2010-10/10/2010", Constantes.DELIMITADOR_INTERVALO));
	}
	
	/**
	 * Compara vigencias
	 * @throws Exception
	 */
	@Test
	public void testComparaIntervalos() throws Exception {
		// Vigencias com fins iguais
		assertEquals(0, tm.comparaIntervalos("12/10/2009-12/10/2010", "13/10/2009-12/10/2010", 
											Constantes.DELIMITADOR_INTERVALO, Constantes.DELIMITADOR_DATA) );
		// Vigencia1 com datafim maior que Vigencia2
		assertEquals(1, tm.comparaIntervalos("12/10/2009-12/10/2010", "12/10/2009-11/10/2010", 
				Constantes.DELIMITADOR_INTERVALO, Constantes.DELIMITADOR_DATA) );
		// Vigencia2 com datafim maior que Vigencia1
		assertEquals(-1, tm.comparaIntervalos("12/10/2009-11/10/2010", "12/10/2009-12/10/2010", 
				Constantes.DELIMITADOR_INTERVALO, Constantes.DELIMITADOR_DATA) );
	}
	
	/**
	 * Testa se uma nova dataFim esta sendo colocada corretamente.
	 */
	@Test
	public void testnovaDataFimIntervalo(){
		assertEquals("10/10/2009-10/10/2010", tm.novaDataFimIntervalo("10/10/2009-11/11/2011", "10/10/2010") );
	}
	
	/**
	 * Testa se uma novo dia anterior esta sendo retornado
	 */
	@Test
	public void testDiaAnterior(){
		//Ano anterior
		assertEquals("31/12/2009", tm.diaAnterior("01/01/2010"));
		//Ano bissexto
		assertEquals("29/02/2004", tm.diaAnterior("01/03/2004"));
		assertEquals("28/02/2009", tm.diaAnterior("01/03/2009"));
		//Outros testes
		assertEquals("31/03/2008", tm.diaAnterior("01/04/2008"));
		assertEquals("30/11/2008", tm.diaAnterior("01/12/2008"));
		assertEquals("29/03/2008", tm.diaAnterior("30/03/2008"));
		assertEquals("15/10/2008", tm.diaAnterior("16/10/2008"));
		assertEquals("30/11/2008", tm.diaAnterior("31/11/2008"));
	}
}
