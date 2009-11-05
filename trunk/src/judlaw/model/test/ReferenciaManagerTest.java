/**
 * 
 */
package judlaw.model.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import judlaw.model.bean.ref.Alteracao;
import judlaw.model.manager.ReferenciaManager;
import judlaw.model.util.Constantes;

import org.junit.Before;
import org.junit.Test;

/**
 * @author halley
 *
 */
public class ReferenciaManagerTest {
	
	ReferenciaManager refManager = ReferenciaManager.getInstance();

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/* ------------------------------------------------------------------ */
    /* -------------------- OPERACOES ALTERACAO ------------------------- */
    /* ------------------------------------------------------------------ */
	/**
	 * Test method for {@link judlaw.model.manager.ReferenciaManager#salvaAlteracao(judlaw.model.bean.ref.Alteracao)}.
	 */
	@Test
	public void testSalvaAlteracao() {
		// esvazia lista de alteracoes
		refManager.removeAlteracoes();
		List<Alteracao> alteracoes = new ArrayList<Alteracao>();
		alteracoes = refManager.getAlteracoes();
		assertEquals(0, alteracoes.size());
		
		// cria e persiste alteracao
		Alteracao alteracao1 = new Alteracao("origem1", "destino1", "18/11/2009", 
											 Constantes.INCLUSAO, Constantes.MAIS_RESTRITIVA);
		refManager.salvaAlteracao(alteracao1);
		
		// verifica a nova lista
		alteracoes = refManager.getAlteracoes();
		assertEquals(1, alteracoes.size());
		
		// verificando a alteracao recuperada do BD
		Alteracao alteracaoBD = alteracoes.get(0);
		assertEquals( alteracao1.getOrigem(), alteracaoBD.getOrigem() );
		assertEquals( alteracao1.getDestino(), alteracaoBD.getDestino() );
		assertEquals( alteracao1.getData(), alteracaoBD.getData() );
		assertEquals( alteracao1.getTipo(), alteracaoBD.getTipo() );
		assertEquals( alteracao1.getCaracteristica(), alteracaoBD.getCaracteristica() );
	}

	/**
	 * Test method for {@link judlaw.model.manager.ReferenciaManager#getAlteracoes()}.
	 */
	@Test
	public void testGetAlteracoes() {
	//	fail("Not yet implemented");
	}

	/**
	 * Test method for {@link judlaw.model.manager.ReferenciaManager#removeAlteracoes()}.
	 */
	@Test
	public void testRemoveAlteracoes() {
	//	fail("Not yet implemented");
	}

	/**
	 * Test method for {@link judlaw.model.manager.ReferenciaManager#recuperaAlteracaoPorAtributo(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testRecuperaAlteracaoPorAtributo() {
	//	fail("Not yet implemented");
	}

	/* ------------------------------------------------------------------ */
    /* -------------------- OPERACOES CITACAODOCJUD --------------------- */
    /* ------------------------------------------------------------------ */
	/**
	 * Test method for {@link judlaw.model.manager.ReferenciaManager#salvaCitacaoDocJud(judlaw.model.bean.ref.CitacaoDocJud)}.
	 */
	@Test
	public void testSalvaCitacaoDocJud() {
	//	fail("Not yet implemented");
	}

	/**
	 * Test method for {@link judlaw.model.manager.ReferenciaManager#getCitacoesDocJud()}.
	 */
	@Test
	public void testGetCitacoesDocJud() {
	//	fail("Not yet implemented");
	}

	/**
	 * Test method for {@link judlaw.model.manager.ReferenciaManager#removeCitacoesDocJud()}.
	 */
	@Test
	public void testRemoveCitacoesDocJud() {
	//	fail("Not yet implemented");
	}

	/**
	 * Test method for {@link judlaw.model.manager.ReferenciaManager#recuperaCitacaoDocJudPorAtributo(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testRecuperaCitacaoDocJudPorAtributo() {
	//	fail("Not yet implemented");
	}

	/* ------------------------------------------------------------------ */
    /* -------------------- OPERACOES CITACAODOCLEG --------------------- */
    /* ------------------------------------------------------------------ */
	/**
	 * Test method for {@link judlaw.model.manager.ReferenciaManager#salvaCitacaoDocLeg(judlaw.model.bean.ref.CitacaoDocLeg)}.
	 */
	@Test
	public void testSalvaCitacaoDocLeg() {
	//	fail("Not yet implemented");
	}

	/**
	 * Test method for {@link judlaw.model.manager.ReferenciaManager#getCitacoesDocLeg()}.
	 */
	@Test
	public void testGetCitacoesDocLeg() {
	//	fail("Not yet implemented");
	}

	/**
	 * Test method for {@link judlaw.model.manager.ReferenciaManager#removeCitacoesDocLeg()}.
	 */
	@Test
	public void testRemoveCitacoesDocLeg() {
	//	fail("Not yet implemented");
	}

	/**
	 * Test method for {@link judlaw.model.manager.ReferenciaManager#recuperaCitacaoDocLegPorAtributo(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testRecuperaCitacaoDocLegPorAtributo() {
	//	fail("Not yet implemented");
	}

}
