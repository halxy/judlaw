/* 
 * Universidade Federal de Campina Grande
 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas	
 */
package judlaw.model.logic.time;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import judlaw.model.bean.law.ElementoNorma;
import judlaw.model.bean.law.Norma;
import judlaw.model.bean.ref.Referencia;
import judlaw.model.util.Constantes;

/**
 * Classe TimeLogic. Define a logica temporal geral do sistema
 * @author Halley Freitas
 *
 */
public class TimeLogic {

	private static TimeLogic timeLogic = null;
	/*
	 * Atributos utilizados pelos algoritmos de verificacao da consistencia temporal
	 */
	private List<Norma> normasPaiAtualizados = new ArrayList<Norma>();
	private List<ElementoNorma> elementosNormaPaiAtualizados = new ArrayList<ElementoNorma>();
	private List<ElementoNorma> elementosNormaFilhosAtualizados = new ArrayList<ElementoNorma>();
	private List<String> elementosAtualizadosString;
	
	/**
	 * Singleton
	 * @return
	 */
	public static TimeLogic getInstance() {
		if (timeLogic == null) {
			timeLogic = new TimeLogic();
		}
		return timeLogic;
	}
	
	public List<Norma> getNormasPaiAtualizados() {
		return normasPaiAtualizados;
	}

	public void setNormasPaiAtualizados(List<Norma> normasPaiAtualizados) {
		this.normasPaiAtualizados = normasPaiAtualizados;
	}

	public List<ElementoNorma> getElementosNormaPaiAtualizados() {
		return elementosNormaPaiAtualizados;
	}

	public void setElementosNormaPaiAtualizados(
			List<ElementoNorma> elementosNormaPaiAtualizados) {
		this.elementosNormaPaiAtualizados = elementosNormaPaiAtualizados;
	}

	public List<ElementoNorma> getElementosNormaFilhosAtualizados() {
		return elementosNormaFilhosAtualizados;
	}

	public void setElementosNormaFilhosAtualizados(
			List<ElementoNorma> elementosNormaFilhosAtualizados) {
		this.elementosNormaFilhosAtualizados = elementosNormaFilhosAtualizados;
	}

	public List<String> getElementosAtualizadosString() {
		return elementosAtualizadosString;
	}

	public void setElementosAtualizadosString(
			List<String> elementosAtualizadosString) {
		this.elementosAtualizadosString = elementosAtualizadosString;
	}
	
	/**
	 * Retorna a data mais atual
	 * @param data1 - Modelo: dd/MM/yyyy
	 * @param data2 - Modelo: dd/MM/yyyy
	 * @param delimitador - Pode ser "/", "-", etc;
	 * @return 0 caso as datas sejam iguais, 1 caso a data1 seja mais atual e -1 caso a data2 seja mais atual.
	 * @throws Exception 
	 */
	public int comparaDatas(String data1, String data2, String delimitador) throws Exception {
		// Manipulando a primeira data
		StringTokenizer token1 = new StringTokenizer(data1, delimitador);
		String dia1, mes1, ano1;
		try{
			dia1 = token1.nextToken();
			mes1 = token1.nextToken();
			ano1 = token1.nextToken();
		} catch (Exception e) {
			throw new NoSuchElementException("Missing values");
		}
		int dataInt1 = Integer.parseInt(ano1+mes1+dia1);

		// Manipulando a segunda data
		StringTokenizer token2 = new StringTokenizer(data2, delimitador);
		String dia2, mes2, ano2;
		try{
			dia2 = token2.nextToken();
			mes2 = token2.nextToken();
			ano2 = token2.nextToken();
		} catch (Exception e) {
			throw new NoSuchElementException("Missing values");
		}
		int dataInt2 = Integer.parseInt(ano2+mes2+dia2);
	
		// Caso a primeira 
		if (dataInt1 > dataInt2) {
			return 1;
		} else if (dataInt2 > dataInt1) {
			return -1;
		}
		return 0;
	}
	
