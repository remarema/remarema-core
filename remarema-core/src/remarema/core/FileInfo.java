package remarema.core;

/**
 * Diese Klasse enth�lt alle Infos die wichtig sind von Dateien
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

}
