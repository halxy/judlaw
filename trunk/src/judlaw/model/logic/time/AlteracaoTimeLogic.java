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

import judlaw.model.bean.law.TextoLegal;
import judlaw.model.bean.ref.Alteracao;
import judlaw.model.util.Constantes;

/**
 * Classe AlteracaoTimeLogic - define a logica temporal das alteracoes feitas por normas e elementosNorma
 * @author Halley Freitas
 *
 */
public class AlteracaoTimeLogic extends TimeLogic {

	private static AlteracaoTimeLogic alteracaoTimeLogic = null;
	
	/**
	 * Singleton
	 * @return
	 */
	public static AlteracaoTimeLogic getInstance() {
		if (alteracaoTimeLogic == null) {
			alteracaoTimeLogic = new AlteracaoTimeLogic();
		}
		return alteracaoTimeLogic;
	}
	
	public List<Alteracao> inconsistenciaTemporal(TextoLegal textoLegal) throws Exception {
		List<Alteracao> listaResultado = new ArrayList<Alteracao>();
		List<Alteracao> alteracoesFeitas = textoLegal.getAlteracoesFeitas();
		/*
		 * Caso a data da referencia feita pela norma seja mais atual que a dataFim
		 * de vigencia das normas e dos elementosNorma, a referencia esta temporalmente inconsistente.
		 * Sao analisadas apenas as referencias feitas a normas e elementosnorma, pois outros documentos
		 * juridicos nao tem "vigencia"
		 */
		for( Alteracao alteracao : alteracoesFeitas ){
			//Caso a alteracao foi feita a uma norma
			if ( alteracao.getNormaDestino() != null ){
				if ( comparaVigenciaComData(alteracao.getNormaDestino().getVigencia(), 
						                    Constantes.DELIMITADOR_VIGENCIA, 
						                    alteracao.getData(), 
						                    Constantes.DELIMITADOR_DATA) < 0 ) { /* Caso for < 0, a data da ref eh mais atual
						                     									que a dataFim da vigencia. */
					listaResultado.add( alteracao );
				}
			//Caso a alteracao foi feita a um elementonorma
			} else if ( alteracao.getElementoNormaDestino() != null ){
				if ( comparaVigenciaComData(alteracao.getElementoNormaDestino().getVigencia(), 
	                    					Constantes.DELIMITADOR_VIGENCIA, 
	                    					alteracao.getData(), 
	                    					Constantes.DELIMITADOR_DATA) < 0 ) { /* Caso for < 0, a data da ref eh mais atual
																				 que a dataFim da vigencia. */
					listaResultado.add( alteracao );
				}
			}
		}
		return listaResultado;
	}
}
