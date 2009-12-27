/* 
 * Universidade Federal de Campina Grande
 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas	
 */
package judlaw.model.test.dbmanager;

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
import judlaw.model.persistence.dbmanager.docjud.CabecalhoManager;
import judlaw.model.persistence.dbmanager.docjud.DocJudManager;
import judlaw.model.persistence.dbmanager.docjud.EmentaManager;
import judlaw.model.persistence.dbmanager.docjud.EncerramentoManager;
import judlaw.model.persistence.dbmanager.docjud.ParteManager;
import judlaw.model.persistence.dbmanager.docjud.RelatorioManager;
import judlaw.model.persistence.dbmanager.docjud.VotoManager;
import judlaw.model.persistence.dbmanager.ref.CitacaoDocJudManager;
import judlaw.model.persistence.dbmanager.ref.ReferenciaManager;

import org.junit.Before;
import org.junit.Test;

/**
 * Classe DocJudManagerTest - testes das operacoes no BD dos documentos juridicos e de seus elementos
 * pacote dbmanager.docjud
 * @author Halley Freitas
 *
 */
public class DocJudManagerTest {
	
	private DocJudManager docJudManager = DocJudManager.getInstance();
	private CabecalhoManager cabecalhoManager = CabecalhoManager.getInstance();
	private EmentaManager ementaManager = EmentaManager.getInstance();
	private EncerramentoManager encerramentoManager = EncerramentoManager.getInstance();
	private ParteManager parteManager = ParteManager.getInstance();
	private RelatorioManager relatorioManager = RelatorioManager.getInstance();
	private VotoManager votoManager = VotoManager.getInstance();
	
	@Before
	public void setUp() {
		ReferenciaManager.getInstance().removeReferencias();
		/* ---------- Esvazia a lista de Documentos Juridicos ----------*/
		docJudManager.removeDocumentosJuridicos();
		cabecalhoManager.removeCabecalhos();
		ementaManager.removeEmentas();
		relatorioManager.removeRelatorios();
		encerramentoManager.removeEncerramentos();
		votoManager.removeVotos();
		parteManager.removePartes();	
	}
	/**
	 * Testa a persistencia de um documento juridico, verificando a corretude dessa acao em relacao aos
	 * seus dependentes: Cabecalho, Ementa, Encerramento, Parte, Relatorio e Voto.
	 * 
	 */
	@Test
	public void testSaveDocumentoJuridico() {
		/* ---------- Verifica se a lista esta vazia ----------*/
		assertEquals(0, docJudManager.getDocumentosJuridicos().size());
		
		/* ---------- Elementos do DocumentoJuridico ----------*/		
		//Cabecalho
		Cabecalho cabecalho1 = new Cabecalho();
		cabecalho1.setCodRegistro("codRegistro");
		cabecalho1.setTribunal("tribunal");
		//Ementa
		Ementa ementa1 = new Ementa("ementa1");
		//Relatorio
		Relatorio relatorio1 = new Relatorio("relatorio1");
		//Encerramento
		Encerramento encerramento1 = new Encerramento("decisao1");
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
		assertEquals(1, cabecalhoManager.getCabecalhos().size() );
		assertEquals(1, ementaManager.getEmentas().size() );
		assertEquals(1, relatorioManager.getRelatorios().size() );
		assertEquals(1, encerramentoManager.getEncerramentos().size() );
		assertEquals(2, votoManager.getVotos().size() );
		assertEquals(2, parteManager.getPartes().size() );
		
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
		/* ---------- Verifica se a lista esta vazia ----------*/
		assertEquals(0, docJudManager.getDocumentosJuridicos().size());

		//Verifica se as demais listas também estão vazias
		assertEquals( 0, cabecalhoManager.getCabecalhos().size() );
		assertEquals( 0, ementaManager.getEmentas().size() );
		assertEquals( 0, relatorioManager.getRelatorios().size() );
		assertEquals( 0, encerramentoManager.getEncerramentos().size() );
		assertEquals( 0, votoManager.getVotos().size() );
		assertEquals( 0, parteManager.getPartes().size() );
		
		/* ---------- Elementos do DocumentoJuridico ----------*/		
		//Cabecalho
		Cabecalho cabecalho1 = new Cabecalho();
		cabecalho1.setCodRegistro("codRegistro");
		cabecalho1.setTribunal("tribunal");
		//Ementa
		Ementa ementa1 = new Ementa("ementa1");
		//Relatorio
		Relatorio relatorio1 = new Relatorio("relatorio1");
		//Encerramento
		Encerramento encerramento1 = new Encerramento("decisao1");
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
		
		/* ---------- Verifica se os elementos foram inseridos em suas respectivas tabelas  ----------*/
		assertEquals( 1, docJudManager.getDocumentosJuridicos().size() );
		assertEquals( 1, cabecalhoManager.getCabecalhos().size() );
		assertEquals( 1, ementaManager.getEmentas().size() );
		assertEquals( 1, relatorioManager.getRelatorios().size() );
		assertEquals( 1, encerramentoManager.getEncerramentos().size() );
		assertEquals( 2, votoManager.getVotos().size() );
		assertEquals( 2, parteManager.getPartes().size() );
		
		/*
		 * Removendo a lista de documentos juridicos eh esperado que a lista dos demais elementos
		 * tambem seja removida
		 */
		docJudManager.removeDocumentoJuridico( docJudManager.getDocumentosJuridicos().get(0) );
		assertEquals( 0, docJudManager.getDocumentosJuridicos().size() );
		assertEquals( 0, cabecalhoManager.getCabecalhos().size() );
		assertEquals( 0, ementaManager.getEmentas().size() );
		assertEquals( 0, relatorioManager.getRelatorios().size() );
		assertEquals( 0, encerramentoManager.getEncerramentos().size() );
		assertEquals( 0, votoManager.getVotos().size() );
		assertEquals( 0, parteManager.getPartes().size() );
	}
	
