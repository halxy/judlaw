/* 
 * Universidade Federal de Campina Grande
 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas	
 */
package judlaw.model.logic.time;

import java.util.ArrayList;
import java.util.List;

import judlaw.model.bean.law.ElementoNorma;
import judlaw.model.bean.law.Norma;
import judlaw.model.bean.law.TextoLegal;
import judlaw.model.persistence.dbmanager.law.NormaManager;
import judlaw.model.util.Constantes;

/**
 * Classe LawTimeLogic. Define a logica temporal associada aos textos legais (normas e elementosNorma).
 * @author Halley Freitas
 *
 */
public class LawTimeLogic extends TimeLogic {
    
	private static LawTimeLogic lawTimeLogic = null;
	private TimeLogic timeLogic = TimeLogic.getInstance();
	
	/**
	 * Singleton
	 * @return
	 */
	public static LawTimeLogic getInstance() {
		if (lawTimeLogic == null) {
			lawTimeLogic = new LawTimeLogic();
		}
		return lawTimeLogic;
	}
	
	/**
	 * Retorna se um TextoLegal (Norma ou ElementoNorma) está válido em uma determinada data.
	 * @param tl
	 * @param data
	 * @return true, caso o textoLegal esteja valido. false, caso contrario.
	 * @throws Exception
	 */
	public boolean textoLegalValido(TextoLegal tl, String data) throws Exception {
		String validadeTL = tl.getValidade();				
		return timeLogic.dataContidaEmIntervalo(data, Constantes.DELIMITADOR_DATA, 
				                                validadeTL, Constantes.DELIMITADOR_INTERVALO);
	}
	
	/*
	 * IMPORTANTE: Pode haver casos em que a norma está válida, porém não está vigente. No entanto,
	 * o contrário não é verdadeiro, então sempre que um elemento não for mais valido, ele também
	 * não é mais vigente.
	 */
	/**
	 * Retorna se um TextoLegal (Norma ou ElementoNorma) está vigente em uma determinada data.
	 * @param tl
	 * @param data
	 * @return true, caso o textoLegal esteja vigente. false, caso contrario.
	 * @throws Exception
	 */
	public boolean textoLegalVigente(TextoLegal tl, String data) throws Exception {
		String vigenciaTL = tl.getVigencia();				
		return timeLogic.dataContidaEmIntervalo(data, Constantes.DELIMITADOR_DATA, 
												vigenciaTL, Constantes.DELIMITADOR_INTERVALO);
	}
	
    /**
     * Dada uma lista de elementosNorma e uma certa data, são retornados os elementosNorma cuja data
     * de validade não foram ainda expirados.
     * @param elementosNorma
     * @param data
     * @return
     * @throws Exception Caso haja alguma ma-formatacao na data
     */
    public List<ElementoNorma> elementosNormaValidosData(List<ElementoNorma> elementosNorma,
    		                                              String data) throws Exception {
    	List<ElementoNorma> elementosValidos = new ArrayList<ElementoNorma>();
    	for( ElementoNorma eleN : elementosNorma ) {
    		if ( textoLegalValido(eleN, data) ) {
    			elementosValidos.add( eleN );
    		}
    	}
    	return elementosValidos;
    }
    
    /**
     * Dada uma lista de elementosNorma e uma certa data, são retornados os elementosNorma cuja data
     * de vigencia não foram ainda expirados.
     * @param elementosNorma
     * @param data
     * @return
     * @throws Exception Caso haja alguma ma-formatacao na data
     */
    public List<ElementoNorma> elementosNormaVigentesData(List<ElementoNorma> elementosNorma,
    		                                              String data) throws Exception {
    	List<ElementoNorma> elementosVigentes = new ArrayList<ElementoNorma>();
    	for( ElementoNorma eleN : elementosNorma ) {
    		if ( textoLegalVigente(eleN, data) ) {
    			elementosVigentes.add( eleN );
    		}
    	}
    	return elementosVigentes;
    }
    
