/* 
 * Universidade Federal de Campina Grande
 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas	
 */
package judlaw.model.logic;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * Classe TimeLogic - define a logica temporal geral do sistema
 * @author Halley Freitas
 *
 */
public class TimeLogic {

	private static TimeLogic timeLogic = null;
	
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
	 * Compara uma vigencia com uma data para saber se o prazo da vigencia é mais atual que
	 * a data
	 * @param vigencia
	 * @param delimitadorVigencia
	 * @param data
	 * @param delimitador
	 * @return 0 ou 1 Caso a vigencia esteja valida em comparacao ao parametro data, -1 caso a vigencia
	 * ja nao seja mais valida neste mesma data.
	 * @throws Exception Caso as datas dos parametros fimVigencia e data estejam mal-formatadas
	 */
	public int comparaVigenciaComData(String vigencia, String delimitadorVigencia, 
			                          String data, String delimitadorData) throws Exception {
		/*
		 * Modelo de vigencia esperada:
		 * dd1/mm1/yyyy1-dd2/mm2/yyyy2
		 */
		StringTokenizer tokenVigencia = new StringTokenizer(vigencia, delimitadorVigencia);
		tokenVigencia.nextToken(); // passando o primeiro token referente à data inicio da vigencia
		String fimVigencia = tokenVigencia.nextToken();
		return comparaDatas(fimVigencia, data, delimitadorData);
	}
	
	/**
	 * Compara as vigencias e retorna qual a mais atual
	 * @param vigencia1
	 * @param vigencia2
	 * @param delimitadorVigencias
	 * @param delimitadorDatas
	 * @return 1 se a Vigencia1 tiver uma data fim maior, 0 se as datas fim forem iguais e -1 caso
	 * a data fim da Vigencia2 for maior.
	 * @throws Exception Caso as datas dos parametros fimVigencia1 e fimVigencia2 estejam mal-formatadas
	 */
	public int comparaVigencias(String vigencia1, String vigencia2, 
								String delimitadorVigencias, String delimitadorDatas) throws Exception {
		/*
		 * Modelo de vigencia esperada:
		 * dd1/mm1/yyyy1-dd2/mm2/yyyy2
		 */
		//FimVigencia1
		StringTokenizer tokenVigencia = new StringTokenizer(vigencia1, delimitadorVigencias);
		tokenVigencia.nextToken(); // passando o primeiro token referente à data inicio da vigencia
		String fimVigencia1 = tokenVigencia.nextToken();
		//FimVigencia2
		StringTokenizer tokenVigencia2 = new StringTokenizer(vigencia2, delimitadorVigencias);
		tokenVigencia2.nextToken(); // passando o primeiro token referente à data inicio da vigencia
		String fimVigencia2 = tokenVigencia2.nextToken();
		return comparaDatas(fimVigencia1, fimVigencia2, delimitadorDatas);
	}
}
