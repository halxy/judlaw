package judlaw.model.logic.time;

import java.util.ArrayList;
import java.util.List;

import judlaw.model.bean.law.ElementoNorma;
import judlaw.model.bean.law.Norma;
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
    
    /**
     * Reconstroi temporalmente uma norma através de uma data passada como parâmetro
     * @param norma
     * @param data
     * @return
     */
    public Norma reconstroiNormaTemporal(Norma norma, String data) {
    	Norma visaoNormaTemporal = new Norma();
    	NormaManager.getInstance().setTodosParametrosNorma(norma, visaoNormaTemporal);
    	//TODO
    	return visaoNormaTemporal;
    }
}
