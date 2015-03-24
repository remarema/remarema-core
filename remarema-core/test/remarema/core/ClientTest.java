package remarema.core;

import java.io.OutputStream;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Die Klasse testet.
 * 
 * @author Regina
 *
 */
public class ClientTest {
	private Server server;
	private String workdir;
	private List<FileInfo> fileListClient;

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
	
	

	@Test
	public void fileListClient() {
		Client client = new Client();
		String directory = "C:\\Users\\Regina\\Documents\\Test_Diplom";
		client.listFilesClient(directory);
		

	}
	

	

	

}
