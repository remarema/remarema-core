package remarema.core;

import java.io.*;
import java.util.List;

/**
 * Die Klasse repräsentiert einen Server
 * 
 * @author Regina
 *
 */

public class Server {

	private FileRepository repository;

	/**
	 * Verzeichnis wird im Konstruktor mitgegeben
	 * 
	 * @param directory
	 * @throws FileNotFoundException
	 */

	public Server(FileRepository fileRepository) {
		this.repository = fileRepository;
	}

	/**
	 * Diese Methode schließt den Eingabestrom sie gibt eine Fehlermeldung aus,
	 * wenn der Strom nicht geschlossen wird
	 * 
	 * @param inputStream
	 */

	private void closeInputStream(InputStream inputStream) {
		try {
			inputStream.close();
		} catch (IOException e) {
			String msg = "InputStream konnte nicht geschlossen werden";
			throw new RuntimeException(msg, e);
		}
	}

	/**
	 * Alle Dateien von der Eingabedatei werden in den Ausgabedateistrom kopiert
	 * 
	 * @param inputFile
	 * @param destination
	 * @throws FileNotFoundException
	 */
	private void copyFileToDestination(File inputFile, OutputStream destination) {
		InputStream inputStream = createInputStream(inputFile);
		try {
			byte[] buffer = new byte[1024];

			int rc = inputStream.read(buffer);
			while (rc != -1) {
				destination.write(buffer, 0, rc);
				rc = inputStream.read(buffer);
			}

			destination.flush();
		} catch (IOException e) {
			throw new RuntimeException(
					"Beim Kopieren der Datei ist ein Fehler aufgetreten", e);
		} finally {
			closeInputStream(inputStream);
		}
	}

	private InputStream createInputStream(File file) {
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			String msg = "can't create input stream";
			throw new IllegalArgumentException(msg, e);
		}
	}

	public List<FileInfo> listFiles(String path) {
		return repository.listFiles(path);
	}

	/**
	 * Files werden aufgerufen und der Inhalt wird in eine andere Klasse
	 * gespeichert
	 * 
	 * @param filename
	 * @param destination
	 * @return result
	 */
	public void retrieveFile(String filename, OutputStream destination) {
		File sourceFile = repository.getFile(filename);
		copyFileToDestination(sourceFile, destination);
	}

}