    /**
     * Dado um elementoNorma raiz, verifica recursivamente em todos os filhos e nos filhos dos filhos
     * quais elementos sao validos de acordo com uma data passada
     * @param elementoNorma
     * @param data
     * @throws Exception Caso haja uma ma-formatacao na data
     */
    public void filhosValidosRecursivo(ElementoNorma elementoNorma, String data) throws Exception {
    	//Começando pelo primeiro nivel sao podados os filhos antes de fazer a recursividade
    	elementoNorma.setElementosNorma( elementosNormaValidosData( elementoNorma.getElementosNorma(), data) );
    	List<ElementoNorma> filhos = elementoNorma.getElementosNorma(); 
		for(ElementoNorma filho : filhos) {
			filhosValidosRecursivo(filho, data);
		}
    }
    
    /**
     * Dado um elementoNorma raiz, verifica recursivamente em todos os filhos e nos filhos dos filhos
     * quais elementos sao vigentes de acordo com uma data passada
     * @param elementoNorma
     * @param data
     * @throws Exception Caso haja uma ma-formatacao na data
     */
    public void filhosVigentesRecursivo(ElementoNorma elementoNorma, String data) throws Exception {
    	//Começando pelo primeiro nivel sao podados os filhos antes de fazer a recursividade
    	elementoNorma.setElementosNorma( elementosNormaVigentesData( elementoNorma.getElementosNorma(), data) );
    	List<ElementoNorma> filhos = elementoNorma.getElementosNorma(); 
		for(ElementoNorma filho : filhos) {
			filhosVigentesRecursivo(filho, data);
		}
    }
	
    /**
     * Valida todos os elementosNorma de uma norma quanto à validade
     * @param norma
     * @param data
     * @throws Exception
     */
    public void validaElementosNormaValidade(Norma norma, String data) throws Exception {
    	norma.setElementosNorma( elementosNormaValidosData( norma.getElementosNorma(), data) );
    	for( ElementoNorma eleN : norma.getElementosNorma() ) {
    		filhosValidosRecursivo(eleN, data);
    	}	
    }
    
    /**
     * Valida todos os elementosNorma de uma norma quanto à vigência
     * @param norma
     * @param data
     * @throws Exception
     */
    public void validaElementosNormaVigencia(Norma norma, String data) throws Exception {
    	norma.setElementosNorma( elementosNormaVigentesData( norma.getElementosNorma(), data) );
    	for( ElementoNorma eleN : norma.getElementosNorma() ) {
    		filhosVigentesRecursivo(eleN, data);
    	}	
    }
    
    /**
     * Valida as referências de uma norma. Método utilizado para a reconstrução temporal de uma norma, 
     * ou seja, só retorna as referências feitas a partir daquela data
     * @param norma
     * @param data
     * @throws Exception 
     */
    public void validaReferencias(Norma norma, String data) throws Exception {
    	norma.setCitacoesFeitas( CitacaoTextLegTimeLogic.getInstance().citacoesTextLegValidas(norma.getCitacoesFeitas(), data) );
    	norma.setCitacoesRecebidasTextLeg( CitacaoTextLegTimeLogic.getInstance().citacoesTextLegValidas(norma.getCitacoesRecebidasTextLeg(), 
    			                           data) );
    	norma.setCitacoesRecebidasDocJud( CitacaoDocJudTimeLogic.getInstance().citacoesDocJudValidas(norma.getCitacoesRecebidasDocJud(), data) );
    	norma.setAlteracoesFeitas( AlteracaoTimeLogic.getInstance().alteracoesValidas(norma.getAlteracoesFeitas(), data) );
    	norma.setAlteracoesRecebidas( AlteracaoTimeLogic.getInstance().alteracoesValidas(norma.getAlteracoesRecebidas(), data) );
    }
    
