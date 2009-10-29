package judlaw.model.manager;

import judlaw.model.docjud.DocumentoJuridico;
import judlaw.model.util.HibernateUtil;


/**
 * Classe responsável por gerenciar o módulo dos documentos jurídicos
 * @author Halley Freitas
 *
 */
public class DocJudManager {
	
	private static DocJudManager docjudManager = null;
//	private DBManager dbManager = DBManager.getInstance();
//	private TimeManager timeManager = TimeManager.getInstance();
	
	
   /**
    * Retorna uma instancia da classe LawManager
    * @return
    */
    public static DocJudManager getInstance(){
        if(docjudManager == null)
        	docjudManager = new DocJudManager();
        return docjudManager;
    }
    
    /**
     * Persiste um Documento Juridico na base de dados
     * @param elementoNorma
     */
	public void saveDocumentoJuridico(DocumentoJuridico documentoJuridico) {
		HibernateUtil.beginTransaction();
		HibernateUtil.getSession().save(documentoJuridico);
		HibernateUtil.getSession().flush();
		HibernateUtil.getSession().save(documentoJuridico.getCabecalho());
		HibernateUtil.commitTransaction();
		HibernateUtil.closeSession();
	}
    
}
