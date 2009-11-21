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
 * Classe NormaManager - responsável por gerenciar as operacoes no banco de dados das normas 
 * @author Halley Freitas
 *
 */
public class NormaManager {
	
	private static NormaManager lawManager = null;
	private DBManager dbManager = DBManager.getInstance();	
	
   /**
    * Retorna uma instancia da classe NormaManager
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
//    		eleN.setNormaPai(norma);
    		eleN.getNormasPai().add( norma );
    	}
		//Persistindo
    	dbManager.save(norma);
    	
    	//Persistindo os nós filhos
    	for(ElementoNorma eleN : elementosNorma) {
    		ElementoNormaManager.getInstance().salvaFilhosElementoRecursivo(eleN);
    	}
	}
    
    /**
     * Salva a nova versao de uma norma
     * @param norma
     */
    public void salvaNormaAlterada(Norma norma) {
		//Setando ElementosNorma
    	List<ElementoNorma> elementosNorma = norma.getElementosNorma(); // 1o nivel - elementosNorma filhos da Norma
    	for(ElementoNorma eleN : elementosNorma) {
//    		eleN.setNormaPai(norma);
    		eleN.getNormasPai().add( norma );
    		dbManager.save( eleN );
    	}
		//Persistindo
    	dbManager.save(norma);
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
    
    /**
     * Replica os atributos de uma norma em outra.
     * Obs: as referencias nao sao replicadas, pois entende-se que so percentem a um momento temporal
     * do banco de dados, entao uma norma modificada naquele momento temporal nao recebe as referencias
     * da versao antiga
     * @param norma
     * @param novaNorma
     */
    public void setParametrosNorma(Norma norma, Norma novaNorma) {
    	novaNorma.setEpigrafe( norma.getEpigrafe() );
    	novaNorma.setEmenta( norma.getEmenta() );
    	novaNorma.setAutoria( norma.getAutoria() );
    	novaNorma.setLocal( norma.getLocal() );
    	novaNorma.setIdentificadorUnico( norma.getIdentificadorUnico() );
    	novaNorma.setTipo( norma.getTipo() );
    	novaNorma.setDataPublicacao( norma.getDataPublicacao() );
    	novaNorma.setVigencia( norma.getVigencia() );
    	for(ElementoNorma eleN : norma.getElementosNorma() ) {
    		novaNorma.getElementosNorma().add( eleN );
    	}
    }
}
