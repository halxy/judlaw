package judlaw.model.manager;

public class ReferenciaManager {
	
	private static ReferenciaManager referenciaManager = null;
	
	
	   /**
	    * Retorna uma instancia da classe ReferenciaManager
	    * @return
	    */
	    public static ReferenciaManager getInstance(){
	        if(referenciaManager == null)
	        	referenciaManager = new ReferenciaManager();
	        return referenciaManager;
	    }
	    
	    
}
