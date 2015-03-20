package remarema.core;

import java.io.OutputStream;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
/**
 * Die Klasse testet.
 * @author Regina
 *
 */
public class ClientTest {
	private Server server;
	private String workdir;

	@Before
	public void setup() {
		server = Mockito.mock(Server.class);
		workdir = System.getProperty("java.io.tmpdir");

	}

	@Test
	public void downloadOneFile() {
		Client client = new Client(server);
		client.retrieve("Hallo World");
		Mockito.verify(server).Fileausgabe(Mockito.anyString(),
				Mockito.any(OutputStream.class));
		

	}

}
