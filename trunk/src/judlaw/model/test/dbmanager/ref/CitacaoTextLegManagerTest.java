/* 
 * Universidade Federal de Campina Grande
 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas	
 */
package judlaw.model.test.dbmanager.ref;

import static org.junit.Assert.assertEquals;
import judlaw.model.bean.docjud.DocumentoJuridico;
import judlaw.model.bean.law.ElementoNorma;
import judlaw.model.bean.law.Norma;
import judlaw.model.persistence.dbmanager.docjud.DocJudManager;
import judlaw.model.persistence.dbmanager.law.ElementoNormaManager;
import judlaw.model.persistence.dbmanager.law.NormaManager;
import judlaw.model.persistence.dbmanager.ref.CitacaoTextLegManager;
import judlaw.model.persistence.dbmanager.ref.ReferenciaManager;

import org.junit.Before;
import org.junit.Test;

/**
 * Classe ReferenciaManagerTest - testes das operacoes da classe ReferenciaManager
 * @author Halley Freitas
 *
 */
public class CitacaoTextLegManagerTest {
	
	private ReferenciaManager refManager = ReferenciaManager.getInstance();
	private CitacaoTextLegManager citacaoTextLegManager = CitacaoTextLegManager.getInstance();
	private NormaManager normaManager = NormaManager.getInstance();
	private ElementoNormaManager elementoNormaManager = ElementoNormaManager.getInstance();
	private DocJudManager docJudManager = DocJudManager.getInstance();
	
	@Before
	public void setUp() {
		/* ---------- Esvazia as listas envolvidas ----------*/
		refManager.removeReferencias();
		normaManager.removeLaw();
		docJudManager.removeDocumentosJuridicos();	
	}
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
		Norma norma1 = new Norma("ementaN1", "autoriaN1", "localN1", "identificadorUnicoN1", "tipoN1", 
				"dataPublicacaoN1", "vigenciaN1");
		ElementoNorma artigo1 = new ElementoNorma("textoArt1", "identificadorUnicoArt1", "tipoArt1", 
				"dataPublicacaoArt1", "vigenciaArt1");
		norma1.getElementosNorma().add(artigo1);
		
		Norma norma2 = new Norma("ementaN2", "autoriaN2", "localN2", "identificadorUnicoN2", "tipoN2", 
				"dataPublicacaoN2", "vigenciaN2");
		ElementoNorma artigo2 = new ElementoNorma("textoArt2", "identificadorUnicoArt2", "tipoArt2", 
				"dataPublicacaoArt2", "vigenciaArt2");
		norma2.getElementosNorma().add(artigo2);
		
		normaManager.salvaNorma(norma1);
		normaManager.salvaNorma(norma2);
		
		/* Criando e persistindo o documento juridico */
		DocumentoJuridico docJud1 = new DocumentoJuridico();
		docJud1.setIdentificadorUnico("idUnico");
		docJudManager.salvaDocumentoJuridico(docJud1);
		
		/* ---------- Verifica as cardinalidade das tabelas dos elementos envolvidos ----------*/
		assertEquals(2, normaManager.getNormas().size() );
		assertEquals(2, elementoNormaManager.getElementosNorma().size() );
		assertEquals(1, docJudManager.getDocumentosJuridicos().size() );
		
		/* Criando as CitacoesTextLeg */
		//Norma1 -> Norma2  (N->N)
		citacaoTextLegManager.criaCitacaoTextLeg(norma1, norma2, "16/11/2009");
		//Norma1 -> Artigo1 (N->EN)
		citacaoTextLegManager.criaCitacaoTextLeg(norma1, artigo1, "17/11/2009");
		//Norma1 -> DocJud1 (N->DJ)
		citacaoTextLegManager.criaCitacaoTextLeg(norma1, docJud1, "18/11/2009");
		//Artigo1 -> Norma1 (EN->N)
		citacaoTextLegManager.criaCitacaoTextLeg(artigo1, norma1, "19/11/2009");
		//Artigo1 -> Artigo2 (EN->EN)
		citacaoTextLegManager.criaCitacaoTextLeg(artigo1, artigo2, "20/11/2009");
		//Artigo1 -> DocJud1 (EN->DJ)
		citacaoTextLegManager.criaCitacaoTextLeg(artigo1, docJud1, "21/11/2009");
		
		/* Verificando se os atributos foram persistidos corretamente */
		//Quantidade de alteracoes
		assertEquals(6, citacaoTextLegManager.getCitacoesTextLeg().size() );
		
