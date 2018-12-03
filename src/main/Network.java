package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import main.networking.Client;

public class Network {
	public ServerSocket ss;	
	
	public Network(int port) {
        try {
        	ss = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	public void start() {
		while(true) {
			try {
				Socket s = ss.accept();
				new Thread(new Client(s)).start();
			} catch (Exception e) {e.printStackTrace();return;}
			if(ss.isClosed())
				break;
		}
	}
}
