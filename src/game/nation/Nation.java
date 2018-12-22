package game.nation;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import game.resources.Inventory;

public class Nation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5982268049046188288L;
	public List<TilePoint> tiles;
	public Inventory inv;
	public int id;
	public File homedir;
	private static Random r;
	
	static {
		r = new Random();
		File f = new File("NationData/");
		if (!f.exists())
			f.mkdir();
	}

	public Nation() {
		tiles = new ArrayList<>();
		inv = new Inventory();
		int gid;
		do {
			gid = r.nextInt();
		} while(!new File("NationData/"+gid+".nation").exists());
		homedir = new File("NationData/"+(id=gid)+".nation");
		
	}
	public byte randNeg() {
		return (byte) (Math.random()>.5?-1:1);
	}
	
	
	
	

}
