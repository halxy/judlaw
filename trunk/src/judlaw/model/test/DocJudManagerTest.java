package judlaw.model.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import judlaw.model.bean.docjud.Cabecalho;
import judlaw.model.bean.docjud.DocumentoJuridico;
import judlaw.model.bean.docjud.Ementa;
import judlaw.model.bean.docjud.Encerramento;
import judlaw.model.bean.docjud.Parte;
import judlaw.model.bean.docjud.Relatorio;
import judlaw.model.bean.docjud.Voto;
import judlaw.model.manager.DocJudManager;

import org.junit.Test;

/**
 * Classe para testes da Classe TimeManager
 * @author Halley Freitas
 *
 */
public class DocJudManagerTest {
	DocJudManager docJudManager = DocJudManager.getInstance();
	
	/**
	 * Testa a persistencia de um documento juridico, verificando a corretude dessa acao em relacao aos
	 * seus dependentes: Cabecalho, Ementa, Encerramento, Parte, Relatorio e Voto.
	 * 
	 */
	@Test
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
		//Partes
		ArrayList<Parte> partes = new ArrayList<Parte>();
		partes.add( new Parte("titulo1", "nome1") );
		partes.add( new Parte("titulo2", "nome2") );
		
		/* ---------- Criacao e Persistencia do DocumentoJuridico ----------*/	
		DocumentoJuridico docJud1 = new DocumentoJuridico();
		docJud1.setIdentificadorUnico("idUnico");
		docJud1.setCabecalho(cabecalho1);
		docJud1.setEmenta(ementa1);
		docJud1.setRelatorio(relatorio1);
		docJud1.setEncerramento(encerramento1);
		docJud1.setVotos(votos);
		docJud1.setPartes(partes);
		docJudManager.salvaDocumentoJuridico(docJud1);
		
		/* ---------- Verifica as cardinalidade das tabelas dos elementos envolvidos ----------*/
		assertEquals(1, docJudManager.getDocumentosJuridicos().size() );
		assertEquals(1, docJudManager.getCabecalhos().size() );
		assertEquals(1, docJudManager.getEmentas().size() );
		assertEquals(1, docJudManager.getRelatorios().size() );
		assertEquals(1, docJudManager.getEncerramentos().size() );
		assertEquals(2, docJudManager.getVotos().size() );
		assertEquals(2, docJudManager.getPartes().size() );
		
		/* ---------- Verifica se os atributos e elementos foram persistidos corretamente ----------*/
		DocumentoJuridico docJudBD = docJudManager.getDocumentosJuridicos().get(0);

		//Atributo identificadorUnico
		assertEquals( docJudBD.getIdentificadorUnico(), docJud1.getIdentificadorUnico() );
		
		//CABECALHO
		assertEquals( docJudBD.getCabecalho().getCodRegistro(), docJud1.getCabecalho().getCodRegistro() );
		//Relacao Bidirecional
		assertEquals( docJudBD.getCabecalho().getDocumentoJuridico().getIdentificadorUnico(), 
					  docJud1.getCabecalho().getDocumentoJuridico().getIdentificadorUnico() );
		
		//EMENTA
		assertEquals( docJudBD.getEmenta().getTexto(), docJud1.getEmenta().getTexto() );
		//Relacao Bidirecional
		assertEquals( docJudBD.getEmenta().getDocumentoJuridico().getIdentificadorUnico(), 
					  docJud1.getEmenta().getDocumentoJuridico().getIdentificadorUnico() );
		
		//RELATORIO
		assertEquals( docJudBD.getRelatorio().getTexto(), docJud1.getRelatorio().getTexto() );
		//Relacao Bidirecional
		assertEquals( docJudBD.getRelatorio().getDocumentoJuridico().getIdentificadorUnico(), 
					  docJud1.getRelatorio().getDocumentoJuridico().getIdentificadorUnico() );
		
		//ENCERRAMENTO
		assertEquals( docJudBD.getEncerramento().getDecisao(), docJud1.getEncerramento().getDecisao() );
		assertEquals( docJudBD.getEncerramento().getLocal(), docJud1.getEncerramento().getLocal() );
		//Relacao Bidirecional
		assertEquals( docJudBD.getEncerramento().getDocumentoJuridico().getIdentificadorUnico(), 
					  docJud1.getEncerramento().getDocumentoJuridico().getIdentificadorUnico() );
		
