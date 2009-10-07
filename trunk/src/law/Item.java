package law;

import java.util.List;

import referencia.Referencia;

public class Item  extends ElementoNorma {

	public Item(int identificador, String texto, String data, String vigencia,
			List<Referencia> referenciasFeitas,
			List<Referencia> referenciasRecebidas, String elementoPai) {
		super(identificador, texto, data, vigencia, referenciasFeitas,
				referenciasRecebidas, elementoPai);
		// TODO Auto-generated constructor stub
	}


}
