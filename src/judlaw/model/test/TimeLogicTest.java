package judlaw.model.test;

import static org.junit.Assert.assertEquals;
import judlaw.model.logic.TimeLogic;

import org.junit.Before;
import org.junit.Test;

/**
 * Classe para testes da Classe TimeManager
 * @author Halley Freitas
 *
 */
public class TimeLogicTest {
	TimeLogic tm = TimeLogic.getInstance();
	String data1, data2, data3, data4, data5;
	String vigencia1;
	
	@Before public void setUp(){
		data1 = "12/10/2009";
		data2 = "12/10/2010";
		data3 = "13/10/2009";
		data4 = "13/11/2009";
		data5 = "13/11/2009";
		vigencia1 = "12/10/2009-13/10/2009";
	}

	/**
	 * Test method for {@link judlaw.model.logic.TimeLogic#comparaDatas(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testDataMaisAtual() throws Exception {
		assertEquals(1, tm.comparaDatas(data2, data1, "/"));
		assertEquals(-1, tm.comparaDatas(data3, data4, "/"));
		assertEquals(0, tm.comparaDatas(data4, data5, "/"));
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void comparaVigenciaComData() throws Exception {
		// Vigencia dentro do prazo de validade
		assertEquals(1, tm.comparaVigenciaComData(vigencia1, "-", data1, "/") );
		assertEquals(0, tm.comparaVigenciaComData(vigencia1, "-", data3, "/") );
		// Vigencia expirada
		assertEquals(-1, tm.comparaVigenciaComData(vigencia1, "-", data2, "/") );
		assertEquals(-1, tm.comparaVigenciaComData(vigencia1, "-", data4, "/") );
		assertEquals(-1, tm.comparaVigenciaComData(vigencia1, "-", data5, "/") );
	}
}
