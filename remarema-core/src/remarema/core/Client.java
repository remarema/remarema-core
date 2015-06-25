package remarema.core;

import java.io.*;
import java.util.Collections;
import java.util.List;

/**
 * Die Klasse Client repräsentiert einen Client,der sich Dateien vom Server
 * herunterladet.Der Client soll in einem Verzeichnis denselben Inhalt haben wie
 * der Server an eine festgelegten Verzeichnis,
 * 
 * @author Regina
 *
 */
public class Client {

	private FileRepository repository;

	public Client(FileRepository repository) {
		this.repository = repository;
	}

	/**
	 * Es wird überprüft das Verzeichnis existiert.
	 *
	 * 
	 * @param directory
	 * @return wenn das File existiert sein Verzeichnis ansonsten eine Sammlung
	 *         einer leeren Liste.
	 */
	public List<FileInfo> listFiles(String directory) {
		if (repository.makeFileFromPath(directory).exists()) {
			return repository.listFiles(directory);
		}
		return Collections.emptyList();
	}

	/**
	 * Hier wird der Pfad eines leeren Verzeichnis entfernt. Dazu wird die
	 * Methode RemoveFile verwendet in der alle nicht aktullen Files gelöscht
	 * werden.
	 * 
	 * @param path
	 */

	public void remove(String path) {
		removeFile(repository.getFile(path));
	}

	/**
	 * Diese Methode entfernt Dateien Es wird überprüft ob eine Datei in diesem
	 * Verzeichnis ist,wenn kein Datei darin ist wird das Verzeichnis entfernt
	 * 
	 * @param file
	 *            ein Ojekt von gleichnamigen Typ File
	 */

	private void removeFile(File file) {
		if (file.isDirectory()) {
			removeDirectory(file);
		} else {
			file.delete();
		}
	}

	/**
	 * Diese Methode entfernt Verzeichnise die leer sind. Der Unterschied zu @link
	 * removeFile ist das hier mit einer for-each Schleife gearbeitet wird und
	 * erst im letzten Schleifendurchlauf wird das Verzeichnis gelöscht.
	 * 
	 * @param directory
	 *            Verzeichnisse in den Dateinen liegen.
	 */
	private void removeDirectory(File directory) {
		File[] directoryContents = directory.listFiles();
		for (File file : directoryContents) {
			removeFile(file);
		}
		directory.delete();
	}

	/**
	 * Hier wird ein neues Stream erzeugt in den den Pfad aller existierten
	 * Files geschrieben wird.
	 * 
	 * 
	 * @param path
	 * @return neu Stream in den Dateien geschrieben wurden.
	 */
	public OutputStream createOutputStream(String path) {
		File file = repository.makeFileFromPath(path);
		if (file.exists()) {
			String msg = "can't create output stream for file:" + path;
			throw new RuntimeException(msg);
		}
		try {
			makeParentDirectory(file);
			return new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			String msg = "can't create output stream for file:" + path;
			throw new RuntimeException(msg);
		}
	}

	/**
	 * Es wird wird überprüft ,ob das Elternverzeichnis der Datei des
	 * file-Parameters existiert, wenn es nicht exisiert wird es neu angelegt.
	 * 
	 * @param file
	 */
	private void makeParentDirectory(File file) {
		File parent = file.getParentFile();
		if (!parent.exists()) {
			parent.mkdirs();
		}
	}

	/**
	 * Hier wird überprüft ob das File aktuell ist.
	 * 
	 * @param fileInfo
	 * @return true wird zurückgegen wenn das File existiert
	 */
	public boolean isFileUpToDate(FileInfo fileInfo) {
		File file = repository.makeFileFromPath(fileInfo.getName());
		if (file.exists()) {
			return true;
		}
		return false;
	}

	/**
	 * Mit "." wird im die erste Stelle des relativen Verzeichnises von Server
	 * auf den Client syncronisiert.
	 * 
	 * @param server
	 */
	public void synchronize(Server server) {
		synchronizeDirectory(server, ".");
	}

	/**
	 * Hier werden alle Verzeichnisse Schritt für Schritt durchlafen. Aus dem
	 * Unterverzeichniss da gerade an der Reihe ist werden die nicht aktuellen
	 * Files kopiert.Ist man mit eine Verzeichnis fertig spring ma eine Stufe
	 * höher und erledigt mit jeden weiten Verzeichniss das selbe. Zum Schluss
	 * werden die Datein im Wurzelverzeichnis syncronisiert.
	 * 
	 * @param server
	 * @param directory
	 */
	public void synchronizeDirectory(Server server, String directory) {
		List<FileInfo> serverFiles = server.listFiles(directory);
		List<FileInfo> clientFiles = listFiles(directory);

		removeObsoleteFiles(serverFiles, clientFiles);

		for (FileInfo serverFile : serverFiles) {
			if (serverFile.isDirectory()) {
				synchronizeDirectory(server, serverFile.getName());
			} else {
				synchronizeFile(server, serverFile);
			}
		}

	}

	/**
	 * Dies Methode syncronisiert Files von Server auf den CLient, Es wird
	 * überprüft ob das File aktuell ist,wenn dies zutrifft wird nichts
	 * syoncronisiert.Ansonsten wird der Pfad des Files vom Server in einen
	 * Outputstream geschrieben. Stehem nicht aktuellen Files in diesen stream
	 * wird er geschlossen.
	 * 
	 * @param server
	 * @param fileInfo
	 *            alle wichitgen Indormationen des Files
	 */
	private void synchronizeFile(Server server, FileInfo fileInfo) {
		if (isFileUpToDate(fileInfo)) {
			return;
		}
		String fileName = fileInfo.getName();
		OutputStream outputStream = createOutputStream(fileName);
		try {
			server.retrieveFile(fileName, outputStream);
		} finally {
			close(outputStream);
		}
	}

	/**
	 * Hier wird der Outputstream geschlossen ,falls es nicht geschlossen werden
	 * kann wird ein Fehlermedlung zurückgegeben
	 * 
	 * @param outputStream
	 *            sagt aus das in den Strom geschrieben werden kann.
	 */

	private void close(OutputStream outputStream) {
		try {
			outputStream.close();
		} catch (IOException e) {
			String msg = "OutputStream konnte nicht geschlossen werden";
			throw new RuntimeException(msg, e);
		}
	}

	/**
	 * Hier wird überprüft ob ein File veraltet ist ,wenn es veraltet ist wird
	 * es entfernt
	 * 
	 * @param serverFiles
	 * @param clientFiles
	 */
	private void removeObsoleteFiles(List<FileInfo> serverFiles,
			List<FileInfo> clientFiles) {
		for (FileInfo clientFile : clientFiles) {
			if (!clientFile.isInList(serverFiles)) {
				remove(clientFile.getName());
			}
		}
	}

}
