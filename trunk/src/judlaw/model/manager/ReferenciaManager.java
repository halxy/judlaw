package judlaw.model.manager;

import java.util.List;

import judlaw.model.docjud.DocumentoJuridico;
import judlaw.model.lei.Norma;
import judlaw.model.ref.Referencia;

public class ReferenciaManager {
	
	private static ReferenciaManager referenciaManager = null;
	private static DBManager dbManager = DBManager.getInstance();
	
	
	   /**
	    * Retorna uma instancia da classe ReferenciaManager
	    * @return
	    */
	    public static ReferenciaManager getInstance(){
	        if(referenciaManager == null)
	        	referenciaManager = new ReferenciaManager();
	        return referenciaManager;
	    }
	    
		public void saveReferencia(Referencia referencia) {
			dbManager.save(referencia);
		}
	    
	    @SuppressWarnings("unchecked")
		public List<Referencia> getTodasReferencias(){
			return dbManager.selectAll( new Referencia() );
		}
	    
		public void removeTodasReferencias(){
			dbManager.removeAll( new Referencia() );
		}
		
		@SuppressWarnings("unchecked")
		public List<Referencia> recuperaReferenciaPorAtributo(String atributo, String valor) {
			return dbManager.selectObjectsByField(new Referencia(), atributo, valor);
		}
		
		public void saveReferencia(DocumentoJuridico docjud, Norma norma, String tipo, String efeito) {
			Referencia ref = new Referencia();
			ref.setOrigem( docjud.getCabecalho().getIdDocumento() );
			ref.setDestino( norma.getIdentificadorUnico() );
			ref.setTipo(tipo);
			ref.setEfeito(efeito);
		}
}
