package judlaw.model.manager;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * Classe que manipula o tempo;
 * @author Halley Freitas
 *
 */
public class TimeManager {

	private static TimeManager timeManager = null;
	
	/**
	 * 
	 * @return
	 */
	public static TimeManager getInstance() {
		if (timeManager == null) {
			timeManager = new TimeManager();
		}
		return timeManager;
	}
	
	/**
	 * Verifica se a data não possui valores absurdos.
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	public boolean verificaData(String dia, String mes, String ano) throws Exception {
		// Verifica o dia
		int diaInt = Integer.parseInt(dia);
		if (diaInt > 31 || diaInt < 1) {
			throw new Exception("Dia invalido");
		}
		
		// Verifica o mes
		int mesInt = Integer.parseInt(mes);
		if (mesInt > 12 || mesInt < 1) {
			throw new Exception("Mes invalido");
		}
		
		// Verifica o ano
		int anoInt = Integer.parseInt(ano);
		if (anoInt < 1) {
			throw new Exception("Ano invalido");
		}
		return true;
	}
	
	/**
	 * Retorna a data mais atual
	 * @param data1 - Modelo: dd/MM/yyyy
	 * @param data2 - Modelo: dd/MM/yyyy
	 * @param delimitador - Pode ser "/", "-", etc;
	 * @return 0 caso as datas sejam iguais, 1 caso a data1 seja mais atual e 2 caso a data2 seja mais atual.
	 * @throws Exception 
	 */
	public int dataMaisAtual(String data1, String data2, String delimitador) throws Exception {
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
		// Verifica se a data esta OK e transforma para o valor em INT.
		verificaData(dia1, mes1, ano1);
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
			return 2;
		}
		return 0;
	}
}
