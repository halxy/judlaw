/* 
 * Universidade Federal de Campina Grande
 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas	
 */
package judlaw.model.dbmanager.ref;

import java.util.List;

import judlaw.model.bean.docjud.DocumentoJuridico;
import judlaw.model.bean.law.ElementoNorma;
import judlaw.model.bean.law.Norma;
import judlaw.model.bean.ref.CitacaoDocJud;
import judlaw.model.dbmanager.DBManager;

/**
 * Classe CitacaoDocJudManager - responsável por gerenciar as operacoes no banco de dados das citacoesDocJud
 * @author Halley Freitas
 *
 */
public class CitacaoDocJudManager {
	
	private static CitacaoDocJudManager citacaoDocJudManager = null;
	private DBManager dbManager = DBManager.getInstance();	
	
   /**
    * Retorna uma instancia da classe CitacaoDocJudManager
    * @return
    */
    public static CitacaoDocJudManager getInstance(){
        if(citacaoDocJudManager == null)
        	citacaoDocJudManager = new CitacaoDocJudManager();
        return citacaoDocJudManager;
    }

	@SuppressWarnings("unchecked")
	public List<CitacaoDocJud> getCitacoesDocJud() {
		return dbManager.selectAll( new CitacaoDocJud() );
	}
	
	/**
	 * CitacaoDocJud DocJud -> DocJud
	 * @param documentoJuridicoOrigem
	 * @param documentoJuridicoDestino
	 * @param data
	 */
	public void criaCitacaoDocJud(DocumentoJuridico documentoJuridicoOrigem, DocumentoJuridico documentoJuridicoDestino,
			String data) {
		CitacaoDocJud citacaoDocJud = new CitacaoDocJud(documentoJuridicoOrigem, documentoJuridicoDestino, data);
		dbManager.save(citacaoDocJud);
		documentoJuridicoOrigem.getCitacoesFeitas().add( citacaoDocJud );
		documentoJuridicoDestino.getCitacoesRecebidasDocJud().add( citacaoDocJud );
		dbManager.save(documentoJuridicoOrigem);
		dbManager.save(documentoJuridicoDestino);
	}
	
	/**
	 * CitacaoDocJud DocJud -> Norma
	 * @param documentoJuridicoOrigem
	 * @param normaDestino
	 * @param data
	 */
	public void criaCitacaoDocJud(DocumentoJuridico documentoJuridicoOrigem, Norma normaDestino,
			String data) {
		CitacaoDocJud citacaoDocJud = new CitacaoDocJud(documentoJuridicoOrigem, normaDestino, data);
		dbManager.save(citacaoDocJud);
		documentoJuridicoOrigem.getCitacoesFeitas().add( citacaoDocJud );
		normaDestino.getCitacoesRecebidasDocJud().add( citacaoDocJud );
		dbManager.save(documentoJuridicoOrigem);
		dbManager.save(normaDestino);
	}
	
	/**
	 * CitacaoDocJud DocJud -> ElementoNorma
	 * @param documentoJuridicoOrigem
	 * @param elementoNormaDestino
	 * @param data
	 */
	public void criaCitacaoDocJud(DocumentoJuridico documentoJuridicoOrigem, ElementoNorma elementoNormaDestino,
			String data) {
		CitacaoDocJud citacaoDocJud = new CitacaoDocJud(documentoJuridicoOrigem, elementoNormaDestino, data);
		dbManager.save(citacaoDocJud);
		documentoJuridicoOrigem.getCitacoesFeitas().add( citacaoDocJud );
		elementoNormaDestino.getCitacoesRecebidasDocJud().add( citacaoDocJud );
		dbManager.save(documentoJuridicoOrigem);
		dbManager.save(elementoNormaDestino);
	}
	
	/**
	 * Remove uma CitacaoDocJud do Banco de dados
	 * @param citacaoDocJud
	 */
	public void removeCitacaoDocJud(CitacaoDocJud citacaoDocJud) {
    	//Origem
    	citacaoDocJud.getDocumentoJuridicoOrigem().getCitacoesFeitas().remove( citacaoDocJud );
    	dbManager.save( citacaoDocJud.getDocumentoJuridicoOrigem() );
    	//Destino
    	if( citacaoDocJud.getDocumentoJuridicoDestino() != null ){
    		citacaoDocJud.getDocumentoJuridicoDestino().getCitacoesRecebidasDocJud().remove( citacaoDocJud );
    		dbManager.save( citacaoDocJud.getDocumentoJuridicoDestino() );
    	} else if (citacaoDocJud.getNormaDestino() != null ){
    		citacaoDocJud.getNormaDestino().getCitacoesRecebidasDocJud().remove( citacaoDocJud );
    		dbManager.save( citacaoDocJud.getNormaDestino() );
    	} else {
    		citacaoDocJud.getElementoNormaDestino().getCitacoesRecebidasDocJud().remove( citacaoDocJud );
    		dbManager.save( citacaoDocJud.getElementoNormaDestino() );
    	}
    	dbManager.remove( citacaoDocJud );
    }
	
	/**
	 * Altera uma citacaoDocJud
	 * @param newCitacaoDJud
	 * @param oldCitacaoDocJud
	 */
	public void alteraCitacaoDocJud(CitacaoDocJud newCitacaoDJud, CitacaoDocJud oldCitacaoDocJud){
		oldCitacaoDocJud.setData( newCitacaoDJud.getData() );
		dbManager.save( oldCitacaoDocJud ); 
	}
}
