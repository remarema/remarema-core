package remarema.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileRepository {

	public FileRepository() {

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
		File source = new File(directory);
		File[] sourceFiles = source.listFiles();

		List<FileInfo> fileList = new ArrayList<FileInfo>();
		for (File file : sourceFiles) {
			fileList.add(createFileInfoFromFile(file));
		}
		return fileList;
	}
	/**
	 * es werden Infos zu einen File erzeugt
	 * @param file
	 * @return die erzeugten Infos
	 */

	private FileInfo createFileInfoFromFile(File file) {
		FileInfo info = new FileInfo();
		info.setName(file.getName());
		info.setLastModified(file.lastModified());
		info.setDirectory(file.isDirectory());
		return info;
	}

	/**
	 * gib neue Files aus und überprüft sie
	 * @param directory
	 * @param other
	 * @return die vermissten Dateien
	 */
	public List<FileInfo> getNewFiles(String directory, List<FileInfo> other) {
		List<FileInfo> missingFiles = new ArrayList<FileInfo>();
		List<FileInfo> currentFiles = listFiles(directory);

		for (FileInfo current : currentFiles) {
			if (isFileNotInList(current, other)) {
				missingFiles.add(current);
			}
		}
		
		return missingFiles;
	}
	/**
	 * es wird nach Datei gesucht die nicht in der Liste sind
	 * @param current
	 * @param other
	 * @return wenn die Datei nicht inListe ist die abfrage richtig
	 */

	private boolean isFileNotInList(FileInfo current, List<FileInfo> other) {
		for (FileInfo e : other) {
			if (current.getName().equals(e.getName())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * ermittle dateien, die nicht in der Ablage sind
	 * 
	 * @param directory
	 * @param other
	 * @return result
	 */
	public List<FileInfo> getMissingFiles(String directory, List<FileInfo> other) {
		List<FileInfo> result = new ArrayList<FileInfo>();
		List<FileInfo> listFiles = listFiles(directory);
		for (FileInfo current : listFiles) {
			for (FileInfo e : other) {
				if (current.getName().equals(e.getName())) {
					result.add(current);
				}
			}
		}
		return result;
	}

	
		
	}


