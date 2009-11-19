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
import judlaw.model.dbmanager.docjud.DocJudManager;
import judlaw.model.dbmanager.law.ElementoNormaManager;
import judlaw.model.dbmanager.law.NormaManager;
import judlaw.model.dbmanager.ref.AlteracaoManager;
import judlaw.model.dbmanager.ref.ReferenciaManager;

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
		normaManager.removeNormas();
		elementoNormaManager.removeElementosNorma();
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
}
