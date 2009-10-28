/* 
 * Universidade Federal de Campina Grande
 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Informatica
 * Sistema JudLaw
 * Mestrando: Halley Freitas
 * 		
 */
package judlaw.controller;


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
