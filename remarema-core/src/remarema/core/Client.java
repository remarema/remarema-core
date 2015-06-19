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
	 * Es wird
	 * 
	 * @param directory
	 * @return
	 */
	public List<FileInfo> listFiles(String directory) {
		if(repository.makeFileFromPath(directory).exists()){
			return repository.listFiles(directory);
		}
		return Collections.emptyList();
	}

	/**
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
	 * Diese Methode erzeugt einen Ausgabestrom
	 * 
	 * 
	 * @param path
	 * @return
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
	 * Es wird wird überprüft ,ob das Elternverzeichnis der Datei des file-Parameters existiert, wenn es nicht
	 * exisiert wird es neu angelegt.
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
	 * 
	 * @param fileInfo
	 * @return
	 */
	public boolean isFileUpToDate(FileInfo fileInfo) {
		File file = repository.makeFileFromPath(fileInfo.getName());
		if (file.exists()) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param server
	 */
	public void synchronize(Server server) {
		synchronizeDirectory(server, ".");
	}

	/**
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
	 * 
	 * @param server
	 * @param fileInfo
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
	 * 
	 * @param outputStream
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
