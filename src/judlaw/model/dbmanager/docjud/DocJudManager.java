/* 
 * Universidade Federal de Campina Grande
 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas	
 */
package judlaw.model.dbmanager.docjud;

import java.util.List;

import judlaw.model.bean.docjud.Cabecalho;
import judlaw.model.bean.docjud.DocumentoJuridico;
import judlaw.model.bean.docjud.Ementa;
import judlaw.model.bean.docjud.Encerramento;
import judlaw.model.bean.docjud.Parte;
import judlaw.model.bean.docjud.Relatorio;
import judlaw.model.bean.docjud.Voto;
import judlaw.model.dbmanager.DBManager;


/**
 * Classe DocJudManager - responsável por gerenciar as operacoes no banco de dados dos documentos jurídicos 
 * @author Halley Freitas
 *
 */
public class DocJudManager {
	
	private static DocJudManager docjudManager = null;
	private static DBManager dbManager = DBManager.getInstance();
	
	
   /**
    * Retorna uma instancia da classe DocJudManager
    * @return
    */
    public static DocJudManager getInstance(){
        if(docjudManager == null)
        	docjudManager = new DocJudManager();
        return docjudManager;
    }
    /**
     * 
     * @param documentoJuridico
     */
    public void salvaDocumentoJuridico(DocumentoJuridico documentoJuridico) {
    	//Setando Cabecalho
    	Cabecalho cabecalho = documentoJuridico.getCabecalho();
    	cabecalho.setDocumentoJuridico(documentoJuridico);
    	
    	//Setando Ementa
    	Ementa ementa = documentoJuridico.getEmenta();
    	ementa.setDocumentoJuridico(documentoJuridico);
    	
    	//Setando Encerramento
    	Encerramento encerramento = documentoJuridico.getEncerramento();
    	encerramento.setDocumentoJuridico(documentoJuridico);
    	
    	//Setando Relatorio
    	Relatorio relatorio = documentoJuridico.getRelatorio();
    	relatorio.setDocumentoJuridico(documentoJuridico);
    	
    	//Setando Votos
    	List<Voto> votos = documentoJuridico.getVotos();
    	for(Voto v : votos) {
    		v.setDocumentoJuridico(documentoJuridico);
    	}
    	//Setando Partes
    	List<Parte> partes = documentoJuridico.getPartes();
    	for(Parte p : partes) {
    		p.getDocumentosJuridicos().add(documentoJuridico);
    	}
    	dbManager.save(documentoJuridico);
    }
    
    @SuppressWarnings("unchecked")
	public List<DocumentoJuridico> getDocumentosJuridicos() {
		return dbManager.selectAll( new DocumentoJuridico() );
	}
	
	/**
	 * removeDocumentosJuridicos - por se tratar de um BD temporal, usar apenas em ocasiões especiais
	 */
	public void removeDocumentosJuridicos() {
		dbManager.removeAll( new DocumentoJuridico() );
	}
	
	/**
	 * Remove um DocumentoJuridico do Banco de Dados
	 * @param documentoJuridico
	 */
	public void removeDocumentoJuridico(DocumentoJuridico documentoJuridico){
		dbManager.remove(documentoJuridico);
	}
	
	/* ------------------------------------------------------------------ */
    /* -------------------- OPERACOES RELATORIO ------------------------- */
    /* ------------------------------------------------------------------ */
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<Relatorio> getRelatorios() {
		return dbManager.selectAll( new Relatorio() );
	}
	
	public void removeRelatorios() {
		dbManager.removeAll( new Relatorio() );
	}
	
	/**
	 * Remove relatoro
	 * @param docJud
	 */
	public void removeRelatorio(DocumentoJuridico docJud) {
		Relatorio relatorio = docJud.getRelatorio();
		docJud.setRelatorio(null);
		dbManager.save(docJud);
		dbManager.remove(relatorio);
	}
	
	/**
	 * Altera um relatorio que ja esta inserido no Banco de Dados
	 * @param ementa
	 * @param docJud
	 */
	public void alteraRelatorioBD(Relatorio relatorio, DocumentoJuridico docJud) {
		Relatorio relatorioBD = docJud.getRelatorio();
		if(relatorioBD == null) {
			relatorioBD = new Relatorio();
		}
		relatorioBD.setTexto( relatorio.getTexto() );
		relatorioBD.setDocumentoJuridico(docJud);
		dbManager.save(relatorioBD);
		docJud.setRelatorio(relatorioBD);
		dbManager.save(docJud);
	}
	
	@SuppressWarnings("unchecked")
	public List<Relatorio> selectRelatorioPorAtributo(String atributo, String valor) {
		return dbManager.selectObjectsByField(new Relatorio(), atributo, valor);
	}
	
	/* ------------------------------------------------------------------ */
    /* -------------------- OPERACOES VOTO ------------------------------ */
    /* ------------------------------------------------------------------ */
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<Voto> getVotos() {
		return dbManager.selectAll( new Voto() );
	}
	
	public void removeVotos() {
		dbManager.removeAll( new Voto() );
	}
	
	public void adicionaVoto(Voto voto, DocumentoJuridico docJud) {
		List<Voto> votos = docJud.getVotos();
		votos.add(voto);
		dbManager.save( docJud );
		voto.setDocumentoJuridico(docJud);
		dbManager.save(voto);
	}
	
	public void removeVoto(Voto voto, DocumentoJuridico docJud) {
		List<Voto> votos = docJud.getVotos();
		votos.remove( voto );
		dbManager.save( docJud ); 
		dbManager.remove( voto );
	}
	
	public void alteraVotoBD(Voto votoLista, Voto newVoto, DocumentoJuridico docJud) {
		List<Voto> votos = docJud.getVotos();
		votos.get( votos.indexOf(votoLista) ).setTexto( newVoto.getTexto() );
		dbManager.save( docJud ); 
	}
	
	@SuppressWarnings("unchecked")
	public List<Voto> selectVotoPorAtributo(String atributo, String valor) {
		return dbManager.selectObjectsByField(new Voto(), atributo, valor);
	}
}
