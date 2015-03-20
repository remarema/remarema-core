package remarema.core;
/**
 * Diese Klasse enthält alle Infos die wichtig sind von Dateien
 * @author Regina
 *
 */

public class FileInfo {
	public String name;
	public int date;
	private boolean directory;
	/**
	 * Diese Methode gibt einen Namen aus
	 * @return das Feld name von Typ String
	 */
	public String getName(){
		return name;
	}
	/**
	 * Bei dieser Methode wird das Datum ausgeben
	 * @return Datum vom Typ int
	 */
	public int getDate(){
		return date;
	}
	/**
	 * Ist die Ausgabe ein Verzeichnis oder eine Datei
	 * Handelt es sich um ein Verzeichnis ist directory richtig
	 */
	public void isDirectory(){
		if(directory== true){
			System.out.println("Das ist eine Verzeichnis");
			
		}
		else{
			System.out.println("Das ist eine Datei");
			
		}
	}
	

}
