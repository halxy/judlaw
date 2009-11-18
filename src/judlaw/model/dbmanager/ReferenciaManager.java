/* 
 * Universidade Federal de Campina Grande
 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas	
 */
package judlaw.model.dbmanager;

import java.util.List;

import judlaw.model.bean.docjud.DocumentoJuridico;
import judlaw.model.bean.law.ElementoNorma;
import judlaw.model.bean.law.Norma;
import judlaw.model.bean.ref.Alteracao;
import judlaw.model.bean.ref.CitacaoDocJud;
import judlaw.model.bean.ref.CitacaoTextLeg;


/**
 * Classe ReferenciaManager - responsável por gerenciar as operacoes no banco de dados das referencias: 
 * Alteracao, CitacaoDocJud e CitacaoDocLeg;
 * @author Halley Freitas
 *
 */
public class ReferenciaManager {
	
	private static ReferenciaManager referenciaManager = null;
	private static DBManager dbManager = DBManager.getInstance();
	
	
   /**
    * Retorna uma instancia da classe ReferenciaManager
    * @return
    */
    public static ReferenciaManager getInstance(){
        if(referenciaManager == null)
        	referenciaManager = new ReferenciaManager();
        return referenciaManager;
    }
    
	/**
	 * Remove todas as referencias do Banco de Dados
	 */
    public void removeReferencias() {
		//Alteracoes
		for( Alteracao alt : getAlteracoes() ) {
			alt.setNormaOrigem(null);
			alt.setNormaDestino(null);
			alt.setElementoNormaOrigem(null);
			alt.setElementoNormaDestino(null);	
			dbManager.save(alt);
		}
		dbManager.removeAll( new Alteracao() );
		//CitacoesDocJud
		for( CitacaoDocJud citDocJud : getCitacoesDocJud() ){
			citDocJud.setDocumentoJuridicoOrigem(null);
			citDocJud.setDocumentoJuridicoDestino(null);
			citDocJud.setElementoNormaDestino(null);
			citDocJud.setNormaDestino(null);
			dbManager.save(citDocJud);
		}
		dbManager.removeAll( new CitacaoDocJud() );
		//CitacoesTextLeg
		for( CitacaoTextLeg citTextLeg : getCitacoesTextLeg() ){
			citTextLeg.setDocumentoJuridicoDestino(null);
			citTextLeg.setElementoNormaDestino(null);
			citTextLeg.setNormaDestino(null);
			citTextLeg.setElementoNormaOrigem(null);
			citTextLeg.setNormaOrigem(null);
			dbManager.save(citTextLeg);
		}
		dbManager.removeAll( new CitacaoTextLeg() );
	}
    
    /* ------------------------------------------------------------------ */
    /* -------------------- OPERACOES ALTERACAO ------------------------- */
    /* ------------------------------------------------------------------ */ 
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
	
	/* ------------------------------------------------------------------ */
    /* -------------------- OPERACOES CITACAODOCJUD --------------------- */
    /* ------------------------------------------------------------------ */
	@SuppressWarnings("unchecked")
	public List<CitacaoDocJud> getCitacoesDocJud() {
		return dbManager.selectAll( new CitacaoDocJud() );
	}
	
	/**
	 * CitacaoDocJud DocJud -> DocJud
	 * @param documentoJuridicoOrigem
	 * @param documentoJuridicoDestino
	 * @param data
	 */
	public void criaCitacaoDocJud(DocumentoJuridico documentoJuridicoOrigem, DocumentoJuridico documentoJuridicoDestino,
			String data) {
		CitacaoDocJud citacaoDocJud = new CitacaoDocJud(documentoJuridicoOrigem, documentoJuridicoDestino, data);
		dbManager.save(citacaoDocJud);
		documentoJuridicoOrigem.getCitacoesFeitas().add( citacaoDocJud );
		documentoJuridicoDestino.getCitacoesRecebidasDocJud().add( citacaoDocJud );
		dbManager.save(documentoJuridicoOrigem);
		dbManager.save(documentoJuridicoDestino);
	}
	
	/**
	 * CitacaoDocJud DocJud -> Norma
	 * @param documentoJuridicoOrigem
	 * @param normaDestino
	 * @param data
	 */
	public void criaCitacaoDocJud(DocumentoJuridico documentoJuridicoOrigem, Norma normaDestino,
			String data) {
		CitacaoDocJud citacaoDocJud = new CitacaoDocJud(documentoJuridicoOrigem, normaDestino, data);
		dbManager.save(citacaoDocJud);
		documentoJuridicoOrigem.getCitacoesFeitas().add( citacaoDocJud );
		normaDestino.getCitacoesRecebidasDocJud().add( citacaoDocJud );
		dbManager.save(documentoJuridicoOrigem);
		dbManager.save(normaDestino);
	}
	
