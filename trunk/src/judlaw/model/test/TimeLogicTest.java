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
	
	@Before public void setUp(){
		data1 = "12/10/2009";
		data2 = "12/10/2010";
		data3 = "13/10/2009";
		data4 = "13/11/2009";
		data5 = "13/11/2009";
	}

	/**
	 * Test method for {@link judlaw.model.logic.TimeLogic#dataMaisAtual(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testDataMaisAtual() throws Exception {
		assertEquals(1, tm.dataMaisAtual(data2, data1, "/"));
		assertEquals(-1, tm.dataMaisAtual(data3, data4, "/"));
		assertEquals(0, tm.dataMaisAtual(data4, data5, "/"));
	}
}
