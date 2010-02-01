package judlaw.model.logic.time;

import java.util.ArrayList;
import java.util.List;

import judlaw.model.bean.law.ElementoNorma;
import judlaw.model.bean.law.Norma;
import judlaw.model.bean.law.TextoLegal;
import judlaw.model.bean.ref.CitacaoDocJud;
import judlaw.model.bean.ref.CitacaoTextLeg;
import judlaw.model.bean.ref.Referencia;
import judlaw.model.persistence.dbmanager.law.NormaManager;
import judlaw.model.util.Constantes;

public class LawTimeLogic {
    
	private static LawTimeLogic lawTimeLogic = null;
	
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
	 * Retorna se um TextoLegal (Norma ou ElementoNorma) ainda é válido dada uma determinada
	 * data
	 * @param tl
	 * @param data
	 * @return Caso retorna 0 ou 1, o Texto Legal ainda é valido.
	 * @throws Exception Caso haja alguma ma formatacao
	 */
	public boolean textoLegalValido(TextoLegal tl, String data) throws Exception {
		String vigenciaTL = tl.getVigencia();		
		return TimeLogic.getInstance().comparaVigenciaComData(vigenciaTL, Constantes.DELIMITADOR_VIGENCIA,
				   data, Constantes.DELIMITADOR_DATA) >= 0;
	}
	
    /**
     * Dada uma lista de elementosNorma e uma certa data, são retornados os elementosNorma cuja data
     * de vigência não foram ainda expirados.
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
     * Valida todos os elementosNorma de uma norma
     * @param norma
     * @param data
     * @throws Exception
     */
    public void validaElementosNorma(Norma norma, String data) throws Exception {
    	norma.setElementosNorma( elementosNormaValidosData( norma.getElementosNorma(), data) );
    	for( ElementoNorma eleN : norma.getElementosNorma() ) {
    		filhosValidosRecursivo(eleN, data);
    	}	
    }
    
	/**
	 * Retorna se uma referencia (citacao ou alteracao) foi feita a partir daquela data
	 * @param referencia
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public boolean referenciaValida(Referencia referencia, String data) throws Exception {
		String dataRef = referencia.getData();		
		return TimeLogic.getInstance().comparaDatas(dataRef, data, Constantes.DELIMITADOR_DATA) >= 0 ;
	}
	
	
    public List<CitacaoTextLeg> citacoesTextLegValidas(List<CitacaoTextLeg> citacoesTL,
    													 String data) throws Exception {
    	List<CitacaoTextLeg> citacoesTLValidas = new ArrayList<CitacaoTextLeg>();
    	for(CitacaoTextLeg citTL : citacoesTL) {
    		if( referenciaValida(citTL, data) ) {
    			citacoesTLValidas.add( citTL );
    		}
    	}
    	return citacoesTLValidas;
    }
    
    public List<CitacaoDocJud> citacoesDocJudValidas(List<CitacaoDocJud> citacoesDJ,
			 										   String data) throws Exception {
		List<CitacaoDocJud> citacoesDJValidas = new ArrayList<CitacaoDocJud>();
		for(CitacaoDocJud citDJ : citacoesDJ) {
			if( referenciaValida(citDJ, data) ) {
			citacoesDJValidas.add( citDJ );
			}
		}
		return citacoesDJValidas;
}
    
    /**
     * Valida as referências de uma norma
     * @param norma
     * @param data
     * @throws Exception 
     */
    public void validaReferencias(Norma norma, String data) throws Exception {
    	norma.setCitacoesFeitas( citacoesTextLegValidas(norma.getCitacoesFeitas(), data) );
    	norma.setCitacoesRecebidasTextLeg( citacoesTextLegValidas(norma.getCitacoesRecebidasTextLeg(), 
    			                           data) );
    	norma.setCitacoesRecebidasDocJud( citacoesDocJudValidas(norma.getCitacoesRecebidasDocJud(), data));
    }
    
    /**
     * Reconstroi temporalmente uma norma através de uma data passada como parâmetro
     * @param norma
     * @param data
     * @return
     * @throws Exception Caso haja uma ma-formatacao nas datas
     */
    public Norma reconstroiNormaTemporal(Norma norma, String data) throws Exception {
    	Norma visaoNormaTemporal = new Norma();
    	NormaManager.getInstance().setTodosParametrosNorma(norma, visaoNormaTemporal);
    	validaElementosNorma( visaoNormaTemporal, data );
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
