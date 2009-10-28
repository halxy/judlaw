package judlaw.model.lei;

import judlaw.model.util.TimeManager;

/**
 * Classe respons�vel por gerenciar o m�dulo das leis
 * @author Halley Freitas
 *
 */
public class LawManager {
	
	private static LawManager lawManager = null;
	private TimeManager timeManager = TimeManager.getInstance();
	
	
   /**
    * Retorna uma instancia da classe LawManager
    * @return
    */
    public static LawManager getInstance(){
        if(lawManager == null)
        	lawManager = new LawManager();
        return lawManager;
    }
}
