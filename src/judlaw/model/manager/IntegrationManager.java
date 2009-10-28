package judlaw.model.manager;

/**
 * Classe responsavel pelas operacoes de integracao entre documentos juridicos e dispositivos legais
 * @author Halley Freitas
 *
 */
public class IntegrationManager {

	private static IntegrationManager integrationManager = null;
	
	/**
	 * Retorna uma instancia do IntegrationManager
	 * @return
	 */
	public static IntegrationManager getInstance() {
		if (integrationManager == null) {
			integrationManager = new IntegrationManager();
		}
		return integrationManager;
	}
}