	/**
	 * Teste que verifica se um DocumentoJuridico esta sendo alterado de forma correta
	 */
	@Test
	public void testAlteraDocumentoJuridico() {
		/* ---------- Verifica se a lista esta vazia ----------*/
		assertEquals(0, docJudManager.getDocumentosJuridicos().size());
		
		//Verifica se as demais listas também estão vazias
		assertEquals( 0, cabecalhoManager.getCabecalhos().size() );
		assertEquals( 0, ementaManager.getEmentas().size() );
		assertEquals( 0, relatorioManager.getRelatorios().size() );
		assertEquals( 0, encerramentoManager.getEncerramentos().size() );
		assertEquals( 0, votoManager.getVotos().size() );
		assertEquals( 0, parteManager.getPartes().size() );
		
		/* ---------- Elementos do DocumentoJuridico ----------*/		
		//Cabecalho
		Cabecalho cabecalho1 = new Cabecalho();
		cabecalho1.setCodRegistro("codRegistro");
		cabecalho1.setTribunal("tribunal");
		//Ementa
		Ementa ementa1 = new Ementa("ementa1");
		//Relatorio
		Relatorio relatorio1 = new Relatorio("relatorio1");
		//Encerramento
		Encerramento encerramento1 = new Encerramento("decisao1");
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
		// Partes
		List<Parte> partesBD = docJudBD.getPartes();
		partesBD.get(0).setTitulo("titulo11");
		partesBD.get(1).setNome("nome22");
		
		//Setando as novas propriedades no objeto
		docJudBD.setCabecalho(cabecalhoBD);
		docJudBD.setEmenta(ementaBD);
		docJudBD.setRelatorio(relatorioBD);
		docJudBD.setEncerramento(encerramentoBD);
		docJudBD.setVotos(votosBD);
		docJudBD.setPartes(partesBD);
		docJudManager.salvaDocumentoJuridico(docJudBD);
		
		/* ---------- Verifica se os elementos foram inseridos em suas respectivas tabelas  ----------*/
		assertEquals( 1, docJudManager.getDocumentosJuridicos().size() );
		assertEquals( 1, cabecalhoManager.getCabecalhos().size() );
		assertEquals( 1, ementaManager.getEmentas().size() );
		assertEquals( 1, relatorioManager.getRelatorios().size() );
		assertEquals( 1, encerramentoManager.getEncerramentos().size() );
		assertEquals( 2, votoManager.getVotos().size() );
		assertEquals( 2, parteManager.getPartes().size() );
		
		/* ---------- Verifica se as propriedades foram modificadas  ----------*/
		DocumentoJuridico docJudBD2 = docJudManager.getDocumentosJuridicos().get(0);
		assertEquals( docJudBD2.getCabecalho().getCodRegistro(), cabecalhoBD.getCodRegistro() );
		assertEquals( docJudBD2.getEmenta().getTexto(), ementaBD.getTexto() );
		assertEquals( docJudBD2.getRelatorio().getTexto(), relatorioBD.getTexto() );
		assertEquals( docJudBD2.getEncerramento().getDecisao(), encerramentoBD.getDecisao() );
		assertEquals( docJudBD2.getVotos().get(0).getTexto(), votosBD.get(0).getTexto() );
		assertEquals( docJudBD2.getVotos().get(1).getTexto(), votosBD.get(1).getTexto() );
		assertEquals( docJudBD2.getPartes().get(0).getTitulo(), partesBD.get(0).getTitulo() );
		assertEquals( docJudBD2.getPartes().get(1).getNome(), partesBD.get(1).getNome() );
	}
	
