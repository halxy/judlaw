/* 
 * Universidade Federal de Campina Grande
 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas	
 */
package judlaw.model.persistence.dbmanager.docjud;

import java.util.List;

import judlaw.model.bean.docjud.DocumentoJuridico;
import judlaw.model.bean.docjud.Ementa;
import judlaw.model.persistence.dbmanager.DBManager;

/**
 * Classe EmentaManager. Define as operacoes no BD das Ementas
 * @author Halley Freitas
 *
 */
public class EmentaManager {

	private static EmentaManager ementaManager = null;
	private static DBManager dbManager = DBManager.getInstance();
	
	/**
	 * 
	 * @return
	 */
	public static EmentaManager getInstance(){
        if(ementaManager == null)
        	ementaManager = new EmentaManager();
        return ementaManager;
    }
	
	@SuppressWarnings("unchecked")
	public List<Ementa> getEmentas() {
		return dbManager.selectAll( new Ementa() );
	}
	
	public void removeEmentas() {
		dbManager.removeAll( new Ementa() );
	}
	
	/**
	 * Remove ementa
	 * @param docJud
	 */
	public void removeEmenta(DocumentoJuridico docJud) {
		Ementa ementa = docJud.getEmenta();
		docJud.setEmenta(null);
		dbManager.save(docJud);
		dbManager.remove(ementa);
	}
	
	/**
	 * Altera uma ementa que ja esta inserida no Banco de Dados
	 * @param ementa
	 * @param docJud
	 */
	public void alteraEmentaBD(Ementa ementa, DocumentoJuridico docJud) {
		Ementa ementaBD = docJud.getEmenta();
		if(ementaBD == null) {
			ementaBD = new Ementa();
		}
		ementaBD.setTexto( ementa.getTexto() );
		ementaBD.setDocumentoJuridico(docJud);
		dbManager.save(ementaBD);
		docJud.setEmenta(ementaBD);
		dbManager.save(docJud);
	}
	
	@SuppressWarnings("unchecked")
	public List<Ementa> selectEmentaPorAtributo(String atributo, String valor) {
		return dbManager.selectObjectsByField(new Ementa(), atributo, valor);
	}
}
