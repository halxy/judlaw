/* 
 * Universidade Federal de Campina Grande
 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas	
 */
package judlaw.model.persistence.xmlmanager;

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
import judlaw.model.bean.ref.CitacaoDocJud;
import judlaw.model.bean.ref.CitacaoTextLeg;

import com.thoughtworks.xstream.XStream;

/**
 * Classe DocJudXMLManager. Define a persistencia dos documentos juridicos em XML
 * @author Halley Freitas
 *
 */
public class DocJudXMLManager {
	
	private XStream xstream = null;
	private static DocJudXMLManager docJudXmlManager = null;
	
	/**
	 * Singleton
	 * @return
	 */
    public static DocJudXMLManager getInstance(){
        if(docJudXmlManager == null)
        	docJudXmlManager = new DocJudXMLManager();
        return docJudXmlManager;
    }
    
    /**
     * 
     * @return
     */
    public XStream getXstream() {
    	if (xstream == null ) {
    		xstream = new XStream();
    		setAliases();
    	}
		return xstream;
	}

    /**
     * 
     * @param xstream
     */
	public void setXstream(XStream xstream) {
		this.xstream = xstream;
	}
	
	/*
	 * Seta os aliases, ou partes do documento juridico
	 */
	private void setAliases() {
		this.xstream.alias("documentojuridico", DocumentoJuridico.class);
		this.xstream.alias("cabecalho", Cabecalho.class);
		this.xstream.alias("ementa", Ementa.class);
		this.xstream.alias("encerramento", Encerramento.class);
		this.xstream.alias("parte", Parte.class);
		this.xstream.alias("relatorio", Relatorio.class);
		this.xstream.alias("voto", Voto.class);
		this.xstream.alias("citacoesFeitas", CitacaoDocJud.class);
		this.xstream.alias("citacoesRecebidasDocJud", CitacaoDocJud.class);
		this.xstream.alias("citacoesRecebidasTextLeg", CitacaoTextLeg.class);
	}

	/**
	 * Salva um documento juridico em XML
	 * @param documentoJuridico
	 * @param nomeArquivo nome desejado do arquivo em xml
	 * @throws IOException 
	 */
	public void salvaDocumentoJuridico(DocumentoJuridico documentoJuridico, String nomeArquivo) throws IOException {
    	setXstream( new XStream() );
    	//Setando as classes no xstream
    	setAliases();
    	//Persistindo
    	ObjectOutputStream out = xstream.createObjectOutputStream( new FileWriter("xml\\"+ nomeArquivo +".xml"));
		out.writeObject(documentoJuridico);
		out.close();
    }
	
	/*
	 * Main para testes
	 */
	public static void main(String[] args) {
		//Cabecalho
		Cabecalho cabecalho1 = new Cabecalho();
		cabecalho1.setCodRegistro("codRegistro");
		cabecalho1.setTribunal("tribunal");
		//Ementa
		Ementa ementa1 = new Ementa("ementa1");
		//Relatorio
		Relatorio relatorio1 = new Relatorio("relatorio1");
		//Encerramento
		Encerramento encerramento1 = new Encerramento("acordao", "decisao1", "orgaoJulgador");
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
		
		try {
			DocJudXMLManager.getInstance().salvaDocumentoJuridico(docJud1, "HC1234");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		XStream xstream = DocJudXMLManager.getInstance().getXstream();
		try {
			ObjectInputStream in = xstream.createObjectInputStream( new FileReader("xml\\HC1234.xml"));
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
