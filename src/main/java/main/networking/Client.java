package main.networking;

import java.awt.Point;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import game.GlobalData;
import game.nation.Nation;
import game.nation.TilePoint;
import game.tile.Tile;
import main.Boot;
import main.DLogger;

/**
 * A connection to a client
 * @author devan
 *
 */
public class Client implements Runnable {
	public Input ois;
	public Output oos;
	ClientCommands cc;
	Nation n;
	
	//Client's Chunk Coordinate
	Point p = new Point();
	
	ArrayList<TileUpdate> edits;
	public Client(Socket s) throws IOException {
		DLogger.info("New Client!");
		edits = new ArrayList<>();
		oos = new Output(s.getOutputStream());
		oos.flush();
		ois = new Input(s.getInputStream());
		DLogger.info("Client Connection Established!");
		cc = new ClientCommands(this);
	}
	
	boolean loggedin = false;
	/*
	 * Op codes 
	 * 0 cx -> cy -> <- 3x3 Chunk Array 
	 * 1 <- Tile updates
	 * 2 -> distance <- accept
	 * 3 <- 0 (ping testing)
	 */
	public void run() {
		try {
			while (true) {
				Integer opcode = GlobalData.kryo.readObject(ois, Integer.class);
					DLogger.info(String.valueOf(opcode));
				if (opcode == 0 && loggedin) {
					cc.getChunks();
				} else if (opcode == 1 && loggedin) {
					cc.getUpdates();
				} else if(opcode == 2 && loggedin) {
					cc.changeRender();
				} else if(opcode == 3 && loggedin) {
					cc.ping();
				} else if(opcode == 4) {
					cc.register();
				} else if(opcode == 5) {
					cc.login();
				} else {
					DLogger.warn("Connection Aborted!");
					return;
				}
			}
		} catch (Exception e) {
			Boot.mainet.clients.remove(this);
		}
	}
	
	/**
	 * adds a tile update to the client, automatically discards edits that aren't in range
	 * @param t
	 * 		The location of the update
	 */
	public void addUpdate(TilePoint t, Tile tile) {
		if(p.distance(t.chunk) <= cc.renderdistance)
			edits.add(new TileUpdate(t, tile));
	}
	
}
