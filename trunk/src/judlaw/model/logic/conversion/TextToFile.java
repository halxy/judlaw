package judlaw.model.logic.conversion;

import java.io.PrintWriter;

public class TextToFile {

	private static TextToFile textToFile = null;
	
   /**
    * Retorna uma instancia da classe TextToFile
    * @return
    */
    public static TextToFile getInstance() {
        if(textToFile == null)
        	textToFile = new TextToFile();
        return textToFile;
    }
	/**
	 * Transforma um texto em um arquivo txt;
	 * @param pdfText
	 * @param fileName
	 */
    public void textToFile(String pdfText, String fileName) {
    	try {
    		PrintWriter pw = new PrintWriter(fileName);
    		pw.print(pdfText);
    		pw.close();    	
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	System.out.println("Concluido.");
    }

    /*
     * Main para testes
     */
    public static void main(String[] args) {
//		TextToFile.getInstance().textToFile(PDFToText.getInstance().pdftoText("Del2848compilado.htm"), "Del2848compilado.htm.txt");
    	TextToFile.getInstance().textToFile(HTMLToText.getInstance().htmltoText("Lcp123.htm"), "Lcp123.htm.txt");
    }
}
