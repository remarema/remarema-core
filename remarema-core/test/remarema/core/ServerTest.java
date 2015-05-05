package remarema.core;

import java.io.*;

import org.junit.Test;

public class ServerTest {

	@Test
	/**
	 * neuer Server wird erstelle diesen wird ein Pfad mitgegeben
	 * in disen Pfad wird der inhalt der DAtei gespeichert;
	 * @throws IOException
	 */
	public void fileLists() throws IOException {

		FileRepository repository = new FileRepository(new File(
				"test/resources"));
		Server server = new Server(repository);

		String path = repository.getRootDirectory().getAbsolutePath()
				+ "\\outputstream.txt";
		OutputStream stream = new FileOutputStream(path);
		server.retrieveFile("halloWelt", stream);
		stream.close();

	}
}
