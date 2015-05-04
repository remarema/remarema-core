package remarema.core;

import java.io.File;
import java.util.List;

public class Main {
	FileRepository re = new FileRepository();
	

	public void syncronFile(String directoryClient, String directoryServer) {
		List<FileInfo> listeClient = re.listFiles(directoryClient);		
		List<FileInfo> missingFiles = re.getNewFiles(directoryServer,listeClient);
		
		re.copyFiles(missingFiles, directoryClient);
	}

}
