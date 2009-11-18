/* 
 * Universidade Federal de Campina Grande
 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas	
 */
package judlaw.controller;


/**
 * Facade JudLawFacade - facade do sistema
 * @author Halley Freitas
 *
 */
public class JudLawFacade {
	
	private static JudLawFacade facade = null;
	
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
