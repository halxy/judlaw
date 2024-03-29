/* 
 * Universidade Federal de Campina Grande
 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas	
 */
package judlaw.model.test.logic;

import static org.junit.Assert.assertEquals;
import judlaw.model.bean.law.ElementoNorma;
import judlaw.model.bean.law.Norma;
import judlaw.model.logic.time.AlteracaoTimeLogic;
import judlaw.model.persistence.dbmanager.docjud.DocJudManager;
import judlaw.model.persistence.dbmanager.law.ElementoNormaManager;
import judlaw.model.persistence.dbmanager.law.NormaManager;
import judlaw.model.persistence.dbmanager.ref.AlteracaoManager;
import judlaw.model.persistence.dbmanager.ref.ReferenciaManager;

import org.junit.Before;
import org.junit.Test;

/**
 * Classe AlteracaoTimeLogicTest - testes que verificam a consistencia temporal das alteracoes
 * feitas por textos legais
 * @author Halley Freitas
 *
 */
public class AlteracaoTimeLogicTest {
	
	private NormaManager normaManager = NormaManager.getInstance();
	private ElementoNormaManager elementoNormaManager = ElementoNormaManager.getInstance();
	private DocJudManager docJudManager = DocJudManager.getInstance();
	private AlteracaoManager alteracaoManager = AlteracaoManager.getInstance();
	private AlteracaoTimeLogic alteracaoTimeLogic = AlteracaoTimeLogic.getInstance();
	
	@Before
	public void setUp() throws Exception {
		ReferenciaManager.getInstance().removeReferencias();
		normaManager.removeLaw();
		docJudManager.removeDocumentosJuridicos();
	}

	@Test
	public void testInconsistenciaTemporal() throws Exception {
		/* ---------- Norma e ElementosNorma ----------*/
		ElementoNorma artigo1 = new ElementoNorma("textoArt1", "identificadorUnicoArt1", "tipoArt1", 
														"12/10/2010", "vigenciaArt1");
		ElementoNorma paragrafo1 = new ElementoNorma("textoParagrafo1", "identificadorUnicoParagrafo1", "tipoParagrafo1", 
				"11/10/2010", "10/10/2010-10/10/2011");
		ElementoNorma inciso1 = new ElementoNorma("textoInciso1", "identificadorUnicoInciso1", "tipoInciso1", 
				"12/10/2010", "vigenciaInciso1");
		ElementoNorma inciso2 = new ElementoNorma("textoInciso2", "identificadorUnicoInciso2", "tipoInciso2", 
				"09/10/2010", "10/10/2010-10/09/2011");
		
		paragrafo1.getElementosNorma().add(inciso1);
		paragrafo1.getElementosNorma().add(inciso2);
		artigo1.getElementosNorma().add(paragrafo1);
		
		ElementoNorma artigo2 = new ElementoNorma("textoArt2", "identificadorUnicoArt2", "tipoArt2", 
				"11/10/2010", "vigenciaArt2");
		ElementoNorma paragrafo2 = new ElementoNorma("textoParagrafo2", "identificadorUnicoParagrafo2", "tipoParagrafo2", 
				"11/10/2010", "vigenciaParagrafo2");
		ElementoNorma paragrafo3 = new ElementoNorma("textoParagrafo3", "identificadorUnicoParagrafo3", "tipoParagrafo3", 
				"11/10/2010", "vigenciaParagrafo3");
		artigo2.getElementosNorma().add(paragrafo2);
		artigo2.getElementosNorma().add(paragrafo3);
		
		Norma norma1 = new Norma("ementaN1", "autoriaN1", "localN1", "identificadorUnicoN1", "tipoN1", 
								"10/10/2010", "10/10/2010-10/10/2012");
		norma1.getElementosNorma().add(artigo1);
		norma1.getElementosNorma().add(artigo2);
		normaManager.salvaNorma(norma1);
		
		Norma norma2 = new Norma("ementaN2", "autoriaN2", "localN2", "identificadorUnicoN2", "tipoN2", 
				"10/10/2010", "10/10/2010-09/10/2011");
		ElementoNorma artigo3 = new ElementoNorma("textoArt3", "identificadorUnicoArt3", "tipoArt3", 
				"11/10/2010", "vigenciaArt3");
		norma2.getElementosNorma().add( artigo3 );
		normaManager.salvaNorma(norma2);
		
		/*
		 *                         Norma1              Norma2
		 *                         /    \              /
		 *                       Art1   Art2         Art3
		 *                       /      /  \
		 *                      Par1  Par2  Par3
		 *                     /   \
		 *                  Inc1   Inc2 
		 */
		/* ---------- Verifica as cardinalidade das tabelas dos elementos envolvidos ----------*/
		assertEquals( 2, normaManager.getNormas().size() );
		assertEquals( 8, elementoNormaManager.getElementosNorma().size() );
		/*
		 *   Elementos envolvidos nas citacoes e suas vigencias.
		 *   As citacoes foram feitas no mesmo documento, em 10/10/2011
		 *   Norma2: 10/10/2010-09/10/2011 (Vigencia expirada)
		 *   Par1: 10/10/2010-10/10/2011 (Vigora ate o dia da citacao)
		 *   Inc2: 10/10/2010-10/09/2011 (Vigencia expirada)
		 */
		/* Criando as Alteracoes */
		//Norma1 -> Norma2
		alteracaoManager.criaAlteracaoSimples(norma1, norma2, "10/10/2011", "", "");
		//Norma1 -> Par1
		alteracaoManager.criaAlteracaoSimples(norma1, paragrafo1, "10/10/2011", "", "");
		//Norma1 -> Inc2
		alteracaoManager.criaAlteracaoSimples(norma1, inciso2, "10/10/2011", "", "");
		
		/* Verificando se os atributos foram persistidos corretamente */
		//Quantidade de alteracoes
		assertEquals(3, alteracaoManager.getAlteracoes().size() );
		
		/* Verificando se as inconsistencias foram detectadas corretamente */
		assertEquals( norma1.getIdentificadorUnico(), 
				      alteracaoManager.getAlteracoes().get(0).getNormaOrigem().getIdentificadorUnico() );
		assertEquals( norma1.getIdentificadorUnico(), 
			      	  alteracaoManager.getAlteracoes().get(1).getNormaOrigem().getIdentificadorUnico() );
		assertEquals( norma1.getIdentificadorUnico(), 
			          alteracaoManager.getAlteracoes().get(2).getNormaOrigem().getIdentificadorUnico() );
		Norma norma1BD = alteracaoManager.getAlteracoes().get(0).getNormaOrigem();
		/* Verificando as inconsistencias temporais */
		assertEquals( 2, alteracaoTimeLogic.inconsistenciaTemporal(norma1BD).size());
		assertEquals( norma2.getIdentificadorUnico(), 
				alteracaoTimeLogic.inconsistenciaTemporal(norma1BD).get(0).
				                                             getNormaDestino().getIdentificadorUnico());
		assertEquals( inciso2.getIdentificadorUnico(), 
				alteracaoTimeLogic.inconsistenciaTemporal(norma1BD).get(1).
			                                             getElementoNormaDestino().getIdentificadorUnico());
		/* Verificando o refinamento da inconsistencia temporal */
		//1) O pai e filho de paragrafo1 tem datas mais atuais += 2;
		//2) Norma2 tem o artigo3 que eh mais atual += 1;
		//3) O pai de inciso2 tambem eh mais atual += 1;
		assertEquals(2, AlteracaoTimeLogic.getInstance().getElementosNormaFilhosAtualizados().size() );
	}
}
