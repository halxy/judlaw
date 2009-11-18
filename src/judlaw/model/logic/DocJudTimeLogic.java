/* 
 * Universidade Federal de Campina Grande
 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas	
 */
package judlaw.model.logic;

import java.util.ArrayList;
import java.util.List;

import judlaw.model.bean.docjud.DocumentoJuridico;
import judlaw.model.bean.ref.CitacaoDocJud;
import judlaw.model.util.Constantes;

/**
 * Classe DocJudTimeLogic - define a logica temporal dos documentos juridicos
 * @author Halley Freitas
 *
 */
public class DocJudTimeLogic extends TimeLogic {
	
	/**
	 * Retorna todas citacoes que possuem inconsistencias temporais
	 * @param docJud
	 * @return
	 * @throws Exception Excecao caso as datas estejam mal-formatadas
	 */
	public List<CitacaoDocJud> inconsistenciasTemporaisCDJ(DocumentoJuridico docJud) throws Exception {
		List<CitacaoDocJud> listaResultado = new ArrayList<CitacaoDocJud>();
		List<CitacaoDocJud> citacoesFeitas = docJud.getCitacoesFeitas();
		/*
		 * Caso a data do DocumentoJuridico seja mais atual do que a referencia, é um indicativo
		 * que esta é inconsistente temporalmente
		 */
		for( CitacaoDocJud citacao : citacoesFeitas ){
			//Caso a citacao foi feita a um documento juridico
			if( citacao.getDocumentoJuridicoDestino() != null ) {
				if ( comparaDatas(citacao.getData(), 
						citacao.getDocumentoJuridicoDestino().getDataPublicacao(), 
						Constantes.DELIMITADOR_DATA) < 0 ) { // <0 acontece quando a segunda data eh mais atual
						listaResultado.add( citacao );
					}
			//Caso a citacao foi feita a uma norma
			} else if ( citacao.getNormaDestino() != null ){
				if ( comparaVigenciaComData(citacao.getNormaDestino().getVigencia(), 
						                    Constantes.DELIMITADOR_VIGENCIA, 
						                    citacao.getData(), 
						                    Constantes.DELIMITADOR_DATA) < 0) { // <0 acontece quando a segunda data eh mais atual
						listaResultado.add( citacao );
					}
			//Caso a citacao foi feita a um elementonorma
			} else {
				if ( comparaVigenciaComData(citacao.getElementoNormaDestino().getVigencia(), 
	                    Constantes.DELIMITADOR_VIGENCIA, 
	                    citacao.getData(), 
	                    Constantes.DELIMITADOR_DATA) < 0) { // <0 acontece quando a segunda data eh mais atual
						listaResultado.add( citacao );
				}
			}
		}
		return listaResultado;
	}
}
