package judlaw.model.manager;

import java.util.List;

import judlaw.model.bean.lei.ElementoNorma;
import judlaw.model.bean.lei.Norma;


/**
 * Classe responsável por gerenciar o módulo das leis
 * @author Halley Freitas
 *
 */
public class LawManager {
	
	private static LawManager lawManager = null;
	private DBManager dbManager = DBManager.getInstance();
//	private TimeManager timeManager = TimeManager.getInstance();
	
	
   /**
    * Retorna uma instancia da classe LawManager
    * @return
    */
    public static LawManager getInstance(){
        if(lawManager == null)
        	lawManager = new LawManager();
        return lawManager;
    }
    
	/* ------------------------------------------------------------------ */
    /* -------------------- OPERACOES NORMA ----------------------------- */
    /* ------------------------------------------------------------------ */
    /**
     * 
     */
    public void salvaNorma(Norma norma) {
		//Setando ElementosNorma
    	List<ElementoNorma> elementosNorma = norma.getElementosNorma();
    	for(ElementoNorma eleN : elementosNorma) {
    		eleN.setNormaPai(norma);
    	}
		dbManager.save(norma);
	}
	
    /* ------------------------------------------------------------------ */
    /* -------------------- OPERACOES ELEMENTO NORMA -------------------- */
    /* ------------------------------------------------------------------ */
    /**
     * Persiste um elementoNorma na base de dados
     * @param elementoNorma
     */
	public void salvaElementoNorma(ElementoNorma elementoNorma) {
		//Setando ElementosNorma
    	List<ElementoNorma> elementosNorma = elementoNorma.getElementosNorma();
    	for(ElementoNorma eleN : elementosNorma) {
    		eleN.setElementoNormaPai(elementoNorma);
    	}
		dbManager.save(elementoNorma);
	}
    
	/**
     * Retorna todos os elementos norma
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<ElementoNorma> getElementosNorma(){
		return dbManager.selectAll(new ElementoNorma());
	}
    
    /**
     * Remove todos os elementosNorma
     */
	public void removeElementosNorma() {
		dbManager.removeAll(new ElementoNorma());
	}
	
	/**
	 * Recupera os objetos pelo atributo
	 * @param atributo
	 * @param valor
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List recuperaElementoPorAtributo(String atributo, String valor) {
		return dbManager.selectObjectsByField(new ElementoNorma(), atributo, valor);
	}
}
