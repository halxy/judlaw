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
import judlaw.model.bean.docjud.Relatorio;
import judlaw.model.persistence.dbmanager.DBManager;

/**
 * Classe RelatorioManager - define as operacoes no BD dos Relatorios
 * @author Halley Freitas
 *
 */
public class RelatorioManager {

	private static RelatorioManager relatorioManager = null;
	private static DBManager dbManager = DBManager.getInstance();
	
	/**
	 * 
	 * @return
	 */
	public static RelatorioManager getInstance(){
        if(relatorioManager == null)
        	relatorioManager = new RelatorioManager();
        return relatorioManager;
    }
	
	@SuppressWarnings("unchecked")
	public List<Relatorio> getRelatorios() {
		return dbManager.selectAll( new Relatorio() );
	}
	
	public void removeRelatorios() {
		dbManager.removeAll( new Relatorio() );
	}
	
	/**
	 * Remove relatoro
	 * @param docJud
	 */
	public void removeRelatorio(DocumentoJuridico docJud) {
		Relatorio relatorio = docJud.getRelatorio();
		docJud.setRelatorio(null);
		dbManager.save(docJud);
		dbManager.remove(relatorio);
	}
	
	/**
	 * Altera um relatorio que ja esta inserido no Banco de Dados
	 * @param ementa
	 * @param docJud
	 */
	public void alteraRelatorioBD(Relatorio relatorio, DocumentoJuridico docJud) {
		Relatorio relatorioBD = docJud.getRelatorio();
		if(relatorioBD == null) {
			relatorioBD = new Relatorio();
		}
		relatorioBD.setTexto( relatorio.getTexto() );
		relatorioBD.setDocumentoJuridico(docJud);
		dbManager.save(relatorioBD);
		docJud.setRelatorio(relatorioBD);
		dbManager.save(docJud);
	}
	
	@SuppressWarnings("unchecked")
	public List<Relatorio> selectRelatorioPorAtributo(String atributo, String valor) {
		return dbManager.selectObjectsByField(new Relatorio(), atributo, valor);
	}
}
