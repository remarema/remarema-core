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
	 * wenn der Strom nicht geschlossen wird.
	 * 
	 * @param inputStream
	 *            der Parameter in eine Objekt von Typ InputStream
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
	 * Diese Methode kopiert die Datei in einen Ouputstream. Falls bei
	 * kopieren der Datei ein Fehler auftritt wird eine <code>RuntimeException</code>
	 * geworfen und ein Fehlermeldung ausgegeben
	 * 
	 * @param inputFile
	 *            ist vom Typ File und repräsentiert die Dateieingaben
	 * @param destination
	 *            der Bestimmungsort ist vom Typ Ausgabestrom
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

	/**
	 * Bei dieser Methode wird ein Eingabestrom erzeugt,wenn der dieser nicht
	 * gefunden wird kann
	 * 
	 * @FileNotFoundException geworfen mit eine schriftlichen
	 * Fehlermeldung ausgegeben
	 * 
	 * @throws IllegalArgumentException es wird wieder die Fehlermeldung
	 * 
	 * @param file eine Objekt des gleichnamigen Typ File
	 * @return neuen Eingabestrom mit dem Parameter File
	 */

	private InputStream createInputStream(File file) {
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			String msg = "can't create input stream";
			throw new IllegalArgumentException(msg, e);
		}
	}
	/**
	 * Die Methode den Pfad einer Liste zurück.
	 * 
	 * @param path ist ein Objekt vom Typ string ,damit der Pfad  der Datei gemeint
	 * @return Eine Liste mit seinen Pfad die sich in der Ablage befindet
	 */

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
