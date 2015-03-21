package remarema.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author Regina
 *
 */
public class Client {

	private Server server;

	public Client(Server server) {
		this.server = server;
	}
/**
 * 
 * @param filename
 */
	public void retrieve(String filename) {
		OutputStream destination = newOutputStream(filename);
		server.Fileausgabe(filename, destination);

	}

	private OutputStream newOutputStream(String filename) {

		File hotzenplotz = new File(filename);

		FileOutputStream igenwie = null;
		try {
			igenwie = new FileOutputStream(hotzenplotz);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return igenwie;
	}
	public List<FileInfo> listFiles(String directory) {
		List<FileInfo> fileListClient = new ArrayList<FileInfo>();
		File source =new File(directory);
	File[]sourceFiles=source.listFiles();
	for(File file:sourceFiles){
		
		FileInfo info =new FileInfo();
		info.setName(file.getName());
		info.setLastModified(file.lastModified());
		info.setDirectory(file.isDirectory());
		fileListClient.add(info);
		
	}
		
		
		return fileListClient;
		

	}
	public File existFile(String name){
		
	}
	public File currentFile(String name ,long lastModified){
		
	}
	public File oldFile(String name,long lastModified){
	

}