    /**
     * Reconstroi temporalmente uma norma através de uma data passada como parâmetro. Não são retornados
     * os elementos que não estão válidos.
     * @param norma
     * @param data
     * @return Norma que representa a visão temporal atual da norma passada como parâmetro
     * @throws Exception Caso haja uma ma-formatacao nas datas
     */
    public Norma reconstroiNormaTemporalValidade(Norma norma, String data) throws Exception {
    	Norma visaoNormaTemporal = new Norma();
    	NormaManager.getInstance().setTodosParametrosNorma(norma, visaoNormaTemporal);
    	// Exclui da visão os elementosNorma que não estão válidos em relação à data
    	validaElementosNormaValidade( visaoNormaTemporal, data );
    	// Exclui da visão as referências que não estão válidas em relação à data
    	validaReferencias( visaoNormaTemporal, data);
    	// Retorna a visão
    	return visaoNormaTemporal;
    }
    
    /**
     * Reconstroi temporalmente uma norma através de uma data passada como parâmetro. Não são retornados
     * os elementos que não estão vigentes
     * @param norma
     * @param data
     * @return Norma que representa a visão temporal atual da norma passada como parâmetro
     * @throws Exception Caso haja uma ma-formatacao nas datas
     */
    public Norma reconstroiNormaTemporalVigencia(Norma norma, String data) throws Exception {
    	Norma visaoNormaTemporal = new Norma();
    	NormaManager.getInstance().setTodosParametrosNorma(norma, visaoNormaTemporal);
    	// Exclui da visão os elementosNorma que não estão válidos em relação à data
    	validaElementosNormaVigencia( visaoNormaTemporal, data );
    	// Exclui da visão as referências que não estão válidas em relação à data
    	validaReferencias( visaoNormaTemporal, data);
    	// Retorna a visão
    	return visaoNormaTemporal;
    }
    
    /**
     * Dada uma lista de normas, retorna aquela que possui a data de publicacao mais atual.
     * @param normas
     * @return
     * @throws Exception Lancada caso der algum erro na comparacao das datas
     */
    public Norma normaMaisAtual(List<Norma> normas) throws Exception {
    	Norma normaResultado = normas.get( normas.size() - 1 ); // pega o ultimo elemento
    	for( Norma normaAux : normas ) {
    		if (TimeLogic.getInstance().comparaDatas(normaAux.getDataPublicacao(), 
    												 normaResultado.getDataPublicacao(), 
    												 Constantes.DELIMITADOR_DATA) > 0) {
    			normaResultado = normaAux;
    		}
    	}
    	return normaResultado;
    }
    
    /**
     * A partir de um ElementoNorma, a norma a qual ele pertence é reconstruida.
     * @param elementoNorma
     * @return
     * @throws Exception Devido ao metodo normaMaisAtual
     */
    public Norma reconstroiNorma(ElementoNorma elementoNorma) throws Exception {
    	List<ElementoNorma> elementosPai = elementoNorma.getElementosNormaPai();
    	ElementoNorma eleNormaAux = null;
    	while( elementosPai.size() > 0 ) { // Retorna o elementoNorma mais proximo da norma
    		eleNormaAux = elementosPai.get(0);
    		elementosPai = eleNormaAux.getElementosNormaPai();
    	}
    	if( eleNormaAux == null ) { //se nao houverem elementosPai
    		eleNormaAux = elementoNorma;
    	}
    	/*
    	 *  Recuperando as normasPai que possuem data de publicacao menor ou igual à data
    	 *  de publicação do elementoNorma para depois pegar o maior da lista.
    	 */
    	ArrayList<Norma> normasPai = new ArrayList<Norma>();
    	for( Norma normaPai : eleNormaAux.getNormasPai() ) {
    		if(TimeLogic.getInstance().comparaDatas(normaPai.getDataPublicacao(), 
    												elementoNorma.getDataPublicacao(), 
    												Constantes.DELIMITADOR_DATA) < 1) {
    			normasPai.add( normaPai );
    		}
    	}
    	return normaMaisAtual(normasPai);
    }
}