	/**
	 * CitacaoDocJud DocJud -> ElementoNorma
	 * @param documentoJuridicoOrigem
	 * @param elementoNormaDestino
	 * @param data
	 */
	public void criaCitacaoDocJud(DocumentoJuridico documentoJuridicoOrigem, ElementoNorma elementoNormaDestino,
			String data) {
		CitacaoDocJud citacaoDocJud = new CitacaoDocJud(documentoJuridicoOrigem, elementoNormaDestino, data);
		dbManager.save(citacaoDocJud);
		documentoJuridicoOrigem.getCitacoesFeitas().add( citacaoDocJud );
		elementoNormaDestino.getCitacoesRecebidasDocJud().add( citacaoDocJud );
		dbManager.save(documentoJuridicoOrigem);
		dbManager.save(elementoNormaDestino);
	}
	
	/**
	 * Remove uma CitacaoDocJud do Banco de dados
	 * @param citacaoDocJud
	 */
	public void removeCitacaoDocJud(CitacaoDocJud citacaoDocJud) {
    	//Origem
    	citacaoDocJud.getDocumentoJuridicoOrigem().getCitacoesFeitas().remove( citacaoDocJud );
    	dbManager.save( citacaoDocJud.getDocumentoJuridicoOrigem() );
    	//Destino
    	if( citacaoDocJud.getDocumentoJuridicoDestino() != null ){
    		citacaoDocJud.getDocumentoJuridicoDestino().getCitacoesRecebidasDocJud().remove( citacaoDocJud );
    		dbManager.save( citacaoDocJud.getDocumentoJuridicoDestino() );
    	} else if (citacaoDocJud.getNormaDestino() != null ){
    		citacaoDocJud.getNormaDestino().getCitacoesRecebidasDocJud().remove( citacaoDocJud );
    		dbManager.save( citacaoDocJud.getNormaDestino() );
    	} else {
    		citacaoDocJud.getElementoNormaDestino().getCitacoesRecebidasDocJud().remove( citacaoDocJud );
    		dbManager.save( citacaoDocJud.getElementoNormaDestino() );
    	}
    	dbManager.remove( citacaoDocJud );
    }
	
	/**
	 * Altera uma citacaoDocJud
	 * @param newCitacaoDJud
	 * @param oldCitacaoDocJud
	 */
	public void alteraCitacaoDocJud(CitacaoDocJud newCitacaoDJud, CitacaoDocJud oldCitacaoDocJud){
		oldCitacaoDocJud.setData( newCitacaoDJud.getData() );
		dbManager.save( oldCitacaoDocJud ); 
	}
	
	/* ------------------------------------------------------------------ */
    /* -------------------- OPERACOES CITACAOTEXTLEG -------------------- */
    /* ------------------------------------------------------------------ */
	@SuppressWarnings("unchecked")
	public List<CitacaoTextLeg> getCitacoesTextLeg() {
		return dbManager.selectAll( new CitacaoTextLeg() );
	}
	
	/**
	 * CitacaoTextLeg Norma -> Norma
	 * @param normaOrigem
	 * @param normaDestino
	 * @param data
	 */
	public void criaCitacaoTextLeg(Norma normaOrigem, Norma normaDestino, String data){
    	CitacaoTextLeg citacaoTextLeg = new CitacaoTextLeg(normaOrigem, normaDestino, data);
    	dbManager.save(citacaoTextLeg);
    	normaOrigem.getCitacoesFeitas().add( citacaoTextLeg );
    	normaDestino.getCitacoesRecebidasTextLeg().add( citacaoTextLeg );
    	dbManager.save(normaOrigem);
    	dbManager.save(normaDestino);
	}
	
	/**
	 * CitacaoTextLeg Norma -> ElementoNorma
	 * @param normaOrigem
	 * @param eleNormanormaDestino
	 * @param data
	 */
	public void criaCitacaoTextLeg(Norma normaOrigem, ElementoNorma elementoNormaDestino, String data){
    	CitacaoTextLeg citacaoTextLeg = new CitacaoTextLeg(normaOrigem, elementoNormaDestino, data);
    	dbManager.save(citacaoTextLeg);
    	normaOrigem.getCitacoesFeitas().add( citacaoTextLeg );
    	elementoNormaDestino.getCitacoesRecebidasTextLeg().add( citacaoTextLeg );
    	dbManager.save(normaOrigem);
    	dbManager.save(elementoNormaDestino);
	}
	
	/**
	 * CitacaoTextLeg Norma -> DocumentoJuridico
	 * @param normaOrigem
	 * @param documentoJuridicoDestino
	 * @param data
	 */
	public void criaCitacaoTextLeg(Norma normaOrigem, DocumentoJuridico documentoJuridicoDestino, String data){
    	CitacaoTextLeg citacaoTextLeg = new CitacaoTextLeg(normaOrigem, documentoJuridicoDestino, data);
    	dbManager.save(citacaoTextLeg);
    	normaOrigem.getCitacoesFeitas().add( citacaoTextLeg );
    	documentoJuridicoDestino.getCitacoesRecebidasTextLeg().add( citacaoTextLeg );
    	dbManager.save(normaOrigem);
    	dbManager.save(documentoJuridicoDestino);
	}
	
