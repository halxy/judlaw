package judlaw.model.manager;

import java.util.List;

import judlaw.model.bean.docjud.Cabecalho;
import judlaw.model.bean.docjud.DocumentoJuridico;
import judlaw.model.bean.docjud.Ementa;
import judlaw.model.bean.docjud.Encerramento;
import judlaw.model.bean.docjud.Parte;
import judlaw.model.bean.docjud.Relatorio;
import judlaw.model.bean.docjud.Voto;


/**
 * Classe respons�vel por gerenciar o m�dulo dos documentos jur�dicos
 * @author Halley Freitas
 *
 */
public class DocJudManager {
	
	private static DocJudManager docjudManager = null;
	private static DBManager dbManager = DBManager.getInstance();
	
	
   /**
    * Retorna uma instancia da classe LawManager
    * @return
    */
    public static DocJudManager getInstance(){
        if(docjudManager == null)
        	docjudManager = new DocJudManager();
        return docjudManager;
    }
    
    /* ------------------------------------------------------------------ */
    /* -------------------- OPERACOES DOCUMENTOJURIDICO ----------------- */
    /* ------------------------------------------------------------------ */
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
	 * removeDocumentosJuridicos - por se tratar de um BD temporal, usar apenas em ocasi�es especiais
	 */
	public void removeDocumentosJuridicos() {
		dbManager.removeAll( new DocumentoJuridico() );
	}
	
	/* ------------------------------------------------------------------ */
    /* -------------------- OPERACOES CABECALHO ------------------------- */
    /* ------------------------------------------------------------------ */
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<Cabecalho> getCabecalhos() {
		return dbManager.selectAll( new Cabecalho() );
	}
	
	public void removeCabecalhos() {
		dbManager.removeAll( new Cabecalho() );
	}
	
	/**
	 * Altera um cabecalho que ja esta inserido no Banco de Dados
	 * @param cabecalho
	 * @param docJud
	 */
	public void alteraCabecalhoBD(Cabecalho cabecalho, DocumentoJuridico docJud) {
		Cabecalho cabecalhoBD = docJud.getCabecalho();
		if(cabecalhoBD == null) {
			cabecalhoBD = new Cabecalho();
		}
		cabecalhoBD.setCodRegistro( cabecalho.getCodRegistro() );
		cabecalhoBD.setOrgaoJulgador( cabecalho.getOrgaoJulgador() );
		cabecalhoBD.setTribunal( cabecalho.getTribunal() );
		cabecalhoBD.setDocumentoJuridico(docJud);
		dbManager.save(cabecalhoBD);
		docJud.setCabecalho(cabecalhoBD);
		dbManager.save(docJud);
	}
	
	/**
	 * Remove o cabecalho de um documentoJuridico
	 * @param docJud
	 */
	public void removeCabecalho(DocumentoJuridico docJud) {
		Cabecalho cabecalho = docJud.getCabecalho();
		docJud.setCabecalho( null );
		dbManager.save(docJud);
		dbManager.remove(cabecalho);
	}
	
	/* ------------------------------------------------------------------ */
    /* -------------------- OPERACOES EMENTA ---------------------------- */
    /* ------------------------------------------------------------------ */
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<Ementa> getEmentas() {
		return dbManager.selectAll( new Ementa() );
	}
	
	public void removeEmentas() {
		dbManager.removeAll( new Ementa() );
	}
	
	/**
	 * Remove ementa
	 * @param docJud
	 */
	public void removeEmenta(DocumentoJuridico docJud) {
		Ementa ementa = docJud.getEmenta();
		docJud.setEmenta(null);
		dbManager.save(docJud);
		dbManager.remove(ementa);
	}
	
	/**
	 * Altera uma ementa que ja esta inserida no Banco de Dados
	 * @param ementa
	 * @param docJud
	 */
	public void alteraEmentaBD(Ementa ementa, DocumentoJuridico docJud) {
		Ementa ementaBD = docJud.getEmenta();
		if(ementaBD == null) {
			ementaBD = new Ementa();
		}
		ementaBD.setTexto( ementa.getTexto() );
		ementaBD.setDocumentoJuridico(docJud);
		dbManager.save(ementaBD);
		docJud.setEmenta(ementaBD);
		dbManager.save(docJud);
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
	
	/* ------------------------------------------------------------------ */
    /* -------------------- OPERACOES ENCERRAMENTO ---------------------- */
    /* ------------------------------------------------------------------ */
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<Encerramento> getEncerramentos() {
		return dbManager.selectAll( new Encerramento() );
	}
	
	public void removeEncerramentos() {
		dbManager.removeAll( new Encerramento() );
	}
	
	/**
	 * Remove encerramento
	 * @param docJud
	 */
	public void removeEncerramento(DocumentoJuridico docJud) {
		Encerramento encerramento = docJud.getEncerramento();
		docJud.setEncerramento(null);
		dbManager.save(docJud);
		dbManager.remove(encerramento);
	}
	
	/**
	 * Altera um encerramento que ja esta inserido no Banco de Dados
	 * @param encerramento
	 * @param docJud
	 */
	public void alteraEncerramentoBD(Encerramento encerramento, DocumentoJuridico docJud) {
		Encerramento encerramentoBD = docJud.getEncerramento();
		if(encerramentoBD == null) {
			encerramentoBD = new Encerramento();
		}
		encerramentoBD.setDecisao( encerramento.getDecisao() );
		encerramento.setLocal( encerramento.getLocal() );
		encerramentoBD.setDocumentoJuridico(docJud);
		dbManager.save(encerramentoBD);
		docJud.setEncerramento(encerramentoBD);
		dbManager.save(docJud);
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
	
	/* ------------------------------------------------------------------ */
    /* -------------------- OPERACOES Partes ---------------------------- */
    /* ------------------------------------------------------------------ */
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<Parte> getPartes() {
		return dbManager.selectAll( new Parte() );
	}
	
	public void removePartes() {
		dbManager.removeAll( new Parte() );
	}
	
	public void adicionaParte(Parte parte, DocumentoJuridico docJud) {
		List<Parte> partes = docJud.getPartes();
		partes.add(parte);
		dbManager.save( docJud );
		parte.getDocumentosJuridicos().add(docJud);
		dbManager.save(parte);
	}
	
	public void removeParte(Parte parte, DocumentoJuridico docJud) {
		List<Parte> partes = docJud.getPartes();
		partes.remove( parte );
		dbManager.save( docJud ); 
		dbManager.remove( parte );
	}
}
