package judlaw.model.bean;

/**
 * Classe Abstrata Documento - Superclasse dos documentos jurídicos e legais
 * @author Halley Freitas
 *
 */
public abstract class Documento {
	
	private String identificadorUnico; // ex: cp_art120; lei1234; cc_art1_par2.
	private String dataPublicacao; // dd-MM-yyy
	
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
