/* 
 * Universidade Federal de Campina Grande
 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas	
 */
package judlaw.model.dbmanager.docjud;

import java.util.List;

import judlaw.model.bean.docjud.DocumentoJuridico;
import judlaw.model.bean.docjud.Parte;
import judlaw.model.dbmanager.DBManager;

/**
 * Classe ParteManager - define as operacoes no BD das Partes
 * @author Halley Freitas
 *
 */
public class ParteManager {

	private static ParteManager parteManager = null;
	private static DBManager dbManager = DBManager.getInstance();
	
	/**
	 * 
	 * @return
	 */
	public static ParteManager getInstance(){
        if(parteManager == null)
        	parteManager = new ParteManager();
        return parteManager;
    }
	
	@SuppressWarnings("unchecked")
	public List<Parte> getPartes() {
		return dbManager.selectAll( new Parte() );
	}
	
	public void removePartes() {
		dbManager.removeAll( new Parte() );
	}
	
	public void adicionaParte(Parte parte, DocumentoJuridico docJud) {
		List<Parte> partes = docJud.getPartes();
		partes.add(parte);
		dbManager.save( docJud );
		parte.getDocumentosJuridicos().add(docJud);
		dbManager.save(parte);
	}
	
	public void removeParte(Parte parte, DocumentoJuridico docJud) {
		List<Parte> partes = docJud.getPartes();
		partes.remove( parte );
		dbManager.save( docJud ); 
		dbManager.remove( parte );
	}
	
	public void alteraParteBD(Parte parteLista, Parte newParte, DocumentoJuridico docJud) {
		List<Parte> partes = docJud.getPartes();
		partes.get( partes.indexOf(parteLista) ).setNome( newParte.getNome() );
		partes.get( partes.indexOf(parteLista) ).setTitulo( newParte.getTitulo() );
		dbManager.save( docJud ); 
	}
	
	@SuppressWarnings("unchecked")
	public List<Parte> selectPartePorAtributo(String atributo, String valor) {
		return dbManager.selectObjectsByField(new Parte(), atributo, valor);
	}
}