	/**
	 * CitacaoTextLeg ElementoNorma -> Norma
	 * @param elementoNormaOrigem
	 * @param normaDestino
	 * @param data
	 */
	public void criaCitacaoTextLeg(ElementoNorma elementoNormaOrigem, Norma normaDestino, String data){
    	CitacaoTextLeg citacaoTextLeg = new CitacaoTextLeg(elementoNormaOrigem, normaDestino, data);
    	dbManager.save(citacaoTextLeg);
    	elementoNormaOrigem.getCitacoesFeitas().add( citacaoTextLeg );
    	normaDestino.getCitacoesRecebidasTextLeg().add( citacaoTextLeg );
    	dbManager.save(elementoNormaOrigem);
    	dbManager.save(normaDestino);
	}
	
	/**
	 * CitacaoTextLeg ElementoNorma -> ElementoNorma
	 * @param elementoNormaOrigem
	 * @param elementoNormaDestino
	 * @param data
	 */
	public void criaCitacaoTextLeg(ElementoNorma elementoNormaOrigem, ElementoNorma elementoNormaDestino, String data){
    	CitacaoTextLeg citacaoTextLeg = new CitacaoTextLeg(elementoNormaOrigem, elementoNormaDestino, data);
    	dbManager.save(citacaoTextLeg);
    	elementoNormaOrigem.getCitacoesFeitas().add( citacaoTextLeg );
    	elementoNormaDestino.getCitacoesRecebidasTextLeg().add( citacaoTextLeg );
    	dbManager.save(elementoNormaOrigem);
    	dbManager.save(elementoNormaDestino);
	}
	
	/**
	 * CitacaoTextLeg ElementoNorma -> DocumentoJuridico
	 * @param elementoNormaOrigem
	 * @param documentoJuridicoDestino
	 * @param data
	 */
	public void criaCitacaoTextLeg(ElementoNorma elementoNormaOrigem, DocumentoJuridico documentoJuridicoDestino, String data){
    	CitacaoTextLeg citacaoTextLeg = new CitacaoTextLeg(elementoNormaOrigem, documentoJuridicoDestino, data);
    	dbManager.save(citacaoTextLeg);
    	elementoNormaOrigem.getCitacoesFeitas().add( citacaoTextLeg );
    	documentoJuridicoDestino.getCitacoesRecebidasTextLeg().add( citacaoTextLeg );
    	dbManager.save(elementoNormaOrigem);
    	dbManager.save(documentoJuridicoDestino);
	}
	
	/**
	 * Remove uma CitacaoTextLeg do Banco de dados
	 * @param citacaoTextLeg
	 */
	public void removeCitacaoTextLeg(CitacaoTextLeg citacaoTextLeg) {
		//Origem
    	if( citacaoTextLeg.getNormaOrigem() != null ) {
    		citacaoTextLeg.getNormaOrigem().getCitacoesFeitas().remove( citacaoTextLeg );
    		dbManager.save( citacaoTextLeg.getNormaOrigem() );
    	} else {
    		citacaoTextLeg.getElementoNormaOrigem().getCitacoesFeitas().remove( citacaoTextLeg );
    		dbManager.save( citacaoTextLeg.getElementoNormaOrigem() );
    	}
    	//Destino
    	if( citacaoTextLeg.getNormaDestino() != null ){
    		citacaoTextLeg.getNormaDestino().getCitacoesRecebidasTextLeg().remove( citacaoTextLeg );
    		dbManager.save( citacaoTextLeg.getNormaDestino() );
    	} else if ( citacaoTextLeg.getElementoNormaDestino() != null ){
    		citacaoTextLeg.getElementoNormaDestino().getCitacoesRecebidasTextLeg().remove( citacaoTextLeg );
    		dbManager.save( citacaoTextLeg.getElementoNormaDestino() );
    	} else {
    		citacaoTextLeg.getDocumentoJuridicoDestino().getCitacoesRecebidasTextLeg().remove( citacaoTextLeg );
    		dbManager.save( citacaoTextLeg.getDocumentoJuridicoDestino() );
    	}
    	dbManager.remove( citacaoTextLeg );
    }
	
	/**
	 * Altera a data de uma citacaoTextLeg
	 * @param newCitacaoTextLeg
	 * @param oldCitacaoTextLeg
	 */
	public void alteraCitacaoTextLeg(CitacaoTextLeg newCitacaoTextLeg, CitacaoTextLeg oldCitacaoTextLeg) {
		oldCitacaoTextLeg.setData( newCitacaoTextLeg.getData() );
		dbManager.save( oldCitacaoTextLeg );
	}
}
