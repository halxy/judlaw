package judlaw.model.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import judlaw.model.bean.docjud.Cabecalho;
import judlaw.model.bean.docjud.DocumentoJuridico;
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
		
		//Documento Juridico
		DocumentoJuridico docJud1 = new DocumentoJuridico();
		docJud1.setIdentificadorUnico("idUnico");
		docJud1.setCabecalho(cabecalho1);
		docJudManager.salvaDocumentoJuridico(docJud1);
		
		// verifica a nova lista
		documentosJuridicos = docJudManager.getDocumentosJuridicos();
		assertEquals(1, documentosJuridicos.size());
		
		// verificando a alteracao recuperada do BD
		DocumentoJuridico docJudBD = documentosJuridicos.get(0);
		//verificando apenas um atributo de documento juridico
		assertEquals( docJud1.getIdentificadorUnico(), docJudBD.getIdentificadorUnico() );
		//verificando se as propriedades do cabecalho foram setadas corretamente;
		assertEquals( docJud1.getCabecalho().getCodRegistro(), cabecalho1.getCodRegistro() );
		// verificando se a relacao bidirecional funciona
		assertEquals( docJud1.getCabecalho().getDocumentoJuridico().getIdentificadorUnico(), 
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
		
		//Cria e persiste o documento juridico		
		//Cabecalho
		Cabecalho cabecalho1 = new Cabecalho();
		cabecalho1.setCodRegistro("codRegistro");
		cabecalho1.setOrgaoJulgador("orgaoJulgador");
		cabecalho1.setTribunal("tribunal");
		
		//Documento Juridico
		DocumentoJuridico docJud1 = new DocumentoJuridico();
		docJud1.setIdentificadorUnico("idUnico");
		docJud1.setCabecalho(cabecalho1);
		docJudManager.salvaDocumentoJuridico(docJud1);
		
		// verifica as listas apos a inclusao
		assertEquals(1, docJudManager.getDocumentosJuridicos().size());
		assertEquals(1, docJudManager.getCabecalhos().size());
		
		/*
		 * removendo a lista de documentos juridicos e esperando que a lista de cabecalhos
		 * tambem seja removida
		 */
		docJudManager.removeDocumentosJuridicos();
		assertEquals(0, docJudManager.getDocumentosJuridicos().size());
		assertEquals(0, docJudManager.getCabecalhos().size());
	}
	/**
	 * Teste que verifica se ao modificar um documento juridico, ele é persistido corretamente
	 */
	@Test
	public void testAlteraDocumentoJuridico() {
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
		
		//Documento Juridico
		DocumentoJuridico docJud1 = new DocumentoJuridico();
		docJud1.setIdentificadorUnico("idUnico");
		docJud1.setCabecalho(cabecalho1);
		docJudManager.salvaDocumentoJuridico(docJud1);
		
		// verifica a nova lista
		documentosJuridicos = docJudManager.getDocumentosJuridicos();
		assertEquals(1, documentosJuridicos.size());
		
		// verificando a alteracao recuperada do BD
		DocumentoJuridico docJudBD = documentosJuridicos.get(0);
		//verificando apenas um atributo de documento juridico
		assertEquals( docJud1.getIdentificadorUnico(), docJudBD.getIdentificadorUnico() );
		//verificando se as propriedades do cabecalho foram setadas corretamente;
		assertEquals( docJud1.getCabecalho().getCodRegistro(), cabecalho1.getCodRegistro() );
		// verificando se a relacao bidirecional funciona
		assertEquals( docJud1.getCabecalho().getDocumentoJuridico().getIdentificadorUnico(), 
				      docJud1.getIdentificadorUnico() );
		
		// Fazendo a alteracao
		Cabecalho cabecalhoBD = docJud1.getCabecalho();
		cabecalhoBD.setCodRegistro("codRegistro2");
		docJudBD.setCabecalho(cabecalhoBD);
		docJudManager.salvaDocumentoJuridico(docJudBD);
		// o BD deve conter apenas um elemento
		assertEquals(1, documentosJuridicos.size());
		DocumentoJuridico docJudBD2 = documentosJuridicos.get(0);
		assertEquals( docJudBD2.getCabecalho().getCodRegistro(), cabecalhoBD.getCodRegistro() );
	}
}
