package remarema.core;

public class Main {

	public static void main(String[] args) {

		Client client = new Client();
		Server server = new Server();
		client.synchronize(server);
	}
}
