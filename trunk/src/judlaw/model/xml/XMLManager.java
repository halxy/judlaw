package judlaw.model.xml;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

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
		
		//Cabecalho
		Cabecalho cabecalho1 = new Cabecalho();
		cabecalho1.setCodRegistro("codRegistro");
		cabecalho1.setOrgaoJulgador("orgaoJulgador");
		cabecalho1.setTribunal("tribunal");
		//Ementa
		Ementa ementa1 = new Ementa("ementa1");
		//Relatorio
		Relatorio relatorio1 = new Relatorio("relatorio1");
		//Encerramento
		Encerramento encerramento1 = new Encerramento("decisao1", "local1");
		//Votos
		ArrayList<Voto> votos = new ArrayList<Voto>();
		votos.add( new Voto("voto1") );
		votos.add( new Voto("voto2") );
		//Partes
		ArrayList<Parte> partes = new ArrayList<Parte>();
		partes.add( new Parte("titulo1", "nome1") );
		partes.add( new Parte("titulo2", "nome2") );
		
		/* ---------- Criacao e Persistencia do DocumentoJuridico ----------*/	
		DocumentoJuridico docJud1 = new DocumentoJuridico();
		docJud1.setIdentificadorUnico("idUnico");
		docJud1.setCabecalho(cabecalho1);
		docJud1.setEmenta(ementa1);
		docJud1.setRelatorio(relatorio1);
		docJud1.setEncerramento(encerramento1);
		docJud1.setVotos(votos);
		docJud1.setPartes(partes);
		
		ObjectOutputStream out;
		try {
			out = xstream.createObjectOutputStream( new FileWriter("x.xml"));
			out.writeObject(docJud1);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			ObjectInputStream in = xstream.createObjectInputStream( new FileReader("x.xml"));
			DocumentoJuridico docJud = (DocumentoJuridico) in.readObject();
			System.out.println(docJud.getIdentificadorUnico());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
