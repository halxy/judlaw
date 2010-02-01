/* 
 * Universidade Federal de Campina Grande
 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas	
 */
package judlaw.model.persistence.dbmanager.doutrina;

import java.util.List;

import judlaw.model.bean.doutrina.Doutrina;
import judlaw.model.persistence.dbmanager.DBManager;

/**
 * Classe DoutrinaManager. Define as operacoes no BD das doutrinas 
 * @author halley
 *
 */
public class DoutrinaManager {

	private static DoutrinaManager doutrinaManager = null;
	private static DBManager dbManager = DBManager.getInstance();
	
	
	public void salvaDoutrina(Doutrina doutrina) {
		dbManager.save( doutrina );
	}
   
		/**
    * Retorna uma instancia da classe DoutrinaManager
    * @return
    */
    public static DoutrinaManager getInstance(){
        if(doutrinaManager == null)
        	doutrinaManager = new DoutrinaManager();
        return doutrinaManager;
    }
    
    @SuppressWarnings("unchecked")
	public List<Doutrina> getDoutrinas() {
		return dbManager.selectAll( new Doutrina() );
	}
	
	/**
	 * removeDoutrinas
	 */
	public void removeDoutrinas() {
		dbManager.removeAll( new Doutrina() );
	}
	
}
