package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;

public class Network {
	public ServerSocket serversocket;
	public ObjectInputStream ois;
	public ObjectOutputStream oos;
	
	public Network(int port) {
		try {
			serversocket = new ServerSocket(port);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void client() {
		
	}
}
