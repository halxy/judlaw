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

import org.pdfbox.cos.COSDocument;
import org.pdfbox.pdfparser.PDFParser;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.util.PDFTextStripper;

/**
 * Classe PDFToText - Converte um PDF para String.
 * @author Halley Freitas
 *
 */
public class PDFToText {
	
	private PDFParser parser;
	private String parsedText;
	private PDFTextStripper pdfStripper;
	private PDDocument pdDoc;
	private COSDocument cosDoc;
	
	private static PDFToText pdfToText = null;
	
	
   /**
    * Retorna uma instancia da classe PDFToText
    * @return
    */
    public static PDFToText getInstance() {
        if(pdfToText == null)
        	pdfToText = new PDFToText();
        return pdfToText;
    }
	        
    /**
     * Extrai a String de um arquivo PDF
     * @param arquivoPDF Arquivo PDF a ser convertido para String
     * @return String do arquivo PDF
     */
    public String pdftoText(String arquivoPDF) {
        System.out.println("Recuperando texto do arquivo " + arquivoPDF + "....");
        File f = new File(arquivoPDF);
        //Procurando o arquivo
        if (!f.isFile()) {
            System.out.println("O arquivo " + arquivoPDF + " não existe.");
        }
        //Criando o parser
        try {
            parser = new PDFParser(new FileInputStream(f));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Convertendo o PDF para String
        try {
            parser.parse();
            cosDoc = parser.getDocument();
            pdfStripper = new PDFTextStripper();
            pdDoc = new PDDocument(cosDoc);
            parsedText = pdfStripper.getText(pdDoc); 
        } catch (Exception e) {
            System.out.println("Erro.");
            e.printStackTrace();
            try { //Fechando
               if (cosDoc != null) {
            	   cosDoc.close();
               }
               if (pdDoc != null) {
            	   pdDoc.close();
               }
            } catch (Exception e1) {
               e.printStackTrace();
            }
        }      
        System.out.println("Conversao concluida com sucesso.");
        return parsedText;
    }
	    
    /*
     * Main para testes
     */
    public static void main(String args[]) {
    	String pdfToText = PDFToText.getInstance().pdftoText("HC84445.pdf");
    	System.out.println(pdfToText);
    }  
}