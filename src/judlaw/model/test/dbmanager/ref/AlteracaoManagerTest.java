/* 
 * Universidade Federal de Campina Grande
 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas	
 */
package judlaw.model.test.dbmanager.ref;

import static org.junit.Assert.assertEquals;
import judlaw.model.bean.law.ElementoNorma;
import judlaw.model.bean.law.Norma;
import judlaw.model.bean.ref.Alteracao;
import judlaw.model.dbmanager.docjud.DocJudManager;
import judlaw.model.dbmanager.law.ElementoNormaManager;
import judlaw.model.dbmanager.law.NormaManager;
import judlaw.model.dbmanager.ref.AlteracaoManager;
import judlaw.model.dbmanager.ref.ReferenciaManager;
import judlaw.model.util.Constantes;

import org.junit.Before;
import org.junit.Test;

/**
 * Classe AlteracaoManagerTest - testes das operacoes de alteracoes
 * @author Halley Freitas
 *
 */
public class AlteracaoManagerTest {
	
	private ReferenciaManager refManager = ReferenciaManager.getInstance();
	private AlteracaoManager alteracaoManager = AlteracaoManager.getInstance();
	private NormaManager normaManager = NormaManager.getInstance();
	private ElementoNormaManager elementoNormaManager = ElementoNormaManager.getInstance();
	private DocJudManager docJudManager = DocJudManager.getInstance();
	
	@Before
	public void setUp() {
		/* ---------- Esvazia as listas envolvidas ----------*/
		refManager.removeReferencias();
		normaManager.removeLaw();
//		elementoNormaManager.removeElementosNorma();
		docJudManager.removeDocumentosJuridicos();	
	}
	
	 /* ------------------------------------------------------------------ */
    /* -------------------- TESTE ALTERACAO ------------------------------ */
    /* ------------------------------------------------------------------ */
	
