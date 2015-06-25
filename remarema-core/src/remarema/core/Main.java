package remarema.core;

import java.io.File;

public class Main {
	/**
	 * Hier werden Dateien von server auf den CLient synchronisiert. Es wird für
	 * den Client und für den Server ein Pfad festgelegt. Alles was sich im
	 * Server Verzeichnis befindet ist nach einen Aufruf auch im
	 * CLientverzeichnis.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		File fileclient = new File("D:\\ClientVerzeichnis");
		File fileserver = new File("D:\\ServerVerzeichnis");
		FileRepository serverrepository = new FileRepository(fileserver);
		FileRepository clientrepository = new FileRepository(fileclient);
		Client client = new Client(clientrepository);
		Server server = new Server(serverrepository);
		client.synchronize(server);
	}
}
