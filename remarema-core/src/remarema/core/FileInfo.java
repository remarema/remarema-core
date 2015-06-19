package remarema.core;

import java.util.List;

/**
 * Diese Klasse enthält Informationen über eine Datei. Die Datei bezitzt einen
 * Namen und einen Zeitstempel an den es zu letzt verändert wurde. Weiter gibt
 * uns diese Klasse Informationen ob es sich bei der Datei um eine Datei oder
 * ein Verzeichnis handelt. Mit der Methode {@link #isInList(List)} kann geprüft
 * werden, ob ein FileINfo Objekt in einer Liste vorhanden ist.
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
	 * Hier legen wir fest das Zeitstempel der letzten Veränderung der Datei .
	 * 
	 * @param lastModified
	 *            Datum der letzten Veränderung.
	 */

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	/**
	 * Hier wird das Directory zurückgegebn,wenn es sich um ein Directory
	 * handelt.
	 * 
	 * @return wenn true zurückgeben wird ist es ein Verzeichnis.
	 */

	public boolean isDirectory() {
		return directory;
	}

	/**
	 * Bei dieser Methode kann festgelegt werden das die Datei ein Verzeichnis
	 * ist.
	 *
	 */
	public void setDirectory(boolean directory) {
		this.directory = directory;
	}

	/**
	 * Hier werde die Elemente der Liste durchlaufen .Bei jeden dieser Elemente
	 * wird überprüft ob sein Name gleich den festgelegeten Namen ist.
	 * 
	 * @param fileinfoList
	 * 
	 * @return wenn die Fileinnformatione übereinstimmen wird true zurückgegeben
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
