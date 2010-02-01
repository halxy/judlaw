package judlaw.model.test.logic;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import judlaw.model.bean.law.ElementoNorma;
import judlaw.model.bean.law.Norma;
import judlaw.model.logic.time.LawTimeLogic;
import judlaw.model.persistence.dbmanager.docjud.DocJudManager;
import judlaw.model.persistence.dbmanager.law.ElementoNormaManager;
import judlaw.model.persistence.dbmanager.law.NormaManager;
import judlaw.model.persistence.dbmanager.ref.AlteracaoManager;
import judlaw.model.persistence.dbmanager.ref.ReferenciaManager;
import judlaw.model.util.Constantes;

import org.junit.Before;
import org.junit.Test;

public class LawTimeLogicTest {

	private LawTimeLogic lawTimeLogic = LawTimeLogic.getInstance();
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
		docJudManager.removeDocumentosJuridicos();	
	}
	
	@Test
	public void testElementosNormaValidosData() {
		// DataLimite
		String data = "11/11/2011";
		// ElementosNorma
		ElementoNorma ele1 = new ElementoNorma();
		ele1.setVigencia("10/10/2010-11/11/2011");
		ElementoNorma ele2 = new ElementoNorma();
		ele2.setVigencia("10/10/2010-99/99/9999");
		ElementoNorma ele3 = new ElementoNorma();
		ele3.setVigencia("10/10/2010-11/11/2010");
		ElementoNorma ele4 = new ElementoNorma();
		ele4.setVigencia("10/10/2010-10/11/2011");
		// Norma
		Norma norma1 = new Norma();
		norma1.getElementosNorma().add( ele1 );
		norma1.getElementosNorma().add( ele2 );
		norma1.getElementosNorma().add( ele3 );
		norma1.getElementosNorma().add( ele4 );
		// Testes
		assertEquals( 4, norma1.getElementosNorma().size() );
		try {
			norma1.setElementosNorma( lawTimeLogic.elementosNormaValidosData(norma1.getElementosNorma(), data));
			/*
			 * Espera-se que apenas dois elementos restem: ele1 e ele2;
			 */
			assertEquals( 2, norma1.getElementosNorma().size() );
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals( ele1.getVigencia(), norma1.getElementosNorma().get(0).getVigencia() );
		assertEquals( ele2.getVigencia(), norma1.getElementosNorma().get(1).getVigencia() );
	}
	
	@Test
	public void testFilhosValidosRecursivo() {
		/*
		 *            A r t i g o 1
		 *           /     |       \
		 *    Par1(V)   Par2(INV) Par3(V)
		 *   /    \        |         \
		 * IN1(V)IN2(INV) IN3(INV)   IN4(V)
		 */
		
		// DataLimite
		String data = "11/11/2011";
		// ElementosNorma
		ElementoNorma in1 = new ElementoNorma();
		in1.setVigencia("10/10/2010-99/99/9999");
		ElementoNorma in2 = new ElementoNorma();
		in2.setVigencia("10/10/2010-10/10/2010");
		ElementoNorma in3 = new ElementoNorma();
		in3.setVigencia("10/10/2010-10/10/2010");
		ElementoNorma in4 = new ElementoNorma();
		in4.setVigencia("10/10/2010-11/11/2011");
		
		ElementoNorma par1 = new ElementoNorma();
		par1.setVigencia("10/10/2010-99/99/9999");
		par1.getElementosNorma().add(in1);
		par1.getElementosNorma().add(in2);
		assertEquals( 2, par1.getElementosNorma().size() );
		
		ElementoNorma par2 = new ElementoNorma();
		par2.setVigencia("10/10/2010-10/10/2010");
		par2.getElementosNorma().add(in3);
		assertEquals( 1, par2.getElementosNorma().size() );
		
		ElementoNorma par3 = new ElementoNorma();
		par3.setVigencia("10/10/2010-99/99/9999");
		par3.getElementosNorma().add(in4);
		assertEquals( 1, par3.getElementosNorma().size() );
		
		ElementoNorma art1 = new ElementoNorma();
		art1.setVigencia("10/10/2010-99/99/9999");
		art1.getElementosNorma().add(par1);
		art1.getElementosNorma().add(par2);
		art1.getElementosNorma().add(par3);
		assertEquals( 3, art1.getElementosNorma().size() );
		
		try {
			lawTimeLogic.filhosValidosRecursivo(art1, data);
			//Verificando se os filhos foram validados recursivamente
			assertEquals( 2, art1.getElementosNorma().size() );
			
			assertEquals( 1, par1.getElementosNorma().size() );
			assertEquals( "10/10/2010-99/99/9999", par1.getElementosNorma().get(0).getVigencia() );
			
			assertEquals( 1, par3.getElementosNorma().size() );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testNormaMaisAtual() {
		/*
		 * Normas
		 */
		Norma norma1 = new Norma();
		norma1.setIdentificadorUnico("norma1");
		norma1.setDataPublicacao("10/10/2009");	
		Norma norma2 = new Norma();
		norma2.setIdentificadorUnico("norma2");
		norma2.setDataPublicacao("11/10/2009");	
		Norma norma3 = new Norma();
		norma3.setIdentificadorUnico("norma3");
		norma3.setDataPublicacao("12/10/2009");	
		Norma norma4 = new Norma();
		norma4.setIdentificadorUnico("norma4");
		norma4.setDataPublicacao("13/10/2009");
		/*
		 * Lista
		 */
		ArrayList<Norma> normas = new ArrayList<Norma>();
		normas.add(norma1);
		normas.add(norma2);
		normas.add(norma3);
		normas.add(norma4);
		
		try {
			assertEquals( "norma4", lawTimeLogic.normaMaisAtual(normas).getIdentificadorUnico());
		} catch (Exception e) {
			e.printStackTrace();
		}
		//Atualizando a lista
		normas.clear();
		norma1.setDataPublicacao("10/10/2009");	
		norma2.setDataPublicacao("10/11/2009");
		normas.add(norma1);
		normas.add(norma2);
		try {
			assertEquals( "norma2", lawTimeLogic.normaMaisAtual(normas).getIdentificadorUnico());
		} catch (Exception e) {
			e.printStackTrace();
		}
		//Atualizando a lista
		normas.clear();
		norma1.setDataPublicacao("10/10/2010");	
		norma2.setDataPublicacao("10/11/2009");
		normas.add(norma1);
		normas.add(norma2);
		try {
			assertEquals( "norma1", lawTimeLogic.normaMaisAtual(normas).getIdentificadorUnico());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Testa a reconstrucao de uma norma, testando ja no banco de dados
	 */
	@Test
	public void testReconstroiNorma() {
		/*
		 *       Norma1(t1)         Norma1(t2)      Norma1(t3)          Norma2;
		 *        /      \          /     \         /      \
		 *      Art1(t1)Art1(t2) Art1(t1)Art1(t2) Art1(t1)Art1(t2)
		 *       /    \          /    \            /    \
		 *  Par1(t1) Par1(t3)Par1(t1) Par1(t3)  Par1(t1) Par1(t3)
		 */
		/* ---------- Verifica se as listas estao vazias ----------*/
		assertEquals( 0, normaManager.getNormas().size() );
		assertEquals( 0, elementoNormaManager.getElementosNorma().size() );
		
		/* ---------- Criando Norma1, Artigo1 e Norma2 ----------*/
		Norma norma1 = new Norma("ementaN1", "autoriaN1", "localN1", "identificadorUnicoN1", "tipoN1", 
				"10/10/2010", "10/10/2010-99/99/9999");
		ElementoNorma artigo1 = new ElementoNorma("textoArt1", "identificadorUnicoArt1", "tipoArt1", 
				"10/10/2010", "10/10/2010-99/99/9999");
		ElementoNorma paragrafo1 = new ElementoNorma("textoParagrafo1", "identificadorUnicoParagrafo1", "tipoParagrafo1", 
				"10/10/2010", "10/10/2010-99/99/9999");
		artigo1.getElementosNorma().add(paragrafo1);
		norma1.getElementosNorma().add( artigo1 );
		normaManager.salvaNorma(norma1);
		Norma norma2 = new Norma("ementaN2", "autoriaN2", "localN2", "identificadorUnicoN2", "tipoN2", 
				"11/11/2011", "11/11/2011-99/99/9999");
		normaManager.salvaNorma( norma2 );
		
		/* ---------- Cardinalidades ----------*/
		assertEquals( 2, normaManager.getNormas().size() );
		assertEquals( 2, elementoNormaManager.getElementosNorma().size() );
		
		/* 
		 * AlteracaoModificacao1 = Norma2 modifica Artigo1
		 */
		ElementoNorma artigo1Mod = new ElementoNorma();
		artigo1Mod.setTexto("textoArt1Novo");
		alteracaoManager.criaAlteracaoModificacao(norma2, artigo1, artigo1Mod, "11/11/2011", Constantes.MAIS_RESTRITIVA);
		
		/* ---------- Cardinalidades ----------*/
		Norma norma1BD = (Norma) normaManager.selectNormaPorAtributo("identificadorUnico", 
																		norma1.getIdentificadorUnico()).get(0);
		assertEquals( 2, norma1BD.getElementosNorma().size() );
		assertEquals( norma1BD.getElementosNorma().get(0).getNormasPai().get(0).getIdentificadorUnico(),
				      norma1BD.getElementosNorma().get(1).getNormasPai().get(0).getIdentificadorUnico());
		
		/* ---------- Adicionando Normat2 e Normat3 ----------*/
//		Norma normat2 = (Norma) normaManager.selectNormaPorAtributo("identificadorUnico", 
//                			norma1.getIdentificadorUnico()).get(0);
		Norma normat2 = new Norma();
		normat2.setLocal("localN1t2");
		alteracaoManager.criaAlteracaoModificacao(norma2, norma1, normat2, "11/11/2011", Constantes.MAIS_RESTRITIVA);
		
//		Norma normat3 = (Norma) normaManager.selectNormaPorAtributo("identificadorUnico", 
//    			norma1.getIdentificadorUnico()).get(0);
		Norma normat3 = new Norma();
		normat3.setLocal("localN1t3");
		alteracaoManager.criaAlteracaoModificacao(norma2, norma1, normat3, "12/12/2012", Constantes.MAIS_RESTRITIVA);
		
		/* ---------- Cardinalidades ----------*/
		assertEquals( 4, normaManager.getNormas().size() );
		assertEquals( 3, elementoNormaManager.getElementosNorma().size() );
		
		/* ---------- Verificando os filhos das Normas em t1, t2 e t3 ----------*/
		assertEquals(3, normaManager.selectNormaPorAtributo("identificadorUnico", 
                												norma1.getIdentificadorUnico()).size());
		
		/* ---------- Verificando os pais de Artigo ----------*/
		assertEquals(2, elementoNormaManager.selectElementoPorAtributo("identificadorUnico", 
				                                                        artigo1.getIdentificadorUnico()).size());
		ElementoNorma artt1BD = (ElementoNorma) elementoNormaManager.selectElementoPorAtributo("identificadorUnico", 
                artigo1.getIdentificadorUnico()).get(0);
		assertEquals("textoArt1", artt1BD.getTexto());
		ElementoNorma artt1BD2 = (ElementoNorma) elementoNormaManager.selectElementoPorAtributo("identificadorUnico", 
                artigo1.getIdentificadorUnico()).get(1);
		assertEquals("textoArt1Novo", artt1BD2.getTexto());
		
		assertEquals(3, artt1BD.getNormasPai().size());
		assertEquals(3, artt1BD2.getNormasPai().size());
		assertEquals(1, artt1BD.getElementosNorma().size());
		assertEquals(1, artt1BD2.getElementosNorma().size());
		
		ElementoNorma par1BD = (ElementoNorma) elementoNormaManager.selectElementoPorAtributo("identificadorUnico", 
				paragrafo1.getIdentificadorUnico()).get(0);
		try {
			assertEquals("localN1", lawTimeLogic.reconstroiNorma(artt1BD).getLocal());
			assertEquals("localN1", lawTimeLogic.reconstroiNorma(par1BD).getLocal());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
