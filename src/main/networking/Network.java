package main.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Network {
	public ServerSocket ss;
	public List<Client> clients;
	
	public Network(int port) {
		clients = new ArrayList<>();
        try {
        	ss = new ServerSocket(port);
        } catch (IOException e) {e.printStackTrace();}
	}
	public void start() {
		while(true) {
			Client c = null;
			try {
				Socket s = ss.accept();
				c = new Client(s);
				new Thread(c).start();
				clients.add(c);
			} catch (Exception e) {clients.remove(c);}
			System.out.println(clients.size());
			if(ss.isClosed())
				break;
		}
	}
}