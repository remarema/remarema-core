package remarema.core;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
/**
 * 
 * @author Regina
 *
 */
public class Client {

	private Server server;

	public Client(Server server) {
		this.server = server;
	}
/**
 * 
 * @param filename
 */
	public void retrieve(String filename) {
		OutputStream destination = newOutputStream(filename);
		server.Fileausgabe(filename, destination);

	}

	private OutputStream newOutputStream(String filename) {

		File hotzenplotz = new File(filename);

		FileOutputStream igenwie = null;
		try {
			igenwie = new FileOutputStream(hotzenplotz);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return igenwie;
	}
	

}
