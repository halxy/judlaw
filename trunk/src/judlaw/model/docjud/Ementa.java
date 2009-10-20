/* 
 * Universidade Federal de Campina Grande

 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Informatica
 * Sistema JudLaw
 * Mestrando: Halley Freitas
 * 		
 */
package judlaw.model.docjud;

import java.util.ArrayList;
import java.util.List;

import judlaw.model.reference.Referencia;

public class Ementa {

	private String texto;
	private List<Referencia> referenciasLegislativas; //MODIFICAR TODO

	public Ementa() {
		inicializaListas();
	}
	
	private void inicializaListas(){
		this.referenciasLegislativas = new ArrayList<Referencia>();
	}
	
	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public List<Referencia> getReferenciasLegislativas() {
		return referenciasLegislativas;
	}

	public void setReferenciasLegislativas(List<Referencia> referenciasLegislativas) {
		this.referenciasLegislativas = referenciasLegislativas;
	}
}
