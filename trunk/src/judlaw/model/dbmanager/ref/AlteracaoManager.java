/* 
 * Universidade Federal de Campina Grande
 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas	
 */
package judlaw.model.dbmanager.ref;

import java.util.List;

import judlaw.model.bean.law.ElementoNorma;
import judlaw.model.bean.law.Norma;
import judlaw.model.bean.ref.Alteracao;
import judlaw.model.dbmanager.DBManager;

/**
 * Classe AlteracaoManager - respons�vel por gerenciar as operacoes no banco de dados das alteracoes 
 * @author Halley Freitas
 *
 */
public class AlteracaoManager {

	private static AlteracaoManager alteracaoManager = null;
	private DBManager dbManager = DBManager.getInstance();	
	
   /**
    * Retorna uma instancia da classe AlteracaoManager
    * @return
    */
    public static AlteracaoManager getInstance(){
        if(alteracaoManager == null)
        	alteracaoManager = new AlteracaoManager();
        return alteracaoManager;
    }
    
    /**
	 * Alteracao Norma -> Norma
	 * @param normaOrigem
	 * @param normaDestino
	 * @param data
	 * @param tipo
	 * @param caracteristica
	 */
    public void criaAlteracao(Norma normaOrigem, Norma normaDestino, String data, String tipo, String caracteristica){
    	Alteracao alt = new Alteracao(normaOrigem, normaDestino, data, tipo, caracteristica);
    	dbManager.save(alt);
    	normaOrigem.getAlteracoesFeitas().add(alt);
    	normaDestino.getAlteracoesRecebidas().add(alt);
    	dbManager.save(normaOrigem);
    	dbManager.save(normaDestino);
	}
    
    /**
     * Alteracao Norma -> ElementoNorma
     * @param normaOrigem
     * @param elementoNormaDestino
     * @param data
     * @param tipo
     * @param caracteristica
     */
    public void criaAlteracao(Norma normaOrigem, ElementoNorma elementoNormaDestino, String data, String tipo, String caracteristica){
    	Alteracao alt = new Alteracao(normaOrigem, elementoNormaDestino, data, tipo, caracteristica);
    	dbManager.save(alt);
    	normaOrigem.getAlteracoesFeitas().add(alt);
    	elementoNormaDestino.getAlteracoesRecebidas().add(alt);
    	dbManager.save(normaOrigem);
    	dbManager.save(elementoNormaDestino);
	}
    
    /**
     * Alteracao ElementoNorma -> Norma
     * @param elementoNormaOrigem
     * @param normaDestino
     * @param data
     * @param tipo
     * @param caracteristica
     */
    public void criaAlteracao(ElementoNorma elementoNormaOrigem, Norma normaDestino, String data, String tipo, String caracteristica){
    	Alteracao alt = new Alteracao(elementoNormaOrigem, normaDestino, data, tipo, caracteristica);
    	dbManager.save(alt);
    	elementoNormaOrigem.getAlteracoesFeitas().add(alt);
    	normaDestino.getAlteracoesRecebidas().add(alt);
    	dbManager.save(elementoNormaOrigem);
    	dbManager.save(normaDestino);
	}
    
    /**
     * Alteracao ElementoNorma -> ElementoNorma
     * @param elementoNormaOrigem
     * @param elementoNormaDestino
     * @param data
     * @param tipo
     * @param caracteristica
     */
    public void criaAlteracao(ElementoNorma elementoNormaOrigem, ElementoNorma elementoNormaDestino, String data, String tipo, String caracteristica){
    	Alteracao alt = new Alteracao(elementoNormaOrigem, elementoNormaDestino, data, tipo, caracteristica);
    	dbManager.save(alt);
    	elementoNormaOrigem.getAlteracoesFeitas().add(alt);
    	elementoNormaDestino.getAlteracoesRecebidas().add(alt);
    	dbManager.save(elementoNormaOrigem);
    	dbManager.save(elementoNormaDestino);
	}
	
    /**
     * Remove uma alteracao do banco de dados
     * @param alteracao
     */
    public void removeAlteracao(Alteracao alteracao) {
    	//Origem
    	if( alteracao.getNormaOrigem() != null ) {
    		alteracao.getNormaOrigem().getAlteracoesFeitas().remove( alteracao );
    		dbManager.save( alteracao.getNormaOrigem() );
    	} else {
    		alteracao.getElementoNormaOrigem().getAlteracoesFeitas().remove( alteracao );
    		dbManager.save( alteracao.getElementoNormaOrigem() );
    	}
    	//Destino
    	if( alteracao.getNormaDestino() != null ) {
    		alteracao.getNormaDestino().getAlteracoesRecebidas().remove( alteracao );
    		dbManager.save( alteracao.getNormaDestino() );
    	} else {
    		alteracao.getElementoNormaDestino().getAlteracoesRecebidas().remove( alteracao );
    		dbManager.save( alteracao.getElementoNormaDestino() );
    	}
    	dbManager.remove( alteracao );
    }
    	
    /**
     * Retorna todas as alteracoes do banco de dados
     * @return
     */
	@SuppressWarnings("unchecked")
	public List<Alteracao> getAlteracoes() {
		return dbManager.selectAll( new Alteracao() );
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
	 * Altera uma Alteracao no banco de dados
	 * @param newAlteracao
	 * @param oldAlteracao
	 */
	public void alteraAlteracao(Alteracao newAlteracao, Alteracao oldAlteracao){
		oldAlteracao.setData( newAlteracao.getData() );
		oldAlteracao.setTipo( newAlteracao.getTipo() );
		oldAlteracao.setCaracteristica( newAlteracao.getCaracteristica() );
		dbManager.save( oldAlteracao );
	}
}
