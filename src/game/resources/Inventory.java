package game.resources;

import java.util.ArrayList;
import java.util.HashMap;

import exceptions.NotEnoughResourcesException;

public class Inventory {
	public HashMap<String,Integer> resources;
	public void take(String resource, int amount) throws NotEnoughResourcesException {
		int current = resources.get(resource);
		if(current > amount) {
			resources.remove(resource);
			resources.put(resource, current-amount);
		}
		else
			throw new NotEnoughResourcesException("Not enough " + resource + " current amount: " + current);
	}
	
	public void take(ArrayList<Resource> resources) throws NotEnoughResourcesException {
		for(int x = 0; x < resources.size(); x++) {
			Resource r = resources.get(x);
			take(r.resourceID, r.amount);
		}
	}
}