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
import judlaw.model.logic.time.CitacaoTextLegTimeLogic;
import judlaw.model.persistence.dbmanager.docjud.DocJudManager;
import judlaw.model.persistence.dbmanager.law.ElementoNormaManager;
import judlaw.model.persistence.dbmanager.law.NormaManager;
import judlaw.model.persistence.dbmanager.ref.CitacaoTextLegManager;
import judlaw.model.persistence.dbmanager.ref.ReferenciaManager;

import org.junit.Before;
import org.junit.Test;

/**
 * Classe CitacaoTextLegTimeLogicTest - testes que verificam a consistencia temporal das citacoes
 * feitas por textos legais
 * @author Halley Freitas
 *
 */
public class CitacaoTextLegTimeLogicTest {
	
	private NormaManager normaManager = NormaManager.getInstance();
	private ElementoNormaManager elementoNormaManager = ElementoNormaManager.getInstance();
	private DocJudManager docJudManager = DocJudManager.getInstance();
	private CitacaoTextLegManager citacaoTLManager = CitacaoTextLegManager.getInstance();
	private CitacaoTextLegTimeLogic citacaoTLTimeLogic = CitacaoTextLegTimeLogic.getInstance();
	
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
														"10/10/2010", "vigenciaArt1");
		ElementoNorma paragrafo1 = new ElementoNorma("textoParagrafo1", "identificadorUnicoParagrafo1", "tipoParagrafo1", 
				"10/10/2010", "10/10/2010-10/10/2011");
		ElementoNorma inciso1 = new ElementoNorma("textoInciso1", "identificadorUnicoInciso1", "tipoInciso1", 
				"10/10/2010", "vigenciaInciso1");
		ElementoNorma inciso2 = new ElementoNorma("textoInciso2", "identificadorUnicoInciso2", "tipoInciso2", 
				"10/10/2010", "10/10/2010-10/09/2011");
		
		paragrafo1.getElementosNorma().add(inciso1);
		paragrafo1.getElementosNorma().add(inciso2);
		artigo1.getElementosNorma().add(paragrafo1);
		
		ElementoNorma artigo2 = new ElementoNorma("textoArt2", "identificadorUnicoArt2", "tipoArt2", 
				"10/10/2010", "vigenciaArt2");
		ElementoNorma paragrafo2 = new ElementoNorma("textoParagrafo2", "identificadorUnicoParagrafo2", "tipoParagrafo2", 
				"10/10/2010", "vigenciaParagrafo2");
		ElementoNorma paragrafo3 = new ElementoNorma("textoParagrafo3", "identificadorUnicoParagrafo3", "tipoParagrafo3", 
				"10/10/2010", "vigenciaParagrafo3");
		artigo2.getElementosNorma().add(paragrafo2);
		artigo2.getElementosNorma().add(paragrafo3);
		
		Norma norma1 = new Norma("ementaN1", "autoriaN1", "localN1", "identificadorUnicoN1", "tipoN1", 
								"10/10/2010", "10/10/2010-10/10/2012");
		norma1.getElementosNorma().add(artigo1);
		norma1.getElementosNorma().add(artigo2);
		normaManager.salvaNorma(norma1);
		
		Norma norma2 = new Norma("ementaN2", "autoriaN2", "localN2", "identificadorUnicoN2", "tipoN2", 
				"10/10/2010", "10/10/2010-09/10/2011");
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
		assertEquals( 2, normaManager.getNormas().size() );
		assertEquals( 7, elementoNormaManager.getElementosNorma().size() );
		/*
		 *   Elementos envolvidos nas citacoes e suas vigencias.
		 *   As citacoes foram feitas no mesmo documento, em 10/10/2011
		 *   Norma2: 10/10/2010-09/10/2011 (Vigencia expirada)
		 *   Par1: 10/10/2010-10/10/2011 (Vigora ate o dia da citacao)
		 *   Inc2: 10/10/2010-10/09/2011 (Vigencia expirada)
		 */
		/* Criando as CitacoesTextLeg */
		//Norma1 -> Norma2
		citacaoTLManager.criaCitacaoTextLeg(norma1, norma2, "10/10/2011");
		//Norma1 -> Par1
		citacaoTLManager.criaCitacaoTextLeg(norma1, paragrafo1, "10/10/2011");
		//Norma1 -> Inc2
		citacaoTLManager.criaCitacaoTextLeg(norma1, inciso2, "10/10/2011");
		
		/* Verificando se os atributos foram persistidos corretamente */
		//Quantidade de citacoes
		assertEquals(3, citacaoTLManager.getCitacoesTextLeg().size() );
		
		/* Verificando se as inconsistencias foram detectadas corretamente */
		assertEquals( norma1.getIdentificadorUnico(), 
				      citacaoTLManager.getCitacoesTextLeg().get(0).getNormaOrigem().getIdentificadorUnico() );
		assertEquals( norma1.getIdentificadorUnico(), 
			      	  citacaoTLManager.getCitacoesTextLeg().get(1).getNormaOrigem().getIdentificadorUnico() );
		assertEquals( norma1.getIdentificadorUnico(), 
			          citacaoTLManager.getCitacoesTextLeg().get(2).getNormaOrigem().getIdentificadorUnico() );
		Norma norma1BD = citacaoTLManager.getCitacoesTextLeg().get(0).getNormaOrigem();
		//Norma2 e Inciso2
		assertEquals( 2, citacaoTLTimeLogic.inconsistenciaTemporal(norma1BD).size());
		assertEquals( norma2.getIdentificadorUnico(), 
				citacaoTLTimeLogic.inconsistenciaTemporal(norma1BD).get(0).
				                                             getNormaDestino().getIdentificadorUnico());
		assertEquals( inciso2.getIdentificadorUnico(), 
				citacaoTLTimeLogic.inconsistenciaTemporal(norma1BD).get(1).
			                                             getElementoNormaDestino().getIdentificadorUnico());
	}
}
