package judlaw.model.dbmanager.docjud;

import java.util.List;

import judlaw.model.bean.docjud.DocumentoJuridico;
import judlaw.model.bean.docjud.Voto;
import judlaw.model.dbmanager.DBManager;

public class VotoManager {

	private static VotoManager votoManager = null;
	private static DBManager dbManager = DBManager.getInstance();
	
	/**
	 * 
	 * @return
	 */
	public static VotoManager getInstance(){
        if(votoManager == null)
        	votoManager = new VotoManager();
        return votoManager;
    }
	
	@SuppressWarnings("unchecked")
	public List<Voto> getVotos() {
		return dbManager.selectAll( new Voto() );
	}
	
	public void removeVotos() {
		dbManager.removeAll( new Voto() );
	}
	
	public void adicionaVoto(Voto voto, DocumentoJuridico docJud) {
		List<Voto> votos = docJud.getVotos();
		votos.add(voto);
		dbManager.save( docJud );
		voto.setDocumentoJuridico(docJud);
		dbManager.save(voto);
	}
	
	public void removeVoto(Voto voto, DocumentoJuridico docJud) {
		List<Voto> votos = docJud.getVotos();
		votos.remove( voto );
		dbManager.save( docJud ); 
		dbManager.remove( voto );
	}
	
	public void alteraVotoBD(Voto votoLista, Voto newVoto, DocumentoJuridico docJud) {
		List<Voto> votos = docJud.getVotos();
		votos.get( votos.indexOf(votoLista) ).setTexto( newVoto.getTexto() );
		dbManager.save( docJud ); 
	}
	
	@SuppressWarnings("unchecked")
	public List<Voto> selectVotoPorAtributo(String atributo, String valor) {
		return dbManager.selectObjectsByField(new Voto(), atributo, valor);
	}
}
