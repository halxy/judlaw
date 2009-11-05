package judlaw.model.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import judlaw.model.bean.docjud.Cabecalho;
import judlaw.model.bean.docjud.DocumentoJuridico;
import judlaw.model.bean.docjud.Ementa;
import judlaw.model.bean.docjud.Encerramento;
import judlaw.model.bean.docjud.Relatorio;
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

	/**
	 * Test method for {@link judlaw.model.manager.DocJudManager#saveDocumentoJuridico(judlaw.model.docjud.DocumentoJuridico)}.
	 */
	@Test
	public void testSaveDocumentoJuridico() {
		// esvazia lista de documentos juridicos
		docJudManager.removeDocumentosJuridicos();
		List<DocumentoJuridico> documentosJuridicos = new ArrayList<DocumentoJuridico>();
		documentosJuridicos = docJudManager.getDocumentosJuridicos();
		assertEquals(0, documentosJuridicos.size());
		
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
		documentosJuridicos = docJudManager.getDocumentosJuridicos();
		assertEquals(1, documentosJuridicos.size());
		
		// verificando a alteracao recuperada do BD
		DocumentoJuridico docJudBD = documentosJuridicos.get(0);
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
	 * Teste que verifica se ao modificar um documento juridico, ele � persistido corretamente
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