	/**
	 * Teste de persistencia de alteracoes
	 */
	@Test
	public void testSalvaAlteracao(){
		/*
		 *           Norma1               Norma2
		 *             |                    |
		 *        ElementoNorma1      ElementoNorma2
		 */
		/* Criando e persistindo as normas e elementosNorma*/
		Norma norma1 = new Norma("epigrafeN1", "ementaN1", "autoriaN1", "localN1", "identificadorUnicoN1", "tipoN1", 
				"dataPublicacaoN1", "vigenciaN1");
		ElementoNorma artigo1 = new ElementoNorma("textoArt1", "identificadorUnicoArt1", "tipoArt1", 
				"dataPublicacaoArt1", "vigenciaArt1");
		norma1.getElementosNorma().add(artigo1);
		
		Norma norma2 = new Norma("epigrafeN2", "ementaN2", "autoriaN2", "localN2", "identificadorUnicoN2", "tipoN2", 
				"dataPublicacaoN2", "vigenciaN2");
		ElementoNorma artigo2 = new ElementoNorma("textoArt2", "identificadorUnicoArt2", "tipoArt2", 
				"dataPublicacaoArt2", "vigenciaArt2");
		norma2.getElementosNorma().add(artigo2);
		
		normaManager.salvaNorma(norma1);
		normaManager.salvaNorma(norma2);
		/* ---------- Verifica as cardinalidade das tabelas dos elementos envolvidos ----------*/
		assertEquals(2, normaManager.getNormas().size() );
		assertEquals(2, elementoNormaManager.getElementosNorma().size() );
		
		/* Criando as Alteracoes */
		//Norma1 -> Norma2  (N->N)
		alteracaoManager.criaAlteracaoSimples(norma1, norma2, "16/11/2009", "inclusao", "");
		//Norma1 -> Artigo2 (N->EN)
		alteracaoManager.criaAlteracaoSimples(norma1, artigo2, "17/11/2009", "inclusao", "");
		//Artigo1 -> Norma2 (EN->N)
		alteracaoManager.criaAlteracaoSimples(artigo1, norma2, "18/11/2009", "inclusao", "");
		//Artigo1 -> Artigo2 (EN->EN)
		alteracaoManager.criaAlteracaoSimples(artigo1, artigo2, "19/11/2009", "inclusao", "");
		
		/* Verificando se os atributos foram persistidos corretamente */
		//Quantidade de alteracoes
		assertEquals(4, alteracaoManager.getAlteracoes().size() );		
		// Atributos das Referencias
		//Norma1 -> Norma2  (N->N)
		assertEquals( alteracaoManager.getAlteracoes().get(0).getNormaOrigem().getIdentificadorUnico(),
				      norma1.getIdentificadorUnico() );
		assertEquals( alteracaoManager.getAlteracoes().get(0).getNormaDestino().getIdentificadorUnico(),
			      	  norma2.getIdentificadorUnico() );
		//Artigo1 -> Norma2 (EN->N)
		assertEquals( alteracaoManager.getAlteracoes().get(1).getElementoNormaOrigem().getIdentificadorUnico(),
			      	  artigo1.getIdentificadorUnico() );
		assertEquals( alteracaoManager.getAlteracoes().get(1).getNormaDestino().getIdentificadorUnico(),
		      	      norma2.getIdentificadorUnico() );
		//Norma1 -> Artigo2 (N->EN)
		assertEquals( alteracaoManager.getAlteracoes().get(2).getNormaOrigem().getIdentificadorUnico(),
			      	  norma1.getIdentificadorUnico() );
		assertEquals( alteracaoManager.getAlteracoes().get(2).getElementoNormaDestino().getIdentificadorUnico(),
		      	      artigo2.getIdentificadorUnico() );
		//Artigo1 -> Artigo2 (EN->EN)
		assertEquals( alteracaoManager.getAlteracoes().get(3).getElementoNormaOrigem().getIdentificadorUnico(),
			      	  artigo1.getIdentificadorUnico() );
		assertEquals( alteracaoManager.getAlteracoes().get(3).getElementoNormaDestino().getIdentificadorUnico(),
		      	      artigo2.getIdentificadorUnico() );
		
		/*
		 * Atributos das Normas e ElementosNormas
		 */
		//NORMA1
		assertEquals( normaManager.getNormas().get(0).getIdentificadorUnico(),
					  norma1.getIdentificadorUnico());
		assertEquals( normaManager.getNormas().get(0).getAlteracoesFeitas().get(0).getData(),
				  	  "16/11/2009");
		//NORMA2
		assertEquals( normaManager.getNormas().get(1).getIdentificadorUnico(),
				      norma2.getIdentificadorUnico());
		assertEquals( normaManager.getNormas().get(1).getAlteracoesRecebidas().get(0).getData(),
					  "16/11/2009");
		assertEquals( normaManager.getNormas().get(1).getAlteracoesRecebidas().get(1).getData(),
		  			  "18/11/2009");
		//ELEMENTONORMA1
		assertEquals( elementoNormaManager.getElementosNorma().get(0).getIdentificadorUnico(),
					  artigo1.getIdentificadorUnico());
		assertEquals( elementoNormaManager.getElementosNorma().get(0).getAlteracoesFeitas().get(0).getData(),
				  	  "18/11/2009");
		assertEquals( elementoNormaManager.getElementosNorma().get(0).getAlteracoesFeitas().get(1).getData(),
	  	  			  "19/11/2009");
	}
	/**
	 * Testa se a alteracao esta sendo removida corretamente
	 */
	@Test
	public void testRemoveAlteracao(){
		/*
		 *           Norma1               Norma2
		 *             |                    |
		 *        ElementoNorma1      ElementoNorma2
		 */
		/* Criando e persistindo as normas e elementosNorma*/
		Norma norma1 = new Norma("epigrafeN1", "ementaN1", "autoriaN1", "localN1", "identificadorUnicoN1", "tipoN1", 
				"dataPublicacaoN1", "vigenciaN1");
		ElementoNorma artigo1 = new ElementoNorma("textoArt1", "identificadorUnicoArt1", "tipoArt1", 
				"dataPublicacaoArt1", "vigenciaArt1");
		norma1.getElementosNorma().add(artigo1);
		
		Norma norma2 = new Norma("epigrafeN2", "ementaN2", "autoriaN2", "localN2", "identificadorUnicoN2", "tipoN2", 
				"dataPublicacaoN2", "vigenciaN2");
		ElementoNorma artigo2 = new ElementoNorma("textoArt2", "identificadorUnicoArt2", "tipoArt2", 
				"dataPublicacaoArt2", "vigenciaArt2");
		norma2.getElementosNorma().add(artigo2);
		
		normaManager.salvaNorma(norma1);
		normaManager.salvaNorma(norma2);
		/* ---------- Verifica as cardinalidade das tabelas dos elementos envolvidos ----------*/
		assertEquals(2, normaManager.getNormas().size() );
		assertEquals(2, elementoNormaManager.getElementosNorma().size() );
		
		/* Criando as Alteracoes */
		//Norma1 -> Norma2  (N->N)
		alteracaoManager.criaAlteracaoSimples(norma1, norma2, "16/11/2009", "inclusao", "");
		//Norma1 -> Artigo2 (N->EN)
		alteracaoManager.criaAlteracaoSimples(norma1, artigo2, "17/11/2009", "inclusao", "");
		//Artigo1 -> Norma2 (EN->N)
		alteracaoManager.criaAlteracaoSimples(artigo1, norma2, "18/11/2009", "inclusao", "");
		//Artigo1 -> Artigo2 (EN->EN)
		alteracaoManager.criaAlteracaoSimples(artigo1, artigo2, "19/11/2009", "inclusao", "");
		
		/* Verificando se os atributos foram persistidos corretamente */
		//Quantidade de alteracoes
		assertEquals(4, alteracaoManager.getAlteracoes().size() );
		
		/* ---------- Verifica as cardinalidade das alteracoes ----------*/
		//Norma1
		assertEquals(norma1.getIdentificadorUnico(), normaManager.getNormas().get(0).getIdentificadorUnico() );
		assertEquals(2, normaManager.getNormas().get(0).getAlteracoesFeitas().size() );
		//Norma2
		assertEquals(norma2.getIdentificadorUnico(), normaManager.getNormas().get(1).getIdentificadorUnico() );
		assertEquals(2, normaManager.getNormas().get(1).getAlteracoesRecebidas().size() );
		//Artigo1
		assertEquals(artigo1.getIdentificadorUnico(), elementoNormaManager.getElementosNorma().get(0).getIdentificadorUnico() );
		assertEquals(2, elementoNormaManager.getElementosNorma().get(0).getAlteracoesFeitas().size() );
		//Artigo2
		assertEquals(2, elementoNormaManager.getElementosNorma().get(1).getAlteracoesRecebidas().size() );
		
		/* ---------- Verificando as alteracoes ----------*/
		//Norma1 -> Norma2  (N->N)
		assertEquals("16/11/2009", alteracaoManager.getAlteracoes().get(0).getData() );
		//Artigo1 -> Norma2 (EN->N)
		assertEquals("18/11/2009", alteracaoManager.getAlteracoes().get(1).getData() );
		//Norma1 -> Artigo2 (N->EN)
		assertEquals("17/11/2009", alteracaoManager.getAlteracoes().get(2).getData() );
		//Artigo1 -> Artigo2 (EN->EN)
		assertEquals("19/11/2009", alteracaoManager.getAlteracoes().get(3).getData() );
		
		/* ---------- Removendo as alteracoes ----------*/
		//Norma1 -> Norma2  (N->N)
		alteracaoManager.removeAlteracao( alteracaoManager.getAlteracoes().get(0) );
		assertEquals(3, alteracaoManager.getAlteracoes().size() );
		assertEquals(1, normaManager.getNormas().get(0).getAlteracoesFeitas().size() ); //Norma1
		assertEquals(2, elementoNormaManager.getElementosNorma().get(0).getAlteracoesFeitas().size() ); //Artigo1
		assertEquals(1, normaManager.getNormas().get(1).getAlteracoesRecebidas().size() ); //Norma2
		assertEquals(2, elementoNormaManager.getElementosNorma().get(1).getAlteracoesRecebidas().size() ); //Artigo2
		//Artigo1 -> Norma2 (EN->N)
		alteracaoManager.removeAlteracao( alteracaoManager.getAlteracoes().get(0) );
		assertEquals(2, alteracaoManager.getAlteracoes().size() );
		assertEquals(1, normaManager.getNormas().get(0).getAlteracoesFeitas().size() ); //Norma1
		assertEquals(1, elementoNormaManager.getElementosNorma().get(0).getAlteracoesFeitas().size() ); //ElementoNorma1
		assertEquals(0, normaManager.getNormas().get(1).getAlteracoesRecebidas().size() ); //Norma2
		assertEquals(2, elementoNormaManager.getElementosNorma().get(1).getAlteracoesRecebidas().size() ); //Artigo2
		//Norma1 -> Artigo2 (N->EN)
		alteracaoManager.removeAlteracao( alteracaoManager.getAlteracoes().get(0) );
		assertEquals(1, alteracaoManager.getAlteracoes().size() );
		assertEquals(0, normaManager.getNormas().get(0).getAlteracoesFeitas().size() ); //Norma1
		assertEquals(1, elementoNormaManager.getElementosNorma().get(0).getAlteracoesFeitas().size() ); //ElementoNorma1
		assertEquals(0, normaManager.getNormas().get(1).getAlteracoesRecebidas().size() ); //Norma2
		assertEquals(1, elementoNormaManager.getElementosNorma().get(1).getAlteracoesRecebidas().size() ); //Artigo2
		//Artigo1 -> Artigo2 (EN->EN)
		alteracaoManager.removeAlteracao( alteracaoManager.getAlteracoes().get(0) );
		assertEquals(0, alteracaoManager.getAlteracoes().size() );
		assertEquals(0, normaManager.getNormas().get(0).getAlteracoesFeitas().size() ); //Norma1
		assertEquals(0, elementoNormaManager.getElementosNorma().get(0).getAlteracoesFeitas().size() ); //ElementoNorma1
		assertEquals(0, normaManager.getNormas().get(1).getAlteracoesRecebidas().size() ); //Norma2
		assertEquals(0, elementoNormaManager.getElementosNorma().get(1).getAlteracoesRecebidas().size() ); //Artigo2
	}
	
