package main.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import main.DLogger;

/**
 * A network, manages clients
 * @author devan
 *
 */
public class Network implements Runnable {
	private ServerSocket ss;
	public final List<Client> clients;
	
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
        	DLogger.error(e.getMessage());
        	System.exit(0);
        }
	}
	public void run() {
		do {
			Client c = null;
			try {
				Socket s = ss.accept();
				c = new Client(s);
				new Thread(c).start();
				clients.add(c);
			} catch (Exception e) {clients.remove(c);}
		} while (!ss.isClosed());
	}
}