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
import judlaw.model.bean.ref.Alteracao;
import judlaw.model.util.Constantes;

/**
 * Classe AlteracaoTimeLogic. Define a logica temporal das alteracoes feitas por normas e elementosNorma.
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
	 * Verifica se ha alguma inconsistencia temporal nas alteracoes feitas por um texto legal. Caso a data da referencia feita pela norma seja mais atual que a dataFim
	 * de vigencia das normas e dos elementosNorma, a referencia esta temporalmente inconsistente. Também
	 * são armazenados "warnings" caso o pai ou algum dos filhos do texto legal referenciado tenha sido
	 * atualizado.
	 * @param textoLegal
	 * @return Lista de alteracoes feitas que estao inconsistentes temporalmente
	 * @throws Exception
	 */
	public List<Alteracao> inconsistenciaTemporal(TextoLegal textoLegal) throws Exception {
		inicializaListas();
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
				/*
				 * Verificando os filhos: caso eles tenham uma data de publicacao mais recente que 
				 * a data de publicacao da norma, sao adicionados na lista
				 */
				for( ElementoNorma eleNorma : alteracao.getNormaDestino().getElementosNorma() ) {
					if( comparaDatas(eleNorma.getDataPublicacao(), 
									 alteracao.getNormaDestino().getDataPublicacao(), 
									 Constantes.DELIMITADOR_DATA) > 0) {
						getElementosNormaFilhosAtualizados().add( eleNorma );
						getElementosAtualizadosString().add("O elemento "+eleNorma.getIdentificadorUnico()+" possui uma data de publicacao mais atual: "+eleNorma.getDataPublicacao());
					}
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
				/*
				 * Verificando os pais
				 */
				if( alteracao.getElementoNormaDestino().getNormasPai().size() > 0 ) {
					for( Norma normaPai : alteracao.getElementoNormaDestino().getNormasPai() ) {
						if( comparaDatas(normaPai.getDataPublicacao(), 
								 		 alteracao.getElementoNormaDestino().getDataPublicacao(), 
								 		 Constantes.DELIMITADOR_DATA) > 0) {
							getNormasPaiAtualizados().add( normaPai );
							getElementosAtualizadosString().add("O elemento "+normaPai.getIdentificadorUnico()+" possui uma data de publicacao mais atual: "+normaPai.getDataPublicacao());
						}
					}
				} else {
					for( ElementoNorma eleNormaPai : alteracao.getElementoNormaDestino().getElementosNormaPai() ) {
						if( comparaDatas(eleNormaPai.getDataPublicacao(), 
								 		 alteracao.getElementoNormaDestino().getDataPublicacao(), 
								 		 Constantes.DELIMITADOR_DATA) > 0) {
							getElementosNormaPaiAtualizados().add( eleNormaPai );
							getElementosAtualizadosString().add("O elemento "+eleNormaPai.getIdentificadorUnico()+" possui uma data de publicacao mais atual: "+eleNormaPai.getDataPublicacao());
						}
					}
				}
				
				/*
				 * Verificando os filhos
				 */
				for( ElementoNorma eleNorma : alteracao.getElementoNormaDestino().getElementosNorma() ) {
					if( comparaDatas(eleNorma.getDataPublicacao(), 
									 alteracao.getElementoNormaDestino().getDataPublicacao(), 
									 Constantes.DELIMITADOR_DATA) > 0) {
						getElementosNormaFilhosAtualizados().add( eleNorma );
						getElementosAtualizadosString().add("O elemento "+eleNorma.getIdentificadorUnico()+" possui uma data de publicacao mais atual: "+eleNorma.getDataPublicacao());
					}
				}
			}
		}
		return listaResultado;
	}
	
	/**
     * Dada uma lista de alteracoes e uma data, sao retornadas aquelas que ainda estao validas
     * @param alteracoes
     * @param data
     * @return
     * @throws Exception
     */
    public List<Alteracao> alteracoesValidas(List<Alteracao> alteracoes,
    												 String data) throws Exception {
    	List<Alteracao> alteracoesValidas = new ArrayList<Alteracao>();
    	for(Alteracao alt : alteracoes) {
    		if( referenciaValida(alt, data) ) {
    			alteracoesValidas.add( alt );
    		}
    	}
    	return alteracoesValidas;
    }
}