	/**
	 * Teste do metodos metodos alteraPropriedadeBD da classe DocJudManager
	 */
	@Test
	public void testAlteraPropriedades() {
		/* ---------- Verifica se a lista esta vazia ----------*/
		assertEquals(0, docJudManager.getDocumentosJuridicos().size());
		
		//Verifica se as demais listas também estão vazias
		assertEquals( 0, cabecalhoManager.getCabecalhos().size() );
		assertEquals( 0, ementaManager.getEmentas().size() );
		
		/* ---------- Elementos do DocumentoJuridico ----------*/		
		//Cabecalho
		Cabecalho cabecalho1 = new Cabecalho();
		cabecalho1.setCodRegistro("codRegistro");
		cabecalho1.setTribunal("tribunal");
		//Ementa
		Ementa ementa1 = new Ementa("ementa1");
		
		/* ---------- Criacao e Persistencia do DocumentoJuridico ----------*/	
		DocumentoJuridico docJud1 = new DocumentoJuridico();
		docJud1.setIdentificadorUnico("idUnico");
		docJud1.setCabecalho(cabecalho1);
		docJud1.setEmenta(ementa1);
		docJudManager.salvaDocumentoJuridico(docJud1);
		
		/* ---------- Verifica se os atributos e elementos foram persistidos corretamente ----------*/
		DocumentoJuridico docJudBD = docJudManager.getDocumentosJuridicos().get(0);
		
		/* ---------- ALTERACOES ----------*/
		// Cabecalho
		Cabecalho cabecalhoBD = new Cabecalho();
		cabecalhoBD.setCodRegistro("codRegistro2");
		// Ementa
		Ementa ementaBD = new Ementa();
		ementaBD.setTexto("ementa2");
		
		/* ---------- Alterando  ----------*/
		cabecalhoManager.alteraCabecalhoBD(cabecalhoBD, docJudBD);
		ementaManager.alteraEmentaBD(ementaBD, docJudBD);
		
		/* ---------- Verifica se os elementos foram inseridos em suas respectivas tabelas  ----------*/
		assertEquals( 1, docJudManager.getDocumentosJuridicos().size() );
		assertEquals( 1, cabecalhoManager.getCabecalhos().size() );
		assertEquals( 1, ementaManager.getEmentas().size() );	
		
		/* ---------- Verifica se as propriedades foram modificadas  ----------*/
		DocumentoJuridico docJudBD2 = docJudManager.getDocumentosJuridicos().get(0);
		assertEquals( docJudBD2.getCabecalho().getCodRegistro(), cabecalhoBD.getCodRegistro() );
		assertEquals( docJudBD2.getEmenta().getTexto(), ementaBD.getTexto() );
	}
	