	/**
	 * Teste que verifica se uma revogacao esta sendo feita corretamente
	 */
	@Test
	public void testCriaAlteracaoRevogacao(){
		/* ---------- Verifica se as listas estao vazias ----------*/
		assertEquals( 0, normaManager.getNormas().size() );
		assertEquals( 0, elementoNormaManager.getElementosNorma().size() );
		
		/* ---------- Norma1 e seus elementos ----------*/
		ElementoNorma artigo1 = new ElementoNorma("textoArt1", "identificadorUnicoArt1", "tipoArt1", 
														"dataPublicacaoArt1", "10/10/2010-99/99/9999");
		ElementoNorma paragrafo1 = new ElementoNorma("textoParagrafo1", "identificadorUnicoParagrafo1", "tipoParagrafo1", 
				"dataPublicacaoParagrafo1", "10/10/2010-99/99/9999");
		ElementoNorma inciso1 = new ElementoNorma("textoInciso1", "identificadorUnicoInciso1", "tipoInciso1", 
				"dataPublicacaoInciso1", "10/10/2010-99/99/9999");
		ElementoNorma inciso2 = new ElementoNorma("textoInciso2", "identificadorUnicoInciso2", "tipoInciso2", 
				"dataPublicacaoInciso2", "10/10/2010-99/99/9999");
		
		paragrafo1.getElementosNorma().add(inciso1);
		paragrafo1.getElementosNorma().add(inciso2);
		artigo1.getElementosNorma().add(paragrafo1);
		
		ElementoNorma artigo2 = new ElementoNorma("textoArt2", "identificadorUnicoArt2", "tipoArt2", 
				"dataPublicacaoArt2", "10/10/2010-99/99/9999");
		ElementoNorma paragrafo2 = new ElementoNorma("textoParagrafo2", "identificadorUnicoParagrafo2", "tipoParagrafo2", 
				"dataPublicacaoParagrafo2", "10/10/2010-99/99/9999");
		ElementoNorma paragrafo3 = new ElementoNorma("textoParagrafo3", "identificadorUnicoParagrafo3", "tipoParagrafo3", 
				"dataPublicacaoParagrafo3", "10/10/2010-99/99/9999");
		artigo2.getElementosNorma().add(paragrafo2);
		artigo2.getElementosNorma().add(paragrafo3);
		
		Norma norma1 = new Norma("epigrafeN1", "ementaN1", "autoriaN1", "localN1", "identificadorUnicoN1", "tipoN1", 
								"dataPublicacaoN1", "10/10/2010-99/99/9999");
		norma1.getElementosNorma().add(artigo1);
		norma1.getElementosNorma().add(artigo2);
		normaManager.salvaNorma(norma1);
		
		/* ---------- Norma2 e seus elementos ----------*/
		ElementoNorma artigo3 = new ElementoNorma("textoArt3", "identificadorUnicoArt3", "tipoArt3", 
				"dataPublicacaoArt3", "10/10/2010-99/99/9999");
		ElementoNorma artigo4 = new ElementoNorma("textoArt4", "identificadorUnicoArt4", "tipoArt4", 
				"dataPublicacaoArt4", "10/10/2010-99/99/9999");
		Norma norma2 = new Norma("epigrafeN2", "ementaN2", "autoriaN2", "localN2", "identificadorUnicoN2", "tipoN2", 
				"dataPublicacaoN2", "10/10/2010-99/99/9999");
		norma2.getElementosNorma().add( artigo3 );
		norma2.getElementosNorma().add( artigo4 );
		normaManager.salvaNorma( norma2 );
		
		/*
		 *                         Norma1                  Norma2
		 *                         /    \                  /    \
		 *                       Art1   Art2             Art3  Art4
		 *                       /      /  \
		 *                      Par1  Par2  Par3
		 *                     /   \
		 *                  Inc1   Inc2 
		 */
		/* ---------- Verifica as cardinalidade das tabelas dos elementos envolvidos ----------*/
		//Quantidade de normas
		assertEquals(2, normaManager.getNormas().size() );
		//Quantidade de ElementosNorma
		assertEquals(9, elementoNormaManager.getElementosNorma().size() );
		
		/* ---------- Verifica as vigencias antes ----------*/
		//Norma1
		Norma norma1BD = (Norma) normaManager.selectNormaPorAtributo("identificadorUnico", norma1.getIdentificadorUnico()).get(0);
		assertEquals(norma1.getIdentificadorUnico(), 
					 norma1BD.getIdentificadorUnico());
		assertEquals("10/10/2010-99/99/9999", norma1BD.getVigencia());
		//Norma2
		Norma norma2BD = (Norma) normaManager.selectNormaPorAtributo("identificadorUnico", norma2.getIdentificadorUnico()).get(0);
		assertEquals(norma2.getIdentificadorUnico(), norma2BD.getIdentificadorUnico());
		assertEquals("10/10/2010-99/99/9999", norma2BD.getVigencia());
		//Artigo1
		ElementoNorma artigo1BD = (ElementoNorma) elementoNormaManager.selectElementoPorAtributo
		                                          ("identificadorUnico", artigo1.getIdentificadorUnico()).get(0);
		assertEquals(artigo1.getIdentificadorUnico(), artigo1BD.getIdentificadorUnico());
		assertEquals("10/10/2010-99/99/9999", artigo1BD.getVigencia());
		//Artigo3
		ElementoNorma artigo3BD = (ElementoNorma) elementoNormaManager.selectElementoPorAtributo
		                                          ("identificadorUnico", artigo3.getIdentificadorUnico()).get(0);
		assertEquals(artigo3.getIdentificadorUnico(), artigo3BD.getIdentificadorUnico());
		assertEquals("10/10/2010-99/99/9999", artigo3BD.getVigencia());
		//Inciso2
		ElementoNorma inciso2BD = (ElementoNorma) elementoNormaManager.selectElementoPorAtributo
		                                          ("identificadorUnico", inciso2.getIdentificadorUnico()).get(0);
		assertEquals(inciso2.getIdentificadorUnico(), inciso2BD.getIdentificadorUnico());
		assertEquals("10/10/2010-99/99/9999", inciso2BD.getVigencia());
		
		/*
		 * Criando as alteracoes complexas
		 * As vigencias das normas e dos elementos eh 10/10/2010-99/99/9999, enquanto a data
		 * da revogacao foi 10/10/2011. Sendo assim, eh esperado que as novas vigencias sejam
		 * 10/10/2010-10/10/2011
		 */
		//NORMA1 Revoga NORMA2;
		alteracaoManager.criaAlteracaoRevogacao(norma1, norma2, "10/10/2011", "");
		/* ---------- Verifica as vigencias depois das alteracoes ----------*/
		//Norma2
		norma2BD = (Norma) normaManager.selectNormaPorAtributo("identificadorUnico", norma2.getIdentificadorUnico()).get(0);
		assertEquals("10/10/2010-10/10/2011", norma2BD.getVigencia());
		//Artigo3
		artigo3BD = (ElementoNorma) elementoNormaManager.selectElementoPorAtributo
        ("identificadorUnico", artigo3.getIdentificadorUnico()).get(0);
		assertEquals("10/10/2010-10/10/2011", artigo3BD.getVigencia());
		
		//NORMA1 Revoga INCISO2;
		alteracaoManager.criaAlteracaoRevogacao(norma1, inciso2, "10/10/2011", "");
		/* ---------- Verifica as vigencias depois das alteracoes ----------*/
		inciso2BD = (ElementoNorma) elementoNormaManager.selectElementoPorAtributo
        								("identificadorUnico", inciso2.getIdentificadorUnico()).get(0);
		assertEquals("10/10/2010-10/10/2011", inciso2BD.getVigencia());
		
		//PARAGRAFO2 Revoga PARAGRAFO3;
		alteracaoManager.criaAlteracaoRevogacao(paragrafo2, paragrafo3, "10/10/2011", "");
		/* ---------- Verifica as vigencias depois das alteracoes ----------*/
		ElementoNorma paragrafo3BD = (ElementoNorma) elementoNormaManager.selectElementoPorAtributo
												("identificadorUnico", paragrafo3.getIdentificadorUnico()).get(0);
		assertEquals("10/10/2010-10/10/2011", paragrafo3BD.getVigencia());
		
		//PARAGRAFO2 Revoga NORMA1;
		alteracaoManager.criaAlteracaoRevogacao(paragrafo2, norma1, "10/10/2011", "");
		/* ---------- Verifica se as alteracoes foram feitas corretamente ----------*/
		//Norma1
		norma1BD = (Norma) normaManager.selectNormaPorAtributo("identificadorUnico", norma1.getIdentificadorUnico()).get(0);
		assertEquals("10/10/2010-10/10/2011", norma1BD.getVigencia());
		//Artigo1
		artigo1BD = (ElementoNorma) elementoNormaManager.selectElementoPorAtributo
									("identificadorUnico", artigo1.getIdentificadorUnico()).get(0);
		assertEquals("10/10/2010-10/10/2011", artigo1BD.getVigencia());
		//Inciso1
		ElementoNorma inciso1BD = (ElementoNorma) elementoNormaManager.selectElementoPorAtributo
								   ("identificadorUnico", inciso1.getIdentificadorUnico()).get(0);
		assertEquals("10/10/2010-10/10/2011", inciso1BD.getVigencia());
		
		/* ---------- Verifica a cardinalidade ----------*/
		assertEquals(4, alteracaoManager.getAlteracoes().size() );
	}
	
