/* 
 * Universidade Federal de Campina Grande
 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas	
 */
package judlaw.model.logic.conversion;

import java.io.File;
import java.io.FileInputStream;

import org.apache.xerces.dom.CoreDocumentImpl;
import org.cyberneko.html.parsers.DOMFragmentParser;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
 
public class HTMLToText {
	
	private FileInputStream fin;
	private StringBuffer TextBuffer;
	private InputSource inSource;
    
	private static HTMLToText htmlToText = null;
	
	
	   /**
	    * Retorna uma instancia da classe HTMLToText
	    * @return
	    */
	    public static HTMLToText getInstance() {
	        if(htmlToText == null)
	        	htmlToText = new HTMLToText();
	        return htmlToText;
	    }
	/*
     * Processa o texto dos nodes recursivamente
     */
    private void processNode(Node node) {
    	if( node == null ) return;
    	//Processa um textNode
    	if( node.getNodeType() == Node.TEXT_NODE ) {
    		TextBuffer.append(node.getNodeValue());
    	} else if( node.hasChildNodes() ) {
			//Processa seus filhos	
			NodeList childList = node.getChildNodes();
			int childLen = childList.getLength();
			for (int count = 0; count < childLen; count ++) 
				processNode(childList.item(count));					
		}
		else return;
    }
    
    /**
     * Extrai o texto de um HTML
     * @param fileName
     * @return
     */
    public String htmltoText(String fileName) {
    	DOMFragmentParser parser = new DOMFragmentParser();   	
    	System.out.println("Recuperando texto do arquivo " + fileName + "....");
        File f = new File(fileName);
        if (!f.isFile()) {
            System.out.println("O arquivo " + fileName + " não existe.");
        }
        try {
            fin = new FileInputStream(f);
        } catch (Exception e) {
            System.out.println("Não foi possível abrir o arquivo " + fileName + " para leitura.");
        } 
        try {
            inSource = new InputSource(fin);
        } catch (Exception e) {
            System.out.println("Não foi possível abrir o Input Source do arquivo " + fileName);
        } 
        CoreDocumentImpl codeDoc = new CoreDocumentImpl();
        DocumentFragment doc = codeDoc.createDocumentFragment();       
        try {
        	parser.parse(inSource, doc);
        } catch (Exception e) {
        	System.out.println("Não foi possível recuperar o texto do arquivo " + fileName);    	
        }  
        TextBuffer = new StringBuffer();
        processNode(doc);    
        System.out.println("Conversão concluida com sucesso."); 		
    	return TextBuffer.toString();
    }     
    
    /*
     * Main para testes
     */
    public static void main(String args[]) {
    	HTMLToText html = new HTMLToText();
    	String htmlToText = html.htmltoText("Del2848compilado.htm");
    	System.out.println(htmlToText.substring(0, 2000));
    }  
}