	/**
	 * Testa remover as propriedades Cabecalho, Ementa, Relatorio e Encerramento e apos isso setar
	 * novamente essas propriedades
	 */
	@Test
	public void testRemoveESetaPropriedades() {
		/* ---------- Verifica se a lista esta vazia ----------*/
		assertEquals( 0, docJudManager.getDocumentosJuridicos().size() );
		
		//Cabecalho
		Cabecalho cabecalho1 = new Cabecalho();
		cabecalho1.setCodRegistro("codRegistro");
		cabecalho1.setTribunal("tribunal");
		//Ementa
		Ementa ementa1 = new Ementa("ementa1");
		//Relatorio
		Relatorio relatorio1 = new Relatorio("relatorio1");
		//Encerramento
		Encerramento encerramento1 = new Encerramento("decisao1");
		
		/* ---------- Criacao e Persistencia do DocumentoJuridico ----------*/	
		DocumentoJuridico docJud1 = new DocumentoJuridico();
		docJud1.setIdentificadorUnico("idUnico");
		docJud1.setCabecalho(cabecalho1);
		docJud1.setEmenta(ementa1);
		docJud1.setRelatorio(relatorio1);
		docJud1.setEncerramento(encerramento1);
		docJudManager.salvaDocumentoJuridico(docJud1);
		
		//Verifica a cardinalidade das listas
		assertEquals( 1, docJudManager.getDocumentosJuridicos().size() );
		assertEquals( 1, cabecalhoManager.getCabecalhos().size() );
		assertEquals( 1, ementaManager.getEmentas().size() );
		assertEquals( 1, relatorioManager.getRelatorios().size() );
		assertEquals( 1, encerramentoManager.getEncerramentos().size() );
		
		/* ---------- Remove os cabecalhos ----------*/
		cabecalhoManager.removeCabecalho( docJudManager.getDocumentosJuridicos().get(0) );
		ementaManager.removeEmenta( docJudManager.getDocumentosJuridicos().get(0) );
		relatorioManager.removeRelatorio( docJudManager.getDocumentosJuridicos().get(0) );
		encerramentoManager.removeEncerramento( docJudManager.getDocumentosJuridicos().get(0) );
		
		//Verifica a cardinalidade das listas
		assertEquals( 1, docJudManager.getDocumentosJuridicos().size() );
		assertEquals( 0, cabecalhoManager.getCabecalhos().size() );
		assertEquals( 0, ementaManager.getEmentas().size() );
		assertEquals( 0, relatorioManager.getRelatorios().size() );
		assertEquals( 0, encerramentoManager.getEncerramentos().size() );
		
		/* ---------- Setando novamente as propriedades ----------*/
		DocumentoJuridico docJudBD = docJudManager.getDocumentosJuridicos().get(0);
		cabecalhoManager.alteraCabecalhoBD(cabecalho1, docJudBD);
		ementaManager.alteraEmentaBD(ementa1, docJudBD);
		relatorioManager.alteraRelatorioBD(relatorio1, docJudBD);
		encerramentoManager.alteraEncerramentoBD(encerramento1, docJudBD);
		
		//Verifica a cardinalidade das listas
		assertEquals( 1, docJudManager.getDocumentosJuridicos().size() );
		assertEquals( 1, cabecalhoManager.getCabecalhos().size() );
		assertEquals( 1, ementaManager.getEmentas().size() );
		assertEquals( 1, relatorioManager.getRelatorios().size() );
		assertEquals( 1, encerramentoManager.getEncerramentos().size() );
		
		/* ---------- Verificando as propriedades ----------*/
		//Cabecalho
		assertEquals( docJudBD.getCabecalho().getDocumentoJuridico().getIdentificadorUnico(), 
				  	  docJud1.getCabecalho().getDocumentoJuridico().getIdentificadorUnico() );
		assertEquals( cabecalhoManager.getCabecalhos().get(0).getDocumentoJuridico().getIdentificadorUnico(),
					  docJudBD.getIdentificadorUnico() );
		//Ementa
		assertEquals( docJudBD.getEmenta().getDocumentoJuridico().getIdentificadorUnico(), 
				  	  docJud1.getEmenta().getDocumentoJuridico().getIdentificadorUnico() );
		assertEquals( ementaManager.getEmentas().get(0).getDocumentoJuridico().getIdentificadorUnico(),
					  docJudBD.getIdentificadorUnico() );
		//Relatorio
		assertEquals( docJudBD.getRelatorio().getDocumentoJuridico().getIdentificadorUnico(), 
				  	  docJud1.getRelatorio().getDocumentoJuridico().getIdentificadorUnico() );
		assertEquals( relatorioManager.getRelatorios().get(0).getDocumentoJuridico().getIdentificadorUnico(),
					  docJudBD.getIdentificadorUnico() );
		//Encerramento
		assertEquals( docJudBD.getEncerramento().getDocumentoJuridico().getIdentificadorUnico(), 
				  	  docJud1.getEncerramento().getDocumentoJuridico().getIdentificadorUnico() );
		assertEquals( encerramentoManager.getEncerramentos().get(0).getDocumentoJuridico().getIdentificadorUnico(),
					  docJudBD.getIdentificadorUnico() );
	}
	
