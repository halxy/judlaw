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
}
