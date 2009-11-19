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
import judlaw.model.logic.time.TimeLogic;
import judlaw.model.util.Constantes;

/**
 * Classe AlteracaoManager - responsável por gerenciar as operacoes no banco de dados das alteracoes 
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
    
    /*
     * ALTERACOES SIMPLES: sao aquelas que não modificam o banco de dados, apenas incluem uma nova entrada
     * na tabela de alteracoes com as informacoes do relacionamento
     */
    /**
	 * Alteracao Norma -> Norma
	 * @param normaOrigem
	 * @param normaDestino
	 * @param data
	 * @param tipo
	 * @param caracteristica
	 */
    public void criaAlteracaoSimples(Norma normaOrigem, Norma normaDestino, String data, String tipo, String caracteristica){
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
    public void criaAlteracaoSimples(Norma normaOrigem, ElementoNorma elementoNormaDestino, String data, String tipo, String caracteristica){
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
    public void criaAlteracaoSimples(ElementoNorma elementoNormaOrigem, Norma normaDestino, String data, String tipo, String caracteristica){
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
    public void criaAlteracaoSimples(ElementoNorma elementoNormaOrigem, ElementoNorma elementoNormaDestino, String data, String tipo, String caracteristica){
    	Alteracao alt = new Alteracao(elementoNormaOrigem, elementoNormaDestino, data, tipo, caracteristica);
    	dbManager.save(alt);
    	elementoNormaOrigem.getAlteracoesFeitas().add(alt);
    	elementoNormaDestino.getAlteracoesRecebidas().add(alt);
    	dbManager.save(elementoNormaOrigem);
    	dbManager.save(elementoNormaDestino);
	}
	
    /*
     * ALTERACOES COMPLEXAS: sao aquelas que modificam o banco de dados, atualizando o banco de dados temporal,
     * alem de criar uma entrada na tabela de alteracoes
     */  
    //REVOGACAO
    /*
     * Revoga todos os elementos de uma norma e seus filhos
     */
	private void revogaElementosNormaRecursivo(ElementoNorma elementoNorma, String vigenciaExpirada) {
		elementoNorma.setVigencia( vigenciaExpirada );
		List<ElementoNorma> filhos = elementoNorma.getElementosNorma();
		for(ElementoNorma filho : filhos) {
			filho.setVigencia( vigenciaExpirada );
			dbManager.save(filho);
			revogaElementosNormaRecursivo(filho, vigenciaExpirada);
		}
	}
	
	/**
	 * Cria uma alteracao e modifica o destino juntamente com seus elementosNorma no que diz respeito
	 * a vigencia, jah que esta foi revogada.
	 * @param normaOrigem
	 * @param normaDestino
	 * @param dataRevogacao
	 * @param caracteristica
	 */
    public void criaAlteracaoRevogacao(Norma normaOrigem, Norma normaDestino, String dataRevogacao, String caracteristica){
    	Alteracao alt = new Alteracao(normaOrigem, normaDestino, dataRevogacao, Constantes.REVOGACAO, caracteristica);
    	dbManager.save(alt);
    	normaOrigem.getAlteracoesFeitas().add(alt);
    	dbManager.save(normaOrigem);
    	//Setando a nova vigencia da normaDestino
    	normaDestino.getAlteracoesRecebidas().add(alt);
    	String vigenciaExpirada = TimeLogic.getInstance().novaDataFimVigencia(normaDestino.getVigencia(), dataRevogacao);
    	normaDestino.setVigencia( vigenciaExpirada );
    	//Setando a vigencia expirada para os elementosNorma filhos
    	for(ElementoNorma eleNorma : normaDestino.getElementosNorma()) {
    		revogaElementosNormaRecursivo(eleNorma, vigenciaExpirada);
    	}
    	dbManager.save(normaDestino);
    }
    
    public void criaAlteracaoRevogacao(Norma normaOrigem, ElementoNorma elementoNormaDestino, String dataRevogacao, String caracteristica){
    	Alteracao alt = new Alteracao(normaOrigem, elementoNormaDestino, dataRevogacao, Constantes.REVOGACAO, caracteristica);
    	dbManager.save(alt);
    	normaOrigem.getAlteracoesFeitas().add(alt);
    	dbManager.save(normaOrigem);
    	//Setando a nova vigencia do elementoNormaDestino
    	elementoNormaDestino.getAlteracoesRecebidas().add(alt);
    	String vigenciaExpirada = TimeLogic.getInstance().novaDataFimVigencia(elementoNormaDestino.getVigencia(), dataRevogacao);
    	elementoNormaDestino.setVigencia( vigenciaExpirada );
    	//Setando a vigencia expirada para os elementosNorma filhos
    	for(ElementoNorma eleNorma : elementoNormaDestino.getElementosNorma()) {
    		revogaElementosNormaRecursivo(eleNorma, vigenciaExpirada);
    	}
    	dbManager.save(elementoNormaDestino);
    }

    public void criaAlteracaoRevogacao(ElementoNorma elementoNormaOrigem, Norma normaDestino, String dataRevogacao, String caracteristica){
    	Alteracao alt = new Alteracao(elementoNormaOrigem, normaDestino, dataRevogacao, Constantes.REVOGACAO, caracteristica);
    	dbManager.save(alt);
    	elementoNormaOrigem.getAlteracoesFeitas().add(alt);
    	dbManager.save(elementoNormaOrigem);
    	//Setando a nova vigencia da normaDestino
    	normaDestino.getAlteracoesRecebidas().add(alt);
    	String vigenciaExpirada = TimeLogic.getInstance().novaDataFimVigencia(normaDestino.getVigencia(), dataRevogacao);
    	normaDestino.setVigencia( vigenciaExpirada );
    	//Setando a vigencia expirada para os elementosNorma filhos
    	for(ElementoNorma eleNorma : normaDestino.getElementosNorma()) {
    		revogaElementosNormaRecursivo(eleNorma, vigenciaExpirada);
    	}
    	dbManager.save(normaDestino);
    }
    
    public void criaAlteracaoRevogacao(ElementoNorma elementoNormaOrigem, ElementoNorma elementoNormaDestino, String dataRevogacao, String caracteristica){
    	Alteracao alt = new Alteracao(elementoNormaOrigem, elementoNormaDestino, dataRevogacao, Constantes.REVOGACAO, caracteristica);
    	dbManager.save(alt);
    	elementoNormaOrigem.getAlteracoesFeitas().add(alt);
    	dbManager.save(elementoNormaOrigem);
    	//Setando a nova vigencia do elementoNormaDestino
    	elementoNormaDestino.getAlteracoesRecebidas().add(alt);
    	String vigenciaExpirada = TimeLogic.getInstance().novaDataFimVigencia(elementoNormaDestino.getVigencia(), dataRevogacao);
    	elementoNormaDestino.setVigencia( vigenciaExpirada );
    	//Setando a vigencia expirada para os elementosNorma filhos
    	for(ElementoNorma eleNorma : elementoNormaDestino.getElementosNorma()) {
    		revogaElementosNormaRecursivo(eleNorma, vigenciaExpirada);
    	}
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
