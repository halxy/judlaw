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
	
	//Lista de Excecoes a serem utilizadas na busca complexa por inconsistencias temporais
	private List<Exception> inconsistenciasTemporais = new ArrayList<Exception>();
	
	private static DocJudTimeLogic docJudTimeLogic = null;
	
	/**
	 * Singleton
	 * @return
	 */
	public static DocJudTimeLogic getInstance() {
		if (docJudTimeLogic == null) {
			docJudTimeLogic = new DocJudTimeLogic();
		}
		return docJudTimeLogic;
	}
	
	public List<Exception> getInconsistenciasTemporais() {
		return inconsistenciasTemporais;
	}

	public void setInconsistenciasTemporais(List<Exception> inconsistenciasTemporais) {
		this.inconsistenciasTemporais = inconsistenciasTemporais;
	}
	
	/**
	 * Dado um documentoJuridico, retorna as citacoes temporalmente inconsistentes feitas por ele.
	 * @param docJud
	 * @return Citacoes temporalmente inconsistentes feitas por ele.
	 * @throws Exception
	 */
	public List<CitacaoDocJud> inconsistenciaTemporalSimples(DocumentoJuridico docJud) throws Exception {
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
