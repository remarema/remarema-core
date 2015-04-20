package remarema.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileRepository {

	public FileRepository() {

	}
	/** Gibt die Liste der Vorhandenen Dateien eines Verzeichnis mit Hilfe von FileInfo zurück
	 * 
	 * @param directory
	 *            gibt an aus welchen Verzeichnis die Dateien sind.
	 * @return es wird fileList zurückgegeben
	 */

	public List<FileInfo> listFiles(String directory) {
		List<FileInfo> fileList = new ArrayList<FileInfo>();
		File source = new File(directory);
		File[] sourceFiles = source.listFiles();
		for (File file : sourceFiles) {

			FileInfo info = new FileInfo();
			info.setName(file.getName());
			info.setLastModified(file.lastModified());
			info.setDirectory(file.isDirectory());
			fileList.add(info);

		}
		return fileList;
	}

	/**
	 * ermittle dateien, die noch nicht in der übergebenen Liste other vorhanden
	 * sind.
	 * die  nicht vorhandene Dateien in result speichern
	 * 
	 * @param directory
	 * @param other
	 * @return result
	 */
	public List<FileInfo> getNewFiles(String directory, List<FileInfo> other) {
		List<FileInfo> result = new ArrayList<FileInfo>();

		List<FileInfo> listFiles = listFiles(directory);

		for (FileInfo current : listFiles) {
			boolean fileExists = false;
			for (FileInfo e : other) {
				if (current.getName().equals(e.getName())) {
					fileExists = true;
				}
			}
			if (fileExists) {

			}
			
			else {
				result.add(current);

			}

		}
		return result;
	}

	/**
	 * ermittle dateien, die nicht im Repository sind
	 * 
	 * @param directory
	 * @param other
	 * @return result
	 */
	public List<FileInfo> getMissingFiles(String directory, List<FileInfo> other) {
		List<FileInfo> result = new ArrayList<FileInfo>();
		List<FileInfo> listFiles = listFiles(directory);
		for (FileInfo current : listFiles) {
			boolean fileDoMuch = false;
			for (FileInfo e : other) {
				if (current.getName().equals(e.getName())) {
					fileDoMuch = true;
				}
			}
			if (fileDoMuch) {

				result.add(current);
			}

		}
		return result;
	}

}
