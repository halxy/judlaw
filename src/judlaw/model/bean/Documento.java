package judlaw.model.bean;

/**
 * Classe Abstrata Documento - Superclasse dos documentos jurídicos e legais
 * @author Halley Freitas
 *
 */
public abstract class Documento {
	
	private String identificadorUnico;
	private String dataPublicacao;
	
	public String getIdentificadorUnico() {
		return identificadorUnico;
	}
	
	public void setIdentificadorUnico(String identificadorUnico) {
		this.identificadorUnico = identificadorUnico;
	}
	
	public String getDataPublicacao() {
		return dataPublicacao;
	}
	
	public void setDataPublicacao(String dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}
}
