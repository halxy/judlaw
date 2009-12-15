package judlaw.model.test.logic;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import judlaw.model.bean.law.Norma;
import judlaw.model.logic.time.LawTimeLogic;

import org.junit.Test;

public class LawTimeLogicTest {

	private LawTimeLogic lawTimeLogic = LawTimeLogic.getInstance();
	
	@Test
	public void testNormaMaisAtual() {
		/*
		 * Normas
		 */
		Norma norma1 = new Norma();
		norma1.setIdentificadorUnico("norma1");
		norma1.setDataPublicacao("10/10/2009");	
		Norma norma2 = new Norma();
		norma2.setIdentificadorUnico("norma2");
		norma2.setDataPublicacao("11/10/2009");	
		Norma norma3 = new Norma();
		norma3.setIdentificadorUnico("norma3");
		norma3.setDataPublicacao("12/10/2009");	
		Norma norma4 = new Norma();
		norma4.setIdentificadorUnico("norma4");
		norma4.setDataPublicacao("13/10/2009");
		/*
		 * Lista
		 */
		ArrayList<Norma> normas = new ArrayList<Norma>();
		normas.add(norma1);
		normas.add(norma2);
		normas.add(norma3);
		normas.add(norma4);
		
		try {
			assertEquals( "norma4", lawTimeLogic.normaMaisAtual(normas).getIdentificadorUnico());
		} catch (Exception e) {
			e.printStackTrace();
		}
		//Atualizando a lista
		normas.clear();
		norma1.setDataPublicacao("10/10/2009");	
		norma2.setDataPublicacao("10/11/2009");
		normas.add(norma1);
		normas.add(norma2);
		try {
			assertEquals( "norma2", lawTimeLogic.normaMaisAtual(normas).getIdentificadorUnico());
		} catch (Exception e) {
			e.printStackTrace();
		}
		//Atualizando a lista
		normas.clear();
		norma1.setDataPublicacao("10/10/2010");	
		norma2.setDataPublicacao("10/11/2009");
		normas.add(norma1);
		normas.add(norma2);
		try {
			assertEquals( "norma1", lawTimeLogic.normaMaisAtual(normas).getIdentificadorUnico());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
