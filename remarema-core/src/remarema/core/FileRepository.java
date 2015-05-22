package remarema.core;

import java.io.File;
import java.util.*;

public class FileRepository {

	private File rootDirectory;

	public FileRepository(File rootDirectory) {
		this.rootDirectory = rootDirectory;
	}

	/**
	 * Es werden Infos zu den jeweiligen File passent erzeugt.
	 * 
	 * @param file
	 * @return die erzeugten Infos
	 */
	private FileInfo createFileInfoFromFile(File file) {
		FileInfo info = new FileInfo();
		info.setName(createRelativeFileName(file));
		info.setLastModified(file.lastModified());
		info.setDirectory(file.isDirectory());
		return info;
	}

	private String createRelativeFileName(File file) {
		String rootURI = rootDirectory.toURI().toString();
		String fileURI = file.toURI().toString();
		String name = fileURI.substring(rootURI.length());
		return name;
	}

	public File getFile(String path) {
		File file = makeFileFromPath(path);
		if (file.isFile()) {
			return file;
		}
		String msg = "path not a valid file:" + path;
		throw new IllegalArgumentException(msg);
	}

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
		String msg = "path not a valid directory:" + path;
		throw new IllegalArgumentException(msg);
	}

	/**
	 * Gibt die Liste der Vorhandenen Dateien eines Verzeichnis mit Hilfe von
	 * FileInfo zurück
	 * 
	 * @param directory
	 *            gibt an aus welchen Verzeichnis die Dateien sind.
	 * @return es wird fileList zurückgegeben
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
	 * Es  wird eine File erstellt ,das sich im gewählten Path befindet
	 * @param path ist der Ort in den die Verzeichnisse oder Dateien liegen
	 * @return die neue  das im root Verzeichnis ist und dessen Pfad wir wissen
	 */

	public File makeFileFromPath(String path) {
		return new File(rootDirectory, path);
	}

}
