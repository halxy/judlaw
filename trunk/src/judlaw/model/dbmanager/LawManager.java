package judlaw.model.dbmanager;

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
    	List<ElementoNorma> elementosNorma = norma.getElementosNorma(); // 1o nivel - elementosNorma filhos da Norma
    	for(ElementoNorma eleN : elementosNorma) {
    		eleN.setNormaPai(norma);
    	}
		//Persistindo
    	dbManager.save(norma);
    	
    	//Persistindo os nós filhos
    	for(ElementoNorma eleN : elementosNorma) {
    		salvaElementoRecursivo(eleN);
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
    
    /* ------------------------------------------------------------------ */
    /* -------------------- OPERACOES ELEMENTO NORMA -------------------- */
    /* ------------------------------------------------------------------ */
    
    /*
     * Salva um elementoNorma e todos seus nos filhos recursivamente
     */
    private void salvaElementoRecursivo(ElementoNorma elementoNorma) {
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
