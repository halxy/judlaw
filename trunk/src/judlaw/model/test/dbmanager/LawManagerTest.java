/* 
 * Universidade Federal de Campina Grande
 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas	
 */
package judlaw.model.test.dbmanager;

import static org.junit.Assert.assertEquals;
import judlaw.model.bean.docjud.DocumentoJuridico;
import judlaw.model.bean.law.ElementoNorma;
import judlaw.model.bean.law.Norma;
import judlaw.model.dbmanager.docjud.DocJudManager;
import judlaw.model.dbmanager.law.ElementoNormaManager;
import judlaw.model.dbmanager.law.NormaManager;
import judlaw.model.dbmanager.ref.AlteracaoManager;
import judlaw.model.dbmanager.ref.CitacaoTextLegManager;
import judlaw.model.dbmanager.ref.ReferenciaManager;

import org.junit.Before;
import org.junit.Test;

/**
 * Classe LawManagerTest - testes das operacoes no BD das normas e elementosNorma
 * pacote dbmanager.law
 * @author Halley Freitas
 *
 */
public class LawManagerTest {

	private NormaManager normaManager = NormaManager.getInstance();
	private ElementoNormaManager elementoNormaManager = ElementoNormaManager.getInstance();
	
	@Before
	public void setUp() {
		/* ---------- Esvazia a lista de Normas e ElementosNorma ----------*/
		ReferenciaManager.getInstance().removeReferencias();
		normaManager.removeLaw();
	}
	
	/**
	 * Teste que verifica se uma norma esta sendo salva corretamente
	 */
	@Test
	public void testSalvaNorma(){
		/* ---------- Verifica se as listas estao vazias ----------*/
		assertEquals( 0, normaManager.getNormas().size() );
		assertEquals( 0, elementoNormaManager.getElementosNorma().size() );
		
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
		normaManager.salvaNorma(norma1);
		
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
		assertEquals(1, normaManager.getNormas().size() );
		//Quantidade de ElementosNorma da Norma
		assertEquals(2, normaManager.getNormas().get(0).getElementosNorma().size() );
		//Quantidade de ElementosNorma
		assertEquals(7, elementoNormaManager.getElementosNorma().size() );
		//Quantidade filhos de Par1
		assertEquals(2, normaManager.getNormas().get(0).getElementosNorma().get(0)
													  .getElementosNorma().get(0)
													  .getElementosNorma().size());
		
		//Quantidade filhos de Art2
		assertEquals(2, normaManager.getNormas().get(0).getElementosNorma().get(1)
													  .getElementosNorma().size()); 
		
		/* ---------- Verifica se os atributos e elementos foram persistidos corretamente ----------*/
		Norma normaBD = normaManager.getNormas().get(0);

		//Atributo identificadorUnico
		assertEquals( normaBD.getIdentificadorUnico(), norma1.getIdentificadorUnico() );
		
		//ARTIGO1
		assertEquals( normaBD.getElementosNorma().get(0).getIdentificadorUnico(), 
					  norma1.getElementosNorma().get(0).getIdentificadorUnico() );
		//Relacao Bidirecional
		assertEquals( normaBD.getElementosNorma().get(0).getNormasPai().get(0).getIdentificadorUnico(), 
					  norma1.getElementosNorma().get(0).getNormasPai().get(0).getIdentificadorUnico() );
		
		//INCISO1 e INCISO2
		assertEquals( normaBD.getElementosNorma().get(0).getElementosNorma().get(0).getElementosNorma().get(0).getTexto(), 
					  norma1.getElementosNorma().get(0).getElementosNorma().get(0).getElementosNorma().get(0).getTexto() );
		assertEquals( normaBD.getElementosNorma().get(0).getElementosNorma().get(0).getElementosNorma().get(1).getTexto(), 
				      norma1.getElementosNorma().get(0).getElementosNorma().get(0).getElementosNorma().get(1).getTexto() );
		//Relacao Bidirecional
		assertEquals( normaBD.getElementosNorma().get(0).getElementosNorma().get(0).getElementosNorma().get(0).getElementosNormaPai().get(0).getIdentificadorUnico(), 
				      norma1.getElementosNorma().get(0).getElementosNorma().get(0).getElementosNorma().get(0).getElementosNormaPai().get(0).getIdentificadorUnico() );
		assertEquals( normaBD.getElementosNorma().get(0).getElementosNorma().get(0).getElementosNorma().get(1).getElementosNormaPai().get(0).getIdentificadorUnico(), 
					  norma1.getElementosNorma().get(0).getElementosNorma().get(0).getElementosNorma().get(1).getElementosNormaPai().get(0).getIdentificadorUnico() );
	}
	
