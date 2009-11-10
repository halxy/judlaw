/* 
 * Universidade Federal de Campina Grande


 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas
 * 		
 */
package judlaw.model.manager;

import java.util.List;

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
	    
    /* ------------------------------------------------------------------ */
    /* -------------------- OPERACOES ALTERACAO ------------------------- */
    /* ------------------------------------------------------------------ */
    /**
     * 
     */
    public void salvaAlteracao(Alteracao alteracao) {
		dbManager.save(alteracao);
	}
	    
	@SuppressWarnings("unchecked")
	public List<Alteracao> getAlteracoes() {
		return dbManager.selectAll( new Alteracao() );
	}
	
	/**
	 * removeAlteracoes - por se tratar de um BD temporal, usar apenas em ocasiões especiais
	 */
	public void removeAlteracoes() {
		dbManager.removeAll( new Alteracao() );
	}
	
	/**
	 * Recupera as Alteracoes que atraves de valores dos seus atributos
	 * @param atributo
	 * @param valor
	 * @return Lista com as Alteracoes recuperadas
	 */
	@SuppressWarnings("unchecked")
	public List<Alteracao> recuperaAlteracaoPorAtributo(String atributo, String valor) {
		return dbManager.selectObjectsByField(new Alteracao(), atributo, valor);
	}
	
	/* ------------------------------------------------------------------ */
    /* -------------------- OPERACOES CITACAODOCJUD --------------------- */
    /* ------------------------------------------------------------------ */
    
    public void salvaCitacaoDocJud(CitacaoDocJud citacaoDocJud) {
		dbManager.save(citacaoDocJud);
	}
	    
	@SuppressWarnings("unchecked")
	public List<CitacaoDocJud> getCitacoesDocJud() {
		return dbManager.selectAll( new CitacaoDocJud() );
	}
	
	/**
	 * removeCitacoesDocJud - por se tratar de um BD temporal, usar apenas em ocasiões especiais
	 */
	public void removeCitacoesDocJud() {
		dbManager.removeAll( new CitacaoDocJud() );
	}
	
	/**
	 * Recupera as CitacoesDocJud que atraves de valores dos seus atributos
	 * @param atributo
	 * @param valor
	 * @return Lista com as CitacoesDocJud recuperadas
	 */
	@SuppressWarnings("unchecked")
	public List<CitacaoDocJud> recuperaCitacaoDocJudPorAtributo(String atributo, String valor) {
		return dbManager.selectObjectsByField(new CitacaoDocJud(), atributo, valor);
	}
	
	/* ------------------------------------------------------------------ */
    /* -------------------- OPERACOES CITACAODOCLEG --------------------- */
    /* ------------------------------------------------------------------ */
    
	public void salvaCitacaoDocLeg(CitacaoTextLeg citacaoDocLeg) {
		dbManager.save(citacaoDocLeg);
	}
	    
	@SuppressWarnings("unchecked")
	public List<CitacaoTextLeg> getCitacoesDocLeg() {
		return dbManager.selectAll( new CitacaoTextLeg() );
	}
	
	/**
	 * removeCitacoesDocLeg - por se tratar de um BD temporal, usar apenas em ocasiões especiais
	 */
	public void removeCitacoesDocLeg() {
		dbManager.removeAll( new CitacaoTextLeg() );
	}
	
	/**
	 * Recupera as CitacoesDocLeg que atraves de valores dos seus atributos
	 * @param atributo
	 * @param valor
	 * @return Lista com as CitacoesDocLeg recuperadas
	 */
	@SuppressWarnings("unchecked")
	public List<CitacaoTextLeg> recuperaCitacaoDocLegPorAtributo(String atributo, String valor) {
		return dbManager.selectObjectsByField(new CitacaoTextLeg(), atributo, valor);
	}
}
