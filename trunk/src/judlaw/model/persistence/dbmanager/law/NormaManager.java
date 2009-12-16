/* 
 * Universidade Federal de Campina Grande
 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas	
 */
package judlaw.model.persistence.dbmanager.law;

import java.util.List;

import judlaw.model.bean.law.ElementoNorma;
import judlaw.model.bean.law.Norma;
import judlaw.model.persistence.dbmanager.DBManager;


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
    		eleN.getNormasPai().add( norma );
    //		dbManager.save( eleN );
    	}
		//Persistindo
    	dbManager.save(norma);
	}
       
    /**
     * Remove todas as normas e elementosNorma.
     * Obs: eh necessario remover primeiro os elementosFilhos para depois remover o objeto em si.
     */
    public void removeLaw() {
    	for ( Norma norma : getNormas() ){
    		norma.getElementosNorma().clear(); //remove os filhos primeiro
    		dbManager.save( norma );
    	}
		dbManager.removeAll( new Norma() );
		
		for ( ElementoNorma eleNorma: ElementoNormaManager.getInstance().getElementosNorma()){
			if(eleNorma.getElementosNorma().size()>0) {
				eleNorma.getElementosNorma().clear(); //remove os filhos primero
				dbManager.save( eleNorma );
			}
		}
		dbManager.removeAll( new ElementoNorma() );
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
    public void setParametrosNorma(Norma norma, Norma novaNorma, String dataModificacao) {
    	novaNorma.setUrl( norma.getUrl() );
    	novaNorma.setEmenta( norma.getEmenta() );
    	novaNorma.setAutoria( norma.getAutoria() );
    	novaNorma.setLocal( norma.getLocal() );
    	novaNorma.setIdentificadorUnico( norma.getIdentificadorUnico() );
    	novaNorma.setTipo( norma.getTipo() );
    	novaNorma.setNumero( norma.getNumero() );
    	//Temporalidade
    	novaNorma.setDataPublicacao( dataModificacao );
    	novaNorma.setVigencia( dataModificacao + "-99/99/9999" );
    	//Filhos
    	for(ElementoNorma eleN : norma.getElementosNorma() ) {
    		novaNorma.getElementosNorma().add( eleN );
    	}
    }
}
