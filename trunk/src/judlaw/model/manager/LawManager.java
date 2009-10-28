package judlaw.model.manager;

import java.util.List;

import judlaw.model.lei.ElementoNorma;


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
    
    /**
     * Persiste um elementoNorma na base de dados
     * @param elementoNorma
     */
	public void saveElementoNorma(ElementoNorma elementoNorma) {
		dbManager.save(elementoNorma);
	}
    
	/**
     * Retorna todos os elementos norma
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<ElementoNorma> getTodosElementosNorma(){
		return dbManager.selectAll(new ElementoNorma());
	}
    
    /**
     * Remove todos os elementosNorma
     */
	public void removeTodosElementosNorma() {
		dbManager.removeAll(new ElementoNorma());
	}
}
