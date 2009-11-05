package judlaw.model.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import judlaw.model.bean.docjud.Cabecalho;
import judlaw.model.bean.docjud.DocumentoJuridico;
import judlaw.model.bean.docjud.Ementa;
import judlaw.model.bean.docjud.Encerramento;
import judlaw.model.bean.docjud.Relatorio;
import judlaw.model.bean.docjud.Voto;
import judlaw.model.manager.DocJudManager;

import org.junit.Before;
import org.junit.Test;

/**
 * Classe para testes da Classe TimeManager
 * @author Halley Freitas
 *
 */
public class DocJudManagerTest {
	DocJudManager docJudManager = DocJudManager.getInstance();
	
	@Before public void setUp(){
	}

	@Test
	/**
	 * Testa a persistencia de um documento juridico, verificando a corretude dessa acao em relacao aos
	 * seus dependentes: Cabecalho, Ementa, Encerramento, Parte, Relatorio e Voto.
	 * 
	 */
	public void testSaveDocumentoJuridico() {
		/* ---------- Esvazia a lista de Documentos Juridicos ----------*/
		docJudManager.removeDocumentosJuridicos();
		assertEquals(0, docJudManager.getDocumentosJuridicos().size());
		
		/* ---------- Elementos do DocumentoJuridico ----------*/		
		//Cabecalho
		Cabecalho cabecalho1 = new Cabecalho();
		cabecalho1.setCodRegistro("codRegistro");
		cabecalho1.setOrgaoJulgador("orgaoJulgador");
		cabecalho1.setTribunal("tribunal");
		//Ementa
		Ementa ementa1 = new Ementa("ementa1");
		//Relatorio
		Relatorio relatorio1 = new Relatorio("relatorio1");
		//Encerramento
		Encerramento encerramento1 = new Encerramento("decisao1", "local1");
		//Votos
		ArrayList<Voto> votos = new ArrayList<Voto>();
		votos.add( new Voto("voto1") );
		votos.add( new Voto("voto2") );
		
		/* ---------- Criacao e Persistencia do DocumentoJuridico ----------*/	
		DocumentoJuridico docJud1 = new DocumentoJuridico();
		docJud1.setIdentificadorUnico("idUnico");
		docJud1.setCabecalho(cabecalho1);
		docJud1.setEmenta(ementa1);
		docJud1.setRelatorio(relatorio1);
		docJud1.setEncerramento(encerramento1);
		docJud1.setVotos(votos);
		docJudManager.salvaDocumentoJuridico(docJud1);
		
		/* ---------- Verifica as cardinalidade das tabelas dos elementos envolvidos ----------*/
		assertEquals(1, docJudManager.getDocumentosJuridicos().size() );
		assertEquals(1, docJudManager.getCabecalhos().size() );
		assertEquals(1, docJudManager.getEmentas().size() );
		assertEquals(1, docJudManager.getRelatorios().size() );
		assertEquals(1, docJudManager.getEncerramentos().size() );
		assertEquals(2, docJudManager.getVotos().size() );
		
		/* ---------- Verifica se os atributos e elementos foram persistidos corretamente ----------*/
		DocumentoJuridico docJudBD = docJudManager.getDocumentosJuridicos().get(0);

		// Atributo identificadorUnico
		assertEquals( docJudBD.getIdentificadorUnico(), docJud1.getIdentificadorUnico() );
		
		// CABECALHO
		assertEquals( docJudBD.getCabecalho().getCodRegistro(), docJud1.getCabecalho().getCodRegistro() );
		// Relacao Bidirecional
		assertEquals( docJudBD.getCabecalho().getDocumentoJuridico().getIdentificadorUnico(), 
					  docJud1.getCabecalho().getDocumentoJuridico().getIdentificadorUnico() );
		
		// EMENTA
		assertEquals( docJudBD.getEmenta().getTexto(), docJud1.getEmenta().getTexto() );
		// Relacao Bidirecional
		assertEquals( docJudBD.getEmenta().getDocumentoJuridico().getIdentificadorUnico(), 
					  docJud1.getEmenta().getDocumentoJuridico().getIdentificadorUnico() );
		
		// RELATORIO
		assertEquals( docJudBD.getRelatorio().getTexto(), docJud1.getRelatorio().getTexto() );
		// Relacao Bidirecional
		assertEquals( docJudBD.getRelatorio().getDocumentoJuridico().getIdentificadorUnico(), 
					  docJud1.getRelatorio().getDocumentoJuridico().getIdentificadorUnico() );
		
		// ENCERRAMENTO
		assertEquals( docJudBD.getEncerramento().getDecisao(), docJud1.getEncerramento().getDecisao() );
		assertEquals( docJudBD.getEncerramento().getLocal(), docJud1.getEncerramento().getLocal() );
		// Relacao Bidirecional
		assertEquals( docJudBD.getEncerramento().getDocumentoJuridico().getIdentificadorUnico(), 
					  docJud1.getEncerramento().getDocumentoJuridico().getIdentificadorUnico() );
	}
	
