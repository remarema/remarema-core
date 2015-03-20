package remarema.core;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse repräsentiert einen Server
 * 
 * @author Regina
 *
 */
public class Server {
	/*
	 * public void Fileausgabe( String Filename, OutputStream destination ) {
	 * 
	 * 
	 * 
	 * 
	 * }
	 */
	/**
	 * Gibt die Liste der Vorhandenen Dateien eines Verzeichnis mit Hilfe von FileInfo zurück
	 * 
	 * @param directory
	 *            gibt an aus welchen Verzeichnis die Dateien sind.
	 * @return es wird fileList zurückgegeben
	 */
	public List<FileInfo> listFiles(String directory) {
		List<FileInfo> fileList = new ArrayList<FileInfo>();
		File source =new File(directory);
	File[]sourceFiles=source.listFiles();
	for(File file:sourceFiles){
		
		FileInfo info =new FileInfo();
		info.setName(file.getName());
		info.setLastModified(file.lastModified());
		info.setDirectory(file.isDirectory());
		fileList.add(info);
		
	}
		
		
		return fileList;

	}

}
