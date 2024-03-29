/* 
 * Universidade Federal de Campina Grande
 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas	
 */
package judlaw.model.persistence.dbmanager.ref;

import java.util.List;

import judlaw.model.bean.docjud.DocumentoJuridico;
import judlaw.model.bean.doutrina.Doutrina;
import judlaw.model.bean.law.ElementoNorma;
import judlaw.model.bean.law.Norma;
import judlaw.model.bean.ref.CitacaoDocJud;
import judlaw.model.persistence.dbmanager.DBManager;

/**
 * Classe CitacaoDocJudManager. Responsável por gerenciar as operacoes no banco de dados das citacoesDocJud
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
	 */
	public void criaCitacaoDocJud(DocumentoJuridico documentoJuridicoOrigem, DocumentoJuridico documentoJuridicoDestino) {
		CitacaoDocJud citacaoDocJud = new CitacaoDocJud(documentoJuridicoOrigem, documentoJuridicoDestino);
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
	 */
	public void criaCitacaoDocJud(DocumentoJuridico documentoJuridicoOrigem, Norma normaDestino) {
		CitacaoDocJud citacaoDocJud = new CitacaoDocJud(documentoJuridicoOrigem, normaDestino);
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
	 */
	public void criaCitacaoDocJud(DocumentoJuridico documentoJuridicoOrigem, ElementoNorma elementoNormaDestino) {
		CitacaoDocJud citacaoDocJud = new CitacaoDocJud(documentoJuridicoOrigem, elementoNormaDestino);
		dbManager.save(citacaoDocJud);
		documentoJuridicoOrigem.getCitacoesFeitas().add( citacaoDocJud );
		elementoNormaDestino.getCitacoesRecebidasDocJud().add( citacaoDocJud );
		dbManager.save(documentoJuridicoOrigem);
		dbManager.save(elementoNormaDestino);
	}
	
	/**
	 * CitacaoDocJud DocJud -> Doutrina
	 * @param documentoJuridicoOrigem
	 * @param doutrinaDestino
	 */
	public void criaCitacaoDocJud(DocumentoJuridico documentoJuridicoOrigem, Doutrina doutrinaDestino) {
		CitacaoDocJud citacaoDocJud = new CitacaoDocJud(documentoJuridicoOrigem, doutrinaDestino);
		dbManager.save(citacaoDocJud);
		documentoJuridicoOrigem.getCitacoesFeitas().add( citacaoDocJud );
		doutrinaDestino.getCitacoesRecebidasDocJud().add( citacaoDocJud );
		dbManager.save(documentoJuridicoOrigem);
		dbManager.save(doutrinaDestino);
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
    	if( citacaoDocJud.getDocumentoJuridicoDestino() != null ){ //DocumentoJuridico
    		citacaoDocJud.getDocumentoJuridicoDestino().getCitacoesRecebidasDocJud().remove( citacaoDocJud );
    		dbManager.save( citacaoDocJud.getDocumentoJuridicoDestino() );
    	} else if (citacaoDocJud.getNormaDestino() != null ){ //Norma
    		citacaoDocJud.getNormaDestino().getCitacoesRecebidasDocJud().remove( citacaoDocJud );
    		dbManager.save( citacaoDocJud.getNormaDestino() );
    	} else if (citacaoDocJud.getElementoNormaDestino() != null ){ //ElementoNorma
    		citacaoDocJud.getElementoNormaDestino().getCitacoesRecebidasDocJud().remove( citacaoDocJud );
    		dbManager.save( citacaoDocJud.getElementoNormaDestino() );
    	} else { //Doutrina
    		citacaoDocJud.getDoutrinaDestino().getCitacoesRecebidasDocJud().remove( citacaoDocJud );
    		dbManager.save( citacaoDocJud.getDoutrinaDestino() );
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
