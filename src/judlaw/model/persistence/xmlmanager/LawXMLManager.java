/* 
 * Universidade Federal de Campina Grande
 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas	
 */
package judlaw.model.persistence.xmlmanager;

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
    
    /**
     * 
     * @return
     */
	public XStream getXstreamNorma() {
		if (xstreamNorma == null ) {
			xstreamNorma = new XStream();
    		setAliasesNorma();
    	}
		return xstreamNorma;
	}

	/**
	 * 
	 * @param xstreamNorma
	 */
	public void setXstreamNorma(XStream xstreamNorma) {
		this.xstreamNorma = xstreamNorma;
	}

	/*
	 * Seta os aliases (partes) da norma.
	 */
	private void setAliasesNorma() {
		this.xstreamNorma.alias("norma", Norma.class);
		this.xstreamNorma.alias("elementosNorma", ElementoNorma.class);
		this.xstreamNorma.alias("citacoesFeitas", CitacaoDocJud.class);
		this.xstreamNorma.alias("citacoesRecebidasDocJud", CitacaoDocJud.class);
		this.xstreamNorma.alias("citacoesRecebidasTextLeg", CitacaoTextLeg.class);
		this.xstreamNorma.alias("alteracoesFeitas", Alteracao.class);
		this.xstreamNorma.alias("alteracoesRecebidas", Alteracao.class);
	}
	
	/**
	 * 
	 * @param norma
	 * @param nomeArquivo
	 * @throws IOException
	 */
	public void salvaNorma(Norma norma, String nomeArquivo) throws IOException {
    	setXstreamNorma( new XStream() );
    	//Setando as classes no xstream
    	setAliasesNorma();
    	//Persistindo
    	ObjectOutputStream out = xstreamNorma.createObjectOutputStream( new FileWriter("xml\\"+ nomeArquivo +".xml"));
		out.writeObject(norma);
		out.close();
    }
	
	/**
	 * 
	 * @return
	 */
	public XStream getXstreamElementoNorma() {
		if (xstreamElementoNorma == null ) {
			xstreamElementoNorma = new XStream();
    		setAliasesElementoNorma();
    	}
		return xstreamElementoNorma;
	}
	
	/**
	 * 
	 * @param xstreamElementoNorma
	 */
	public void setXstreamElementoNorma(XStream xstreamElementoNorma) {
		this.xstreamElementoNorma = xstreamElementoNorma;
	}

	/*
	 * Seta os aliases (partes) de um elementoNorma.
	 */
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
	
	/**
	 * Persiste um elementoNorma em um arquivo XML
	 * @param elementoNorma
	 * @param nomeArquivo
	 * @throws IOException
	 */
	public void salvaElementoNorma(ElementoNorma elementoNorma, String nomeArquivo) throws IOException {
    	setXstreamElementoNorma( new XStream() );
    	//Setando as classes no xstream
    	setAliasesElementoNorma();
    	//Persistindo
    	ObjectOutputStream out = xstreamElementoNorma.createObjectOutputStream( new FileWriter("xml\\"+ nomeArquivo +".xml"));
		out.writeObject(elementoNorma);
		out.close();
    }
	
	/*
	 * Main para testes
	 */
	public static void main(String[] args) {
		Norma norma = new Norma();

		norma.setTipo("LEI");
		norma.setNumero(12095);
		norma.setDataPublicacao("19/11/2009");
		norma.setEmenta("Declara Sant’Ana do Livramento, Estado do Rio Grande do Sul, cidade símbolo da integração " +
				"brasileira com os países membros do Mercosul.");
		norma.setAutoria("O PRESIDENTE DA REPÚBLICA");
		norma.setLocal("Brasilia");
		norma.setIdentificadorUnico("lei12095");
		norma.setVigencia("19/11/2009-99/99/9999");
		
		ElementoNorma art1 = new ElementoNorma();
		art1.setIdentificadorUnico("art1_lei12095");
		art1.setTipo("artigo");
		art1.setNumero("1");
		art1.setDataPublicacao("19/11/2009");
		art1.setVigencia("19/11/2009-99/99/9999");
		art1.setTexto("A cidade de Sant’Ana do Livramento, localizada na fronteira oeste do Estado do Rio Grande do Sul, " +
				"é declarada cidade símbolo da integração brasileira com os demais países membros do Mercado Comum do " +
				"Sul - MERCOSUL.");
		
		ElementoNorma art2 = new ElementoNorma();
		art2.setIdentificadorUnico("art2_lei12095");
		art2.setTipo("artigo");
		art2.setNumero("2");
		art2.setDataPublicacao("19/11/2009");
		art2.setVigencia("19/11/2009-99/99/9999");
		art2.setTexto("O Poder Executivo promoverá ampla divulgação desta Lei, inclusive no âmbito do Mercosul, da " +
				"Organização dos Estados Americanos - OEA e de demais organizações intergovernamentais afetas");
		
		ElementoNorma art3 = new ElementoNorma();
		art3.setIdentificadorUnico("art3_lei12095");
		art3.setTipo("artigo");
		art3.setNumero("3");
		art3.setDataPublicacao("19/11/2009");
		art3.setVigencia("19/11/2009-99/99/9999");
		art3.setTexto("Esta Lei entra em vigor na data de sua publicação.");
		
		norma.getElementosNorma().add(art1);
		norma.getElementosNorma().add(art2);
		norma.getElementosNorma().add(art3);
		
		try {
			LawXMLManager.getInstance().salvaNorma(norma, "lei12095");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println( LawXMLManager.getInstance().getXstreamNorma().toXML( norma ));
	}
}
