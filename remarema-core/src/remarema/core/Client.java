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
	private List<FileInfo> fileListClient;

	public Client() {

	}

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
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return igenwie;
	}

	

	public String OuputList(List<FileInfo> fileListClient) {
		String fileAusgabe = "";
		this.fileListClient = fileListClient;
		for (int i = 0; i < fileListClient.size(); i++) {
			fileAusgabe = fileListClient.toString();
		}
		return fileAusgabe;

	}
	/**
	 * existiert das File das ich von Server erhalten habe an CLient ebenfall
	 * @param name
	 * @return
	 */

	

	public File currentFile(String name, long lastModified) {
		return null;

	}

	public File oldFile(String name, long lastModified) {
		return null;

	}

}
