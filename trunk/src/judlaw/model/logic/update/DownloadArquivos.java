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
	
	/*
	 * Faz o download de um arquivo
	 */
	private static void download(String endereco, String nomeArquivoLocal, String diretorio, String tipo){
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
	private static void imprimeDownload(int byteEscrito, String nomeArquivoLocal) {
		System.out.println("Arquivo baixado com sucesso");
		int mb = byteEscrito / 1024;
		if(mb < 1000) {
			System.out.println("File name:\""+nomeArquivoLocal+ "\"\nTamanho :" + mb + "KB");
		} else {
			double x = (double) mb / 1000;
			System.out.println("File name:\""+nomeArquivoLocal+ "\"\nTamanho :" + x + "MB");		
		}
	}

	public static void downloadArquivo(String fileAddress, String destinationDir, String tipo) {
	// Find the index of last occurance of character ‘/’ and ‘.’.
	int lastIndexOfSlash =
	fileAddress.lastIndexOf('/');
	int lastIndexOfPeriod =
	fileAddress.lastIndexOf('.');

	// Find the name of file to be downloaded from the address.
	String fileName=fileAddress.substring
	(lastIndexOfSlash + 1);

	// Check whether path or file name is given correctly.
	if (lastIndexOfPeriod >=1 &&  lastIndexOfSlash >= 0 && lastIndexOfSlash < fileAddress.length())
			{
			download(fileAddress,fileName,
			destinationDir, tipo);
			}
			else
			{
			System.err.println("Specify correct path or file name.");
	}
	}
			
	public static void main(String[] args){
			downloadArquivo("http://en.wikipedia.org/wiki/HTML", ".", Constantes.DOWNLOAD_HTML); 
		}
}

