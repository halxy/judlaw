/* 
 * Universidade Federal de Campina Grande


 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas
 * 		
 */
package judlaw.model.dbmanager;

import java.util.List;

import judlaw.model.bean.lei.Norma;
import judlaw.model.bean.ref.Alteracao;
import judlaw.model.bean.ref.CitacaoDocJud;
import judlaw.model.bean.ref.CitacaoTextLeg;


/**
 * Classe ReferenciaManager - Gerencia a manipulacao dos objetos do tipo Referencia: 
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
		for( Alteracao alt : getAlteracoes() ) {
			alt.setNormaOrigem(null);
			alt.setNormaDestino(null);
			alt.setElementoNormaOrigem(null);
			alt.setElementoNormaDestino(null);	
			dbManager.save(alt);
		}
		dbManager.removeAll( new Alteracao() );
		//CitacoesDocJud
		for( CitacaoDocJud citDocJud : getCitacoesDocJud() ){
			citDocJud.setDocumentoJuridicoOrigem(null);
			citDocJud.setDocumentoJuridicoDestino(null);
			citDocJud.setElementoNormaDestino(null);
			citDocJud.setNormaDestino(null);
			dbManager.save(citDocJud);
		}
		dbManager.removeAll( new CitacaoDocJud() );
		//CitacoesTextLeg
		for( CitacaoTextLeg citTextLeg : getCitacoesTextLeg() ){
			citTextLeg.setDocumentoJuridicoDestino(null);
			citTextLeg.setElementoNormaDestino(null);
			citTextLeg.setNormaDestino(null);
			citTextLeg.setElementoNormaOrigem(null);
			citTextLeg.setNormaOrigem(null);
			dbManager.save(citTextLeg);
		}
		dbManager.removeAll( new CitacaoTextLeg() );
	}
    
    /* ------------------------------------------------------------------ */
    /* -------------------- OPERACOES ALTERACAO ------------------------- */
    /* ------------------------------------------------------------------ */
    /**
     * 
     */
    
    /**
	 * Alteracao Norma -> Norma
	 * @param normaOrigem
	 * @param normaDestino
	 * @param data
	 * @param tipo
	 * @param caracteristica
	 */
    public void criaAlteracao(Norma normaOrigem, Norma normaDestino, String data, String tipo, String caracteristica){
    	Alteracao alt = new Alteracao(normaOrigem, normaDestino, data, tipo, caracteristica);
    	dbManager.save(alt);
    	normaOrigem.getAlteracoesFeitas().add(alt);
    	normaDestino.getAlteracoesRecebidas().add(alt);
    	dbManager.save(normaOrigem);
    	dbManager.save(normaDestino);
	}
	    
	@SuppressWarnings("unchecked")
	public List<Alteracao> getAlteracoes() {
		return dbManager.selectAll( new Alteracao() );
	}
	
	/**
	 * Recupera as Alteracoes que atraves de valores dos seus atributos
	 * @param atributo
	 * @param valor
	 * @return Lista com as Alteracoes recuperadas
	 */
	@SuppressWarnings("unchecked")
	public List<Alteracao> selectAlteracaoPorAtributo(String atributo, String valor) {
		return dbManager.selectObjectsByField(new Alteracao(), atributo, valor);
	}
	
	/* ------------------------------------------------------------------ */
    /* -------------------- OPERACOES CITACAODOCJUD --------------------- */
    /* ------------------------------------------------------------------ */
	
	@SuppressWarnings("unchecked")
	public List<CitacaoDocJud> getCitacoesDocJud() {
		return dbManager.selectAll( new CitacaoDocJud() );
	}
	
	/* ------------------------------------------------------------------ */
    /* -------------------- OPERACOES CITACAOTEXTLEG -------------------- */
    /* ------------------------------------------------------------------ */
	
	@SuppressWarnings("unchecked")
	public List<CitacaoTextLeg> getCitacoesTextLeg() {
		return dbManager.selectAll( new CitacaoTextLeg() );
	}
	
	public void criaTextLeg(Norma normaOrigem, Norma normaDestino, String data){
    	CitacaoTextLeg citacaoTextLeg = new CitacaoTextLeg(normaOrigem, normaDestino, data);
    	dbManager.save(citacaoTextLeg);
    	normaOrigem.getCitacoesFeitas().add( citacaoTextLeg );
    	normaDestino.getCitacoesRecebidasTextLeg().add( citacaoTextLeg );
    	dbManager.save(normaOrigem);
    	dbManager.save(normaDestino);
	}
}
