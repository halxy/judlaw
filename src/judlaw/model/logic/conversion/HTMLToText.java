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
	
	FileInputStream fin = null;
	StringBuffer TextBuffer = null;
	InputSource inSource = null;
    
    //Gets the text content from Nodes recursively
    private void processNode(Node node) {
    	if (node == null) return;
    	
    	//Process a text node
    	if (node.getNodeType() == node.TEXT_NODE) {
    		TextBuffer.append(node.getNodeValue());
    	} else if (node.hasChildNodes()) {
			//Process the Node's children 
					
			NodeList childList = node.getChildNodes();
			int childLen = childList.getLength();
			
			for (int count = 0; count < childLen; count ++) 
				processNode(childList.item(count));					
		}
		else return;
    }
    
    // Extracts text from HTML Document
    public String htmltoText(String fileName) {
    	
    	DOMFragmentParser parser = new DOMFragmentParser();
    	
    	System.out.println("Parsing text from HTML file " + fileName + "....");
        File f = new File(fileName);
        
        if (!f.isFile()) {
            System.out.println("File " + fileName + " does not exist.");
            return null;
        }
        
        try {
            fin = new FileInputStream(f);
        } catch (Exception e) {
            System.out.println("Unable to open HTML file " + fileName + " for reading.");
            return null;
        } 
        	
        try {
            inSource = new InputSource(fin);
        } catch (Exception e) {
            System.out.println("Unable to open Input source from HTML file " + fileName);
            return null;
        } 
        
        CoreDocumentImpl codeDoc = new CoreDocumentImpl();
        DocumentFragment doc = codeDoc.createDocumentFragment();
        
        try {
        	parser.parse(inSource, doc);
        } catch (Exception e) {
        	System.out.println("Unable to parse HTML file " + fileName);
        	return null;       	
        }
        
        TextBuffer = new StringBuffer();
        
        //Node is a super interface of DocumentFragment, so no typecast needed
        processNode(doc);
        
        System.out.println("Done.");
    		
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
