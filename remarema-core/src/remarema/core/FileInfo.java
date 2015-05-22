package remarema.core;

import java.util.List;

/**
 * Diese Klasse enthält Informationen über eine Datei. Die Datei bezitzt einen
 * Namen und ein Datum an den es zu letzt verändert wurde. Weiter gibt uns diese
 * Klasse Informationen ob es sich bei der Datei um eine Datei oder ein
 * Verzeichnis handelt.Schlussendlich wird acuh noch überprüft ob diese Datei in
 * einer Liste ist.
 * 
 * @author Regina
 *
 */

public class FileInfo {
	private String name;
	private long lastModified;
	private boolean directory;

	/**
	 * Diese Methode liefert den Namen der Datei zurück
	 * 
	 * @return der Name des Files wird zurückgegeben
	 */
	public String getName() {
		return name;
	}

	/**
	 * Hier legen wir den Namen der Datei fest.
	 */

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * In dieser Methode wird das aktuelle Datum der Datei zurückgegeben.
	 * 
	 * @return das aktuelleste Datum dieser Datei.
	 */
	public long getLastModified() {
		return lastModified;
	}

	/**
	 * Hier legen wir fest das Datum der letzten Veränderung der Datei .
	 * 
	 * @param lastModified
	 *            Datum der letzten Veränderung.
	 */

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	/**
	 * Über diese Methode kann abgefragt werden ob die Datei eine Verzeichnis
	 * oder eine Datei ist.
	 * 
	 * @return wenn true ist die Datei ein Verzeichnis.
	 */

	public boolean isDirectory() {
		return directory;
	}

	/**
	 * Bei dieser Methode kann festgelegt werdenob diese Datei ein Verzeichnis
	 * ist.
	 *
	 */
	public void setDirectory(boolean directory) {
		this.directory = directory;
	}

	/**
	 * Hier wird überprüft ob die Datei in einer Liste vorhanden ist
	 * 
	 * @param fileinfoList
	 * 
	 * @return wenn true zurückgegeben wird ist die Datei in der Liste
	 */

	public boolean isInList(List<FileInfo> fileinfoList) {
		for (FileInfo e : fileinfoList) {
			if (getName().equals(e.getName())) {
				return true;
			}
		}
		return false;
	}

}