	/**
	 * Testa criar uma alteracao do tipo inclusao
	 */
	@Test
	public void testCriaAlteracaoInclusao(){
		/* ---------- Verifica se as listas estao vazias ----------*/
		assertEquals( 0, normaManager.getNormas().size() );
		assertEquals( 0, elementoNormaManager.getElementosNorma().size() );
		assertEquals( 0, alteracaoManager.getAlteracoes().size() );
		
		/* ---------- Criando Norma1 e Norma2 ----------*/
		Norma norma1 = new Norma("epigrafeN1", "ementaN1", "autoriaN1", "localN1", "identificadorUnicoN1", "tipoN1", 
				"dataPublicacaoN1", "10/10/2010-99/99/9999");
		normaManager.salvaNorma(norma1);
		Norma norma2 = new Norma("epigrafeN2", "ementaN2", "autoriaN2", "localN2", "identificadorUnicoN2", "tipoN2", 
				"dataPublicacaoN2", "10/10/2010-99/99/9999");
		normaManager.salvaNorma( norma2 );
				
		/*
		 * SITUACAO ANTES
		 * 
		 *  Norma1                  Norma2
		 */
		
		/* ---------- Incluindo os elementos ----------*/
		//Norma2 INCLUI EM Norma1 O Artigo1
		/*
		 *                         Norma1                  Norma2
		 *                         /    
		 *                       Art1
		 *                       /      
		 *                      Par1              
		 */
		
		/* ---------- AlteracaoInclusao ----------*/
		ElementoNorma artigo1 = new ElementoNorma("textoArt1", "identificadorUnicoArt1", "tipoArt1", 
				"dataPublicacaoArt1", "10/10/2010-99/99/9999");
		ElementoNorma paragrafo1 = new ElementoNorma("textoParagrafo1", "identificadorUnicoParagrafo1", "tipoParagrafo1", 
				"dataPublicacaoParagrafo1", "10/10/2010-99/99/9999");
		artigo1.getElementosNorma().add(paragrafo1);
		alteracaoManager.criaAlteracaoInclusao(norma2, norma1, artigo1, "11/11/2011");
		/* ---------- Verificando a corretude da inclusao ----------*/
		Norma norma1BD = (Norma) normaManager.selectNormaPorAtributo("identificadorUnico", norma1.getIdentificadorUnico()).get(0);
		Alteracao alteracaoBD = alteracaoManager.getAlteracoes().get(0);
		ElementoNorma artigo1BD = norma1BD.getElementosNorma().get(0);
		ElementoNorma paragrafo1BD = artigo1BD.getElementosNorma().get(0);
		//Cardinalidades
		assertEquals( 1, alteracaoManager.getAlteracoes().size() );
		assertEquals( 2, normaManager.getNormas().size() );
		assertEquals( 1, norma1BD.getElementosNorma().size() );
		assertEquals( 1, artigo1BD.getElementosNorma().size() );
		//Atributos
		//Artigo1BD
		assertEquals(artigo1BD.getIdentificadorUnico(), artigo1.getIdentificadorUnico());
		assertEquals(artigo1BD.getNormasPai().get(0).getIdentificadorUnico(), norma1.getIdentificadorUnico());
		assertEquals(artigo1BD.getDataPublicacao(), alteracaoBD.getData());
		//Paragrafo1BD
		assertEquals(paragrafo1BD.getIdentificadorUnico(), paragrafo1.getIdentificadorUnico());
		assertEquals(paragrafo1BD.getElementosNormaPai().get(0).getIdentificadorUnico(), artigo1.getIdentificadorUnico());
		assertEquals(paragrafo1BD.getDataPublicacao(), alteracaoBD.getData());
		//AlteracaoBD
		assertEquals(alteracaoBD.getNormaOrigem().getIdentificadorUnico(),
					 norma2.getIdentificadorUnico());
		assertEquals(alteracaoBD.getElementoNormaDestino().getIdentificadorUnico(),
				     artigo1.getIdentificadorUnico());	
		
		//Artigo1 INCLUI EM Norma2 O Artigo2
		/*
		 *                         Norma1                  Norma2
		 *                         /                        /
		 *                       Art1                      Art2
		 *                       /      
		 *                      Par1              
		 */
		ElementoNorma artigo2 = new ElementoNorma("textoArt2", "identificadorUnicoArt2", "tipoArt2", 
				"dataPublicacaoArt2", "10/10/2010-99/99/9999");
		alteracaoManager.criaAlteracaoInclusao(artigo1, norma2, artigo2, "11/11/2012");
		/* ---------- Verificando a corretude da inclusao ----------*/
		Norma norma2BD = (Norma) normaManager.selectNormaPorAtributo("identificadorUnico", norma2.getIdentificadorUnico()).get(0);
		Alteracao alteracaoBD2 = alteracaoManager.getAlteracoes().get(1);
		ElementoNorma artigo2BD = norma2BD.getElementosNorma().get(0);
		//Cardinalidades
		assertEquals( 2, alteracaoManager.getAlteracoes().size() );
		assertEquals( 2, normaManager.getNormas().size() );
		assertEquals( 3, elementoNormaManager.getElementosNorma().size() );
		assertEquals( 1, norma2BD.getElementosNorma().size() );
		//Artigo2BD
		assertEquals(artigo2BD.getIdentificadorUnico(), artigo2.getIdentificadorUnico());
		assertEquals(artigo2BD.getNormasPai().get(0).getIdentificadorUnico(), norma2.getIdentificadorUnico());
		assertEquals(artigo2BD.getDataPublicacao(), alteracaoBD2.getData());
		//AlteracaoBD2
		assertEquals(alteracaoBD2.getElementoNormaOrigem().getIdentificadorUnico(),
					 artigo1.getIdentificadorUnico());
		assertEquals(alteracaoBD2.getElementoNormaDestino().getIdentificadorUnico(),
				     artigo2.getIdentificadorUnico());	
	}
	