	/**
	 * Testa as operacoes de adicionar, remover e alterar Votos.
	 * Votos tem um tratamento diferenciado das demais por se tratar de uma lista,
	 * dai um metodo so para ele.
	 */
	@Test
	public void testOperacoesVotos() {
		//Votos
		ArrayList<Voto> votos = new ArrayList<Voto>();
		votos.add( new Voto("voto1") );
		votos.add( new Voto("voto2") );
		
		/* ---------- Criacao e Persistencia do DocumentoJuridico ----------*/	
		DocumentoJuridico docJud1 = new DocumentoJuridico();
		docJud1.setIdentificadorUnico("idUnico");
		docJud1.setVotos(votos);
		docJudManager.salvaDocumentoJuridico(docJud1);
		
		DocumentoJuridico docJudBD = docJudManager.getDocumentosJuridicos().get(0);
		//Verifica a cardinalidade das listas
		assertEquals( 1, docJudManager.getDocumentosJuridicos().size() );
		assertEquals( 2, docJudBD.getVotos().size() );
		assertEquals( 2, votoManager.getVotos().size() );
		
		//Adicionando Votos
		votoManager.adicionaVoto(new Voto("voto3"), docJudBD);
		
		//Verifica a cardinalidade das listas
		assertEquals( 1, docJudManager.getDocumentosJuridicos().size() );
		assertEquals( 3, docJudManager.getDocumentosJuridicos().get(0).getVotos().size() );
		assertEquals( 3, votoManager.getVotos().size() );
		
		//Verifica as propriedades dos votos
		assertEquals( votoManager.getVotos().get(0).getDocumentoJuridico().getIdentificadorUnico(),
					  docJudBD.getIdentificadorUnico());
		assertEquals( votoManager.getVotos().get(1).getDocumentoJuridico().getIdentificadorUnico(),
				  docJudBD.getIdentificadorUnico());
		assertEquals( votoManager.getVotos().get(2).getDocumentoJuridico().getIdentificadorUnico(),
				  docJudBD.getIdentificadorUnico());
		
		//Removendo Votos
		Voto votoBD = docJudBD.getVotos().get(0);
		votoManager.removeVoto(votoBD, docJudBD);
		
		//Verifica a cardinalidade das listas
		assertEquals( 1, docJudManager.getDocumentosJuridicos().size() );
		assertEquals( 2, docJudManager.getDocumentosJuridicos().get(0).getVotos().size() );
		assertEquals( 2, votoManager.getVotos().size() );
		
		//Verifica os elementos que sobraram
		assertEquals( "voto2", docJudManager.getDocumentosJuridicos().get(0).getVotos().get(0).getTexto() );
		assertEquals( "voto3", docJudManager.getDocumentosJuridicos().get(0).getVotos().get(1).getTexto() );
		
		//Alterando um Voto
		votoManager.alteraVotoBD( docJudBD.getVotos().get(0), new Voto("voto1"), docJudBD);
		//Verificando se a alteracao foi feita corretamente
		assertEquals( "voto1", docJudManager.getDocumentosJuridicos().get(0).getVotos().get(0).getTexto() );
		assertEquals( "voto1", votoManager.getVotos().get(0).getTexto() );
		assertEquals( votoManager.getVotos().get(0).getDocumentoJuridico().getIdentificadorUnico(),
					  docJudBD.getIdentificadorUnico() );
	}
	
