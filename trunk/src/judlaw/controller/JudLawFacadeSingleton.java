/* 
 * Universidade Federal de Campina Grande
 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Informatica
 * Sistema JudLaw
 * Mestrando: Halley Freitas
 * 		
 */
package judlaw.controller;

/**
 * Classe JudLawFacadeSingleton. 
 * Retorna uma instância da classe JudLawFacade
 * @author Halley Freitas
 *
 */
public class JudLawFacadeSingleton {
	
	private static JudLawFacade facade = null;
	
	/**
	 * Private Constructor.
	 */
	private JudLawFacadeSingleton() {
	}
	
	/**
	 * Retorna uma instancia da JudLawFacade.
	 * @return Retorna uma instancia da JudLawFacade.
	 */
	public static JudLawFacade getInstance() {
		if (facade == null) {
			facade = new JudLawFacade();
		}
		return facade;
	}
	
}
