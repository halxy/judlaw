/* 
 * Universidade Federal de Campina Grande
 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas	
 */
package judlaw.model.test;

import static org.junit.Assert.assertEquals;
import judlaw.model.bean.docjud.DocumentoJuridico;
import judlaw.model.bean.law.ElementoNorma;
import judlaw.model.bean.law.Norma;
import judlaw.model.dbmanager.docjud.DocJudManager;
import judlaw.model.dbmanager.law.NormaManager;
import judlaw.model.dbmanager.ref.ReferenciaManager;

import org.junit.Before;
import org.junit.Test;

/**
 * Classe ReferenciaManagerTest - testes das operacoes da classe ReferenciaManager
 * @author Halley Freitas
 *
 */
public class ReferenciaManagerTest {
	
	ReferenciaManager refManager = ReferenciaManager.getInstance();
	NormaManager lawManager = NormaManager.getInstance();
	DocJudManager docJudManager = DocJudManager.getInstance();
	
	@Before
	public void setUp() {
		/* ---------- Esvazia as listas envolvidas ----------*/
		refManager.removeReferencias();
		lawManager.removeNormas();
		lawManager.removeElementosNorma();
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
		
		lawManager.salvaNorma(norma1);
		lawManager.salvaNorma(norma2);
		/* ---------- Verifica as cardinalidade das tabelas dos elementos envolvidos ----------*/
		assertEquals(2, lawManager.getNormas().size() );
		assertEquals(2, lawManager.getElementosNorma().size() );
		
		/* Criando as Alteracoes */
		//Norma1 -> Norma2  (N->N)
		refManager.criaAlteracao(norma1, norma2, "16/11/2009", "inclusao", "");
		//Norma1 -> Artigo2 (N->EN)
		refManager.criaAlteracao(norma1, artigo2, "17/11/2009", "inclusao", "");
		//Artigo1 -> Norma2 (EN->N)
		refManager.criaAlteracao(artigo1, norma2, "18/11/2009", "inclusao", "");
		//Artigo1 -> Artigo2 (EN->EN)
		refManager.criaAlteracao(artigo1, artigo2, "19/11/2009", "inclusao", "");
		
		/* Verificando se os atributos foram persistidos corretamente */
		//Quantidade de alteracoes
		assertEquals(4, refManager.getAlteracoes().size() );		
		// Atributos das Referencias
		//Norma1 -> Norma2  (N->N)
		assertEquals( refManager.getAlteracoes().get(0).getNormaOrigem().getIdentificadorUnico(),
				      norma1.getIdentificadorUnico() );
		assertEquals( refManager.getAlteracoes().get(0).getNormaDestino().getIdentificadorUnico(),
			      	  norma2.getIdentificadorUnico() );
		//Artigo1 -> Norma2 (EN->N)
		assertEquals( refManager.getAlteracoes().get(1).getElementoNormaOrigem().getIdentificadorUnico(),
			      	  artigo1.getIdentificadorUnico() );
		assertEquals( refManager.getAlteracoes().get(1).getNormaDestino().getIdentificadorUnico(),
		      	      norma2.getIdentificadorUnico() );
		//Norma1 -> Artigo2 (N->EN)
		assertEquals( refManager.getAlteracoes().get(2).getNormaOrigem().getIdentificadorUnico(),
			      	  norma1.getIdentificadorUnico() );
		assertEquals( refManager.getAlteracoes().get(2).getElementoNormaDestino().getIdentificadorUnico(),
		      	      artigo2.getIdentificadorUnico() );
		//Artigo1 -> Artigo2 (EN->EN)
		assertEquals( refManager.getAlteracoes().get(3).getElementoNormaOrigem().getIdentificadorUnico(),
			      	  artigo1.getIdentificadorUnico() );
		assertEquals( refManager.getAlteracoes().get(3).getElementoNormaDestino().getIdentificadorUnico(),
		      	      artigo2.getIdentificadorUnico() );
		
		/*
		 * Atributos das Normas e ElementosNormas
		 */
		//NORMA1
		assertEquals( lawManager.getNormas().get(0).getIdentificadorUnico(),
					  norma1.getIdentificadorUnico());
		assertEquals( lawManager.getNormas().get(0).getAlteracoesFeitas().get(0).getData(),
				  	  "16/11/2009");
		//NORMA2
		assertEquals( lawManager.getNormas().get(1).getIdentificadorUnico(),
				      norma2.getIdentificadorUnico());
		assertEquals( lawManager.getNormas().get(1).getAlteracoesRecebidas().get(0).getData(),
					  "16/11/2009");
		assertEquals( lawManager.getNormas().get(1).getAlteracoesRecebidas().get(1).getData(),
		  			  "18/11/2009");
		//ELEMENTONORMA1
		assertEquals( lawManager.getElementosNorma().get(0).getIdentificadorUnico(),
					  artigo1.getIdentificadorUnico());
		assertEquals( lawManager.getElementosNorma().get(0).getAlteracoesFeitas().get(0).getData(),
				  	  "18/11/2009");
		assertEquals( lawManager.getElementosNorma().get(0).getAlteracoesFeitas().get(1).getData(),
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
		
		lawManager.salvaNorma(norma1);
		lawManager.salvaNorma(norma2);
		/* ---------- Verifica as cardinalidade das tabelas dos elementos envolvidos ----------*/
		assertEquals(2, lawManager.getNormas().size() );
		assertEquals(2, lawManager.getElementosNorma().size() );
		
		/* Criando as Alteracoes */
		//Norma1 -> Norma2  (N->N)
		refManager.criaAlteracao(norma1, norma2, "16/11/2009", "inclusao", "");
		//Norma1 -> Artigo2 (N->EN)
		refManager.criaAlteracao(norma1, artigo2, "17/11/2009", "inclusao", "");
		//Artigo1 -> Norma2 (EN->N)
		refManager.criaAlteracao(artigo1, norma2, "18/11/2009", "inclusao", "");
		//Artigo1 -> Artigo2 (EN->EN)
		refManager.criaAlteracao(artigo1, artigo2, "19/11/2009", "inclusao", "");
		
		/* Verificando se os atributos foram persistidos corretamente */
		//Quantidade de alteracoes
		assertEquals(4, refManager.getAlteracoes().size() );
		
		/* ---------- Verifica as cardinalidade das alteracoes ----------*/
		//Norma1
		assertEquals(norma1.getIdentificadorUnico(), lawManager.getNormas().get(0).getIdentificadorUnico() );
		assertEquals(2, lawManager.getNormas().get(0).getAlteracoesFeitas().size() );
		//Norma2
		assertEquals(norma2.getIdentificadorUnico(), lawManager.getNormas().get(1).getIdentificadorUnico() );
		assertEquals(2, lawManager.getNormas().get(1).getAlteracoesRecebidas().size() );
		//Artigo1
		assertEquals(artigo1.getIdentificadorUnico(), lawManager.getElementosNorma().get(0).getIdentificadorUnico() );
		assertEquals(2, lawManager.getElementosNorma().get(0).getAlteracoesFeitas().size() );
		//Artigo2
		assertEquals(2, lawManager.getElementosNorma().get(1).getAlteracoesRecebidas().size() );
		
		/* ---------- Verificando as alteracoes ----------*/
		//Norma1 -> Norma2  (N->N)
		assertEquals("16/11/2009", refManager.getAlteracoes().get(0).getData() );
		//Artigo1 -> Norma2 (EN->N)
		assertEquals("18/11/2009", refManager.getAlteracoes().get(1).getData() );
		//Norma1 -> Artigo2 (N->EN)
		assertEquals("17/11/2009", refManager.getAlteracoes().get(2).getData() );
		//Artigo1 -> Artigo2 (EN->EN)
		assertEquals("19/11/2009", refManager.getAlteracoes().get(3).getData() );
		
		/* ---------- Removendo as alteracoes ----------*/
		//Norma1 -> Norma2  (N->N)
		refManager.removeAlteracao( refManager.getAlteracoes().get(0) );
		assertEquals(3, refManager.getAlteracoes().size() );
		assertEquals(1, lawManager.getNormas().get(0).getAlteracoesFeitas().size() ); //Norma1
		assertEquals(2, lawManager.getElementosNorma().get(0).getAlteracoesFeitas().size() ); //Artigo1
		assertEquals(1, lawManager.getNormas().get(1).getAlteracoesRecebidas().size() ); //Norma2
		assertEquals(2, lawManager.getElementosNorma().get(1).getAlteracoesRecebidas().size() ); //Artigo2
		//Artigo1 -> Norma2 (EN->N)
		refManager.removeAlteracao( refManager.getAlteracoes().get(0) );
		assertEquals(2, refManager.getAlteracoes().size() );
		assertEquals(1, lawManager.getNormas().get(0).getAlteracoesFeitas().size() ); //Norma1
		assertEquals(1, lawManager.getElementosNorma().get(0).getAlteracoesFeitas().size() ); //ElementoNorma1
		assertEquals(0, lawManager.getNormas().get(1).getAlteracoesRecebidas().size() ); //Norma2
		assertEquals(2, lawManager.getElementosNorma().get(1).getAlteracoesRecebidas().size() ); //Artigo2
		//Norma1 -> Artigo2 (N->EN)
		refManager.removeAlteracao( refManager.getAlteracoes().get(0) );
		assertEquals(1, refManager.getAlteracoes().size() );
		assertEquals(0, lawManager.getNormas().get(0).getAlteracoesFeitas().size() ); //Norma1
		assertEquals(1, lawManager.getElementosNorma().get(0).getAlteracoesFeitas().size() ); //ElementoNorma1
		assertEquals(0, lawManager.getNormas().get(1).getAlteracoesRecebidas().size() ); //Norma2
		assertEquals(1, lawManager.getElementosNorma().get(1).getAlteracoesRecebidas().size() ); //Artigo2
		//Artigo1 -> Artigo2 (EN->EN)
		refManager.removeAlteracao( refManager.getAlteracoes().get(0) );
		assertEquals(0, refManager.getAlteracoes().size() );
		assertEquals(0, lawManager.getNormas().get(0).getAlteracoesFeitas().size() ); //Norma1
		assertEquals(0, lawManager.getElementosNorma().get(0).getAlteracoesFeitas().size() ); //ElementoNorma1
		assertEquals(0, lawManager.getNormas().get(1).getAlteracoesRecebidas().size() ); //Norma2
		assertEquals(0, lawManager.getElementosNorma().get(1).getAlteracoesRecebidas().size() ); //Artigo2
	}
	
	 /* ------------------------------------------------------------------ */
    /* -------------------- TESTES CITACAOTEXTLEG ------------------------ */
    /* ------------------------------------------------------------------ */
	/**
	 * Testa se as citacoesTextLeg estao sendo salvas corretamente
	 */
	@Test
	public void testSalvaCitacaoTextLeg(){
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
		
		lawManager.salvaNorma(norma1);
		lawManager.salvaNorma(norma2);
		
		/* Criando e persistindo o documento juridico */
		DocumentoJuridico docJud1 = new DocumentoJuridico();
		docJud1.setIdentificadorUnico("idUnico");
		docJudManager.salvaDocumentoJuridico(docJud1);
		
		/* ---------- Verifica as cardinalidade das tabelas dos elementos envolvidos ----------*/
		assertEquals(2, lawManager.getNormas().size() );
		assertEquals(2, lawManager.getElementosNorma().size() );
		assertEquals(1, docJudManager.getDocumentosJuridicos().size() );
		
		/* Criando as CitacoesTextLeg */
		//Norma1 -> Norma2  (N->N)
		refManager.criaCitacaoTextLeg(norma1, norma2, "16/11/2009");
		//Norma1 -> Artigo1 (N->EN)
		refManager.criaCitacaoTextLeg(norma1, artigo1, "17/11/2009");
		//Norma1 -> DocJud1 (N->DJ)
		refManager.criaCitacaoTextLeg(norma1, docJud1, "18/11/2009");
		//Artigo1 -> Norma1 (EN->N)
		refManager.criaCitacaoTextLeg(artigo1, norma1, "19/11/2009");
		//Artigo1 -> Artigo2 (EN->EN)
		refManager.criaCitacaoTextLeg(artigo1, artigo2, "20/11/2009");
		//Artigo1 -> DocJud1 (EN->DJ)
		refManager.criaCitacaoTextLeg(artigo1, docJud1, "21/11/2009");
		
		/* Verificando se os atributos foram persistidos corretamente */
		//Quantidade de alteracoes
		assertEquals(6, refManager.getCitacoesTextLeg().size() );
		
		/*
		 * Atributos das Referencias
		 * Por algum motivo obscuro, ao pegar a lista de Citacoes, eles nao vem na ordem
		 * que foram inseridos, dai a diferenca da ordem que foi inserida para a ordem que 
		 * estao sendo avaliados os atributos.
		 */
		
		//Norma1 -> Norma2  (N->N)
		assertEquals( refManager.getCitacoesTextLeg().get(0).getNormaOrigem().getIdentificadorUnico(),
				  norma1.getIdentificadorUnico());
		assertEquals( refManager.getCitacoesTextLeg().get(0).getNormaDestino().getIdentificadorUnico(),
			      norma2.getIdentificadorUnico());
				
		//Artigo1 -> Norma1 (EN->N)
		assertEquals( refManager.getCitacoesTextLeg().get(1).getElementoNormaOrigem().getIdentificadorUnico(),
				  artigo1.getIdentificadorUnico());
		assertEquals( refManager.getCitacoesTextLeg().get(1).getNormaDestino().getIdentificadorUnico(),
			      norma1.getIdentificadorUnico());
		
		//Artigo1 -> Artigo2 (EN->EN)
		assertEquals( refManager.getCitacoesTextLeg().get(2).getElementoNormaOrigem().getIdentificadorUnico(),
				  artigo1.getIdentificadorUnico());
		assertEquals( refManager.getCitacoesTextLeg().get(2).getElementoNormaDestino().getIdentificadorUnico(),
			      artigo2.getIdentificadorUnico());
		
		//Norma1 -> Artigo1 (N->EN)
		assertEquals( refManager.getCitacoesTextLeg().get(3).getNormaOrigem().getIdentificadorUnico(),
				  norma1.getIdentificadorUnico());
		assertEquals( refManager.getCitacoesTextLeg().get(3).getElementoNormaDestino().getIdentificadorUnico(),
					artigo1.getIdentificadorUnico());
		
		//Norma1 -> DocJud1 (N->DJ)
		assertEquals( refManager.getCitacoesTextLeg().get(4).getNormaOrigem().getIdentificadorUnico(),
				  norma1.getIdentificadorUnico());
		assertEquals( refManager.getCitacoesTextLeg().get(4).getDocumentoJuridicoDestino().getIdentificadorUnico(),
			      docJud1.getIdentificadorUnico());
		
		//Artigo1 -> DocJud1 (EN->DJ)
		assertEquals( refManager.getCitacoesTextLeg().get(5).getElementoNormaOrigem().getIdentificadorUnico(),
				  artigo1.getIdentificadorUnico());
		assertEquals( refManager.getCitacoesTextLeg().get(5).getDocumentoJuridicoDestino().getIdentificadorUnico(),
			      docJud1.getIdentificadorUnico());
		
		/*
		 * Atributos das Normas, ElementosNormas e DocumentoJuridico
		 * Por algum motivo obscuro, ao pegar a lista de normas e elementosnorma, eles nao vem na ordem
		 * que foram inseridos, dai a diferenca da ordem que foi inserida para a ordem que 
		 * estao sendo avaliados os atributos.
		 */
		//NORMA1
		assertEquals( lawManager.getNormas().get(1).getIdentificadorUnico(),
					  norma1.getIdentificadorUnico());
		assertEquals( lawManager.getNormas().get(1).getCitacoesFeitas().get(0).getData(),
				  	  "16/11/2009");
		assertEquals( lawManager.getNormas().get(1).getCitacoesRecebidasTextLeg().get(0).getData(),
	  	  				"19/11/2009");
		//NORMA2
		assertEquals( lawManager.getNormas().get(0).getIdentificadorUnico(),
				      norma2.getIdentificadorUnico());
		assertEquals( lawManager.getNormas().get(0).getCitacoesRecebidasTextLeg().get(0).getData(),
					  "16/11/2009");
		//ELEMENTONORMA1
		assertEquals( lawManager.getElementosNorma().get(1).getIdentificadorUnico(),
					  artigo1.getIdentificadorUnico());
		assertEquals( lawManager.getElementosNorma().get(1).getCitacoesFeitas().get(0).getData(),
				  	  "19/11/2009");
		assertEquals( lawManager.getElementosNorma().get(1).getCitacoesFeitas().get(1).getData(),
	  	  			  "20/11/2009");
		assertEquals( lawManager.getElementosNorma().get(1).getCitacoesFeitas().get(2).getData(),
						"21/11/2009");
		//DOCUMENTOJURIDICO1
		assertEquals( docJudManager.getDocumentosJuridicos().get(0).getIdentificadorUnico(),
				  	docJud1.getIdentificadorUnico());
		assertEquals( docJudManager.getDocumentosJuridicos().get(0).getCitacoesRecebidasTextLeg().get(0).getData(),
			  	"18/11/2009");
		assertEquals( docJudManager.getDocumentosJuridicos().get(0).getCitacoesRecebidasTextLeg().get(1).getData(),
	  			"21/11/2009");
	}
	
	/**
	 * Testa se a CitacaoTextLeg esta sendo removida corretamente
	 */
	@Test
	public void testRemoveCitacaoTextLeg(){
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
		lawManager.salvaNorma(norma1);
		lawManager.salvaNorma(norma2);
		
		/* Criando e persistindo o documento juridico */
		DocumentoJuridico docJud1 = new DocumentoJuridico();
		docJud1.setIdentificadorUnico("idUnico1");
		docJudManager.salvaDocumentoJuridico(docJud1);
		/* ---------- Verifica as cardinalidade das tabelas dos elementos envolvidos ----------*/
		assertEquals(2, lawManager.getNormas().size() );
		assertEquals(2, lawManager.getElementosNorma().size() );
		assertEquals(1, docJudManager.getDocumentosJuridicos().size() );
		
		/* Criando as CitacoesTextLeg */
		//Norma1 -> Norma2  (N->N)
		refManager.criaCitacaoTextLeg(norma1, norma2, "16/11/2009");
		//Artigo1 -> Artigo2 (EN->EN)
		refManager.criaCitacaoTextLeg(artigo1, artigo2, "17/11/2009");
		//Artigo1 -> DocJud1 (N->DJ)
		refManager.criaCitacaoTextLeg(artigo1, docJud1, "18/11/2009");
		
		/* Verificando se os atributos foram persistidos corretamente */
		//Quantidade de citacoesTextLeg
		assertEquals(3, refManager.getCitacoesTextLeg().size() );
		/*
		 * Por algum motivo obscuro, ao pegar a lista elementos, eles nao vem na ordem
		 * que foram inseridos, dai a diferenca da ordem que foi inserida para a ordem que 
		 * estao sendo avaliados os atributos.
		 */
		//Norma1
		assertEquals(norma1.getIdentificadorUnico(), lawManager.getNormas().get(0).getIdentificadorUnico() );
		assertEquals(1, lawManager.getNormas().get(0).getCitacoesFeitas().size() );
		//Norma2
		assertEquals(norma2.getIdentificadorUnico(), lawManager.getNormas().get(1).getIdentificadorUnico() );
		assertEquals(1, lawManager.getNormas().get(1).getCitacoesRecebidasTextLeg().size() );
		//Artigo1
		assertEquals(artigo1.getIdentificadorUnico(), lawManager.getElementosNorma().get(1).getIdentificadorUnico() );
		assertEquals(2, lawManager.getElementosNorma().get(1).getCitacoesFeitas().size() );
		//Artigo2
		assertEquals(artigo2.getIdentificadorUnico(), lawManager.getElementosNorma().get(0).getIdentificadorUnico() );
		assertEquals(1, lawManager.getElementosNorma().get(0).getCitacoesRecebidasTextLeg().size() );
		//DocJud1
		assertEquals(1, docJudManager.getDocumentosJuridicos().get(0).getCitacoesRecebidasTextLeg().size() );
				
		/* ---------- Removendo as citacoes ----------*/
		/*
		 * Norma1 -> Norma2  (N->N)
		 */
		assertEquals("16/11/2009", refManager.getCitacoesTextLeg().get(0).getData() );
		refManager.removeCitacaoTextLeg( refManager.getCitacoesTextLeg().get(0) );
		assertEquals(2, refManager.getCitacoesTextLeg().size() );
		//Norma1
		assertEquals(norma1.getIdentificadorUnico(), lawManager.getNormas().get(0).getIdentificadorUnico() );
		assertEquals(0, lawManager.getNormas().get(0).getCitacoesFeitas().size() );
		//Norma2
		assertEquals(norma2.getIdentificadorUnico(), lawManager.getNormas().get(1).getIdentificadorUnico() );
		assertEquals(0, lawManager.getNormas().get(1).getCitacoesRecebidasTextLeg().size() );
		//Artigo1
		assertEquals(artigo1.getIdentificadorUnico(), lawManager.getElementosNorma().get(0).getIdentificadorUnico() );
		assertEquals(2, lawManager.getElementosNorma().get(0).getCitacoesFeitas().size() );
		//Artigo2
		assertEquals(1, lawManager.getElementosNorma().get(1).getCitacoesRecebidasTextLeg().size() );
		//DocJud1
		assertEquals(1, docJudManager.getDocumentosJuridicos().get(0).getCitacoesRecebidasTextLeg().size() );
		/*
		 * Norma1 -> DocJud1 (N->DJ)
		 */
		assertEquals("18/11/2009", refManager.getCitacoesTextLeg().get(0).getData() );
		refManager.removeCitacaoTextLeg( refManager.getCitacoesTextLeg().get(0) );
		assertEquals(1, refManager.getCitacoesTextLeg().size() );
		//Norma1
		assertEquals(norma1.getIdentificadorUnico(), lawManager.getNormas().get(0).getIdentificadorUnico() );
		assertEquals(0, lawManager.getNormas().get(0).getCitacoesFeitas().size() );
		//Norma2
		assertEquals(norma2.getIdentificadorUnico(), lawManager.getNormas().get(1).getIdentificadorUnico() );
		assertEquals(0, lawManager.getNormas().get(1).getCitacoesRecebidasTextLeg().size() );
		//Artigo1
		assertEquals(artigo1.getIdentificadorUnico(), lawManager.getElementosNorma().get(1).getIdentificadorUnico() );
		assertEquals(1, lawManager.getElementosNorma().get(1).getCitacoesFeitas().size() );
		//Artigo2
		assertEquals(artigo2.getIdentificadorUnico(), lawManager.getElementosNorma().get(0).getIdentificadorUnico() );
		assertEquals(1, lawManager.getElementosNorma().get(0).getCitacoesRecebidasTextLeg().size() );
		//DocJud1
		assertEquals(0, docJudManager.getDocumentosJuridicos().get(0).getCitacoesRecebidasTextLeg().size() );

		/*
		 * Artigo1 -> Artigo2 (EN->EN)
		 */
		assertEquals("17/11/2009", refManager.getCitacoesTextLeg().get(0).getData() );
		refManager.removeCitacaoTextLeg( refManager.getCitacoesTextLeg().get(0) );
		assertEquals(0, refManager.getCitacoesTextLeg().size() );
		//Norma1
		assertEquals(norma1.getIdentificadorUnico(), lawManager.getNormas().get(0).getIdentificadorUnico() );
		assertEquals(0, lawManager.getNormas().get(0).getCitacoesFeitas().size() );
		//Norma2
		assertEquals(norma2.getIdentificadorUnico(), lawManager.getNormas().get(1).getIdentificadorUnico() );
		assertEquals(0, lawManager.getNormas().get(1).getCitacoesRecebidasTextLeg().size() );
		//Artigo1
		assertEquals(artigo1.getIdentificadorUnico(), lawManager.getElementosNorma().get(0).getIdentificadorUnico() );
		assertEquals(0, lawManager.getElementosNorma().get(0).getCitacoesFeitas().size() );
		//Artigo2
		assertEquals(artigo2.getIdentificadorUnico(), lawManager.getElementosNorma().get(1).getIdentificadorUnico() );
		assertEquals(0, lawManager.getElementosNorma().get(1).getCitacoesRecebidasTextLeg().size() );
		//DocJud1
		assertEquals(0, docJudManager.getDocumentosJuridicos().get(0).getCitacoesRecebidasTextLeg().size() );
	}
	
	 /* ------------------------------------------------------------------ */
    /* -------------------- TESTES CITACAODOCJUD ------------------------ */
    /* ------------------------------------------------------------------ */
	@Test
	public void testSalvaCitacaoDocJud(){
		/*
		 *           Norma1               
		 *             |                   
		 *        ElementoNorma1      
		 */
		/* Criando e persistindo a norma e o elementoNorma*/
		Norma norma1 = new Norma("epigrafeN1", "ementaN1", "autoriaN1", "localN1", "identificadorUnicoN1", "tipoN1", 
				"dataPublicacaoN1", "vigenciaN1");
		ElementoNorma artigo1 = new ElementoNorma("textoArt1", "identificadorUnicoArt1", "tipoArt1", 
				"dataPublicacaoArt1", "vigenciaArt1");
		norma1.getElementosNorma().add(artigo1);
		lawManager.salvaNorma(norma1);
		
		/* Criando e persistindo o documento juridico */
		DocumentoJuridico docJud1 = new DocumentoJuridico();
		docJud1.setIdentificadorUnico("idUnico1");
		DocumentoJuridico docJud2 = new DocumentoJuridico();
		docJud2.setIdentificadorUnico("idUnico2");
		docJudManager.salvaDocumentoJuridico(docJud1);
		docJudManager.salvaDocumentoJuridico(docJud2);
		
		/* ---------- Verifica as cardinalidade das tabelas dos elementos envolvidos ----------*/
		assertEquals(1, lawManager.getNormas().size() );
		assertEquals(1, lawManager.getElementosNorma().size() );
		assertEquals(2, docJudManager.getDocumentosJuridicos().size() );
		
		/* Criando as CitacoesDocJud */
		//DocJud1 -> DocJud2  (DJ->DJ)
		refManager.criaCitacaoDocJud(docJud1, docJud2, "16/11/2009");
		//DocJud1 -> Norma1 (DJ->N)
		refManager.criaCitacaoDocJud(docJud1, norma1, "17/11/2009");
		//DocJud1 -> Artigo1 (DJ->EN)
		refManager.criaCitacaoDocJud(docJud1, artigo1, "18/11/2009");
		
		/* Verificando se os atributos foram persistidos corretamente */
		//Quantidade de alteracoes
		assertEquals(3, refManager.getCitacoesDocJud().size() );
		
		/*
		 * Atributos das Referencias
		 */
		
		//DocJud1 -> DocJud2  (DJ->DJ)
		assertEquals( refManager.getCitacoesDocJud().get(0).getDocumentoJuridicoOrigem().getIdentificadorUnico(),
				  	  docJud1.getIdentificadorUnico());
		assertEquals( refManager.getCitacoesDocJud().get(0).getDocumentoJuridicoDestino().getIdentificadorUnico(),
			  	  	  docJud2.getIdentificadorUnico());
		
		//DocJud1 -> Norma1 (DJ->N)
		assertEquals( refManager.getCitacoesDocJud().get(1).getDocumentoJuridicoOrigem().getIdentificadorUnico(),
			  	  docJud1.getIdentificadorUnico());
		assertEquals( refManager.getCitacoesDocJud().get(1).getNormaDestino().getIdentificadorUnico(),
			      norma1.getIdentificadorUnico());
		
		//DocJud1 -> Artigo1 (DJ->EN)
		assertEquals( refManager.getCitacoesDocJud().get(2).getDocumentoJuridicoOrigem().getIdentificadorUnico(),
			  	  docJud1.getIdentificadorUnico());
		assertEquals( refManager.getCitacoesDocJud().get(2).getElementoNormaDestino().getIdentificadorUnico(),
			      artigo1.getIdentificadorUnico());
	}
	
	/**
	 * Testa se a CitacaoDocJud esta sendo removida corretamente
	 */
	@Test
	public void testRemoveCitacaoDocJud(){
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
		lawManager.salvaNorma(norma1);
		
		/* Criando e persistindo o documento juridico */
		DocumentoJuridico docJud1 = new DocumentoJuridico();
		docJud1.setIdentificadorUnico("idUnico1");
		DocumentoJuridico docJud2 = new DocumentoJuridico();
		docJud2.setIdentificadorUnico("idUnico2");
		docJudManager.salvaDocumentoJuridico(docJud1);
		docJudManager.salvaDocumentoJuridico(docJud2);
		/* ---------- Verifica as cardinalidade das tabelas dos elementos envolvidos ----------*/
		assertEquals(1, lawManager.getNormas().size() );
		assertEquals(1, lawManager.getElementosNorma().size() );
		assertEquals(2, docJudManager.getDocumentosJuridicos().size() );
		
		/* Criando as CitacoesDocJud */
		//DocJud1 -> DocJud2 (DJ->DJ)
		refManager.criaCitacaoDocJud(docJud1, docJud2, "16/11/2009");
		//DocJud1 -> Norma1 (DJ->N)
		refManager.criaCitacaoDocJud(docJud1, norma1, "17/11/2009");
		//DocJud1 -> Artigo1 (DJ->EN)
		refManager.criaCitacaoDocJud(docJud1, artigo1, "18/11/2009");
		
		/* Verificando se os atributos foram persistidos corretamente */
		//Quantidade de citacoesDocJud
		assertEquals(3, refManager.getCitacoesDocJud().size() );
		/*
		 * Por algum motivo obscuro, ao pegar a lista elementos, eles nao vem na ordem
		 * que foram inseridos, dai a diferenca da ordem que foi inserida para a ordem que 
		 * estao sendo avaliados os atributos.
		 */
		//Norma1
		assertEquals(norma1.getIdentificadorUnico(), lawManager.getNormas().get(0).getIdentificadorUnico() );
		assertEquals(1, lawManager.getNormas().get(0).getCitacoesRecebidasDocJud().size() );
		//Artigo1
		assertEquals(artigo1.getIdentificadorUnico(), lawManager.getElementosNorma().get(0).getIdentificadorUnico() );
		assertEquals(1, lawManager.getElementosNorma().get(0).getCitacoesRecebidasDocJud().size() );
		//DocJud1
		assertEquals(docJud1.getIdentificadorUnico(), docJudManager.getDocumentosJuridicos().get(1).getIdentificadorUnico() );
		assertEquals(3, docJudManager.getDocumentosJuridicos().get(1).getCitacoesFeitas().size() );
		//DocJud2
		assertEquals(docJud2.getIdentificadorUnico(), docJudManager.getDocumentosJuridicos().get(0).getIdentificadorUnico() );
		assertEquals(1, docJudManager.getDocumentosJuridicos().get(0).getCitacoesRecebidasDocJud().size() );
				
		/* ---------- Removendo as citacoes ----------*/
		/*
		 * DocJud1 -> DocJud2 (DJ->DJ)
		 */
		assertEquals("16/11/2009", refManager.getCitacoesDocJud().get(0).getData() );
		refManager.removeCitacaoDocJud( refManager.getCitacoesDocJud().get(0) );
		assertEquals(2, refManager.getCitacoesDocJud().size() );
		//Norma1
		assertEquals(norma1.getIdentificadorUnico(), lawManager.getNormas().get(0).getIdentificadorUnico() );
		assertEquals(1, lawManager.getNormas().get(0).getCitacoesRecebidasDocJud().size() );
		//Artigo1
		assertEquals(artigo1.getIdentificadorUnico(), lawManager.getElementosNorma().get(0).getIdentificadorUnico() );
		assertEquals(1, lawManager.getElementosNorma().get(0).getCitacoesRecebidasDocJud().size() );
		//DocJud1
		assertEquals(docJud1.getIdentificadorUnico(), docJudManager.getDocumentosJuridicos().get(0).getIdentificadorUnico() );
		assertEquals(2, docJudManager.getDocumentosJuridicos().get(0).getCitacoesFeitas().size() );
		//DocJud2
		assertEquals(docJud2.getIdentificadorUnico(), docJudManager.getDocumentosJuridicos().get(1).getIdentificadorUnico() );
		assertEquals(0, docJudManager.getDocumentosJuridicos().get(1).getCitacoesRecebidasDocJud().size() );
		/*
		 * DocJud1 -> Norma1 (DJ->N)
		 */
		assertEquals("17/11/2009", refManager.getCitacoesDocJud().get(0).getData() );
		refManager.removeCitacaoDocJud( refManager.getCitacoesDocJud().get(0) );
		assertEquals(1, refManager.getCitacoesDocJud().size() );
		//Norma1
		assertEquals(norma1.getIdentificadorUnico(), lawManager.getNormas().get(0).getIdentificadorUnico() );
		assertEquals(0, lawManager.getNormas().get(0).getCitacoesRecebidasDocJud().size() );
		//Artigo1
		assertEquals(artigo1.getIdentificadorUnico(), lawManager.getElementosNorma().get(0).getIdentificadorUnico() );
		assertEquals(1, lawManager.getElementosNorma().get(0).getCitacoesRecebidasDocJud().size() );
		//DocJud1
		assertEquals(docJud1.getIdentificadorUnico(), docJudManager.getDocumentosJuridicos().get(1).getIdentificadorUnico() );
		assertEquals(1, docJudManager.getDocumentosJuridicos().get(1).getCitacoesFeitas().size() );
		//DocJud2
		assertEquals(docJud2.getIdentificadorUnico(), docJudManager.getDocumentosJuridicos().get(0).getIdentificadorUnico() );
		assertEquals(0, docJudManager.getDocumentosJuridicos().get(0).getCitacoesRecebidasDocJud().size() );

		/*
		 * DocJud1 -> Artigo1 (DJ->EN)
		 */
		assertEquals("18/11/2009", refManager.getCitacoesDocJud().get(0).getData() );
		refManager.removeCitacaoDocJud( refManager.getCitacoesDocJud().get(0) );
		assertEquals(0, refManager.getCitacoesDocJud().size() );
		//Norma1
		assertEquals(norma1.getIdentificadorUnico(), lawManager.getNormas().get(0).getIdentificadorUnico() );
		assertEquals(0, lawManager.getNormas().get(0).getCitacoesRecebidasDocJud().size() );
		//Artigo1
		assertEquals(artigo1.getIdentificadorUnico(), lawManager.getElementosNorma().get(0).getIdentificadorUnico() );
		assertEquals(0, lawManager.getElementosNorma().get(0).getCitacoesRecebidasDocJud().size() );
		//DocJud1
		assertEquals(docJud1.getIdentificadorUnico(), docJudManager.getDocumentosJuridicos().get(1).getIdentificadorUnico() );
		assertEquals(0, docJudManager.getDocumentosJuridicos().get(1).getCitacoesFeitas().size() );
		//DocJud2
		assertEquals(docJud2.getIdentificadorUnico(), docJudManager.getDocumentosJuridicos().get(0).getIdentificadorUnico() );
		assertEquals(0, docJudManager.getDocumentosJuridicos().get(0).getCitacoesRecebidasDocJud().size() );
	}
}
