/**
 * 
 */
package judlaw.model.test;

import judlaw.model.dbmanager.ReferenciaManager;

import org.junit.Before;
import org.junit.Test;

/**
 * @author halley
 *
 */
public class ReferenciaManagerTest {
	
	ReferenciaManager refManager = ReferenciaManager.getInstance();
	
	@Before
	public void setUp() {
		/* ---------- Esvazia a lista de Referencias ----------*/
		refManager.removeAlteracoes();
	}

	/**
	 * Testa se as alteracoes estao sendo salvas corretamente
	 */
	@Test
	public void testSalvaAlteracao(){
		
	}
}