	/**
	 * Testa se as alteracoes do tipo modificacao estao sendo feitas do moddo correto.
	 */
	@Test
	public void testCriaAlteracaoModificacao(){
		/* ---------- Verifica se as listas estao vazias ----------*/
		assertEquals( 0, normaManager.getNormas().size() );
		assertEquals( 0, elementoNormaManager.getElementosNorma().size() );
		assertEquals( 0, alteracaoManager.getAlteracoes().size() );
		
		/* ---------- Criando Norma1, Artigo1, Paragrafo1 e Norma2 ----------*/
		Norma norma1 = new Norma("epigrafeN1", "ementaN1", "autoriaN1", "localN1", "identificadorUnicoN1", "tipoN1", 
				"dataPublicacaoN1", "10/10/2010-99/99/9999");
		ElementoNorma artigo1 = new ElementoNorma("textoArt1", "identificadorUnicoArt1", "tipoArt1", 
				"dataPublicacaoArt1", "10/10/2010-99/99/9999");
		ElementoNorma paragrafo1 = new ElementoNorma("textoParagrafo1", "identificadorUnicoParagrafo1", "tipoParagrafo1", 
				"dataPublicacaoParagrafo1", "10/10/2010-99/99/9999");
		artigo1.getElementosNorma().add(paragrafo1);
		norma1.getElementosNorma().add( artigo1 );
		normaManager.salvaNorma(norma1);
		Norma norma2 = new Norma("epigrafeN2", "ementaN2", "autoriaN2", "localN2", "identificadorUnicoN2", "tipoN2", 
				"dataPublicacaoN2", "10/10/2010-99/99/9999");
		normaManager.salvaNorma( norma2 );
		/*
		 *                         Norma1                  Norma2
		 *                         /                      
		 *                       Art1                      
		 *                       /      
		 *                      Par1              
		 */
		
		/* ---------- Cardinalidades ----------*/
		assertEquals( 2, normaManager.getNormas().size() );
		assertEquals( 2, elementoNormaManager.getElementosNorma().size() );
		assertEquals( 0, alteracaoManager.getAlteracoes().size() );
		
		/* ---------- Criando uma nova modificada ----------*/
		Norma norma1Aux = (Norma) normaManager.selectNormaPorAtributo("identificadorUnico", 
				                                                      norma1.getIdentificadorUnico()).get(0);
		norma1Aux.setEmenta("novaEmentaN1"); // Modificando a ementa
		/* 
		 * AlteracaoModificacao1 = Norma2 modifica Norma1
		 */
		alteracaoManager.criaAlteracaoModificacao(norma2, norma1, norma1Aux, "20/11/2011", Constantes.MAIS_RESTRITIVA);
		
		/* ---------- Cardinalidades ----------*/
		assertEquals( 3, normaManager.getNormas().size() );
		assertEquals( 2, elementoNormaManager.getElementosNorma().size() );
		assertEquals( 1, alteracaoManager.getAlteracoes().size() );
		
		/* ---------- Verificando os atributos da nova Norma----------*/
		Alteracao alteracaoModificacaoBD = alteracaoManager.getAlteracoes().get(0);
		assertEquals( 2, normaManager.selectNormaPorAtributo("identificadorUnico",norma1.getIdentificadorUnico()).size());
		Norma norma1BDAux = (Norma) normaManager.selectNormaPorAtributo("identificadorUnico", 
									norma1.getIdentificadorUnico()).get(1);
		//Atributos
		assertEquals( norma1BDAux.getDataPublicacao(), alteracaoModificacaoBD.getData());
		assertEquals( "novaEmentaN1", norma1BDAux.getEmenta());
		//Filhos
		//Artigo1
		assertEquals( 1, norma1BDAux.getElementosNorma().size() );
		ElementoNorma artigo1BD = (ElementoNorma) norma1BDAux.getElementosNorma().get(0);
		assertEquals( artigo1BD.getIdentificadorUnico(), artigo1.getIdentificadorUnico() );
		assertEquals( 2, artigo1BD.getNormasPai().size() );
		assertEquals( 1, artigo1BD.getElementosNorma().size() );
		//Paragrafo1
		assertEquals( 1, artigo1BD.getElementosNorma().size() );
		ElementoNorma paragrafo1BD = (ElementoNorma) artigo1BD.getElementosNorma().get(0);
		assertEquals( paragrafo1BD.getIdentificadorUnico(), paragrafo1.getIdentificadorUnico() );
		assertEquals( 1, paragrafo1BD.getElementosNormaPai().size() );
		
		/* 
		 * AlteracaoModificacao2 = Norma2 modifica Artigo1
		 */
		ElementoNorma artigo1Aux = (ElementoNorma) elementoNormaManager.selectElementoPorAtributo("identificadorUnico", 
                																		artigo1.getIdentificadorUnico()).get(0);
		artigo1Aux.setTexto("textoArt1Aux"); // Modificando o texto
		alteracaoManager.criaAlteracaoModificacao(norma2, artigo1, artigo1Aux, "20/11/2011", Constantes.MAIS_RESTRITIVA);
		/* ---------- Cardinalidades ----------*/
		assertEquals( 3, normaManager.getNormas().size() );
		assertEquals( 3, elementoNormaManager.getElementosNorma().size() );
		assertEquals( 2, alteracaoManager.getAlteracoes().size() );
		
		/* ---------- Verificando os atributos do novo Elemento----------*/
		Alteracao alteracaoModificacaoBD2 = alteracaoManager.getAlteracoes().get(1);
		assertEquals( 2, elementoNormaManager.selectElementoPorAtributo("identificadorUnico", artigo1.getIdentificadorUnico()).size());
		ElementoNorma artigo1BDAux = (ElementoNorma) elementoNormaManager.selectElementoPorAtributo("identificadorUnico", 
									artigo1.getIdentificadorUnico()).get(1);
		//Atributos
		assertEquals( artigo1BDAux.getDataPublicacao(), alteracaoModificacaoBD2.getData());
		assertEquals( "textoArt1Aux", artigo1BDAux.getTexto());
		//Filhos
		//Paragrafo1
		assertEquals( 1, artigo1BDAux.getElementosNorma().size() );
		ElementoNorma paragrafo1BDAux = (ElementoNorma) artigo1BDAux.getElementosNorma().get(0);
		assertEquals( paragrafo1BDAux.getIdentificadorUnico(), paragrafo1.getIdentificadorUnico() );
		assertEquals( 2, paragrafo1BDAux.getElementosNormaPai().size() );
	}
	