	/**
	 * Retorna se uma data esta contida em um intervalo
	 * @param data
	 * @param delimitadorData
	 * @param intervalo
	 * @param delimitadorIntervalo
	 * @return true, caso a data esteja contida no intervalo. false, caso contrario.
	 * @throws Exception Caso alguma das datas envolvidas esteja mal-formatada
	 */
	public boolean dataContidaEmIntervalo(String data, String delimitadorData, String intervalo, 
			                               String delimitadorIntervalo) throws Exception {
		StringTokenizer tokenIntervalo = new StringTokenizer(intervalo, delimitadorIntervalo);
		String dataInicioIntervalo = tokenIntervalo.nextToken();
		String dataFimIntervalo = tokenIntervalo.nextToken();
		if( (comparaDatas(data, dataInicioIntervalo, delimitadorData) >= 0) && 
			(comparaDatas(data, dataFimIntervalo, delimitadorData) <= 0) ) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * Compara os intervalos e retorna qual a mais atual
	 * @param intervalo1
	 * @param intervalo2
	 * @param delimitadorIntervalos
	 * @param delimitadorDatas
	 * @return 1 se a Intervalo1 tiver uma data fim maior, 0 se as datas fim forem iguais e -1 caso
	 * a data fim da Intervalo2 for maior.
	 * @throws Exception Caso as datas dos parametros fimIntervalo1 e fimIntervalo2 estejam mal-formatadas
	 */
	public int comparaIntervalos(String intervalo1, String intervalo2, 
								String delimitadorIntervalos, String delimitadorDatas) throws Exception {
		/*
		 * Modelo de intervalo esperada:
		 * dd1/mm1/yyyy1-dd2/mm2/yyyy2
		 */
		//FimIntervalo1
		StringTokenizer tokenIntervalo = new StringTokenizer(intervalo1, delimitadorIntervalos);
		tokenIntervalo.nextToken(); // passando o primeiro token referente � data inicio da intervalo
		String fimIntervalo1 = tokenIntervalo.nextToken();
		//FimIntervalo2
		StringTokenizer tokenIntervalo2 = new StringTokenizer(intervalo2, delimitadorIntervalos);
		tokenIntervalo2.nextToken(); // passando o primeiro token referente � data inicio da intervalo
		String fimIntervalo2 = tokenIntervalo2.nextToken();
		return comparaDatas(fimIntervalo1, fimIntervalo2, delimitadorDatas);
	}
	
	/**
	 * A dataFim da intervalo passada como parametro eh modificada para uma nova data
	 * @param intervalo
	 * @param novaDataFim
	 * @return
	 */
	public String novaDataFimIntervalo(String intervalo, String novaDataFim) {
		StringTokenizer tokenIntervalo = new StringTokenizer(intervalo, Constantes.DELIMITADOR_INTERVALO);
		String dataInicio = tokenIntervalo.nextToken();
		return dataInicio +"-"+ novaDataFim;
	}
	
	/**
	 * Passada uma data (dd/mm/yyyy) como parametro, eh retornado o dia anterior � data
	 * @param data
	 * @return data com um dia anterior � data de publicacao nova
	 */
	public String diaAnterior(String data) {
		StringTokenizer tokenData = new StringTokenizer(data, Constantes.DELIMITADOR_DATA);
		//Dia
		String dia = tokenData.nextToken();
		int diaInt = Integer.parseInt( dia );
		//Mes
		String mes = tokenData.nextToken();
		int mesInt = Integer.parseInt( mes );
		//Ano
		String ano = tokenData.nextToken();
		int anoInt = Integer.parseInt( ano );
		//Setando o dia
		if ( diaInt == 1 ) { 
			if(mesInt == 1 || mesInt == 2 || mesInt == 4 || mesInt == 6 || mesInt == 8 || 
			   mesInt == 9 || mesInt == 11) {
				diaInt = 31;
			} else if( mesInt == 3 ) {
				if( anoBissexto( anoInt ) ) {
					diaInt = 29;
				} else {
					diaInt = 28;
				}
			} else {
				diaInt = 30;
			}
			//Setando o mes e o ano
			if( mesInt == 1) {
				mesInt = 12;
				anoInt = anoInt-1;
			} else {
				mesInt = mesInt-1;
			}
		} else {
			diaInt = diaInt-1;
		}
		//Organizando as strings do dia e do mes
		if(diaInt < 10) {
			dia = "0" + diaInt;
		} else {
			dia = String.valueOf(diaInt);
		}
		if(mesInt < 10) {
			mes = "0" + mesInt;
		} else {
			mes = String.valueOf(mesInt);
		}
		String diaAnterior = dia + Constantes.DELIMITADOR_DATA + mes + Constantes.DELIMITADOR_DATA + anoInt;
		return diaAnterior;
	}

	/*
	 * Retorna se um ano � bissexto
	 */
	private boolean anoBissexto(int ano) {
		return new GregorianCalendar().isLeapYear(ano);
	}
	
	/**
	 * Recebe o mes na forma de uma String (eg. Novembro) e retorna sua forma numeral (eg. 11)
	 * @param mes
	 * @return
	 */
	public int mesStringToInt(String mes) {
		if( mes.equalsIgnoreCase("janeiro") ) {
			return 1;
		} else if ( mes.equalsIgnoreCase("fevereiro") ) {
			return 2;
		} else if ( mes.equalsIgnoreCase("mar�o") || mes.equalsIgnoreCase("marco") ) {
			return 3;
		} else if ( mes.equalsIgnoreCase("abril") ) {
			return 4;
		} else if ( mes.equalsIgnoreCase("maio") ) {
			return 5;
		} else if ( mes.equalsIgnoreCase("junho") ) {
			return 6;
		} else if ( mes.equalsIgnoreCase("julho") ) {
			return 7;
		} else if ( mes.equalsIgnoreCase("agosto") ) {
			return 8;
		} else if ( mes.equalsIgnoreCase("setembro") ) {
			return 9;
		} else if ( mes.equalsIgnoreCase("outubro") ) {
			return 10;
		} else if ( mes.equalsIgnoreCase("novembro") ) {
			return 11;
		} else { //Dezembro
			return 12;
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
}
