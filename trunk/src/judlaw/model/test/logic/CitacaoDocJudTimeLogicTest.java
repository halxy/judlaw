/* 
 * Universidade Federal de Campina Grande
 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas	
 */
package judlaw.model.test.logic;

import static org.junit.Assert.assertEquals;
import judlaw.model.bean.docjud.DocumentoJuridico;
import judlaw.model.bean.law.ElementoNorma;
import judlaw.model.bean.law.Norma;
import judlaw.model.dbmanager.docjud.DocJudManager;
import judlaw.model.dbmanager.law.ElementoNormaManager;
import judlaw.model.dbmanager.law.NormaManager;
import judlaw.model.dbmanager.ref.CitacaoDocJudManager;
import judlaw.model.dbmanager.ref.ReferenciaManager;
import judlaw.model.logic.time.CitacaoDocJudTimeLogic;

import org.junit.Before;
import org.junit.Test;

/**
 * Classe CitacaoDocJudTimeLogicTest - testes que verificam a consistencia temporal das citacoes
 * feitas por documentos juridicos
 * @author Halley Freitas
 *
 */
public class CitacaoDocJudTimeLogicTest {
	
	private NormaManager normaManager = NormaManager.getInstance();
	private ElementoNormaManager elementoNormaManager = ElementoNormaManager.getInstance();
	private DocJudManager docJudManager = DocJudManager.getInstance();
	private CitacaoDocJudTimeLogic citacaoDocJudTimeLogic = CitacaoDocJudTimeLogic.getInstance();
	private CitacaoDocJudManager citacaoDocJudManager = CitacaoDocJudManager.getInstance();
	
	@Before
	public void setUp() throws Exception {
		ReferenciaManager.getInstance().removeReferencias();
		normaManager.removeNormas();
		elementoNormaManager.removeElementosNorma();
		docJudManager.removeDocumentosJuridicos();
	}

	@Test
	/**
	 * Test method for {@link judlaw.model.logic.time.DocJudTimeLogic#inconsistenciaTemporalSimples(DocumentoJuridico docJud)}.
	 */
	public void testInconsistenciaTemporalSimples() throws Exception {
		/* ---------- Documento Juridico ----------*/
		DocumentoJuridico docJud1 = new DocumentoJuridico();
		docJud1.setIdentificadorUnico("idUnico1");
		docJud1.setDataExpedicao("10/10/2011");
		docJudManager.salvaDocumentoJuridico(docJud1);
		
		/* ---------- Norma e ElementosNorma ----------*/
		ElementoNorma artigo1 = new ElementoNorma("textoArt1", "identificadorUnicoArt1", "tipoArt1", 
														"dataPublicacaoArt1", "vigenciaArt1");
		ElementoNorma paragrafo1 = new ElementoNorma("textoParagrafo1", "identificadorUnicoParagrafo1", "tipoParagrafo1", 
				"dataPublicacaoParagrafo1", "10/10/2010-10/10/2011");
		ElementoNorma inciso1 = new ElementoNorma("textoInciso1", "identificadorUnicoInciso1", "tipoInciso1", 
				"dataPublicacaoInciso1", "vigenciaInciso1");
		ElementoNorma inciso2 = new ElementoNorma("textoInciso2", "identificadorUnicoInciso2", "tipoInciso2", 
				"dataPublicacaoInciso2", "10/10/2010-10/09/2011");
		
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
								"dataPublicacaoN1", "10/10/2010-10/10/2012");
		norma1.getElementosNorma().add(artigo1);
		norma1.getElementosNorma().add(artigo2);
		normaManager.salvaNorma(norma1);
		
		Norma norma2 = new Norma("epigrafeN2", "ementaN2", "autoriaN2", "localN2", "identificadorUnicoN2", "tipoN2", 
				"dataPublicacaoN2", "10/10/2010-09/10/2011");
		normaManager.salvaNorma(norma2);
		
		/*
		 *                         Norma1              Norma2
		 *                         /    \
		 *                       Art1   Art2
		 *                       /      /  \
		 *                      Par1  Par2  Par3
		 *                     /   \
		 *                  Inc1   Inc2 
		 */
		/* ---------- Verifica as cardinalidade das tabelas dos elementos envolvidos ----------*/
		assertEquals( 1, docJudManager.getDocumentosJuridicos().size() );
		assertEquals( 2, normaManager.getNormas().size() );
		assertEquals( 7, elementoNormaManager.getElementosNorma().size() );
		/*
		 *   Elementos envolvidos nas citacoes e suas vigencias.
		 *   As citacoes foram feitas no mesmo documento, em 10/10/2011
		 *   Norma1: 10/10/2010-10/10/2012 (OK)
		 *   Norma2: 10/10/2010-09/10/2011 (Vigencia expirada)
		 *   Par1: 10/10/2010-10/10/2011 (Vigora ate o dia da citacao)
		 *   Inc2: 10/10/2010-10/09/2011 (Vigencia expirada)
		 */
		/* Criando as CitacoesDocJud */
		//DocJud1 -> Norma1
		citacaoDocJudManager.criaCitacaoDocJud(docJud1, norma1);
		//DocJud1 -> Norma2 
		citacaoDocJudManager.criaCitacaoDocJud(docJud1, norma2);
		//DocJud1 -> Paragrafo1
		citacaoDocJudManager.criaCitacaoDocJud(docJud1, paragrafo1);
		//DocJud1 -> Inciso2
		citacaoDocJudManager.criaCitacaoDocJud(docJud1, inciso2);
		
		/* Verificando se os atributos foram persistidos corretamente */
		//Quantidade de citacoes
		assertEquals(4, citacaoDocJudManager.getCitacoesDocJud().size() );
		
		/* Verificando se as inconsistencias foram detectadas corretamente */
		assertEquals( docJud1.getIdentificadorUnico(), 
				      docJudManager.getDocumentosJuridicos().get(0).getIdentificadorUnico() );
		DocumentoJuridico docJud1BD = docJudManager.getDocumentosJuridicos().get(0);
		//Norma2 e Inciso2
		assertEquals( 2, citacaoDocJudTimeLogic.inconsistenciaTemporalSimples(docJud1BD).size());
		assertEquals( norma2.getIdentificadorUnico(), 
				      citacaoDocJudTimeLogic.inconsistenciaTemporalSimples(docJud1BD).get(0).
				                                             getNormaDestino().getIdentificadorUnico());
		assertEquals( inciso2.getIdentificadorUnico(), 
			      citacaoDocJudTimeLogic.inconsistenciaTemporalSimples(docJud1BD).get(1).
			                                             getElementoNormaDestino().getIdentificadorUnico());
	}
}