package judlaw.model.docjud;

import java.util.List;


public class DocumentoJuridico {

	private Ementa ementa;
	private Cabecalho cabecalho;
	private List<Voto> votos;
	
	public Ementa getEmenta() {
		return ementa;
	}
	
	public void setEmenta(Ementa ementa) {
		this.ementa = ementa;
	}
	
	public Cabecalho getCabecalho() {
		return cabecalho;
	}
	
	public void setCabecalho(Cabecalho cabecalho) {
		this.cabecalho = cabecalho;
	}
	
	public List<Voto> getVotos() {
		return votos;
	}
	
	public void setVotos(List<Voto> votos) {
		this.votos = votos;
	}
	

}
