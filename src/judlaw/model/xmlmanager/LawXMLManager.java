package judlaw.model.xmlmanager;

import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;

import judlaw.model.bean.law.ElementoNorma;
import judlaw.model.bean.law.Norma;
import judlaw.model.bean.ref.Alteracao;
import judlaw.model.bean.ref.CitacaoDocJud;
import judlaw.model.bean.ref.CitacaoTextLeg;

import com.thoughtworks.xstream.XStream;

public class LawXMLManager {
	
	private XStream xstreamNorma = null;
	private XStream xstreamElementoNorma = null;
	private static LawXMLManager lawXmlManager = null;
	
	/**
	 * Singleton
	 * @return
	 */
    public static LawXMLManager getInstance(){
        if(lawXmlManager == null)
        	lawXmlManager = new LawXMLManager();
        return lawXmlManager;
    }
    
	public XStream getXstreamNorma() {
		if (xstreamNorma == null ) {
			xstreamNorma = new XStream();
    		setAliasesNorma();
    	}
		return xstreamNorma;
	}

	public void setXstreamNorma(XStream xstreamNorma) {
		this.xstreamNorma = xstreamNorma;
	}

	private void setAliasesNorma() {
		this.xstreamNorma.alias("norma", Norma.class);
		this.xstreamNorma.alias("elementosNorma", ElementoNorma.class);
		this.xstreamNorma.alias("citacoesFeitas", CitacaoDocJud.class);
		this.xstreamNorma.alias("citacoesRecebidasDocJud", CitacaoDocJud.class);
		this.xstreamNorma.alias("citacoesRecebidasTextLeg", CitacaoTextLeg.class);
		this.xstreamNorma.alias("alteracoesFeitas", Alteracao.class);
		this.xstreamNorma.alias("alteracoesRecebidas", Alteracao.class);
	}
	
	public void salvaNorma(Norma norma, String nomeArquivo) throws IOException {
    	setXstreamNorma( new XStream() );
    	//Setando as classes no xstream
    	setAliasesNorma();
    	//Persistindo
    	ObjectOutputStream out = xstreamNorma.createObjectOutputStream( new FileWriter("xml\\"+ nomeArquivo +".xml"));
		out.writeObject(norma);
		out.close();
    }
	
	public XStream getXstreamElementoNorma() {
		if (xstreamElementoNorma == null ) {
			xstreamElementoNorma = new XStream();
    		setAliasesElementoNorma();
    	}
		return xstreamElementoNorma;
	}
	
	public void setXstreamElementoNorma(XStream xstreamElementoNorma) {
		this.xstreamElementoNorma = xstreamElementoNorma;
	}

	private void setAliasesElementoNorma() {
		this.xstreamElementoNorma.alias("elementosNormaPai", ElementoNorma.class);
		this.xstreamElementoNorma.alias("normasPai", Norma.class);
		this.xstreamElementoNorma.alias("elementosNorma", ElementoNorma.class);
		this.xstreamElementoNorma.alias("citacoesFeitas", CitacaoDocJud.class);
		this.xstreamElementoNorma.alias("citacoesRecebidasDocJud", CitacaoDocJud.class);
		this.xstreamElementoNorma.alias("citacoesRecebidasTextLeg", CitacaoTextLeg.class);
		this.xstreamElementoNorma.alias("alteracoesFeitas", Alteracao.class);
		this.xstreamElementoNorma.alias("alteracoesRecebidas", Alteracao.class);
	}
	
	public void salvaElementoNorma(ElementoNorma elementoNorma, String nomeArquivo) throws IOException {
    	setXstreamElementoNorma( new XStream() );
    	//Setando as classes no xstream
    	setAliasesElementoNorma();
    	//Persistindo
    	ObjectOutputStream out = xstreamElementoNorma.createObjectOutputStream( new FileWriter("xml\\"+ nomeArquivo +".xml"));
		out.writeObject(elementoNorma);
		out.close();
    }
	
	public static void main(String[] args) {
		Norma norma = new Norma();
		norma.setDataPublicacao("oa");
		
		ElementoNorma elementoNorma = new ElementoNorma();
		elementoNorma.setDataPublicacao("oa2");
		
		try {
			LawXMLManager.getInstance().salvaNorma(norma, "x");
			LawXMLManager.getInstance().salvaElementoNorma(elementoNorma, "x2");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println( LawXMLManager.getInstance().getXstreamNorma().toXML( norma ));
		System.out.println( LawXMLManager.getInstance().getXstreamNorma().toXML( elementoNorma ));
	}
}
