/* 
 * Universidade Federal de Campina Grande
 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas	
 */
package judlaw.model.persistence.dbmanager.docjud;

import java.util.List;

import judlaw.model.bean.docjud.Cabecalho;
import judlaw.model.bean.docjud.DocumentoJuridico;
import judlaw.model.persistence.dbmanager.DBManager;

/**
 * Classe CabecalhoManager - define as operacoes no BD dos Cabecalhos
 * @author Halley Freitas
 *
 */
public class CabecalhoManager {

	private static CabecalhoManager cabecalhoManager = null;
	private static DBManager dbManager = DBManager.getInstance();
	
	/**
	 * 
	 * @return
	 */
	public static CabecalhoManager getInstance(){
        if(cabecalhoManager == null)
        	cabecalhoManager = new CabecalhoManager();
        return cabecalhoManager;
    }
	
	@SuppressWarnings("unchecked")
	public List<Cabecalho> getCabecalhos() {
		return dbManager.selectAll( new Cabecalho() );
	}
	
	public void removeCabecalhos() {
		dbManager.removeAll( new Cabecalho() );
	}
	
	/**
	 * Altera um cabecalho que ja esta inserido no Banco de Dados
	 * @param cabecalho
	 * @param docJud
	 */
	public void alteraCabecalhoBD(Cabecalho cabecalho, DocumentoJuridico docJud) {
		Cabecalho cabecalhoBD = docJud.getCabecalho();
		if(cabecalhoBD == null) {
			cabecalhoBD = new Cabecalho();
		}
		cabecalhoBD.setCodRegistro( cabecalho.getCodRegistro() );
		cabecalhoBD.setOrgaoJulgador( cabecalho.getOrgaoJulgador() );
		cabecalhoBD.setTribunal( cabecalho.getTribunal() );
		cabecalhoBD.setDocumentoJuridico(docJud);
		dbManager.save(cabecalhoBD);
		docJud.setCabecalho(cabecalhoBD);
		dbManager.save(docJud);
	}
	
	/**
	 * Remove o cabecalho de um documentoJuridico
	 * @param docJud
	 */
	public void removeCabecalho(DocumentoJuridico docJud) {
		Cabecalho cabecalho = docJud.getCabecalho();
		docJud.setCabecalho( null );
		dbManager.save(docJud);
		dbManager.remove(cabecalho);
	}
	
	@SuppressWarnings("unchecked")
	public List<Cabecalho> selectCabecalhoPorAtributo(String atributo, String valor) {
		return dbManager.selectObjectsByField(new Cabecalho(), atributo, valor);
	}
}
