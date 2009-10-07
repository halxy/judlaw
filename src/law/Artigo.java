package law;

import java.util.List;

import referencia.Referencia;

public class Artigo extends ElementoNorma {

	public Artigo(int identificador, String texto, String data,
			String vigencia, List<Referencia> referenciasFeitas,
			List<Referencia> referenciasRecebidas) {
		super(identificador, texto, data, vigencia, referenciasFeitas,
				referenciasRecebidas);
		// TODO Auto-generated constructor stub
	}

}
