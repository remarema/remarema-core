package remarema.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Regina
 *
 */
public class Client {

	private List<FileInfo> fileListClient;
	private String filename;
	private File hotzenplotz;
	private Server server;

	/**
	 * Im Konstruktor erzeugen wir einen Server
	 * 
	 * @param server
	 */

	public Client(Server server) {
		this.server = server;
		this.hotzenplotz = new File(filename);
	}

	/**
	 * Es wird ein neuer Ausgabestrom erzeugt
	 * @param filename
	 * @return igenwie
	 */

	private OutputStream newOutputStream(String filename) {

		FileOutputStream igenwie = null;
		try {
			igenwie = new FileOutputStream(hotzenplotz);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return igenwie;
	}
/**
 * gibt  eine Ausgabeliste von Files zurück
 * @param fileListClient
 * @return fileAusgabe
 */
	public String OuputList(List<FileInfo> fileListClient) {
		String fileAusgabe = "";
		this.fileListClient = fileListClient;
		for (int i = 0; i < fileListClient.size(); i++) {
			fileAusgabe = fileListClient.toString();
		}
		return fileAusgabe;

	}

	/**
	 * Abrufen von Filenamen und dessen Verzeichnis
	 * @param filename
	 */
	public void retrieve(String filename) throws IOException {
		OutputStream destination = newOutputStream(filename);
		try {
			server.retrieveFile(filename, destination);
		} finally {
			destination.close();
		}

	}
/**
 * Der Client schickt eine Anfrage an den Server
 * @return fileListe
 */
	public List Serverrequest() {
		return fileListClient;

	}

}
