package remarema.core;

import static org.junit.Assert.*;

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
		String directory="./test/resources";
		List<FileInfo>list=client.listFilesClient(directory);
		assertFalse(list.isEmpty());
		System.out.println(list.get(0).getName());
		long lastModified=list.get(0).getLastModified();
		
		
		

	}

	

	

	

}