	/**
	 * Testa as operacoes de adicionar, remover e alterar Partes
	 * Partes tem um tratamento diferenciado das demais por se tratar de uma lista,
	 * dai um metodo so para ela.
	 */
	@Test
	public void testOperacoesPartes() {
		//Partes
		ArrayList<Parte> partes = new ArrayList<Parte>();
		partes.add( new Parte("titulo1", "nome1") );
		partes.add( new Parte("titulo2", "nome2") );
		
		/* ---------- Criacao e Persistencia do DocumentoJuridico ----------*/	
		DocumentoJuridico docJud1 = new DocumentoJuridico();
		docJud1.setIdentificadorUnico("idUnico");
		docJud1.setPartes(partes);
		docJudManager.salvaDocumentoJuridico(docJud1);
		
		DocumentoJuridico docJudBD = docJudManager.getDocumentosJuridicos().get(0);
		//Verifica a cardinalidade das listas
		assertEquals( 1, docJudManager.getDocumentosJuridicos().size() );
		assertEquals( 2, docJudBD.getPartes().size() );
		assertEquals( 2, parteManager.getPartes().size() );
		
		//Adicionando Votos
		parteManager.adicionaParte(new Parte("titulo3", " nome3"), docJudBD);
		
		//Verifica a cardinalidade das listas
		assertEquals( 1, docJudManager.getDocumentosJuridicos().size() );
		assertEquals( 3, docJudManager.getDocumentosJuridicos().get(0).getPartes().size() );
		assertEquals( 3, parteManager.getPartes().size() );
		
		//Verifica as propriedades dos votos
		assertEquals( parteManager.getPartes().get(0).getDocumentosJuridicos().get(0).getIdentificadorUnico(),
					  docJudBD.getIdentificadorUnico());
		assertEquals( parteManager.getPartes().get(1).getDocumentosJuridicos().get(0).getIdentificadorUnico(),
				  docJudBD.getIdentificadorUnico());
		assertEquals( parteManager.getPartes().get(2).getDocumentosJuridicos().get(0).getIdentificadorUnico(),
				  docJudBD.getIdentificadorUnico());
		
		//Removendo Partes
		Parte parteBD = docJudBD.getPartes().get(0);
		parteManager.removeParte(parteBD, docJudBD);
		
		//Verifica a cardinalidade das listas
		assertEquals( 1, docJudManager.getDocumentosJuridicos().size() );
		assertEquals( 2, docJudManager.getDocumentosJuridicos().get(0).getPartes().size() );
		assertEquals( 2, parteManager.getPartes().size() );
		
		//Verifica os elementos que sobraram
		assertEquals( "titulo2", docJudManager.getDocumentosJuridicos().get(0).getPartes().get(0).getTitulo() );
		assertEquals( "titulo3", docJudManager.getDocumentosJuridicos().get(0).getPartes().get(1).getTitulo() );
		
		//Alterando uma Parte
		parteManager.alteraParteBD( docJudBD.getPartes().get(0), new Parte("titulo1", "nome1"), docJudBD);
		//Verificando se a alteracao foi feita corretamente
		assertEquals( "titulo1", docJudManager.getDocumentosJuridicos().get(0).getPartes().get(0).getTitulo() );
		assertEquals( "nome1", docJudManager.getDocumentosJuridicos().get(0).getPartes().get(0).getNome() );
		assertEquals( "titulo1", parteManager.getPartes().get(0).getTitulo() );
		assertEquals( parteManager.getPartes().get(0).getDocumentosJuridicos().get(0).getIdentificadorUnico(),
					  docJudBD.getIdentificadorUnico() );
	}
	
	/**
	 * Testa as buscas no banco de dados por atributos
	 */
	@Test
	public void testBuscasPorAtributos() {
		/* ---------- Verifica se a lista esta vazia ----------*/
		assertEquals(0, docJudManager.getDocumentosJuridicos().size());
		
		//Verifica se as demais listas também estão vazias
		assertEquals( 0, cabecalhoManager.getCabecalhos().size() );
		assertEquals( 0, ementaManager.getEmentas().size() );
		assertEquals( 0, relatorioManager.getRelatorios().size() );
		assertEquals( 0, encerramentoManager.getEncerramentos().size() );
		assertEquals( 0, votoManager.getVotos().size() );
		assertEquals( 0, parteManager.getPartes().size() );
		
		/* ---------- Elementos do DocumentoJuridico ----------*/		
		//Cabecalho
		Cabecalho cabecalho1 = new Cabecalho();
		cabecalho1.setCodRegistro("codRegistro");
		cabecalho1.setTribunal("tribunal");
		//Ementa
		Ementa ementa1 = new Ementa("ementa1");
		//Relatorio
		Relatorio relatorio1 = new Relatorio("relatorio1");
		//Encerramento
		Encerramento encerramento1 = new Encerramento("decisao1");
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
		
		/* ---------- Buscas ----------*/
		//Cabecalho
		List<Cabecalho> cabecalhos = cabecalhoManager.selectCabecalhoPorAtributo("codRegistro", "codRegistro");
		assertEquals( cabecalhos.get(0).getCodRegistro(), cabecalho1.getCodRegistro() );
		assertEquals( cabecalhos.get(0).getDocumentoJuridico().getIdentificadorUnico(), 
					  docJud1.getIdentificadorUnico() );
		//Ementa
		List<Ementa> ementas = ementaManager.selectEmentaPorAtributo("texto", "ementa1");
		assertEquals( ementas.get(0).getTexto(), ementa1.getTexto() );
		assertEquals( ementas.get(0).getDocumentoJuridico().getIdentificadorUnico(), 
					  docJud1.getIdentificadorUnico() );
		//Relatorio
		List<Relatorio> relatorios = relatorioManager.selectRelatorioPorAtributo("texto", "relatorio1");
		assertEquals( relatorios.get(0).getTexto(), relatorio1.getTexto() );
		assertEquals( relatorios.get(0).getDocumentoJuridico().getIdentificadorUnico(), 
					  docJud1.getIdentificadorUnico() );
		//Encerramento
		List<Encerramento> encerramentos = encerramentoManager.selectEncerramentoPorAtributo("decisao", "decisao1");
		assertEquals( encerramentos.get(0).getDocumentoJuridico().getIdentificadorUnico(), 
					  docJud1.getIdentificadorUnico() );
	}
	
