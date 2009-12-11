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
import judlaw.model.bean.law.ElementoNorma;
import judlaw.model.bean.law.Norma;
import judlaw.model.bean.ref.CitacaoTextLeg;
import judlaw.model.persistence.dbmanager.DBManager;

/**
 * Classe CitacaoTextLegManager - responsável por gerenciar as operacoes no banco de dados das citacoesTextLeg 
 * @author Halley Freitas
 *
 */
public class CitacaoTextLegManager {

	private static CitacaoTextLegManager citacaoTextLegManager = null;
	private DBManager dbManager = DBManager.getInstance();	
	
   /**
    * Retorna uma instancia da classe CitacaoTextLegManager
    * @return
    */
    public static CitacaoTextLegManager getInstance(){
        if(citacaoTextLegManager == null)
        	citacaoTextLegManager = new CitacaoTextLegManager();
        return citacaoTextLegManager;
    }

	@SuppressWarnings("unchecked")
	public List<CitacaoTextLeg> getCitacoesTextLeg() {
		return dbManager.selectAll( new CitacaoTextLeg() );
	}
	
	/**
	 * CitacaoTextLeg Norma -> Norma
	 * @param normaOrigem
	 * @param normaDestino
	 * @param data
	 */
	public void criaCitacaoTextLeg(Norma normaOrigem, Norma normaDestino, String data){
    	CitacaoTextLeg citacaoTextLeg = new CitacaoTextLeg(normaOrigem, normaDestino, data);
    	dbManager.save(citacaoTextLeg);
    	normaOrigem.getCitacoesFeitas().add( citacaoTextLeg );
    	normaDestino.getCitacoesRecebidasTextLeg().add( citacaoTextLeg );
    	dbManager.save(normaOrigem);
    	dbManager.save(normaDestino);
	}
	
	/**
	 * CitacaoTextLeg Norma -> ElementoNorma
	 * @param normaOrigem
	 * @param eleNormanormaDestino
	 * @param data
	 */
	public void criaCitacaoTextLeg(Norma normaOrigem, ElementoNorma elementoNormaDestino, String data){
    	CitacaoTextLeg citacaoTextLeg = new CitacaoTextLeg(normaOrigem, elementoNormaDestino, data);
    	dbManager.save(citacaoTextLeg);
    	normaOrigem.getCitacoesFeitas().add( citacaoTextLeg );
    	elementoNormaDestino.getCitacoesRecebidasTextLeg().add( citacaoTextLeg );
    	dbManager.save(normaOrigem);
    	dbManager.save(elementoNormaDestino);
	}
	
	/**
	 * CitacaoTextLeg Norma -> DocumentoJuridico
	 * @param normaOrigem
	 * @param documentoJuridicoDestino
	 * @param data
	 */
	public void criaCitacaoTextLeg(Norma normaOrigem, DocumentoJuridico documentoJuridicoDestino, String data){
    	CitacaoTextLeg citacaoTextLeg = new CitacaoTextLeg(normaOrigem, documentoJuridicoDestino, data);
    	dbManager.save(citacaoTextLeg);
    	normaOrigem.getCitacoesFeitas().add( citacaoTextLeg );
    	documentoJuridicoDestino.getCitacoesRecebidasTextLeg().add( citacaoTextLeg );
    	dbManager.save(normaOrigem);
    	dbManager.save(documentoJuridicoDestino);
	}
	
	/**
	 * CitacaoTextLeg ElementoNorma -> Norma
	 * @param elementoNormaOrigem
	 * @param normaDestino
	 * @param data
	 */
	public void criaCitacaoTextLeg(ElementoNorma elementoNormaOrigem, Norma normaDestino, String data){
    	CitacaoTextLeg citacaoTextLeg = new CitacaoTextLeg(elementoNormaOrigem, normaDestino, data);
    	dbManager.save(citacaoTextLeg);
    	elementoNormaOrigem.getCitacoesFeitas().add( citacaoTextLeg );
    	normaDestino.getCitacoesRecebidasTextLeg().add( citacaoTextLeg );
    	dbManager.save(elementoNormaOrigem);
    	dbManager.save(normaDestino);
	}
	
