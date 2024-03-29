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
 * Classe CitacaoDocJudTimeLogic. Define a logica temporal das citacoes feitas por documentos juridicos.
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
		setElementosAtualizadosString( new ArrayList<String>() );
	}
	
	/**
	 * Dado um documentoJuridico, retorna as citacoes temporalmente inconsistentes feitas por ele. Tamb�m
	 * s�o armazenados "warnings" caso o pai ou algum dos filhos do texto legal referenciado tenha sido
	 * atualizado.
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
				if ( !dataContidaEmIntervalo(citacao.getData(), Constantes.DELIMITADOR_DATA, 
						citacao.getNormaDestino().getVigencia(), Constantes.DELIMITADOR_INTERVALO) ) { /* Caso for < 0, a data da ref eh mais atual
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
				if ( !dataContidaEmIntervalo(citacao.getData(), Constantes.DELIMITADOR_DATA, 
						citacao.getElementoNormaDestino().getVigencia(), Constantes.DELIMITADOR_INTERVALO) ) { /* Caso for < 0, a data da ref eh mais atual
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
	
	/**
     * Dada uma lista de citacoesDocJud e uma data, sao retornadas aquelas que ainda estao validas
     * @param citacoesDJ
     * @param data
     * @return Lista de cita��esDocJud que possuem data igual ou superior � data passada como par�metro
     * @throws Exception
     */
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
}