	/**
	 * Teste que verifica se ao remover os documentos juridicos, as instancias das entidades que dependem
	 * deles tambem sao removidas
	 */
	@Test
	public void testRemoveDocumentoJuridico() {
		// esvazia as listas
		docJudManager.removeDocumentosJuridicos();
		assertEquals( 0, docJudManager.getDocumentosJuridicos().size() );
		docJudManager.removeCabecalhos();
		assertEquals( 0, docJudManager.getCabecalhos().size() );
		docJudManager.removeEmentas();
		assertEquals( 0, docJudManager.getEmentas().size() );
		docJudManager.removeRelatorios();
		assertEquals( 0, docJudManager.getRelatorios().size() );
		docJudManager.removeEncerramentos();
		assertEquals( 0, docJudManager.getEncerramentos().size() );
		
		//Cria e persiste o documento juridico		
		//Cabecalho
		Cabecalho cabecalho1 = new Cabecalho();
		cabecalho1.setCodRegistro("codRegistro");
		cabecalho1.setOrgaoJulgador("orgaoJulgador");
		cabecalho1.setTribunal("tribunal");
		//Ementa
		Ementa ementa1 = new Ementa("ementa1");
		//Relatorio
		Relatorio relatorio1 = new Relatorio("relatorio1");
		//Encerramento
		Encerramento encerramento1 = new Encerramento("decisao1", "local1");
		//Documento Juridico
		DocumentoJuridico docJud1 = new DocumentoJuridico();
		docJud1.setIdentificadorUnico("idUnico");
		docJud1.setCabecalho(cabecalho1);
		docJud1.setEmenta(ementa1);
		docJud1.setRelatorio(relatorio1);
		docJud1.setEncerramento(encerramento1);
		docJudManager.salvaDocumentoJuridico(docJud1);
		
		// verifica as listas apos a inclusao
		assertEquals( 1, docJudManager.getDocumentosJuridicos().size() );
		assertEquals( 1, docJudManager.getCabecalhos().size() );
		assertEquals( 1, docJudManager.getEmentas().size() );
		assertEquals( 1, docJudManager.getRelatorios().size() );
		assertEquals( 1, docJudManager.getEncerramentos().size() );
		
		/*
		 * removendo a lista de documentos juridicos e esperando que a lista de cabecalhos
		 * tambem seja removida
		 */
		docJudManager.removeDocumentosJuridicos();
		assertEquals( 0, docJudManager.getDocumentosJuridicos().size() );
		assertEquals( 0, docJudManager.getCabecalhos().size() );
		assertEquals( 0, docJudManager.getEmentas().size() );
		assertEquals( 0, docJudManager.getRelatorios().size() );
		assertEquals( 0, docJudManager.getEncerramentos().size() );
	}
	/**
	 * Teste que verifica se ao modificar um documento juridico, ele é persistido corretamente
	 */
	@Test
	public void testAlteraDocumentoJuridico() {
		// esvazia lista de documentos juridicos
		docJudManager.removeDocumentosJuridicos();
		assertEquals( 0, docJudManager.getDocumentosJuridicos().size() );
		
		//Cria e persiste o documento juridico		
		//Cabecalho
		Cabecalho cabecalho1 = new Cabecalho();
		cabecalho1.setCodRegistro("codRegistro");
		cabecalho1.setOrgaoJulgador("orgaoJulgador");
		cabecalho1.setTribunal("tribunal");
		//Ementa
		Ementa ementa1 = new Ementa("ementa1");
		//Relatorio
		Relatorio relatorio1 = new Relatorio("relatorio1");
		//Encerramento
		Encerramento encerramento1 = new Encerramento("decisao1", "local1");
		//Documento Juridico
		DocumentoJuridico docJud1 = new DocumentoJuridico();
		docJud1.setIdentificadorUnico("idUnico");
		docJud1.setCabecalho(cabecalho1);
		docJud1.setEmenta(ementa1);
		docJud1.setRelatorio(relatorio1);
		docJud1.setEncerramento(encerramento1);
		docJudManager.salvaDocumentoJuridico(docJud1);
		
		// verifica a nova lista
		assertEquals( 1, docJudManager.getDocumentosJuridicos().size() );
		
		// verificando a alteracao recuperada do BD
		DocumentoJuridico docJudBD = docJudManager.getDocumentosJuridicos().get(0);
		//verificando apenas um atributo de documento juridico
		assertEquals( docJud1.getIdentificadorUnico(), docJudBD.getIdentificadorUnico() );
		
		// CABECALHO
		//verificando se as propriedades do cabecalho foram setadas corretamente;
		assertEquals( docJud1.getCabecalho().getCodRegistro(), cabecalho1.getCodRegistro() );
		// verificando se a relacao bidirecional funciona
		assertEquals( docJud1.getCabecalho().getDocumentoJuridico().getIdentificadorUnico(), 
				      docJud1.getIdentificadorUnico() );
		// EMENTA
		//verificando se as propriedades da ementa foram setadas corretamente;
		assertEquals( docJud1.getEmenta().getTexto(), ementa1.getTexto() );
		// verificando se a relacao bidirecional funciona
		assertEquals( docJud1.getEmenta().getDocumentoJuridico().getIdentificadorUnico(), 
				      docJud1.getIdentificadorUnico() );
		// RELATORIO
		//verificando se as propriedades do relatorio foram setadas corretamente;
		assertEquals( docJud1.getRelatorio().getTexto(), relatorio1.getTexto() );
		// verificando se a relacao bidirecional funciona
		assertEquals( docJud1.getRelatorio().getDocumentoJuridico().getIdentificadorUnico(), 
				      docJud1.getIdentificadorUnico() );
		// ENCERRAMENTO
		//verificando se as propriedades do encerramento foram setadas corretamente;
		assertEquals( docJud1.getEncerramento().getDecisao(), encerramento1.getDecisao() );
		assertEquals( docJud1.getEncerramento().getLocal(), encerramento1.getLocal() );
		// verificando se a relacao bidirecional funciona
		assertEquals( docJud1.getEncerramento().getDocumentoJuridico().getIdentificadorUnico(), 
				      docJud1.getIdentificadorUnico() );
		
		// Fazendo a alteracao
		// Cabecalho
		Cabecalho cabecalhoBD = docJud1.getCabecalho();
		cabecalhoBD.setCodRegistro("codRegistro2");
		// Ementa
		Ementa ementaBD = docJud1.getEmenta();
		ementaBD.setTexto("ementa2");
		// Relatorio
		Relatorio relatorioBD = docJud1.getRelatorio();
		relatorioBD.setTexto("relatorio2");
		// Encerramento
		Encerramento encerramentoBD = docJud1.getEncerramento();
		encerramentoBD.setDecisao("decisao2");
		
		//Setando as novas propriedades no objeto
		docJudBD.setCabecalho(cabecalhoBD);
		docJudBD.setEmenta(ementaBD);
		docJudBD.setRelatorio(relatorioBD);
		docJudBD.setEncerramento(encerramentoBD);
		docJudManager.salvaDocumentoJuridico(docJudBD);
		// o BD deve conter apenas um elemento
		assertEquals( 1, docJudManager.getDocumentosJuridicos().size() );
		//Verificando se o documento juridico armazenado possui as caracteristicas modificadas
		DocumentoJuridico docJudBD2 = docJudManager.getDocumentosJuridicos().get(0);
		assertEquals( docJudBD2.getCabecalho().getCodRegistro(), cabecalhoBD.getCodRegistro() );
		assertEquals( docJudBD2.getEmenta().getTexto(), ementaBD.getTexto() );
		assertEquals( docJudBD2.getRelatorio().getTexto(), relatorioBD.getTexto() );
		assertEquals( docJudBD2.getEncerramento().getDecisao(), encerramentoBD.getDecisao() );
	}
}
