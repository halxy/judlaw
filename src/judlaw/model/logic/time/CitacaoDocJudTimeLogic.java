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

import judlaw.model.bean.docjud.DocumentoJuridico;
import judlaw.model.bean.law.ElementoNorma;
import judlaw.model.bean.law.Norma;
import judlaw.model.bean.ref.CitacaoDocJud;
import judlaw.model.util.Constantes;

/**
 * Classe CitacaoDocJudTimeLogic - define a logica temporal das citacoes de documentos juridicos
 * @author Halley Freitas
 *
 */
public class CitacaoDocJudTimeLogic extends TimeLogic {
	
	private static CitacaoDocJudTimeLogic citacaoDocJudTimeLogic = null;
	
	/**
	 * Singleton
	 * @return
	 */
	public static CitacaoDocJudTimeLogic getInstance() {
		if (citacaoDocJudTimeLogic == null) {
			citacaoDocJudTimeLogic = new CitacaoDocJudTimeLogic();
		}
		return citacaoDocJudTimeLogic;
	}
	
	/*
	 * Inicializa as listas a serem utilizadas na refinacao da inconsistencia temporal
	 */
	private void inicializaListas() {
		setNormasPaiAtualizados( new ArrayList<Norma>() );
		setElementosNormaPaiAtualizados( new ArrayList<ElementoNorma>() );
		setElementosNormaFilhosAtualizados( new ArrayList<ElementoNorma>() );
	}
	/**
	 * Dado um documentoJuridico, retorna as citacoes temporalmente inconsistentes feitas por ele.
	 * @param docJud
	 * @return Citacoes temporalmente inconsistentes feitas por ele.
	 * @throws Exception
	 */
	public List<CitacaoDocJud> inconsistenciaTemporal(DocumentoJuridico docJud) throws Exception {
		inicializaListas();
		List<CitacaoDocJud> listaResultado = new ArrayList<CitacaoDocJud>();
		List<CitacaoDocJud> citacoesFeitas = docJud.getCitacoesFeitas();
		/*
		 * Caso a data da referencia feita pelo documento juridico seja mais atual que a dataFim
		 * de vigencia das normas e dos elementosNorma, a referencia esta temporalmente inconsistente.
		 * Sao analisadas apenas as referencias feitas a normas e elementosnorma, pois outros documentos
		 * juridicos nao tem "vigencia"
		 */
		for( CitacaoDocJud citacao : citacoesFeitas ){
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
