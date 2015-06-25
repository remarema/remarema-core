package remarema.core;

import java.io.*;
import java.util.List;

/**
 * Die Klasse repräsentiert einen Server Vom Server werden die Dateien und
 * Verzeichnisse heruntergeladen ,die der Client noch nicht hat.
 * 
 * @author Regina
 *
 */

public class Server {

	private FileRepository repository;

	/**
	 * Ein Verzeichnis wird im Konstruktor mitgegeben.
	 * 
	 * @param ein
	 *            FileRepository
	 * 
	 */

	public Server(FileRepository fileRepository) {
		this.repository = fileRepository;
	}

	/**
	 * Diese Methode schließt Inputstream sie gibt eine Fehlermeldung aus, wenn
	 * der Strom nicht geschlossen wird.
	 * 
	 * @param inputStream
	 *            ist da um den Strominhalt zu lesen
	 * 
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
	 * Diese Methode kopiert die Datei in einen Ouputstream. Falls bei kopieren
	 * der Datei ein Fehler auftritt wird eine <code>RuntimeException</code>
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
	 * Aus dem übergeben Parameter File wird ein Inputstream erzeugt.
	 * 
	 * Im Fehlerfall wird eine @IllegalArgumentException ausgelöst.
	 * 
	 * Wird @FileNotFoundException geworfen mit eine schriftlichen Fehlermeldung
	 * ausgegeben
	 * 
	 * @throws neue
	 *             Fehlermeldung
	 * 
	 *             Als @param wird File mitgegeben
	 * @return den neu erzeugten Inputstream mit dem Parameter File
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
	 * Hier wird eine neue Liste erzeugt die dann später beim Vergleichen hilft.
	 * 
	 * @param path
	 *            ist ein Objekt vom Typ string ,damit der Pfad der Datei
	 *            gemeint
	 * @return Eine Liste mit seinen Pfad die sich in der Ablage befindet
	 */

	public List<FileInfo> listFiles(String path) {
		return repository.listFiles(path);
	}

	/**
	 * Es werden Files empfangen und an eine anderen Bestimmungort kopiert.
	 * 
	 * @param filename
	 *            repräsentiert die Datei.
	 * @param destination
	 *            Bestimmungsort.
	 */
	public void retrieveFile(String filename, OutputStream destination) {
		File sourceFile = repository.getFile(filename);
		copyFileToDestination(sourceFile, destination);
	}

}
