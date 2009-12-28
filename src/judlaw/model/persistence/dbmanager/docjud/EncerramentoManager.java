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
import judlaw.model.bean.docjud.Encerramento;
import judlaw.model.persistence.dbmanager.DBManager;

/**
 * Classe EncerramentoManager - define as operacoes no BD dos Encerramentos
 * @author Halley Freitas
 *
 */
public class EncerramentoManager {

	private static EncerramentoManager encerramentoManager = null;
	private static DBManager dbManager = DBManager.getInstance();
	
	/**
	 * 
	 * @return
	 */
	public static EncerramentoManager getInstance(){
        if(encerramentoManager == null)
        	encerramentoManager = new EncerramentoManager();
        return encerramentoManager;
    }
	
	@SuppressWarnings("unchecked")
	public List<Encerramento> getEncerramentos() {
		return dbManager.selectAll( new Encerramento() );
	}
	
	public void removeEncerramentos() {
		dbManager.removeAll( new Encerramento() );
	}
	
	/**
	 * Remove encerramento
	 * @param docJud
	 */
	public void removeEncerramento(DocumentoJuridico docJud) {
		Encerramento encerramento = docJud.getEncerramento();
		docJud.setEncerramento(null);
		dbManager.save(docJud);
		dbManager.remove(encerramento);
	}
	
	/**
	 * Altera um encerramento que ja esta inserido no Banco de Dados
	 * @param encerramento
	 * @param docJud
	 */
	public void alteraEncerramentoBD(Encerramento encerramento, DocumentoJuridico docJud) {
		Encerramento encerramentoBD = docJud.getEncerramento();
		if(encerramentoBD == null) {
			encerramentoBD = new Encerramento();
		}
		encerramentoBD.setAcordao( encerramento.getAcordao() );
		encerramentoBD.setDecisao( encerramento.getDecisao() );
		encerramentoBD.setOrgaoJulgador( encerramento.getOrgaoJulgador() );
		encerramentoBD.setDocumentoJuridico(docJud);
		dbManager.save(encerramentoBD);
		docJud.setEncerramento(encerramentoBD);
		dbManager.save(docJud);
	}
	
	@SuppressWarnings("unchecked")
	public List<Encerramento> selectEncerramentoPorAtributo(String atributo, String valor) {
		return dbManager.selectObjectsByField(new Encerramento(), atributo, valor);
	}
}
