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
import judlaw.model.bean.ref.CitacaoTextLeg;
import judlaw.model.util.Constantes;

/**
 * Classe CitacaoTextLegTimeLogic - define a logica temporal das citacoes de textos legais:
 * normas e elementosNorma
 * @author Halley Freitas
 *
 */
public class CitacaoTextLegTimeLogic extends TimeLogic {

	private static CitacaoTextLegTimeLogic citacaoTextLegTimeLogic = null;
	
	/**
	 * Singleton
	 * @return
	 */
	public static CitacaoTextLegTimeLogic getInstance() {
		if (citacaoTextLegTimeLogic == null) {
			citacaoTextLegTimeLogic = new CitacaoTextLegTimeLogic();
		}
		return citacaoTextLegTimeLogic;
	}
	
	/**
	 * Verifica as inconsistencias temporais de citacoes feitas por um texto legal
	 * @param textoLegal
	 * @return
	 * @throws Exception
	 */
	public List<CitacaoTextLeg> inconsistenciaTemporal(TextoLegal textoLegal) throws Exception {
		List<CitacaoTextLeg> listaResultado = new ArrayList<CitacaoTextLeg>();
		List<CitacaoTextLeg> citacoesFeitas = textoLegal.getCitacoesFeitas();
		/*
		 * Caso a data da referencia feita pela norma seja mais atual que a dataFim
		 * de vigencia das normas e dos elementosNorma, a referencia esta temporalmente inconsistente.
		 * Sao analisadas apenas as referencias feitas a normas e elementosnorma, pois outros documentos
		 * juridicos nao tem "vigencia"
		 */
		for( CitacaoTextLeg citacao : citacoesFeitas ){
			//Caso a citacao foi feita a uma norma
			if ( citacao.getNormaDestino() != null ){
				if ( comparaVigenciaComData(citacao.getNormaDestino().getVigencia(), 
						                    Constantes.DELIMITADOR_VIGENCIA, 
						                    citacao.getData(), 
						                    Constantes.DELIMITADOR_DATA) < 0 ) { /* Caso for < 0, a data da ref eh mais atual
						                     									que a dataFim da vigencia. */
					listaResultado.add( citacao );
				}
			//Caso a citacao foi feita a um elementonorma
			} else if ( citacao.getElementoNormaDestino() != null ){
				if ( comparaVigenciaComData(citacao.getElementoNormaDestino().getVigencia(), 
	                    					Constantes.DELIMITADOR_VIGENCIA, 
	                    					citacao.getData(), 
	                    					Constantes.DELIMITADOR_DATA) < 0 ) { /* Caso for < 0, a data da ref eh mais atual
																				 que a dataFim da vigencia. */
					listaResultado.add( citacao );
				}
			}
		}
		return listaResultado;
	}
}
