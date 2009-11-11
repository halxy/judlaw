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
 * Classe responsável por gerenciar o módulo dos documentos jurídicos
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
	 * removeDocumentosJuridicos - por se tratar de um BD temporal, usar apenas em ocasiões especiais
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
		cabecalhoBD.setCodRegistro( cabecalho.getCodRegistro() );
		cabecalhoBD.setOrgaoJulgador( cabecalho.getOrgaoJulgador() );
		cabecalhoBD.setTribunal( cabecalho.getTribunal() );
		dbManager.save(docJud);
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
	 * Altera uma ementa que ja esta inserida no Banco de Dados
	 * @param ementa
	 * @param docJud
	 */
	public void alteraEmentaBD(Ementa ementa, DocumentoJuridico docJud) {
		Ementa ementaBD = docJud.getEmenta();
		ementaBD.setTexto( ementa.getTexto() );
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
	 * Altera um relatorio que ja esta inserido no Banco de Dados
	 * @param ementa
	 * @param docJud
	 */
	public void alteraRelatorioBD(Relatorio relatorio, DocumentoJuridico docJud) {
		Relatorio relatorioBD = docJud.getRelatorio();
		relatorioBD.setTexto( relatorio.getTexto() );
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
	 * Altera um encerramento que ja esta inserido no Banco de Dados
	 * @param encerramento
	 * @param docJud
	 */
	public void alteraEncerramentoBD(Encerramento encerramento, DocumentoJuridico docJud) {
		Encerramento encerramentoBD = docJud.getEncerramento();
		encerramentoBD.setDecisao( encerramento.getDecisao() );
		encerramento.setLocal( encerramento.getLocal() );
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
}
