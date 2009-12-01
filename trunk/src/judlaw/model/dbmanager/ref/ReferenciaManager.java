/* 
 * Universidade Federal de Campina Grande
 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas	
 */
package judlaw.model.dbmanager.ref;

import judlaw.model.bean.ref.Alteracao;
import judlaw.model.bean.ref.CitacaoDocJud;
import judlaw.model.bean.ref.CitacaoTextLeg;
import judlaw.model.dbmanager.DBManager;


/**
 * Classe ReferenciaManager - responsável por gerenciar as operacoes no banco de dados das referencias: 
 * Alteracao, CitacaoDocJud e CitacaoDocLeg;
 * @author Halley Freitas
 *
 */
public class ReferenciaManager {
	
	private static ReferenciaManager referenciaManager = null;
	private static DBManager dbManager = DBManager.getInstance();
	
	
   /**
    * Retorna uma instancia da classe ReferenciaManager
    * @return
    */
    public static ReferenciaManager getInstance(){
        if(referenciaManager == null)
        	referenciaManager = new ReferenciaManager();
        return referenciaManager;
    }
    
	/**
	 * Remove todas as referencias do Banco de Dados
	 */
    public void removeReferencias() {
		//Alteracoes
		for( Alteracao alt : AlteracaoManager.getInstance().getAlteracoes() ) {
			alt.setNormaOrigem(null);
			alt.setNormaDestino(null);
			alt.setElementoNormaOrigem(null);
			alt.setElementoNormaDestino(null);	
			dbManager.save(alt);
		}
		dbManager.removeAll( new Alteracao() );
		//CitacoesDocJud
		for( CitacaoDocJud citDocJud : CitacaoDocJudManager.getInstance().getCitacoesDocJud() ){
			citDocJud.setDocumentoJuridicoOrigem(null);
			citDocJud.setDocumentoJuridicoDestino(null);
			citDocJud.setDoutrinaDestino(null);
			citDocJud.setElementoNormaDestino(null);
			citDocJud.setNormaDestino(null);
			dbManager.save(citDocJud);
		}
		dbManager.removeAll( new CitacaoDocJud() );
		//CitacoesTextLeg
		for( CitacaoTextLeg citTextLeg : CitacaoTextLegManager.getInstance().getCitacoesTextLeg() ){
			citTextLeg.setDocumentoJuridicoDestino(null);
			citTextLeg.setElementoNormaDestino(null);
			citTextLeg.setNormaDestino(null);
			citTextLeg.setElementoNormaOrigem(null);
			citTextLeg.setNormaOrigem(null);
			dbManager.save(citTextLeg);
		}
		dbManager.removeAll( new CitacaoTextLeg() );
	}
}
