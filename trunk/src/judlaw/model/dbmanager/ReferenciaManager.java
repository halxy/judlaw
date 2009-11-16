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
	    
    /* ------------------------------------------------------------------ */
    /* -------------------- OPERACOES ALTERACAO ------------------------- */
    /* ------------------------------------------------------------------ */
    /**
     * 
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
	 * removeAlteracoes - por se tratar de um BD temporal, usar apenas em ocasi�es especiais
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
	public List<Alteracao> selectAlteracaoPorAtributo(String atributo, String valor) {
		return dbManager.selectObjectsByField(new Alteracao(), atributo, valor);
	}
	
	/**
	 * Remove todos os tipos de referencias
	 */
	public void removeReferencias() {
//		for (Norma n: LawManager.getInstance().getNormas()){
//			n.setAlteracoesFeitas(null);
//			n.setAlteracoesRecebidas(null);
//			n.setCitacoesFeitas(null);
//			n.setCitacoesRecebidasDocJud(null);
//			n.setCitacoesRecebidasTextLeg(null);
//		}
		dbManager.removeAll( new Alteracao() );
		dbManager.removeAll( new CitacaoDocJud() );
		dbManager.removeAll( new CitacaoTextLeg() );
	}
}