		/*
		 * Atributos das Referencias
		 * Por algum motivo obscuro, ao pegar a lista de Citacoes, eles nao vem na ordem
		 * que foram inseridos, dai a diferenca da ordem que foi inserida para a ordem que 
		 * estao sendo avaliados os atributos.
		 */
		
		//Norma1 -> Norma2  (N->N)
		assertEquals( citacaoTextLegManager.getCitacoesTextLeg().get(0).getNormaOrigem().getIdentificadorUnico(),
				  norma1.getIdentificadorUnico());
		assertEquals( citacaoTextLegManager.getCitacoesTextLeg().get(0).getNormaDestino().getIdentificadorUnico(),
			      norma2.getIdentificadorUnico());
				
		//Artigo1 -> Norma1 (EN->N)
		assertEquals( citacaoTextLegManager.getCitacoesTextLeg().get(1).getElementoNormaOrigem().getIdentificadorUnico(),
				  artigo1.getIdentificadorUnico());
		assertEquals( citacaoTextLegManager.getCitacoesTextLeg().get(1).getNormaDestino().getIdentificadorUnico(),
			      norma1.getIdentificadorUnico());
		
		//Artigo1 -> Artigo2 (EN->EN)
		assertEquals( citacaoTextLegManager.getCitacoesTextLeg().get(2).getElementoNormaOrigem().getIdentificadorUnico(),
				  artigo1.getIdentificadorUnico());
		assertEquals( citacaoTextLegManager.getCitacoesTextLeg().get(2).getElementoNormaDestino().getIdentificadorUnico(),
			      artigo2.getIdentificadorUnico());
		
		//Norma1 -> Artigo1 (N->EN)
		assertEquals( citacaoTextLegManager.getCitacoesTextLeg().get(3).getNormaOrigem().getIdentificadorUnico(),
				  norma1.getIdentificadorUnico());
		assertEquals( citacaoTextLegManager.getCitacoesTextLeg().get(3).getElementoNormaDestino().getIdentificadorUnico(),
					artigo1.getIdentificadorUnico());
		
		//Norma1 -> DocJud1 (N->DJ)
		assertEquals( citacaoTextLegManager.getCitacoesTextLeg().get(4).getNormaOrigem().getIdentificadorUnico(),
				  norma1.getIdentificadorUnico());
		assertEquals( citacaoTextLegManager.getCitacoesTextLeg().get(4).getDocumentoJuridicoDestino().getIdentificadorUnico(),
			      docJud1.getIdentificadorUnico());
		
		//Artigo1 -> DocJud1 (EN->DJ)
		assertEquals( citacaoTextLegManager.getCitacoesTextLeg().get(5).getElementoNormaOrigem().getIdentificadorUnico(),
				  artigo1.getIdentificadorUnico());
		assertEquals( citacaoTextLegManager.getCitacoesTextLeg().get(5).getDocumentoJuridicoDestino().getIdentificadorUnico(),
			      docJud1.getIdentificadorUnico());
		
