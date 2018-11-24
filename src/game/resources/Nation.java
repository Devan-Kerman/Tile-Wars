package game.resources;

import main.Boot;

public class Nation {
	public int id;
	public Inventory i;
	
	public Nation() {
		id = Boot.nationdb.data.get("Nation") + 1;
		Boot.nationdb.replace("Nation", id);
		i = new Inventory();
	}
}
