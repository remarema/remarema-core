package remarema.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

	private File directory;

	private FileRepository filerepository;

	/**
	 * Verzeichnis wird im Konstruktor mitgegeben
	 * 
	 * @param directory
	 * @throws FileNotFoundException
	 */

	public Server(File directory) throws FileNotFoundException {
		assertThatRootDirectoryExitst(directory);
		this.directory = directory;
		this.filerepository = new FileRepository();
	}

	/**
	 * es wird überprüft ob das Wurzelverzeichnis vorhanden ist
	 * 
	 * @param directory
	 * @throws FileNotFoundException
	 */

	private void assertThatRootDirectoryExitst(File directory)
			throws FileNotFoundException {
		if (!directory.exists()) {
			throw new FileNotFoundException(
					"Wurzelverzeichnis existiert nicht:"
							+ directory.getAbsolutePath());
		}
	}
/**
 * Alle Dateien von der Eingabedatei werden in den Ausgabedateistrom kopiert
 * @param inputFile
 * @param destination
 * @throws FileNotFoundException
 */
	private void copyFileToDestination(File inputFile, OutputStream destination)
			throws FileNotFoundException {
		InputStream inputStream = new FileInputStream(inputFile);
		try {
			int rc = inputStream.read();

			while (rc != -1) {
				destination.write(rc);
				rc = inputStream.read();
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
	 * Diese Methode schließt den Eingabestrom 
	 * sie gibt eine Fehlermeldung aus, wenn der Strom nicht geschlossen wird
	 * @param inputStream
	 */

	private void closeInputStream(InputStream inputStream) {
		try {
			inputStream.close();
		} catch (IOException e) {
			throw new RuntimeException(
					"InputStream konnte nicht geschlossen werden", e);
		}
	}

	/**
	 * Files werden aufgerufen und der Inhalt wird in eine andere Klasse
	 * gespeichert
	 * 
	 * @param filename
	 * @param destination
	 * @return result
	 * @throws FileNotFoundException
	 */

	public void retrieveFile(String filename, OutputStream destination)
			throws FileNotFoundException {
		List<FileInfo> fileLists = filerepository.listFiles(directory
				.getAbsolutePath());

		for (FileInfo result : fileLists) {
			if (result.getName().equals(filename)) {
				File inputFile = new File(directory, filename);
				copyFileToDestination(inputFile, destination);
				return;
			}
		}

		throw new FileNotFoundException("Die Datei ist nicht vorhanden:"
				+ filename);
	}
	/**
	 * gibt Verzeichnis
	 * @return Verzeichnis
	 */

	public File getDirectory() {
		return directory;
	}
/**
 * setzt Verzeichnis
 * @param directory
 */
	public void setDirectory(File directory) {
		this.directory = directory;
	}

}