	/**
	 * Testa se apos uma norma ter um de seus elementos modificados (seu filho agora tem dois pais), ela
	 * pode ser removida normalmente, esperando-se que o elemento so tenha agora 1 pai (o mais atual)
	 */
	@Test
	public void testRemoveNormaAposAlteracaoModificacao(){
		/* ---------- Verifica se as listas estao vazias ----------*/
		assertEquals( 0, normaManager.getNormas().size() );
		assertEquals( 0, elementoNormaManager.getElementosNorma().size() );
		assertEquals( 0, alteracaoManager.getAlteracoes().size() );
		
		/* ---------- Criando Norma1, Artigo1 e Norma2 ----------*/
		Norma norma1 = new Norma("epigrafeN1", "ementaN1", "autoriaN1", "localN1", "identificadorUnicoN1", "tipoN1", 
				"dataPublicacaoN1", "10/10/2010-99/99/9999");
		ElementoNorma artigo1 = new ElementoNorma("textoArt1", "identificadorUnicoArt1", "tipoArt1", 
				"dataPublicacaoArt1", "10/10/2010-99/99/9999");
		norma1.getElementosNorma().add( artigo1 );
		normaManager.salvaNorma(norma1);
		Norma norma2 = new Norma("epigrafeN2", "ementaN2", "autoriaN2", "localN2", "identificadorUnicoN2", "tipoN2", 
				"dataPublicacaoN2", "10/10/2010-99/99/9999");
		normaManager.salvaNorma( norma2 );
		/*
		 *                         Norma1                  Norma2
		 *                         /                      
		 *                       Art1                      
		 *                           
		 *                                   
		 */
		
		/* ---------- Cardinalidades ----------*/
		assertEquals( 2, normaManager.getNormas().size() );
		assertEquals( 1, elementoNormaManager.getElementosNorma().size() );
		assertEquals( 0, alteracaoManager.getAlteracoes().size() );
		
		/* 
		 * AlteracaoModificacao1 = Norma2 modifica Artigo1
		 */
		ElementoNorma artigo1Aux = (ElementoNorma) elementoNormaManager.selectElementoPorAtributo("identificadorUnico", 
				artigo1.getIdentificadorUnico()).get(0);
		artigo1Aux.setTexto("textoArt1Aux"); // Modificando o texto
		alteracaoManager.criaAlteracaoModificacao(norma2, artigo1, artigo1Aux, "20/11/2011", Constantes.MAIS_RESTRITIVA);
		
		/* ---------- Cardinalidades ----------*/
		Norma norma1BD = (Norma) normaManager.selectNormaPorAtributo("identificadorUnico", 
																		norma1.getIdentificadorUnico()).get(0);
		assertEquals( 2, normaManager.getNormas().size() );
		assertEquals( 2, elementoNormaManager.getElementosNorma().size() );
		for( ElementoNorma eleN : elementoNormaManager.getElementosNorma()) {
			System.out.println(eleN.getTexto());
			System.out.println(eleN.getNormasPai().get(0).getIdentificadorUnico());
		}
		assertEquals( 2, norma1BD.getElementosNorma().size() );
		assertEquals( 1, alteracaoManager.getAlteracoes().size() );
	}
}
