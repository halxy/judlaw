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
		this.xstream.alias("parte", Parte.class);
		this.xstream.alias("ementa", Ementa.class);
		this.xstream.alias("encerramento", Encerramento.class);
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
		/*
		 * Teste com o Ac�rd�o REsp 1120616/PR
		 */
		
		//Cabecalho
		Cabecalho cabecalho1 = new Cabecalho();
		cabecalho1.setTribunal("Superior Tribunal de Justi�a");
		cabecalho1.setTipo("RECURSO ESPECIAL");
		cabecalho1.setNumero(1120616);
		cabecalho1.setLocal("Paran�");
		cabecalho1.setCodRegistro("2009/0104563-1");
		
		//Partes
		ArrayList<Parte> partes = new ArrayList<Parte>();
		partes.add( new Parte("RELATOR", "MINISTRO BENEDITO GON�ALVES") );
		partes.add( new Parte("RECORRENTE", "CONFEDERA��O NACIONAL DA AGRICULTURA - CNA E OUTROS") );
		partes.add( new Parte("ADVOGADO", "KLAUSS DIAS KUHNEN E OUTRO(S)") );
		partes.add( new Parte("RECORRIDO", "ALCIDES LAZZARI") );
		partes.add( new Parte("ADVOGADO", "CL�UDIA PIZZATTO E OUTRO(S)") );
		
		//Ementa
		Ementa ementa1 = new Ementa("DIREITO TRIBUT�RIO E SINDICAL. RECURSO ESPECIAL. CONTRIBUI��O SINDICAL RURAL. PUBLICA��O DE NOTIFICA��O EM JORNAIS LOCAIS DE " +
				"GRANDE CIRCULA��O. APLICA��O DO ARTIGO 605 DA CLT." +
				" NECESSIDADE. CONDI��O DE PROCEDIBILIDADE E EXIGIBILIDADE." +
				" OBSERV�NCIA DO PRINC�PIO DA PUBLICIDADE. RECURSO SUBMETIDO AO" +
				"REGIME PREVISTO NO ARTIGO 543-C DO CPC...");
		
		//Relatorio
		Relatorio relatorio1 = new Relatorio("O SENHOR MINISTRO BENEDITO GON�ALVES (Relator): Cuida-se de " +
				"recurso especial interposto pela Confedera��o Nacional da Agricultura - CNA e outros, com" +
				" fundamento nas al�neas 'a' e 'c' do permissivo constitucional, contra ac�rd�o proferido pelo " +
				"Tribunal de Justi�a do Paran�. O ac�rd�o julgador da apela��o foi assim ementado (fl. 554):...");
		
		//Votos
		ArrayList<Voto> votos = new ArrayList<Voto>();
		votos.add( new Voto("O SENHOR MINISTRO BENEDITO GON�ALVES (Relator): Preenchidos os " +
				"requisitos de admissibilidade, conhe�o do recurso especial. " +
				"Inicialmente registra-se que a presente controv�rsia n�o se identifica com a julgada no " +
				"recurso especial repetitivo n. 902.349 - PR, da relatoria do Min. Luiz Fux, que versou sobre a " +
				"incid�ncia ou n�o de multa morat�ria e a defini��o do dispositivo legal a ser aplicado em caso de " +
				"recolhimento a destempo da contribui��o sindical rural: se o art. 600 da CLT, ou se o art. 2� da Lei " +
				"8.022/90, que teria revogado o preceito anterior implicitamente...") );
		
		//Encerramento
		Encerramento encerramento1 = new Encerramento();
		encerramento1.setAcordao(" AC�RD�O \n" +
				"Vistos, relatados e discutidos os autos em que s�o partes as acima indicadas, acordam os Ministros " +
				"da Primeira Se��o do Superior Tribunal de Justi�a, por unanimidade, negar provimento ao recurso " +
				"especial, nos termos do voto do Sr. Ministro Relator. Os Srs. Ministros Hamilton Carvalhido, " +
				"Eliana Calmon, Luiz Fux, Castro Meira, Denise Arruda, Humberto Martins, Herman Benjamin e Mauro " +
				"Campbell Marques votaram com o Sr. Ministro Relator. \n " +
				"Bras�lia (DF), 25 de novembro de 2009(Data do Julgamento)");		
		encerramento1.setDecisao("Certifico que a egr�gia PRIMEIRA SE��O, ao apreciar o processo em ep�grafe na sess�o realizada nesta data, proferiu a seguinte decis�o:" +
				"A Se��o, por unanimidade, negou provimento ao recurso especial, nos termos do voto " +
				"do Sr. Ministro Relator. Os Srs. Ministros Hamilton Carvalhido, Eliana Calmon, Luiz Fux, " +
				"Castro Meira, Denise Arruda, Humberto Martins, Herman Benjamin e Mauro Campbell Marques " +
				"votaram com o Sr. Ministro Relator." );
		encerramento1.setOrgaoJulgador("PRIMEIRA SE��O");
		
		/* ---------- Criacao e Persistencia do DocumentoJuridico ----------*/	
		DocumentoJuridico docJud1 = new DocumentoJuridico();
		docJud1.setIdentificadorUnico("resp1120616PR");
		docJud1.setCabecalho(cabecalho1);
		docJud1.setEmenta(ementa1);
		docJud1.setRelatorio(relatorio1);
		docJud1.setEncerramento(encerramento1);
		docJud1.setVotos(votos);
		docJud1.setPartes(partes);
		
		docJud1.setDataJulgamento("25/11/2009");
		docJud1.setDataPublicacao("30/11/2009");
		
		/* ---------- Criacao e Persistencia do DocumentoJuridico ----------*/		
		try {
			DocJudXMLManager.getInstance().salvaDocumentoJuridico(docJud1, "resp1120616PR");
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
