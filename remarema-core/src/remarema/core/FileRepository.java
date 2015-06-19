package remarema.core;

import java.io.File;
import java.util.*;

/**
 * In dieser Klasse wird im Konstruktor eine Wurzelverzeichnis mitgegeben, eine
 * weitere Methode {@link #createFileInfoFromFile(File)}erzeugt einne Datei mit
 * ihren Dateiinformationen.
 * 
 * @author Regina
 *
 */

public class FileRepository {

	private File rootDirectory;

	/**
	 * Bei dieser Methode wird der Dateiablage ein Wurzelverzeichnis mitgegeben
	 * ,wenn die Dateiablage neu aufgerufen
	 * 
	 * @param rootDirectory
	 *            ist ein Verzeichnis
	 */

	public FileRepository(File rootDirectory) {
		this.rootDirectory = rootDirectory;
	}

	/**
	 * Diese Methode erzeugt Dateiinformationen von einer Datei ,diese
	 * Informationen beinhalten den Namen der Datei,den Zeitstempel an dem die
	 * 
	 * Datei zuletzt verändert wurde und in welchen Verzeichnis sich die Datei
	 * befindet. {@link #createRelativeFileName(File)} bei dieser Methode wird
	 * 
	 * ein relativer und systemunabhängiger Pfad erzeugt. Die nächste Methode
	 * überprüft ob es sichbei dem File um eine Datei oder um ein Verzeichnis
	 * 
	 * handelt. Weiter wird ein Wurzelverzeichnis in einer Methode zurück
	 * geliefert. Die Methode {@link #getSubdirectory(String)} erzeugt eine
	 * 
	 * relativen Pfad ,der aus das Verzeichnis des Repositories zeugt. In dieser
	 * Klasse gibt es auch einer Klasse die überprüft ob die Datei in der List
	 * 
	 * ist. Die letzt Methode erstellt eine Datei die sich im gewälten Pfad
	 * befindet.
	 * 
	 * @param file
	 *            dieser Parameter wird benötig damit klar ist das, die erzeugte
	 *            Datei vom Typ Datei ist.
	 * @return die Informationen der Datei
	 */
	private FileInfo createFileInfoFromFile(File file) {
		FileInfo info = new FileInfo();
		info.setName(createRelativeFileName(file));

		info.setLastModified(file.lastModified());
		info.setDirectory(file.isDirectory());
		return info;
	}

	/**
	 * Diese Methode erzeugt aus dem übergebenen File relativen und
	 * systemunabhängigen Pfad.
	 * 
	 * Der relative Pfad wird erzeugt, indem das Wurzelverzeichniss
	 * abgeschnitten wird. Der Pfad ist systemunabhängig, da die Pfade zuerst in
	 * eine URI umgewandelt werden.
	 * 
	 * @param die
	 *            Informationen der Datei
	 * @return es wird der Neu erzeugte Dateiname zurückgegeben
	 */

	private String createRelativeFileName(File file) {
		String rootURI = rootDirectory.toURI().toString();
		String fileURI = file.toURI().toString();
		String name = fileURI.substring(rootURI.length());
		return name;
	}

	/**
	 * Diese Methode liefert eine Datei zurück . Die Datei befindet sich in eine
	 * Verzeichnis von dem der Pfad mitgegeben wird . Es wird über überprüft ob
	 * es sich bei den gefunden Objekt wirklich um eine Datei oder um ein
	 * Verzeichnis handelt.
	 * 
	 * @param path
	 *            Pfad in den die Datei zu finden ist
	 * @return wenn das Objekt eine Datei ist wird diese zurück geben,falls es
	 *         keine Datei ist erscheind eine Fehlermeldung
	 */

	public File getFile(String path) {
		File file = makeFileFromPath(path);
		if (file.isFile()) {
			return file;
		}
		String msg = "path not a valid file:" + path;
		throw new IllegalArgumentException(msg);
	}

	/**
	 * Hier wird das Wurzelverzeichnis zurück geliefert.
	 * 
	 * @return das Root Verzeichnis
	 */

	public File getRootDirectory() {
		return rootDirectory;
	}

	/**
	 * Erzeugt aus dem relativen Pfad ein {@link File} Objekt, welches auf ein
	 * Verzeichnis innerhalb des Repositories zeigt.
	 * 
	 * @throws IllegalArgumentException
	 *             Wird geworfen, wenn der Pfad kein Verzeichnis innerhalb des
	 *             Repositories ist.
	 * @param path
	 *            Ein relativer Pfad innerhalb des Repositories
	 * @return Ein File Objekt, welches den absoluten Pfad des Verzeichnisses
	 *         darstellt.
	 */
	public File getSubdirectory(String path) {
		File subdirectory = makeFileFromPath(path);
		if (subdirectory.isDirectory()) {
			return subdirectory;
		}
		String msg = "path not a valid directory:" + subdirectory;
		throw new IllegalArgumentException(msg);
	}

	/**
	 * Gibt die Liste der Vorhandenen Dateien eines reativen Verzeichnisses mit.
	 * Dann wird überprüft,ob die Datei in der Liste vorhanden ist.
	 * 
	 * @param directory
	 *            gibt an aus welchen Verzeichnis die Dateien sind.
	 * @return es wird die Liste der Dateien zurückgegeben
	 */
	public List<FileInfo> listFiles(String directory) {
		File subdirectory = getSubdirectory(directory);
		File[] sourceFiles = subdirectory.listFiles();
		List<FileInfo> fileList = new ArrayList<FileInfo>();
		for (File file : sourceFiles) {
			fileList.add(createFileInfoFromFile(file));
		}
		return fileList;
	}

	/**
	 * Es wird eine File erstellt ,das sich im gewählten Path befindet
	 * 
	 * @param path
	 *            ist der Ort in den die Verzeichnisse oder Dateien liegen
	 * @return die neue das im root Verzeichnis ist und dessen Pfad wir wissen
	 */

	public File makeFileFromPath(String path) {
		return new File(rootDirectory, path);
	}

}
