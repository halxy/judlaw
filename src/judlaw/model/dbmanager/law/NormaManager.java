/* 
 * Universidade Federal de Campina Grande
 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas	
 */
package judlaw.model.dbmanager.law;

import java.util.List;

import judlaw.model.bean.law.ElementoNorma;
import judlaw.model.bean.law.Norma;
import judlaw.model.dbmanager.DBManager;


/**
 * Classe LawManager - responsável por gerenciar as operacoes no banco de dados das normas e elementosNorma 
 * @author Halley Freitas
 *
 */
public class NormaManager {
	
	private static NormaManager lawManager = null;
	private DBManager dbManager = DBManager.getInstance();	
	
   /**
    * Retorna uma instancia da classe LawManager
    * @return
    */
    public static NormaManager getInstance(){
        if(lawManager == null)
        	lawManager = new NormaManager();
        return lawManager;
    }
    
    /**
     * 
     * @param norma
     */
    public void salvaNorma(Norma norma) {
		//Setando ElementosNorma
    	List<ElementoNorma> elementosNorma = norma.getElementosNorma(); // 1o nivel - elementosNorma filhos da Norma
    	for(ElementoNorma eleN : elementosNorma) {
    		eleN.setNormaPai(norma);
    	}
		//Persistindo
    	dbManager.save(norma);
    	
    	//Persistindo os nós filhos
    	for(ElementoNorma eleN : elementosNorma) {
    		ElementoNormaManager.getInstance().salvaElementoRecursivo(eleN);
    	}
	}
    
    /**
     * Remove todas as normas
     */
    public void removeNormas() {
		dbManager.removeAll( new Norma() );
	}
    
    /**
     * Retorna todos as normas
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<Norma> getNormas(){
		return dbManager.selectAll( new Norma() );
	}
    
    @SuppressWarnings("unchecked")
	public List selectNormaPorAtributo(String atributo, String valor) {
		return dbManager.selectObjectsByField(new Norma(), atributo, valor);
	}
	
    /**
     * Remove uma norma do banco de dados
     * @param norma
     */
    public void removeNorma(Norma norma) {
    	dbManager.remove( norma ); 
    }
}