	/**
	 * CitacaoTextLeg ElementoNorma -> ElementoNorma
	 * @param elementoNormaOrigem
	 * @param elementoNormaDestino
	 * @param data
	 */
	public void criaCitacaoTextLeg(ElementoNorma elementoNormaOrigem, ElementoNorma elementoNormaDestino, String data){
    	CitacaoTextLeg citacaoTextLeg = new CitacaoTextLeg(elementoNormaOrigem, elementoNormaDestino, data);
    	dbManager.save(citacaoTextLeg);
    	elementoNormaOrigem.getCitacoesFeitas().add( citacaoTextLeg );
    	elementoNormaDestino.getCitacoesRecebidasTextLeg().add( citacaoTextLeg );
    	dbManager.save(elementoNormaOrigem);
    	dbManager.save(elementoNormaDestino);
	}
	
	/**
	 * CitacaoTextLeg ElementoNorma -> DocumentoJuridico
	 * @param elementoNormaOrigem
	 * @param documentoJuridicoDestino
	 * @param data
	 */
	public void criaCitacaoTextLeg(ElementoNorma elementoNormaOrigem, DocumentoJuridico documentoJuridicoDestino, String data){
    	CitacaoTextLeg citacaoTextLeg = new CitacaoTextLeg(elementoNormaOrigem, documentoJuridicoDestino, data);
    	dbManager.save(citacaoTextLeg);
    	elementoNormaOrigem.getCitacoesFeitas().add( citacaoTextLeg );
    	documentoJuridicoDestino.getCitacoesRecebidasTextLeg().add( citacaoTextLeg );
    	dbManager.save(elementoNormaOrigem);
    	dbManager.save(documentoJuridicoDestino);
	}
	
	/**
	 * Remove uma CitacaoTextLeg do Banco de dados
	 * @param citacaoTextLeg
	 */
	public void removeCitacaoTextLeg(CitacaoTextLeg citacaoTextLeg) {
		//Origem
    	if( citacaoTextLeg.getNormaOrigem() != null ) {
    		citacaoTextLeg.getNormaOrigem().getCitacoesFeitas().remove( citacaoTextLeg );
    		dbManager.save( citacaoTextLeg.getNormaOrigem() );
    	} else {
    		citacaoTextLeg.getElementoNormaOrigem().getCitacoesFeitas().remove( citacaoTextLeg );
    		dbManager.save( citacaoTextLeg.getElementoNormaOrigem() );
    	}
    	//Destino
    	if( citacaoTextLeg.getNormaDestino() != null ){
    		citacaoTextLeg.getNormaDestino().getCitacoesRecebidasTextLeg().remove( citacaoTextLeg );
    		dbManager.save( citacaoTextLeg.getNormaDestino() );
    	} else if ( citacaoTextLeg.getElementoNormaDestino() != null ){
    		citacaoTextLeg.getElementoNormaDestino().getCitacoesRecebidasTextLeg().remove( citacaoTextLeg );
    		dbManager.save( citacaoTextLeg.getElementoNormaDestino() );
    	} else {
    		citacaoTextLeg.getDocumentoJuridicoDestino().getCitacoesRecebidasTextLeg().remove( citacaoTextLeg );
    		dbManager.save( citacaoTextLeg.getDocumentoJuridicoDestino() );
    	}
    	dbManager.remove( citacaoTextLeg );
    }
	
	/**
	 * Altera a data de uma citacaoTextLeg
	 * @param newCitacaoTextLeg
	 * @param oldCitacaoTextLeg
	 */
	public void alteraCitacaoTextLeg(CitacaoTextLeg newCitacaoTextLeg, CitacaoTextLeg oldCitacaoTextLeg) {
		oldCitacaoTextLeg.setData( newCitacaoTextLeg.getData() );
		dbManager.save( oldCitacaoTextLeg );
	}
}
