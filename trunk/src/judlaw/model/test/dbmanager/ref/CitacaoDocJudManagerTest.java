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
import judlaw.model.bean.doutrina.Doutrina;
import judlaw.model.bean.law.ElementoNorma;
import judlaw.model.bean.law.Norma;
import judlaw.model.dbmanager.docjud.DocJudManager;
import judlaw.model.dbmanager.doutrina.DoutrinaManager;
import judlaw.model.dbmanager.law.ElementoNormaManager;
import judlaw.model.dbmanager.law.NormaManager;
import judlaw.model.dbmanager.ref.CitacaoDocJudManager;
import judlaw.model.dbmanager.ref.ReferenciaManager;

import org.junit.Before;
import org.junit.Test;

/**
 * Classe ReferenciaManagerTest - testes das operacoes da classe ReferenciaManager
 * @author Halley Freitas
 *
 */
public class CitacaoDocJudManagerTest {
	
	private ReferenciaManager refManager = ReferenciaManager.getInstance();
	private CitacaoDocJudManager citacaoDocJudManager = CitacaoDocJudManager.getInstance();
	private NormaManager normaManager = NormaManager.getInstance();
	private ElementoNormaManager elementoNormaManager = ElementoNormaManager.getInstance();
	private DocJudManager docJudManager = DocJudManager.getInstance();
	private DoutrinaManager doutrinaManager = DoutrinaManager.getInstance();
	
	@Before
	public void setUp() {
		/* ---------- Esvazia as listas envolvidas ----------*/
		refManager.removeReferencias();
		normaManager.removeLaw();
		docJudManager.removeDocumentosJuridicos();
		doutrinaManager.removeDoutrinas();
	}
	
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
		normaManager.salvaNorma(norma1);
		
		/* Criando e persistindo o documento juridico */
		DocumentoJuridico docJud1 = new DocumentoJuridico();
		docJud1.setIdentificadorUnico("idUnico1");
		DocumentoJuridico docJud2 = new DocumentoJuridico();
		docJud2.setIdentificadorUnico("idUnico2");
		docJudManager.salvaDocumentoJuridico(docJud1);
		docJudManager.salvaDocumentoJuridico(docJud2);
		
		/* Criando e persistindo uma doutrina */
		Doutrina doutrina = new Doutrina();
		doutrina.setAutor("autor");
		doutrinaManager.salvaDoutrina( doutrina );
		
		/* ---------- Verifica as cardinalidade das tabelas dos elementos envolvidos ----------*/
		assertEquals(1, normaManager.getNormas().size() );
		assertEquals(1, elementoNormaManager.getElementosNorma().size() );
		assertEquals(2, docJudManager.getDocumentosJuridicos().size() );
		assertEquals(1, doutrinaManager.getDoutrinas().size() );
		
		/* Criando as CitacoesDocJud */
		//DocJud1 -> DocJud2  (DJ->DJ)
		citacaoDocJudManager.criaCitacaoDocJud(docJud1, docJud2);
		//DocJud1 -> Norma1 (DJ->N)
		citacaoDocJudManager.criaCitacaoDocJud(docJud1, norma1);
		//DocJud1 -> Artigo1 (DJ->EN)
		citacaoDocJudManager.criaCitacaoDocJud(docJud1, artigo1);
		//DocJud1 -> Doutrina (DJ->DO)
		citacaoDocJudManager.criaCitacaoDocJud(docJud1, doutrina);
		
		/* Verificando se os atributos foram persistidos corretamente */
		//Quantidade de alteracoes
		assertEquals(4, citacaoDocJudManager.getCitacoesDocJud().size() );
		
		/*
		 * Atributos das Citacoes
		 */
		
		//DocJud1 -> DocJud2  (DJ->DJ)
		assertEquals( citacaoDocJudManager.getCitacoesDocJud().get(0).getDocumentoJuridicoOrigem().getIdentificadorUnico(),
				  	  docJud1.getIdentificadorUnico());
		assertEquals( citacaoDocJudManager.getCitacoesDocJud().get(0).getDocumentoJuridicoDestino().getIdentificadorUnico(),
			  	  	  docJud2.getIdentificadorUnico());
		
		//DocJud1 -> Norma1 (DJ->N)
		assertEquals( citacaoDocJudManager.getCitacoesDocJud().get(1).getDocumentoJuridicoOrigem().getIdentificadorUnico(),
			  	  docJud1.getIdentificadorUnico());
		assertEquals( citacaoDocJudManager.getCitacoesDocJud().get(1).getNormaDestino().getIdentificadorUnico(),
			      norma1.getIdentificadorUnico());
		
