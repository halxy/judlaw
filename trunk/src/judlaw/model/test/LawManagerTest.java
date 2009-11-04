package judlaw.model.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import judlaw.model.bean.lei.ElementoNorma;
import judlaw.model.manager.LawManager;
import judlaw.model.ref.Referencia;
import judlaw.model.util.Constantes;

import org.junit.Before;
import org.junit.Test;

/**
 * Classe de Testes para a classe LawManager
 * @author Halley Freitas
 *
 */
public class LawManagerTest {

	// DBManager
	private LawManager lawManager = LawManager.getInstance();
	
	//ElementoNorma
	private ElementoNorma inciso;
	List<Referencia> referencias1, referencias2;
	
	@Before public void setUp(){
		// Setando as propriedades dos elementos da norma
		
		/* -------------------- INCISO -------------------- */
		inciso = new ElementoNorma();
		inciso.setIdentificadorUnico("cp_art120_par2_inc1");
		inciso.setTipo(Constantes.INCISO);
		inciso.setTexto("Texto do inciso");
		inciso.setDataPublicacao("28/10/2009");
		inciso.setVigencia("28/10/2009-99/99/9999");		
	}
	/**
	 * Test method for {@link judlaw.model.bean.lei.ElementoNorma#getPai}.
	 * @throws Exception 
	 */
	@Test public void testGetPai() {
		ElementoNorma ele1 = new ElementoNorma();
		ele1.setIdentificadorUnico("cp_art120_par1");
		assertEquals("cp_art120", ele1.getPai());
		assertEquals("cp_art120", ele1.getPai());
	}
	
	/**
	 * Test method for {@link judlaw.model.manager.LawManager#saveElementoNorma}. 
	 */
	@Test public void testSaveElementoNorma() {
		// verifica se a lista esta vazia antes
		lawManager.removeTodosElementosNorma();
		List<ElementoNorma> elementos = new ArrayList<ElementoNorma>();
		elementos = lawManager.getTodosElementosNorma();
		assertEquals(0, elementos.size());
		
		// persiste o elemento
		lawManager.saveElementoNorma( inciso );
		
		// verifica a nova lista
		elementos = lawManager.getTodosElementosNorma();
		assertEquals(1, elementos.size());
		
		// verificando o elemento recuperado do BD
		ElementoNorma elementoBD = elementos.get(0);
		assertEquals(elementoBD.getIdentificadorUnico(), inciso.getIdentificadorUnico());
	}
	
	/**
	 * Test method for {@link judlaw.model.manager.LawManager#recuperaElementoPorAtributo(java.lang.String, java.lang.String)}. 
	 */
	@Test public void testRecuperaElementoPorAtributo() {
		// verifica se a lista esta vazia antes
		lawManager.removeTodosElementosNorma();
		List<ElementoNorma> elementos = new ArrayList<ElementoNorma>();
		elementos = lawManager.getTodosElementosNorma();
		assertEquals(0, elementos.size());
		
		// persiste o elemento
		lawManager.saveElementoNorma( inciso );
		
		// procura o elemento cujo identificador unico seja "cp_art120_par2_inc1"
		ElementoNorma elemento = (ElementoNorma) lawManager.recuperaElementoPorAtributo
													("identificadorUnico","cp_art120_par2_inc1").get(0);
		assertEquals(elemento.getPai(), inciso.getPai());
	}
}