		/*
		 * Atributos das Normas, ElementosNormas e DocumentoJuridico
		 * Por algum motivo obscuro, ao pegar a lista de normas e elementosnorma, eles nao vem na ordem
		 * que foram inseridos, dai a diferenca da ordem que foi inserida para a ordem que 
		 * estao sendo avaliados os atributos.
		 */
		//NORMA1
		assertEquals( normaManager.getNormas().get(1).getIdentificadorUnico(),
					  norma1.getIdentificadorUnico());
		assertEquals( normaManager.getNormas().get(1).getCitacoesFeitas().get(0).getData(),
				  	  "16/11/2009");
		assertEquals( normaManager.getNormas().get(1).getCitacoesRecebidasTextLeg().get(0).getData(),
	  	  			   "19/11/2009");
		//NORMA2
		assertEquals( normaManager.getNormas().get(0).getIdentificadorUnico(),
				      norma2.getIdentificadorUnico());
		assertEquals( normaManager.getNormas().get(0).getCitacoesRecebidasTextLeg().get(0).getData(),
					  "16/11/2009");
		//ELEMENTONORMA1
		assertEquals( elementoNormaManager.getElementosNorma().get(1).getIdentificadorUnico(),
					  artigo1.getIdentificadorUnico());
		assertEquals( elementoNormaManager.getElementosNorma().get(1).getCitacoesFeitas().get(0).getData(),
				  	  "19/11/2009");
		assertEquals( elementoNormaManager.getElementosNorma().get(1).getCitacoesFeitas().get(1).getData(),
	  	  			  "20/11/2009");
		assertEquals( elementoNormaManager.getElementosNorma().get(1).getCitacoesFeitas().get(2).getData(),
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
		Norma norma1 = new Norma("ementaN1", "autoriaN1", "localN1", "identificadorUnicoN1", "tipoN1", 
				"dataPublicacaoN1", "vigenciaN1");
		ElementoNorma artigo1 = new ElementoNorma("textoArt1", "identificadorUnicoArt1", "tipoArt1", 
				"dataPublicacaoArt1", "vigenciaArt1");
		norma1.getElementosNorma().add(artigo1);
		Norma norma2 = new Norma("ementaN2", "autoriaN2", "localN2", "identificadorUnicoN2", "tipoN2", 
				"dataPublicacaoN2", "vigenciaN2");
		ElementoNorma artigo2 = new ElementoNorma("textoArt2", "identificadorUnicoArt2", "tipoArt2", 
				"dataPublicacaoArt2", "vigenciaArt2");
		norma2.getElementosNorma().add(artigo2);
		normaManager.salvaNorma(norma1);
		normaManager.salvaNorma(norma2);
		
		/* Criando e persistindo o documento juridico */
		DocumentoJuridico docJud1 = new DocumentoJuridico();
		docJud1.setIdentificadorUnico("idUnico1");
		docJudManager.salvaDocumentoJuridico(docJud1);
		/* ---------- Verifica as cardinalidade das tabelas dos elementos envolvidos ----------*/
		assertEquals(2, normaManager.getNormas().size() );
		assertEquals(2, elementoNormaManager.getElementosNorma().size() );
		assertEquals(1, docJudManager.getDocumentosJuridicos().size() );
		
		/* Criando as CitacoesTextLeg */
		//Norma1 -> Norma2  (N->N)
		citacaoTextLegManager.criaCitacaoTextLeg(norma1, norma2, "16/11/2009");
		//Artigo1 -> Artigo2 (EN->EN)
		citacaoTextLegManager.criaCitacaoTextLeg(artigo1, artigo2, "17/11/2009");
		//Artigo1 -> DocJud1 (N->DJ)
		citacaoTextLegManager.criaCitacaoTextLeg(artigo1, docJud1, "18/11/2009");
		
		/* Verificando se os atributos foram persistidos corretamente */
		//Quantidade de citacoesTextLeg
		assertEquals(3, citacaoTextLegManager.getCitacoesTextLeg().size() );
		/*
		 * Por algum motivo obscuro, ao pegar a lista elementos, eles nao vem na ordem
		 * que foram inseridos, dai a diferenca da ordem que foi inserida para a ordem que 
		 * estao sendo avaliados os atributos.
		 */
		//Norma1
		assertEquals(norma1.getIdentificadorUnico(), normaManager.getNormas().get(0).getIdentificadorUnico() );
		assertEquals(1, normaManager.getNormas().get(0).getCitacoesFeitas().size() );
		//Norma2
		assertEquals(norma2.getIdentificadorUnico(), normaManager.getNormas().get(1).getIdentificadorUnico() );
		assertEquals(1, normaManager.getNormas().get(1).getCitacoesRecebidasTextLeg().size() );
		//Artigo1
		assertEquals(artigo1.getIdentificadorUnico(), elementoNormaManager.getElementosNorma().get(1).getIdentificadorUnico() );
		assertEquals(2, elementoNormaManager.getElementosNorma().get(1).getCitacoesFeitas().size() );
		//Artigo2
		assertEquals(artigo2.getIdentificadorUnico(), elementoNormaManager.getElementosNorma().get(0).getIdentificadorUnico() );
		assertEquals(1, elementoNormaManager.getElementosNorma().get(0).getCitacoesRecebidasTextLeg().size() );
		//DocJud1
		assertEquals(1, docJudManager.getDocumentosJuridicos().get(0).getCitacoesRecebidasTextLeg().size() );
				
		/* ---------- Removendo as citacoes ----------*/
		/*
		 * Norma1 -> Norma2  (N->N)
		 */
		assertEquals("16/11/2009", citacaoTextLegManager.getCitacoesTextLeg().get(0).getData() );
		citacaoTextLegManager.removeCitacaoTextLeg( citacaoTextLegManager.getCitacoesTextLeg().get(0) );
		assertEquals(2, citacaoTextLegManager.getCitacoesTextLeg().size() );
		//Norma1
		assertEquals(norma1.getIdentificadorUnico(), normaManager.getNormas().get(0).getIdentificadorUnico() );
		assertEquals(0, normaManager.getNormas().get(0).getCitacoesFeitas().size() );
		//Norma2
		assertEquals(norma2.getIdentificadorUnico(), normaManager.getNormas().get(1).getIdentificadorUnico() );
		assertEquals(0, normaManager.getNormas().get(1).getCitacoesRecebidasTextLeg().size() );
		//Artigo1
		assertEquals(artigo1.getIdentificadorUnico(), elementoNormaManager.getElementosNorma().get(0).getIdentificadorUnico() );
		assertEquals(2, elementoNormaManager.getElementosNorma().get(0).getCitacoesFeitas().size() );
		//Artigo2
		assertEquals(1, elementoNormaManager.getElementosNorma().get(1).getCitacoesRecebidasTextLeg().size() );
		//DocJud1
		assertEquals(1, docJudManager.getDocumentosJuridicos().get(0).getCitacoesRecebidasTextLeg().size() );
		/*
		 * Norma1 -> DocJud1 (N->DJ)
		 */
		assertEquals("18/11/2009", citacaoTextLegManager.getCitacoesTextLeg().get(0).getData() );
		citacaoTextLegManager.removeCitacaoTextLeg( citacaoTextLegManager.getCitacoesTextLeg().get(0) );
		assertEquals(1, citacaoTextLegManager.getCitacoesTextLeg().size() );
		//Norma1
		assertEquals(norma1.getIdentificadorUnico(), normaManager.getNormas().get(0).getIdentificadorUnico() );
		assertEquals(0, normaManager.getNormas().get(0).getCitacoesFeitas().size() );
		//Norma2
		assertEquals(norma2.getIdentificadorUnico(), normaManager.getNormas().get(1).getIdentificadorUnico() );
		assertEquals(0, normaManager.getNormas().get(1).getCitacoesRecebidasTextLeg().size() );
		//Artigo1
		assertEquals(artigo1.getIdentificadorUnico(), elementoNormaManager.getElementosNorma().get(1).getIdentificadorUnico() );
		assertEquals(1, elementoNormaManager.getElementosNorma().get(1).getCitacoesFeitas().size() );
		//Artigo2
		assertEquals(artigo2.getIdentificadorUnico(), elementoNormaManager.getElementosNorma().get(0).getIdentificadorUnico() );
		assertEquals(1, elementoNormaManager.getElementosNorma().get(0).getCitacoesRecebidasTextLeg().size() );
		//DocJud1
		assertEquals(0, docJudManager.getDocumentosJuridicos().get(0).getCitacoesRecebidasTextLeg().size() );

		/*
		 * Artigo1 -> Artigo2 (EN->EN)
		 */
		assertEquals("17/11/2009", citacaoTextLegManager.getCitacoesTextLeg().get(0).getData() );
		citacaoTextLegManager.removeCitacaoTextLeg( citacaoTextLegManager.getCitacoesTextLeg().get(0) );
		assertEquals(0, citacaoTextLegManager.getCitacoesTextLeg().size() );
		//Norma1
		assertEquals(norma1.getIdentificadorUnico(), normaManager.getNormas().get(0).getIdentificadorUnico() );
		assertEquals(0, normaManager.getNormas().get(0).getCitacoesFeitas().size() );
		//Norma2
		assertEquals(norma2.getIdentificadorUnico(), normaManager.getNormas().get(1).getIdentificadorUnico() );
		assertEquals(0, normaManager.getNormas().get(1).getCitacoesRecebidasTextLeg().size() );
		//Artigo1
		assertEquals(artigo1.getIdentificadorUnico(), elementoNormaManager.getElementosNorma().get(0).getIdentificadorUnico() );
		assertEquals(0, elementoNormaManager.getElementosNorma().get(0).getCitacoesFeitas().size() );
		//Artigo2
		assertEquals(artigo2.getIdentificadorUnico(), elementoNormaManager.getElementosNorma().get(1).getIdentificadorUnico() );
		assertEquals(0, elementoNormaManager.getElementosNorma().get(1).getCitacoesRecebidasTextLeg().size() );
		//DocJud1
		assertEquals(0, docJudManager.getDocumentosJuridicos().get(0).getCitacoesRecebidasTextLeg().size() );
	}
}