		//DocJud1 -> Artigo1 (DJ->EN)
		assertEquals( citacaoDocJudManager.getCitacoesDocJud().get(2).getDocumentoJuridicoOrigem().getIdentificadorUnico(),
			  	  docJud1.getIdentificadorUnico());
		assertEquals( citacaoDocJudManager.getCitacoesDocJud().get(2).getElementoNormaDestino().getIdentificadorUnico(),
			      artigo1.getIdentificadorUnico());
		
		//DocJud1 -> Doutrina (DJ->DO)
		assertEquals( citacaoDocJudManager.getCitacoesDocJud().get(3).getDocumentoJuridicoOrigem().getIdentificadorUnico(),
			  	  docJud1.getIdentificadorUnico());
		assertEquals( citacaoDocJudManager.getCitacoesDocJud().get(3).getDoutrinaDestino().getAutor(),
			      doutrina.getAutor());
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
		normaManager.salvaNorma(norma1);
		
		/* Criando e persistindo o documento juridico */
		DocumentoJuridico docJud1 = new DocumentoJuridico();
		docJud1.setIdentificadorUnico("idUnico1");
		docJud1.setDataExpedicao("16/11/2009");
		DocumentoJuridico docJud2 = new DocumentoJuridico();
		docJud2.setIdentificadorUnico("idUnico2");
		docJudManager.salvaDocumentoJuridico(docJud1);
		docJudManager.salvaDocumentoJuridico(docJud2);
		
		/* Criando e persistindo uma doutrina */
		Doutrina doutrina = new Doutrina();
		doutrina.setAutor("autor");
		doutrinaManager.salvaDoutrina( doutrina );
		
		/* ---------- Verifica as cardinalidade das tabelas dos elementos envolvidos ----------*/
		assertEquals(1, normaManager.getNormas().size() );
		assertEquals(1, elementoNormaManager.getElementosNorma().size() );
		assertEquals(2, docJudManager.getDocumentosJuridicos().size() );
		assertEquals(1, doutrinaManager.getDoutrinas().size() );
		
		/* Criando as CitacoesDocJud */
		//DocJud1 -> DocJud2 (DJ->DJ)
		citacaoDocJudManager.criaCitacaoDocJud(docJud1, docJud2);
		//DocJud1 -> Norma1 (DJ->N)
		citacaoDocJudManager.criaCitacaoDocJud(docJud1, norma1);
		//DocJud1 -> Artigo1 (DJ->EN)
		citacaoDocJudManager.criaCitacaoDocJud(docJud1, artigo1);
		//DocJud1 -> Doutrina (DJ->DO)
		citacaoDocJudManager.criaCitacaoDocJud(docJud1, doutrina);
		
		/* Verificando se os atributos foram persistidos corretamente */
		//Quantidade de citacoesDocJud
		assertEquals(4, citacaoDocJudManager.getCitacoesDocJud().size() );
		/*
		 * Por algum motivo obscuro, ao pegar a lista elementos, eles nao vem na ordem
		 * que foram inseridos, dai a diferenca da ordem que foi inserida para a ordem que 
		 * estao sendo avaliados os atributos.
		 */
		//Norma1
		assertEquals(norma1.getIdentificadorUnico(), normaManager.getNormas().get(0).getIdentificadorUnico() );
		assertEquals(1, normaManager.getNormas().get(0).getCitacoesRecebidasDocJud().size() );
		//Artigo1
		assertEquals(artigo1.getIdentificadorUnico(), elementoNormaManager.getElementosNorma().get(0).getIdentificadorUnico() );
		assertEquals(1, elementoNormaManager.getElementosNorma().get(0).getCitacoesRecebidasDocJud().size() );
		//DocJud1
		assertEquals(docJud1.getIdentificadorUnico(), docJudManager.getDocumentosJuridicos().get(1).getIdentificadorUnico() );
		assertEquals(4, docJudManager.getDocumentosJuridicos().get(1).getCitacoesFeitas().size() );
		//DocJud2
		assertEquals(docJud2.getIdentificadorUnico(), docJudManager.getDocumentosJuridicos().get(0).getIdentificadorUnico() );
		assertEquals(1, docJudManager.getDocumentosJuridicos().get(0).getCitacoesRecebidasDocJud().size() );
		//Doutrina
		assertEquals(doutrina.getAutor(), doutrinaManager.getDoutrinas().get(0).getAutor());
		assertEquals(1, doutrinaManager.getDoutrinas().get(0).getCitacoesRecebidasDocJud().size());
		
