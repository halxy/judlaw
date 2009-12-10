package judlaw.model.logic.update;
import java.io.*;
import java.net.*;

public class DownloadFiles {

	final static int size=1024;
	
	public static void FileDownload(String fileAddress, String localFileName, String destinationDir){
		OutputStream os = null;
		URLConnection URLConn = null;
	
		// URLConnection class represents a communication link between the
		// application and a URL.
		InputStream is = null;
		try {
			URL fileUrl;
			byte[] buf;
			int ByteRead,ByteWritten=0;
			fileUrl= new URL(fileAddress);
			os = new BufferedOutputStream(new FileOutputStream(destinationDir+"\\"+localFileName));
		//The URLConnection object is created by invoking the  	
		// openConnection method on a URL.
			URLConn = fileUrl.openConnection();
			is = URLConn.getInputStream();
			buf = new byte[size];
			while ((ByteRead = is.read(buf)) != -1) {
				os.write(buf, 0, ByteRead);
				ByteWritten += ByteRead;
			}
			System.out.println("Downloaded Successfully.");
			System.out.println("File name:\""+localFileName+ "\"\nNo of bytes :" + ByteWritten);
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
	
	public static void fileDownload(String fileAddress, String destinationDir) {

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
			FileDownload(fileAddress,fileName,
			destinationDir);
			}
			else
			{
			System.err.println("Specify correct path or file name.");
	}}
			
	public static void main(String[] args){
			fileDownload("http://en.wikipedia.org/wiki/HTML", "."); 
		}
}

