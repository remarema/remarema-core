package remarema.core;

import static org.junit.Assert.*;

import java.io.IOException;
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
	private List<FileInfo> fileList;

	@Before
	public void setup() {
		server = Mockito.mock(Server.class);
		workdir = System.getProperty("java.io.tmpdir");

	}

	@Test
	public void downloadOneFile() throws IOException {
		Client client = new Client(server);
		client.retrieve("Hallo World");
		Mockito.verify(server).retrieveFile(Mockito.anyString(),
				Mockito.any(OutputStream.class));

	}
	
	

	@Test
	public void fileList() {
		FileRepository re = new FileRepository();
		String directory="./test/resources";
		List<FileInfo>list=re.listFiles(directory);
		assertFalse(list.isEmpty());
		System.out.println(list.get(0).getName());
		long lastModified=list.get(0).getLastModified();
		
		
		

	}

	

	

	

}
