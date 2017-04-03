package net;

import java.net.DatagramSocket;

public class Server {
	
	private DatagramSocket[] socket;
	private Thread listener, worker;
	private int active = 0;
	private boolean listening;
	
	public Server(int port) {
		DatagramSocket[] connections = new DatagramSocket[3];
	}
	public void initialize() {
		listener = new Thread(() -> listen(), "Listener");
		worker = new Thread(() -> process(), "Worker");
		
		listener.start();
	}
	
	private void listen() {
		while(listening) {
			
		}
	}
	
	public void process() {
		
	}
	public void sendData() {
		
	}

	
	
}
