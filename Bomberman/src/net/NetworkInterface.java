package net;

public class NetworkInterface {
	
	private Server server;
	private Client client;
	
	private boolean isServer;
	
	public NetworkInterface(boolean isServer, String ip, int port) {
		this.isServer = isServer;
		if(isServer) {
			server = new Server(port);
		}else {
			client = new Client(ip, port
					);
		}
	}
	
}
