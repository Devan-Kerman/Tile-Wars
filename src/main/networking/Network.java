package main.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import main.Serverside;

public class Network implements Runnable {
	public ServerSocket ss;
	public List<Client> clients;
	
	/**
	 * Creates a new network on the specified port
	 * @param port
	 * 		port number
	 */
	public Network(int port) {
		clients = new ArrayList<>();
        try {
        	ss = new ServerSocket(port);
        } catch (IOException e) {
        	Serverside.logger.error(e.getMessage());
        	System.exit(0);
        }
	}
	public void run() {
		while(true) {
			Client c = null;
			try {
				Socket s = ss.accept();
				c = new Client(s);
				new Thread(c).start();
				clients.add(c);
			} catch (Exception e) {clients.remove(c);}
			if(ss.isClosed())
				break;
		}
	}
}