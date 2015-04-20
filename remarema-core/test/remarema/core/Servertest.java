package remarema.core;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.junit.Test;

public class Servertest {


@Test
public void fileLists() throws FileNotFoundException {
	Server server = new Server();
	OutputStream stream = new FileOutputStream("./test/resources/outputstream.txt");
	server.Fileausgabe("halloWelt", stream);
	
}
}
