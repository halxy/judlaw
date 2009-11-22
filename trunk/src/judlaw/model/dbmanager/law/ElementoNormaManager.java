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
    public void salvaFilhosElementoRecursivo(ElementoNorma elementoNorma) {
    	List<ElementoNorma> filhos = elementoNorma.getElementosNorma();
		for(ElementoNorma filho : filhos) {
//			filho.setElementoNormaPai(elementoNorma);
			filho.getElementosNormaPai().add( elementoNorma );
			dbManager.save(filho);
			salvaFilhosElementoRecursivo(filho);
		}
    }
    
    /**
     * Remove os filhos do elementoNormaRecursivamente
     * @param elementoNorma
     */
    public void removePaisRecursivo( ElementoNorma elementoNorma ) {
		List<ElementoNorma> filhos = elementoNorma.getElementosNorma();
		for(ElementoNorma filho : filhos) {
			removePais(filho);
			filho.getElementosNorma().clear();
			dbManager.save(filho);
			salvaFilhosElementoRecursivo(filho);
		}
    }
    
    public void removePais(ElementoNorma elementoNorma) {
		if( elementoNorma.getNormasPai().size() > 0) {
			elementoNorma.getNormasPai().clear();
		} else {
			elementoNorma.getElementosNormaPai().clear();
		}
	}
    
    /**
     * Persiste um elementoNorma na base de dados
     * @param elementoNorma
     */
	public void salvaElementoNorma(ElementoNorma elementoNorma) {
		salvaFilhosElementoRecursivo(elementoNorma);
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
		dbManager.removeAll( new ElementoNorma() );
	}
	
	/**
	 * Remove um ElementoNorma do banco de dados
	 * @param elementoNorma
	 */
	public void removeElementoNorma(ElementoNorma elementoNorma) {
//		if(elementoNorma.getNormaPai() != null) { //Caso o pai do elemento seja uma Norma
//			elementoNorma.getNormaPai().getElementosNorma().remove( elementoNorma );
//			dbManager.save( elementoNorma.getNormaPai() );
//		} else { //Caso o pai do elemento seja um ElementoNorma
//			elementoNorma.getElementoNormaPai().getElementosNorma().remove( elementoNorma );
//			dbManager.save( elementoNorma.getElementoNormaPai() );
//		}
		if(elementoNorma.getNormasPai().size() > 0) { //Caso o pai do elemento seja uma Norma
			for( Norma normaPai : elementoNorma.getNormasPai() ) {
				normaPai.getElementosNorma().remove( elementoNorma );
				dbManager.save( normaPai );
			}
		} else { //Caso o pai do elemento seja um ElementoNorma
			for( ElementoNorma elementoNormaPai : elementoNorma.getElementosNormaPai() ) {
				elementoNormaPai.getElementosNorma().remove( elementoNorma );
				dbManager.save( elementoNormaPai );
			}
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
	 * @param normaPai
	 */
	public void adicionaElementoNorma(ElementoNorma elementoNorma, Norma normaPai) {
//		elementoNorma.setNormaPai(normaPai);
		elementoNorma.getNormasPai().add( normaPai );
		normaPai.getElementosNorma().add( elementoNorma );
		dbManager.save(normaPai);
		salvaFilhosElementoRecursivo(elementoNorma);
	}
	
	/**
	 * 
	 * @param elementoNorma
	 * @param elementoNormaPai
	 */
	public void adicionaElementoNorma(ElementoNorma elementoNorma, ElementoNorma elementoNormaPai) {
//		elementoNorma.setElementoNormaPai( elementoNormaPai );
		elementoNorma.getElementosNormaPai().add( elementoNormaPai );
		elementoNormaPai.getElementosNorma().add( elementoNorma );
		dbManager.save( elementoNormaPai );
		salvaFilhosElementoRecursivo(elementoNorma);
	}

	/**
     * Replica os atributos de um elementoNorma em outro
     * Obs: as referencias nao sao replicadas, pois entende-se que so percentem a um momento temporal
     * do banco de dados, entao um elementonorma modificado naquele momento temporal nao recebe as referencias
     * da versao antiga
	 * @param elementoNormaModificado
	 * @param novoElementoNorma
	 */
	public void setParametrosElementoNorma(ElementoNorma elementoNormaModificado, ElementoNorma novoElementoNorma){
		novoElementoNorma.setTexto( elementoNormaModificado.getTexto() );
		novoElementoNorma.setIdentificadorUnico( elementoNormaModificado.getIdentificadorUnico() );
		novoElementoNorma.setTipo( elementoNormaModificado.getTipo() );
		novoElementoNorma.setDataPublicacao( elementoNormaModificado.getDataPublicacao() );
		novoElementoNorma.setVigencia( elementoNormaModificado.getVigencia() );
		novoElementoNorma.setElementosNorma( elementoNormaModificado.getElementosNorma() );
		if( elementoNormaModificado.getNormasPai().size() > 0 ){
			novoElementoNorma.setNormasPai( elementoNormaModificado.getNormasPai() );
		} else {
			novoElementoNorma.setElementosNormaPai( elementoNormaModificado.getElementosNormaPai() );
		}
	}
}
