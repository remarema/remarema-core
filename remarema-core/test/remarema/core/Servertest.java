package remarema.core;

import org.junit.Test;

public class Servertest {


@Test
public void fileListServer() {
	Server server = new Server();
	String directory = "C:\\Users\\Regina\\Documents\\Test_Server";
	server.listFilesServer(directory);
}
}
