package judlaw.model.manager;

import java.util.List;

import judlaw.model.bean.docjud.Cabecalho;
import judlaw.model.bean.docjud.DocumentoJuridico;


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
    
//    /**
//     * Persiste um Documento Juridico na base de dados
//     * @param elementoNorma
//     */
//	public void saveDocumentoJuridico(DocumentoJuridico documentoJuridico) {
//		HibernateUtil.beginTransaction();
//		HibernateUtil.getSession().save(documentoJuridico);
//		HibernateUtil.getSession().flush();
//		HibernateUtil.getSession().save(documentoJuridico.getCabecalho());
//		HibernateUtil.commitTransaction();
//		HibernateUtil.closeSession();
//	}
    
    public void salvaDocumentoJuridico(DocumentoJuridico documentoJuridico) {
    	Cabecalho cabecalho = documentoJuridico.getCabecalho();
    	cabecalho.setDocumentoJuridico(documentoJuridico);
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
    
}
