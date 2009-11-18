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
 * Classe ElementoNormaManager - responsável por gerenciar as operacoes no banco de dados dos elementosNorma 
 * @author Halley Freitas
 *
 */
public class ElementoNormaManager {

	private static ElementoNormaManager elementoNormaManager = null;
	private DBManager dbManager = DBManager.getInstance();	
	
   /**
    * Retorna uma instancia da classe ElementoNormaManager
    * @return
    */
    public static ElementoNormaManager getInstance(){
        if(elementoNormaManager == null)
        	elementoNormaManager = new ElementoNormaManager();
        return elementoNormaManager;
    }
    
    /**
     * Salva um elementoNorma e todos seus nos filhos recursivamente
     * @param elementoNorma
     */
    public void salvaElementoRecursivo(ElementoNorma elementoNorma) {
    	List<ElementoNorma> filhos = elementoNorma.getElementosNorma();
		for(ElementoNorma filho : filhos) {
			filho.setElementoNormaPai(elementoNorma);			
			dbManager.save(filho);
			salvaElementoRecursivo(filho);
		}
    }
    
    /**
     * Persiste um elementoNorma na base de dados
     * @param elementoNorma
     */
	public void salvaElementoNorma(ElementoNorma elementoNorma) {
		salvaElementoRecursivo(elementoNorma);
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
	 * Remove um ElementoNorma do banco de dados
	 * @param elementoNorma
	 */
	public void removeElementoNorma(ElementoNorma elementoNorma) {
		if(elementoNorma.getNormaPai() != null) { //Caso o pai do elemento seja uma Norma
			elementoNorma.getNormaPai().getElementosNorma().remove( elementoNorma );
			dbManager.save( elementoNorma.getNormaPai() );
		} else { //Caso o pai do elemento seja um ElementoNorma
			elementoNorma.getElementoNormaPai().getElementosNorma().remove( elementoNorma );
			dbManager.save( elementoNorma.getElementoNormaPai() );
		}
		dbManager.remove( elementoNorma );
	}
	
	/**
	 * Recupera os objetos pelo atributo
	 * @param atributo
	 * @param valor
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List selectElementoPorAtributo(String atributo, String valor) {
		return dbManager.selectObjectsByField(new ElementoNorma(), atributo, valor);
	}
	
	/**
	 * Adiciona um ElementoNorma a uma norma
	 * @param elementoNorma
	 * @param norma
	 */
	public void adicionaElementoNorma(ElementoNorma elementoNorma, Norma norma) {
		elementoNorma.setNormaPai(norma);
		norma.getElementosNorma().add( elementoNorma );
		dbManager.save(norma);
		salvaElementoRecursivo(elementoNorma);
	}
}
