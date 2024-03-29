/* 
 * Universidade Federal de Campina Grande
 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas	
 */
package judlaw.model.persistence.dbmanager.ref;

import java.util.List;

import judlaw.model.bean.law.ElementoNorma;
import judlaw.model.bean.law.Norma;
import judlaw.model.bean.ref.Alteracao;
import judlaw.model.logic.time.TimeLogic;
import judlaw.model.persistence.dbmanager.DBManager;
import judlaw.model.persistence.dbmanager.law.ElementoNormaManager;
import judlaw.model.persistence.dbmanager.law.NormaManager;
import judlaw.model.util.Constantes;

/**
 * Classe AlteracaoManager. Respons�vel por gerenciar as operacoes no banco de dados das alteracoes 
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
     * ALTERACOES SIMPLES: sao aquelas que n�o modificam o banco de dados, apenas incluem uma nova entrada
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
    
    /* --------------- REVOGACAO --------------- */
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
    public void criaAlteracaoRevogacao(Norma normaOrigem, Norma normaDestino, String dataRevogacao, String tipo, String caracteristica){
    	Alteracao alt = new Alteracao(normaOrigem, normaDestino, dataRevogacao, tipo, caracteristica);
    	dbManager.save(alt);
    	normaOrigem.getAlteracoesFeitas().add(alt);
    	dbManager.save(normaOrigem);
    	//Setando a nova vigencia da normaDestino
    	normaDestino.getAlteracoesRecebidas().add(alt);
    	String vigenciaExpirada = TimeLogic.getInstance().novaDataFimIntervalo(normaDestino.getVigencia(), dataRevogacao);
    	normaDestino.setVigencia( vigenciaExpirada );
    	//Setando a vigencia expirada para os elementosNorma filhos
    	for(ElementoNorma eleNorma : normaDestino.getElementosNorma()) {
    		revogaElementosNormaRecursivo(eleNorma, vigenciaExpirada);
    	}
    	dbManager.save(normaDestino);
    }
    
    public void criaAlteracaoRevogacao(Norma normaOrigem, ElementoNorma elementoNormaDestino, String dataRevogacao, String tipo ,String caracteristica){
    	Alteracao alt = new Alteracao(normaOrigem, elementoNormaDestino, dataRevogacao, tipo, caracteristica);
    	dbManager.save(alt);
    	normaOrigem.getAlteracoesFeitas().add(alt);
    	dbManager.save(normaOrigem);
    	//Setando a nova vigencia do elementoNormaDestino
    	elementoNormaDestino.getAlteracoesRecebidas().add(alt);
    	String vigenciaExpirada = TimeLogic.getInstance().novaDataFimIntervalo(elementoNormaDestino.getVigencia(), dataRevogacao);
    	elementoNormaDestino.setVigencia( vigenciaExpirada );
    	//Setando a vigencia expirada para os elementosNorma filhos
    	for(ElementoNorma eleNorma : elementoNormaDestino.getElementosNorma()) {
    		revogaElementosNormaRecursivo(eleNorma, vigenciaExpirada);
    	}
    	dbManager.save(elementoNormaDestino);
    }

    public void criaAlteracaoRevogacao(ElementoNorma elementoNormaOrigem, Norma normaDestino, String dataRevogacao, String tipo, String caracteristica){
    	Alteracao alt = new Alteracao(elementoNormaOrigem, normaDestino, dataRevogacao, tipo, caracteristica);
    	dbManager.save(alt);
    	elementoNormaOrigem.getAlteracoesFeitas().add(alt);
    	dbManager.save(elementoNormaOrigem);
    	//Setando a nova vigencia da normaDestino
    	normaDestino.getAlteracoesRecebidas().add(alt);
    	String vigenciaExpirada = TimeLogic.getInstance().novaDataFimIntervalo(normaDestino.getVigencia(), dataRevogacao);
    	normaDestino.setVigencia( vigenciaExpirada );
    	//Setando a vigencia expirada para os elementosNorma filhos
    	for(ElementoNorma eleNorma : normaDestino.getElementosNorma()) {
    		revogaElementosNormaRecursivo(eleNorma, vigenciaExpirada);
    	}
    	dbManager.save(normaDestino);
    }
    
    public void criaAlteracaoRevogacao(ElementoNorma elementoNormaOrigem, ElementoNorma elementoNormaDestino, String dataRevogacao, String tipo, String caracteristica){
    	Alteracao alt = new Alteracao(elementoNormaOrigem, elementoNormaDestino, dataRevogacao, tipo, caracteristica);
    	dbManager.save(alt);
    	elementoNormaOrigem.getAlteracoesFeitas().add(alt);
    	dbManager.save(elementoNormaOrigem);
    	//Setando a nova vigencia do elementoNormaDestino
    	elementoNormaDestino.getAlteracoesRecebidas().add(alt);
    	String vigenciaExpirada = TimeLogic.getInstance().novaDataFimIntervalo(elementoNormaDestino.getVigencia(), dataRevogacao);
    	elementoNormaDestino.setVigencia( vigenciaExpirada );
    	//Setando a vigencia expirada para os elementosNorma filhos
    	for(ElementoNorma eleNorma : elementoNormaDestino.getElementosNorma()) {
    		revogaElementosNormaRecursivo(eleNorma, vigenciaExpirada);
    	}
    	dbManager.save(elementoNormaDestino);
    }
    
    /* --------------- INCLUSAO --------------- */
    /*
     * Apenas sao incluidos elementosNorma, tanto em normas quanto em outros elementosNorma
     */
    /*
     * Revoga todos os elementos de uma norma e seus filhos
     */
	private void setaDataPublicacaoRecursiva(ElementoNorma elementoNorma, String dataPublicacao) {
		elementoNorma.setDataPublicacao( dataPublicacao );
		List<ElementoNorma> filhos = elementoNorma.getElementosNorma();
		for(ElementoNorma filho : filhos) {
			filho.setDataPublicacao( dataPublicacao );
			dbManager.save(filho);
			revogaElementosNormaRecursivo(filho, dataPublicacao);
		}
	}
	
    /**
     * A alteracaoInclusao inclue um novo elemento em uma norma/Elemento, e a entrada na tabela de Alteracoes
     * eh configurada pelo relacionamento entre a origem e o novo elemento.
     * @param normaOrigem
     * @param normaDestino
     * @param novoElemento
     * @param dataInclusao
     * @param caracteristica
     */
    public void criaAlteracaoInclusao(Norma normaOrigem, Norma normaDestino, ElementoNorma novoElemento, String dataInclusao){
    	//A data de publicacao do elemento sera a data em que ele foi incluido
    	setaDataPublicacaoRecursiva(novoElemento, dataInclusao);
    	ElementoNormaManager.getInstance().adicionaElementoNorma(novoElemento, normaDestino);
    	Alteracao alteracao = new Alteracao(normaOrigem, novoElemento, dataInclusao, Constantes.INCLUSAO, Constantes.NEUTRA);
    	dbManager.save( alteracao );
    }
    
    public void criaAlteracaoInclusao(Norma normaOrigem, ElementoNorma elementoNormaDestino, ElementoNorma novoElemento, String dataInclusao){
    	//A data de publicacao do elemento sera a data em que ele foi incluido
    	setaDataPublicacaoRecursiva(novoElemento, dataInclusao);
    	ElementoNormaManager.getInstance().adicionaElementoNorma(novoElemento, elementoNormaDestino);
    	Alteracao alteracao = new Alteracao(normaOrigem, novoElemento, dataInclusao, Constantes.INCLUSAO, Constantes.NEUTRA);
    	dbManager.save( alteracao );
    }
    
    public void criaAlteracaoInclusao(ElementoNorma elementoNormaOrigem, Norma normaDestino, ElementoNorma novoElemento, String dataInclusao){
    	//A data de publicacao do elemento sera a data em que ele foi incluido
    	setaDataPublicacaoRecursiva(novoElemento, dataInclusao);
    	ElementoNormaManager.getInstance().adicionaElementoNorma(novoElemento, normaDestino);
    	Alteracao alteracao = new Alteracao(elementoNormaOrigem, novoElemento, dataInclusao, Constantes.INCLUSAO, Constantes.NEUTRA);
    	dbManager.save( alteracao );
    }
    
    public void criaAlteracaoInclusao(ElementoNorma elementoNormaOrigem, ElementoNorma elementoNormaDestino, ElementoNorma novoElemento, String dataInclusao){
    	//A data de publicacao do elemento sera a data em que ele foi incluido
    	setaDataPublicacaoRecursiva(novoElemento, dataInclusao);
    	ElementoNormaManager.getInstance().adicionaElementoNorma(novoElemento, elementoNormaDestino);
    	Alteracao alteracao = new Alteracao(elementoNormaOrigem, novoElemento, dataInclusao, Constantes.INCLUSAO, Constantes.NEUTRA);
    	dbManager.save( alteracao );
    }
    
    /* --------------- MODIFICACAO --------------- */
    /**
     * � criada uma alteracaoModificacao, tendo como Destino a norma que sofreu a modificacao   
     * @param normaOrigem - A norma modificadora
     * @param normaDestino - A norma que sofreu a modificacao
     * @param normaModificada - A nova norma apos a modificacao
     * @param dataModificacao
     * @param caracteristica
     */
    public void criaAlteracaoModificacao(Norma normaOrigem, Norma normaDestino, Norma normaModificada, 
    		                             String dataModificacao, String caracteristica){
    	//Setando o fim da vigencia da norma alterada
    	normaDestino.setVigencia( TimeLogic.getInstance().novaDataFimIntervalo(normaDestino.getVigencia(), 
    							  TimeLogic.getInstance().diaAnterior(dataModificacao)));
    	dbManager.save(normaDestino);
    	//Criando a nova norma
//    	Norma novaNorma = new Norma();
//    	NormaManager.getInstance().setParametrosNorma(normaModificada, novaNorma, dataModificacao);
//    	NormaManager.getInstance().salvaNormaAlterada( novaNorma );
    	NormaManager.getInstance().setParametrosNorma(normaDestino, normaModificada, dataModificacao);
    	NormaManager.getInstance().salvaNormaAlterada( normaModificada );
    	//Criando a alteracao
    	Alteracao alteracao = new Alteracao(normaOrigem, normaDestino, dataModificacao, Constantes.MODIFICACAO, caracteristica);
    	dbManager.save( alteracao );
    }
    
    public void criaAlteracaoModificacao(Norma normaOrigem, ElementoNorma elementoNormaDestino, ElementoNorma elementoNormaModificado, 
            String dataModificacao, String caracteristica){
    	//Setando o fim da vigencia do elementoNorma alterado
    	elementoNormaDestino.setVigencia( TimeLogic.getInstance().novaDataFimIntervalo(elementoNormaDestino.getVigencia(), 
    							  TimeLogic.getInstance().diaAnterior(dataModificacao)));
    	dbManager.save(elementoNormaDestino);
    	//Criando o novo elementoNorma
		ElementoNormaManager.getInstance().setParametrosElementoNorma(elementoNormaDestino, elementoNormaModificado, dataModificacao);
		//Pais
		for( Norma normaPai : elementoNormaModificado.getNormasPai() ){
			normaPai.getElementosNorma().add( elementoNormaModificado );
			dbManager.save( normaPai );
		}
		for( ElementoNorma elementoNormaPai : elementoNormaModificado.getElementosNormaPai() ){
			elementoNormaPai.getElementosNorma().add( elementoNormaModificado );
			dbManager.save( elementoNormaPai );
		}
		ElementoNormaManager.getInstance().salvaElementoAlterado( elementoNormaModificado );
		//Criando a alteracao
		Alteracao alteracao = new Alteracao(normaOrigem, elementoNormaDestino, dataModificacao, Constantes.MODIFICACAO, caracteristica);
		dbManager.save( alteracao );
    }
    
    public void criaAlteracaoModificacao(ElementoNorma elementoNormaOrigem, Norma normaDestino, Norma normaModificada, 
            String dataModificacao, String caracteristica){
    	//Setando o fim da vigencia da norma alterada
    	normaDestino.setVigencia( TimeLogic.getInstance().novaDataFimIntervalo(normaDestino.getVigencia(), 
    							  TimeLogic.getInstance().diaAnterior(dataModificacao)));
    	dbManager.save(normaDestino);
    	//Criando a nova norma
    	Norma novaNorma = new Norma();
		NormaManager.getInstance().setParametrosNorma(normaModificada, novaNorma, dataModificacao);
		NormaManager.getInstance().salvaNormaAlterada( novaNorma );
		//Criando a alteracao
		Alteracao alteracao = new Alteracao(elementoNormaOrigem, normaDestino, dataModificacao, Constantes.MODIFICACAO, caracteristica);
		dbManager.save( alteracao );
    }
    
    public void criaAlteracaoModificacao(ElementoNorma elementoNormaOrigem, ElementoNorma elementoNormaDestino, ElementoNorma elementoNormaModificado, 
            String dataModificacao, String caracteristica){
    	//Setando o fim da vigencia do elementoNorma alterado
    	elementoNormaDestino.setVigencia( TimeLogic.getInstance().novaDataFimIntervalo(elementoNormaDestino.getVigencia(), 
    							  TimeLogic.getInstance().diaAnterior(dataModificacao)));
    	dbManager.save(elementoNormaDestino);
    	//Criando o novo elementoNorma
		ElementoNormaManager.getInstance().setParametrosElementoNorma(elementoNormaDestino, elementoNormaModificado, dataModificacao);
		//Pais
		for( Norma normaPai : elementoNormaModificado.getNormasPai() ){
			normaPai.getElementosNorma().add( elementoNormaModificado );
			dbManager.save( normaPai );
		}
		for( ElementoNorma elementoNormaPai : elementoNormaModificado.getElementosNormaPai() ){
			elementoNormaPai.getElementosNorma().add( elementoNormaModificado );
			dbManager.save( elementoNormaPai );
		}
		ElementoNormaManager.getInstance().salvaElementoAlterado( elementoNormaModificado );
		//Criando a alteracao
		Alteracao alteracao = new Alteracao(elementoNormaOrigem, elementoNormaDestino, dataModificacao, Constantes.MODIFICACAO, caracteristica);
		dbManager.save( alteracao );
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
		oldAlteracao.setResultadoAlteracao( newAlteracao.getResultadoAlteracao() );
		dbManager.save( oldAlteracao );
	}
}
