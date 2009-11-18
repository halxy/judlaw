/* 
 * Universidade Federal de Campina Grande
 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas	
 */
package judlaw.controller;

import judlaw.model.dbmanager.LawManager;


public class JudLawFacade {
	
	private static JudLawFacade facade = null;
	private LawManager lawManager = LawManager.getInstance();
	
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
	
	/* ------------------------------------------------------------------ */
    /* -------------------- OPERACOES ELEMENTO NORMA -------------------- */
    /* ------------------------------------------------------------------ */
	/**
     * Remove todos os elementosNorma;
     */
    public void removeElementosNorma() {
    	lawManager.removeElementosNorma();
    }
}