	/**
	 * Testa se uma norma esta sendo removida corretamente, incluindo seus ElementosNorma.
	 */
	@Test
	public void testRemoveNorma(){
		/* ---------- Verifica se as listas estao vazias ----------*/
		assertEquals( 0, normaManager.getNormas().size() );
		assertEquals( 0, elementoNormaManager.getElementosNorma().size() );
		
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
		normaManager.salvaNorma(norma1);
		
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
		assertEquals(1, normaManager.getNormas().size() );
		//Quantidade de ElementosNorma
		assertEquals(7, elementoNormaManager.getElementosNorma().size() );

		/* ---------- Removendo o ElementoNorma ----------*/
		normaManager.removeNorma( normaManager.getNormas().get(0) );
		
		/* ---------- Verifica novamente as cardinalidade das tabelas ----------*/
		//Quantidade de normas
		assertEquals(0, normaManager.getNormas().size() );
		//Quantidade de ElementosNorma
		assertEquals(0, elementoNormaManager.getElementosNorma().size() );
	}
	
	/**
	 * Testa se um elementoNorma esta sendo removida corretamente, incluindo seus ElementosNorma.
	 */
	@Test
	public void testRemoveElementoNorma(){
		/* ---------- Verifica se as listas estao vazias ----------*/
		assertEquals( 0, normaManager.getNormas().size() );
		assertEquals( 0, elementoNormaManager.getElementosNorma().size() );
		
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
		normaManager.salvaNorma(norma1);
		
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
		assertEquals(1, normaManager.getNormas().size() );
		//Quantidade de ElementosNorma
		assertEquals(7, elementoNormaManager.getElementosNorma().size() );

		/* ---------- Removendo o ElementoNorma Artigo1 ----------*/
		ElementoNorma artigo1BD = elementoNormaManager.getElementosNorma().get(0);
		assertEquals(artigo1.getIdentificadorUnico(), artigo1BD.getIdentificadorUnico() );
		elementoNormaManager.removeElementoNorma( artigo1BD );
		
		/* ---------- Verifica novamente as cardinalidade das tabelas ----------*/
		//Quantidade de normas
		assertEquals(1, normaManager.getNormas().size() );
		//Quantidade de ElementosNorma da Norma
		assertEquals(1, normaManager.getNormas().get(0).getElementosNorma().size() );
		//Quantidade de ElementosNorma
		assertEquals(3, elementoNormaManager.getElementosNorma().size() );	
	}
	