	/**
	 * Verifica se as referencias feitas a um documento juridico sao removidas quando ele eh removido
	 */
	@Test
	public void testRemoveDocumentoReferencias() {
		/* ---------- Verifica se a lista esta vazia ----------*/
		assertEquals(0, docJudManager.getDocumentosJuridicos().size());
		//DJ1
		DocumentoJuridico docJud1 = new DocumentoJuridico();
		docJud1.setIdentificadorUnico("idUnico1");
		docJud1.setDataJulgamento("16/11/2009");
		docJudManager.salvaDocumentoJuridico(docJud1);
		//DJ2
		DocumentoJuridico docJud2 = new DocumentoJuridico();
		docJud2.setIdentificadorUnico("idUnico2");
		docJud2.setDataJulgamento("16/11/2009");
		docJudManager.salvaDocumentoJuridico(docJud2);
		//Verifica a cardinalidade dos documentos
		assertEquals( 2, docJudManager.getDocumentosJuridicos().size() );
		
		/* ---------- Criando as Referencias ----------*/
		CitacaoDocJudManager.getInstance().criaCitacaoDocJud(docJud1, docJud2);
		CitacaoDocJudManager.getInstance().criaCitacaoDocJud(docJud2, docJud1);
		//Verifica a cardinalidade das citacoes
		assertEquals( 2, CitacaoDocJudManager.getInstance().getCitacoesDocJud().size() );
		
		/* ---------- Removendo docJud1 ----------*/
		docJudManager.removeDocumentoJuridico( docJud1 );
		//Verifica as cardinalidades
		assertEquals( 1, docJudManager.getDocumentosJuridicos().size() );
		//As citacoes tem que terem sido removidas
		assertEquals( 0, CitacaoDocJudManager.getInstance().getCitacoesDocJud().size() );
		/* ---------- Adicionando novamente docJud1 ----------*/
		DocumentoJuridico docJud3 = new DocumentoJuridico();
		docJud3.setIdentificadorUnico("idUnico3");
		docJud3.setDataJulgamento("16/11/2009");
		docJudManager.salvaDocumentoJuridico(docJud3);
		//Verifica os documentos
		assertEquals( 2, docJudManager.getDocumentosJuridicos().size() );
		assertEquals( docJud2.getIdentificadorUnico(), 
					  docJudManager.getDocumentosJuridicos().get(0).getIdentificadorUnico() );
		assertEquals( docJud3.getIdentificadorUnico(), 
				  docJudManager.getDocumentosJuridicos().get(1).getIdentificadorUnico() );
		
		/* ---------- Criando as Referencias novamente ----------*/
		CitacaoDocJudManager.getInstance().criaCitacaoDocJud(docJudManager.getDocumentosJuridicos().get(1),
														  docJudManager.getDocumentosJuridicos().get(0));
		CitacaoDocJudManager.getInstance().criaCitacaoDocJud(docJudManager.getDocumentosJuridicos().get(0),
														  docJudManager.getDocumentosJuridicos().get(1));
		//Verifica a cardinalidade das citacoes
		assertEquals( 2, CitacaoDocJudManager.getInstance().getCitacoesDocJud().size() );
		
		/* ---------- Removendo docJud2 ----------*/
		docJudManager.removeDocumentoJuridico( docJudManager.getDocumentosJuridicos().get(1) );
		//Verifica as cardinalidades
		assertEquals( 1, docJudManager.getDocumentosJuridicos().size() );
		//As citacoes tem que terem sido removidas
		assertEquals( 0, CitacaoDocJudManager.getInstance().getCitacoesDocJud().size() );
	}
}
