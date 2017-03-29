package net;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client implements Runnable {
	
	private DatagramSocket socket;
	private InetAddress address;
	
	public Client(String ip, int port) {
		try {
			address = InetAddress.getByName(ip);
			socket = new DatagramSocket();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
	}
	
	
	@Override
	public void run() {
		while(true) {
			
		}
	}
	
}