	/**
	 * Teste que verifica a alteracao de um ElementoNorma
	 */
	@Test
	public void testAlteraElementoNorma() {
		/* ---------- Verifica se as listas estao vazias ----------*/
		assertEquals( 0, normaManager.getNormas().size() );
		assertEquals( 0, elementoNormaManager.getElementosNorma().size() );
		
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
		normaManager.salvaNorma(norma1);
		
		/*
		 *                         Norma1
		 *                         /    
		 *                       Art1   
		 *                       /  
		 *                      Par1  
		 *                     /   \
		 *                  Inc1   Inc2 
		 */
		/* ---------- Verifica as cardinalidade das tabelas dos elementos envolvidos ----------*/
		//Quantidade de normas
		assertEquals(1, normaManager.getNormas().size() );
		//Quantidade de ElementosNorma
		assertEquals(4, elementoNormaManager.getElementosNorma().size() );
		
		/* ---------- Adicionando um ElementoNorma ----------*/
		ElementoNorma artigo2 = new ElementoNorma("textoArt2", "identificadorUnicoArt2", "tipoArt2", 
				"dataPublicacaoArt2", "vigenciaArt2");
		ElementoNorma paragrafo2 = new ElementoNorma("textoParagrafo2", "identificadorUnicoParagrafo2", "tipoParagrafo2", 
				"dataPublicacaoParagrafo2", "vigenciaParagrafo2");
		ElementoNorma paragrafo3 = new ElementoNorma("textoParagrafo3", "identificadorUnicoParagrafo3", "tipoParagrafo3", 
				"dataPublicacaoParagrafo3", "vigenciaParagrafo3");
		artigo2.getElementosNorma().add(paragrafo2);
		artigo2.getElementosNorma().add(paragrafo3);
		
		elementoNormaManager.adicionaElementoNorma(artigo2, normaManager.getNormas().get(0));
		
		/*
		 *                         Norma1
		 *                         /   \
		 *                       Art1  Art2 
		 *                       /     /   \
 		 *                      Par1  Par2 Par3
		 *                     /   \
		 *                  Inc1   Inc2 
		 */
		
		/* ---------- Verifica as cardinalidade das tabelas dos elementos envolvidos ----------*/
		//Quantidade de normas
		assertEquals(1, normaManager.getNormas().size() );
		//Quantidade de ElementosNorma
		assertEquals(7, elementoNormaManager.getElementosNorma().size() );
		assertEquals(2, normaManager.getNormas().get(0).getElementosNorma().size());
		
		/* ---------- Verifica se os atributos e elementos foram persistidos corretamente ----------*/
		Norma normaBD = normaManager.getNormas().get(0);
		
		//ARTIGO2
		/*
		 * ATENCAO: por algum motivo obscuro, as vezes o artigo2 esta na posicao 0 e outras vezes na posicao1,
		 * podendo gerar erro nos testes.
		 */
		assertEquals( artigo2.getIdentificadorUnico(),
					  normaBD.getElementosNorma().get(1).getIdentificadorUnico());
		//Relacao Bidirecional
		assertEquals( norma1.getIdentificadorUnico(),	
					  normaBD.getElementosNorma().get(1).getNormasPai().get(0).getIdentificadorUnico() );
		
		//PARAGRAFO2 e PARAGRAFO3
		assertEquals( normaBD.getElementosNorma().get(1).getElementosNorma().get(0).getTexto(), 
					  paragrafo2.getTexto() );
		assertEquals( normaBD.getElementosNorma().get(1).getElementosNorma().get(1).getTexto(), 
				      paragrafo3.getTexto() );
		//Relacao Bidirecional
		assertEquals( normaBD.getElementosNorma().get(1).getElementosNorma().get(0).getElementosNormaPai().get(0).getIdentificadorUnico(), 
				      artigo2.getIdentificadorUnico() );
		assertEquals( normaBD.getElementosNorma().get(1).getElementosNorma().get(1).getElementosNormaPai().get(0).getIdentificadorUnico(), 
					  artigo2.getIdentificadorUnico() );
	}
	
