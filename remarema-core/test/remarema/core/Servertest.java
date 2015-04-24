package remarema.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Test;

public class Servertest {


@Test
/**
 * neuer Server wird erstelle diesen wird ein Pfad mitgegeben
 * in disen Pfad wird der inhalt der DAtei gespeichert;
 * @throws IOException
 */
public void fileLists() throws IOException {
	
	Server server = new Server(new File("test/resources/"));
	String path = server.getDirectory().getAbsolutePath() + "\\outputstream.txt";
	OutputStream stream = new FileOutputStream(path);
	server.retrieveFile("halloWelt", stream);
	stream.close();
	
}
}
