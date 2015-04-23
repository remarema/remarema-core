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

	private boolean fileIdent;

	public FileInfo Fileausgabe(String Filename, OutputStream destination) {
		FileRepository filerepository = new FileRepository();
		String directory = "./test/resources";
		List<FileInfo> fileLists = filerepository.listFiles(directory);

		for(FileInfo result : fileLists){
			fileIdent = false;
			if(result.getName().equals(Filename)){
				fileIdent = true;
				System.out.println(fileIdent);
				return result;
				
			}
		}
		System.out.println(fileIdent);
		return null;
		
		
		// suche die Datei mit Filename im Repository
		// und schreibe den Inhalt in den OutputStream destination
	}

}
