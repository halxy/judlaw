/**
 * 
 */
package judlaw.model.test;

import static org.junit.Assert.assertEquals;
import judlaw.model.bean.lei.Norma;
import judlaw.model.dbmanager.DocJudManager;
import judlaw.model.dbmanager.LawManager;
import judlaw.model.dbmanager.ReferenciaManager;

import org.junit.Before;
import org.junit.Test;

/**
 * @author halley
 *
 */
public class ReferenciaManagerTest {
	
	ReferenciaManager refManager = ReferenciaManager.getInstance();
	LawManager lawManager = LawManager.getInstance();
	DocJudManager docJudManager = DocJudManager.getInstance();
	
	@Before
	public void setUp() {
		/* ---------- Esvazia as listas envolvidas ----------*/
		lawManager.removeNormas();
		lawManager.removeElementosNorma();
		docJudManager.removeDocumentosJuridicos();
		
	}

	/**
	 * Testa se as alteracoes estao sendo salvas corretamente
	 */
	@Test
	public void testSalvaAlteracao(){
		/* Criando e persistindo as normas */
		Norma norma1 = new Norma("epigrafeN1", "ementaN1", "autoriaN1", "localN1", "identificadorUnicoN1", "tipoN1", 
				"dataPublicacaoN1", "vigenciaN1");
		
		Norma norma2 = new Norma("epigrafeN2", "ementaN2", "autoriaN2", "localN2", "identificadorUnicoN2", "tipoN2", 
				"dataPublicacaoN2", "vigenciaN2");
		
		lawManager.salvaNorma(norma1);
		lawManager.salvaNorma(norma2);
		//Quantidade de normas
		assertEquals(2, lawManager.getNormas().size() );
		
		/* Criando a alteracao */
		refManager.criaAlteracao(norma1, norma2, "16/11/2009", "inclusao", "");
		
		/* Verificando se os atributos foram persistidos corretamente */
		//Quantidade de alteracoes
		assertEquals(1, refManager.getAlteracoes().size() );
		
		// Atributos da Referencia
		assertEquals( refManager.getAlteracoes().get(0).getNormaOrigem().getIdentificadorUnico(),
				      norma1.getIdentificadorUnico() );
		assertEquals( refManager.getAlteracoes().get(0).getNormaDestino().getIdentificadorUnico(),
			      	  norma2.getIdentificadorUnico() );
		assertEquals( refManager.getAlteracoes().get(0).getElementoNormaOrigem(),
			          null );
		assertEquals( refManager.getAlteracoes().get(0).getElementoNormaDestino(),
		          null );
		
		// Atributos das normas
		assertEquals( lawManager.getNormas().get(0).getIdentificadorUnico(),
					  norma1.getIdentificadorUnico());
		assertEquals( lawManager.getNormas().get(1).getIdentificadorUnico(),
				      norma2.getIdentificadorUnico());
	}
}