		/* ---------- Removendo as citacoes ----------*/
		/*
		 * DocJud1 -> DocJud2 (DJ->DJ)
		 */
		citacaoDocJudManager.removeCitacaoDocJud( citacaoDocJudManager.getCitacoesDocJud().get(0) );
		assertEquals(3, citacaoDocJudManager.getCitacoesDocJud().size() );
		//Norma1
		assertEquals(norma1.getIdentificadorUnico(), normaManager.getNormas().get(0).getIdentificadorUnico() );
		assertEquals(1, normaManager.getNormas().get(0).getCitacoesRecebidasDocJud().size() );
		//Artigo1
		assertEquals(artigo1.getIdentificadorUnico(), elementoNormaManager.getElementosNorma().get(0).getIdentificadorUnico() );
		assertEquals(1, elementoNormaManager.getElementosNorma().get(0).getCitacoesRecebidasDocJud().size() );
		//DocJud1
		assertEquals(docJud1.getIdentificadorUnico(), docJudManager.getDocumentosJuridicos().get(0).getIdentificadorUnico() );
		assertEquals(3, docJudManager.getDocumentosJuridicos().get(0).getCitacoesFeitas().size() );
		//DocJud2
		assertEquals(docJud2.getIdentificadorUnico(), docJudManager.getDocumentosJuridicos().get(1).getIdentificadorUnico() );
		assertEquals(0, docJudManager.getDocumentosJuridicos().get(1).getCitacoesRecebidasDocJud().size() );
		/*
		 * DocJud1 -> Norma1 (DJ->N)
		 */
		citacaoDocJudManager.removeCitacaoDocJud( citacaoDocJudManager.getCitacoesDocJud().get(0) );
		assertEquals(2, citacaoDocJudManager.getCitacoesDocJud().size() );
		//Norma1
		assertEquals(norma1.getIdentificadorUnico(), normaManager.getNormas().get(0).getIdentificadorUnico() );
		assertEquals(0, normaManager.getNormas().get(0).getCitacoesRecebidasDocJud().size() );
		//Artigo1
		assertEquals(artigo1.getIdentificadorUnico(), elementoNormaManager.getElementosNorma().get(0).getIdentificadorUnico() );
		assertEquals(1, elementoNormaManager.getElementosNorma().get(0).getCitacoesRecebidasDocJud().size() );
		//DocJud1
		assertEquals(docJud1.getIdentificadorUnico(), docJudManager.getDocumentosJuridicos().get(1).getIdentificadorUnico() );
		assertEquals(2, docJudManager.getDocumentosJuridicos().get(1).getCitacoesFeitas().size() );
		//DocJud2
		assertEquals(docJud2.getIdentificadorUnico(), docJudManager.getDocumentosJuridicos().get(0).getIdentificadorUnico() );
		assertEquals(0, docJudManager.getDocumentosJuridicos().get(0).getCitacoesRecebidasDocJud().size() );

		/*
		 * DocJud1 -> Doutrina (DJ->DO)
		 */
		citacaoDocJudManager.removeCitacaoDocJud( citacaoDocJudManager.getCitacoesDocJud().get(0) );
		assertEquals(0, doutrinaManager.getDoutrinas().get(0).getCitacoesRecebidasDocJud().size());
		//DocJud1
		assertEquals(docJud1.getIdentificadorUnico(), docJudManager.getDocumentosJuridicos().get(1).getIdentificadorUnico() );
		assertEquals(1, docJudManager.getDocumentosJuridicos().get(1).getCitacoesFeitas().size() );
		
		/*
		 * DocJud1 -> Artigo1 (DJ->EN)
		 */
		citacaoDocJudManager.removeCitacaoDocJud( citacaoDocJudManager.getCitacoesDocJud().get(0) );
		assertEquals(0, citacaoDocJudManager.getCitacoesDocJud().size() );
		//Norma1
		assertEquals(norma1.getIdentificadorUnico(), normaManager.getNormas().get(0).getIdentificadorUnico() );
		assertEquals(0, normaManager.getNormas().get(0).getCitacoesRecebidasDocJud().size() );
		//Artigo1
		assertEquals(artigo1.getIdentificadorUnico(), elementoNormaManager.getElementosNorma().get(0).getIdentificadorUnico() );
		assertEquals(0, elementoNormaManager.getElementosNorma().get(0).getCitacoesRecebidasDocJud().size() );
		//DocJud1
		assertEquals(docJud1.getIdentificadorUnico(), docJudManager.getDocumentosJuridicos().get(1).getIdentificadorUnico() );
		assertEquals(0, docJudManager.getDocumentosJuridicos().get(1).getCitacoesFeitas().size() );
		//DocJud2
		assertEquals(docJud2.getIdentificadorUnico(), docJudManager.getDocumentosJuridicos().get(0).getIdentificadorUnico() );
		assertEquals(0, docJudManager.getDocumentosJuridicos().get(0).getCitacoesRecebidasDocJud().size() );
	}
}
