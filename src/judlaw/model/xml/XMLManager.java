package judlaw.model.xml;

import judlaw.model.bean.docjud.Cabecalho;
import judlaw.model.bean.docjud.DocumentoJuridico;
import judlaw.model.bean.docjud.Ementa;
import judlaw.model.bean.docjud.Encerramento;
import judlaw.model.bean.docjud.Parte;
import judlaw.model.bean.docjud.Relatorio;
import judlaw.model.bean.docjud.Voto;

import com.thoughtworks.xstream.XStream;

public class XMLManager {

	public static void main(String[] args) {
		
		XStream xstream = new XStream();
		
		xstream.alias("documentojuridico", DocumentoJuridico.class);
		xstream.alias("cabecalho", Cabecalho.class);
		xstream.alias("ementa", Ementa.class);
		xstream.alias("encerramento", Encerramento.class);
		xstream.alias("parte", Parte.class);
		xstream.alias("relatorio", Relatorio.class);
		xstream.alias("voto", Voto.class);
		
		DocumentoJuridico docJud = new DocumentoJuridico();
		String xml = xstream.toXML( docJud );
		System.out.println(xml);
	}
}
