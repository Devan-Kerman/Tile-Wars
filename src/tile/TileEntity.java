package tile;

import java.util.Map;

import game.nation.Nation;
import util.Editor;

/**
 * Litterally just a tile with an improvment/thing
 * @author devan
 *
 */
public class TileEntity extends Tile {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2129487004281541842L;
	public Improvement i;
	public int ownerid;
	private Map<String, Object> data;
	@SuppressWarnings("unchecked")
	public <T> T getData(String key) {
		return (T) data.get(key);
	}
	public void demolishImprovment(Nation n) {
		if(i!=null)
			i.demolish(n);
	}
	
	@SuppressWarnings("unchecked")
	public <T> void editData(String key, Editor<T> editor) {
		data.put(key, editor.edit((T) data.remove(key)));
	}
	
	public void deleteData(String key) {
		data.remove(key);
	}
	
}
