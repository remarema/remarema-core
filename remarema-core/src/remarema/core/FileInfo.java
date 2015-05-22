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

	/**
	 * Diese Methode getName kann in anderen Klassen benutzt werden um den Namen
	 * den Files aufzurufen
	 * 
	 * @return der Name des Files wird zurückgegeben
	 */
	public String getName() {
		return name;
	}

	/**
	 * Bei dieser Setter Methode ist möglich den Namen zu ändern in anderen
	 * Klassen
	 */

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Mit GetLastMofified kann auch LastModified in anderen Klassen verwendet
	 * werden
	 * 
	 * @return
	 */
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
	/**
	 *Hier werden zwei Listen verglichen und  wenn eine Datei oder ein Verzeichnis in beiden Liste vorhanden ist 
	 *wird true zurückgegeben.
	 * @param other
	 * @return true wenn Elemete  gleich
	 */
	

	public boolean isInList(List<FileInfo> other) {
		for (FileInfo e : other) {
			if (getName().equals(e.getName())) {
				return true;
			}
		}
		return false;
	}

}
