/* 
 * Universidade Federal de Campina Grande
 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas	
 */
package judlaw.model.logic.update;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import judlaw.model.util.Constantes;

/**
 * 
 * @author Halley Freitas
 *
 */
public class DownloadArquivos {

	final static int size = 1024;
	private static DownloadArquivos downloadArquivos = null;
	
	
   /**
    * Retorna uma instancia da classe DownloadArquivos
    * @return
    */
    public static DownloadArquivos getInstance(){
        if(downloadArquivos == null)
        	downloadArquivos = new DownloadArquivos();
        return downloadArquivos;
    }
	/*
	 * Faz o download de um arquivo
	 */
	private void download(String endereco, String nomeArquivoLocal, String diretorio, String tipo){
		OutputStream os = null;
		URLConnection URLConn = null;
		InputStream is = null;
		
		try {
			URL fileUrl;
			byte[] buf;
			int byteLido, byteEscrito = 0;
			fileUrl= new URL(endereco);
			if(tipo.equalsIgnoreCase(Constantes.DOWNLOAD_HTML)) { //Caso o arquivo seja um html
				nomeArquivoLocal += ".html";
			}
			os = new BufferedOutputStream(new FileOutputStream(diretorio+"\\"+nomeArquivoLocal));
			
			URLConn = fileUrl.openConnection();
			is = URLConn.getInputStream();
			buf = new byte[size];
			while ( (byteLido = is.read(buf) ) != -1) {
				os.write(buf, 0, byteLido);
				byteEscrito += byteLido;
			}
			imprimeDownload(byteEscrito, nomeArquivoLocal);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				is.close();
				os.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	} 
	
	/*
	 * Imprime o resultado do download
	 */
	private void imprimeDownload(int byteEscrito, String nomeArquivoLocal) {
		int mb = byteEscrito / 1024;
		if(mb < 1000) {
			System.out.println("Arquivo \""+nomeArquivoLocal+ "\" baixado com sucesso.\nTamanho :" + mb + "KB");
		} else {
			double x = (double) mb / 1000;
			System.out.println("Arquivo \""+nomeArquivoLocal+ "\" baixado com sucesso.\nTamanho :" + x + "MB");		
		}
	}

	/**
	 * 
	 * @param endereco
	 * @param destinationDir
	 * @param tipo
	 */
	public void downloadArquivo(String endereco, String diretorio, String tipo) {
		// Acha o indice da ultima ocorrencia de "/" e "."
		int ultimoIndiceDaBarra = endereco.lastIndexOf('/');
		int ultimoIndiceDoPonto = endereco.lastIndexOf('.');
	
		// Acha o nome a ser salvo do arquivo pelo endereco
		String nomeArquivoLocal = endereco.substring(ultimoIndiceDaBarra + 1);
	
		// Check whether path or file name is given correctly.
		if (ultimoIndiceDoPonto >=1 &&  ultimoIndiceDaBarra >= 0 && ultimoIndiceDaBarra < endereco.length()) {
			download( endereco, nomeArquivoLocal, diretorio, tipo );
		}
		else {
			System.err.println("Especifique o caminho correto.");
		}
	}
			
	public static void main(String[] args){
			DownloadArquivos.getInstance().downloadArquivo("http://en.wikipedia.org/wiki/HTML", ".", Constantes.DOWNLOAD_HTML); 
	}
}

