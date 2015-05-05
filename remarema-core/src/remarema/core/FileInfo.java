package remarema.core;

import java.util.List;

/**
 * Diese Klasse enthält alle Infos die wichtig sind von Dateien die Methoden
 * können in anderen Klassen verwendet werden
 * 
 * @author Regina
 *
 */

public class FileInfo {
	private String name;
	private long lastModified;
	private boolean directory;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getLastModified() {
		return lastModified;
	}

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	public boolean isDirectory() {
		return directory;
	}

	public void setDirectory(boolean directory) {
		this.directory = directory;
	}

	public boolean isInList(List<FileInfo> other) {
		for (FileInfo e : other) {
			if (getName().equals(e.getName())) {
				return true;
			}
		}
		return false;
	}

}
