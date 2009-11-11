package judlaw.model.test;

import static org.junit.Assert.assertEquals;
import judlaw.model.bean.lei.ElementoNorma;
import judlaw.model.bean.lei.Norma;
import judlaw.model.manager.LawManager;

import org.junit.Test;

/**
 * Classe de Testes para a classe LawManager
 * @author Halley Freitas
 *
 */
public class LawManagerTest {

	// DBManager
	private LawManager lawManager = LawManager.getInstance();
	
	@Test
	public void testSalvaNorma() {
		/* ---------- Esvazia a lista de Normas ----------*/
		lawManager.removeNormas();
		assertEquals( 0, lawManager.getNormas().size() );
		lawManager.removeElementosNorma();
		assertEquals( 0, lawManager.getElementosNorma().size() );
		
		/* ---------- Elementos da Norma ----------*/
		ElementoNorma elementoNorma1 = new ElementoNorma("textoEN1", "identificadorUnicoEN1", "tipoEN1", 
														"dataPublicacaoEN1", "vigenciaEN1");
		ElementoNorma elementoNorma2 = new ElementoNorma("textoEN2", "identificadorUnicoEN2", "tipoEN2", 
				"dataPublicacaoEN2", "vigenciaEN2");
		elementoNorma1.getElementosNorma().add(elementoNorma2);
		
		Norma norma1 = new Norma("epigrafeN1", "ementaN1", "autoriaN1", "localN1", "identificadorUnicoN1", "tipoN1", 
								"dataPublicacaoN1", "vigenciaN1");
		norma1.getElementosNorma().add(elementoNorma1);
		lawManager.salvaNorma(norma1);
		
		/* ---------- Verifica as cardinalidade das tabelas dos elementos envolvidos ----------*/
		assertEquals(1, lawManager.getNormas().size() );
		assertEquals(1, lawManager.getNormas().get(0).getElementosNorma().size() );
		assertEquals(2, lawManager.getElementosNorma().size() );
		assertEquals(1, lawManager.getElementosNorma().get(0).getElementosNorma().size() );
		
		/* ---------- Verifica se os atributos e elementos foram persistidos corretamente ----------*/
		Norma normaBD = lawManager.getNormas().get(0);

		//Atributo identificadorUnico
		assertEquals( normaBD.getIdentificadorUnico(), norma1.getIdentificadorUnico() );
		
		//ELEMENTONORMA1
		assertEquals( normaBD.getElementosNorma().get(0).getIdentificadorUnico(), 
					  norma1.getElementosNorma().get(0).getIdentificadorUnico() );
		//Relacao Bidirecional
		assertEquals( normaBD.getElementosNorma().get(0).getNormaPai().getIdentificadorUnico(), 
					  norma1.getElementosNorma().get(0).getNormaPai().getIdentificadorUnico() );
	}
}
