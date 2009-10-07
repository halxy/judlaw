package law;

import java.util.List;

import referencia.Referencia;

public class Artigo extends ElementoNorma {

	public Artigo(int identificador, String texto, String data,
			String vigencia, List<Referencia> referenciasFeitas,
			List<Referencia> referenciasRecebidas, String elementoPai) {
		super(identificador, texto, data, vigencia, referenciasFeitas,
				referenciasRecebidas, elementoPai);
		// TODO Auto-generated constructor stub
	}

	private String caput;
	


	public String getCaput() {
		return caput;
	}

	public void setCaput(String caput) {
		this.caput = caput;
	}
	
}
