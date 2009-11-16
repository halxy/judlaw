package judlaw.model.test;

import static org.junit.Assert.assertEquals;
import judlaw.model.bean.lei.ElementoNorma;
import judlaw.model.bean.lei.Norma;
import judlaw.model.dbmanager.LawManager;

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
	
	@Before
	public void setUp() {
		/* ---------- Esvazia a lista de Normas e ElementosNorma ----------*/
		lawManager.removeNormas();
		lawManager.removeElementosNorma();
	}
	
	/**
	 * Teste que verifica se uma norma esta sendo salva corretamente
	 */
	@Test
	public void testSalvaNorma(){
		/* ---------- Verifica se as listas estao vazias ----------*/
		assertEquals( 0, lawManager.getNormas().size() );
		assertEquals( 0, lawManager.getElementosNorma().size() );
		
		/* ---------- Elementos da Norma ----------*/
		ElementoNorma artigo1 = new ElementoNorma("textoArt1", "identificadorUnicoArt1", "tipoArt1", 
														"dataPublicacaoArt1", "vigenciaArt1");
		ElementoNorma paragrafo1 = new ElementoNorma("textoParagrafo1", "identificadorUnicoParagrafo1", "tipoParagrafo1", 
				"dataPublicacaoParagrafo1", "vigenciaParagrafo1");
		ElementoNorma inciso1 = new ElementoNorma("textoInciso1", "identificadorUnicoInciso1", "tipoInciso1", 
				"dataPublicacaoInciso1", "vigenciaInciso1");
		ElementoNorma inciso2 = new ElementoNorma("textoInciso2", "identificadorUnicoInciso2", "tipoInciso2", 
				"dataPublicacaoInciso2", "vigenciaInciso2");
		
		paragrafo1.getElementosNorma().add(inciso1);
		paragrafo1.getElementosNorma().add(inciso2);
		artigo1.getElementosNorma().add(paragrafo1);
		
		ElementoNorma artigo2 = new ElementoNorma("textoArt2", "identificadorUnicoArt2", "tipoArt2", 
				"dataPublicacaoArt2", "vigenciaArt2");
		ElementoNorma paragrafo2 = new ElementoNorma("textoParagrafo2", "identificadorUnicoParagrafo2", "tipoParagrafo2", 
				"dataPublicacaoParagrafo2", "vigenciaParagrafo2");
		ElementoNorma paragrafo3 = new ElementoNorma("textoParagrafo3", "identificadorUnicoParagrafo3", "tipoParagrafo3", 
				"dataPublicacaoParagrafo3", "vigenciaParagrafo3");
		artigo2.getElementosNorma().add(paragrafo2);
		artigo2.getElementosNorma().add(paragrafo3);
		
		Norma norma1 = new Norma("epigrafeN1", "ementaN1", "autoriaN1", "localN1", "identificadorUnicoN1", "tipoN1", 
								"dataPublicacaoN1", "vigenciaN1");
		norma1.getElementosNorma().add(artigo1);
		norma1.getElementosNorma().add(artigo2);
		lawManager.salvaNorma(norma1);
		
		/*
		 *                         Norma1
		 *                         /    \
		 *                       Art1   Art2
		 *                       /      /  \
		 *                      Par1  Par2  Par3
		 *                     /   \
		 *                  Inc1   Inc2 
		 */
		/* ---------- Verifica as cardinalidade das tabelas dos elementos envolvidos ----------*/
		//Quantidade de normas
		assertEquals(1, lawManager.getNormas().size() );
		//Quantidade de ElementosNorma da Norma
		assertEquals(2, lawManager.getNormas().get(0).getElementosNorma().size() );
		//Quantidade de ElementosNorma
		assertEquals(7, lawManager.getElementosNorma().size() );
		//Quantidade filhos de Par1
		assertEquals(2, lawManager.getNormas().get(0).getElementosNorma().get(0)
													  .getElementosNorma().get(0)
													  .getElementosNorma().size());
		
		//Quantidade filhos de Art2
		assertEquals(2, lawManager.getNormas().get(0).getElementosNorma().get(1)
													  .getElementosNorma().size()); 
		
		/* ---------- Verifica se os atributos e elementos foram persistidos corretamente ----------*/
		Norma normaBD = lawManager.getNormas().get(0);

		//Atributo identificadorUnico
		assertEquals( normaBD.getIdentificadorUnico(), norma1.getIdentificadorUnico() );
		
		//ARTIGO1
		assertEquals( normaBD.getElementosNorma().get(0).getIdentificadorUnico(), 
					  norma1.getElementosNorma().get(0).getIdentificadorUnico() );
		//Relacao Bidirecional
		assertEquals( normaBD.getElementosNorma().get(0).getNormaPai().getIdentificadorUnico(), 
					  norma1.getElementosNorma().get(0).getNormaPai().getIdentificadorUnico() );
		
		//INCISO1 e INCISO2
		assertEquals( normaBD.getElementosNorma().get(0).getElementosNorma().get(0).getElementosNorma().get(0).getTexto(), 
					  norma1.getElementosNorma().get(0).getElementosNorma().get(0).getElementosNorma().get(0).getTexto() );
		assertEquals( normaBD.getElementosNorma().get(0).getElementosNorma().get(0).getElementosNorma().get(1).getTexto(), 
				      norma1.getElementosNorma().get(0).getElementosNorma().get(0).getElementosNorma().get(1).getTexto() );
		//Relacao Bidirecional
		assertEquals( normaBD.getElementosNorma().get(0).getElementosNorma().get(0).getElementosNorma().get(0).getElementoNormaPai().getIdentificadorUnico(), 
				      norma1.getElementosNorma().get(0).getElementosNorma().get(0).getElementosNorma().get(0).getElementoNormaPai().getIdentificadorUnico() );
		assertEquals( normaBD.getElementosNorma().get(0).getElementosNorma().get(0).getElementosNorma().get(1).getElementoNormaPai().getIdentificadorUnico(), 
					  norma1.getElementosNorma().get(0).getElementosNorma().get(0).getElementosNorma().get(1).getElementoNormaPai().getIdentificadorUnico() );
	}
	
	/**
	 * Testa se uma norma esta sendo removida corretamente, incluindo seus ElementosNorma.
	 */
	@Test
	public void testRemoveNorma(){
		/* ---------- Verifica se as listas estao vazias ----------*/
		assertEquals( 0, lawManager.getNormas().size() );
		assertEquals( 0, lawManager.getElementosNorma().size() );
		
		/* ---------- Elementos da Norma ----------*/
		ElementoNorma artigo1 = new ElementoNorma("textoArt1", "identificadorUnicoArt1", "tipoArt1", 
														"dataPublicacaoArt1", "vigenciaArt1");
		ElementoNorma paragrafo1 = new ElementoNorma("textoParagrafo1", "identificadorUnicoParagrafo1", "tipoParagrafo1", 
				"dataPublicacaoParagrafo1", "vigenciaParagrafo1");
		ElementoNorma inciso1 = new ElementoNorma("textoInciso1", "identificadorUnicoInciso1", "tipoInciso1", 
				"dataPublicacaoInciso1", "vigenciaInciso1");
		ElementoNorma inciso2 = new ElementoNorma("textoInciso2", "identificadorUnicoInciso2", "tipoInciso2", 
				"dataPublicacaoInciso2", "vigenciaInciso2");
		
		paragrafo1.getElementosNorma().add(inciso1);
		paragrafo1.getElementosNorma().add(inciso2);
		artigo1.getElementosNorma().add(paragrafo1);
		
		ElementoNorma artigo2 = new ElementoNorma("textoArt2", "identificadorUnicoArt2", "tipoArt2", 
				"dataPublicacaoArt2", "vigenciaArt2");
		ElementoNorma paragrafo2 = new ElementoNorma("textoParagrafo2", "identificadorUnicoParagrafo2", "tipoParagrafo2", 
				"dataPublicacaoParagrafo2", "vigenciaParagrafo2");
		ElementoNorma paragrafo3 = new ElementoNorma("textoParagrafo3", "identificadorUnicoParagrafo3", "tipoParagrafo3", 
				"dataPublicacaoParagrafo3", "vigenciaParagrafo3");
		artigo2.getElementosNorma().add(paragrafo2);
		artigo2.getElementosNorma().add(paragrafo3);
		
		Norma norma1 = new Norma("epigrafeN1", "ementaN1", "autoriaN1", "localN1", "identificadorUnicoN1", "tipoN1", 
								"dataPublicacaoN1", "vigenciaN1");
		norma1.getElementosNorma().add(artigo1);
		norma1.getElementosNorma().add(artigo2);
		lawManager.salvaNorma(norma1);
		
		/*
		 *                         Norma1
		 *                         /    \
		 *                       Art1   Art2
		 *                       /      /  \
		 *                      Par1  Par2  Par3
		 *                     /   \
		 *                  Inc1   Inc2 
		 */
		/* ---------- Verifica as cardinalidade das tabelas dos elementos envolvidos ----------*/
		//Quantidade de normas
		assertEquals(1, lawManager.getNormas().size() );
		//Quantidade de ElementosNorma
		assertEquals(7, lawManager.getElementosNorma().size() );

		/* ---------- Removendo o ElementoNorma ----------*/
		lawManager.removeNormas();
		
		/* ---------- Verifica novamente as cardinalidade das tabelas ----------*/
		//Quantidade de normas
		assertEquals(0, lawManager.getNormas().size() );
		//Quantidade de ElementosNorma
		assertEquals(0, lawManager.getElementosNorma().size() );
	}
	
	/**
	 * Teste que verifica a alteracao de um ElementoNorma
	 */
	@Test
	public void testAlteraElementoNorma() {
		/* ---------- Verifica se as listas estao vazias ----------*/
		assertEquals( 0, lawManager.getNormas().size() );
		assertEquals( 0, lawManager.getElementosNorma().size() );
		
		/* ---------- Elementos da Norma ----------*/
		ElementoNorma artigo1 = new ElementoNorma("textoArt1", "identificadorUnicoArt1", "tipoArt1", 
														"dataPublicacaoArt1", "vigenciaArt1");
		ElementoNorma paragrafo1 = new ElementoNorma("textoParagrafo1", "identificadorUnicoParagrafo1", "tipoParagrafo1", 
				"dataPublicacaoParagrafo1", "vigenciaParagrafo1");
		ElementoNorma inciso1 = new ElementoNorma("textoInciso1", "identificadorUnicoInciso1", "tipoInciso1", 
				"dataPublicacaoInciso1", "vigenciaInciso1");
		ElementoNorma inciso2 = new ElementoNorma("textoInciso2", "identificadorUnicoInciso2", "tipoInciso2", 
				"dataPublicacaoInciso2", "vigenciaInciso2");
		
		paragrafo1.getElementosNorma().add(inciso1);
		paragrafo1.getElementosNorma().add(inciso2);
		artigo1.getElementosNorma().add(paragrafo1);
		
		Norma norma1 = new Norma("epigrafeN1", "ementaN1", "autoriaN1", "localN1", "identificadorUnicoN1", "tipoN1", 
								"dataPublicacaoN1", "vigenciaN1");
		norma1.getElementosNorma().add(artigo1);
		lawManager.salvaNorma(norma1);
		
		/*
		 *                         Norma1
		 *                         /    \
		 *                       Art1   Art2
		 *                       /      /  \
		 *                      Par1  Par2  Par3
		 *                     /   \
		 *                  Inc1   Inc2 
		 */
		/* ---------- Verifica as cardinalidade das tabelas dos elementos envolvidos ----------*/
		//Quantidade de normas
		assertEquals(1, lawManager.getNormas().size() );
		//Quantidade de ElementosNorma
		assertEquals(4, lawManager.getElementosNorma().size() );
		
		/* ---------- Adicionando um ElementoNorma ----------*/
		ElementoNorma artigo2 = new ElementoNorma("textoArt2", "identificadorUnicoArt2", "tipoArt2", 
				"dataPublicacaoArt2", "vigenciaArt2");
		ElementoNorma paragrafo2 = new ElementoNorma("textoParagrafo2", "identificadorUnicoParagrafo2", "tipoParagrafo2", 
				"dataPublicacaoParagrafo2", "vigenciaParagrafo2");
		ElementoNorma paragrafo3 = new ElementoNorma("textoParagrafo3", "identificadorUnicoParagrafo3", "tipoParagrafo3", 
				"dataPublicacaoParagrafo3", "vigenciaParagrafo3");
		artigo2.getElementosNorma().add(paragrafo2);
		artigo2.getElementosNorma().add(paragrafo3);
		
		lawManager.adicionaElementoNorma(artigo2, lawManager.getNormas().get(0));
		
		/* ---------- Verifica as cardinalidade das tabelas dos elementos envolvidos ----------*/
		//Quantidade de normas
		assertEquals(1, lawManager.getNormas().size() );
		//Quantidade de ElementosNorma
		assertEquals(7, lawManager.getElementosNorma().size() );
		assertEquals(2, lawManager.getNormas().get(0).getElementosNorma().size());
		
		/* ---------- Verifica se os atributos e elementos foram persistidos corretamente ----------*/
		Norma normaBD = lawManager.getNormas().get(0);
		
		//ARTIGO2
		/*
		 * Obs: por algum motivo obscuro, ao adicionar o novo elemento, ele vai para a posicao inicial
		 * da lista, dai a comparacao com getElementosNorma().get(0).
		 */
		assertEquals( artigo2.getIdentificadorUnico(),
					  normaBD.getElementosNorma().get(0).getIdentificadorUnico());
		//Relacao Bidirecional
		assertEquals( normaBD.getElementosNorma().get(0).getNormaPai().getIdentificadorUnico(),
					  norma1.getIdentificadorUnico() );
		
		//PARAGRAFO2 e PARAGRAFO3
		assertEquals( normaBD.getElementosNorma().get(0).getElementosNorma().get(0).getTexto(), 
					  paragrafo2.getTexto() );
		assertEquals( normaBD.getElementosNorma().get(0).getElementosNorma().get(1).getTexto(), 
				      paragrafo3.getTexto() );
//		//Relacao Bidirecional
//		assertEquals( normaBD.getElementosNorma().get(0).getElementosNorma().get(0).getElementosNorma().get(0).getElementoNormaPai().getIdentificadorUnico(), 
//				      norma1.getElementosNorma().get(0).getElementosNorma().get(0).getElementosNorma().get(0).getElementoNormaPai().getIdentificadorUnico() );
//		assertEquals( normaBD.getElementosNorma().get(0).getElementosNorma().get(0).getElementosNorma().get(1).getElementoNormaPai().getIdentificadorUnico(), 
//					  norma1.getElementosNorma().get(0).getElementosNorma().get(0).getElementosNorma().get(1).getElementoNormaPai().getIdentificadorUnico() );
	}
}