	/**
	 * Verifica se as citacoesTextLeg feitas a uma norma sao removidas quando ela eh removida
	 */
	@Test
	public void testRemoveNormaCitacaoTextLeg() {
		/* ---------- Verifica se as listas estao vazias ----------*/
		assertEquals( 0, normaManager.getNormas().size() );
		assertEquals( 0, elementoNormaManager.getElementosNorma().size() );
		
		/* ---------- Norma1 ----------*/
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
		normaManager.salvaNorma(norma1);
		
		/* ---------- Norma2 ----------*/
		Norma norma2 = new Norma("epigrafeN2", "ementaN2", "autoriaN2", "localN2", "identificadorUnicoN2", "tipoN2", 
				"dataPublicacaoN2", "vigenciaN2");
		normaManager.salvaNorma(norma2);
		
		/* ---------- DocumentoJuridico ----------*/
		DocumentoJuridico docJud1 = new DocumentoJuridico();
		docJud1.setIdentificadorUnico("idUnico1");
		DocJudManager.getInstance().salvaDocumentoJuridico(docJud1);
		
		/*
		 *                         Norma1         Norma2     docJud1
		 *                         /    
		 *                       Art1   
		 *                       /  
		 *                      Par1  
		 *                     /   \
		 *                  Inc1   Inc2 
		 */
		/* ---------- Verifica as cardinalidade das tabelas dos elementos envolvidos ----------*/
		//Quantidade de normas
		assertEquals(2, normaManager.getNormas().size() );
		//Quantidade de ElementosNorma
		assertEquals(4, elementoNormaManager.getElementosNorma().size() );
		/* ---------- Verifica os elementos adicionados ----------*/
		assertEquals(norma1.getIdentificadorUnico(), 
					normaManager.getNormas().get(0).getIdentificadorUnico() );
		assertEquals(norma2.getIdentificadorUnico(), 
					normaManager.getNormas().get(1).getIdentificadorUnico() );
		/* ---------- Criando as referencias ----------*/
		
		// Norma1 -> Norma2
		CitacaoTextLegManager.getInstance().criaCitacaoTextLeg(norma1, norma2, "18/11/2009");
		// Artigo1 -> Norma2
		CitacaoTextLegManager.getInstance().criaCitacaoTextLeg(artigo1, norma2, "18/11/2009");
		// Norma2 -> DJ1
		CitacaoTextLegManager.getInstance().criaCitacaoTextLeg(norma2, docJud1, "18/11/2009");
		// Norma2 -> Artigo1
		CitacaoTextLegManager.getInstance().criaCitacaoTextLeg(norma2, artigo1, "18/11/2009");
		//Verifica a cardinalidade das citacoesTextLeg
		assertEquals( 4, CitacaoTextLegManager.getInstance().getCitacoesTextLeg().size() );
		
		//Removendo Norma1
		normaManager.removeNorma( normaManager.getNormas().get(0) );
		//Verifica a cardinalidade das citacoesTextLeg
		assertEquals( 1, CitacaoTextLegManager.getInstance().getCitacoesTextLeg().size() );
		//Removendo DocJud1
		DocJudManager.getInstance().removeDocumentoJuridico( docJud1 );
		//Verifica a cardinalidade das citacoesTextLeg
		assertEquals( 0, CitacaoTextLegManager.getInstance().getCitacoesTextLeg().size() );
	}
	
	/**
	 * Verifica se as alteracoes feitas a uma norma sao removidas quando ela eh removida
	 */
	@Test
	public void testRemoveNormaAlteracao() {
		/* ---------- Verifica se as listas estao vazias ----------*/
		assertEquals( 0, normaManager.getNormas().size() );
		assertEquals( 0, elementoNormaManager.getElementosNorma().size() );
		
		/* ---------- Norma1 ----------*/
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
		normaManager.salvaNorma(norma1);
		
		/* ---------- Norma2 ----------*/
		Norma norma2 = new Norma("epigrafeN2", "ementaN2", "autoriaN2", "localN2", "identificadorUnicoN2", "tipoN2", 
				"dataPublicacaoN2", "vigenciaN2");
		normaManager.salvaNorma(norma2);
		
		/*
		 *                         Norma1         Norma2    
		 *                         /    
		 *                       Art1   
		 *                       /  
		 *                      Par1  
		 *                     /   \
		 *                  Inc1   Inc2 
		 */
		/* ---------- Verifica as cardinalidade das tabelas dos elementos envolvidos ----------*/
		//Quantidade de normas
		assertEquals(2, normaManager.getNormas().size() );
		//Quantidade de ElementosNorma
		assertEquals(4, elementoNormaManager.getElementosNorma().size() );
		/* ---------- Verifica os elementos adicionados ----------*/
		assertEquals(norma1.getIdentificadorUnico(), 
					normaManager.getNormas().get(0).getIdentificadorUnico() );
		assertEquals(norma2.getIdentificadorUnico(), 
					normaManager.getNormas().get(1).getIdentificadorUnico() );
		/* ---------- Criando as referencias ----------*/
		
		// Norma1 -> Norma2
		AlteracaoManager.getInstance().criaAlteracaoSimples(norma1, norma2, "18/11/2009", "", "");
		// Artigo1 -> Norma2
		AlteracaoManager.getInstance().criaAlteracaoSimples(artigo1, norma2, "18/11/2009", "", "");
		// Norma2 -> Artigo1
		AlteracaoManager.getInstance().criaAlteracaoSimples(norma2, artigo1, "18/11/2009", "", "");
		//Verifica a cardinalidade das citacoesTextLeg
		assertEquals( 3, AlteracaoManager.getInstance().getAlteracoes().size() );
		
		//Removendo Norma1
		normaManager.removeNorma( normaManager.getNormas().get(0) );
		//Verifica a cardinalidade das citacoesTextLeg
		assertEquals( 0, AlteracaoManager.getInstance().getAlteracoes().size() );
	}
}
