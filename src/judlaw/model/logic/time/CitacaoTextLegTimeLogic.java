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
	
	/*
	 * Inicializa as listas a serem utilizadas na refinacao da inconsistencia temporal
	 */
	private void inicializaListas() {
		setNormasPaiAtualizados( new ArrayList<Norma>() );
		setElementosNormaPaiAtualizados( new ArrayList<ElementoNorma>() );
		setElementosNormaFilhosAtualizados( new ArrayList<ElementoNorma>() );
		setElementosAtualizadosString( new ArrayList<String>() );
	}
	
	/**
	 * Verifica as inconsistencias temporais de citacoes feitas por um texto legal. Também
	 * são armazenados "warnings" caso o pai ou algum dos filhos do texto legal referenciado tenha sido
	 * atualizado.
	 * @param textoLegal
	 * @return
	 * @throws Exception
	 */
	public List<CitacaoTextLeg> inconsistenciaTemporal(TextoLegal textoLegal) throws Exception {
		inicializaListas();
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
				/*
				 * Verificando os filhos: caso eles tenham uma data de publicacao mais recente que 
				 * a data de publicacao da norma, sao adicionados na lista
				 */
				for( ElementoNorma eleNorma : citacao.getNormaDestino().getElementosNorma() ) {
					if( comparaDatas(eleNorma.getDataPublicacao(), 
									 citacao.getNormaDestino().getDataPublicacao(), 
									 Constantes.DELIMITADOR_DATA) > 0) {
						getElementosNormaFilhosAtualizados().add( eleNorma );
						getElementosAtualizadosString().add("O elemento "+eleNorma.getIdentificadorUnico()+" possui uma data de publicacao mais atual: "+eleNorma.getDataPublicacao());
					}
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
				/*
				 * Verificando os pais
				 */
				if( citacao.getElementoNormaDestino().getNormasPai().size() > 0 ) {
					for( Norma normaPai : citacao.getElementoNormaDestino().getNormasPai() ) {
						if( comparaDatas(normaPai.getDataPublicacao(), 
										 citacao.getElementoNormaDestino().getDataPublicacao(), 
								 		 Constantes.DELIMITADOR_DATA) > 0) {
							getNormasPaiAtualizados().add( normaPai );
							getElementosAtualizadosString().add("O elemento "+normaPai.getIdentificadorUnico()+" possui uma data de publicacao mais atual: "+normaPai.getDataPublicacao());
						}
					}
				} else {
					for( ElementoNorma eleNormaPai : citacao.getElementoNormaDestino().getElementosNormaPai() ) {
						if( comparaDatas(eleNormaPai.getDataPublicacao(), 
										 citacao.getElementoNormaDestino().getDataPublicacao(), 
								 		 Constantes.DELIMITADOR_DATA) > 0) {
							getElementosNormaPaiAtualizados().add( eleNormaPai );
							getElementosAtualizadosString().add("O elemento "+eleNormaPai.getIdentificadorUnico()+" possui uma data de publicacao mais atual: "+eleNormaPai.getDataPublicacao());
						}
					}
				}
				
				/*
				 * Verificando os filhos
				 */
				for( ElementoNorma eleNorma : citacao.getElementoNormaDestino().getElementosNorma() ) {
					if( comparaDatas(eleNorma.getDataPublicacao(), 
									 citacao.getElementoNormaDestino().getDataPublicacao(), 
									 Constantes.DELIMITADOR_DATA) > 0) {
						getElementosNormaFilhosAtualizados().add( eleNorma );
						getElementosAtualizadosString().add("O elemento "+eleNorma.getIdentificadorUnico()+" possui uma data de publicacao mais atual: "+eleNorma.getDataPublicacao());
					}
				}
			}
		}
		return listaResultado;
	}
}