		//VOTOS
		assertEquals( docJudBD.getVotos().get(0).getTexto(), votos.get(0).getTexto() );
		assertEquals( docJudBD.getVotos().get(1).getTexto(), votos.get(1).getTexto() );
		//Relacao Bidirecional
		assertEquals( docJudBD.getVotos().get(0).getDocumentoJuridico().getIdentificadorUnico(),
				      docJud1.getVotos().get(0).getDocumentoJuridico().getIdentificadorUnico());
		
		//PARTES
		assertEquals( docJudBD.getPartes().get(0).getTitulo(), partes.get(0).getTitulo() );
		assertEquals( docJudBD.getPartes().get(1).getNome(), partes.get(1).getNome() );
		//Relacao Bidirecional
		assertEquals( docJudBD.getPartes().get(0).getDocumentosJuridicos().get(0).getIdentificadorUnico(),
				      docJud1.getPartes().get(0).getDocumentosJuridicos().get(0).getIdentificadorUnico());
	}
	
	
	/**
	 * Testa se ao remover um DocumentoJuridico, as instancias que dependem dele tambem sao removidas
	 */
	@Test
	public void testRemoveDocumentoJuridico() {
		/* ---------- Esvazia a lista de Documentos Juridicos ----------*/
		docJudManager.removeDocumentosJuridicos();
		assertEquals(0, docJudManager.getDocumentosJuridicos().size());
		//Verifica se as demais listas também estão vazias
		assertEquals( 0, docJudManager.getDocumentosJuridicos().size() );
		assertEquals( 0, docJudManager.getCabecalhos().size() );
		assertEquals( 0, docJudManager.getEmentas().size() );
		assertEquals( 0, docJudManager.getRelatorios().size() );
		assertEquals( 0, docJudManager.getEncerramentos().size() );
		assertEquals( 0, docJudManager.getVotos().size() );
		
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
		
		/* ---------- Verifica se os elementos foram inseridos em suas respectivas tabelas  ----------*/
		assertEquals( 1, docJudManager.getDocumentosJuridicos().size() );
		assertEquals( 1, docJudManager.getCabecalhos().size() );
		assertEquals( 1, docJudManager.getEmentas().size() );
		assertEquals( 1, docJudManager.getRelatorios().size() );
		assertEquals( 1, docJudManager.getEncerramentos().size() );
		assertEquals( 2, docJudManager.getVotos().size() );
		
		/*
		 * Removendo a lista de documentos juridicos eh esperado que a lista dos demais elementos
		 * tambem seja removida
		 */
		docJudManager.removeDocumentosJuridicos();
		assertEquals( 0, docJudManager.getDocumentosJuridicos().size() );
		assertEquals( 0, docJudManager.getCabecalhos().size() );
		assertEquals( 0, docJudManager.getEmentas().size() );
		assertEquals( 0, docJudManager.getRelatorios().size() );
		assertEquals( 0, docJudManager.getEncerramentos().size() );
		assertEquals( 0, docJudManager.getVotos().size() );
	}
	
	/**
	 * Teste que verifica se um DocumentoJuridico esta sendo alterado de forma correta
	 */
	@Test
	public void testAlteraDocumentoJuridico() {
		/* ---------- Esvazia a lista de Documentos Juridicos ----------*/
		docJudManager.removeDocumentosJuridicos();
		assertEquals(0, docJudManager.getDocumentosJuridicos().size());
		//Verifica se as demais listas também estão vazias
		assertEquals( 0, docJudManager.getDocumentosJuridicos().size() );
		assertEquals( 0, docJudManager.getCabecalhos().size() );
		assertEquals( 0, docJudManager.getEmentas().size() );
		assertEquals( 0, docJudManager.getRelatorios().size() );
		assertEquals( 0, docJudManager.getEncerramentos().size() );
		assertEquals( 0, docJudManager.getVotos().size() );
		
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
		
		/* ---------- Verifica se os atributos e elementos foram persistidos corretamente ----------*/
		DocumentoJuridico docJudBD = docJudManager.getDocumentosJuridicos().get(0);

		//Atributo identificadorUnico
		assertEquals( docJudBD.getIdentificadorUnico(), docJud1.getIdentificadorUnico() );
		
		//CABECALHO
		assertEquals( docJudBD.getCabecalho().getCodRegistro(), docJud1.getCabecalho().getCodRegistro() );
		//Relacao Bidirecional
		assertEquals( docJudBD.getCabecalho().getDocumentoJuridico().getIdentificadorUnico(), 
					  docJud1.getCabecalho().getDocumentoJuridico().getIdentificadorUnico() );
		
		//EMENTA
		assertEquals( docJudBD.getEmenta().getTexto(), docJud1.getEmenta().getTexto() );
		//Relacao Bidirecional
		assertEquals( docJudBD.getEmenta().getDocumentoJuridico().getIdentificadorUnico(), 
					  docJud1.getEmenta().getDocumentoJuridico().getIdentificadorUnico() );
		
		//RELATORIO
		assertEquals( docJudBD.getRelatorio().getTexto(), docJud1.getRelatorio().getTexto() );
		//Relacao Bidirecional
		assertEquals( docJudBD.getRelatorio().getDocumentoJuridico().getIdentificadorUnico(), 
					  docJud1.getRelatorio().getDocumentoJuridico().getIdentificadorUnico() );
		
		//ENCERRAMENTO
		assertEquals( docJudBD.getEncerramento().getDecisao(), docJud1.getEncerramento().getDecisao() );
		assertEquals( docJudBD.getEncerramento().getLocal(), docJud1.getEncerramento().getLocal() );
		//Relacao Bidirecional
		assertEquals( docJudBD.getEncerramento().getDocumentoJuridico().getIdentificadorUnico(), 
					  docJud1.getEncerramento().getDocumentoJuridico().getIdentificadorUnico() );
		
		//VOTOS
		assertEquals( docJudBD.getVotos().get(0).getTexto(), votos.get(0).getTexto() );
		assertEquals( docJudBD.getVotos().get(1).getTexto(), votos.get(1).getTexto() );
		//Relacao Bidirecional
		assertEquals( docJudBD.getVotos().get(0).getDocumentoJuridico().getIdentificadorUnico(),
				      docJud1.getVotos().get(0).getDocumentoJuridico().getIdentificadorUnico());
		
		/* ---------- ALTERACOES ----------*/
		// Cabecalho
		Cabecalho cabecalhoBD = docJudBD.getCabecalho();
		cabecalhoBD.setCodRegistro("codRegistro2");
		// Ementa
		Ementa ementaBD = docJudBD.getEmenta();
		ementaBD.setTexto("ementa2");
		// Relatorio
		Relatorio relatorioBD = docJudBD.getRelatorio();
		relatorioBD.setTexto("relatorio2");
		// Encerramento
		Encerramento encerramentoBD = docJudBD.getEncerramento();
		encerramentoBD.setDecisao("decisao2");
		// Votos
		List<Voto> votosBD = docJudBD.getVotos();
		votosBD.get(0).setTexto("voto11");
		votosBD.get(1).setTexto("voto22");
		
		//Setando as novas propriedades no objeto
		docJudBD.setCabecalho(cabecalhoBD);
		docJudBD.setEmenta(ementaBD);
		docJudBD.setRelatorio(relatorioBD);
		docJudBD.setEncerramento(encerramentoBD);
		docJudBD.setVotos(votosBD);
		docJudManager.salvaDocumentoJuridico(docJudBD);
		
		/* ---------- Verifica se os elementos foram inseridos em suas respectivas tabelas  ----------*/
		assertEquals( 1, docJudManager.getDocumentosJuridicos().size() );
		assertEquals( 1, docJudManager.getCabecalhos().size() );
		assertEquals( 1, docJudManager.getEmentas().size() );
		assertEquals( 1, docJudManager.getRelatorios().size() );
		assertEquals( 1, docJudManager.getEncerramentos().size() );
		assertEquals( 2, docJudManager.getVotos().size() );
		
		/* ---------- Verifica se as propriedades foram modificadas  ----------*/
		DocumentoJuridico docJudBD2 = docJudManager.getDocumentosJuridicos().get(0);
		assertEquals( docJudBD2.getCabecalho().getCodRegistro(), cabecalhoBD.getCodRegistro() );
		assertEquals( docJudBD2.getEmenta().getTexto(), ementaBD.getTexto() );
		assertEquals( docJudBD2.getRelatorio().getTexto(), relatorioBD.getTexto() );
		assertEquals( docJudBD2.getEncerramento().getDecisao(), encerramentoBD.getDecisao() );
		assertEquals( docJudBD2.getVotos().get(0).getTexto(), votosBD.get(0).getTexto() );
		assertEquals( docJudBD2.getVotos().get(1).getTexto(), votosBD.get(1).getTexto() );
	}
}
