package server.network;

import ai.play.devtech.core.api.structures.AtomicArrayList;
import ai.play.devtech.tilewars.DLogger;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Network {

	List<Client> clients;

	int port;
	ServerSocket socket;

	public Network(int port) {
		try {
			socket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		clients = new AtomicArrayList<>();
	}

	public void acceptClient() {
		try {
			Socket sock = socket.accept();
			DLogger.debug("New client");
			Client client = new Client(sock);
			client.setKill(() -> clients.remove(client));
			clients.add(client);
		} catch (IOException e) {
			DLogger.error("Unable to accept client!");
			e.printStackTrace();
		}
	}

}
